package com.skyvn.hw.util;

import android.view.View;
import android.widget.Toast;

public class ToastManager {

    public static void showShort(String message) {
        Toast sToast = new Toast(AppManager.getAppManager().curremtActivity());
//                = Toast.makeText(AppManager.getAppManager().curremtActivity(), null, Toast.LENGTH_SHORT);
//        toast.setText(message);
//        toast.show();
        View v = Toast.makeText(AppManager.getAppManager().curremtActivity(), "", Toast.LENGTH_SHORT).getView();
        sToast.setView(v);
        sToast.setText(message);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }

}
