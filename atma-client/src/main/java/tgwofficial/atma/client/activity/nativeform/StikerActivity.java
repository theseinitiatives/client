package tgwofficial.atma.client.activity.nativeform;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.BankDarahDetailActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class StikerActivity extends AppCompatActivity {

    private TextView name, htp_, penolong_, tempat_, pendamping_, transportsi_, nama_donor;
    private Button editButton;
    private Cursor c;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stiker_layout);

        name = (TextView) findViewById(R.id.nama_ibu);
        htp_ = (TextView) findViewById(R.id.htp);
        penolong_ = (TextView) findViewById(R.id.penolong);
        tempat_ = (TextView) findViewById(R.id.tempat);
        pendamping_ = (TextView) findViewById(R.id.pendamping);
        transportsi_ = (TextView) findViewById(R.id.transportasi);
        nama_donor = (TextView) findViewById(R.id.donor);

        final String id = getIntent().getStringExtra("id");
        final String namas = getIntent().getStringExtra("nama");
        final String htp = getIntent().getStringExtra("htp");
        final String jenisk = getIntent().getStringExtra("jenis");

        dbManager = new DbManager(this);
        dbManager.open();

        // Cursor cursorRencanaPersalinan = dbManager.fet(id);
        name.setText("  " +namas);


        htp_.setText(" "+ htp);
        Cursor c = dbManager.fetchRencanaPersalinan(id);
        if (c.moveToFirst()) {
            nama_donor.setText("  " +c.getString(c.getColumnIndexOrThrow("name_pendonor")));
            penolong_.setText("" + c.getString(c.getColumnIndexOrThrow("penolong_persalinan")));
            tempat_.setText("  " + c.getString(c.getColumnIndexOrThrow("tempat_persalinan")));
            pendamping_.setText("  " + c.getString(c.getColumnIndexOrThrow("pendamping_persalinan")));



        }
        Cursor cursorkerndaran = dbManager.fetchJenisKendaraan(jenisk);
        if (cursorkerndaran.moveToFirst()) {
            transportsi_.setText("  " + cursorkerndaran.getString(cursorkerndaran.getColumnIndexOrThrow("jenis_kendaraan")));
            Log.e("JENISK",cursorkerndaran.getString(cursorkerndaran.getColumnIndexOrThrow("jenis_kendaraan")));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi = new NavigationmenuController(this);
        navi.backtoIbu();

    }
}