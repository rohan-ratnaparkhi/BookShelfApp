package com.talentica.bookshelfapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.talentica.bookshelfapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rohanr on 8/25/16.
 */
public class StoredUser {

    static String ClassTag = "StoredUser";
    public static void storeUser(Context ctx, String response, String username, String pwd){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_profile), Context.MODE_PRIVATE);
        JSONObject res = null;
        try {
            res = new JSONObject(response);
            String uToken = res.getString("data");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.USER_TOKEN, uToken);
            editor.putString(Constants.KEY_USERNAME, username);
            editor.putString(Constants.KEY_PASSWORD, pwd);
            editor.commit();
            Log.d("Rohan", "User stored successfully");
        } catch (JSONException e) {
            Log.d(Constants.APP_TAG, "inside " + ClassTag + " error: " + e.getMessage());
        }

    }

    public static User getStoredUser(Context ctx){
        User user = new User();
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_profile), Context.MODE_PRIVATE);
        user.setUserName(sharedPref.getString(Constants.KEY_USERNAME, ""));
        user.setUserPassword(sharedPref.getString(Constants.KEY_PASSWORD, ""));
        return user;
    }
}
