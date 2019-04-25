package theseinitiatives.atma.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.application.App;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;
import theseinitiatives.atma.client.sync.ApiService;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    final Activity activity = this;
    EditText edtUsername;
    EditText edtPassword;
    TextView buildText;
    Button btnLogin;
    ProgressBar progressBar;
    private Context context = App.getAppContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlurryAgent.onStartSession(context);
        setContentView(R.layout.login_layout);
        edtUsername = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);
        init();

        buildText = (TextView) findViewById(R.id.build_text);
        buildText.setText(AllConstants.version_build);

        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //  userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Map<String, String> Params = new HashMap<>();
                Params.put("Try Login",dateNow().toString());
                FlurryHelper.logOneTimeEvent(activity.getClass().getSimpleName(), Params);
                final String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AllConstants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService = retrofit.create(ApiService.class);
                    Call<ResponseBody> result = apiService.authLogin(username,password);
                    result.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.body()!=null) {
                                    String temp = response.body().string();
                                    Log.d("onResponse",temp);
                                    FlurryAgent.setUserId(username);
                                    saveUserData(temp);
                                    saveLocationTree(temp);
                                    AllConstants.MAY_PROCEED = temp.length() > 50;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                AllConstants.MAY_PROCEED = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,"failure detected",Toast.LENGTH_LONG).show();
                            AllConstants.MAY_PROCEED = false;
                        }
                    });
                    new AuthLogin().execute();

                    //do login
                    // doLogin(username, password);
                }
            }
        });

    }


    private void init(){
        SharedPreferences sharedPref = getSharedPreferences(AllConstants.SHARED_PREF, Context.MODE_PRIVATE);
        boolean loggedIn = sharedPref.getBoolean(getString(R.string.loggedin), false);
        if (loggedIn){
            long last_active = sharedPref.getLong("last_active", 0);
            long interval = System.currentTimeMillis()-last_active;
            if (!(interval>=57600000)){
                Intent myIntent = new Intent(getApplicationContext(), IdentitasIbuActivity.class);
                startActivity(myIntent);
                finish();
            }
        }
    }

    public boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        fillUserIfExists();
        DbManager db = new DbManager(getApplicationContext()).open();
        Cursor c = db.fetchUserCredential();
        c.moveToFirst();
        Log.e("cursor length",c.getColumnCount()+"");
        try {
            if (c.getString(c.getColumnIndexOrThrow("username")).equalsIgnoreCase(username) &&
                    c.getString(c.getColumnIndexOrThrow("password")).equals(password)) {
                onLoginSuccess();
                return false;
            }else{
                Toast.makeText(getApplicationContext(), "Failed to Login, please check your connection, username or password", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        }catch(Exception e){

        }
        return true;
    }

    private class AuthLogin extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            boolean go = false;
            int attempt = 0;
            while(!go){
                attempt++;
                if(AllConstants.MAY_PROCEED) {
//                    progressBar.setVisibility(View.INVISIBLE);
                    go=AllConstants.MAY_PROCEED;
                    onLoginSuccess();
                    break;
                }if(attempt>30)
                    break;
                try {
                    Thread.sleep(250);
                }catch(InterruptedException ie){
                    break;
                }
            }
            return AllConstants.FLAG_SUCCESS;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!AllConstants.MAY_PROCEED) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Failed to Login, please check your connection, username or password", Toast.LENGTH_LONG).show();
            }
            AllConstants.MAY_PROCEED=false;
        }
    }

    private void saveUserData(String jsonString){
        try{
            JSONObject obj = new JSONObject(jsonString);
            JSONObject user = new JSONObject(obj.get("user").toString());

            JSONObject user_location = new JSONObject((obj.get("user_location").toString()));
            DbManager db = new DbManager(getApplicationContext()).open();
            db.insertUserData(
                    user.getString(DbHelper.PERSON_ID),
                    user.getString(DbHelper.USERNAME),
                    edtPassword.getText().toString(),
                    user.getString(DbHelper.EMAIL),
                    user.getString(DbHelper.FIRST_NAME),
                    user.getString(DbHelper.LAST_NAME),
                    user.getString(DbHelper.COMPANY),
                    user.getString(DbHelper.PHONE),
                    user.getString(DbHelper.GROUPS),
                    user_location.getString(DbHelper.LOCATION_ID),
                    user_location.getString("name"),
                    user_location.getString(DbHelper.LOCATION_TAG_ID),
                    user_location.getString(DbHelper.PARENT_LOCATION),
                    user_location.getString(DbHelper.LOCATION_TAG)
            );
        }catch (JSONException e){
            Log.e(getLocalClassName(),e.getMessage());
        }
    }

    private void saveLocationTree(String jsonString){
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray location = new JSONArray((obj.get("locations_tree").toString()));
            DbManager db = new DbManager(getApplicationContext()).open();

            for (int i = 0; i < location.length(); i++) {
                JSONObject var = location.getJSONObject(i);
                Log.e("Variable "+i,var.toString());
                db.insertLocationTree(
                        var.getString(DbHelper.LOCATION_ID),
                        var.getString("name"),
                        var.getString(DbHelper.LOCATION_TAG_ID),
                        var.getString(DbHelper.PARENT_LOCATION),
                        var.getString(DbHelper.LOCATION_TAG)
                );
            }
        }catch (JSONException e){
            Log.e(getLocalClassName(),e.getMessage());
        }
    }


    private void fillUserIfExists() {

        DbManager db = new DbManager(getApplicationContext()).open();
        Cursor c = db.fetchUserCredential();
        if(c.moveToFirst()) {
            try {
                String user = c.getString(c.getColumnIndexOrThrow("username"));
                edtUsername.setText(user);
                edtUsername.setEnabled(false);
                FlurryAgent.setUserId(user);
            } catch (Exception e) {

            }
        }
       /* if (context.userService().hasARegisteredUser()) {

        }*/
    }

    private void onLoginSuccess(){
        SharedPreferences sharedPref = getSharedPreferences(AllConstants.SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.loggedin), true);
        editor.apply();
        Intent myIntent = new Intent(getApplicationContext(), IdentitasIbuActivity.class);
        myIntent.putExtra("login status","Login Success");
        FlurryHelper.endFlurryLog(this);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillUserIfExists();
        FlurryHelper.startFlurryLog(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(AllConstants.SHARED_PREF, Context.MODE_PRIVATE);
        boolean loggedIn = sharedPref.getBoolean(getString(R.string.loggedin), false);
        if (!loggedIn){
            FlurryHelper.endFlurryLog(this);
        }
    }
}