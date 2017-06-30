package com.techflitter.testexam.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Toast;

import com.techflitter.testexam.R;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.DecimalFormat;


public class CommonUtils {

    public static boolean isNumber(String str) {
        boolean isNum = str.matches("[0-9]+");
        return isNum;
    }


    public static boolean isEmailValid(String email) {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isPasswordLongEnough(String password) {
        return !(password == null || password.length() < 6);
    }

    public static void serviceError(Context context, Throwable throwable) {
        String temp = throwable.getMessage();
        String errorMessage = temp;
        if (throwable instanceof SocketTimeoutException) {
            errorMessage = context.getString(R.string.msg_socket_timeout_exception);
        } else if (throwable instanceof ConnectException) {
            errorMessage = context.getString(R.string.msg_connection_exception);
        } else if (throwable instanceof RuntimeException) {
            errorMessage = context.getString(R.string.msg_runtime_exception);
        } else if (throwable instanceof UnknownHostException) {
            errorMessage = context.getString(R.string.msg_unknown_host_exception);
        } else if (throwable instanceof UnknownServiceException) {
            errorMessage = context.getString(R.string.msg_unknown_service_exception);
        }
        try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.msg_error)
                    .setMessage(errorMessage)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static void onFailure(Context context, int code, String TAG) {
        if (code == 101 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----token error");
        } else if (code == 102 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----token time out");
        } else if (code == 201 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----lack param");
        } else if (code == 202 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----data null");
            Toast.makeText(context, "data null", Toast.LENGTH_LONG).show();
        } else if (code == 203 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----data already existing");
            Toast.makeText(context, "data already existing", Toast.LENGTH_LONG).show();
        } else if (code == 500 && context != null) {
            Log.i(TAG, "onResponse:http code=" + code + "-----service error");
        }
    }

    public static boolean isNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void networkDialog(final Context context) {
        try {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.network_message)
                    .setPositiveButton(R.string.countersign, null)
                    .create()
                    .show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static void networkDialog(Activity activity, final boolean isFinish) {
        final WeakReference<Activity> activityRef = new WeakReference<>(activity);
        try {
            new AlertDialog.Builder(activity)
                    .setMessage(R.string.network_message)
                    .setPositiveButton(R.string.countersign, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (isFinish) {
                                Activity activity = activityRef.get();
                                if (activity != null) {
                                    activity.finish();
                                }
                            }
                        }
                    })
                    .create()
                    .show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }


    public static void hintDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.network_hint)
                .setMessage(message)
                .setPositiveButton(R.string.countersign, null)
                .create()
                .show();
    }


    public static String DoubleRetainTwo(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }
}
