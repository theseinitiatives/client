package tgwofficial.atma.client.sync;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.JsonResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import tgwofficial.atma.client.db.DbManager;

public class RestApi extends AppCompatActivity {
    private String TAG = RestApi.class.getSimpleName();
    private Context mCon;
    private DbManager dbManager;
    AsyncHttpClient client = new AsyncHttpClient("https://atma.theseforall.org");
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void pull(){
         client.get("/api/pull?location-id=Dusun_test&update-id=0&batch-size=100", new JsonResponseHandler()
        {
            @Override public void onSuccess()
            {
                JsonElement result = getContent();
                Log.d("HASIL", result.toString());

                saveTodb(result.toString());
            }

        });

    }

    public void saveTodb(String resutl){

    }
    public void push(){
      /*  dbManager = new DbManager(this);
        dbManager.open();

        //pull all identitasibu from local db
        Cursor cursor = dbManager.fetchIbu();
        Log.d("POSTT", cursor.toString());*/

        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), "");

        client.post("/api/push", postBody, new JsonResponseHandler()
        {
            @Override public void onSuccess()
            {
                JsonElement result = getContent();
            }
        });
        dbManager.close();
    }
}
