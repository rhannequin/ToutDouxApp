package fr.esgi.toutdouxapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ActionBarActivity {
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mDrawer ;
    private List<HashMap<String,String>> mList ;
    private SimpleAdapter mAdapter;
    final private String PAGE = "page";
    final private String ICON = "icon";
    final private String COUNT = "count";
    
    int mPosition = -1;
    String mTitle = "";

    // Array of strings storing page names
    String[] mPages ;
 
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] mIcons = new int[]{
        R.drawable.ic_action_view_as_list,
        R.drawable.ic_todo,
        R.drawable.ic_done,
        R.drawable.ic_action_add_category
    };

    // Array of strings to initial counts
    String[] mCount = new String[]{"", "", "", "", "","", "", "", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set header with custom options
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));

        setContentView(R.layout.activity_main);

        // Getting an array of page names
        mPages = getResources().getStringArray(R.array.pages);

        // Title of the activity
        mTitle = (String)getTitle();

        // Getting a reference to the drawer listview
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // Getting a reference to the sidebar drawer ( Title + ListView )
        mDrawer = ( LinearLayout) findViewById(R.id.drawer);

        // Each row in the list stores page name, count and icon
        mList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<4;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(PAGE, mPages[i]);
            hm.put(COUNT, mCount[i]);
            hm.put(ICON, Integer.toString(mIcons[i]) );
            mList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { ICON,PAGE,COUNT };

        // Ids of views in listview_layout
        int[] to = { R.id.icon , R.id.page , R.id.count};

        // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);

        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                highlightSelectedPage();
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
                supportInvalidateOptionsMenu();
            }
        };

        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                // Increment hit count of the drawer list item
                //incrementHitCount(position);

                // Show fragment for pages 
                showFragment(position);

                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawer);
            }

        });

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void incrementHitCount(int position){
        HashMap<String, String> item = mList.get(position);
        String count = item.get(COUNT);
        item.remove(COUNT);
        if(count.equals("")){
            count = " 1 ";
        } else {
            int cnt = Integer.parseInt(count.trim());
            cnt ++;
            count = " " + cnt + " ";
        }
        item.put(COUNT, count);
        mAdapter.notifyDataSetChanged();
    }

    public void showFragment(int position){

        //Currently selected page
        mTitle = mPages[position];

        // Creating a fragment object
        PageFragment cFragment = new PageFragment();

        // Creating a Bundle object
        Bundle data = new Bundle();

        // Setting the index of the currently selected item of mDrawerList
        data.putInt("position", position);

        // Setting the position to the fragment
        cFragment.setArguments(data);

        // Getting reference to the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Creating a fragment transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // Adding a fragment to the fragment transaction
        ft.replace(R.id.content_frame, cFragment);

        // Committing the transaction
        ft.commit();
    }
 
    // Highlight the selected page : 0 to 4
    public void highlightSelectedPage(){
        int selectedItem = mDrawerList.getCheckedItemPosition();

        mPosition = selectedItem;

        if(mPosition!=-1) {
            getSupportActionBar().setTitle(mPages[mPosition]);
        }
    }
}