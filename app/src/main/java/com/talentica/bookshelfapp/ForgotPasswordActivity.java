package com.talentica.bookshelfapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Context ctx;
    EditText mEtEmail;
    Button mBtnSendMail;
    private int mStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ctx = this;

        mEtEmail = (EditText) findViewById(R.id.act_forgot_pwd_et_email);
        mBtnSendMail = (Button) findViewById(R.id.act_forgot_pwd_btn_send_mail);

        mBtnSendMail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.act_forgot_pwd_btn_send_mail:
                sendPasswordResetLink();
                break;
        }
    }

    private void sendPasswordResetLink() {

        if(!CommonUtil.isEmailValid(mEtEmail.getText().toString().trim())) {
            Toast.makeText(ctx, "Please enter valid email id", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest forgotPasswordRequest = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.USER_FORGOT_PWD_API + mEtEmail.getText().toString().trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        displayMessage(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayErrorMessage();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, mEtEmail.getText().toString().trim());
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("Rohan", "status = " + mStatusCode);
                saveStatusCode(mStatusCode);
                return super.parseNetworkResponse(response);
            }
        };

        SingletonRequestQueue.getInstance(ctx).addToRequestQueue(forgotPasswordRequest);
    }

    private void displayMessage(String response) {
        if(this.mStatusCode == 200){
            CommonUtil.displaySuccessMsg(ctx, Constants.PASSWORD_RESET_MAIL_SENT);
        }
    }

    private void saveStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    private void displayErrorMessage() {
        if(this.mStatusCode == 404){
            CommonUtil.displayErrorMsg(ctx, Constants.USER_NOT_FOUND);
        } else {
            CommonUtil.displayErrorMsg(ctx, Constants.ERROR_OCCURRED);
        }

    }
}
