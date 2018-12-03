package tgwofficial.atma.client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tgwofficial.atma.client.Utils.LoginAuth;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.sync.ApiService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        edtUsername = (EditText) findViewById(R.id.email);
        edtPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);

        edtUsername.setText("demo");
        edtPassword.setText("Satu2345");
      //  userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
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
//                                    Toast.makeText(LoginActivity.this," response message "+response.body().string(),Toast.LENGTH_LONG).show();
                                    Log.d("Response", response.body().string());
                                    AllConstants.MAY_PROCEED = true;
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
//
                    new AuthLogin().execute();

                    //do login
                   // doLogin(username, password);
                }
            }
        });

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
        /*if(!username.equalsIgnoreCase("demo") & !password.equalsIgnoreCase("demo")) {
            Toast.makeText(this, "Username and Password Incorrect!!", Toast.LENGTH_SHORT).show();
            return  false;
        }*/

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
                    go=AllConstants.MAY_PROCEED;
                    Intent myIntent = new Intent(getApplicationContext(), IdentitasIbuActivity.class);
                    myIntent.putExtra("login status","Login Success");
                    startActivity(myIntent);
                    overridePendingTransition(0, 0);
                    break;
                }if(attempt>20)
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
            if(!AllConstants.MAY_PROCEED)
                Toast.makeText(getApplicationContext(),"Failed to Login, please check your connection, username or password", Toast.LENGTH_LONG).show();
        }
    }



    /*private void doLogin(final String username,final String password){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username, password);
        client.get("http://192.168.1.4:5000/api/resource", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("SUCCESS!!!!!!!!!!");
                Intent myIntent = new Intent(LoginActivity.this, IdentitasIbuActivity.class);
                startActivity(myIntent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                    error)
            {

                error.printStackTrace(System.out);
            }
        });
    }*/

}

