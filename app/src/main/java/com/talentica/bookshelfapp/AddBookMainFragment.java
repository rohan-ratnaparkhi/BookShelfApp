package com.talentica.bookshelfapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by rohanr on 8/27/16.
 */
public class AddBookMainFragment extends Fragment implements View.OnClickListener {

    Button mBtnAddManually;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnAddManually = (Button) getView().findViewById(R.id.frg_add_book_btn_manually);
        mBtnAddManually.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddBookDetailsManuallyActivity.class);
        startActivity(intent);
    }
}
