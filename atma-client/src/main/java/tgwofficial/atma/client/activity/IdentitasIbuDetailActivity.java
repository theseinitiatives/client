package tgwofficial.atma.client.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;


import java.io.Serializable;
import java.util.ArrayList;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.nativeform.FormAddIbuActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class IdentitasIbuDetailActivity extends AppCompatActivity {
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_detail_layout);
        dbManager = new DbManager(this);
        dbManager.open();
        final String id = getIntent().getStringExtra("id");
        Cursor cursor = dbManager.fetchdetaildata(id);

        TextView txt_name = (TextView) findViewById(R.id.name);
        TextView txt_spousename = (TextView) findViewById(R.id.spousename);
        TextView txt_dob = (TextView) findViewById(R.id.dob);
        TextView txt_desa = (TextView) findViewById(R.id.desa);
        TextView txt_htp = (TextView) findViewById(R.id.htp);
        TextView txt_hpht = (TextView) findViewById(R.id.hpht);
        TextView txt_gol_darah = (TextView) findViewById(R.id.gol_darah);
        TextView txt_kader = (TextView) findViewById(R.id.kader);
        TextView txt_hp = (TextView) findViewById(R.id.hp);
        TextView txt_resiko = (TextView) findViewById(R.id.resioko);


        TextView tgl_bersalin = (TextView) findViewById(R.id.tgl_persalinan);
        TextView kondisi_ibu = (TextView) findViewById(R.id.ibuhidup);
        TextView kondisi_anak = (TextView) findViewById(R.id.anakhidup);


        TextView namaDonor = (TextView) findViewById(R.id.nama_donors);

        TextView txt_hubunganPendonor = (TextView) findViewById(R.id.hub_pendonor);
        TextView namaTransportasi = (TextView) findViewById(R.id.transport);
        TextView txt_hubunganPemilik = (TextView) findViewById(R.id.hubPemilik);
        TextView txt_penolognPersalinan = (TextView) findViewById(R.id.penolong);
        TextView txt_tempatbersalin = (TextView) findViewById(R.id.tempatbersalins);
        TextView txt_pendampingPersalinan = (TextView) findViewById(R.id.pendampingPersalinan);


        //set detail
        txt_name.setText("NAMA :"+cursor.getString(cursor.getColumnIndexOrThrow("name")));
        txt_spousename.setText("NAMA PASANGAN :"+cursor.getString(cursor.getColumnIndexOrThrow("spousename")));
        txt_dob.setText("TGL LAHIR :"+cursor.getString(cursor.getColumnIndexOrThrow("tgl_lahir")));
        txt_desa.setText("DUSUN : "+cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
        txt_hpht.setText("HPHT : "+cursor.getString(cursor.getColumnIndexOrThrow("hpht")));
        txt_htp.setText("HTP : "+cursor.getString(cursor.getColumnIndexOrThrow("htp")));
        txt_gol_darah.setText("Gol Darah : "+cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")));
        txt_kader.setText("Nama Kader : "+cursor.getString(cursor.getColumnIndexOrThrow("kader")));
        txt_hp.setText("No Telp : "+cursor.getString(cursor.getColumnIndexOrThrow("telp")));
        txt_resiko.setText("Faktor Resiko : "+cursor.getString(cursor.getColumnIndexOrThrow("resiko")));

        //status bersalin
        Cursor status_persalinan = dbManager.fetchstatuspersalinan(id);
        if ( status_persalinan.moveToFirst() ) {


            tgl_bersalin.setText("Tgl Persalinan : " + status_persalinan.getString(status_persalinan.getColumnIndexOrThrow("tgl_persalinan")));
            kondisi_ibu.setText("Kondisi Ibu : " + status_persalinan.getString(status_persalinan.getColumnIndexOrThrow("kondisi_ibu")));
            kondisi_anak.setText("Kondisi anak : " + status_persalinan.getString(status_persalinan.getColumnIndexOrThrow("kondisi_anak")));

        }
        else{
            tgl_bersalin.setText("Tgl Persalinan : ");

            kondisi_ibu.setText("Kondisi Ibu : ");
            kondisi_anak.setText("Kondisi anak : ");


        }
        ///rencana persalinan
        Cursor cursorRencanaPersalinan = dbManager.fetchRencanaPersalinan(id);
        if ( status_persalinan.moveToFirst() ) {

          //  TextView txt_tempatbersalins = (TextView) findViewById(R.id.tempatbersalins);
          //  TextView txt_penolognbersalin = (TextView) findViewById(R.id.penoling_bersalin);

            txt_penolognPersalinan.setText("Penolong Persalinan: " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("penolong_persalinan")));
            namaDonor.setText("Nama Pendonor : " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("name_pendonor")));
            txt_hubunganPendonor.setText("Hubungan dengan Pendonor : " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("hubungan_pendonor")));
            namaTransportasi.setText("Kendaraan  : " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("pemilik_kendaraan")));
            txt_hubunganPemilik.setText("Hubungan Dengan Pemilik Kendaraan : " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("hubungan_ibu")));
            txt_tempatbersalin.setText("Tempat Persalinan: " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("tempat_persalinan")));

            txt_pendampingPersalinan.setText("Pendamping Persalinan: " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("pendamping_persalinan")));
            //  txt_tempatbersalin.setText("Tempat Persalinan: " + cursorRencanaPersalinan.getString(cursorRencanaPersalinan.getColumnIndexOrThrow("tempat_persalinan")));
        }
        else{
            txt_penolognPersalinan.setText("Penolong Persalinan: " );
            namaDonor.setText("Nama Pendonor : " );
            txt_hubunganPendonor.setText("Hubungan dengan Pendonor : " );
            namaTransportasi.setText("Kendaraan  : " );
            txt_hubunganPemilik.setText("Hubungan Dengan Pemilik Kendaraan : " );
            txt_tempatbersalin.setText("Tempat Persalinan: " );
            txt_pendampingPersalinan.setText("Pendamping Persalinan: " );

        }



        dbManager.close();
        Button editButton = (Button) findViewById(R.id.ibu_detail_edit_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(IdentitasIbuDetailActivity.this, FormAddIbuActivity.class);
                myIntent.putExtra("id",id);
                startActivity(myIntent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }
}