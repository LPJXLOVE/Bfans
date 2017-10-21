package com.technology.lpjxlove.bfans.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.technology.lpjxlove.bfans.Adapter.SortAdapter;
import com.technology.lpjxlove.bfans.Bean.SortModel;
import com.technology.lpjxlove.bfans.R;
import com.technology.lpjxlove.bfans.UI.CustomView.AlphabetSlideBar;
import com.technology.lpjxlove.bfans.Util.CharacterParser;
import com.technology.lpjxlove.bfans.Util.Constant;
import com.technology.lpjxlove.bfans.Util.LocationUtils;
import com.technology.lpjxlove.bfans.Util.PinyinComparatorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 首页进行城市选择的界面
 * @author Chen
 *
 */
public class SelectCityActivity extends AppCompatActivity implements LocationUtils.OnFinishListener {
	
	private Toolbar mToolbar;

	private ListView mProvinceListView;
	/**筛选适配器**/
	private SortAdapter mSortAdapter;
	/**省份数据**/
	private List<SortModel> mProvinceListData;
	
	/**汉字转化为拼音类**/
	private CharacterParser mCharacterParser;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparatorUtil mPinyinComparator;
	
	/**当前城市**/
	private TextView mCurrentCity;
	private String mCurrentProvince;
	private LocationUtils locationUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_selectcity);
		initView();
	}


	private void initView() {
		mToolbar= (Toolbar) findViewById(R.id.titleBar);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ImageView mCurrentLocationSelect = (ImageView) findViewById(R.id.iv_location_select);

		//实例化汉字转拼音
		mCharacterParser = CharacterParser.getInstance();
		
		mPinyinComparator = new PinyinComparatorUtil();
		
		//mBack=(TextView) findViewById(R.id.tv_back);
		TextView mDialog = (TextView) findViewById(R.id.tv_dialog);
		mProvinceListView = (ListView) findViewById(R.id.lv_province);
		EditText mEditText = (EditText) findViewById(R.id.searchEditext);
		mCurrentCity = (TextView) findViewById(R.id.tv_current_city);
		/*右侧筛选字母导航条*/
		AlphabetSlideBar mAlphabetSlideBar = (AlphabetSlideBar) findViewById(R.id.v_slidingbar);
		mAlphabetSlideBar.setTextView(mDialog);
		
		//设置右侧触摸监听
		mAlphabetSlideBar.setOnTouchingLetterChangedListener(new AlphabetSlideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub
				//该字母首次出现的位置
				int position = mSortAdapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					mProvinceListView.setSelection(position);
				}
			}
		});

		mCurrentLocationSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mStr = mCurrentCity.getText().toString();
				if(mStr.equals("正在定位...")){

				}else{
					if (mStr != null) {
						if (mStr.length() > 2) {
							mStr = mStr.substring(0, mStr.length() - 1);
						}
						Constant.Config.cityName = mStr;
						Constant.Config.provinceName =mCurrentProvince;
						//将当前数据返回
						setResult(RESULT_OK);
						finish();
					}
				}

			}
		});
		
		mProvinceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
		    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
		    String mStr=((SortModel)mSortAdapter.getItem(position)).getName();
				int mStrLength=mStr.length();
				String mCurrentProvince=mStr.substring(mStrLength-2,mStrLength);
				mStr = mStr.substring(0,mStrLength-2).replace("市","");
		    Constant.Config.provinceName = mCurrentProvince;
		    Constant.Config.cityName = mStr;
		    //将当前数据返回
		    finish();
			}
		});
		
		mProvinceListData =filledData(getResources().getStringArray(R.array.province));
		
		//根据a-z排序源数据
		Collections.sort(mProvinceListData,mPinyinComparator);
		mSortAdapter = new SortAdapter(this, mProvinceListData);
		mProvinceListView.setAdapter(mSortAdapter);

		//根据输入框输入值的改变来过滤搜索
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		locationUtils=new LocationUtils(getApplicationContext());
		locationUtils.setOnFinishListener(this);
		locationUtils.startLocation();

	/*	*//**当前城市回调函数**//*
		currentLocation = new CurrentLocation(BootApp.getAppContext());
		currentLocation.setcity(new getCity() {
			@Override
			public void getCurrentCity(String currentCity, String currentProvince) {
				mCurrentCity.setText(currentCity);
				mCurrentProvince=currentProvince;
			}
		});*/
	}

    /**
     * 为list填充数据
     * @param date
     * @return
     */
	private List<SortModel> filledData(String[] date) {
		// TODO Auto-generated method stub
	List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = mCharacterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
	}
	
	private void filterData(String string) {
		// TODO Auto-generated method stub
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if(TextUtils.isEmpty(string)){
			filterDateList = mProvinceListData;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : mProvinceListData){
				String name = sortModel.getName();
				if(name.indexOf(string.toString()) != -1 
						|| mCharacterParser.getSelling(name).startsWith
						(string.toString())){
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, mPinyinComparator);
		mSortAdapter.updateListView(filterDateList);
	}

	@Override
	public void onFinish() {
		mCurrentCity.setText(Constant.Config.cityName);
		mCurrentProvince= Constant.Config.provinceName;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationUtils.onDestroy();
	}
}
