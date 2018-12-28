package tgwofficial.atma.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME_IBU = "identitas_ibu";
    public static final String TABLE_PERSALINAN = "status_persalinan";
    public static final String TABLE_NAME_TRANS = "transportasi";
    public static final String TABLE_NAME_BANK = "bank_darah";
    public static final String TABLE_NAME_RENCANA = "rencana_persalinan";
    public static final String TABLE_NAME_USER = "app_user";
    public static final String TABLE_LOCATION_TREE = "location_tree";
    public static final String TABLE_KADER = "kader";
    public static final String TABLE_CLOSE = "close_ibu";



    // Table columns Ibu
    public static final String _ID = "_id";
    public static final String ID_IBU = "id_ibu";
    public static final String ID_TRANS = "id_trans";

    public static final String NAME = "name";
    public static final String NAME_PENDONOR = "name_pendonor";
    public static final String SPOUSENAME = "spousename";
    public static final String STATUS = "status";
    public static final String STATUS_BERSALIN = "status_bersalin";
    public static final String TGL_LAHIR = "tgl_lahir";
    public static final String DUSUN = "dusun";
    public static final String HPHT = "hpht";
    public static final String HTP = "htp";
    public static final String GOL_DARAH = "gol_darah";
    public static final String KADER = "kader";
    public static final String TELP = "telp";
    public static final String TGL_PERSALINAN = "tgl_persalinan";
    public static final String KONDISI_IBU = "kondisi_ibu";
    public static final String KONDISI_ANAK = "kondisi_anak";
    public static final String STATUS_NIFAS = "status_nifas";
    public static final String NIFAS_SELESAI = "nifas_selesai";
    public static final String ALASAN = "alasan";
    public static final String RESIKO = "resiko";

    //Table Column Transportasi
    public static final String Jenis = "jenis_kendaraan";
    public static final String Kapasitas = "kapasitas_kendaraan";
    public static final String GUBUG = "gubug";
    public static final String PROFESI = "profesi";
    public static final String KET = "keterangan";

    //standart column
    public static final String IS_SYNC = "is_sync";
    public static final String IS_SEND = "is_send";
    public static final String TIMESTAMP = "timestamp";
    public static final String FORM_NAME = "form_name";
    public static final String UPDATE_ID = "update_id";
    public static final String LOCATION_ID = "location_id";
    public static final String USER_ID = "user_id";


    // form Rencana Persalinan
    public static final String PENOLONG_PERSALINAN = "penolong_persalinan";
    public static final String TEMPAT_PERSALINAN = "tempat_persalinan";
    public static final String PENDAMPING_PERSALINAN = "pendamping_persalinan";
    public static final String HUBUNGAN_DENGAN_IBU = "hubungan_ibu";
    public static final String HUBUNGAN_PENDONOR_IBU = "hubungan_pendonor";

    public static final String NAME_PEMILIK = "pemilik_kendaraan";
   // public static final String IS_SYNC = "is_sync";


    public static final String JUMLAHBAYI = "jumlah_bayi";
    public static final String JENISKELAMIN =  "jenis_kelamin";
    public static final String KOMPLIKASIIBU = "komplikasi_ibu";
    public static final String KOMPLIKASIANAK = "komplikasi_anak";
    // user field

    /*  Application User Variable*/
    public static final String PERSON_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
