
package com.example.android.navigationdrawerexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTitles;

    ViewPager viewPager;
    private PagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mTitles = getResources().getStringArray(R.array.notification_array);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        viewPager = (ViewPager)findViewById(R.id.myviewpager);
        myPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_chromecast).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_chromecast:
            showDialog(getResources().getString(R.string.title_cast), getResources().getString(R.string.desc_cast));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch (position)
        {
            case 0:
                if(viewPager!=null) {
                    viewPager.setCurrentItem(0);
                }
                mDrawerList.setItemChecked(position, true);
                setTitle(R.string.app_name);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 3:
                showDialog(getResources().getString(R.string.title_cast), getResources().getString(R.string.desc_cast));
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 4:
                showDialog(getResources().getString(R.string.title_info), getResources().getString(R.string.desc_info));
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 5:
                showDialog(getResources().getString(R.string.title_about), getResources().getString(R.string.desc_about));
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            default:
                if(viewPager!=null) {
                    viewPager.setCurrentItem(0);
                }
                mDrawerList.setItemChecked(position, true);
                setTitle(mTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class CustomPagerAdapter extends FragmentPagerAdapter {
        public CustomPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: return new f1();
                case 1: return new f2();
                case 2: return new f3();
                default: return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    }


    private void showDialog(String title,String desc)
    {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle((Html.fromHtml("<h1>" + title + "</h1>")))
                .setMessage(desc)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

}