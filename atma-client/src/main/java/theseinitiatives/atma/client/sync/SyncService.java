package theseinitiatives.atma.client.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import theseinitiatives.atma.client.Utils.ApiUtils;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.db.DbManager;
import theseinitiatives.atma.client.model.syncmodel.ApiModel;

public class SyncService{

    private ApiService mService;
    private DbManager dbManager;
    private long upId;
    private String locas;
    private String loc_tag;
    private boolean forbidden = false;
    private boolean isDusun = false;

    private Context context;

    public SyncService(Context context){
        this.context = context;
        dbManager = new DbManager(context);
        mService = ApiUtils.getSOService();
    }

    public void startSync(){
        push();
    }

    private void push(){
        // api post for pushing data to server api
        RequestBody myreqbody = null;
        String data = (alldata_formatToJson()).toString();
        myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                data);

        Call<ResponseBody> call = mService.savePost(myreqbody);
        Log.e("myreqbody========", ""+data);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody>callback, Response<ResponseBody> response) {
                String res = response.toString();
                if (response.code()==201){
                    dbManager.open();
                    dbManager.updateFlagSycn();
                    dbManager.close();
                }
                pulldata();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pulldata();
            }
        });

    }

    private JSONArray alldata_formatToJson() {
        dbManager.open();
        //pull all identitasibu data from local db
        Cursor cursor = dbManager.fetchUnSyncForm();

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

    private void pulldata() {
        dbManager.open();
        if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            forbidden = true;
            isDusun = true;
        }
        String updateID = dbManager.getlatestUpdateId();
        upId = Long.parseLong(updateID);
        locas = dbManager.getlocName();
        loc_tag = dbManager.getLocTagName();
        dbManager.close();

        Log.e("pull data====",locas + upId);
        /*if(true)
            return;*/

        mService.getData(loc_tag,locas,upId,5000).enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

                if(response.isSuccessful()) {
                    Log.e("RESPONSE-----", response.body().toString());
                    if(response.body().toString().length() < 10) {
                        return;
                    }
                    SaveToDb saveToDb = new SaveToDb();
                    saveToDb.execute(response.body());
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {
                // showErrorMessage();
            }
        });

    }

    private class SaveToDb extends AsyncTask<List<ApiModel>, Void, String> {

        @Override
        protected String doInBackground(List<ApiModel>... params) {
            dbManager.open();
            dbManager.saveTodb2(params[0],null);
            dbManager.close();
            return "SavingToDB";
        }
    }
}
