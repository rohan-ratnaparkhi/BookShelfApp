package com.talentica.bookshelfapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.talentica.bookshelfapp.R;
import com.talentica.bookshelfapp.model.Book;

import java.util.List;

/**
 * Created by rohanr on 8/27/16.
 */
public class BookGridAdapter extends BaseAdapter {

    Context ctx;
    List<Book> bookList;

    public BookGridAdapter(Context ctx, List<Book> bookList) {
        this.ctx = ctx;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return this.bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_book_list, viewGroup, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Picasso.with(ctx).load(this.bookList.get(i).getBookImageUrl()).into(holder.mBookImg);
        holder.mBookImg.setAdjustViewBounds(true);
        holder.mTitle.setText(this.bookList.get(i).getBookName());
        holder.mAuthor.setText(this.bookList.get(i).getAuthorName());
        holder.mLender.setText(this.bookList.get(i).getLenderName());

        return row;
    }

    class ViewHolder {
        ImageView mBookImg;
        TextView mTitle, mAuthor, mLender;

        ViewHolder(View v) {
            mBookImg = (ImageView) v.findViewById(R.id.item_home_list_img);
            mTitle = (TextView) v.findViewById(R.id.item_home_list_title);
            mAuthor = (TextView) v.findViewById(R.id.item_home_list_author);
            mLender = (TextView) v.findViewById(R.id.item_home_list_lender);
        }
    }
}
