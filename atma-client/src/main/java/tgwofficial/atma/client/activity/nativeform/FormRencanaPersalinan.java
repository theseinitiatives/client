package tgwofficial.atma.client.activity.nativeform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.BankDarahActivity;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormRencanaPersalinan extends AppCompatActivity {
    EditText transportasiNama;
    EditText nama_donors;

  //  public static String id;


    String penolongPersalinan;
    String tempatPersalinan;
    String pendampingPersalinan;
    String hubunganPemilik;
    String hubunganPendonor;

    public String getPenolongPersalinan() {
        return penolongPersalinan;
    }

    public void setPenolongPersalinan(String penolongPersalinan) {
        this.penolongPersalinan = penolongPersalinan;
    }

    public String getTempatPersalinan() {
        return tempatPersalinan;
    }

    public void setTempatPersalinan(String tempatPersalinan) {
        this.tempatPersalinan = tempatPersalinan;
    }

    public String getPendampingPersalinan() {
        return pendampingPersalinan;
    }

    public void setPendampingPersalinan(String pendampingPersalinan) {
        this.pendampingPersalinan = pendampingPersalinan;
    }

    public String getHubunganPemilik() {
        return hubunganPemilik;
    }

    public void setHubunganPemilik(String hubunganPemilik) {
        this.hubunganPemilik = hubunganPemilik;
    }

    public String getHubunganPendonor() {
        return hubunganPendonor;
    }

    public void setHubunganPendonor(String hubunganPendonor) {
        this.hubunganPendonor = hubunganPendonor;
    }

    private DbManager dbManager;
    Button btnLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_rencana_persalinan);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");
        nama_donors = (EditText) findViewById(R.id.calon_pendonor);
        transportasiNama = (EditText) findViewById(R.id.transportsis);


        btnLogin = (Button) findViewById(R.id.saved);

        dbManager = new DbManager(this);
        dbManager.open();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String mothername = mother_names.getText().toString();
              //  String idIbu = id;
                String namaDonor = nama_donors.getText().toString();
                String namaTransportasi = transportasiNama.getText().toString();
                String txt_penolognPersalinan = getPenolongPersalinan();
                String txt_tempatBersalin = getTempatPersalinan();
                String txt_pendampingPersalinan = getPendampingPersalinan();
                String txt_hubunganPemilik = getHubunganPemilik();
                String txt_hubunganPendonor = getHubunganPendonor();
                if( namaDonor.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    dbManager.open();
                    dbManager.insertRencanaPersalinan(idIbu, namaDonor, txt_tempatBersalin, txt_penolognPersalinan,txt_pendampingPersalinan, txt_hubunganPemilik, txt_hubunganPendonor, namaTransportasi);
                    dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormRencanaPersalinan.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);

                }
                //validate form
                //  if(validateinput(mothername,donor,notelponss,radioStatus,radioStatus2)){
                //  dbManager.open();
                //  dbManager.insertbankdarah(mothername,donor,notelponss,radioStatus,radioStatus2);
                //  dbManager.close();
                //  }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.dokter:
                if (checked)
                    setPenolongPersalinan("dokter");
                break;
            case R.id.bidan:
                if (checked)
                    setPenolongPersalinan("bidan");
                break;
            case R.id.dukun:
                if (checked)
                    setPenolongPersalinan("dukun");
                break;
            case R.id.lainnya:
                if (checked)
                    setPenolongPersalinan("lainnya");
                break;
            case R.id.rs:
                if (checked)
                    setTempatPersalinan("rumahsakit");
                break;
            case R.id.polindes:
                if (checked)
                    setTempatPersalinan("polindes");
                break;
            case R.id.puskesmas:
                if (checked)
                    setTempatPersalinan("puskesmas");
                break;
            case R.id.rumah:
                if (checked)
                    setPenolongPersalinan("rumah");
                break;
            case R.id.tempat_lainnya:
                if (checked)
                    setPenolongPersalinan("lainnya");
                break;
            case R.id.suami:
                if (checked)
                    setPendampingPersalinan("suami");
                break;
            case R.id.ortu:
                if (checked)
                    setPendampingPersalinan("orangTua");
                break;

            case R.id.saudara:
                if (checked)
                    setPendampingPersalinan("saudara");
                break;

            case R.id.hub_lainnya:
                if (checked)
                    setPendampingPersalinan("lainnya");
                break;

            case R.id.hub_suami:
                if (checked)
                    setHubunganPemilik("suami");
                break;

            case R.id.anggota:
                if (checked)
                    setHubunganPemilik("anggotaKeluarga");
                break;

            case R.id.pend_suami:
                if (checked)
                    setHubunganPendonor("suami");
                break;
            case R.id.pend_saudara:
                if (checked)
                    setHubunganPendonor("saudara");
                break;
            case R.id.pend_ibu:
                if (checked)
                    setHubunganPendonor("ibu");
                break;
            case R.id.pend_ayah:
                if (checked)
                    setHubunganPendonor("ayah");
                break;
            case R.id.pend_lainnya:
                if (checked)
                    setHubunganPendonor("lainnya");
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
