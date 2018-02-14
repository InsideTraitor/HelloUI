package com.droidrocks.demos.helloui.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.droidrocks.demos.helloui.general.NavDrawer;

/**
 * Created by hollisinman on 2/8/18.
 */

public class Permissions {
    
    public void Permissions() {};
    
    public static boolean checkPermission(Activity activity, String permission, int requestCode) {

        boolean hasPermission = false;

        if (ContextCompat.checkSelfPermission(activity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {

                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
            }
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }
}
