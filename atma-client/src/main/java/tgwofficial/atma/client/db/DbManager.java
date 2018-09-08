package tgwofficial.atma.client.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tgwofficial.atma.client.model.Data;
import tgwofficial.atma.client.model.IdentitasIbuModel;

import static android.support.constraint.Constraints.TAG;

public class DbManager {
    private DbHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DbManager(Context c) {
        context = c;
    }

    public DbManager open() throws SQLException {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void saveTodb(String  data, Integer statusCode) {

        Gson gson = new GsonBuilder().create();
        IdentitasIbuModel[] DataArray = gson.fromJson(data, IdentitasIbuModel[].class);
        //  List<IdentitasIbuModel> DataList = Arrays.asList(DataArray);
        List<IdentitasIbuModel> DataList = new ArrayList<>(Arrays.asList(DataArray));

        Log.d("ssssssssssssssss", ""+data);
        // Log.d("aaaaaaaaaaaaaaaaa", Arrays.toString(DataArray));
//        database.beginTransaction();
        for(IdentitasIbuModel model : DataList) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(DbHelper.UPDATE_ID, model.getupdate_id());
          //  contentValue.put(DbHelper.FORM_NAME, model.getform_name());
            contentValue.put(DbHelper.LOCATION_ID, model.getlocation_id());
            contentValue.put(DbHelper.USER_ID, model.getuser_id());

           /* Log.i("getupdate_id" ,model.getupdate_id());
            Log.i("getform_name" ,model.getform_name());
            Log.i("getlocation_id" ,model.getlocation_id());
            Log.i("getuser_id" ,model.getuser_id());
            Log.i("getData" ,model.getData());*/

           String data_ = "["+model.getData()+"]";
            Log.i("aaaaaaaaaaaaaaaaa" ,data_);
            Data[] Datas = gson.fromJson(data_, Data[].class);
            List<Data> DataListed = new ArrayList<>(Arrays.asList(Datas));

            if(model.getform_name().equals("identitas_ibu")) {
                for (Data ListData : DataListed) {
                    contentValue.put(DbHelper.NAME, ListData.getName());
                    contentValue.put(DbHelper.SPOUSENAME, ListData.getSpousename());
                    contentValue.put(DbHelper.STATUS, ListData.getStatus());
                    contentValue.put(DbHelper.DUSUN, ListData.getDusun());
                    contentValue.put(DbHelper.HPHT, ListData.getHpht());
                    contentValue.put(DbHelper.HTP, ListData.getHtp());
                    contentValue.put(DbHelper.TGL_LAHIR, ListData.getTglLahir());
                    contentValue.put(DbHelper.TELP, ListData.getTelp());
                    contentValue.put(DbHelper.TGL_PERSALINAN, ListData.getTglPersalinan());
                    contentValue.put(DbHelper.KONDISI_IBU, ListData.getKondisiIbu());
                    contentValue.put(DbHelper.KONDISI_ANAK, ListData.getKondisiAnak());
                    contentValue.put(DbHelper.KADER, ListData.getKader());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, ListData.getTimestamp());

                   /* Log.i("getName", ListData.getName());
                    Log.i("getSpousename", ListData.getSpousename());
                    Log.i("getStatus", ListData.getStatus());
                    Log.i("getDusun", ListData.getDusun());
                    Log.i("getHpht", ListData.getHpht());
                    Log.i("getHtp", ListData.getHtp());
                    Log.i("getTglLahir", ListData.getTglLahir());
                    Log.i("getTelp", ListData.getTelp());
                    Log.i("getTglPersalinan", ListData.getTglPersalinan());
                    Log.i("getKondisiIbu", ListData.getKondisiIbu());
                    Log.i("getKondisiAnak", ListData.getKondisiAnak());
                    Log.i("getKader", ListData.getKader());
                    Log.i("getIsSend", ListData.getIsSend());
                    Log.i("getIsSync", ListData.getIsSync());
                    Log.i("getTimestamp", ListData.getTimestamp());*/
                    database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);

                }
            }
            else if(model.getform_name().equals("transportasi")){

            }
           /// database.setTransactionSuccessful();
           // database.endTransaction();
        }
    }

   /* public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.NAME, name);
        contentValue.put(DbHelper.TIMESTAMP, desc);
        database.insert(DbHelper.TABLE_NAME, null, contentValue);
    }
*/
    public Cursor fetchIbu() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                DbHelper.DUSUN,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,
                DbHelper.STATUS,
                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchUnSyncIbu() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                DbHelper.DUSUN,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,
                DbHelper.STATUS,
                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.IS_SEND +"!=1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchTrans() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.Jenis,
                DbHelper.Kapasitas,
                DbHelper.TELP,
                DbHelper.DUSUN,
                DbHelper.GUBUG,
                DbHelper.PROFESI,
                DbHelper.KET,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_TRANS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchBankDarah() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DbHelper.GOL_DARAH,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_BANK, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;

    }

    /*public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.DATA, name);
       // contentValues.put(DbHelper.TIMESTAMP, desc);
        int i = database.update(DbHelper.TABLE_NAME, contentValues, DbHelper._ID + " = " + _id, null);
        return i;
    }*/

    public void delete(long _id) {
        database.delete(DbHelper.TABLE_NAME_IBU, DbHelper._ID + "=" + _id, null);
    }


    public JSONArray formatToJson(Cursor cursor)
    {
        //JSONArray resultSet     = new JSONArray();

        JSONArray resultSet = new JSONArray();
        JSONArray resultSet2 = new JSONArray();
        JSONObject rowObject2 = new JSONObject();

        //  resultSet.put();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            try
            {
                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();
                //looping data
                for( int i=0 ;  i< totalColumn ; i++ )
                {
                    /***
                     * TODO
                     * SET THE DATA FROM TABLE*/
                    rowObject2.put("user_id","userteset");
                    rowObject2.put("location_id","Dusun_test");
                    rowObject2.put("form_name","identitas_ibu");
                    rowObject2.put("update_id","identitas_ibu");
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
        return resultSet2;
    }
}
