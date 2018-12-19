package tgwofficial.atma.client.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgwofficial.atma.client.AllConstants;
import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.Utils.ApiUtils;
import tgwofficial.atma.client.Utils.FilterActivity;
import tgwofficial.atma.client.activity.nativeform.FormAddIbuActivity;
import tgwofficial.atma.client.activity.nativeform.FormCloseIbu;
import tgwofficial.atma.client.activity.nativeform.FormRencanaPersalinan;
import tgwofficial.atma.client.activity.nativeform.FormStatusPersalinanActivity;
import tgwofficial.atma.client.adapter.IdentitasibuCursorAdapter;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.model.IdentitasModel;
import tgwofficial.atma.client.model.syncmodel.ApiModel;
import tgwofficial.atma.client.sync.ApiService;

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
    private boolean firstRun = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        /*String extra = getIntent().getStringExtra("login status");

        if(extra!=null){
            Toast.makeText(getApplicationContext(),extra,Toast.LENGTH_LONG).show();
        }*/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long ids = id+1;
                Log.i("__id", ""+id);

             //   IdentitasIbuDetailActivity.id = String.valueOf(ids);
             //   FormRencanaPersalinan.id = String.valueOf(ids);
                choose(id);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        Log.i("MSERVICE", mService.toString());
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
        Toast.makeText(getApplicationContext(),AllConstants.params,Toast.LENGTH_LONG).show();
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
        Log.d("Query refresh",selectionClause);
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
        final String[] forms = {"Form Status Persalinan","Form Rencana Persalinan","Detail View Ibu","Tutup Ibu" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setItems(forms, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if ("Form Rencana Persalinan".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormRencanaPersalinan.class);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                    finish();
                }if ("Form Status Persalinan".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormStatusPersalinanActivity.class);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                    finish();
                }
                if ("Detail View Ibu".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, IdentitasIbuDetailActivity.class);
                    dbManager.open();
                    Cursor c = dbManager.fetchuniqueId(uid);
                    c.moveToFirst();
                    String uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    dbManager.close();
                    intent.putExtra("uniqueId", uniqueId);
                    startActivity(intent);
                    finish();
                }
                if ("Tutup Ibu".equals(forms[which])) {
                    String uid = identitasModels.get((int) ids).getId();
                    Intent intent = new Intent(IdentitasIbuActivity.this, FormCloseIbu.class);
                    intent.putExtra("id", uid);
                    startActivity(intent);
                    finish();
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
            push();
            pulldata();
            //push();
            refreshView();
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
        }
        if(id == R.id.nav_logout){
            super.onBackPressed();
        }
        if(id == R.id.kader_add){
            navi.addKader();
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


        // api post for ibu data
        RequestBody myreqbody = null;
        myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (ibudata_formatToJson()).toString());


        Call<ResponseBody> call =mService.savePost(myreqbody);
        Log.e("myreqbody========", ""+ibudata_formatToJson().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody>callback,Response<ResponseBody>response) {
                String res = response.toString();
                Log.e("DEMO", "post submitted to API." + response);
                Log.e(TAG, "onResponse: " +response.code());
                if (response.code()==201){
                    updateSyncFlagIbu();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DEMO", "Unable to submit post to API.",t);
                Log.e("call", String.valueOf(call));
            }
        });

        // api post for transportasi
        /*RequestBody reqTrans = null;
       // try{
            reqTrans = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    ("{"+transportasi_formatToJson().toString()+"}"));
       // } catch (JSONException e) {
         //   e.printStackTrace();

       // }
        Call<String> postTrans = mService.savePost(reqTrans);
        postTrans.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resp = response.body();
                Log.e("DEMO", "post trans submitted to API." + response);
                updateSyncFlagTrans();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("DEMO", "Unable to submit post to API.",t);
                Log.e("call", String.valueOf(call));
            }
        });

        // api post for Bank daraj
        RequestBody reqDarah = null;
       // try{
            reqDarah = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (bankDarah_formatToJson().toString()));
       // } catch (JSONException e) {
        //    e.printStackTrace();

        //}
        Call<String> postDarah = mService.savePost(reqDarah);
        postDarah.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resp = response.body();
                Log.e("DEMO", "post bank darah submitted to API." + response);
                updateSyncFlagBank();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("DEMO", "Unable to submit post to API.",t);
                Log.e("call", String.valueOf(call));
            }
        });*/

