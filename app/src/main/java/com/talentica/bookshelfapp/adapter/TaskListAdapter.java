package com.talentica.bookshelfapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.talentica.bookshelfapp.CommonUtil;
import com.talentica.bookshelfapp.Constants;
import com.talentica.bookshelfapp.R;
import com.talentica.bookshelfapp.SingletonRequestQueue;
import com.talentica.bookshelfapp.StoredUser;
import com.talentica.bookshelfapp.model.Book;
import com.talentica.bookshelfapp.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rohanr on 8/29/16.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    Context ctx;
    List<Task> taskList;

    public TaskListAdapter(Context ctx, List<Task> taskList) {
        this.ctx = ctx;
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getBookTitle());
        holder.author.setText(task.getBookAuthor());
        holder.requestedBy.setText(task.getBookRequestedBy());
        Picasso.with(ctx).load(task.getBookImgUrl()).into(holder.img);
        holder.img.setAdjustViewBounds(true);
        holder.requestDate.setText(task.getBookRequestedDate());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView title, author, requestedBy, requestDate;
        ImageButton btnReject, btnAccept;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_task_img);
            title = (TextView) itemView.findViewById(R.id.item_task_tv_title);
            author = (TextView) itemView.findViewById(R.id.item_task_tv_author);
            requestDate = (TextView) itemView.findViewById(R.id.item_task_tv_date);
            requestedBy = (TextView) itemView.findViewById(R.id.item_task_tv_requested_by_name);
            btnAccept = (ImageButton) itemView.findViewById(R.id.item_task_img_btn_accept);
            btnReject = (ImageButton) itemView.findViewById(R.id.item_task_img_btn_reject);
            btnReject.setOnClickListener(this);
            btnAccept.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_task_img_btn_accept:
                    approveBookRequest();
                    break;
                case R.id.item_task_img_btn_reject:
                    rejectBookRequest();
                    break;
            }
        }

        private void rejectBookRequest() {
            int pos = getAdapterPosition();
            Log.d("Rohan", "clicked rej: " + pos);
            Log.d("Rohan","id:" + taskList.get(pos).getBookId());
            Log.d("Rohan", "userId:" + taskList.get(pos).getBookRequestedById());
            String rejectUrl = Constants.BASE_URL + Constants.BORROW_BOOK_REJECT_API.replace("{bookId}", taskList.get(pos).getBookId()).replace("{userId}", taskList.get(pos).getBookRequestedById());
            makeApiRequest(rejectUrl);
        }

        private void approveBookRequest() {
            int pos = getAdapterPosition();
            Log.d("Rohan", "clicked acc: " + pos);
            Log.d("Rohan","id:" + taskList.get(pos).getBookId());
            Log.d("Rohan", "userId:" + taskList.get(pos).getBookRequestedById());
            String acceptUrl = Constants.BASE_URL + Constants.BORROW_BOOK_ACCEPT_API.replace("{bookId}", taskList.get(pos).getBookId()).replace("{userId}", taskList.get(pos).getBookRequestedById());
            makeApiRequest(acceptUrl);
        }

        private void makeApiRequest(String url) {
            StringRequest request = new StringRequest(Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            TODO - remove item row from recyclerview and update recyclerview to display only remaining items
                            Log.d("Rohan", "book request success: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            CommonUtil.displayErrorMsg(ctx, Constants.ERROR_OCCURRED);
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(Constants.KEY_AUTHORIZATION, Constants.AUTH_PREPEND + StoredUser.getStoredUser(ctx).getUserToken());
                    return headers;
                }
            };
            SingletonRequestQueue.getInstance(ctx).addToRequestQueue(request);
        }

    }
}

