package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.model.SessionManager;

/**
 * Created by Kay Thi Swe Tun
 **/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = SessionManager.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        inflateDrawerMenu(navigationView);
    }

    private void inflateDrawerMenu(NavigationView navigationView) {
        String role0 = session.getUserRole0();
        String role1 = "";
        if (session.getUserRole1().equals("")) {

            if (role0.equals("Department Head")) {
                navigationView.inflateMenu(R.menu.dephead_menu_drawer);
            }
        } else {
            role1 = session.getUserRole1();
            if (role0.equals("Department Head") && role1.equals("Store Manager")) {
                navigationView.inflateMenu(R.menu.storemanager_menu_drawer);
            } else if (role0.equals("Employee") && role1.equals("Store Clerk")) {
                navigationView.inflateMenu(R.menu.storeclerk_menu_drawer);
            } else if (role0.equals("Employee") && role1.equals("Store Supervisor")) {
                navigationView.inflateMenu(R.menu.storesupervisor_menu_drawer);
            } else if (role0.equals("Acting Department Head") && role1.equals("Store Supervisor")) {
                navigationView.inflateMenu(R.menu.storemanager_menu_drawer);
            } else if (role0.equals("Acting Department Head") && role1.equals("Store Clerk")) {
                navigationView.inflateMenu(R.menu.storemanager_menu_drawer);
            } else {
                navigationView.inflateMenu(R.menu.general_fallback_menu);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        if (id == R.id.logout) {
            session.logoutUser(MainActivity.this);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
//            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
//            new SendMailTask(MainActivity.this).execute("henrytlh@hotmail.com", "Title", "body");
        } else if (id == R.id.nav_delegateHead) {
            Intent i = new Intent(MainActivity.this, DelegateDepHeadActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_generateRetrieval) {
            Intent i = new Intent(MainActivity.this, RetrievalListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_viewRequest) {
            Intent i = new Intent(MainActivity.this, ViewRequestActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_approveRejectPO) {
            Intent i = new Intent(MainActivity.this, ApproveRejectPO.class);
            startActivity(i);
        } else if (id == R.id.nav_raiseAdjustment) {
            Intent i = new Intent(MainActivity.this, RaiseAdjustmentActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_returnwarehouse) {
            Intent i = new Intent(MainActivity.this, ReturntoWarehouseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_manageDepRep) {
            Intent i = new Intent(MainActivity.this, ManageDepRepActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_viewdisb) {
            Intent i = new Intent(MainActivity.this, MainDisbursementListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_ackdelivery) {
            Intent i = new Intent(MainActivity.this, AcknowledgeDelivery.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
