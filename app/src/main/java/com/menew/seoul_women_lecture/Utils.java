package com.menew.seoul_women_lecture;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2015-10-26.
 */
public class Utils {
    public static boolean isNetworkAvailable(Context context) { // network 연결 상태 확인
        try {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState(); // wifi

            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
                return true;
            }

            NetworkInfo.State mobile = conMan.getNetworkInfo(0).getState(); // mobile ConnectivityManager.TYPE_MOBILE
            if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}
