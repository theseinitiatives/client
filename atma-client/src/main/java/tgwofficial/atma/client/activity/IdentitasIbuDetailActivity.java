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

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.nativeform.FormAddIbuActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class IdentitasIbuDetailActivity extends AppCompatActivity {
    private DbManager dbManager;
    public static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_ibu_detail_layout);
        dbManager = new DbManager(this);
        dbManager.open();
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

        txt_name.setText("NAMA :"+cursor.getString(cursor.getColumnIndexOrThrow("name")));
        txt_spousename.setText("NAMA PASANGAN :"+cursor.getString(cursor.getColumnIndexOrThrow("spousename")));
        txt_dob.setText("STATUS:"+cursor.getString(cursor.getColumnIndexOrThrow("tgl_lahir")));
        txt_desa.setText("Dusun: "+cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
        txt_hpht.setText("hpht: "+cursor.getString(cursor.getColumnIndexOrThrow("hpht")));
        txt_htp.setText("Dusun: "+cursor.getString(cursor.getColumnIndexOrThrow("htp")));
        txt_gol_darah.setText("Gol Darah: "+cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")));
        txt_kader.setText("Nama Kader: "+cursor.getString(cursor.getColumnIndexOrThrow("kader")));
        txt_hp.setText("No Telp: "+cursor.getString(cursor.getColumnIndexOrThrow("telp")));

        dbManager.close();
        Button editButton = (Button) findViewById(R.id.ibu_detail_edit_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(IdentitasIbuDetailActivity.this, FormAddIbuActivity.class);
                myIntent.putExtra("id",IdentitasIbuDetailActivity.id);
                startActivity(myIntent);
                overridePendingTransition(0,0);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, IdentitasIbuActivity.class));
        overridePendingTransition(0, 0);


    }
}