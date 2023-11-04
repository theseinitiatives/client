package theseinitiatives.atma.client.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.LoginActivity;
import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.Utils.FilterActivity;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.nativeform.FormAddKader;
import theseinitiatives.atma.client.adapter.KaderCursorAdapter;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;
import theseinitiatives.atma.client.model.KaderViewModel;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class KaderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String EventName = "kader";
    private Activity activity;
    private DbManager dbManager;
    ListView lv;
    SearchView sv;
    long upId;
    String locas;
    boolean forbidden = false;
    private boolean firstRun = true;
    KaderCursorAdapter adapter;
    ArrayList<KaderViewModel> kaderviewmodel=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kader_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sv= (SearchView) findViewById(R.id.sv);
        lv = (ListView) findViewById(R.id.list_view);
        //lv.setAdapter(adapter);
        adapter=new KaderCursorAdapter(this,kaderviewmodel);

        getKader("","name ASC");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getKader(newText, "name ASC");
                return false;
            }
        });
        initDropdownSort();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(KaderActivity.this, FormAddKader.class);
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

    private void refreshList(){
        if(AllConstants.params == null)
            return;
        kaderviewmodel.clear();
        dbManager.open();
        String[]cond = AllConstants.params.split(AllConstants.FLAG_SEPARATOR);
        if(cond.length<2){
            cond = new String[]{"","","no"};
        }
        cond[0] = cond[0].contains("~") ? "" : cond[0];
        cond[1] = cond[1].contains("~") ? "" : cond[1];
        String selectionClause =
                DbHelper.DUSUN + " LIKE '%"+cond[1]+"%'";

        dbManager.clearClause();
        dbManager.setSelection(selectionClause);
        KaderViewModel p = null;
        dbManager.setOrderBy("name ASC");
        Cursor c = dbManager.fetchKaders("","");
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME));
            String dusun = c.getString(c.getColumnIndexOrThrow(DbHelper.DUSUN));
            String username = c.getString(c.getColumnIndexOrThrow(DbHelper.USERNAME));
            String password = c.getString(c.getColumnIndexOrThrow(DbHelper.PASSWORD));

            p = new KaderViewModel();
            p.setNama(name);
            p.setDusuns(dusun);
            p.setUsername(username);
            p.setPassword(password);

            kaderviewmodel.add(p);
        }

        dbManager.close();
        lv.setAdapter(adapter);
//        AllConstants.params = null;

    }
    private void getKader(String searchTerm, String orderBy)
    {
        kaderviewmodel.clear();
        dbManager = new DbManager(this);
        dbManager.open();
        KaderViewModel p = null;
        if(searchTerm.equalsIgnoreCase(""))
            dbManager.setOrderBy(orderBy);
        Cursor c = dbManager.fetchKaders(searchTerm, orderBy);
        while (c.moveToNext()) {
                int id = c.getInt(0);
                //DUSUN,
            //            TELP,
            //            USERNAME,
            //            PASSWORD,
//                String uid = c.getString(c.getColumnIndexOrThrow("_id"));
                String name = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME));
                String dusun = c.getString(c.getColumnIndexOrThrow(DbHelper.DUSUN));
                String username = c.getString(c.getColumnIndexOrThrow(DbHelper.USERNAME));
                String password = c.getString(c.getColumnIndexOrThrow(DbHelper.PASSWORD));
                p = new KaderViewModel();
               // p.setId(uid);
                p.setNama(name);
                p.setDusuns(dusun);
                p.setUsername(username);
                p.setPassword(password);

                kaderviewmodel.add(p);
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
            Intent myIntent = new Intent(KaderActivity.this, IdentitasIbuActivity.class);
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
        }*/

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
            FlurryHelper.endTimedEvent(EventName,Params2);
            navi.startBankDarah();
        }
        if(id == R.id.nav_logout){
//            super.onBackPressed();
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            FlurryHelper.endTimedEvent(EventName,Params2);
                            Intent intent = new Intent(KaderActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(KaderActivity.this);
            builder.setMessage("Anda yakin untuk \"Keluar\" dari aplikasi?").setPositiveButton("Ya", dialogClickListener)
                    .setNegativeButton("Tidak", dialogClickListener).show();
        }
        if(id == R.id.info){
            FlurryHelper.endTimedEvent(EventName,Params2);
            Intent intent = new Intent(KaderActivity.this, InformasiActivity.class);
            startActivity(intent);
            finish();
            // navi.gotoKIA();
        }
        if(id == R.id.kader_add){
           /* if(!forbidden) {
              //  navi.addKader();
            }

            else{
                Toast.makeText(KaderActivity.this, "Maaf fitur ini hanya untuk bidan!",
                        Toast.LENGTH_LONG).show();
            }*/
            //super.onBackPressed();
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
                in.putExtra("source",-1);
                dbManager.close();
                startActivity(in);
            }
        });

        final Spinner dropdownSort = (Spinner) findViewById(R.id.dropdownSort);
        dropdownSort.setAdapter(spinnerAdapter());
        dropdownSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(context,position+"Selected",Toast.LENGTH_SHORT).show();
                getKader("",item[1][position]+"");
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
            {"name ASC","name DESC"}
    };
}
