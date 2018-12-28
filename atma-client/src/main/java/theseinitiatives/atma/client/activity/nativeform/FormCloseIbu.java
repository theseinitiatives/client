package theseinitiatives.atma.client.activity.nativeform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import org.json.JSONObject;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

public class FormCloseIbu extends AppCompatActivity {


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
        setContentView(R.layout.form_tutup_ibu);
        // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");
        UNIQUEID = getIntent().getStringExtra("uniqueId");

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
                    dataArray.put(DbHelper.NIFAS_SELESAI,status_);
                    dataArray.put(DbHelper.UNIQUEID,UNIQUEID);
                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }
                dbManager.open();
                dbManager.closeIbu(idIbu,status_,alasan);
                dbManager.insertsyncTable("close_ibu", System.currentTimeMillis(), dataArray.toString(), 0, 0);

                dbManager.close();
                finish();
                Intent myIntent = new Intent(FormCloseIbu.this, IdentitasIbuActivity.class);
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
            case R.id.masa_nifas_berakhir:
                if(checked)
                    setAlasan("nifas berakhir");
                break;
            case R.id.meninggal:
                if(checked)
                    setAlasan("meninggal");
                break;
            case R.id.keguguran:
                if(checked)
                    setAlasan("keguguran");
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
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

}