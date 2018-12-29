package theseinitiatives.atma.client.activity.nativeform;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class FormRencanaPersalinan extends AppCompatActivity {
    EditText transportasiNama;
    EditText nama_donors;

    private boolean isPreloaded = false;

    private CheckBox checkBoxSuami;
    private CheckBox checkBoxOrangTua;
    private CheckBox checkBoxSaudara;
    private CheckBox checkBoxMertua;
    private CheckBox checkBoxLainnya;

    private RadioButton dokter,bidan,dukun,penolongLainnya;
    private RadioButton rumahSakit,puskesmas,polindes,rumah,tempatLainnya;
    private RadioButton suami,keluarga,tetangga,pemilikLain;
    private RadioButton donorSuami,donorSaudara,donorIbu,donorAyah,donorLainnya;

    private AutoCompleteTextView pemilikTransportasi;
    private AutoCompleteTextView calonPendonor;

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
        String temp = ""
                + (checkBoxSuami.isChecked() ?getString(R.string.suami)+"," : "")
                + (checkBoxOrangTua.isChecked() ?getString(R.string.ortu)+"," : "")
                + (checkBoxSaudara.isChecked() ?getString(R.string.saudara)+"," : "")
                + (checkBoxMertua.isChecked() ?getString(R.string.mertua)+ "," : "")
                + (checkBoxLainnya.isChecked() ?getString(R.string.lainnya)+ "," : "")
                ;
        if(temp.length()>1)
            temp = temp.substring(0,temp.length()-1);
        return temp;
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
    String uniqueId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_rencana_persalinan);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");

        dokter = (RadioButton) findViewById(R.id.dokter);
        bidan = (RadioButton) findViewById(R.id.bidan);
        dukun = (RadioButton) findViewById(R.id.dukun);
        penolongLainnya = (RadioButton) findViewById(R.id.lainnya);

        rumahSakit = (RadioButton) findViewById(R.id.rs);
        puskesmas = (RadioButton) findViewById(R.id.puskesmas);
        polindes = (RadioButton) findViewById(R.id.polindes);
        rumah = (RadioButton) findViewById(R.id.rumah);
        tempatLainnya = (RadioButton) findViewById(R.id.tempat_lainnya);

        suami = (RadioButton) findViewById(R.id.hub_suami);
        keluarga = (RadioButton) findViewById(R.id.anggota);
        tetangga = (RadioButton) findViewById(R.id.tetangga);
        pemilikLain = (RadioButton) findViewById(R.id.hub_lainnya);

        donorSuami = (RadioButton) findViewById(R.id.pend_suami);
        donorSaudara = (RadioButton) findViewById(R.id.pend_saudara);
        donorIbu = (RadioButton) findViewById(R.id.pend_ibu);
        donorAyah = (RadioButton) findViewById(R.id.pend_ayah);
        donorLainnya = (RadioButton) findViewById(R.id.pend_lainnya);

        pemilikTransportasi = (AutoCompleteTextView) findViewById(R.id.transportsis);
        calonPendonor = (AutoCompleteTextView) findViewById(R.id.calon_pendonor);

        checkBoxSuami = (CheckBox) findViewById(R.id.suami);
        checkBoxOrangTua = (CheckBox) findViewById(R.id.ortu);
        checkBoxMertua = (CheckBox) findViewById(R.id.mertua);
        checkBoxSaudara = (CheckBox) findViewById(R.id.saudara);
        checkBoxLainnya = (CheckBox) findViewById(R.id.pendamping_lainnya);
       // dbManager.open();
       // nama_donors = (EditText) findViewById(R.id.calon_pendonor);
      //  transportasiNama = (EditText) findViewById(R.id.transportsis);

        btnLogin = (Button) findViewById(R.id.saved);

        dbManager = new DbManager(this);
        dbManager.open();
        Cursor c = dbManager.fetchuniqueId(idIbu);
        c.moveToFirst();
        uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
        Log.e("UNIQUE======",uniqueId);

        Cursor cur = dbManager.fetchRencanaPersalinan(uniqueId);
        cur.moveToFirst();
        Log.e("Length of cursor",""+cur.getCount());
        if(cur.getCount()>0)
            preloadForm(cur);

        //==========================
        // Search Nama Pemilik
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, getNamaPemilik());
        //Find TextView control
        final AutoCompleteTextView transportasiNama = (AutoCompleteTextView) findViewById(R.id.transportsis);
        //Set the number of characters the user must type before the drop down list is shown
        transportasiNama.setThreshold(1);
        //Set the adapter
        transportasiNama.setAdapter(adapter);

        //==========================
        // Search Nama Donor
        final ArrayAdapter<String> adapterDonor = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, getNamaDonor());
        //Find TextView control
        final AutoCompleteTextView nama_donors = (AutoCompleteTextView) findViewById(R.id.calon_pendonor);
        //Set the number of characters the user must type before the drop down list is shown
        nama_donors.setThreshold(1);
        //Set the adapter
        nama_donors.setAdapter(adapterDonor);




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
                Date currentTime = Calendar.getInstance().getTime();
                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.ID_IBU,uniqueId);
                    dataArray.put(DbHelper.NAME_PENDONOR, namaDonor);
                    dataArray.put(DbHelper.TEMPAT_PERSALINAN,txt_tempatBersalin);
                    dataArray.put(DbHelper.PENOLONG_PERSALINAN,txt_penolognPersalinan);
                    dataArray.put(DbHelper.PENDAMPING_PERSALINAN,txt_pendampingPersalinan);
                    dataArray.put(DbHelper.HUBUNGAN_DENGAN_IBU,txt_hubunganPemilik);
                    dataArray.put(DbHelper.HUBUNGAN_PENDONOR_IBU,txt_hubunganPendonor);
                    dataArray.put(DbHelper.NAME_PEMILIK,namaTransportasi);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());


                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }


                if( namaDonor.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    dbManager.open();
                    if(isPreloaded) {
                        dbManager.updateRencanaPersalinan(uniqueId, namaDonor, txt_tempatBersalin, txt_penolognPersalinan, txt_pendampingPersalinan, txt_hubunganPemilik, txt_hubunganPendonor, namaTransportasi, System.currentTimeMillis());
                        dbManager.insertsyncTable("rencana_persalinan_edit", System.currentTimeMillis(), dataArray.toString(), 0, 0);
                    }else {
                        dbManager.insertRencanaPersalinan(uniqueId, namaDonor, txt_tempatBersalin, txt_penolognPersalinan, txt_pendampingPersalinan, txt_hubunganPemilik, txt_hubunganPendonor, namaTransportasi, System.currentTimeMillis());
                        dbManager.insertsyncTable("rencana_persalinan", System.currentTimeMillis(), dataArray.toString(), 0, 0);
                    }dbManager.close();
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
                    setTempatPersalinan("rumah");
                break;
            case R.id.tempat_lainnya:
                if (checked)
                    setTempatPersalinan("lainnya");
                break;

            case R.id.hub_suami:
                if (checked)
                    setHubunganPemilik("suami");
                break;

            case R.id.anggota:
                if (checked)
                    setHubunganPemilik("anggotaKeluarga");
                break;

            case R.id.tetangga:
                if (checked)
                    setHubunganPemilik("tetangga");
                break;

            case R.id.hub_lainnya:
                if (checked)
                    setHubunganPemilik("lainnya");
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

    public String[] getNamaPemilik() {
        dbManager.open();
        Cursor cursor = dbManager.fetchnamapemilik();
        ArrayList<String> names = new ArrayList<String>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbManager.close();

      //  String[] languagess = { "Budi","Joni","Bravo" };
        return names.toArray(new String[0]);
       // return languagess;
    }

    public String[] getNamaDonor() {
        dbManager.open();
        Cursor cursor = dbManager.fetchnamaDonor();
        ArrayList<String> names = new ArrayList<String>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(cursor.getColumnIndexOrThrow("name_pendonor")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbManager.close();

        //  String[] languagess = { "Budi","Joni","Bravo" };
        return names.toArray(new String[0]);
        // return languagess;
    }

    private void preloadForm(Cursor c){
        if(c==null)
            return;
        isPreloaded = true;
        String penolongPersalinan = c.getString(c.getColumnIndexOrThrow(DbHelper.PENOLONG_PERSALINAN));
        String tempatPersalinan = c.getString(c.getColumnIndexOrThrow(DbHelper.TEMPAT_PERSALINAN));
        String pendampingPersalinan = c.getString(c.getColumnIndexOrThrow(DbHelper.PENDAMPING_PERSALINAN));
        String hubunganIbu = c.getString(c.getColumnIndexOrThrow(DbHelper.HUBUNGAN_DENGAN_IBU));
        String namePendonor = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME_PENDONOR));
        String pemilikKendaraan = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME_PEMILIK));
        String hubunganPendonor = c.getString(c.getColumnIndexOrThrow(DbHelper.HUBUNGAN_PENDONOR_IBU));

        checkPenolongPersalinan(penolongPersalinan);
        checkTempatBersalin(tempatPersalinan);
        checkPendampingPersalinan(pendampingPersalinan);
        pemilikTransportasi.setText(pemilikKendaraan);
        checkPemilikTransportasi(hubunganIbu);
        calonPendonor.setText(namePendonor);
        checkDonor(hubunganPendonor);

    }

    private void checkPenolongPersalinan(String value){
        switch (value){
            case "dokter" : dokter.setChecked(true);break;
            case "bidan" : bidan.setChecked(true);break;
            case "dukun" : dukun.setChecked(true);break;
            case "lainnya" : penolongLainnya.setChecked(true);break;
        }
    }

    private void checkTempatBersalin(String value){
        switch (value){
            case "rumahsakit" : rumahSakit.setChecked(true);break;
            case "polindes" : polindes.setChecked(true);break;
            case "puskesmas" : puskesmas.setChecked(true);break;
            case "rumah" : rumah.setChecked(true);break;
            case "lainnya" : tempatLainnya.setChecked(true);break;
        }
    }

    private void checkPendampingPersalinan(String value){
        if(value.contains(getString(R.string.suami)))
            checkBoxSuami.setChecked(true);
        if(value.contains(getString(R.string.ortu)))
            checkBoxOrangTua.setChecked(true);
        if(value.contains(getString(R.string.mertua)))
            checkBoxMertua.setChecked(true);
        if(value.contains(getString(R.string.saudara)))
            checkBoxSaudara.setChecked(true);
        if(value.contains(getString(R.string.lainnya)))
            checkBoxLainnya.setChecked(true);
    }

    private void checkPemilikTransportasi(String value){
        switch (value){
            case "suami" : suami.setChecked(true);break;
            case "anggotaKeluarga" : keluarga.setChecked(true);break;
            case "tetangga" : tetangga.setChecked(true);break;
            case "lainnya" : pemilikLain.setChecked(true);break;
        }
    }

    private void checkDonor(String value){
        switch (value){
            case "suami" : donorSuami.setChecked(true);break;
            case "saudara" : donorSaudara.setChecked(true);break;
            case "ibu" : donorIbu.setChecked(true);break;
            case "ayah" : donorAyah.setChecked(true);break;
            case "lainnya" : donorLainnya.setChecked(true);break;
        }
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }


}
