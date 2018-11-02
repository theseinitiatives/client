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

import tgwofficial.atma.client.model.ApiModel;
import tgwofficial.atma.client.model.IbuData;

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

    public void saveTodb2(List<ApiModel>  DataList, Integer statusCode) {

        Gson gson = new GsonBuilder().create();

        for(ApiModel model : DataList) {
            ContentValues contentValue = new ContentValues();
            contentValue.put(DbHelper.UPDATE_ID, model.getupdate_id());
            //  contentValue.put(DbHelper.FORM_NAME, model.getform_name());
            contentValue.put(DbHelper.LOCATION_ID, model.getlocation_id());
            contentValue.put(DbHelper.USER_ID, model.getuser_id());

            String data_ = null;

            //check data in server of is mixed between array and list
                    if(model.getData().contains("[")){
                        data_ = model.getData();
                    }
                    else if (model.getData().contains("\"[")){
                        data_ = model.getData().replace("\"[","[").replace("]\"","]");

                    }
                    else{
                        data_ = "["+model.getData()+"]";
                    }
            Log.i("aaaaaaaaaaaaaaaaa" ,data_);
            IbuData[] ibuData = gson.fromJson(data_, IbuData[].class);
            List<IbuData> ibuDataListed = new ArrayList<>(Arrays.asList(ibuData));

            if(model.getform_name().equals("identitas_ibu")) {
                for (IbuData listIbuData : ibuDataListed) {
                    contentValue.put(DbHelper.NAME, listIbuData.getName());
                    contentValue.put(DbHelper.SPOUSENAME, listIbuData.getSpousename());
                    contentValue.put(DbHelper.STATUS, listIbuData.getStatus());
                    contentValue.put(DbHelper.DUSUN, listIbuData.getDusun());
                    contentValue.put(DbHelper.HPHT, listIbuData.getHpht());
                    contentValue.put(DbHelper.HTP, listIbuData.getHtp());
                    contentValue.put(DbHelper.TGL_LAHIR, listIbuData.getTglLahir());
                    contentValue.put(DbHelper.TELP, listIbuData.getTelp());
                    contentValue.put(DbHelper.TGL_PERSALINAN, listIbuData.getTglPersalinan());
                    contentValue.put(DbHelper.KONDISI_IBU, listIbuData.getKondisiIbu());
                    contentValue.put(DbHelper.KONDISI_ANAK, listIbuData.getKondisiAnak());
                    contentValue.put(DbHelper.KADER, listIbuData.getKader());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listIbuData.getTimestamp());


                    database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);

                }
            }
            else if(model.getform_name().equals("transportasi")){
                /**TODO
                 * Get data from form transportasi and transform the data using TransportasiData model and put into database**/
            }
            else if(model.getform_name().equals("transportasi")){
                /**TODO
                 * Get data from form transportasi and transform the data using BankDarahData model and put into database**/
            }
            /// database.setTransactionSuccessful();
            // database.endTransaction();
        }
    }

    public void insertibu(String mothername, String husbandname,String dobss, String gubugss,
    String hphtss, String htpss,String goldarahss, String kaderss,String notelponss, String radioStatus, String radioStatus2) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.NAME, mothername);
        contentValue.put(  DbHelper.SPOUSENAME, husbandname);
        contentValue.put( DbHelper.TGL_LAHIR,dobss);
        contentValue.put( DbHelper.DUSUN,gubugss);
        contentValue.put( DbHelper.HPHT,hphtss);
        contentValue.put( DbHelper.HTP,htpss);
        contentValue.put( DbHelper.GOL_DARAH,goldarahss);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.KADER,kaderss);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);
    }

    public Cursor fetchIbu(String searchTerm, String orderByASCDESC) {
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
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.NAME+" LIKE '%"+searchTerm+"%'", selectionArgs, groupBy, having, orderByASCDESC);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_IBU, columns, selection, selectionArgs, groupBy, having, orderBy);

        return c;
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
        Cursor cursor = database.query(DbHelper.TABLE_NAME_TRANS, columns, selection, selectionArgs, groupBy, having, orderBy);
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
        Cursor cursor = database.query(DbHelper.TABLE_NAME_BANK, columns, selection, selectionArgs, groupBy, having, orderBy);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        clearClause();
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


    public void insertbankdarah(String mothername,String donor, String notelponss, String radioStatus, String radioStatus2) {

        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,mothername);
        contentValue.put( DbHelper.NAME_PENDONOR,donor);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.GOL_DARAH,radioStatus2);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
    }

    public void insertbanktransportasi(String text_pemiliks, String jenis,String text_gubug, String text_kapasitass, String text_dusuns, String text_profesis, String text_kets) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,text_pemiliks);
        contentValue.put( DbHelper.Jenis,jenis);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.Kapasitas,text_kapasitass);
        contentValue.put( DbHelper.DUSUN,text_dusuns);
        contentValue.put( DbHelper.PROFESI,text_profesis);
        contentValue.put( DbHelper.KET,text_kets);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
    }

    public Cursor fetchdetaildata(String id) {
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
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper._ID +"="+id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    private void clearClause(){
        selection = null;
        selectionArgs = null;
        groupBy = null;
        having = null;
        orderBy = null;
    }

    public void setSelection(String args){
        this.selection = args;
    }
    public void setSelectionArgs(String[] args){
        this.selectionArgs = args;
    }
    public void setGroupBy(String args){
        this.groupBy = args;
    }
    public void setHaving(String args){
        this.having = args;
    }
    public void setOrderBy(String args){
        this.orderBy = args;
    }

    private String selection = null;
    private String selectionArgs[] = null;
    private String groupBy = null;
    private String having = null;
    private String orderBy = null;
}