package com.talentica.bookshelfapp.model;

/**
 * Created by rohanr on 8/23/16.
 */
public class Book {
    private String bookName;
    private String authorName;
    private String bookId;
    private String lenderName;
    private String bookImageUrl;

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }
}
