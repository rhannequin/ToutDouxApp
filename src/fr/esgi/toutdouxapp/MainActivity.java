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
import android.support.v7.app.ActionBar;
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

    String mTitle = "";
    String[] mPages ;

    int mPosition = -1;
    int[] mIcons = new int[]{
        R.drawable.ic_action_view_as_list,
        R.drawable.ic_todo,
        R.drawable.ic_done,
        R.drawable.ic_action_add_category
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setBackgroundDrawable(
            new ColorDrawable(Color.parseColor("#333333"))
        );

        setContentView(R.layout.activity_main);

        mPages = getResources().getStringArray(R.array.pages);
        mTitle = (String) getTitle();
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);

        mList = new ArrayList<HashMap<String,String>>();
        for(int i=0; i<4; i++) {
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(PAGE, mPages[i]);
            hm.put(ICON, Integer.toString(mIcons[i]) );
            mList.add(hm);
        }

        String[] from = { ICON, PAGE};
        int[] to = { R.id.icon, R.id.page};

        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            R.drawable.ic_drawer ,
            R.string.drawer_open,
            R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                highlightSelectedPage();
                supportInvalidateOptionsMenu();
            }

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

                switch(position){
                    case 0:
                        showMainFragment(0, "all");
                        break;
                    case 1:
                        showMainFragment(1, "todo");
                        break;
                    case 2:
                        showMainFragment(2, "done");
                        break;
                    case 3:
                        showCategoriesListFragment();
                        break;
                }

                mDrawerLayout.closeDrawer(mDrawer);
            }

        });

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);

        // Load showMainFragment && set title
        showMainFragment(0, "all");
        getSupportActionBar().setTitle(mPages[0]);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showMainFragment(int i, String filter){
        mTitle = mPages[i];
        MainFragment cFragment = new MainFragment();

        Bundle data = new Bundle();
        data.putInt("position", i);
        data.putString("filter", filter);
        cFragment.setArguments(data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, cFragment);
        ft.commit();
    }

    public void showCategoriesListFragment(){
        mTitle = mPages[1];
        CategoriesListFragment cFragment = new CategoriesListFragment();

        Bundle data = new Bundle();
        data.putInt("position", 1);
        cFragment.setArguments(data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, cFragment);
        ft.commit();
    }

    public void highlightSelectedPage(){
        int selectedItem = mDrawerList.getCheckedItemPosition();
        mPosition = selectedItem;
        if(mPosition != -1) {
            getSupportActionBar().setTitle(mPages[mPosition]);
        }
    }
}
