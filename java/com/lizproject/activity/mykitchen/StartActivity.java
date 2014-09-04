package com.lizproject.activity.mykitchen;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lizproject.activity.mykitchen.model.AndroidDatabaseManager;
import com.lizproject.activity.mykitchen.ui.core.fragments.AllCoucineFrac;
import com.lizproject.activity.mykitchen.ui.core.fragments.OptionsFrac;
import com.lizproject.activity.mykitchen.ui.core.fragments.PrincipaInitlFrac;


/* La actividad principal que Hereda de una Acividad Base
      Maneja el Drawer lateral con su respectivo ListView
      y carga el Fragment principal que a su vez carga dos Fragments dentro de el
 */
public class StartActivity extends BaseActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listdrawer;
    private ArrayAdapter<String> StartAdapter;
    private DrawerLayout mDrawerLayout;
    private Fragment fragment;
    private CharSequence mDrawerTitle;

    private CharSequence mTitle;
    private String stringitemlist[];
    private  SearchView searchView;
    // Start Activiy charge the DrawerLayout and manage the ActionBar
    public static final String TAG = "ImmersiveModeFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        listdrawer = (ListView) findViewById(R.id.listview_start);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.selected_drawer);

        stringitemlist = getResources().getStringArray(R.array.item_listview);

        mTitle = mDrawerTitle = getTitle();
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        int height = decorView.getHeight();
                        Log.i(TAG, "Current height: " + height);
                    }
                });

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        StartAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringitemlist);

        listdrawer.setAdapter(StartAdapter);


        listdrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            // En caso de selecion de uno de los items del ListView hace un replace.
                selectItem(pos);

            }
        });


        // codigo por terminar.
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {
            // importante si la actividad no tiene ningun estado anterio el que abre por defecto es el fragment en la poscion 1
            selectItem(1);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_websearch).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

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


        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet


                // Assumes current activity is the searchable activity
                return true;
            case R.id.agregar_tab_bar:

                Intent in = new Intent(this, RecepyAdd.class);
                startActivity(in);
                return true;
            case R.id.sample_action:
                toggleHideyBar();
                return true;
            case R.id.action_settings:

                Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toggleHideyBar() {

        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    // the selection from ListView
    private void selectItem(int pos) {
        // update the main content by replacing fragments

        if (pos == 0) {

        } else if (pos == 1) {
            fragment = new PrincipaInitlFrac();

        } else if (pos == 2) {
            fragment = new AllCoucineFrac();
        } else if (pos == 3) {
            fragment = new OptionsFrac();
        }

        Bundle args = new Bundle();


        if (fragment != null) {
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout_start, fragment).commit();
        }
        // update selected item and title, then close the drawer

        listdrawer.setItemChecked(pos, true);
        setTitle(stringitemlist[pos]);
        mDrawerLayout.closeDrawer(listdrawer);


    }
}
