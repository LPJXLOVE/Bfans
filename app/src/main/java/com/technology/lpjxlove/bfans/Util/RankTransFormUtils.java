package com.technology.lpjxlove.bfans.Util;

import android.content.Context;

import com.technology.lpjxlove.bfans.R;

/**
 * Created by LPJXLOVE on 2016/10/20.
 */

public class RankTransFormUtils {

    public static String RankTranFormToName(Context context,int count){
        int index=0;
        if (count<=5){
            index=0;
        }else if (count>5&&count<=10){
            index=1;
        }else if (count>10&&count<=15){
            index=2;
        }else if (count>15&&count<=21){
            index=3;
        }else if (count>21&&count<=27){
            index=4;
        }else if (count>27&&count<=33){
            index=5;
        }else if (count>33&&count<=39){
            index=6;
        }else if (count>39&&count<=46){
            index=7;
        }else if (count>46&&count<=54){
            index=8;
        }else if (count>54&&count<=62){
            index=9;
        }else if (count>62&&count<=71){
            index=10;
        }else if (count>71&&count<=82){
            index=11;
        }else if (count>82&&count<=123){
            index=12;
        }else if (count>123&&count<=204){
            index=13;
        }else if (count>204&&count<=365){
            index=14;
        }else if (count>=366){
            index=15;
        }
        return context.getResources().getStringArray(R.array.client_level)[index];
    }


}
