package theseinitiatives.atma.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.LoginActivity;
import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.Utils.ApiUtils;
import theseinitiatives.atma.client.Utils.FilterActivity;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.nativeform.FormAddIbuActivity;
import theseinitiatives.atma.client.activity.nativeform.FormCloseIbu;
import theseinitiatives.atma.client.activity.nativeform.FormRencanaPersalinan;
import theseinitiatives.atma.client.activity.nativeform.FormStatusPersalinanActivity;
import theseinitiatives.atma.client.adapter.IdentitasibuCursorAdapter;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;
import theseinitiatives.atma.client.model.IdentitasModel;
import theseinitiatives.atma.client.model.syncmodel.ApiModel;
import theseinitiatives.atma.client.sync.ApiService;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class IdentitasIbuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DbManager dbManager;
    private DbHelper dbHelper;
    private Context context;
    ListView lv;
    SearchView sv;
    private Menu mymenu;
    private ApiService mService;
    private  String TAG = "POSTTT";

    IdentitasibuCursorAdapter adapter;
    ArrayList<IdentitasModel> identitasModels=new ArrayList<>();
    String userId= "";
    String locaId = "";
    long upId;
    String locas;
    boolean forbidden = false;
    private boolean firstRun = true;
    String EventName = "IdentitasIbu";

    boolean isDusun = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      /*  Map<String, String> Params = new HashMap<String, String>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent(EventName,Params,true);*/
      //  Log.e("STARTING","TEST");
        lv = (ListView) findViewById(R.id.list_view);
        sv= (SearchView) findViewById(R.id.sv);
        //lv.setAdapter(adapter);
        adapter=new IdentitasibuCursorAdapter(this,identitasModels);

        getIbu("","resiko DESC");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getIbu(newText, "resiko DESC");
                return false;
            }
        });
        initDropdownSort();

        dbManager.open();
        if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            forbidden = true;
            isDusun = true;
        }
        String updateID = dbManager.getlatestUpdateId();
        upId = Long.parseLong(updateID);
        locas = dbManager.getlocName();

        dbManager.close();
              //  Log.e("DATETIME","  "+dateNow());
        /*String extra = getIntent().getStringExtra("login status");

        if(extra!=null){
            Toast.makeText(getApplicationContext(),extra,Toast.LENGTH_LONG).show();
        }*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long ids = id+1;
              //  Log.i("__id", ""+id);

             //   IdentitasIbuDetailActivity.id = String.valueOf(ids);
             //   FormRencanaPersalinan.id = String.valueOf(ids);
                choose(id);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(forbidden){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(IdentitasIbuActivity.this, FormAddIbuActivity.class);
                startActivity(myIntent);
                overridePendingTransition(0,0);
                //Snackbar.make(view, "Untuk Tambah Patient Baru", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mService = ApiUtils.getSOService();
       // Log.i("MSERVICE", mService.toString());
        refreshList();

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
        dbManager.open();
        String[]cond = AllConstants.params.split(AllConstants.FLAG_SEPARATOR);
        if(cond.length<2){
            cond = new String[]{"","","no"};
        }
        cond[0] = cond[0].contains("~") ? "" : cond[0];
        cond[1] = cond[1].contains("~") ? "" : cond[1];
        String selectionClause =
                DbHelper.HTP + " LIKE '%"+cond[0]+"%' AND "+
                DbHelper.DUSUN + " LIKE '%"+cond[1]+"%' "+
                (cond[2].equalsIgnoreCase("yes")
                        ? " AND "+DbHelper.RESIKO + " != ''"
                        : "");
        //HTP    //DUSUN //RISTI
       // Log.d("Query refresh",selectionClause);
        dbManager.clearClause();
        dbManager.setSelection(selectionClause);
        identitasModels.clear();
        IdentitasModel p = null;
        dbManager.setOrderBy("hpht desc");
        Cursor c = dbManager.fetchIbu("","");
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String uid = c.getString(c.getColumnIndexOrThrow("_id"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String spouse = c.getString(c.getColumnIndexOrThrow("spousename"));
            String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));
            String goldarah = c.getString(c.getColumnIndexOrThrow("htp"));
            String resiko = c.getString(c.getColumnIndexOrThrow("resiko"));
            p = new IdentitasModel();
            p.setId(uid);
            p.setNama(name);
            p.setPasangan(spouse);
            p.setDusuns(dusun);
            p.setStatus1(goldarah);
            p.setResiko(resiko);

            identitasModels.add(p);
        }
        dbManager.close();
        lv.setAdapter(adapter);
//        AllConstants.params = null;

    }
    public void choose (final  long ids){
        final String[] forms = {"Form Rencana Persalinan","Form Status Persalinan","Data Lengkap Ibu","Tutup Ibu" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setItems(forms, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if ("Form Rencana Persalinan".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormRencanaPersalinan.class);
                    dbManager.open();
                    Cursor c = dbManager.fetchuniqueId(uid);
                    c.moveToFirst();
                    String uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    dbManager.close();
                    intent.putExtra("uniqueId", uniqueId);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                   // finish();
                }if ("Form Status Persalinan".equals(forms[which])) {
                    if(forbidden){
                        Toast.makeText(IdentitasIbuActivity.this, "Maaf fitur ini hanya untuk bidan!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormStatusPersalinanActivity.class);
                    dbManager.open();
                    Cursor c = dbManager.fetchuniqueId(uid);
                    c.moveToFirst();
                    String uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    dbManager.close();
                    intent.putExtra("uniqueId", uniqueId);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                 //   finish();
                }
                if ("Data Lengkap Ibu".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, IdentitasIbuDetailActivity.class);
                    dbManager.open();
                    Cursor c = dbManager.fetchuniqueId(uid);
                    c.moveToFirst();
                    String uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    dbManager.close();
                    intent.putExtra("uniqueId", uniqueId);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                  //  finish();
                    FlurryHelper.endTimedEvent(EventName);
                }
                if ("Tutup Ibu".equals(forms[which])) {
                    if(forbidden){
                        Toast.makeText(IdentitasIbuActivity.this, "Maaf fitur ini hanya untuk bidan!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormCloseIbu.class);
                    dbManager.open();
                    Cursor c = dbManager.fetchuniqueId(uid);
                    c.moveToFirst();
                    String uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    dbManager.close();
                    intent.putExtra("uniqueId", uniqueId);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                  //  finish();
                }
            }
        });
        builder.show();
    }
    private void getIbu(String searchTerm, String orderBy)
    {
        identitasModels.clear();
        dbManager = new DbManager(this);
        dbManager.open();
        IdentitasModel p = null;
        if(searchTerm.equalsIgnoreCase(""))
            dbManager.setOrderBy(orderBy);
        Cursor c = dbManager.fetchIbu(searchTerm,orderBy);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            Cursor cur = dbManager.fetchStatusPersalinan(c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID)));
            cur.moveToFirst();
            boolean haveBirth;
            if(cur.getCount()>0) {
                String statusKehamilan = (cur.getString(cur.getColumnIndexOrThrow(DbHelper.STATUS_BERSALIN)));
                haveBirth = statusKehamilan == null ? false : !statusKehamilan.equalsIgnoreCase("hamil");
            }else{
                haveBirth = false;
            }
            String uid = c.getString(c.getColumnIndexOrThrow("_id"));
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            String spouse = c.getString(c.getColumnIndexOrThrow("spousename"));
            String dusun = c.getString(c.getColumnIndexOrThrow("dusun"));
           String resiko = c.getString(c.getColumnIndexOrThrow("resiko"));
           String htp = AllConstants.convertToDDMMYYYY(c.getString(c.getColumnIndexOrThrow("htp")));
            p = new IdentitasModel();
            p.setId(uid);
            p.setNama(name);
            p.setPasangan(spouse);
            p.setDusuns(dusun);
            p.setStatus1(htp);
            p.setResiko(resiko);
            p.setHaveBirth(haveBirth);

            identitasModels.add(p);
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
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mymenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);
            item.setActionView(iv);

            /**
             *
             * DATA Sync (For right now disabled)*/
            startSync();
            return true;
        }
        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
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
        NavigationmenuController  navi= new NavigationmenuController(this);
        int id = item.getItemId();
       // MenuItem register = R.id.nav_identitas_ibu;
        if (id == R.id.nav_identitas_ibu) {
           // navi.startIdentitasIbu();
        }
        if (id == R.id.nav_transportasi) {
            navi.startTransportasi();
            FlurryHelper.endTimedEvent(EventName,Params2);
        }

        if (id == R.id.nav_bank_darah) {
            navi.startBankDarah();
            FlurryHelper.endTimedEvent(EventName,Params2);
        }
        if(id == R.id.nav_logout){
//            super.onBackPressed();
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(IdentitasIbuActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                            FlurryHelper.endTimedEvent(EventName,Params2);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(IdentitasIbuActivity.this);
            builder.setMessage("Anda yakin untuk \"Keluar\" dari aplikasi?").setPositiveButton("Ya", dialogClickListener)
                    .setNegativeButton("Tidak", dialogClickListener).show();
        }
        if(id == R.id.info){
            Intent intent = new Intent(IdentitasIbuActivity.this, InformasiActivity.class);
            startActivity(intent);
            finish();

            FlurryHelper.endTimedEvent(EventName,Params2);
           // navi.gotoKIA();
        }
        if(id == R.id.kader_add){
            if(!forbidden) {
                navi.addKader();
                FlurryHelper.endTimedEvent(EventName,Params2);
            }
            else
                Toast.makeText(IdentitasIbuActivity.this, "Maaf fitur ini hanya untuk bidan!",
                        Toast.LENGTH_LONG).show();
            //super.onBackPressed();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void resetUpdating()
    {
        // Get our refresh item from the menu
        MenuItem m = mymenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
            // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }
    }

    private void refreshView(){
        ListView list = findViewById(R.id.list_view);
        list.requestLayout();

    }

    public void startSync(){
        push();
    }

    public void push(){
        /***TODO
         * =================================================
         * Included the data from table transportasi and bank darah
         * =================================================*/
        String dummy ="[\n" +
                "    {\n" +
                "        \"update_id\": \"1535462387138\",\n" +
                "        \"form_name\": \"identitas_ibu\",\n" +
                "        \"data\": \"{\\\"_id\\\":\\\"747\\\",\\\"name\\\":\\\"Zimbabwe\\\",\\\"spousename\\\":\\\" DUmmy2\\\",\\\"tgl_lahir\\\":\\\"\\\",\\\"dusun\\\":\\\"\\\",\\\"hpht\\\":\\\"\\\",\\\"htp\\\":\\\"\\\",\\\"gol_darah\\\":\\\"\\\",\\\"status\\\":\\\"Patient Baru\\\",\\\"kader\\\":\\\"\\\",\\\"telp\\\":\\\"\\\",\\\"tgl_persalinan\\\":\\\"\\\",\\\"kondisi_ibu\\\":\\\"\\\",\\\"kondisi_anak\\\":\\\"\\\",\\\"is_send\\\":\\\"0\\\",\\\"is_sync\\\":\\\"0\\\",\\\"timestamp\\\":\\\"2018-08-28 10:04:09\\\"}\",\n" +
                "        \"location_id\": \"Dusun_test\",\n" +
                "        \"user_id\": \"userteset\"\n" +
                "    }\n" +
                "]";

        // api post for pushing data to server api
        RequestBody myreqbody = null;
        String data = (alldata_formatToJson()).toString();
        myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                data);

        Call<ResponseBody> call =mService.savePost(myreqbody);
        Log.e("myreqbody========", ""+data);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody>callback,Response<ResponseBody>response) {
                String res = response.toString();
              //  Log.e("DEMO", "post submitted to API." + response);
              //  Log.e(TAG, "onResponse: " +response.code());
                if (response.code()==201){
                    updateSyncFlagIbu();
                }
                pulldata();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
              ///  Log.e("DEMO", "Unable to submit post to API.",t);
             //   Log.e("call", String.valueOf(call));
               pulldata();
            }
        });

    }

    private void updateSyncFlagIbu() {
        dbManager.open();
        dbManager.updateFlagSycn();
        dbManager.close();
    }

    public JSONArray alldata_formatToJson() {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchUnSyncForm();
        // String data = dbManager.formatToJson(cursor).toString();
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                      //  Log.d(TAG, e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }

    public void pulldata() {
        dbManager.open();
        if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            forbidden = true;
            isDusun = true;
        }
        String updateID = dbManager.getlatestUpdateId();
        upId = Long.parseLong(updateID);
        locas = dbManager.getlocName();
        dbManager.close();

        Log.e("pull data====",locas + upId);
        /*if(true)
            return;*/
        if(!isDusun) {
            mService.getDataDesa(locas,upId,100).enqueue(new Callback<List<ApiModel>>() {
                @Override
                public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                    if(response.isSuccessful()) {
                        Log.e("RESPONSE-----", response.body().toString());
                        if(response.body().toString().length() < 10) {
                            resetUpdating();
                            return;
                        }
                        dbManager.open();
                        dbManager.saveTodb2(response.body(),null);
                        dbManager.close();
                        Toast.makeText(IdentitasIbuActivity.this, "Sync Finished!",
                                Toast.LENGTH_LONG).show();
                      //  Log.e("PULLING", response.body().toString());
                        //  mAdapter.updateAnswers(response.body().getItems());
                      //  Log.d("MainActivity", "posts loaded from API");
                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                    }
                    resetUpdating();
                    refreshList();
                  //  insert latest updateid into db
                  //  fetchSyncedData()
                    getIbu("","resiko DESC");
                }

                @Override
                public void onFailure(Call<List<ApiModel>> call, Throwable t) {
                    // showErrorMessage();
                  //  Log.e("PULLING", "FAIL===");
                    Toast.makeText(IdentitasIbuActivity.this, "Sync FAILED!",
                            Toast.LENGTH_LONG).show();
                  //  Log.d("MainActivity", "error loading from API"+t);
                    resetUpdating();
                }
            });
        }
        else{
            mService.getDataDusun(locas,upId,100).enqueue(new Callback<List<ApiModel>>() {
                @Override
                public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                    if(response.isSuccessful()) {
                        Log.e("RESPONSE-----", response.body().toString());
                        if(response.body().toString().length() < 10) {
                            resetUpdating();
                            return;
                        }
                        dbManager.open();
                        dbManager.saveTodb2(response.body(),null);
                        dbManager.close();
                        Toast.makeText(IdentitasIbuActivity.this, "Sync Finished!",
                                Toast.LENGTH_LONG).show();
                       // Log.e("PULLING", response.body().toString());
                        //  mAdapter.updateAnswers(response.body().getItems());
                       // Log.d("MainActivity", "posts loaded from API");
                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                    }
                    resetUpdating();
                    refreshList();
                    getIbu("","resiko DESC");
                /****
                 * ====================*/
                    //    insert latest updateid into db
                //    fetchSyncedData()
                }

                @Override
                public void onFailure(Call<List<ApiModel>> call, Throwable t) {
                    // showErrorMessage();
                  //  Log.e("PULLING", "FAIL===");
                    Toast.makeText(IdentitasIbuActivity.this, "Sync FAILED!",
                            Toast.LENGTH_LONG).show();
                  //  Log.d("MainActivity", "error loading from API"+t);
                    resetUpdating();
                }
            });
        }

    }

    public void initDropdownSort(){
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
                in.putExtra("source",0);
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
                getIbu("",sortItem[1][position]+"");
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
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sortItem[0]);
    }

    private final String [][] sortItem = {
            {"Faktor Resiko","Nama A-Z","Nama Z-A","HTP Jan-Des", "HTP Des-Jan"},
            {"resiko DESC","name ASC","name DESC", "htp ASC", "htp DESC"}
    };
}
