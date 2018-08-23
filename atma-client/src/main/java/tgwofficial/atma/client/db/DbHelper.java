package tgwofficial.atma.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "identitas_ibu";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
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
    public static final String IS_SYNC = "is_sync";

    public static final String TIMESTAMP = "timestamp";

    // Database Information
    static final String DB_NAME = "identitas_ibu.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT NOT NULL, "
            + SPOUSENAME + " TEXT NOT NULL, "
            + STATUS + " TEXT NOT NULL, "
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
            + IS_SYNC + " INTEGER DEFAULT 0, "
            + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        /** hapus kalo udah mulai release
         *
         * ***/
        //inserting dummy data
        insertDummy(db,"Rana"," Iqbal", "Patient Baru");

        insertDummy(db,"Siti"," Adawiyah"," Patient Baru");
        insertDummy(db,"Marwan Nulhakam", "Belum Dapat","Patient AKut");
        insertDummy(db,"Haneefa", "Ibrahim", "Patient");
        insertDummy(db,"Iqbal", "Muhammad", "P");
        insertDummy(db,"Ainul", "Hamdani", "");
        insertDummy(db,"Nina", "Hidayati", "TOINK");
        insertDummy(db,"Zahroh", "Rana", "ASD");
        insertDummy(db,"Moya", "Dyah", "Patient Baru");


    }

    private static void insertDummy(SQLiteDatabase db, String data,String data2, String data3) {
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + NAME+","+SPOUSENAME+","+STATUS
                + ") VALUES ('" + data + "','"+data2+"','"+data3+"');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}