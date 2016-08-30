package com.talentica.bookshelfapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talentica.bookshelfapp.Constants;
import com.talentica.bookshelfapp.R;
import com.talentica.bookshelfapp.model.Notifications;

import java.util.List;

/**
 * Created by rohanr on 8/30/16.
 */
public class NotificationListAdapter  extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    Context ctx;
    List<Notifications> notificationsList;

    public NotificationListAdapter(Context ctx, List<Notifications> notificationsList) {
        this.ctx = ctx;
        this.notificationsList = notificationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notifications notification = notificationsList.get(position);
        holder.title.setText(notification.getBookTitle());
        if(notification.isBorrower()){
            holder.lenderOrBorrowerName.setText(notification.getBorrowerName());
            holder.returnDueToOrFrom.setText(Constants.DUE_TO);
        } else if(notification.isLender()){
            holder.lenderOrBorrowerName.setText(notification.getLenderName());
            holder.returnDueToOrFrom.setText(Constants.DUE_FROM);
        }
        Picasso.with(ctx).load(notification.getBookImg()).into(holder.img);
        holder.img.setAdjustViewBounds(true);
        holder.returnDate.setText(notification.getReturnDate());
        holder.notificationDateTime.setText(notification.getNotificationDateTime());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, lenderOrBorrowerName, returnDate, notificationDateTime, returnDueToOrFrom;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.row_notification_img);
            title = (TextView) itemView.findViewById(R.id.row_notification_tv_title);
            lenderOrBorrowerName = (TextView) itemView.findViewById(R.id.row_notification_tv_username);
            returnDate = (TextView) itemView.findViewById(R.id.row_notification_tv_date);
            notificationDateTime = (TextView) itemView.findViewById(R.id.row_notification_tv_notification_date_time);
            returnDueToOrFrom = (TextView) itemView.findViewById(R.id.row_notification_tv_to_or_from);
        }

    }
}


