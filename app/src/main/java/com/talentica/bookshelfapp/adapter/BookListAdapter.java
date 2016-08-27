package com.talentica.bookshelfapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talentica.bookshelfapp.BookDetailsActivity;
import com.talentica.bookshelfapp.Constants;
import com.talentica.bookshelfapp.R;
import com.talentica.bookshelfapp.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohanr on 8/27/16.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {

    Context ctx;
    List<Book> bookList;

    public BookListAdapter(Context ctx, List<Book> bookList){
        this.ctx = ctx;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getBookName());
        holder.author.setText(book.getAuthorName());
        holder.lender.setText("Lender " + book.getLenderName());
        Picasso.with(ctx).load(book.getBookImageUrl()).into(holder.img);
        holder.img.setAdjustViewBounds(true);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView title, author, lender;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_home_list_img);
            title = (TextView) itemView.findViewById(R.id.item_home_list_title);
            author = (TextView) itemView.findViewById(R.id.item_home_list_author);
            lender = (TextView) itemView.findViewById(R.id.item_home_list_lender);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(ctx, BookDetailsActivity.class);
            intent.putExtra("bookId", bookList.get(pos).getBookId());
            ctx.startActivity(intent);
            Log.d(Constants.APP_TAG, "an item clicked at pos: " + pos);
        }
    }
}
