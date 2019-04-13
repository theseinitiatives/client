package theseinitiatives.atma.client.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.LoginActivity;
import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.Utils.FilterActivity;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.nativeform.FormAddBankDarah;
import theseinitiatives.atma.client.adapter.BankDarahCursorAdapter;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;
import theseinitiatives.atma.client.model.BankDarahmodel;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class BankDarahActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String EventName = "BankDarah";
    private Activity activity;
    private DbManager dbManager;
    private boolean firstRun = true;
    ListView lv;
    SearchView sv;
    long upId;
    String locas;
    boolean forbidden = false;
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

        dbManager = new DbManager(this);
        dbManager.open();
        if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            forbidden = true;
        }
       // String updateID = dbManager.getlatestUpdateId();
       // upId = Long.parseLong(updateID);
       // locas = dbManager.getlocName();

        dbManager.close();

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

    @Override
    protected void onResume(){
        super.onResume();
        if(firstRun) {
            firstRun = false;
            return;
        }
//        Log.d("On Resume params",AllConstants.params);
//        Toast.makeText(getApplicationContext(),AllConstants.params,Toast.LENGTH_LONG).show();
        refreshList();

    }
    private void getBankDarah(String searchTerm, String orderBy){
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
            String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));
            String gubug = c.getString(c.getColumnIndexOrThrow("gubug"));

            p = new BankDarahmodel();
            p.setId(uid);
            p.setNama(name);
            p.setGolds(gol_darah);
            p.setNomor(hp);
            p.setDusun(dusun+" / "+gubug);

            bankDarahmodels.add(p);
        }

        dbManager.close();
        lv.setAdapter(adapter);
    }

    private void refreshList(){
        if(AllConstants.params == null)
            return;
        bankDarahmodels.clear();
        dbManager.open();
        String[]cond = AllConstants.params.split(AllConstants.FLAG_SEPARATOR);
        if(cond.length<2){
            cond = new String[]{"","","no"};
        }
        cond[0] = cond[0].contains("~") ? "" : cond[0];
        cond[1] = cond[1].contains("~") ? "" : cond[1];
        String selectionClause =
                DbHelper.GOL_DARAH + " LIKE '%"+cond[0]+"%' AND "+
                        DbHelper.DUSUN + " LIKE '%"+cond[1]+"%'";

        Log.d("Query refresh",selectionClause);
        dbManager.clearClause();
        dbManager.setSelection(selectionClause);
        BankDarahmodel p = null;
        dbManager.setOrderBy("name_pendonor ASC");
        Cursor c = dbManager.fetchBankDarah("","");
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String uid = c.getString(c.getColumnIndexOrThrow("_id"));
            String name = c.getString(c.getColumnIndexOrThrow("name_pendonor"));
            String gol_darah = c.getString(c.getColumnIndexOrThrow("gol_darah"));
            String hp = c.getString(c.getColumnIndexOrThrow("telp"));
            String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));
            String gubug = c.getString(c.getColumnIndexOrThrow("gubug"));

            p = new BankDarahmodel();
            p.setId(uid);
            p.setNama(name);
            p.setGolds(gol_darah);
            p.setNomor(hp);
            p.setDusun(dusun+" / "+gubug);

            bankDarahmodels.add(p);
        }

        dbManager.close();
        lv.setAdapter(adapter);
//        AllConstants.params = null;

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
        menu.setGroupVisible(0,false);
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
        final Map<String, String> Params2 = new HashMap<String, String>();
        Params2.put("End",dateNow().toString());

        NavigationmenuController navi= new NavigationmenuController(this);
        int id = item.getItemId();
       // MenuItem register = R.id.nav_identitas_ibu;
        if (id == R.id.nav_identitas_ibu) {
            FlurryHelper.endTimedEvent(EventName,Params2);
            navi.startIdentitasIbu();
        }
        if (id == R.id.nav_transportasi) {
            FlurryHelper.endTimedEvent(EventName,Params2);
            navi.startTransportasi();
        }

        if (id == R.id.nav_bank_darah) {
           // navi.startBankDarah();
        }
        if(id == R.id.nav_logout){
//            super.onBackPressed();
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            FlurryHelper.endTimedEvent(EventName,Params2);
                            //FlurryAgent.onEndSession(context);
                            Intent intent = new Intent(BankDarahActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(BankDarahActivity.this);
            builder.setMessage("Anda yakin untuk \"Keluar\" dari aplikasi?").setPositiveButton("Ya", dialogClickListener)
                    .setNegativeButton("Tidak", dialogClickListener).show();
        }
        if(id == R.id.info){
            FlurryHelper.endTimedEvent(EventName,Params2);
            Intent intent = new Intent(BankDarahActivity.this, InformasiActivity.class);
            startActivity(intent);
            finish();
            // navi.gotoKIA();
        }
        if(id == R.id.kader_add){

            if(!forbidden) {
                FlurryHelper.endTimedEvent(EventName,Params2);
                navi.addKader();
            }
            else
                Toast.makeText(BankDarahActivity.this, "Maaf fitur ini hanya untuk bidan!",
                        Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initDropdownSort(){
        ImageView filterButton = (ImageView) findViewById(R.id.header_filter_icon);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),FilterActivity.class);
//                in.putExtra("database", (Parcelable) dbManager);
                Cursor c = dbManager.open().fetchUserData();
                c.moveToFirst();
                String pos = c.getString(c.getColumnIndexOrThrow(DbHelper.GROUPS));
                in.putExtra("iddesa",c.getString(c.getColumnIndexOrThrow(
                        pos.equals(getString(R.string.bidan))
                                ? DbHelper.LOCATION_ID
                                : DbHelper.PARENT_LOCATION
                        )
                ));
                in.putExtra("source",2);
                dbManager.close();
                startActivity(in);
            }
        });

        final Spinner dropdownSort = (Spinner) findViewById(R.id.dropdownSort);
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

        ImageView sortButton = (ImageView) findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dropdownSort.performClick();
            }
        });
    }
    private ArrayAdapter<String> spinnerAdapter(){
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, item[0]);
    }
    private final String [][] item = {
            {"Nama A-Z","Nama Z-A"},
            {"name_pendonor ASC","name_pendonor DESC"}
    };

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(AllConstants.SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("last_active", System.currentTimeMillis());
        editor.apply();
    }
}
