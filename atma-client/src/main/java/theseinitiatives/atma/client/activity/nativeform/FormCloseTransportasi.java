package theseinitiatives.atma.client.activity.nativeform;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class FormCloseTransportasi extends AppCompatActivity {
    private String TAG = FormCloseIbu.class.getSimpleName();
    final Activity activity = this;

    private DbManager dbManager;

    private void setAlasan(String value){
        this.alasan = value;
    }
    private String getAlasan(){
        return alasan;
    }

    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    String Statuss;
    Button btnLogin;
    String UNIQUEID;
    String uniqueId;
    String alasan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tutup_transportasi);
        // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");
        UNIQUEID = getIntent().getStringExtra("uniqueId");
        final String textDusun = getIntent().getStringExtra("dusun");

        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status_ = getStatuss();
                String UUID = java.util.UUID.randomUUID().toString();
                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.ALASAN, alasan);
                    dataArray.put(DbHelper.TUTUP_DATA,status_);
                    dataArray.put(DbHelper.UNIQUEID,UNIQUEID);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());
                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }
                dbManager.open();
                dbManager.closeTransportasi(idIbu,status_,alasan);
                dbManager.insertsyncTable(DbHelper.TABLE_CLOSE_TRANSPORTASI, System.currentTimeMillis(),textDusun, dataArray.toString(), 0, 0);

                dbManager.close();
                Map<String, String> Params = new HashMap<>();
                Params.put("Save",dateNow().toString());
                FlurryHelper.logOneTimeEvent(activity.getClass().getSimpleName(), Params);
                FlurryHelper.endFlurryLog(activity);
                finish();
                Intent myIntent = new Intent(FormCloseTransportasi.this, TransportasiActivity.class);
                startActivity(myIntent);

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.tutp_tidak:
                if (checked)
                    setStatuss("tidak");
                break;
            case R.id.tutup_ya:
                if (checked)
                    setStatuss("ya");
                break;
            case R.id.tidak_ada_kendaraan:
                if(checked)
                    setAlasan("tidak ada kendaraan");
                break;
            case R.id.meninggal:
                if(checked)
                    setAlasan("meninggal");
                break;
            case R.id.pindah:
                if(checked)
                    setAlasan("pindah");
                break;
            case R.id.salah_entry:
                if(checked)
                    setAlasan("salah entry");
                break;
        }
    }

    public String getDusun(String id){
        if(dbManager == null) {
            dbManager = new DbManager(getApplicationContext());
        }
        dbManager.open();
        Cursor cursor = dbManager.fetchdetaildata(id);
        String namaDUsun = cursor.getString(cursor.getColumnIndexOrThrow("dusun"));
        dbManager.close();
        return namaDUsun;

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoTrans();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FlurryHelper.startFlurryLog(this);
    }
}