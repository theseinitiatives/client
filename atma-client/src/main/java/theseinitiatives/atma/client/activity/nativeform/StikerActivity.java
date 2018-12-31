package theseinitiatives.atma.client.activity.nativeform;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.db.DbManager;
import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

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
        final String jenisk_pemilik = getIntent().getStringExtra("jenis");
        final String uniqueID = getIntent().getStringExtra("uniqueId");

        dbManager = new DbManager(this);
        dbManager.open();

        // Cursor cursorRencanaPersalinan = dbManager.fet(id);
        name.setText("" +humanizes(namas));
        //set null

        htp_.setText(""+ htp);
        Cursor c = dbManager.fetchRencanaPersalinan(uniqueID);
        if (c.moveToFirst()) {
            String tempats = c.getString(c.getColumnIndexOrThrow("tempat_persalinan"));
            String humanizatempat = tempats.equalsIgnoreCase("rumahsakit")?"Rumah Sakit":humanizes(tempats);
            nama_donor.setText("" +humanizes(c.getString(c.getColumnIndexOrThrow("name_pendonor"))));
            penolong_.setText("" + humanizes(c.getString(c.getColumnIndexOrThrow("penolong_persalinan"))));
            tempat_.setText(humanizatempat);
            pendamping_.setText("" + humanizes(c.getString(c.getColumnIndexOrThrow("pendamping_persalinan"))));



        }
        Cursor cursorkerndaran = dbManager.fetchJenisKendaraan(jenisk_pemilik);
        if (cursorkerndaran.moveToFirst()) {
            transportsi_.setText("" + humanizes(cursorkerndaran.getString(cursorkerndaran.getColumnIndexOrThrow("jenis_kendaraan"))));
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