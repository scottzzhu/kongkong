package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    enum Category{
        LEASE, LUGGAGE, PARKING, PETS, RENTAL, OTHER;
    }
    public static Category category;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //get navigation bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Button logInBt = (Button) findViewById(R.id.button2);

        if (user!=null) {

            String userID = user.getUid();
            String userEmail = user.getEmail();

            TextView userTV = (TextView) findViewById(R.id.userTV);
            userTV.setText(user.toString());
            TextView userIDTV = (TextView) findViewById(R.id.userIDTV);
            userIDTV.setText(userID);
            TextView userEmailTV = (TextView) findViewById(R.id.userEmailTV);
            userEmailTV.setText(userEmail);
            //navigation bar info
            View header=navigationView.getHeaderView(0);
            TextView navUserEmail = (TextView) header.findViewById(R.id.userEmail);
            navUserEmail.setText(userEmail);
            TextView navUserID = (TextView) header.findViewById(R.id.userID);
            navUserID.setText(userID);
        }
        else{
            logInBt.setText("Login");
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        Intent myIntent = null;
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_luggage:
                category = Category.LUGGAGE;
                myIntent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_parking:
                category = Category.PARKING;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_pets:
                category = Category.PETS;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_lease:
                category = Category.LEASE;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_rental:
                category = Category.RENTAL;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_other:
                category = Category.OTHER;
                myIntent = new Intent(MainActivity.this, PostViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_share:
                fragment = new ShareFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();

    }

    public void Post(View view){
        Intent myIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(myIntent);
    }
    public void gotoProfile(View view){
        Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
}
