package theseinitiatives.atma.client.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.nativeform.FormAddIbuActivity;
import theseinitiatives.atma.client.activity.nativeform.StikerActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;
import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class IdentitasIbuDetailActivity extends AppCompatActivity {
    private DbManager dbManager;
     String jenis ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_detail_layout);
        dbManager = new DbManager(this);
        dbManager.open();
        final String uniqueId = getIntent().getStringExtra("uniqueId");
        final String id = getIntent().getStringExtra("id");
        Cursor cursor = dbManager.fetchdetaildata(id);
        TextView txt_status = (TextView) findViewById(R.id.status);
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
        TextView txt_last = (TextView) findViewById(R.id.lastedit);

        final String Nama = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        final String htp = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.HTP));
        //set detail
        txt_name.setText("NAMA:"+humanizes(getStringData("name", cursor)));
        txt_spousename.setText("NAMA PASANGAN:"+humanizes( getStringData("spousename", cursor)));
        txt_dob.setText("TGL LAHIR:"+AllConstants.convertToDDMMYYYY(humanizes(cursor.getString(cursor.getColumnIndexOrThrow("tgl_lahir")))));
        txt_desa.setText("DUSUN: "+humanizes(getStringData("dusun", cursor)));
        txt_hpht.setText("HPHT: "+AllConstants.convertToDDMMYYYY(humanizes(cursor.getString(cursor.getColumnIndexOrThrow("hpht")))));
        txt_htp.setText("HTP: "+AllConstants.convertToDDMMYYYY(humanizes(cursor.getString(cursor.getColumnIndexOrThrow("htp")))));
        txt_gol_darah.setText("Gol Darah: "+humanizes(getStringData("gol_darah", cursor)));
        txt_kader.setText("Nama Kader: "+humanizes(getStringData("kader", cursor)));
        txt_hp.setText("No Telp: "+humanizes(getStringData("telp", cursor)));
        txt_resiko.setText("Faktor Resiko: "+humanizes(getStringData("resiko", cursor)));
        txt_last.setText("Edit Terakhir Oleh: "+humanizes(getStringData("user_id", cursor)));

        //status bersalin
        Cursor status_persalinan = dbManager.fetchstatuspersalinan(uniqueId);
        if ( status_persalinan.moveToFirst() ) {

            txt_status.setText("Status: "+humanizes(getStringData("status_bersalin", status_persalinan)));
            tgl_bersalin.setText("Tgl Persalinan: " + humanizes(getStringData("tgl_persalinan", status_persalinan)));
            kondisi_ibu.setText("Kondisi Ibu: " + humanizes(getStringData("kondisi_ibu", status_persalinan)));
            kondisi_anak.setText("Kondisi anak: " + humanizes(getStringData("kondisi_anak", status_persalinan)));

        }
        else{
            tgl_bersalin.setText("Tgl Persalinan: ");
            txt_status.setText("Status: ");
            kondisi_ibu.setText("Kondisi Ibu: ");
            kondisi_anak.setText("Kondisi anak: ");


        }
        ///rencana persalinan
        final Cursor cursorRencanaPersalinan = dbManager.fetchRencanaPersalinan(uniqueId);
        if ( cursorRencanaPersalinan.moveToFirst() ) {
            final String checkrencana = "yes";
          //  TextView txt_tempatbersalins = (TextView) findViewById(R.id.tempatbersalins);
          //  TextView txt_penolognbersalin = (TextView) findViewById(R.id.penoling_bersalin);

            txt_penolognPersalinan.setText("Penolong Persalinan: " + humanizes(getStringData("penolong_persalinan", cursorRencanaPersalinan)));
            namaDonor.setText("Nama Pendonor: " + humanizes(getStringData("name_pendonor", cursorRencanaPersalinan)));
            txt_hubunganPendonor.setText("Hubungan  Pendonor: " + humanizes(getStringData("hubungan_pendonor", cursorRencanaPersalinan)));
            namaTransportasi.setText("Nama Pemilik Kendaraan : " + humanizes(getStringData("pemilik_kendaraan", cursorRencanaPersalinan)));
            txt_hubunganPemilik.setText("Hubungan Pemilik Kendaraan: " + humanizes(getStringData("hubungan_ibu", cursorRencanaPersalinan)));
            txt_tempatbersalin.setText("Tempat Persalinan: " + humanizes(getStringData("tempat_persalinan", cursorRencanaPersalinan)));

            txt_pendampingPersalinan.setText("Pendamping Persalinan: " + humanizes(getStringData("pendamping_persalinan", cursorRencanaPersalinan)));
            jenis = getStringData("pemilik_kendaraan", cursorRencanaPersalinan);

        }
        else{
            txt_penolognPersalinan.setText("Penolong Persalinan: " );
            namaDonor.setText("Nama Pendonor: " );
            txt_hubunganPendonor.setText("Hubungan dengan Pendonor: " );
            namaTransportasi.setText("Kendaraan : " );
            txt_hubunganPemilik.setText("Hubungan Dengan Pemilik Kendaraan: " );
            txt_tempatbersalin.setText("Tempat Persalinan: " );
            txt_pendampingPersalinan.setText("Pendamping Persalinan: " );

        }




        Button editButton = (Button) findViewById(R.id.ibu_detail_edit_button);
        dbManager.open();
        if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            editButton.setVisibility(View.GONE);
        }
        dbManager.close();

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
        Button stikers = (Button) findViewById(R.id.stiker);


        stikers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursorRencanaPersalinan.getCount()>0){
                    Intent myIntent = new Intent(IdentitasIbuDetailActivity.this, StikerActivity.class);
                    myIntent.putExtra("id", id);
                    myIntent.putExtra("nama", Nama);
                    myIntent.putExtra("htp", htp);
                    myIntent.putExtra("jenis", jenis);
                    startActivity(myIntent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else{
                    Toast.makeText(IdentitasIbuDetailActivity.this,"FORM RENCANA PERSALINAN BELUM DI INPUT!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        dbManager.close();
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

    public String getStringData(String tablename, Cursor cursor){

        String data = cursor.getString(cursor.getColumnIndexOrThrow(tablename));
        String text = data!=null?data:"";

        if(text.equalsIgnoreCase("rumahsakit"))
            text = "rumah sakit";
        return text;
    }
}