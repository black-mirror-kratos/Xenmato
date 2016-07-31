package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/9/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Tabs extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener{

    public static ViewPager tabsviewPager;
    private ActionBar mActionBar;
    private Tabsadapter mTabsAdapter;

    private TextView itemCalories;

    private TextView calories;
    private ImageView calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_view);
        itemCalories = (TextView) findViewById(R.id.calories_count);
        calories = (TextView) findViewById(R.id.calories_count_calc);
        //itemCalories.setText("567");
        calculate = (ImageView) findViewById(R.id.calories_icon_calc);

        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);

        mTabsAdapter = new Tabsadapter(getSupportFragmentManager());

        tabsviewPager.setAdapter(mTabsAdapter);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab todayratingstab = getSupportActionBar().newTab().setText("Menu").setTabListener(this);
        Tab caloriescalctab = getSupportActionBar().newTab().setText("Calories Calc").setTabListener(this);
        Tab chartstab = getSupportActionBar().newTab().setText("Charts").setTabListener(this);


        getSupportActionBar().addTab(todayratingstab);
        getSupportActionBar().addTab(caloriescalctab);
        getSupportActionBar().addTab(chartstab);


        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    /*public void caloriesTouchListener(View view){
        totalCalories += Integer.parseInt(itemCalories.getText().toString());
        Toast.makeText(getApplicationContext(),""+totalCalories,Toast.LENGTH_SHORT);
    }*/

    public void caloriesOnclickCalculator(View view){
        //Log.d("sdf","hue");
        CaloriesCalcFragment.MyCaloriesClickMethod(view);
        //calories.setBackgroundColor(Color.BLUE);
    }
    public void resetOnclickCalculator(View view){
        //Log.d("sdf","hue");
        CaloriesCalcFragment.MyResetClickMethod(view);
        tabsviewPager.setCurrentItem(2);
        tabsviewPager.setCurrentItem(0);
        //calories.setBackgroundColor(Color.BLUE);
    }

    /*public void resetHighlight(){
        *//*for(int i=0;i<contactList.size();i++){
            contactList.get(i).clicked = false;
        }*//*

    }*/

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
            String[] TO = {"food_committee@citrix.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                //getActivity().finish();
            }
            catch (android.content.ActivityNotFoundException ex) {

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
