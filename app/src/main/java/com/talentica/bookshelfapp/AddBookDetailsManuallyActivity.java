package com.talentica.bookshelfapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddBookDetailsManuallyActivity extends AppCompatActivity {

    EditText mEtTitle, mEtAuthor, mEtPrice, mEtPublisher, mEtIsbn10,
            mEtIsbn13, mEtTags, mEtComments;
    Spinner mSpnBinding, mSpnGenre;
    RadioGroup mRgCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_details_manually);
        setAllFields();
    }

    private void setAllFields() {
        mEtTitle = (EditText) findViewById(R.id.act_add_book_et_title);
        mEtAuthor = (EditText) findViewById(R.id.act_add_book_et_author);
        mEtPrice = (EditText) findViewById(R.id.act_add_book_et_price);
        mEtPublisher = (EditText) findViewById(R.id.act_add_book_et_publisher);
        mEtIsbn10 = (EditText) findViewById(R.id.act_add_book_et_isbn10);
        mEtIsbn13 = (EditText) findViewById(R.id.act_add_book_et_isbn13);
        mEtTags = (EditText) findViewById(R.id.frg_add_book_common_et_tags);
        mEtComments = (EditText) findViewById(R.id.frg_add_book_common_et_comments);
    }
}
