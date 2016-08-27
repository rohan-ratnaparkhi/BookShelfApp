package com.talentica.bookshelfapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Context ctx;
    EditText mEtName;
    EditText mEtEmail;
    EditText mEtPassword;
    Button mBtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ctx = this;

        mEtName = (EditText) findViewById(R.id.act_signup_et_name);
        mEtEmail = (EditText) findViewById(R.id.act_signup_et_email);
        mEtPassword = (EditText) findViewById(R.id.act_signup_et_password);
        mBtnSignUp = (Button) findViewById(R.id.act_signup_btn_register);

        mBtnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.act_signup_btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.USER_SIGN_UP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(CommonUtil.isSuccessResponse(response)){
                            try {
                                JSONObject res = new JSONObject(response);
                                JSONObject data = res.getJSONObject("data");
                                User user = new User();
                                user.setUserId(data.getString(Constants.KEY_USER_ID));
                                user.setUserName(mEtName.getText().toString());
                                user.setUserEmail(mEtEmail.getText().toString());
                                user.setUserPassword(mEtPassword.getText().toString());
                                StoredUser.storeUser(ctx, user);
                                displayLoginPage();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                displaySignUpError();
                            }
                        } else {
                            displaySignUpError();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displaySignUpError();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, mEtName.getText().toString());
                params.put(Constants.KEY_EMAIL, mEtEmail.getText().toString());
                params.put(Constants.KEY_PASSWORD, mEtPassword.getText().toString());
//                TODO - if roles are to be specified then add sending roles logic below
//                String roles = "[{\"name\":\"string\"}]";
//                params.put(Constants.KEY_ROLES, roles);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(Constants.KEY_AUTHORIZATION, Constants.APP_TOKEN);
                return headers;
            }
        };

        SingletonRequestQueue.getInstance(ctx).addToRequestQueue(signUpRequest);
    }

    private void displayLoginPage(){
        finish();
    }

    private void displaySignUpError(){
        CommonUtil.displayErrorMsg(ctx, Constants.ERROR_OCCURRED);
    }
}
