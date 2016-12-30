package com.technology.lpjxlove.bfans.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.technology.lpjxlove.bfans.Adapter.InputTipAdapter;
import com.technology.lpjxlove.bfans.Adapter.PoiSearchAdapter;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.MapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class MapActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener,AdapterView.OnItemClickListener {
    @InjectView(R.id.map_view)
    MapView mapView;
    @InjectView(R.id.recycle_view)
    RecyclerView recycleView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_view)
    ListView listView;
    private AMap aMap;
    private Action1<PoiItem> action1;
    private SearchView mSearchView;
    private Inputtips inputtips;
    private InputTipAdapter aAdapter;
    private List<Tip> searchTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();
        initData();

    }

    private void initMap() {
        double latitude = Constant.Config.Latitude;
        double longitude = Constant.Config.Longitude;
        MapUtils.MoveCameraAndSetLabel(aMap, latitude, longitude);
        PoiSearch(latitude, longitude);
    }


    /**
     * @param latitude
     * @param longitude POI搜索
     */
    private void PoiSearch(double latitude, double longitude) {
        PoiSearch.Query query = new PoiSearch.Query("", "体育休闲服务", Constant.Config.cityName);
        query.setPageSize(10);
        query.setPageNum(1);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }


    private void initData() {
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        action1 = new Action1<PoiItem>() {//项目点击事件
            @Override
            public void call(PoiItem poiItem) {
                Tip t=new Tip();
                t.setPostion(poiItem.getLatLonPoint());
                t.setAddress(poiItem.getTitle());
                setResult(t);
                finish();

            }
        };
        toolbar.inflateMenu(R.menu.location_menu);
        MenuItem Menuitem = toolbar.getMenu().findItem(R.id.search_contact);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchView = (SearchView) MenuItemCompat.getActionView(Menuitem);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                aAdapter=null;
                listView.setVisibility(View.GONE);
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                doSearch(newText);
                return true;
            }
        });
    }

    private void doSearch(String text) {
        InputtipsQuery inputQuery = new InputtipsQuery(text, Constant.Config.cityName);
        inputQuery.setCityLimit(true);
        if (inputtips==null){
            inputtips = new Inputtips(this, this);
        }
        inputtips.setQuery(inputQuery);
        inputtips.requestInputtipsAsyn();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {
                List<PoiItem> poiItemList = poiResult.getPois();
                PoiSearchAdapter adapter = new PoiSearchAdapter(poiItemList, action1);
                recycleView.setAdapter(adapter);
            }
        } else {
            Log.e("info", "获取兴趣点失败！" + i);
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000) {
            aAdapter=new InputTipAdapter(getApplicationContext(),list);
            searchTip=list;
            listView.setAdapter(aAdapter);
            listView.setOnItemClickListener(this);
            listView.setVisibility(View.VISIBLE);
        }else {
            Log.e("test","InputTip is empty!"+i);
        }
    }

    /**设置返回结果
     * @param tip
     */
    private void setResult(Tip tip){
        Intent i=new Intent();
        i.putExtra("Location",tip);
        this.setResult(RESULT_OK,i);
    }


    /**
     * searchListViewItem点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            Tip t=searchTip.get(position);
            t.setAddress(t.getName());
            this.setResult(t);
            finish();
        }
    }
}
