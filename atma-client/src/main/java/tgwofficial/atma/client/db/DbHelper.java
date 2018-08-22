package tgwofficial.atma.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHelper  extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "identitas_ibu";

    // Table columns
    public static final String _ID = "_id";
    public static final String DATA = "data";
    public static final String TIMESTAMP = "timestamp";

    // Database Information
    static final String DB_NAME = "identitas_ibu.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATA + " TEXT NOT NULL, " + TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

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
        insertDummy(db,"Rana, Iqbal, Patient Baru, Mobil,A");
        insertDummy(db,"Siti, Adawiyah, Patient Baru, Helicopter,BA");
        insertDummy(db,"Marwan Nulhakam, Belum Dapat, MASIH JOMBLO, Hanya CINTA, Darah Biru");
        insertDummy(db,"Haneefa, Ibrahim, Patient Baru, Pesawat,A");
        insertDummy(db,"Iqbal, Muhammad, Patient Baru, UFO,A");
        insertDummy(db,"Ainul, Hamdani, Patient Lama, Superman,B");
        insertDummy(db,"Nina, Hidayati, Patient Baru, Toink,C");
        insertDummy(db,"Zahroh, Rana, Patient Baru, Yach,D");
        insertDummy(db,"Moya, Dyah, Patient Baru, Rocket,E");


    }

    private static void insertDummy(SQLiteDatabase db, String data) {
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + DATA
                + ") VALUES ('" + data + "');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}