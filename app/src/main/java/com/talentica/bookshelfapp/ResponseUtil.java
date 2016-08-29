package com.talentica.bookshelfapp;

import android.util.Log;

import com.talentica.bookshelfapp.model.Book;
import com.talentica.bookshelfapp.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohanr on 8/27/16.
 */
public class ResponseUtil {

    public static List<Book> createBooksListFromResponse(JSONObject response) {
        List<Book> list = new ArrayList<Book>();
        try {

            JSONObject data = response.getJSONObject("data");
            JSONArray books = data.getJSONArray("docs");
            for (int i = 0; i < books.length(); i++) {
                JSONObject bk = books.getJSONObject(i);
                JSONObject publisher = bk.getJSONObject("publisher");
                JSONArray authors = bk.getJSONArray("authors");
                JSONObject author = authors.getJSONObject(0);
                Book book = new Book();
                book.setBookName(bk.getString("name"));
                book.setLenderName(publisher.getString("name"));
                book.setAuthorName(author.getString("name"));
                book.setBookId(bk.getString("_id"));
//        TODO - load respective img here
                book.setBookImageUrl(Constants.TEMP_BOOK_IMG);
                list.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<Task> createTaskListFromResponse(){
        List<Task> taskList = new ArrayList<Task>();
        for(int i = 0; i < 10; i ++){
            Task task = new Task();
            task.setBookAuthor("Author " + i);
            task.setBookId("id"+i);
            task.setBookImgUrl(Constants.TEMP_BOOK_IMG);
            task.setBookTitle("Title " + i);
            task.setBookRequestedBy("Rohan " + i);
            task.setBookRequestedDate("25 Aug");
            taskList.add(task);
        }
        return taskList;
    }
}
