package com.talentica.bookshelfapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton mToolbarHome;
    ImageButton mToolbarTodo;
    ImageButton mToolbarAdd;
    ImageButton mToolbarNotification;
    ImageButton mToolbarProfile;
    LinearLayout mMainContainer;
    Menu mainMenu;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
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

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(searchIntent.getAction())) {
            String qry = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(ctx, "hello there: " + qry, Toast.LENGTH_LONG).show();
        }

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
        getMenuInflater().inflate(R.menu.main, menu);
        mainMenu = menu;
        SearchView searchView = (SearchView) menu.findItem(R.id.book_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
                displayNotifications();
                break;
            case R.id.toolbar_profile:
                mToolbarProfile.setImageResource(R.drawable.ic_profile_select);
                displayMyAccounts();
                break;
        }
    }

    private void displayMyAccounts() {
        MyAccountsFragment myAccountFrg = new MyAccountsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, myAccountFrg).commit();
        displayToolbarWithTitle("My Account");
    }

    private void displayNotifications() {
        NotificationsFragment notificationFrg = new NotificationsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, notificationFrg).commit();
        displayToolbarWithTitle("Notifications");
    }

    private void displayTasks() {
        TasksFragment taskFrg = new TasksFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, taskFrg, Constants.TASK_FRAGMENT).commit();
        displayToolbarWithTitle("My Tasks");
    }

    private void displayToolbarWithTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainMenu.findItem(R.id.book_search).setVisible(false);
    }

    private void displayAddBook() {
        AddBookMainFragment addBookMainFrg = new AddBookMainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.act_main_container, addBookMainFrg, Constants.ADD_BOOK_MAIN_FRAGMENT).commit();
        displayToolbarWithTitle("Add Book");
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
        displayHomeToolbar();
    }

    private void displayHomeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_category);

        toolbar.setTitle("");
    }


}
