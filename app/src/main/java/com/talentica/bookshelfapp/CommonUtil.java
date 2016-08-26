package com.talentica.bookshelfapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rohanr on 8/26/16.
 */
public class CommonUtil {
    public static boolean isEmailValid(String emailId){
//        TODO - email validation
        return true;
    }

    public static boolean isPasswordValid(String pwd){
//        TODO - password validation
        return true;
    }

    public static boolean isSuccessResponse(String response) {
//        TODO - check if response is Ok
        return true;
    }

    public static void displayErrorMsg(Context ctx, String errMsg){
        Toast.makeText(ctx, errMsg, Toast.LENGTH_LONG).show();
    }

    public static void displaySuccessMsg(Context ctx, String errMsg){
        Toast.makeText(ctx, errMsg, Toast.LENGTH_LONG).show();
    }

}
