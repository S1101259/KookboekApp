package nl.raymon.henk.kookbookapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import nl.raymon.henk.kookbookapp.models.Recipe;

public class SideNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecipeFragment.OnFragmentInteractionListener, MyRecipesFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener, NewRecipeFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener, OnlineRecipeListFragment.OnFragmentInteractionListener {

    private FirebaseAuth firebaseInstance;
    private FirebaseUser user;
    private boolean backPressedOnce = false;
    private NavigationView navigationView;



    public static final int HIDE = 0;
    public static final int DOWNLOAD = 1;
    public static final int DELETE = 2;

    private int flag = HIDE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_navigation);
        firebaseInstance = FirebaseAuth.getInstance();
        user = firebaseInstance.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, HomeFragment.newInstance()).commit();
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public void onBackPressed() {
        setFlag(SideNavigationActivity.HIDE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                if (fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1) != null && fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1) == HomeFragment.newInstance()) {
                    fragmentManager.popBackStack();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, HomeFragment.newInstance()).commit();
                }
                super.onBackPressed();
            } else if (fragmentManager.getBackStackEntryCount() == 0) {
                if (backPressedOnce) {
                    finishAffinity();
                } else {
                    backPressedOnce = true;
                    Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backPressedOnce = false;
                        }
                    }, 2000);
                }

            }

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

        changeIcon(menu.findItem(R.id.actionbar_icon));
        return true;
    }

    public void setFlag(int option){
        this.flag = option;
        invalidateOptionsMenu();
    }

    private void changeIcon(MenuItem item){
        switch (flag) {
            case HIDE:
                item.setVisible(false);
                break;

            case DOWNLOAD:
                item.setIcon(R.drawable.ic_baseline_cloud_download_24px);
                item.setVisible(true);
                break;


            case DELETE:
                item.setIcon(R.drawable.ic_baseline_delete_24px);
                item.setVisible(true);
                break;


            default:
                item.setVisible(false);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            replaceFragment(HomeFragment.newInstance());
        } else if (id == R.id.my_recipes) {
            replaceFragment(MyRecipesFragment.newInstance());
        } else if (id == R.id.online_recipes) {
            replaceFragment(OnlineRecipeListFragment.newInstance());
        } else if (id == R.id.new_recipe) {
            replaceFragment(NewRecipeFragment.newInstance());
        } else if (id == R.id.statistics) {
            replaceFragment(StatisticsFragment.newInstance("", ""));

        } else if (id == R.id.logout) {
            firebaseInstance.signOut();
            startActivity(new Intent(this, StartActivity.class));
            setFlag(SideNavigationActivity.HIDE);
        } else {
            replaceFragment(HomeFragment.newInstance());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void goToMyRecipes(View v) {
        replaceFragment(MyRecipesFragment.newInstance());
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    public void goToOnlineRecipes(View v) {
        replaceFragment(OnlineRecipeListFragment.newInstance());
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    public void goToNewRecipe() {
        replaceFragment(NewRecipeFragment.newInstance());
        navigationView.getMenu().getItem(3).setChecked(true);
    }

    public void goToRecipe(Recipe recipe) {
        replaceFragment(RecipeFragment.newInstance(recipe));
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        setFlag(SideNavigationActivity.HIDE);
    }

    public void setSelectedMenuItem(int fragmentId) {
        switch (fragmentId) {
            case R.id.home:
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case R.id.my_recipes:
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case R.id.online_recipes:
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case R.id.new_recipe:
                navigationView.getMenu().getItem(3).setChecked(true);
                break;
            case R.id.statistics:
                navigationView.getMenu().getItem(4).setChecked(true);
                break;
            default:
                navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}