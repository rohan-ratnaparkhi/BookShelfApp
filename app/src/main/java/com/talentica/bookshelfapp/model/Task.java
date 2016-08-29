package com.talentica.bookshelfapp.model;

/**
 * Created by rohanr on 8/29/16.
 */
public class Task {
    private String taskId;
    private String bookId;
    private String bookImgUrl;
    private String bookTitle;
    private String bookAuthor;
    private String bookRequestedBy;
    private String bookRequestedDate;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookImgUrl() {
        return bookImgUrl;
    }

    public void setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookRequestedBy() {
        return bookRequestedBy;
    }

    public void setBookRequestedBy(String bookRequestedBy) {
        this.bookRequestedBy = bookRequestedBy;
    }

    public String getBookRequestedDate() {
        return bookRequestedDate;
    }

    public void setBookRequestedDate(String bookRequestedDate) {
        this.bookRequestedDate = bookRequestedDate;
    }
}
