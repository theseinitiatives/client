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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}