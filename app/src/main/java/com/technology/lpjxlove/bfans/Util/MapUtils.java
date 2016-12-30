package com.technology.lpjxlove.bfans.Util;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.technology.lpjxlove.bfans.R;

/**
 * Created by LPJXLOVE on 2016/9/22.
 */
public class MapUtils {


    public static void MoveCameraAndSetLabel(AMap aMap,double latitude,double longitude){
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(latitude,longitude),
                16,
                0,
                0
        )));
        MarkerOptions options=new MarkerOptions();
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(Resources.getSystem(), R.drawable.ic_place_black_24dp)));
        options.position(new LatLng(latitude,longitude));
        aMap.addMarker(options);
    }

}