//    public static final String CREATED_ON = "created_on";
//    public static final String LAST_LOGIN = "last_login";
//    public static final String ACTIVE = "active";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMPANY = "company";
    public static final String PHONE = "phone";
    public static final String GROUPS = "groups";
    public static final String LOCATION_NAME = "location_name";
    public static final String PARENT_LOCATION = "parent_location";
    public static final String LOCATION_TAG_ID = "location_tag_id";


    /*  Location Tree Variable*/
    public static final String LOC_NAME = "name";


    public static final String UNIQUEID = "unique_id";


    static final String DB_NAME = "atma.DB";

    // database version
    static final int DB_VERSION = 1;


    //sync tables,
    public static final String DATA = "data";


    public static final String TABLE_SYNC = "sync_table";
    // Creating table Ibu
    private static final String CREATE_TABLE_SYNC = "create table "
            + TABLE_SYNC + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + DATA + " TEXT , "
            + FORM_NAME + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0);";

    private static final String CREATE_CLOSE = "create table "
            + TABLE_CLOSE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + UNIQUEID + " TEXT NOT NULL, "
            + NIFAS_SELESAI + " TEXT , "
            + ALASAN + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0);";


    // Creating table Ibu
    private static final String CREATE_TABLE_IBU = "create table "
            + TABLE_NAME_IBU + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UNIQUEID + " TEXT NOT NULL, "
            + NAME + " TEXT ,"
            + SPOUSENAME + " TEXT , "
            + TGL_LAHIR + " TEXT , "
            + DUSUN + " TEXT , "
            + GUBUG + " TEXT , "
            + HPHT + " TEXT , "
            + HTP + " TEXT , "
            + GOL_DARAH + " TEXT , "
            + KADER + " TEXT, "
            + TELP + " TEXT , "
            + RESIKO + " TEXT , "
            + NIFAS_SELESAI + " TEXT , "
            + ALASAN + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    // Creating table Transportasi
    private static final String CREATE_TABLE_TRANS = "create table "
            + TABLE_NAME_TRANS + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UNIQUEID + " TEXT NOT NULL, "
            + NAME + " TEXT , "
            + Jenis + " TEXT, "
            + Kapasitas + " TEXT , "
            + TELP + " TEXT , "
            + DUSUN + " TEXT , "
            + GUBUG + " TEXT , "
            + PROFESI + " TEXT , "
            + KET + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    public static final String TGL_DONOR = "tgl_donor";
    // Creating table Bank Darah
    private static final String CREATE_TABLE_BANK = "create table "
            + TABLE_NAME_BANK + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UNIQUEID + " TEXT NOT NULL, "
            + NAME_PENDONOR + " TEXT , "
            + DUSUN + " TEXT , "
            + GUBUG + " TEXT , "
            + STATUS + " TEXT , "
            + GOL_DARAH + " TEXT , "
            + TGL_DONOR + " TEXT , "
            + TELP + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    private static final String CREATE_TABLE_STATUS_PERSALINAN = "create table "
            + TABLE_PERSALINAN + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_IBU + " TEXT NOT NULL, "
            + STATUS_BERSALIN + " INTEGER DEFAULT 0, "
            + TGL_PERSALINAN + " TEXT , "
            + KONDISI_IBU + " TEXT , "
            + KONDISI_ANAK + " TEXT , "
            + JUMLAHBAYI + " TEXT , "
            + JENISKELAMIN + " TEXT , "
            + KOMPLIKASIIBU + " TEXT , "
            + KOMPLIKASIANAK + " TEXT , "
            + TEMPAT_PERSALINAN + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";




    // Creating table rencana_persalinan
    private static final String CREATE_TABLE_RENCANA = "create table "
            + TABLE_NAME_RENCANA + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ID_IBU + " TEXT NOT NULL, "
            + ID_TRANS + " TEXT , "
            + PENOLONG_PERSALINAN + " TEXT , "
            + TEMPAT_PERSALINAN + " TEXT , "
            + PENDAMPING_PERSALINAN + " TEXT , "
            + HUBUNGAN_DENGAN_IBU + " TEXT , "
            + NAME_PENDONOR + " TEXT,"
            + NAME_PEMILIK + " TEXT,"
            + HUBUNGAN_PENDONOR_IBU + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    // Creating table app user
    private static final String CREATE_TABLE_USER = "create table "
            +TABLE_NAME_USER+" ( "
            +PERSON_ID + " INTEGER NOT NULL PRIMARY KEY, "
            +USERNAME + " TEXT, "
            +PASSWORD + " TEXT, "
            +EMAIL + " TEXT, "
//            +CREATED_ON + " TEXT, "
//            +LAST_LOGIN + " TEXT, "
//            +ACTIVE + " TEXT, "
            +FIRST_NAME + " TEXT, "
            +LAST_NAME + " TEXT, "
            +COMPANY + " TEXT, "
            +PHONE + " TEXT, "
            +GROUPS + " TEXT, "
            +LOCATION_ID+ " TEXT, "
            +LOCATION_NAME+ " TEXT,"
            +PARENT_LOCATION+ " TEXT,"
            +LOCATION_TAG_ID+ " TEXT"
            +")";

    // Creating table location tree
    private static final String CREATE_TABLE_LOCATION_TREE = "create table "
            +TABLE_LOCATION_TREE+" ( "
            +LOCATION_ID+ " TEXT, "
            +LOCATION_NAME+ " TEXT,"
            +PARENT_LOCATION+ " TEXT,"
            +LOCATION_TAG_ID+ " TEXT"
            +")";

    public static final String [] USER_VARIABLE = new String[]{
            PERSON_ID,
            USERNAME,
            EMAIL,
//            CREATED_ON,
//            LAST_LOGIN,
//            ACTIVE,
            FIRST_NAME,
            LAST_NAME,
            COMPANY,
            PHONE,
            GROUPS,
            LOCATION_ID,
            LOCATION_NAME,
            PARENT_LOCATION,
            LOCATION_TAG_ID
        };
    public static final String [] KADER_VAR = new String[]{
            NAME,
            DUSUN,
            TELP,
    };

    // Creating table location tree
    private static final String CREATE_TABLE_KADER = "create table "
            + TABLE_KADER + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NAME+ " TEXT , "
            +DUSUN+ " TEXT ,"
            +TELP+ " TEXT ,"
            +USERNAME+ " TEXT ,"
            +PASSWORD+ " TEXT ,"
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            +UPDATE_ID+ " TEXT ,"
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IBU);
        db.execSQL(CREATE_TABLE_STATUS_PERSALINAN);
        db.execSQL(CREATE_TABLE_TRANS);
        db.execSQL(CREATE_TABLE_BANK);
        db.execSQL(CREATE_TABLE_RENCANA);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LOCATION_TREE);
       db.execSQL(CREATE_TABLE_KADER);
       db.execSQL(CREATE_TABLE_SYNC);
       db.execSQL(CREATE_CLOSE);
      //  insertDummy(db,"Siti"," Adawiyah"," Patient Baru");
      //  insertDummy(db,"Moya", "Dyah", "Patient Baru");

    }

    private static void insertDummy(SQLiteDatabase db, String data,String data2, String data3) {
        db.execSQL("INSERT INTO " + TABLE_NAME_IBU + " (" + NAME+","+SPOUSENAME+","+STATUS
                + ") VALUES ('" + data + "','"+data2+"','"+data3+"');");
        db.execSQL("INSERT INTO " + TABLE_NAME_TRANS + " (" + NAME+","+Jenis+") VALUES ('" + data + "','"+"mobil"+"');");
        db.execSQL("INSERT INTO " + TABLE_NAME_BANK + " (" + NAME_PENDONOR+","+GOL_DARAH+") VALUES ('" + data + "','"+"a - positif"+"');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IBU);
        onCreate(db);
    }


}