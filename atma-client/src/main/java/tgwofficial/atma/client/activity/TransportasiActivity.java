package tgwofficial.atma.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.nativeform.FormAddIbuActivity;
import tgwofficial.atma.client.activity.nativeform.FormAddTransportasi;
import tgwofficial.atma.client.adapter.BankDarahCursorAdapter;
import tgwofficial.atma.client.adapter.IdentitasibuCursorAdapter;
import tgwofficial.atma.client.adapter.TransportasiCursorAdapter;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.model.BankDarahmodel;
import tgwofficial.atma.client.model.IdentitasModel;
import tgwofficial.atma.client.model.TransportasiModel;

public class TransportasiActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Activity activity;
    private DbManager dbManager;
    ListView lv;
    SearchView sv;
    TransportasiCursorAdapter adapter;
    ArrayList<TransportasiModel> transportasiModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportasi_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.list_view);
        sv= (SearchView) findViewById(R.id.sv);
        //lv.setAdapter(adapter);
        adapter=new TransportasiCursorAdapter(this,transportasiModels);

        getkendaraan("","name ASC");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getkendaraan(newText, "name ASC");
                return false;
            }
        });
        initDropdownSort();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TransportasiActivity.this, FormAddTransportasi.class);
                startActivity(myIntent);
               /* Snackbar.make(view, "Untuk Tambah Patient Baru", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getkendaraan(String searchTerm, String orderBy)
    {
        transportasiModels.clear();
        if(!searchTerm.equalsIgnoreCase("")) {
            dbManager = new DbManager(this);
            dbManager.open();
            TransportasiModel p = null;
            Cursor c = dbManager.fetchTrans(searchTerm,orderBy);
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(c.getColumnIndexOrThrow("name"));
                String jenis = c.getString(c.getColumnIndexOrThrow("jenis_kendaraan"));
                String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));


                p = new TransportasiModel();
                p.setNama(name);
                p.setKendaraan(jenis);
                p.setDusuns(dusun);

                transportasiModels.add(p);
            }

            dbManager.close();

            lv.setAdapter(adapter);
        }
        else{
            dbManager = new DbManager(this);
            dbManager.open();
            TransportasiModel p = null;
            Cursor c = dbManager.fetchTrans("", orderBy);
            while (c.moveToNext()) {
                int id = c.getInt(0);

                String name = c.getString(c.getColumnIndexOrThrow("name"));
                String jenis = c.getString(c.getColumnIndexOrThrow("jenis_kendaraan"));
                String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));



                p = new TransportasiModel();
                p.setNama(name);
                p.setKendaraan(jenis);
                p.setDusuns(dusun);

                transportasiModels.add(p);
            }

            dbManager.close();

            lv.setAdapter(adapter);

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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        NavigationmenuController navi= new NavigationmenuController(this);
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
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initDropdownSort(){
        Spinner dropdownSort = (Spinner) findViewById(R.id.dropdownSort);
        dropdownSort.setAdapter(spinnerAdapter());
        final Context context= this.getApplicationContext();
        dropdownSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(context,position+"Selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    private ArrayAdapter<String> spinnerAdapter(){
        String [] item = {
                "Nama ASC",
                "Nama Desc"
        };
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, item);
    }
}
