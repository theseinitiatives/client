package tgwofficial.atma.client.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.Utils.ApiUtils;
import tgwofficial.atma.client.activity.nativeform.FormAddIbuActivity;
import tgwofficial.atma.client.adapter.IdentitasibuCursorAdapter;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.model.ApiModel;
import tgwofficial.atma.client.sync.ApiService;

public class IdentitasIbuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DbManager dbManager;
    private DbHelper dbHelper;
    private Context context;
    private static final int    REQUEST_CODE_GET_JSON = 1;
    private static final String DATA_JSON_PATH        = "identitasibu.json";
    private Menu mymenu;
    private ApiService mService;
    SyncHttpClient client = new SyncHttpClient();
    private  String TAG = "POSTTT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetchIbu();
        Log.d("CURSORS", cursor.toString());

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
                Intent myIntent = new Intent(IdentitasIbuActivity.this, FormAddIbuActivity.class);
                startActivity(myIntent);
                //Snackbar.make(view, "Untuk Tambah Patient Baru", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        /*TextView img = (TextView) findViewById(R.id.name);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(IdentitasIbuActivity.this, IdentitasIbuDetailActivity.class);
                startActivity(myIntent);
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mService = ApiUtils.getSOService();
        Log.i("MSERVICE", mService.toString());

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
            // push();
            ///pulldata();

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
            Intent myIntent = new Intent(IdentitasIbuActivity.this, IdentitasIbuDetailActivity.class);
            startActivity(myIntent);
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

        RequestBody myreqbody = null;
        try {
            myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONArray(dummy)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<String> call =mService.savePost(myreqbody);
        Log.e("myreqbody", ""+myreqbody);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String>callback,Response<String>response) {
                String res = response.body();
                Log.e("DEMO", "post submitted to API." + response);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("DEMO", "Unable to submit post to API.",t);
                Log.e("call", String.valueOf(call));
            }
        });

        resetUpdating();
    }
    public JSONArray formatToJson()
    {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchUnSyncIbu();
        // String data = dbManager.formatToJson(cursor).toString();
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        JSONArray resultSet2 = new JSONArray();
        JSONObject rowObject2 = new JSONObject();

        //  resultSet.put();

        cursor.moveToFirst();
       // while (!cursor.isAfterLast()) {
            try
            {
                int totalColumn = cursor.getCount();
                JSONObject rowObject = new JSONObject();
                //looping data
                Log.i("COUNT",""+totalColumn);
                for( int i=0 ;  i< totalColumn ; i++ )
                {
                    /***
                     * TODO
                     * SET THE DATA FROM TABLE*/
                    rowObject2.put("user_id","userteset");
                    rowObject2.put("location_id","Dusun_test");
                    rowObject2.put("form_name","identitas_ibu");
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
       // }
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
        mService.getData(0).enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                if(response.isSuccessful()) {
                    dbManager.open();
                    dbManager.saveTodb2(response.body(),null);
                    dbManager.close();
                    Toast.makeText(context, "Sync Finished!",
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
                Toast.makeText(context, "Sync FAILED!",
                        Toast.LENGTH_LONG).show();
                Log.d("MainActivity", "error loading from API"+t);

            }
        });

        resetUpdating();
        finish();
        startActivity(getIntent());
    }




}
