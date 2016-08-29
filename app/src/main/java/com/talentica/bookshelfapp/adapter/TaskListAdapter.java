package com.talentica.bookshelfapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talentica.bookshelfapp.R;
import com.talentica.bookshelfapp.model.Book;
import com.talentica.bookshelfapp.model.Task;

import java.util.List;

/**
 * Created by rohanr on 8/29/16.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    Context ctx;
    List<Task> taskList;

    public TaskListAdapter(Context ctx, List<Task> taskList){
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

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_task_img);
            title = (TextView) itemView.findViewById(R.id.item_task_tv_title);
            author = (TextView) itemView.findViewById(R.id.item_task_tv_author);
            requestDate = (TextView) itemView.findViewById(R.id.item_task_tv_date);
            requestedBy = (TextView) itemView.findViewById(R.id.item_task_tv_requested_by_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int pos = getAdapterPosition();
//            Intent intent = new Intent(ctx, BookDetailsActivity.class);
//            intent.putExtra("bookId", bookList.get(pos).getBookId());
//            ctx.startActivity(intent);
//            Log.d(Constants.APP_TAG, "an item clicked at pos: " + pos);
        }
    }
}

