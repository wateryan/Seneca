package com.wateryan.acropolis.seneca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wateryan.acropolis.seneca.R;
import com.wateryan.acropolis.seneca.core.DbController;
import com.wateryan.acropolis.seneca.core.SessionManager;


public class MainActivity extends AppCompatActivity implements FragmentNavigationDrawer.FragmentDrawerListener {

    private SessionManager sessionManager;
    private DbController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentNavigationDrawer fragmentNavigationDrawer = (FragmentNavigationDrawer) getSupportFragmentManager().findFragmentById(
                R.id.fragment_navigation_drawer);
        fragmentNavigationDrawer.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        fragmentNavigationDrawer.setDrawerListener(this);

        displayFragment(0);

        this.dbController = DbController.getInstance(this);
        this.sessionManager = SessionManager.getInstance();
        this.sessionManager.initializeSessions(this.dbController.getUsersAccounts());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.dbController.closeDb();
        this.sessionManager.closeAllSessions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(getApplicationContext(), "Settings is selected.",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search is selected.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayFragment(position);
    }

    private void displayFragment(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new FragmentHome();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new ListFragmentMessages();
                title = getString(R.string.title_messages);
                break;
            case 2:
                fragment = new ListFragmentTopics();
                title = getString(R.string.title_topics);
                break;
            case 3:
                fragment = new ListFragmentAccounts();
                title = getString(R.string.title_accounts);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle(title);
            }
        }
    }
}
