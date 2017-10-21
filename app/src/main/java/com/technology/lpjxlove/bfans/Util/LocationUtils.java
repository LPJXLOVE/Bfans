package com.technology.lpjxlove.bfans.Util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by LPJXLOVE on 2016/9/22.
 */
public class LocationUtils {
    private AMapLocationClient mLocationClient;
    private Context context;
    private OnFinishListener onFinishListener;

    public LocationUtils(Context context) {
        this.context=context;

    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void startLocation(){
        mLocationClient = new AMapLocationClient(context);
        AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation!=null){
                    if (aMapLocation.getErrorCode()==0){

                        Constant.Config.provinceName =aMapLocation.getProvince();
                        Constant.Config.cityName=aMapLocation.getCity();
                        Constant.Config.streetName=aMapLocation.getStreet();
                        Constant.Config.districtName=aMapLocation.getDistrict();
                        Constant.Config.Latitude=aMapLocation.getLatitude();
                        Constant.Config.Longitude=aMapLocation.getLongitude();
                        onFinishListener.onFinish();
                    }else {
                        Log.e("info","定位失败"+aMapLocation.getErrorInfo()+aMapLocation.getErrorCode());
                    }
                    Log.e("info","定位"+aMapLocation.getErrorInfo()+aMapLocation.getErrorCode());
                }

            }
        });
        mLocationClientOption.setOnceLocation(true);
        mLocationClientOption.setOnceLocationLatest(true);
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();
    }


    public void onDestroy(){
        mLocationClient.onDestroy();
    }

    public interface  OnFinishListener{
        void onFinish();
    }




}
