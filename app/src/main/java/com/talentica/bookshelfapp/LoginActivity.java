package com.talentica.bookshelfapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talentica.bookshelfapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mBtnSignIn, mBtnSignUp, mBtnForgotPassword;
    EditText mEtEmailId, mEtPassword;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        // Hide the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        mEtEmailId = (EditText) findViewById(R.id.act_login_et_email);
        mEtPassword = (EditText) findViewById(R.id.act_login_et_password);
        mBtnSignIn = (Button) findViewById(R.id.act_login_btn_sign_in);
        mBtnSignUp = (Button) findViewById(R.id.act_login_btn_sign_up);
        mBtnForgotPassword = (Button) findViewById(R.id.act_login_btn_forgot_password);

        mBtnSignIn.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
        mBtnForgotPassword.setOnClickListener(this);

        checkForStoredUserAndLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForStoredUserAndLogin();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_btn_forgot_password:
//                Intent showForgotPassword = new Intent(ctx, ForgotPasswordActivity.class);
//                startActivity(showForgotPassword);
                break;
            case R.id.act_login_btn_sign_in:
                checkUserLogin();
                break;
            case R.id.act_login_btn_sign_up:
                Intent signUpForm = new Intent(ctx, SignUpActivity.class);
                startActivity(signUpForm);
                break;
        }
    }

    private void checkUserLogin() {
        final String username = mEtEmailId.getText().toString().trim();
        final String pwd = mEtPassword.getText().toString();

        if(!(CommonUtil.isEmailValid(username) && CommonUtil.isPasswordValid(pwd))) {
            Toast.makeText(ctx, getString(R.string.invalid_login_msg), Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                        if(CommonUtil.isSuccessResponse(response)){
                            try {
                                JSONObject res = new JSONObject(response);
                                User user = new User();
                                user.setUserEmail(mEtEmailId.getText().toString());
                                user.setUserPassword(mEtPassword.getText().toString());
                                user.setUserToken(res.getString("data"));
                                StoredUser.storeUser(ctx, user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //                            displayHomePage();
                        } else {
                            Toast.makeText(ctx, "Some error occurred", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "Some error occurred here", Toast.LENGTH_LONG).show();
//                        displayLoginError();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, username);
                params.put(Constants.KEY_PASSWORD, pwd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void checkForStoredUserAndLogin(){
        User user = StoredUser.getStoredUser(ctx);
        mEtEmailId.setText(user.getUserEmail());
        mEtPassword.setText(user.getUserPassword());
        checkUserLogin();
    }



}
