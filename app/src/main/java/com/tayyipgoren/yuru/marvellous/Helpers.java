package com.tayyipgoren.yuru.marvellous;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

public class Helpers {
    public static boolean haveInternet(Context ctx) {

        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
            return false;
        }
        return true;
    }

    public static int DPtoPixels(Resources r, float DP) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, r.getDisplayMetrics());
    }
}
