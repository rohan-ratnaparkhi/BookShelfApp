package com.talentica.bookshelfapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

/**
 * Created by rohanr on 9/6/16.
 */
public class MyAccountsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_accounts, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TabHost host = (TabHost) getView().findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec(Constants.MY_BOOKS);
        spec.setContent(R.id.tab1);
        spec.setIndicator(Constants.MY_BOOKS);
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec(Constants.BOOKS_BORROWED);
        spec.setContent(R.id.tab2);
        spec.setIndicator(Constants.BOOKS_BORROWED);
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec(Constants.USER_PROFILE);
        spec.setContent(R.id.tab3);
        spec.setIndicator(Constants.USER_PROFILE);
        host.addTab(spec);
    }
}
