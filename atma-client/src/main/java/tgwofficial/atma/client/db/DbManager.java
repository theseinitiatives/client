package tgwofficial.atma.client.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tgwofficial.atma.client.AllConstants;
import tgwofficial.atma.client.model.syncmodel.ApiModel;
import tgwofficial.atma.client.model.syncmodel.BankdarahData;
import tgwofficial.atma.client.model.syncmodel.IbuData;
import tgwofficial.atma.client.model.syncmodel.TransportasiData;

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
            ContentValues contentValue2 = new ContentValues();
            ContentValues contentValue = new ContentValues();

            //  contentValue.put(DbHelper.FORM_NAME, model.getform_name());
            contentValue2.put(DbHelper.LOCATION_ID, model.getlocation_id());
            contentValue2.put(DbHelper.USER_ID, model.getuser_id());

            contentValue.put(DbHelper.UPDATE_ID, model.getupdate_id());

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
                    contentValue.put(DbHelper._ID, listIbuData.getId());
                    contentValue.put(DbHelper.NAME, listIbuData.getName());
                    contentValue.put(DbHelper.SPOUSENAME, listIbuData.getSpousename());
                   // contentValue.put(DbHelper.STATUS, listIbuData.getStatus());
                    contentValue.put(DbHelper.DUSUN, listIbuData.getDusun());
                    contentValue.put(DbHelper.HPHT, listIbuData.getHpht());
                    contentValue.put(DbHelper.HTP, listIbuData.getHtp());
                    contentValue.put(DbHelper.TGL_LAHIR, listIbuData.getTglLahir());
                    contentValue.put(DbHelper.TELP, listIbuData.getTelp());
                    /*contentValue.put(DbHelper.TGL_PERSALINAN, listIbuData.getTglPersalinan());
                    contentValue.put(DbHelper.KONDISI_IBU, listIbuData.getKondisiIbu());
                    contentValue.put(DbHelper.KONDISI_ANAK, listIbuData.getKondisiAnak());
                    */
                    contentValue.put(DbHelper.KADER, listIbuData.getKader());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listIbuData.getTimestamp());

                    database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);

                }
            }
            else if(model.getform_name().equals("transportasi")){
                TransportasiData[] transportasiData = gson.fromJson(data_, TransportasiData[].class);
                List<TransportasiData> TransportasiDataList = new ArrayList<>(Arrays.asList(transportasiData));
                for (TransportasiData listTransportasi : TransportasiDataList){
                    contentValue.put(DbHelper._ID, listTransportasi.getId());
                    contentValue.put(DbHelper.NAME, listTransportasi.getName());
                    contentValue.put(DbHelper.Jenis, listTransportasi.getJenis_kendaraan());
                    contentValue.put(DbHelper.Kapasitas, listTransportasi.getKapasitas_kendaraan());
                    contentValue.put(DbHelper.DUSUN, listTransportasi.getGubug());
                    contentValue.put(DbHelper.TELP, listTransportasi.getTelp());
                    contentValue.put(DbHelper.GUBUG, listTransportasi.getGubug());
                    contentValue.put(DbHelper.PROFESI, listTransportasi.getProfesi());
                    contentValue.put(DbHelper.KET, listTransportasi.getKeterangan());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listTransportasi.getTimestamp());
                    database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
                }
                /**TODO
                 * Get data from form transportasi and transform the data using TransportasiData model and put into database**/
            }
            else if(model.getform_name().equals("bank_darah")){
                BankdarahData[] bankdarahData = gson.fromJson(data_, BankdarahData[].class);
                List<BankdarahData> BankDarahDataListed = new ArrayList<>(Arrays.asList(bankdarahData));
                for (BankdarahData listBankDarah : BankDarahDataListed){
                    contentValue.put(DbHelper._ID, listBankDarah.getId());
                    contentValue.put(DbHelper.NAME_PENDONOR, listBankDarah.getName_pendonor());
                    contentValue.put(DbHelper.DUSUN, listBankDarah.getDusun());
                    contentValue.put(DbHelper.GUBUG, listBankDarah.getGubug());
                    contentValue.put(DbHelper.GOL_DARAH, listBankDarah.getGolDarah());
                    contentValue.put(DbHelper.TELP, listBankDarah.getTelp());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listBankDarah.getTimestamp());
                    database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
                }

                /**TODO
                 * Get data from form transportasi and transform the data using BankDarahData model and put into database**/
            }
            /// database.setTransactionSuccessful();
            // database.endTransaction();
        }
    }

    public void insertibu(String mothername, String husbandname,String dobss, String gubugss,
    String hphtss, String htpss,String goldarahss, String kaderss,String notelponss,  String radioStatus2, String resiko,String gubug, String nifas_berakhir) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.NAME, mothername);
        contentValue.put(  DbHelper.SPOUSENAME, husbandname);
        contentValue.put( DbHelper.TGL_LAHIR,dobss);
        contentValue.put( DbHelper.DUSUN,gubugss);
        contentValue.put( DbHelper.HPHT,hphtss);
        contentValue.put( DbHelper.HTP,htpss);
        contentValue.put( DbHelper.GOL_DARAH,goldarahss);
        contentValue.put( DbHelper.GUBUG,gubug);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.RESIKO,resiko);
        contentValue.put( DbHelper.KADER,kaderss);
        contentValue.put( DbHelper.NIFAS_SELESAI,nifas_berakhir);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);
    }

    public void insertStatusPersalinan(String idIbu, String tgl_bersalin,String ibubersalin, String kondisi_ibu, String kondisi_anak){
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.STATUS_BERSALIN,ibubersalin);
        contentValue.put( DbHelper.TGL_PERSALINAN,tgl_bersalin);
        contentValue.put( DbHelper.KONDISI_ANAK,kondisi_anak);
        contentValue.put( DbHelper.KONDISI_IBU,kondisi_ibu);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_PERSALINAN, null, contentValue);
    }

    public void updateIbu(String _id, String mothername, String husbandname,String dobss, String gubugss,
                          String hphtss, String htpss,String goldarahss, String kaderss,String notelponss, String radioStatus2,  String resiko,String gubug,String nifas_berakhir) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.NAME, mothername);
        contentValue.put(  DbHelper.SPOUSENAME, husbandname);
        contentValue.put( DbHelper.TGL_LAHIR,dobss);
        contentValue.put( DbHelper.DUSUN,gubugss);
        contentValue.put( DbHelper.HPHT,hphtss);
        contentValue.put( DbHelper.HTP,htpss);
        contentValue.put( DbHelper.GOL_DARAH,goldarahss);
        contentValue.put( DbHelper.GUBUG,gubug);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.RESIKO,resiko);
        contentValue.put( DbHelper.KADER,kaderss);
        contentValue.put( DbHelper.NIFAS_SELESAI,nifas_berakhir);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_IBU, contentValue,"_id = ?",new String[]{_id});
    }

    public Cursor fetchIbu(String searchTerm, String orderByASCDESC) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                DbHelper.DUSUN,
                DbHelper.GUBUG,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,
                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.RESIKO,
                DbHelper.NIFAS_SELESAI,
                DbHelper.ALASAN,
               /* DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,*/
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.NAME+" LIKE '%"+searchTerm+"%' AND "+DbHelper.NIFAS_SELESAI+"!='ya'", selectionArgs, groupBy, having, orderByASCDESC);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.NIFAS_SELESAI+"!='ya'", selectionArgs, groupBy, having, orderBy);
        clearClause();
        return c;
    }
    public Cursor fetchUnSyncIbu() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                DbHelper.DUSUN,
                DbHelper.GUBUG,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,
                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.RESIKO,
                DbHelper.NIFAS_SELESAI,
                DbHelper.ALASAN,
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DbHelper.UPDATE_ID,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.IS_SEND +"!=1", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchTrans(String searchTerm, String orderByASCDESC) {
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
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.NAME+" LIKE '%"+searchTerm+"%'", selectionArgs, groupBy, having, orderByASCDESC);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_TRANS, columns, selection, selectionArgs, groupBy, having, orderBy);
        clearClause();
        return c;
    }
    public Cursor fetchunSyncTrans() {
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
        Cursor c=null;


            c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchBankDarah(String searchTerm, String orderByASCDESC) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DbHelper.GUBUG,
                DbHelper.GOL_DARAH,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper.NAME_PENDONOR+" LIKE '%"+searchTerm+"%'", selectionArgs, groupBy, having, orderByASCDESC);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_BANK, columns, selection, selectionArgs, groupBy, having, orderBy);
        clearClause();
        return c;


    }
    public Cursor fetchUnsyncBankDarah() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DbHelper.GUBUG,
                DbHelper.GOL_DARAH,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;
            c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }

        return c;


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


    public void insertbankdarah(String donor,String text_gubug,String text_dusun,String notelponss, String radioStatus, String radioStatus2) {

        ContentValues contentValue = new ContentValues();

        contentValue.put( DbHelper.NAME_PENDONOR,donor);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.DUSUN,text_dusun);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.GOL_DARAH,radioStatus2);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
    }

    public void updatebankdarah(String id,String donor,String text_gubug,String text_dusun,String notelponss, String radioStatus, String radioStatus2) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME_PENDONOR,donor);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.DUSUN,text_dusun);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.GOL_DARAH,radioStatus2);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_BANK, contentValue,"id = ?",new String[]{id});
    }

    public void insertbanktransportasi(String text_pemiliks, String jenis,String text_nohp, String text_gubug, String text_kapasitass, String text_dusuns, String text_profesis, String text_kets) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,text_pemiliks);
        contentValue.put( DbHelper.TELP,text_nohp);
        contentValue.put( DbHelper.Jenis,jenis);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.Kapasitas,text_kapasitass);
        contentValue.put( DbHelper.DUSUN,text_dusuns);
        contentValue.put( DbHelper.PROFESI,text_profesis);
        contentValue.put( DbHelper.KET,text_kets);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
    }
    public void insertRencanaPersalinan(String idIbu, String namaDonor, String txt_tempatBersalin, String txt_penolognPersalinan, String txt_pendampingPersalinan, String txt_hubunganPemilik, String txt_hubunganPendonor, String namaTransportasi) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.NAME_PENDONOR,namaDonor);
        contentValue.put( DbHelper.TEMPAT_PERSALINAN,txt_tempatBersalin);
        contentValue.put( DbHelper.PENOLONG_PERSALINAN,txt_penolognPersalinan);
        contentValue.put( DbHelper.PENDAMPING_PERSALINAN,txt_pendampingPersalinan);
        contentValue.put( DbHelper.HUBUNGAN_DENGAN_IBU,txt_hubunganPemilik);
        contentValue.put( DbHelper.HUBUNGAN_PENDONOR_IBU,txt_hubunganPendonor);
        contentValue.put( DbHelper.NAME_PEMILIK,namaTransportasi);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_RENCANA, null, contentValue);
    }

    public void updatebanktransportasi(String id,String text_pemiliks, String jenis,String text_nohp, String text_gubug, String text_kapasitass, String text_dusuns, String text_profesis, String text_kets) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,text_pemiliks);
        contentValue.put( DbHelper.TELP,text_nohp);
        contentValue.put( DbHelper.Jenis,jenis);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.Kapasitas,text_kapasitass);
        contentValue.put( DbHelper.DUSUN,text_dusuns);
        contentValue.put( DbHelper.PROFESI,text_profesis);
        contentValue.put( DbHelper.KET,text_kets);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_TRANS, contentValue,DbHelper._ID+" = ?",new String[]{id});
    }

    public void insertUserData(String id,String username,String password, String email,String first_name, String last_name, String company, String phone, String groups,
                               String location_id, String location_name, String location_tag_id, String parent_location) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.PERSON_ID,id);
        contentValue.put( DbHelper.USERNAME,username);
        contentValue.put( DbHelper.PASSWORD,password);
        contentValue.put( DbHelper.EMAIL,email);
        contentValue.put( DbHelper.FIRST_NAME,first_name);
        contentValue.put( DbHelper.LAST_NAME,last_name);
        contentValue.put( DbHelper.COMPANY,company);
        contentValue.put( DbHelper.PHONE,phone);
        contentValue.put( DbHelper.GROUPS,groups);
        contentValue.put( DbHelper.LOCATION_ID,location_id);
        contentValue.put( DbHelper.LOCATION_NAME,location_name);
        contentValue.put( DbHelper.LOCATION_TAG_ID,location_tag_id);
        contentValue.put( DbHelper.PARENT_LOCATION,parent_location);
        database.insert(DbHelper.TABLE_NAME_USER, null, contentValue);
    }
    public void insertLocationTree(String location_id, String location_name, String location_tag_id, String parent_location) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.LOCATION_ID,location_id);
        contentValue.put( DbHelper.LOCATION_NAME,location_name);
        contentValue.put( DbHelper.LOCATION_TAG_ID,location_tag_id);
        contentValue.put( DbHelper.PARENT_LOCATION,parent_location);
        database.insert(DbHelper.TABLE_LOCATION_TREE, null, contentValue);
    }

    public void insertKader(String name, String dusun, String hp) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,name);
        contentValue.put( DbHelper.DUSUN,dusun);
        contentValue.put( DbHelper.TELP,hp);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_KADER, null, contentValue);
    }
    public Cursor fetchKader(){
        clearClause();
        return database.query(DbHelper.KADER,DbHelper.KADER_VAR,selection,selectionArgs,groupBy,having,orderBy);
    }

    public Cursor fetchUserData(){
        clearClause();
        return database.query(DbHelper.TABLE_NAME_USER,DbHelper.USER_VARIABLE,selection,selectionArgs,groupBy,having,orderBy);
    }

    public Cursor fetchUserCredential(){
        clearClause();
        String[]var = {"username","password"};
        return database.query(DbHelper.TABLE_NAME_USER,var,selection,selectionArgs,groupBy,having,orderBy);
    }
    public Cursor fetchLocationTree(){
        String[]variable = new String[]{
                DbHelper.LOCATION_ID,
                DbHelper.LOCATION_NAME,
                DbHelper.LOCATION_TAG_ID,
                DbHelper.PARENT_LOCATION
        };
        return database.query(DbHelper.TABLE_LOCATION_TREE,variable,selection,selectionArgs,groupBy,having,orderBy);
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

                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.RESIKO,
                DbHelper.NIFAS_SELESAI,
                DbHelper.ALASAN,
               /* DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,*/
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper._ID +"="+id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchRencanaPersalinan(String id) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.ID_IBU,
                DbHelper.PENOLONG_PERSALINAN,
                DbHelper.TEMPAT_PERSALINAN,
                DbHelper.PENDAMPING_PERSALINAN,
                DbHelper.HUBUNGAN_DENGAN_IBU,
                DbHelper.NAME_PEMILIK,
                DbHelper.NAME_PENDONOR,
                DbHelper.HUBUNGAN_PENDONOR_IBU,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_RENCANA, columns, DbHelper.ID_IBU +"="+id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }

    public Cursor fetchstatuspersalinan(String id) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.ID_IBU,
                DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_PERSALINAN, columns, DbHelper.ID_IBU +"="+id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }
    public Cursor fetchnamapemilik() {
        String[] columns = new String[] {
                DbHelper.NAME};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchnamaDonor() {
        String[] columns = new String[] {
                DbHelper.NAME_PENDONOR};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
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


    public void updateFlagIbu() {
        ContentValues contentValue = new ContentValues();

        contentValue.put( DbHelper.IS_SEND,"1");
        contentValue.put( DbHelper.IS_SYNC,"1");
        database.update(DbHelper.TABLE_NAME_IBU, contentValue,DbHelper.IS_SEND+" = 0",null);
    }
    public void updateFlagTrans() {
        ContentValues contentValue = new ContentValues();

        contentValue.put( DbHelper.IS_SEND,"1");
        contentValue.put( DbHelper.IS_SYNC,"1");
        database.update(DbHelper.TABLE_NAME_TRANS, contentValue,DbHelper.IS_SEND+" = 0",null);
    }
    public void updateFlagBank() {
        ContentValues contentValue = new ContentValues();

        contentValue.put( DbHelper.IS_SEND,"1");
        contentValue.put( DbHelper.IS_SYNC,"1");
        database.update(DbHelper.TABLE_NAME_BANK, contentValue,DbHelper.IS_SEND+" = 0",null);
    }


    public void closeIbu(String _id, String status_, String alasans) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.NIFAS_SELESAI, status_);
        contentValue.put(  DbHelper.ALASAN, alasans);
        contentValue.put( DbHelper.UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_IBU, contentValue,"_id = ?",new String[]{_id});

    }
}