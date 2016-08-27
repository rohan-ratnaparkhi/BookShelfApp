package com.talentica.bookshelfapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.talentica.bookshelfapp.adapter.BookGridAdapter;
import com.talentica.bookshelfapp.model.Book;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rohanr on 8/27/16.
 */
public class BookGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_grid, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        TODO - get books and display in grid view
        getAllBooks();
    }

    private void getAllBooks() {
        try {
            JsonObjectRequest fetchAllBooks = new JsonObjectRequest(Request.Method.GET,
                    Constants.BASE_URL + Constants.ALL_BOOKS_API,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(Constants.APP_TAG, "all books" + response.toString());
                            bookList = ResponseUtil.createBooksListFromResponse(response);
                            setGridAdapter();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(Constants.APP_TAG, "all books" + error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + StoredUser.getStoredUser(getContext()).getUserToken());
                    return headers;
                }
            };

            SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(fetchAllBooks);
        } catch (Exception e) {
            Log.d(Constants.APP_TAG, e.toString());
        }
    }

    private void setGridAdapter() {
        GridView bookGrid = (GridView) getView().findViewById(R.id.frg_book_grid);
        bookGrid.setAdapter(new BookGridAdapter(getContext(), bookList));
        bookGrid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(Constants.APP_TAG, "book clicked");
        Intent displayDtls = new Intent(getContext(), BookDetailsActivity.class);
        displayDtls.putExtra("bookId", bookList.get(i).getBookId());
        startActivity(displayDtls);
    }
}
