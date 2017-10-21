package com.technology.lpjxlove.bfans.Bean;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.technology.lpjxlove.bfans.Util.Constant;

/**
 * 定位到当前的城市位置
 *
 * @author Chen
 * @date 2016年5月28日 20:35:58
 */
public class CurrentLocation implements AMapLocationListener {

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationoption = null;

    private getCity bundle_province_city;


    public CurrentLocation(Context context) {
        // TODO Auto-generated constructor stub
        /**初始化定位参数，设置定位模式为高清**/
        mLocationoption = new AMapLocationClientOption();
        mLocationoption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mLocationoption.setOnceLocation(true);
        mLocationoption.setOnceLocationLatest(true);

        /**初始化定位，设置定位回调监听**/
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(this);
        mLocationClient.setLocationOption(mLocationoption);
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation arg0) {
        // TODO Auto-generated method stub
        if (arg0 != null) {
            //errorcode为0表示定位成功
            if (arg0.getErrorCode() == 0) {
                String tempCity;
                String tempProvince;
                tempProvince = arg0.getProvince();
                tempCity = arg0.getCity();
                Constant.Config.streetName=arg0.getStreet()+arg0.getStreetNum();
                Constant.Config.districtName=arg0.getDistrict();
                Constant.Config.Latitude=arg0.getLatitude();
                Constant.Config.Longitude=arg0.getLongitude();
             //   Logg.i("test",arg0.getAoiName());
                //Config.ProvinceName = tempProvince.substring(0, tempProvince.length()-1);
                //Config.CityName =  tempCity.substring(0, tempCity.length()-1);
                bundle_province_city.getCurrentCity(tempCity.substring(0, tempCity.length() - 1),
                        tempProvince.substring(0,2));
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            }
        }
    }

    public void setcity(getCity getcity) {
        this.bundle_province_city = getcity;
    }

    public interface getCity {
        void getCurrentCity(String currentCity, String currentProvince);
    }
}
