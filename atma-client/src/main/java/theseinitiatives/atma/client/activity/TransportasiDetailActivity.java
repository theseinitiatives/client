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
import theseinitiatives.atma.client.activity.nativeform.FormAddTransportasi;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

public class TransportasiDetailActivity extends AppCompatActivity {

    private TextView nama,dusun,gubug,profesi,telepon,jenisKendaraan,kapasitas,keterangan;
    private Button edit;
    private DbManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_transportasi_detail);
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

        nama.setText("NAMA: "+cursor.getString(cursor.getColumnIndexOrThrow("name")));
        dusun.setText("DUSUN: "+cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
        gubug.setText("GUBUG: "+cursor.getString(cursor.getColumnIndexOrThrow("gubug")));
        profesi.setText("PROFESI: "+cursor.getString(cursor.getColumnIndexOrThrow("profesi")));
        telepon.setText("TELEPON: "+cursor.getString(cursor.getColumnIndexOrThrow("telp")));
        jenisKendaraan.setText(String.format(
                "%s: %s",
                "KENDARAAN",
                (cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan")).contains("pickup")
                    ? getString(R.string.pickup)
                    : cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan")).equalsIgnoreCase("id_lainnya")
                        ?cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan_lainnya"))
                        :cursor.getString(cursor.getColumnIndexOrThrow("jenis_kendaraan"))
        )));
        kapasitas.setText("KAPASITAS: "+cursor.getString(cursor.getColumnIndexOrThrow("kapasitas_kendaraan")));
        keterangan.setText("KETERANGAN: "+cursor.getString(cursor.getColumnIndexOrThrow("keterangan")));

        edit = (Button) findViewById(R.id.transportasi_edit_button);
        final String entityID = id;

      //  dbManager.open();
       /* if(dbManager.getUserGroup().equalsIgnoreCase("kader")){
            edit.setVisibility(View.GONE);
        }*/
       // dbManager.close();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransportasiDetailActivity.this,FormAddTransportasi.class);
                intent.putExtra("id",entityID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoTrans();

    }

}
