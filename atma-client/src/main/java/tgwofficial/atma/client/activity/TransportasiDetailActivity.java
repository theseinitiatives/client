package tgwofficial.atma.client.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLOutput;

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class TransportasiDetailActivity extends AppCompatActivity {

    TextView nama,dusun,gubug,profesi,telepon,jenisKendaraan,kapasitas,keterangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportasi_detail);
        String id = getIntent().getStringExtra("id");

        DbManager db = new DbManager(getApplicationContext()).open();
        db.setSelection(DbHelper._ID+" = '"+id+"'");
        Cursor cursor = db.fetchTrans("","");
        cursor.moveToFirst();

        nama = (TextView)findViewById(R.id.transportasi_detail_nama);
        dusun = (TextView)findViewById(R.id.transportasi_detail_dusun);
        gubug = (TextView)findViewById(R.id.transportasi_detail_gubug);
        profesi = (TextView)findViewById(R.id.transportasi_detail_profesi);
        telepon = (TextView)findViewById(R.id.transportasi_detail_telepon);
        jenisKendaraan = (TextView)findViewById(R.id.transportasi_detail_jenis);
        kapasitas = (TextView)findViewById(R.id.transportasi_detail_kapasitas);
        keterangan = (TextView)findViewById(R.id.transportasi_detail_keterangan);

        nama.setText("NAMA : "+cursor.getString(cursor.getColumnIndexOrThrow("name")));
        dusun.setText("DUSUN : "+cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
        gubug.setText("GUBUG : "+cursor.getString(cursor.getColumnIndexOrThrow("gubug")));
        profesi.setText("PROFESI : "+cursor.getString(cursor.getColumnIndexOrThrow("profesi")));
        telepon.setText("TELEPON : "+cursor.getString(cursor.getColumnIndexOrThrow("telp")));
        jenisKendaraan.setText("KENDARAAN : "+cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan")));
        kapasitas.setText("KAPASITAS : "+cursor.getString(cursor.getColumnIndexOrThrow("kapasitas_kendaraan")));
        keterangan.setText("KETERANGAN : "+cursor.getString(cursor.getColumnIndexOrThrow("keterangan")));

    }

}
