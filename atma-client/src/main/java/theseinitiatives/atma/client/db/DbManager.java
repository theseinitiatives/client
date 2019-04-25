package theseinitiatives.atma.client.db;

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
import java.util.Random;

import theseinitiatives.atma.client.model.syncmodel.ApiModel;
import theseinitiatives.atma.client.model.syncmodel.BankdarahData;
import theseinitiatives.atma.client.model.syncmodel.IbuData;
import theseinitiatives.atma.client.model.syncmodel.KaderModel;
import theseinitiatives.atma.client.model.syncmodel.RencanaModel;
import theseinitiatives.atma.client.model.syncmodel.StatusModel;
import theseinitiatives.atma.client.model.syncmodel.TransportasiData;

import static theseinitiatives.atma.client.db.DbHelper.DESA;
import static theseinitiatives.atma.client.db.DbHelper.DUSUN;
import static theseinitiatives.atma.client.db.DbHelper.FORM_NAME;
import static theseinitiatives.atma.client.db.DbHelper.KADER_VAR;
import static theseinitiatives.atma.client.db.DbHelper.PENOLONG_LAIN;
import static theseinitiatives.atma.client.db.DbHelper.POSYANDU;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_NAME_BANK;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_NAME_IBU;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_NAME_RENCANA;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_NAME_TRANS;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_PERSALINAN;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_SYNC;
import static theseinitiatives.atma.client.db.DbHelper.TABLE_UPDATEID_SYNC;
import static theseinitiatives.atma.client.db.DbHelper.UNIQUEID;
import static theseinitiatives.atma.client.db.DbHelper.UPDATE_ID;

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
            contentValue.put(DbHelper.LOCATION_ID, model.getlocation_id());
            contentValue.put(DbHelper.USER_ID, model.getuser_id());
            contentValue.put(UPDATE_ID, model.getupdate_id());
           // contentValue.put(DUSUN, model.getDusun());
           // contentValue.put(DESA, model.getDesa());

            insertUpdateIdforPulling(model.getupdate_id(), model.getform_name());
            // inserting to table updateIdsync for checking latest update id
         /*   Long logn = Long.parseLong(model.getupdate_id());

            insertsyncTable(model.getform_name(),logn,model.getDusun(),model.getData(),1,1);
         */
            String data_ = null;

            //check data in server of is mixed between array and list
                    if(model.getData().contains("[")){
                        data_ = model.getData().replace("\\","");
                    }
                    else if (model.getData().contains("\"[")){
                        data_ = model.getData().replace("\"[","[").replace("]\"","]").replace("\\","");

                    }
                    else if (model.getData().contains("\"{")){
                        data_ = model.getData().replace("\"{","[{").replace("}\"","}]").replace("\\","");

                    }
                    else{
                        data_ = "["+model.getData().replace("\\","")+"]";
                    }
            Log.i("aaaaaaaaaaaaaaaaa" ,data_);

            // insert into db
            if(model.getform_name().contains("identitas_ibu")) {
                IbuData[] ibuData = gson.fromJson(data_, IbuData[].class);
                List<IbuData> ibuDataListed = new ArrayList<>(Arrays.asList(ibuData));
                for (IbuData listIbuData : ibuDataListed) {
                  //  contentValue.put(DbHelper._ID, listIbuData.getId());
                    contentValue.put(DbHelper.UNIQUEID, listIbuData.getUnique_id());
                    contentValue.put(DbHelper.NAME, listIbuData.getName());
                    contentValue.put(DbHelper.SPOUSENAME, listIbuData.getSpousename());
                    contentValue.put(DbHelper.GOL_DARAH, listIbuData.getGolDarah());
                    contentValue.put(POSYANDU, listIbuData.getPosyandu());
                    contentValue.put(DUSUN, listIbuData.getDusun());
                    contentValue.put(DbHelper.HPHT, listIbuData.getHpht());
                    contentValue.put(DbHelper.HTP, listIbuData.getHtp());
                    contentValue.put(DbHelper.TGL_LAHIR, listIbuData.getTglLahir());
                    contentValue.put(DbHelper.TELP, listIbuData.getTelp());
                    contentValue.put(DbHelper.NIFAS_SELESAI, listIbuData.getNifas_selesai());
                    contentValue.put(DbHelper.ALASAN, listIbuData.getAlasan());
                    contentValue.put(DbHelper.GUBUG, listIbuData.getGubug());
                    contentValue.put(DbHelper.KADER, listIbuData.getKader());
                   contentValue.put(DbHelper.RESIKO, listIbuData.getResiko());
                    contentValue.put(DbHelper.RESIKO_LAIN, listIbuData.getResiko_lainnya());
                    /*  contentValue.put(DbHelper.UPDATE_ID, listIbuData.getUpdate_id());
                   */ contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listIbuData.getTimestamp());
                    if (model.getform_name().equalsIgnoreCase("identitas_ibu")) {
                        if (getUniqueID(listIbuData.getUnique_id(), "identitas_ibu") == null) {
                            database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);
                        }
                    }
                    else if(model.getform_name().equalsIgnoreCase("identitas_ibu_edit")) {
                       if (getUniqueID(listIbuData.getUnique_id(), "identitas_ibu") != null) {
                            Log.e("UPDATING====DATAIBU", "");
                            database.update(DbHelper.TABLE_NAME_IBU, contentValue, "unique_id = ?", new String[]{listIbuData.getUnique_id()});
                        } //else {
                      //      database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);
                       // }
                    }
                }
            }
            else if(model.getform_name().contains("transportasi")){
                TransportasiData[] transportasiData = gson.fromJson(data_, TransportasiData[].class);
                List<TransportasiData> TransportasiDataList = new ArrayList<>(Arrays.asList(transportasiData));
                for (TransportasiData listTransportasi : TransportasiDataList){
                   // contentValue.put(DbHelper._ID, listTransportasi.getId());
                    contentValue.put(DbHelper.UNIQUEID, listTransportasi.getUniqueId());
                    contentValue.put(DbHelper.NAME, listTransportasi.getName());
                    contentValue.put(DbHelper.Jenis, listTransportasi.getJenis_kendaraan());
                    contentValue.put(DbHelper.Jenis_LAIN, listTransportasi.getJenis_kendaraan_lainnya());
                    contentValue.put(DbHelper.Kapasitas, listTransportasi.getKapasitas_kendaraan());
                    contentValue.put(DUSUN, listTransportasi.getDusun());
                    contentValue.put(DbHelper.TELP, listTransportasi.getTelp());
                    contentValue.put(DbHelper.GUBUG, listTransportasi.getGubug());
                    contentValue.put(DbHelper.PROFESI, listTransportasi.getProfesi());
                    contentValue.put(DbHelper.KET, listTransportasi.getKeterangan());
                   /* contentValue.put(DbHelper.USER_ID, listTransportasi.getUser_id());
                    contentValue.put(DbHelper.LOCATION_ID, listTransportasi.getLocation_id());
                    contentValue.put(DbHelper.UPDATE_ID, listTransportasi.getUpdate_id());
                   */ contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listTransportasi.getTimestamp());
                    if(model.getform_name().equalsIgnoreCase("transportasi_edit")) {
                     //   if (getUniqueID(listTransportasi.getUniqueId(), "transportasi") != null) {
                            Log.e("UPDATING====DATATRANS", listTransportasi.getUniqueId());
                            database.update(DbHelper.TABLE_NAME_TRANS, contentValue, "unique_id = ?", new String[]{listTransportasi.getUniqueId()});
                       // }
                       // database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
                    }
                    else
                        database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
                }

            }
            else if(model.getform_name().contains("bank_darah")){
                BankdarahData[] bankdarahData = gson.fromJson(data_, BankdarahData[].class);
                List<BankdarahData> BankDarahDataListed = new ArrayList<>(Arrays.asList(bankdarahData));
                for (BankdarahData listBankDarah : BankDarahDataListed){
                   // contentValue.put(DbHelper._ID, listBankDarah.getId());
                    contentValue.put(DbHelper.UNIQUEID, listBankDarah.getUniqueId());
                    contentValue.put(DbHelper.NAME_PENDONOR, listBankDarah.getName_pendonor());
                    contentValue.put(DUSUN, listBankDarah.getDusun());
                    contentValue.put(DbHelper.GUBUG, listBankDarah.getGubug());
                    contentValue.put(DbHelper.GOL_DARAH, listBankDarah.getGolDarah());
                    contentValue.put(DbHelper.TELP, listBankDarah.getTelp());
                    contentValue.put(DbHelper.TGL_DONOR, listBankDarah.getTgl_donor());
                   /* contentValue.put(DbHelper.LOCATION_ID, listBankDarah.getLocation_id());
                    contentValue.put(DbHelper.UPDATE_ID, listBankDarah.getUpdate_id());
                   */ contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, listBankDarah.getTimestamp());
                    if(model.getform_name().equalsIgnoreCase("bank_darah_edit")) {
                       // if (getUniqueID(listBankDarah.getUniqueId(), "bank_darah") != null) {
                       //     Log.e("UPDATING====DATAKADER", "");
                            database.update(DbHelper.TABLE_NAME_BANK, contentValue, "unique_id = ?", new String[]{listBankDarah.getUniqueId()});
                      //  }
                      //  database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
                    }
                    else
                        database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
                }

            }
            else if(model.getform_name().contains("rencana_persalinan")){
                RencanaModel[] rencanaModels = gson.fromJson(data_, RencanaModel[].class);
                List<RencanaModel> rencanaModels1 = new ArrayList<>(Arrays.asList(rencanaModels));
                for (RencanaModel rencanaModel : rencanaModels1){

                    contentValue.put(DbHelper.ID_IBU, rencanaModel.getId_ibu());
                    contentValue.put(DbHelper.ID_TRANS, rencanaModel.getId_trans());
                    contentValue.put(DbHelper.ID_DONOR, rencanaModel.getId_donor());
                    contentValue.put(DbHelper.PENOLONG_PERSALINAN, rencanaModel.getPenolong_persalinan());
                    contentValue.put(DbHelper.TEMPAT_PERSALINAN, rencanaModel.getTempat_persalinan());
                    contentValue.put(DbHelper.PENDAMPING_PERSALINAN, rencanaModel.getPendamping_persalinan());
                    contentValue.put(DbHelper.HUBUNGAN_DENGAN_IBU, rencanaModel.getHubungan_ibu());
                    contentValue.put(DbHelper.NAME_PENDONOR, rencanaModel.getName_pendonor());
                    contentValue.put(DbHelper.NAME_PEMILIK, rencanaModel.getPemilik_kendaraan());
                    contentValue.put(DbHelper.HUBUNGAN_PENDONOR_IBU, rencanaModel.getHubungan_pendonor());

                    contentValue.put(DbHelper.PENOLONG_LAIN, rencanaModel.getPenolong_lainnya());
                    contentValue.put(DbHelper.TEMPAT_LAIN, rencanaModel.getTempat_lainnya());
                    contentValue.put(DbHelper.PENDAMPING_LAIN, rencanaModel.getPendamping_lainnya());
                    contentValue.put(DbHelper.HUB_TRANS_LAIN, rencanaModel.getHub_pemiliktrans_lainnya());
                    contentValue.put(DbHelper.HUB_DONOR_LAIN, rencanaModel.getHub_donor_lainnya());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, rencanaModel.getTimestamp());
                    if(model.getform_name().equalsIgnoreCase("rencana_persalinan_edit")) {
                       // if (getIDIbu(rencanaModel.getId_ibu(), "rencana_persalinan") != null) {
                            Log.e("UPDATING====DATAKADER", "");
                            database.update(DbHelper.TABLE_NAME_RENCANA, contentValue, "id_ibu = ?", new String[]{rencanaModel.getId_ibu()});
                       // }
                       // database.insert(DbHelper.TABLE_NAME_RENCANA, null, contentValue);
                    }
                    else
                        database.insert(DbHelper.TABLE_NAME_RENCANA, null, contentValue);
                //    database.insert(DbHelper.TABLE_NAME_RENCANA, null, contentValue);
                }
            }
            else if(model.getform_name().contains("status_persalinan")){
                StatusModel[] statusModels = gson.fromJson(data_, StatusModel[].class);
                List<StatusModel> statusModels1 = new ArrayList<>(Arrays.asList(statusModels));
                for (StatusModel statusModel : statusModels1){
//                    contentValue.put(DbHelper._ID, statusModel.getId());
                    contentValue.put(DbHelper.ID_IBU, statusModel.getId_ibu());
                    contentValue.put(DbHelper.STATUS_BERSALIN, statusModel.getStatus_bersalin());
                    contentValue.put(DbHelper.TGL_PERSALINAN, statusModel.getTgl_persalinan());
                    contentValue.put(DbHelper.KONDISI_IBU, statusModel.getKondisi_ibu());
                    contentValue.put(DbHelper.KONDISI_ANAK, statusModel.getKondisi_anak());
                    contentValue.put(DbHelper.JUMLAHBAYI, statusModel.getJumlah_bayi());
                    contentValue.put(DbHelper.JENISKELAMIN, statusModel.getJenis_kelamin());
                    contentValue.put(DbHelper.KOMPLIKASIIBU, statusModel.getKomplikasi_ibu());
                    contentValue.put(DbHelper.KOMPLIKASIANAK, statusModel.getKomplikasi_anak());
                    contentValue.put(DbHelper.TEMPAT_PERSALINAN, statusModel.getTempat_persalinan());
                    contentValue.put(DbHelper.TEMPAT_LAIN, statusModel.getTempat_lainnya());
                   /* contentValue.put(DbHelper.USER_ID, statusModel.getUser_id());
                    contentValue.put(DbHelper.LOCATION_ID, statusModel.getLocation_id());
                    contentValue.put(DbHelper.UPDATE_ID, statusModel.getUpdate_id());
                   */ contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                    contentValue.put(DbHelper.TIMESTAMP, statusModel.getTimestamp());
                    if(model.getform_name().equalsIgnoreCase("status_persalinan_edit")) {
                       // if (getIDIbu(statusModel.getId_ibu(), "status_persalinan") != null) {
                            Log.e("UPDATING====DATAKADER", "");
                            database.update(DbHelper.TABLE_PERSALINAN, contentValue, "id_ibu = ?", new String[]{statusModel.getId_ibu()});
                       // }
                      //  database.insert(DbHelper.TABLE_PERSALINAN, null, contentValue);
                    }
                    else
                        database.insert(DbHelper.TABLE_PERSALINAN, null, contentValue);
                   // database.insert(DbHelper.TABLE_PERSALINAN, null, contentValue);
                }
            }

            //close ibu and kader
            else if(model.getform_name().contains("kader")){
                Log.e("GETKADER","");
                KaderModel[] KaderModels = gson.fromJson(data_, KaderModel[].class);
                List<KaderModel> kadermodelslist = new ArrayList<>(Arrays.asList(KaderModels));
                for (KaderModel kadermodels : kadermodelslist) {
                    contentValue.put(DbHelper.NAME, kadermodels.getName());
                    contentValue.put(UNIQUEID, kadermodels.getUnique_id());
                    contentValue.put(DUSUN, kadermodels.getDusun());
                    contentValue.put(DbHelper.TELP, kadermodels.getTelp());
                    contentValue.put(DbHelper.USERNAME, kadermodels.getUsername());
                    contentValue.put(DbHelper.PASSWORD, kadermodels.getPassword());
                    contentValue.put(DbHelper.IS_SEND, 1);
                    contentValue.put(DbHelper.IS_SYNC, 1);
                        if (getUniqueID(kadermodels.getUnique_id(), "kader") != null) {
                            Log.e("UPDATING====DATAKADER", "");
                            database.update(DbHelper.TABLE_KADER, contentValue, "unique_id = ?", new String[]{kadermodels.getUnique_id()});
                        }
                        database.insert(DbHelper.TABLE_KADER, null, contentValue);

                }

            }
            //close ibu and kader
            else if(model.getform_name().equals("close_ibu")) {
                IbuData[] ibuData = gson.fromJson(data_, IbuData[].class);
                List<IbuData> ibuDataListed = new ArrayList<>(Arrays.asList(ibuData));
                for (IbuData listIbuData : ibuDataListed) {
                    contentValue.put(DbHelper.UNIQUEID, listIbuData.getUnique_id());
                    contentValue.put(DbHelper.ALASAN, listIbuData.getAlasan());
                    contentValue.put(DbHelper.NIFAS_SELESAI, listIbuData.getNifas_selesai());
                    if(getUniqueID(listIbuData.getUnique_id(),"identitas_ibu")!=null){
                        database.update(DbHelper.TABLE_NAME_IBU, contentValue,"unique_id = ?",new String[]{listIbuData.getUnique_id()});

                    }
                    database.insert(DbHelper.TABLE_CLOSE, null, contentValue);

                }
            }
            /// database.setTransactionSuccessful();
            // database.endTransaction();
        }
    }



    public void insertibu(String uid,String mothername, String husbandname,String dobss, String posyandu, String gubugss,
    String hphtss, String htpss,String goldarahss, String kaderss,String notelponss,  String radioStatus2, String resiko,String resiko_lain,String gubug, String nifas_berakhir, long updateid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.UNIQUEID, uid);
        contentValue.put(  DbHelper.NAME, mothername);
        contentValue.put(  DbHelper.SPOUSENAME, husbandname);
        contentValue.put( DbHelper.TGL_LAHIR,dobss);
        contentValue.put( POSYANDU,posyandu);
        contentValue.put( DUSUN,gubugss);
        contentValue.put( DbHelper.HPHT,hphtss);
        contentValue.put( DbHelper.HTP,htpss);
        contentValue.put( DbHelper.GOL_DARAH,goldarahss);
        contentValue.put( DbHelper.GUBUG,gubug);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.RESIKO,resiko);
        contentValue.put( DbHelper.RESIKO_LAIN,resiko_lain);
        contentValue.put( DbHelper.KADER,kaderss);
        contentValue.put( DbHelper.NIFAS_SELESAI,nifas_berakhir);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateid);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_IBU, null, contentValue);

    }

    public void insertStatusPersalinan(String idIbu, String tgl_bersalin,String ibubersalin, String kondisi_ibu, String kondisi_anak,String jumlahBayis, String jenisKelamins, String komplikasiIbus, String komplikasiAnak, String tempat, String tempatLain, String dusun){
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.STATUS_BERSALIN,ibubersalin);
        contentValue.put( DbHelper.TGL_PERSALINAN,tgl_bersalin);
        contentValue.put( DbHelper.DUSUN,dusun);
        contentValue.put( DbHelper.KONDISI_ANAK,kondisi_anak);
        contentValue.put( DbHelper.KONDISI_IBU,kondisi_ibu);
        contentValue.put( DbHelper.JUMLAHBAYI,jumlahBayis);
        contentValue.put( DbHelper.JENISKELAMIN,jenisKelamins);
        contentValue.put( DbHelper.KOMPLIKASIIBU,komplikasiIbus);
        contentValue.put( DbHelper.KOMPLIKASIANAK,komplikasiAnak);
        contentValue.put( DbHelper.TEMPAT_PERSALINAN,tempat);
        contentValue.put( DbHelper.TEMPAT_LAIN,tempatLain);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        //String jumlahBayis, String jenisKelamins, String komplikasiIbus, String komplikasiAnak, String tempat
        database.insert(TABLE_PERSALINAN, null, contentValue);
    }

    public void updateStatusPersalinan(String idIbu, String tgl_bersalin,String ibubersalin, String kondisi_ibu, String kondisi_anak,String jumlahBayis, String jenisKelamins, String komplikasiIbus, String komplikasiAnak, String tempat,String tempatLain, String dusun){
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.STATUS_BERSALIN,ibubersalin);
        contentValue.put( DbHelper.TGL_PERSALINAN,tgl_bersalin);
        contentValue.put( DbHelper.DUSUN,dusun);
        contentValue.put( DbHelper.KONDISI_ANAK,kondisi_anak);
        contentValue.put( DbHelper.KONDISI_IBU,kondisi_ibu);
        contentValue.put( DbHelper.JUMLAHBAYI,jumlahBayis);
        contentValue.put( DbHelper.JENISKELAMIN,jenisKelamins);
        contentValue.put( DbHelper.KOMPLIKASIIBU,komplikasiIbus);
        contentValue.put( DbHelper.KOMPLIKASIANAK,komplikasiAnak);
        contentValue.put( DbHelper.TEMPAT_PERSALINAN,tempat);
        contentValue.put( DbHelper.TEMPAT_LAIN,tempatLain);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        //String jumlahBayis, String jenisKelamins, String komplikasiIbus, String komplikasiAnak, String tempat
        database.update(TABLE_PERSALINAN, contentValue,DbHelper.ID_IBU+" = ?",new String[]{idIbu});
    }

    public void updateIbu(String _id, String mothername, String husbandname,String dobss, String posyandu, String gubugss,
                          String hphtss, String htpss,String goldarahss, String kaderss,String notelponss, String radioStatus2,  String resiko,String resikolain,String gubug,String nifas_berakhir, long updateid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.NAME, mothername);
        contentValue.put(  DbHelper.SPOUSENAME, husbandname);
        contentValue.put( DbHelper.TGL_LAHIR,dobss);
        contentValue.put( POSYANDU,posyandu);
        contentValue.put( DUSUN,gubugss);
        contentValue.put( DbHelper.HPHT,hphtss);
        contentValue.put( DbHelper.HTP,htpss);
        contentValue.put( DbHelper.GOL_DARAH,goldarahss);
        contentValue.put( DbHelper.GUBUG,gubug);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.RESIKO,resiko);
        contentValue.put( DbHelper.RESIKO_LAIN,resikolain);
        contentValue.put( DbHelper.KADER,kaderss);
        contentValue.put( DbHelper.NIFAS_SELESAI,nifas_berakhir);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateid);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_IBU, contentValue,"_id = ?",new String[]{_id});
    }

    public Cursor fetchIbu(String searchTerm, String orderByASCDESC) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.UNIQUEID,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                POSYANDU,
                DUSUN,
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
            c = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.NAME+" LIKE '%"+searchTerm+"%' AND "+DbHelper.NIFAS_SELESAI+"!='ya'", selectionArgs, groupBy, having, orderByASCDESC, limit);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_IBU, columns, (selection == null ? "" : selection+" AND ") + DbHelper.NIFAS_SELESAI+"!='ya'", selectionArgs, groupBy, having, orderBy, limit);
        clearClause();
        return c;
    }
    public Cursor fetchUnSyncIbu() {
        String[] columns = new String[] { DbHelper.UNIQUEID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                POSYANDU,
                DUSUN,
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
                UPDATE_ID,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper.IS_SEND +"!=1", null, null, null, orderBy, limit);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchUnSyncForm() {
        String[] columns = new String[] {
                // + USER_ID + " TEXT , "
                //            + LOCATION_ID + " TEXT , "
                //            + UPDATE_ID + " TEXT , "
                //            + DATA + " TEXT , "
                //            + FORM_NAME + " TEXT , "
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DUSUN,
                DbHelper.DESA,
                DbHelper.POSYANDU,
                DbHelper.UPDATE_ID,
                DbHelper.FORM_NAME,
                DbHelper.DATA
                 };
        Cursor cursor = database.query(DbHelper.TABLE_SYNC, columns, DbHelper.IS_SEND +"!=1", null, null, null, orderBy);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



    public Cursor fetchStatusPersalinan(String uniqueId){
        String[] col = new String[]{
                DbHelper.ID_IBU,
                DbHelper.STATUS_BERSALIN
        };
        Cursor cursor = database.query(DbHelper.TABLE_PERSALINAN, col, DbHelper.ID_IBU +" = ?" , new String[]{uniqueId}, groupBy, having, orderBy, limit);
        return cursor;
    }

    public Cursor fetchTrans(String searchTerm, String orderByASCDESC) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.UNIQUEID,
                DbHelper.Jenis,
                DbHelper.Jenis_LAIN,
                DbHelper.Kapasitas,
                DbHelper.TELP,
                DUSUN,
                DbHelper.GUBUG,
                DbHelper.PROFESI,
                DbHelper.KET,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.NAME+" LIKE '%"+searchTerm+"%'", selectionArgs, "unique_id", having, orderByASCDESC, limit);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_TRANS, columns, selection, selectionArgs, "unique_id", having, orderBy, limit);
        clearClause();
        return c;
    }

    public Cursor fetchKaders(String searchTerm, String orderByASCDESC) {

        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_KADER, KADER_VAR, DbHelper.NAME+" LIKE '%"+searchTerm+"%'", selectionArgs, UNIQUEID, having, orderByASCDESC, limit);
            return c;
        }

        c = database.query(DbHelper.TABLE_KADER, KADER_VAR, selection, selectionArgs, UNIQUEID, having, orderBy, limit);
        clearClause();
        return c;
    }
    public Cursor fetchunSyncTrans() {
        String[] columns = new String[] { DbHelper.UNIQUEID,
                UPDATE_ID,
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DbHelper.NAME,
                DbHelper.Jenis,
                DbHelper.Kapasitas,
                DbHelper.TELP,
                DUSUN,
                DbHelper.GUBUG,
                DbHelper.PROFESI,
                DbHelper.KET,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;


            c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchBankDarah(String searchTerm, String orderByASCDESC) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DbHelper.UNIQUEID,
                DUSUN,
                DbHelper.GUBUG,
                DbHelper.GOL_DARAH,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0) {
            c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper.NAME_PENDONOR+" LIKE '%"+searchTerm+"%'", selectionArgs, "unique_id", having, orderByASCDESC, limit);
            return c;
        }

        c = database.query(DbHelper.TABLE_NAME_BANK, columns, selection, selectionArgs, "unique_id", having, orderBy, limit);
        clearClause();
        return c;


    }
    public Cursor fetchBankDarahUpdate(String id) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DUSUN,
                DbHelper.GUBUG,
                DbHelper.GOL_DARAH,
                DbHelper.TGL_DONOR,
                DbHelper.UNIQUEID,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper._ID +"="+id, selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }
    public Cursor fetchUnsyncBankDarah() {
        String[] columns = new String[] { DbHelper.UNIQUEID,
                UPDATE_ID,
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DbHelper.NAME_PENDONOR,
                DbHelper.STATUS,
                DbHelper.GUBUG,
                DbHelper.GOL_DARAH,
                DbHelper.TELP,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;
            c = database.query(DbHelper.TABLE_NAME_BANK, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }

        return c;


    }

    public Cursor fetchUnsyncRencanaPersalinan() {
        String[] columns = new String[] { UPDATE_ID,
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DbHelper.ID_IBU,
                DbHelper.ID_TRANS,
                DbHelper.PENOLONG_PERSALINAN,
                DbHelper.TEMPAT_PERSALINAN,
                DbHelper.PENDAMPING_PERSALINAN,
                DbHelper.HUBUNGAN_DENGAN_IBU,
                DbHelper.NAME_PENDONOR,
                DbHelper.NAME_PEMILIK,
                DbHelper.HUBUNGAN_PENDONOR_IBU,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;
        c = database.query(TABLE_NAME_RENCANA, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }

        return c;


    }

    public Cursor fetchUnsyncStatusPersalinan() {
        String[] columns = new String[] { UPDATE_ID,
                DbHelper.USER_ID,
                DbHelper.LOCATION_ID,
                DbHelper.ID_IBU,
                DbHelper.STATUS_BERSALIN,
                DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.JUMLAHBAYI,
                DbHelper.JENISKELAMIN,
                DbHelper.KOMPLIKASIIBU,
                DbHelper.KOMPLIKASIANAK,
                DbHelper.TEMPAT_PERSALINAN,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor c=null;
        c = database.query(TABLE_PERSALINAN, columns, DbHelper.IS_SEND +"!=1", selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }

        return c;


    }



    public void delete(long _id) {
        database.delete(DbHelper.TABLE_NAME_IBU, DbHelper._ID + "=" + _id, null);
    }


    public void insertbankdarah(String uuid,String donor,String text_gubug,String text_dusun,String notelponss, String radioStatus, String radioStatus2, String tgldonor,long uodateid) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.UNIQUEID, uuid);
        contentValue.put( DbHelper.NAME_PENDONOR,donor);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DUSUN,text_dusun);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.GOL_DARAH,radioStatus2);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.TGL_DONOR,tgldonor);
        contentValue.put( UPDATE_ID,uodateid);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_BANK, null, contentValue);
    }

    public void updatebankdarah(String id,String donor,String text_gubug,String text_dusun,String notelponss, String radioStatus, String radioStatus2, String tgldonor, long uodateid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME_PENDONOR,donor);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DUSUN,text_dusun);
        contentValue.put( DbHelper.STATUS,radioStatus);
        contentValue.put( DbHelper.GOL_DARAH,radioStatus2);
        contentValue.put( DbHelper.TELP,notelponss);
        contentValue.put( DbHelper.TGL_DONOR,tgldonor);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,uodateid);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_BANK, contentValue,"_id = ?",new String[]{id});
    }

    public void insertbanktransportasi(String uuid,String text_pemiliks, String jenis,String kend_lain, String text_nohp, String text_gubug, String text_kapasitass, String text_dusuns, String text_profesis, String text_kets,long updateId) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.UNIQUEID, uuid);
        contentValue.put( DbHelper.NAME,text_pemiliks);
        contentValue.put( DbHelper.TELP,text_nohp);
        contentValue.put( DbHelper.Jenis,jenis);
        contentValue.put( DbHelper.Jenis_LAIN,kend_lain);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.Kapasitas,text_kapasitass);
        contentValue.put( DUSUN,text_dusuns);
        contentValue.put( DbHelper.PROFESI,text_profesis);
        contentValue.put( DbHelper.KET,text_kets);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateId);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_NAME_TRANS, null, contentValue);
    }
    public void insertRencanaPersalinan(String idIbu, String id_trans, String id_donor, String namaDonor, String txt_tempatBersalin, String txt_penolognPersalinan, String txt_pendampingPersalinan, String txt_hubunganPemilik, String txt_hubunganPendonor, String namaTransportasi,long updateId,
                                        String penolongLain,
                                        String tempatLain,
                                        String pendampingLain,
                                        String hubTransLain,
                                        String hubDonorLain, String dusun) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.ID_TRANS,id_trans);
        contentValue.put( DbHelper.ID_DONOR,id_donor);
        contentValue.put( DbHelper.NAME_PENDONOR,namaDonor);
        contentValue.put( DbHelper.DUSUN,dusun);
        contentValue.put( DbHelper.TEMPAT_PERSALINAN,txt_tempatBersalin);
        contentValue.put( DbHelper.PENOLONG_PERSALINAN,txt_penolognPersalinan);
        contentValue.put( DbHelper.PENDAMPING_PERSALINAN,txt_pendampingPersalinan);
        contentValue.put( DbHelper.HUBUNGAN_DENGAN_IBU,txt_hubunganPemilik);
        contentValue.put( DbHelper.HUBUNGAN_PENDONOR_IBU,txt_hubunganPendonor);

        contentValue.put( DbHelper.PENOLONG_LAIN,penolongLain);
        contentValue.put( DbHelper.TEMPAT_LAIN,tempatLain);
        contentValue.put( DbHelper.PENDAMPING_LAIN,pendampingLain);
        contentValue.put( DbHelper.HUB_TRANS_LAIN,hubTransLain);
        contentValue.put( DbHelper.HUB_DONOR_LAIN,hubDonorLain);

        contentValue.put( DbHelper.NAME_PEMILIK,namaTransportasi);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateId);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(TABLE_NAME_RENCANA, null, contentValue);
    }
    public void updateRencanaPersalinan(String idIbu, String id_trans, String id_donor,String namaDonor, String txt_tempatBersalin, String txt_penolognPersalinan, String txt_pendampingPersalinan, String txt_hubunganPemilik, String txt_hubunganPendonor, String namaTransportasi,long updateId,
                                         String penolongLain,
                                        String tempatLain,
                                        String pendampingLain,
                                        String hubTransLain,
                                        String hubDonorLain,
                                        String dusun) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.ID_IBU,idIbu);
        contentValue.put( DbHelper.ID_TRANS,id_trans);
        contentValue.put( DbHelper.ID_DONOR,id_donor);
        contentValue.put( DbHelper.NAME_PENDONOR,namaDonor);
        contentValue.put( DbHelper.DUSUN,dusun);
        contentValue.put( DbHelper.TEMPAT_PERSALINAN,txt_tempatBersalin);
        contentValue.put( DbHelper.PENOLONG_PERSALINAN,txt_penolognPersalinan);
        contentValue.put( DbHelper.PENDAMPING_PERSALINAN,txt_pendampingPersalinan);
        contentValue.put( DbHelper.HUBUNGAN_DENGAN_IBU,txt_hubunganPemilik);
        contentValue.put( DbHelper.HUBUNGAN_PENDONOR_IBU,txt_hubunganPendonor);
        contentValue.put( DbHelper.PENOLONG_LAIN,penolongLain);
        contentValue.put( DbHelper.TEMPAT_LAIN,tempatLain);
        contentValue.put( DbHelper.PENDAMPING_LAIN,pendampingLain);
        contentValue.put( DbHelper.HUB_TRANS_LAIN,hubTransLain);
        contentValue.put( DbHelper.HUB_DONOR_LAIN,hubDonorLain);

        contentValue.put( DbHelper.NAME_PEMILIK,namaTransportasi);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateId);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");

        database.update(DbHelper.TABLE_NAME_RENCANA, contentValue,"id_ibu = ?",new String[]{idIbu});
    }
    public void updatebanktransportasi(String id,String text_pemiliks, String jenis, String kend_lain, String text_nohp, String text_gubug, String text_kapasitass, String text_dusuns, String text_profesis, String text_kets, long updateid) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,text_pemiliks);
        contentValue.put( DbHelper.TELP,text_nohp);
        contentValue.put( DbHelper.Jenis,jenis);
        contentValue.put( DbHelper.Jenis_LAIN,kend_lain);
        contentValue.put( DbHelper.GUBUG,text_gubug);
        contentValue.put( DbHelper.Kapasitas,text_kapasitass);
        contentValue.put( DUSUN,text_dusuns);
        contentValue.put( DbHelper.PROFESI,text_profesis);
        contentValue.put( DbHelper.KET,text_kets);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,updateid);
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_TRANS, contentValue,DbHelper._ID+" = ?",new String[]{id});
    }

    public void insertUserData(String id,String username,String password, String email,String first_name, String last_name, String company, String phone, String groups,
                               String location_id, String location_name, String location_tag_id, String parent_location, String location_tag) {
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
        contentValue.put( DbHelper.LOCATION_TAG,location_tag);
        database.insert(DbHelper.TABLE_NAME_USER, null, contentValue);
    }
    public void insertLocationTree(String location_id, String location_name, String location_tag_id, String parent_location, String location_tag) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.LOCATION_ID,location_id);
        contentValue.put( DbHelper.LOCATION_NAME,location_name);
        contentValue.put( DbHelper.LOCATION_TAG_ID,location_tag_id);
        contentValue.put( DbHelper.PARENT_LOCATION,parent_location);
        contentValue.put( DbHelper.LOCATION_TAG,location_tag);
        database.insert(DbHelper.TABLE_LOCATION_TREE, null, contentValue);
    }

    public void insertKader(String uuid,String name, String posyandu, String dusun, String hp, String username, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.NAME,name);
        contentValue.put( DbHelper.UNIQUEID,uuid);
        contentValue.put( POSYANDU,posyandu);
        contentValue.put( DUSUN,dusun);
        contentValue.put( DbHelper.TELP,hp);
        contentValue.put( DbHelper.USERNAME,username);
        contentValue.put( DbHelper.PASSWORD,password);
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.insert(DbHelper.TABLE_KADER, null, contentValue);
    }

    public void insertsyncTable(String formName, Long updateId, String posyandu, String dusun, String data, int issend, int issync) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName()); //get from user location
        contentValue.put( DbHelper.DESA,getDesa()); // get from location tree with tag id = 5
        contentValue.put( DbHelper.POSYANDU,posyandu); // get from location tree with tag id = 5
        contentValue.put( DUSUN,dusun); // get from location tree with tag id = 5
        contentValue.put( DbHelper.DATA,data);
        contentValue.put( UPDATE_ID,updateId);
        contentValue.put( FORM_NAME,formName);
        contentValue.put( DbHelper.IS_SEND,issend);
        contentValue.put( DbHelper.IS_SYNC,issync);
        database.insert(DbHelper.TABLE_SYNC, null, contentValue);
    }


    public void insertsyncTable(String formName, Long updateId, String dusun, String data, int issend, int issync) {
        ContentValues contentValue = new ContentValues();
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName()); //get from user location
        contentValue.put( DbHelper.DESA,getDesa()); // get from location tree with tag id = 5
        contentValue.put( DbHelper.POSYANDU,getPosyandu(dusun)); // get from location tree with tag id = 5
        contentValue.put( DUSUN,dusun); // get from location tree with tag id = 5
        contentValue.put( DbHelper.DATA,data);
        contentValue.put( UPDATE_ID,updateId);
        contentValue.put( FORM_NAME,formName);
        contentValue.put( DbHelper.IS_SEND,issend);
        contentValue.put( DbHelper.IS_SYNC,issync);
        database.insert(DbHelper.TABLE_SYNC, null, contentValue);
    }

    private String getDesa() {
        setSelection("LOCATION_TAG_ID =5");
        Cursor cursor = fetchLocationTree();
        cursor.moveToFirst();
        String locName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        //close();
        return locName;
    }

    private String getPosyandu(String dusun){
        setSelection(DbHelper.LOCATION_NAME+" = '"+dusun+"' AND "+DbHelper.LOCATION_TAG+"='dusun'");
        Cursor cursor = fetchLocationTree();
        cursor.moveToFirst();
        String parentId = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.PARENT_LOCATION));
        Log.d("DbManager", "getPosyandu: parentId="+parentId);
        setSelection("LOCATION_ID ="+parentId);
        cursor = fetchLocationTree();
        cursor.moveToFirst();
        String locName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        Log.d("DbManager", "getPosyandu: locName="+locName);
        return locName;

    }

    public Cursor fetchKader(){
        clearClause();
        return database.query(DbHelper.KADER,DbHelper.KADER_VAR,selection,selectionArgs,UNIQUEID,having,orderBy, limit);
    }

    public Cursor fetchUserData(){
        clearClause();
        return database.query(DbHelper.TABLE_NAME_USER,DbHelper.USER_VARIABLE,selection,selectionArgs,groupBy,having,orderBy, limit);
    }

    public Cursor fetchUserCredential(){
        clearClause();
        String[]var = {"username","password"};
        return database.query(DbHelper.TABLE_NAME_USER,var,selection,selectionArgs,groupBy,having,orderBy, limit);
    }
    public Cursor fetchLocationTree(){
        String[]variable = new String[]{
                DbHelper.LOCATION_ID,
                DbHelper.LOCATION_NAME,
                DbHelper.LOCATION_TAG_ID,
                DbHelper.PARENT_LOCATION,
                DbHelper.LOCATION_TAG
        };
        return database.query(DbHelper.TABLE_LOCATION_TREE,variable,selection,selectionArgs,groupBy,having,orderBy, limit);
    }

    public Cursor fetchdetaildata(String id) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.UNIQUEID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.TGL_LAHIR,
                POSYANDU,
                DUSUN,
                DbHelper.GUBUG,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,

                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.RESIKO,
                DbHelper.RESIKO_LAIN,
                DbHelper.NIFAS_SELESAI,
                DbHelper.ALASAN,
                DbHelper.USER_ID,
               /* DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,*/
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper._ID +"='"+id+"'", null, null, null, null);
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
                DbHelper.PENOLONG_LAIN,
                DbHelper.TEMPAT_LAIN,
                DbHelper.PENDAMPING_LAIN,
                DbHelper.HUB_TRANS_LAIN,
                DbHelper.HUB_DONOR_LAIN,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(TABLE_NAME_RENCANA, columns, DbHelper.ID_IBU +"='"+id+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }

    public Cursor fetchstatuspersalinan(String id) {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.ID_IBU,
                DbHelper.TGL_PERSALINAN,
                DbHelper.STATUS_BERSALIN,
                DbHelper.TEMPAT_PERSALINAN,
                DbHelper.TEMPAT_LAIN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.JENISKELAMIN,
                DbHelper.JUMLAHBAYI,
                DbHelper.KOMPLIKASIIBU,
                DbHelper.KOMPLIKASIANAK,
                DbHelper.IS_SEND,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(TABLE_PERSALINAN, columns, DbHelper.ID_IBU +"='"+id+"'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }
    public Cursor fetchnamapemilik() {
        String[] columns = new String[] {
                DbHelper.NAME};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_TRANS, columns, null, selectionArgs, UNIQUEID, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchnamaDonor() {
        String[] columns = new String[] {
                DbHelper.NAME_PENDONOR};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_BANK, columns, null, selectionArgs, UNIQUEID, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchuniqueId(String id) {
        String[] columns = new String[] {
                DbHelper.UNIQUEID,
                DbHelper.DUSUN};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_IBU, columns, DbHelper._ID +"="+id, selectionArgs, groupBy, having, orderBy, limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor fetchuniqueId(String id, String tablename) {
        String[] columns = new String[] {
                DbHelper.UNIQUEID};
        Cursor c=null;
        c = database.query(tablename, columns, DbHelper.UNIQUEID +"='"+id+"'", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor fetchIdIbu(String id, String tablename) {
        String[] columns = new String[] {
                DbHelper.ID_IBU};
        Cursor c=null;
        c = database.query(tablename, columns, DbHelper.ID_IBU +"='"+id+"'", selectionArgs, groupBy, having, orderBy);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void clearClause(){
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
    public void setLimit(String args){
        this.limit = args;
    }

    private String selection = null;
    private String selectionArgs[] = null;
    private String groupBy = null;
    private String having = null;
    private String orderBy = null;
    private String limit = null;


    public void updateFlagSycn() {
        ContentValues contentValue = new ContentValues();

        contentValue.put( DbHelper.IS_SEND,"1");
        contentValue.put( DbHelper.IS_SYNC,"1");
        database.update(DbHelper.TABLE_SYNC, contentValue,DbHelper.IS_SEND+" = 0",null);
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
        contentValue.put( DbHelper.USER_ID,getusername());
        contentValue.put( DbHelper.LOCATION_ID,getlocName());
        contentValue.put( UPDATE_ID,System.currentTimeMillis());
        contentValue.put( DbHelper.IS_SEND,"0");
        contentValue.put( DbHelper.IS_SYNC,"0");
        database.update(DbHelper.TABLE_NAME_IBU, contentValue,"_id = ?",new String[]{_id});

    }
    public String getusername(){
        //open();
        Cursor cursor = fetchUserData();
        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.USERNAME));
       // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        //close();
        return username;

    }
    public String getlocName(){

        Cursor cursor = fetchUserData();
        cursor.moveToFirst();
        String locName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        //close();
        return locName;
    }

    public String getLocTagName(){

        Cursor cursor = fetchUserData();
        cursor.moveToFirst();
        String locTagName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.LOCATION_TAG));
        // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        //close();
        return locTagName;
    }

    public String getUserGroup(){

        Cursor cursor = fetchUserData();
        cursor.moveToFirst();
        String userGroup = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.GROUPS));
        // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
        //close();
        return userGroup;
    }

    public String getUniqueID(String id, String tablename){
        String uniq=null;
        Cursor cursor = fetchuniqueId(id, tablename);
        if(cursor.moveToFirst()) {
            uniq = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.UNIQUEID));
            // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
            //close();
        }
        return uniq;
    }

    public String getIDIbu(String id, String tablename){
        String uniq=null;
        Cursor cursor = fetchIdIbu(id, tablename);
        if(cursor.moveToFirst()) {
            uniq = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.ID_IBU));
            // locName = userd.getString(userd.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
            //close();
        }
        return uniq;
    }


    public int randomNum(){
        final int random = new Random().nextInt(100) + 800;

        return random;
    }

    private void insertUpdateIdforPulling(String updateId, String formName) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(  DbHelper.UPDATE_ID, updateId);
        contentValue.put(  DbHelper.FORM_NAME, formName);
        contentValue.put(  DbHelper.IS_SYNC, 1);
        database.insert(TABLE_UPDATEID_SYNC, null, contentValue);

    }

    public Cursor fetchSyncedData() {
        String sql = "SELECT "+UPDATE_ID+" FROM "+TABLE_UPDATEID_SYNC+" ORDER BY "+UPDATE_ID+" DESC";
        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public String getlatestUpdateId() {
        String upId ="0";
        setOrderBy(UPDATE_ID+" DESC");
        Cursor cursor = fetchSyncedData();

        if(cursor.moveToFirst()) {
            upId = cursor.getString(cursor.getColumnIndexOrThrow(UPDATE_ID));
            Log.e("UPDAAAAA", "null");
        }else {
            Log.e("UPDAAAAA", upId);
        }
        return upId;
    }

    public Cursor fetchJenisKendaraan(String namas) {
        String[] columns = new String[] {
                DbHelper.Jenis,
                DbHelper.Jenis_LAIN};
        Cursor c=null;
        c = database.query(DbHelper.TABLE_NAME_TRANS, columns, DbHelper.NAME +" LIKE '%"+namas+"%'", selectionArgs, groupBy, having, orderBy, limit);
        Log.e("JENIS",c.toString());
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

}