package com.talentica.bookshelfapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.talentica.bookshelfapp.adapter.BookListAdapter;
import com.talentica.bookshelfapp.model.Book;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rohanr on 8/27/16.
 */
public class BookListsFragment extends Fragment {

    Context ctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_lists, container, false);
        ctx = getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayRecentlyAddedBooks();
    }

    private void displayRecentlyAddedBooks() {
//        TODO - change url once recently added book api is working
        try {
            JsonObjectRequest recentlyAddedRequest = new JsonObjectRequest(Request.Method.GET,
                    Constants.BASE_URL + Constants.ALL_BOOKS_API,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Rohan", "1: " + response.toString());
                            List<Book> bookList = ResponseUtil.createBooksListFromResponse(response);
                            setAdapterForBooks(bookList);
//                            displayMostReadBooksList(Constants.BASE_URL + Constants.ALL_BOOKS_API);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rohan", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_PAGE, "1");
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + StoredUser.getStoredUser(ctx).getUserToken());
                    return headers;
                }
            };

            SingletonRequestQueue.getInstance(ctx).addToRequestQueue(recentlyAddedRequest);
        } catch (Exception e) {
            Log.d("Rohan", e.toString());
        }
    }

    private void setAdapterForBooks(List<Book> bookList) {
        BookListAdapter adapter = new BookListAdapter(ctx, bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.frg_book_list_rv_recently_added);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
    }
}
