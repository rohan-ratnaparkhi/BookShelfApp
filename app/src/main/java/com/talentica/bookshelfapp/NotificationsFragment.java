package com.talentica.bookshelfapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talentica.bookshelfapp.adapter.NotificationListAdapter;
import com.talentica.bookshelfapp.adapter.TaskListAdapter;
import com.talentica.bookshelfapp.model.Notifications;

import java.util.List;

/**
 * Created by rohanr on 8/30/16.
 */
public class NotificationsFragment extends Fragment {

    Context ctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ctx = getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllNotifications();
    }

    private void getAllNotifications() {
//        TODO - replace with API call once it is ready; using dummy data for now
        List<Notifications> notificationsList = ResponseUtil.createNotificationsListFromResponse();
        setNotificationsAdapter(notificationsList);
    }

    private void setNotificationsAdapter(List<Notifications> notificationsList) {
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.frg_notification_rv);
        NotificationListAdapter adapter = new NotificationListAdapter(ctx, notificationsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new SimpleItemDividerDecoration(ctx));
    }
}