//        Toast.makeText(context, "Sync Finished!",
  //              Toast.LENGTH_LONG).show();
        resetUpdating();
    }

    private void updateSyncFlagIbu() {
        dbManager.open();
        dbManager.updateFlagIbu();
        dbManager.close();
    }
    private void updateSyncFlagTrans() {
        dbManager.open();
        dbManager.updateFlagTrans();
        dbManager.close();
    }
    private void updateSyncFlagBank() {
        dbManager.open();
        dbManager.updateFlagBank();
        dbManager.close();
    }


    public JSONArray ibudata_formatToJson()
    {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchUnSyncIbu();
        // String data = dbManager.formatToJson(cursor).toString();
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        JSONArray resultSet2 = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            resultSet = new JSONArray();
            JSONObject rowObject = new JSONObject();
            JSONObject rowObject2 = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {

                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject2.put("form_name","identitas_ibu");
                        if(cursor.getColumnName(i).equalsIgnoreCase("user_id")){
                            rowObject2.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        if(cursor.getColumnName(i).equalsIgnoreCase("location_id")){
                            rowObject2.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        if(cursor.getColumnName(i).equalsIgnoreCase("update_id")){
                            rowObject2.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                }

            }
            resultSet.put(rowObject);

            try{
                rowObject2.put("data",resultSet);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            resultSet2.put(rowObject2);

            cursor.moveToNext();
        }

        cursor.close();
        Log.e(TAG, "ibudata_formatToJson: "+resultSet2.toString());
        return resultSet2;


        //example expected result

                    /*	update_id: update_id,
                        form_name: nama_table1,
                        location_id: location_id,
                        user_id: user_id
                        data: {
                            table1_id: …,
                            data_lain: …,
                            data_lain: ...
                     },

                    },
*/

    }

    public JSONArray transportasi_formatToJson()
    {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchunSyncTrans();
        // String data = dbManager.formatToJson(cursor).toString();
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        JSONArray resultSet2 = new JSONArray();
        JSONObject rowObject2 = new JSONObject();

        //  resultSet.put();

        cursor.moveToFirst();
         while (!cursor.isAfterLast()) {
        try
        {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            //looping data
            Log.i("COUNT",""+totalColumn);
            for( int i=0 ;  i< totalColumn ; i++ )
            {
                /***
                 * TODO
                 * SET THE DATA FROM TABLE*/
                rowObject2.put("user_id",userId);
                rowObject2.put("location_id",locaId);
                rowObject2.put("form_name","transportasi");
                rowObject2.put("update_id",System.currentTimeMillis());
                // Log.i("ASDASD",resultSet2.toString());

                if( cursor.getColumnName(i) != null )
                {


                    if( cursor.getString(i) != null )
                    {
                        Log.d("TAG_NAME", cursor.getString(i) );
                        rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                    }
                    else
                    {
                        rowObject.put( cursor.getColumnName(i) ,  "" );
                    }

                }

                resultSet2.put(rowObject2);
                rowObject2.put("data",resultSet);


            }

            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        catch( Exception e )
        {
            Log.d("TAG_NAME", e.getMessage()  );
        }
         }
        cursor.close();
        dbManager.close();
        return resultSet2;
    }

    public JSONArray bankDarah_formatToJson()
    {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchUnsyncBankDarah();
        // String data = dbManager.formatToJson(cursor).toString();
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        JSONArray resultSet2 = new JSONArray();
        JSONObject rowObject2 = new JSONObject();

        //  resultSet.put();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        try
        {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            //looping data
            Log.i("COUNT",""+totalColumn);
            for( int i=0 ;  i< totalColumn ; i++ )
            {
                /***
                 * TODO
                 * SET THE DATA FROM TABLE*/
                rowObject2.put("user_id",userId);
                rowObject2.put("location_id",locaId);
                rowObject2.put("form_name","bank_darah");
                rowObject2.put("update_id",System.currentTimeMillis());
                // Log.i("ASDASD",resultSet2.toString());

                if( cursor.getColumnName(i) != null )
                {


                    if( cursor.getString(i) != null )
                    {
                        Log.d("TAG_NAME", cursor.getString(i) );
                        rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                    }
                    else
                    {
                        rowObject.put( cursor.getColumnName(i) ,  "" );
                    }

                }

                resultSet2.put(rowObject2);
                rowObject2.put("data",resultSet);


            }

            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        catch( Exception e )
        {
            Log.d("TAG_NAME", e.getMessage()  );
        }
         }
        cursor.close();
        dbManager.close();
        return resultSet2;
    }

    public void pulldata() {
        /***
         * *doing first pull data
         *
         * =================================================
         * TODO
         * SEPARATE SYNC BETWEEN FIRST PULL AND UPDATE PULL
         * =================================================*/
        dbManager.open();
        String updateID = dbManager.getlatestUpdateId();
        long upId = Long.parseLong(updateID);

        String locas = dbManager.getlocName();
        mService.getData(locas,upId,100).enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                if(response.isSuccessful()) {
                    dbManager.open();
                    dbManager.saveTodb2(response.body(),null);
                    dbManager.close();
                    Toast.makeText(IdentitasIbuActivity.this, "Sync Finished!",
                            Toast.LENGTH_LONG).show();
                  //  Log.i("PULLING", response.body());
                  //  mAdapter.updateAnswers(response.body().getItems());
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {
               // showErrorMessage();
                Toast.makeText(IdentitasIbuActivity.this, "Sync FAILED!",
                        Toast.LENGTH_LONG).show();
                Log.d("MainActivity", "error loading from API"+t);

            }
        });

        resetUpdating();
        finish();
        startActivity(getIntent());
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
                in.putExtra("iddesa",c.getString(c.getColumnIndexOrThrow(DbHelper.PARENT_LOCATION)));
                dbManager.close();
                startActivity(in);
            }
        });

        Spinner dropdownSort = (Spinner) findViewById(R.id.dropdownSort);
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
    }
    private ArrayAdapter<String> spinnerAdapter(){
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sortItem[0]);
    }

    private final String [][] sortItem = {
            {"1 Resiko","Nama A-Z","Nama Z-A","HTP Jan-Des", "HTP Des-Jan"},
            {"resiko DESC","name ASC","name DESC", "htp ASC", "htp DESC"}
    };
}
