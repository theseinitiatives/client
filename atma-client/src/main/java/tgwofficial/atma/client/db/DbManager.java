package tgwofficial.atma.client.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    /*public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DbHelper.NAME, name);
        contentValue.put(DbHelper.TIMESTAMP, desc);
        database.insert(DbHelper.TABLE_NAME, null, contentValue);
    }*/

    public Cursor fetch() {
        String[] columns = new String[] { DbHelper._ID,
                DbHelper.NAME,
                DbHelper.SPOUSENAME,
                DbHelper.STATUS,
                DbHelper.TGL_LAHIR,
                DbHelper.DUSUN,
                DbHelper.HPHT,
                DbHelper.HTP,
                DbHelper.GOL_DARAH,
                DbHelper.KADER,
                DbHelper.TELP,
                DbHelper.TGL_PERSALINAN,
                DbHelper.KONDISI_IBU,
                DbHelper.KONDISI_ANAK,
                DbHelper.IS_SYNC,
                DbHelper.TIMESTAMP };
        Cursor cursor = database.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);
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
        database.delete(DbHelper.TABLE_NAME, DbHelper._ID + "=" + _id, null);
    }
}
