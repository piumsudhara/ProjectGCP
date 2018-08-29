package com.example.pium.chef;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.pium.chef.Fragment.CartFragment;
import com.example.pium.chef.Fragment.FoodDetailFragment;
import com.example.pium.chef.Fragment.HomeFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS = "prefFile";
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(PREFS, 0);
        final String customer = preferences.getString("username", null);
        Bundle bundle = getIntent().getExtras();
        String usernameTxt = bundle.getString("username");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.login_username);
        username.setText("WElCOME, " + usernameTxt);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frag,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_search)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id ==R.id.nav_home)
        {
            setTitle("Chef");
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag,fragment);
            fragmentTransaction.commit();
        }

        if (id == R.id.nav_menu)
        {
            setTitle("Menu");
        }
        else if (id == R.id.nav_cart)
        {
            setTitle("Cart");
            CartFragment fragment = new CartFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag,fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_orders)
        {
            setTitle("Orders");
        }
        else if (id == R.id.nav_log_out)
        {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
            alertDialogBuilder.setMessage("Are You Sure Want To Exit ?");
            alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int arg1)
                {
                        final ProgressDialog pd = new ProgressDialog(Home.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Signing Out...");
                        pd.setIndeterminate(false);
                        pd.show();
                        progressStatus = 0;

                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                while(progressStatus < 100)
                                {
                                    progressStatus += 1;
                                    try
                                    {
                                        Thread.sleep(50);
                                    }
                                    catch(InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    handler.post(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            pd.setProgress(progressStatus);
                                            if (progressStatus == 70)
                                            {
                                                pd.dismiss();
                                                startActivity(new Intent(Home.this, Login.class));
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
            });
            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
