package theseinitiatives.atma.client.activity;

import android.app.Activity;
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
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.nativeform.FormAddBankDarah;
import theseinitiatives.atma.client.db.DbManager;

public class BankDarahDetailActivity extends AppCompatActivity {
    private String TAG = BankDarahDetailActivity.class.getSimpleName();

    private TextView name, golonganDarah, dusun, gubug, telepon, tgl_donor;
    private Button editButton;
    private Cursor c;
    String id;
    private DbManager dbManager;
    final Activity activity = this;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bank_darah_detail);

        name = (TextView)findViewById(R.id.donor_detail_nama);
        golonganDarah = (TextView)findViewById(R.id.donor_detail_golongan);
        dusun = (TextView)findViewById(R.id.donor_detail_dusun);
        gubug = (TextView)findViewById(R.id.donor_detail_gubug);
        telepon = (TextView)findViewById(R.id.donor_detail_telepon);
        tgl_donor  = (TextView)findViewById(R.id.tgl_donor);

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
            tgl_donor.setText("Tgl Donor Terakhir: " + c.getString(c.getColumnIndexOrThrow("tgl_donor")));
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
                FlurryHelper.endFlurryLog(activity);
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

    @Override
    protected void onResume() {
        super.onResume();
        FlurryHelper.startFlurryLog(this);
    }
}
