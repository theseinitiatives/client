package tgwofficial.atma.client.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.nativeform.FormAddBankDarah;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class BankDarahDetailActivity extends AppCompatActivity {

    private TextView name, golonganDarah, dusun, gubug, telepon;
    private Button editButton;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_darah_detail);

        name = (TextView)findViewById(R.id.donor_detail_nama);
        golonganDarah = (TextView)findViewById(R.id.donor_detail_golongan);
        dusun = (TextView)findViewById(R.id.donor_detail_dusun);
        gubug = (TextView)findViewById(R.id.donor_detail_gubug);
        telepon = (TextView)findViewById(R.id.donor_detail_telepon);

        final String id = getIntent().getStringExtra("id");

        DbManager db = new DbManager(this).open();
        db.setSelection(DbHelper._ID+" = '"+id+"'");
        c = db.fetchBankDarah("","");
        if(c!=null)
            c.moveToFirst();

        name.setText("Nama : "+c.getString(c.getColumnIndexOrThrow("name_pendonor")));
        golonganDarah.setText("Golongan Darah : "+c.getString(c.getColumnIndexOrThrow("gol_darah")));
//        dusun.setText(c.getString(c.getColumnIndexOrThrow("dusun")));
        gubug.setText("Gubug : "+c.getString(c.getColumnIndexOrThrow("gubug")));
        telepon.setText("Telepon : "+c.getString(c.getColumnIndexOrThrow("telp")));

        editButton = (Button) findViewById(R.id.donor_detail_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BankDarahDetailActivity.this,FormAddBankDarah.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });
    }

}
