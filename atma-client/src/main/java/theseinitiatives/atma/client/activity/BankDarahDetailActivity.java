package theseinitiatives.atma.client.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.nativeform.FormAddBankDarah;
import theseinitiatives.atma.client.db.DbManager;

public class BankDarahDetailActivity extends AppCompatActivity {

    private TextView name, golonganDarah, dusun, gubug, telepon;
    private Button editButton;
    private Cursor c;
    String id;
    private DbManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bank_darah_detail);

        name = (TextView)findViewById(R.id.donor_detail_nama);
        golonganDarah = (TextView)findViewById(R.id.donor_detail_golongan);
        dusun = (TextView)findViewById(R.id.donor_detail_dusun);
        gubug = (TextView)findViewById(R.id.donor_detail_gubug);
        telepon = (TextView)findViewById(R.id.donor_detail_telepon);


         id = getIntent().getStringExtra("id");

        if(dbManager == null) {
            dbManager = new DbManager(getApplicationContext());
        }
        dbManager.open();
        Cursor c = dbManager.fetchBankDarahUpdate(id);
        if ( c.moveToFirst() ) {

            name.setText("Nama: " + c.getString(c.getColumnIndexOrThrow("name_pendonor")));
            golonganDarah.setText("Golongan Darah: " + c.getString(c.getColumnIndexOrThrow("gol_darah")));
            dusun.setText("Dusun: " +c.getString(c.getColumnIndexOrThrow("dusun")));
            gubug.setText("Gubug: " + c.getString(c.getColumnIndexOrThrow("gubug")));
            telepon.setText("Telepon: " + c.getString(c.getColumnIndexOrThrow("telp")));
        }
        editButton = (Button) findViewById(R.id.donor_detail_edit_button);
       // dbManager.open();
        /*if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            editButton.setVisibility(View.GONE);
        }*/
       // dbManager.close();
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

    @Override
    public void onBackPressed() {
        finish();
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtodarah();

    }
}
