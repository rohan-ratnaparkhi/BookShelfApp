package com.talentica.bookshelfapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton mToolbarHome;
    ImageButton mToolbarTodo;
    ImageButton mToolbarAdd;
    ImageButton mToolbarNotification;
    ImageButton mToolbarProfile;
    LinearLayout mMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mMainContainer = (LinearLayout) findViewById(R.id.act_main_container);
        mToolbarHome = (ImageButton) findViewById(R.id.toolbar_home);
        mToolbarTodo = (ImageButton) findViewById(R.id.toolbar_todo);
        mToolbarAdd = (ImageButton) findViewById(R.id.toolbar_add);
        mToolbarNotification = (ImageButton) findViewById(R.id.toolbar_notification);
        mToolbarProfile = (ImageButton) findViewById(R.id.toolbar_profile);

        mToolbarHome.setOnClickListener(this);
        mToolbarTodo.setOnClickListener(this);
        mToolbarAdd.setOnClickListener(this);
        mToolbarNotification.setOnClickListener(this);
        mToolbarProfile.setOnClickListener(this);

        displayHome();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d(Constants.APP_TAG, "item: " + id);
        displayBooksForCategory(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayBooksForCategory(MenuItem item) {
        BookGridFragment bookGrid = new BookGridFragment();
        bookGrid.setmTag(item.getTitle().toString());
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, bookGrid, Constants.BOOK_GRID).commit();
    }

    @Override
    public void onClick(View view) {
        resetToolbarIconsToDefault();
        switch (view.getId()) {
            case R.id.toolbar_home:
                displayHome();
                break;
            case R.id.toolbar_todo:
                mToolbarTodo.setImageResource(R.drawable.ic_todo_select);
                displayTasks();
                break;
            case R.id.toolbar_add:
                mToolbarAdd.setImageResource(R.drawable.ic_add_select);
                displayAddBook();
                break;
            case R.id.toolbar_notification:
                mToolbarNotification.setImageResource(R.drawable.ic_notification_select);
                break;
            case R.id.toolbar_profile:
                mToolbarProfile.setImageResource(R.drawable.ic_profile_select);
                break;
        }
    }

    private void displayTasks() {
        TasksFragment taskFrg = new TasksFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, taskFrg, Constants.TASK_FRAGMENT).commit();
    }

    private void displayAddBook() {
        AddBookMainFragment addBookMainFrg = new AddBookMainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, addBookMainFrg, Constants.ADD_BOOK_MAIN_FRAGMENT).commit();
    }

    void resetToolbarIconsToDefault() {
        mToolbarHome.setImageResource(R.drawable.ic_home);
        mToolbarTodo.setImageResource(R.drawable.ic_todo);
        mToolbarAdd.setImageResource(R.drawable.ic_add);
        mToolbarNotification.setImageResource(R.drawable.ic_notification);
        mToolbarProfile.setImageResource(R.drawable.ic_profile);
    }

    private void displayHome() {
        mToolbarHome.setImageResource(R.drawable.ic_home_select);
        BookListsFragment bookLists = new BookListsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, bookLists, Constants.BOOK_LISTS).commit();
    }
}
