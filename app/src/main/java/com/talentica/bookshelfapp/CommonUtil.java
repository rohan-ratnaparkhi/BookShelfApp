package com.talentica.bookshelfapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String getFormattedDate(String initialDateString){
        String formattedDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat toDisplayFormat = new SimpleDateFormat("MMM yyyy");
        try {
            Date date = format.parse(initialDateString);
            formattedDate = toDisplayFormat.format(date);
        } catch (ParseException e) {
            Log.d("Rohan", "Error while converting date");
            formattedDate = "";
        }
        return formattedDate;
    }
}
