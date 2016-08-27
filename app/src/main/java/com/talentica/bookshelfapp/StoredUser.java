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

    public static void storeUser(Context ctx, User user) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_profile), Context.MODE_PRIVATE);
        JSONObject res = null;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_TOKEN, user.getUserToken());
        editor.putString(Constants.KEY_EMAIL, user.getUserEmail());
        editor.putString(Constants.KEY_PASSWORD, user.getUserPassword());
        editor.commit();
        Log.d("Rohan", "User stored successfully");
    }

    public static User getStoredUser(Context ctx) {
        User user = new User();
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_profile), Context.MODE_PRIVATE);
        user.setUserEmail(sharedPref.getString(Constants.KEY_EMAIL, ""));
        user.setUserPassword(sharedPref.getString(Constants.KEY_PASSWORD, ""));
        user.setUserToken(sharedPref.getString(Constants.USER_TOKEN, ""));
        return user;
    }
}
