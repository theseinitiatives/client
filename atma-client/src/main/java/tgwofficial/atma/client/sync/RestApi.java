package tgwofficial.atma.client.sync;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.sync.model.IbuModel.IdentitasIbuModel;
import tgwofficial.atma.client.sync.model.IbuModel.IdentitasIbuModelData;

public class RestApi {
    private String TAG = RestApi.class.getSimpleName();
    private Context mCon;
    private DbManager dbManager;
   // AsyncHttpClient client = new AsyncHttpClient("https://atma.theseforall.org");
    ArrayList<HashMap<String, String>> contactList;


    public void pull(){

        SyncHttpClient client = new SyncHttpClient();
        client.get(
                "https://atma.theseforall.org/api/pull?location-id=Dusun_test&update-id=0&batch-size=100",
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {

                        saveTodb(response, statusCode);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                });


    }

    public void saveTodb(String  data, Integer statusCode) {
        Gson gson = new GsonBuilder().create();
        IdentitasIbuModel[] videoArray = gson.fromJson(data, IdentitasIbuModel[].class);

        List<IdentitasIbuModel> videoList = Arrays.asList(videoArray);
//                        IdentitasIbuModel movie = gson.fromJson(response, IdentitasIbuModel.class);
        //                       saveTodb(movie);
        Log.d("ssssssssssssssss", ""+statusCode);
        Log.d("aaaaaaaaaaaaaaaaa", Arrays.toString(videoArray));


    }
    public void push(){
      /*  dbManager = new DbManager(this);
        dbManager.open();

        //pull all identitasibu from local db
        Cursor cursor = dbManager.fetchIbu();
        Log.d("POSTT", cursor.toString());*/

       /* RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), "");

        client.post("/api/push", postBody, new JsonResponseHandler()
        {
            @Override public void onSuccess()
            {
                JsonElement result = getContent();
            }
        });
        dbManager.close();*/
    }
}
