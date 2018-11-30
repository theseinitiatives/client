package tgwofficial.atma.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME_IBU = "identitas_ibu";
    public static final String TABLE_NAME_TRANS = "transportasi";
    public static final String TABLE_NAME_BANK = "bank_darah";



    // Table columns Ibu
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String NAME_PENDONOR = "name_pendonor";
    public static final String SPOUSENAME = "spousename";
    public static final String STATUS = "status";
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

    static final String DB_NAME = "atma.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table Ibu
    private static final String CREATE_TABLE_IBU = "create table "
            + TABLE_NAME_IBU + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + SPOUSENAME + " TEXT NOT NULL, "
            + STATUS + " TEXT , "
            + TGL_LAHIR + " TEXT , "
            + DUSUN + " TEXT , "
            + HPHT + " TEXT , "
            + HTP + " TEXT , "
            + GOL_DARAH + " TEXT , "
            + KADER + " TEXT, "
            + TELP + " TEXT , "
            + TGL_PERSALINAN + " TEXT , "
            + KONDISI_IBU + " TEXT, "
            + KONDISI_ANAK + " TEXT , "
            + USER_ID + " TEXT , "
            + LOCATION_ID + " TEXT , "
            + UPDATE_ID + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    // Creating table Transportasi
    private static final String CREATE_TABLE_TRANS = "create table "
            + TABLE_NAME_TRANS + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + TELP  + " TEXT,  "
            + Jenis + " TEXT, "
            + Kapasitas + " TEXT , "
            + TELP + " TEXT , "
            + DUSUN + " TEXT , "
            + GUBUG + " TEXT , "
            + PROFESI + " TEXT , "
            + KET + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    // Creating table Bank Darah
    private static final String CREATE_TABLE_BANK = "create table "
            + TABLE_NAME_BANK + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_PENDONOR + " TEXT , "
            + DUSUN + " TEXT , "
            + GUBUG + " TEXT , "
            + STATUS + " TEXT , "
            + GOL_DARAH + " TEXT , "
            + TELP + " TEXT , "
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + IS_SEND + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IBU);
        db.execSQL(CREATE_TABLE_TRANS);
        db.execSQL(CREATE_TABLE_BANK);

        /** hapus kalo udah mulai release
         *
         * ***/
       // insertDummy(db,"Zimbabwe"," ADSDS"," Patient Baru");
        //inserting dummy data
      /*  insertDummy(db,"Rana"," Iqbal", "Patient Baru");

        insertDummy(db,"Siti"," Adawiyah"," Patient Baru");
        insertDummy(db,"Marwan Nulhakam", "Belum Dapat","Patient AKut");
        insertDummy(db,"Haneefa", "Ibrahim", "Patient");
        insertDummy(db,"Iqbal", "Muhammad", "P");
        insertDummy(db,"Ainul", "Hamdani", "");
        insertDummy(db,"Nina", "Hidayati", "TOINK");
        insertDummy(db,"Zahroh", "Rana", "ASD");
        insertDummy(db,"Moya", "Dyah", "Patient Baru");*/


    }

    private static void insertDummy(SQLiteDatabase db, String data,String data2, String data3) {
        db.execSQL("INSERT INTO " + TABLE_NAME_IBU + " (" + NAME+","+SPOUSENAME+","+STATUS
                + ") VALUES ('" + data + "','"+data2+"','"+data3+"');");

        db.execSQL("INSERT INTO " + TABLE_NAME_TRANS + " (" + NAME+") VALUES ('" + data + "');");
        db.execSQL("INSERT INTO " + TABLE_NAME_BANK + " (" + NAME+") VALUES ('" + data + "');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_IBU);
        onCreate(db);
    }


}