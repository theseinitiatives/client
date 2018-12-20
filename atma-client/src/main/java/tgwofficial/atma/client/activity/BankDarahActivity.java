package tgwofficial.atma.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.nativeform.FormAddBankDarah;
import tgwofficial.atma.client.adapter.BankDarahCursorAdapter;
import tgwofficial.atma.client.adapter.IdentitasibuCursorAdapter;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.model.BankDarahmodel;
import tgwofficial.atma.client.model.IdentitasModel;

public class BankDarahActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Activity activity;
    private DbManager dbManager;
    ListView lv;
    SearchView sv;
    BankDarahCursorAdapter adapter;
    ArrayList<BankDarahmodel> bankDarahmodels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankdarah_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDropdownSort();

        lv = (ListView) findViewById(R.id.list_view);
        sv= (SearchView) findViewById(R.id.sv);
        //lv.setAdapter(adapter);
        adapter=new BankDarahCursorAdapter(this,bankDarahmodels);

        getBankDarah("","name_pendonor ASC");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getBankDarah(newText, "name_pendonor ASC");
                return false;
            }
        });
        initDropdownSort();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ids = Long.toString(id+1);
                String uid = bankDarahmodels.get((int)id).getId();
                Log.i("__id", ""+uid);
                Intent intent = new Intent(BankDarahActivity.this,BankDarahDetailActivity.class);
                intent.putExtra("id",uid);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BankDarahActivity.this, FormAddBankDarah.class);
                startActivity(myIntent);
                /*Snackbar.make(view, "Untuk Tambah Patient Baru", Snackbar.LENGTH_LONG)
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


    private void getBankDarah(String searchTerm, String orderBy)
    {
        dbManager = new DbManager(this);
        dbManager.open();
        bankDarahmodels.clear();
        BankDarahmodel p = null;
        if(searchTerm.equalsIgnoreCase(""))
            dbManager.setOrderBy(orderBy);
        Cursor c = dbManager.fetchBankDarah(searchTerm, orderBy);
        while (c.moveToNext()) {
                int id = c.getInt(0);
                String uid = c.getString(c.getColumnIndexOrThrow("_id"));
                String name = c.getString(c.getColumnIndexOrThrow("name_pendonor"));
                String gol_darah = c.getString(c.getColumnIndexOrThrow("gol_darah"));
                String hp = c.getString(c.getColumnIndexOrThrow("telp"));
               // String pendonors = c.getString(c.getColumnIndexOrThrow("name_pendonor"));


                p = new BankDarahmodel();
                p.setId(uid);
                p.setNama(name);
                p.setGolds(gol_darah);
                p.setNomor(hp);

              //  p.setPendonor(pendonors);


                bankDarahmodels.add(p);
            }

            dbManager.close();

            lv.setAdapter(adapter);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            Intent myIntent = new Intent(BankDarahActivity.this, IdentitasIbuActivity.class);
            startActivity(myIntent);
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
        }
*/
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
        if(id == R.id.nav_logout){
            super.onBackPressed();
        }
        if(id == R.id.info){
            navi.gotoKIA();
        }
        if(id == R.id.kader_add){
            navi.addKader();
            //super.onBackPressed();
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
                getBankDarah("",item[1][position]+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    private ArrayAdapter<String> spinnerAdapter(){
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, item[0]);
    }
    private final String [][] item = {
            {"Sort ","Nama A-Z","Nama Z-A"},
            {"name_pendonor ASC","name_pendonor ASC","name_pendonor DESC"}
    };
}
