package tgwofficial.atma.client.sync;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import tgwofficial.atma.client.db.DbManager;
import tgwofficial.atma.client.sync.model.IbuModel.Data;
import tgwofficial.atma.client.sync.model.IbuModel.IdentitasIbuModel;

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
        IdentitasIbuModel[] DataArray = gson.fromJson(data, IdentitasIbuModel[].class);
      //  List<IdentitasIbuModel> DataList = Arrays.asList(DataArray);
        List<IdentitasIbuModel> DataList = new ArrayList<>(Arrays.asList(DataArray));

         Log.d("ssssssssssssssss", ""+data);
       // Log.d("aaaaaaaaaaaaaaaaa", Arrays.toString(DataArray));


        for(IdentitasIbuModel model : DataList) {
            Log.i("getupdate_id" ,model.getupdate_id());
            Log.i("getform_name" ,model.getform_name());
            Log.i("getlocation_id" ,model.getlocation_id());
            Log.i("getuser_id" ,model.getuser_id());
            Log.i("getData" ,model.getData());
            String data_ = "["+model.getData()+"]";
            Log.i("aaaaaaaaaaaaaaaaa" ,data_);
            Data[] Datas = gson.fromJson(data_, Data[].class);
            List<Data> DataListed = new ArrayList<>(Arrays.asList(Datas));

            for(Data ListData : DataListed) {
                Log.i("getName" ,ListData.getName());
                Log.i("getSpousename" ,ListData.getSpousename());
                Log.i("getStatus" ,ListData.getStatus());
                Log.i("getDusun" ,ListData.getDusun());
                Log.i("getHpht" ,ListData.getHpht());
                Log.i("getHtp" ,ListData.getHtp());
                Log.i("getTglLahir" ,ListData.getTglLahir());
                Log.i("getTelp" ,ListData.getTelp());
                Log.i("getTglPersalinan" ,ListData.getTglPersalinan());
                Log.i("getKondisiIbu" ,ListData.getKondisiIbu());
                Log.i("getKondisiAnak" ,ListData.getKondisiAnak());
                Log.i("getKader" ,ListData.getKader());
                Log.i("getIsSend" ,ListData.getIsSend());
                Log.i("getIsSync" ,ListData.getIsSync());
                Log.i("getTimestamp" ,ListData.getTimestamp());


                }
            }
    }
/*for (List<Node> l1 : arrayLists) {
   for (Node n : l1) {
       System.out.print(n + " ");
   }

   System.out.println();
} */

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
