package com.talentica.bookshelfapp;

/**
 * Created by rohanr on 8/10/16.
 */
public class Constants {

//    API urls
    public static final String BASE_URL = "http://ec2-52-38-174-68.us-west-2.compute.amazonaws.com:3000/api";
    public static final String USER_LOGIN_API = "/authenticate/login";
    public static final String USER_SIGN_UP_API = "/user";
    public static final String USER_FORGOT_PWD_API= "/authenticate/forgot-password/";
    public static final String ADD_BOOK_API = "/book";
    public static final String ALL_BOOKS_API = "/books";
    public static final String RECENTLY_ADDED_API = "/book/reports/most-read";
    public static final String MOST_READ_API = "/book/reports/most-read";
    public static final String AUTH_PREPEND = "Bearer ";
    public static final String GET_BOOK_BY_ID = "/book/{id}";
    public static final String BORROW_BOOK_API = "/book/request/{id}";


//    keys for json and internal usage
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_ID = "_id";
    public static final String KEY_ROLES = "roles";
    public static final String USER_TOKEN = "uToken";
    public static final String APP_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NzE3NjkwYTI2ZTRlNjQ2MmY5NWExZGUiLCJpYXQiOjE0NzIxOTYzOTksImV4cCI6MTQ3MjI4Mjc5OSwiaXNzIjoiZWMyLTUyLTM4LTE3NC02OC51cy13ZXN0LTIuY29tcHV0ZS5hbWF6b25hd3MuY29tIn0.ctEFqJvqRhBhiu4qwltUMQg3F947TlZfp_NjeAAyv8I";
    public static final String KEY_PAGE = "page";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_AUTHORIZATION = "Authorization";

    //    messages
    public static final String INVALID_CREDENTIALS = "Invalid username / password";
    public static final String ERROR_OCCURRED = "Some error occurred. Please try again.";
    public static final String PASSWORD_RESET_MAIL_SENT = "Email sent successfully.";
    public static final String USER_NOT_FOUND = "User not found.";

    public static final String APP_TAG = "Rohan";
}
