package tgwofficial.atma.client.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.vijay.jsonwizard.activities.JsonFormActivity;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.adapter.IdentitasibuCursorAdapter;
import tgwofficial.atma.client.db.DbManager;

public class IdentitasIbuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DbManager dbManager;
    private Context context;
    private static final int    REQUEST_CODE_GET_JSON = 1;
    private static final String DATA_JSON_PATH        = "identitasibu.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DbManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.list_view);
// Setup cursor adapter using cursor from last step
        IdentitasibuCursorAdapter todoAdapter = new IdentitasibuCursorAdapter(this, cursor);
// Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);


        todoAdapter.changeCursor(cursor);

        dbManager.close();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JsonFormActivity.class);
                String json = "Your complete JSON";
                intent.putExtra("json", json);
                startActivityForResult(intent, REQUEST_CODE_GET_JSON);

                //Snackbar.make(view, "Untuk Tambah Patient Baru", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

       /* ImageView img = (ImageView) findViewById(R.id.ibu);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(IdentitasIbuActivity.this, IdentitasIbuDetailActivity.class);
                startActivity(myIntent);
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        NavigationmenuController  navi= new NavigationmenuController(this);
        int id = item.getItemId();
       // MenuItem register = R.id.nav_identitas_ibu;
        if (id == R.id.nav_identitas_ibu) {
            navi.startIdentitasIbu();
        }
        if (id == R.id.nav_transportasi) {
            navi.startTransportasi();
        }

        if (id == R.id.nav_bank_darah) {
            navi.startBankDarah();
            // Handle the camera action
        }
        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
