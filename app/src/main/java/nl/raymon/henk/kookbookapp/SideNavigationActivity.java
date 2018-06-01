package nl.raymon.henk.kookbookapp;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import nl.raymon.henk.kookbookapp.dummy.DummyContent;

public class SideNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyRecipesFragment.OnListFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener, OnlineRecipesFragment.OnListFragmentInteractionListener, NewRecipeFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener {

    private FirebaseAuth firebaseInstance;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_navigation);
        firebaseInstance = FirebaseAuth.getInstance();
        user = firebaseInstance.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, HomeFragment.newInstance("", "")).commit();
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //DO NOTHING
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_navigation, menu);

        TextView title = findViewById(R.id.nav_title);
        title.setText(user.getDisplayName());

        TextView subtitle = findViewById(R.id.nav_sub_title);
        subtitle.setText(user.getEmail());

        if (user.getPhotoUrl() != null) {
            Picasso.with(this).load(user.getPhotoUrl()).into((ImageView) findViewById(R.id.nav_user_icon));
        }


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

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, HomeFragment.newInstance("", "")).commit();
        } else if (id == R.id.my_recipes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MyRecipesFragment.newInstance(1)).commit();
        } else if (id == R.id.online_recipes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, OnlineRecipesFragment.newInstance(1)).commit();
        } else if (id == R.id.new_recipe) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, NewRecipeFragment.newInstance("", "")).commit();
        } else if (id == R.id.statistics) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, StatisticsFragment.newInstance("", "")).commit();

        } else if (id == R.id.logout) {
            firebaseInstance.signOut();
            startActivity(new Intent(this, StartActivity.class));
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, HomeFragment.newInstance("", "")).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
