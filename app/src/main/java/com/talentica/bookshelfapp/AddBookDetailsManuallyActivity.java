package com.talentica.bookshelfapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookDetailsManuallyActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEtTitle, mEtAuthor, mEtPrice, mEtPublisher, mEtIsbn10,
            mEtIsbn13, mEtTags, mEtComments;
    Spinner mSpnBinding, mSpnGenre;
    RadioGroup mRgCondition;
    Button mBtnCancel, mBtnSubmit;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_details_manually);
        setAllFields();
        ctx = this;
        mBtnCancel.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    private void setAllFields() {
        mEtTitle = (EditText) findViewById(R.id.act_add_book_et_title);
        mEtAuthor = (EditText) findViewById(R.id.act_add_book_et_author);
        mEtPrice = (EditText) findViewById(R.id.act_add_book_et_price);
        mEtPublisher = (EditText) findViewById(R.id.act_add_book_et_publisher);
        mEtIsbn10 = (EditText) findViewById(R.id.act_add_book_et_isbn10);
        mEtIsbn13 = (EditText) findViewById(R.id.act_add_book_et_isbn13);
        mEtTags = (EditText) findViewById(R.id.frg_add_book_common_et_tags);
        mEtComments = (EditText) findViewById(R.id.frg_add_book_common_et_comments);
        mSpnBinding = (Spinner) findViewById(R.id.act_add_book_spn_binding);
        mSpnGenre = (Spinner) findViewById(R.id.frg_add_book_common_spn_genre);
        mRgCondition = (RadioGroup) findViewById(R.id.frg_add_book_common_rg_condition);
        mBtnCancel = (Button) findViewById(R.id.act_add_book_btn_cancel);
        mBtnSubmit = (Button) findViewById(R.id.act_add_book_btn_submit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_add_book_btn_cancel:
                finish();
                break;
            case R.id.act_add_book_btn_submit:
                storeBookDetails();
                break;
        }
    }

    private void storeBookDetails() {
        if (bookDetailsValid()) {
            JSONObject bookDtls = getBookDetailsObject();
//            TODO - JSONObjectRequest to save new book details
            JsonObjectRequest saveBookRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Constants.BASE_URL + Constants.ADD_BOOK_API,
                    bookDtls,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(ctx, "Book Added Successfully", Toast.LENGTH_LONG).show();
                            Log.d("Rohan", response.toString());
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ctx, "error", Toast.LENGTH_LONG).show();
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
            SingletonRequestQueue.getInstance(ctx).addToRequestQueue(saveBookRequest);
        }
    }

    private boolean bookDetailsValid() {
//        TODO - validate all the book details entered
        boolean validDtls = true;
        if(isEditTextEmpty(mEtTitle)){
            mEtTitle.setError("Book name is mandatory");
            validDtls = false;
        }
        if(isEditTextEmpty(mEtAuthor)){
            mEtAuthor.setError("Author name is mandatory");
            validDtls = false;
        }
//        TODO - any one isbn entered is ok; currently checking for both
        if(isEditTextEmpty(mEtIsbn13)){
            mEtIsbn13.setError("ISBN 13 is mandatory");
            validDtls = false;
        }
        if(isEditTextEmpty(mEtIsbn10)){
            mEtIsbn10.setError("ISBN 10 is mandatory");
            validDtls = false;
        }
        if(isEditTextEmpty(mEtTags)){
            mEtTags.setError("Tags are mandatory");
            validDtls = false;
        }
//        TODO - binding and genre spinner item validations
        int radioId = mRgCondition.getCheckedRadioButtonId();
        if(radioId == -1){
            Toast.makeText(this, "Condition of book is mandatory", Toast.LENGTH_LONG).show();
            validDtls = false;
        }
        return validDtls;
    }

    private boolean isEditTextEmpty(EditText et){
        if(et.getText().toString().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private JSONObject getBookDetailsObject() {
//        TODO - getting image for book and adding it to JSON
        JSONObject book = new JSONObject();
        try {
            book.put("isbn13", mEtIsbn13.getText().toString());
            book.put("isbn10", mEtIsbn10.getText().toString());
            book.put("name", mEtTitle.getText().toString());

            List<String> bookTags = Arrays.asList(mEtTags.getText().toString().split(","));
            book.put("tags", new JSONArray(bookTags));
            book.put("publishedOn", new Date().toString());

            List<JSONObject> authors = new ArrayList<JSONObject>();
            JSONObject newAutor = new JSONObject();
            newAutor.put("name", mEtAuthor.getText().toString());
            newAutor.put("email", "dummy@gmail.com");
            authors.add(newAutor);
            book.put("authors", new JSONArray(authors));

            List<JSONObject> comments = new ArrayList<JSONObject>();
            JSONObject comment = new JSONObject();
            comment.put("body", mEtComments.getText().toString());
            comments.add(comment);
            book.put("comments", new JSONArray(comments));

            JSONObject publisher = new JSONObject();
            publisher.put("name", mEtPublisher.getText().toString());
            JSONObject address = new JSONObject();
            address.put("street1", "");
            address.put("street2", "");
            address.put("country", "");
            address.put("city", "");
            address.put("state", "");
            address.put("zip", "");
            publisher.put("address", address);
            List<String> publisherUrls = new ArrayList<String>();
            publisherUrls.add("dummy.com");
            publisher.put("url", new JSONArray(publisherUrls));
            book.put("publisher", publisher);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Rohan", book.toString());

        return book;
    }

}
