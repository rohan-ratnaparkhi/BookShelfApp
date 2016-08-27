package com.talentica.bookshelfapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mImg;
    TextView mTvTitle;
    TextView mTvAuthor;
    TextView mTvLender;
    TextView mTvBinding;
    TextView mTvCondition;
    TextView mTvGenre;
    TextView mTvPublishedOn;
    TextView mTvPublisher;
    TextView mTvIsbn10;
    TextView mTvIsbn13;
    TextView mTvEdition;
    TextView mTvListPrice;
    TextView mTvTags;
    TextView mTvComments;
    Button mBtnBorrow;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ctx = this;
        setLayoutFields();
        mBtnBorrow.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String bookId = (String) bundle.get("bookId");
        populateBookDetails(bookId);
    }

    @Override
    public void onClick(View view) {

    }

    private void setLayoutFields() {
        mImg = (ImageView) findViewById(R.id.act_book_dtls_img);
        mTvTitle = (TextView) findViewById(R.id.act_book_dtls_tv_title);
        mTvAuthor = (TextView) findViewById(R.id.act_book_dtls_tv_author);
        mTvLender = (TextView) findViewById(R.id.act_book_dtls_tv_lender);
        mTvBinding = (TextView) findViewById(R.id.act_book_dtls_tv_binding);
        mTvCondition = (TextView) findViewById(R.id.act_book_dtls_tv_condition);
        mTvGenre = (TextView) findViewById(R.id.act_book_dtls_tv_genre);
        mTvPublishedOn = (TextView) findViewById(R.id.act_book_dtls_tv_published);
        mTvPublisher = (TextView) findViewById(R.id.act_book_dtls_tv_publisher);
        mTvIsbn10 = (TextView) findViewById(R.id.act_book_dtls_tv_isbn10);
        mTvIsbn13 = (TextView) findViewById(R.id.act_book_dtls_tv_isbn13);
        mTvEdition = (TextView) findViewById(R.id.act_book_dtls_tv_edition);
        mTvListPrice = (TextView) findViewById(R.id.act_book_dtls_tv_price);
        mTvTags = (TextView) findViewById(R.id.act_book_dtls_tv_tags);
        mTvComments = (TextView) findViewById(R.id.act_book_dtls_tv_owner_comment);
        mBtnBorrow = (Button) findViewById(R.id.act_book_dtls_btn_borrow);
    }


    private void populateBookDetails(String bookId) {
        try {
            JsonObjectRequest fetchBookDtlsRequest = new JsonObjectRequest(Request.Method.GET,
                    Constants.BASE_URL + Constants.GET_BOOK_BY_ID.replace("{id}", bookId),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Rohan", response.toString());
                            displayBookDetails(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + StoredUser.getStoredUser(ctx).getUserToken());
                    return headers;
                }
            };

            SingletonRequestQueue.getInstance(ctx).addToRequestQueue(fetchBookDtlsRequest);

        } catch (Exception e) {
            Log.d("Rohan", "error: " + e.getMessage());
        }
    }

    private void displayBookDetails(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject("data");
            mTvIsbn10.setText(data.getString("isbn10"));
            mTvIsbn13.setText(data.getString("isbn13"));
            mTvTitle.setText(data.getString("name"));
//            TODO - check for publisher, lender and book image
            mTvPublisher.setText(data.getJSONObject("publisher").getString("name"));
            Picasso.with(ctx).load(Constants.TEMP_BOOK_IMG).into(mImg);

            mTvPublishedOn.setText(CommonUtil.getFormattedDate(data.getString("publishedOn")));
            JSONArray authors = data.getJSONArray("authors");
            if (authors.length() > 0) {
                mTvAuthor.setText(authors.getJSONObject(0).getString("name"));
            }
            JSONArray commentArr = data.getJSONArray("comments");
            if (commentArr.length() > 0) {
                mTvComments.setText(commentArr.getJSONObject(0).getString("body"));
            }
            JSONArray tagArr = data.getJSONArray("tags");
            if (tagArr.length() > 0) {
                String[] strArr = new String[tagArr.length()];
                for (int i = 0; i < tagArr.length(); i++) {
                    strArr[i] = tagArr.getString(i);
                }
                mTvTags.setText(TextUtils.join(", ", strArr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
