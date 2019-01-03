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
import android.widget.LinearLayout;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class FormRencanaPersalinan extends AppCompatActivity {
    EditText transportasiNama;
    EditText nama_donors;

    LinearLayout penolonglainLayouts;
    EditText txt_penolonglains;

    LinearLayout tempatlainLayouts;
    EditText txt_tempatlains;

    LinearLayout pendampinglainLayouts;
    EditText txt_pendampinglains;


    LinearLayout hubungantranslainLayouts;
    EditText txt_hubungantranslains;

    LinearLayout hubungandonorlainLayouts;
    EditText txt_hubungandonorlains;

    String DonorId;
    String TransId;
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
        final String uniqueid = getIntent().getStringExtra("uniqueId");
        penolonglainLayouts = (LinearLayout) findViewById(R.id.penolonglainLayout);
        txt_penolonglains = (EditText) findViewById(R.id.txt_penolonglain);

        penolonglainLayouts = (LinearLayout) findViewById(R.id.penolonglainLayout);
        txt_penolonglains = (EditText) findViewById(R.id.txt_penolonglain);

        tempatlainLayouts = (LinearLayout) findViewById(R.id.tempatlainLayout);
        txt_tempatlains = (EditText) findViewById(R.id.txt_tempatlain);

        pendampinglainLayouts = (LinearLayout) findViewById(R.id.pendampinglainLayout);
        txt_pendampinglains = (EditText) findViewById(R.id.txt_pendampinglain);

        hubungantranslainLayouts = (LinearLayout) findViewById(R.id.hubungantranslainLayout);
        txt_hubungantranslains = (EditText) findViewById(R.id.txt_hubungantranslain);

        hubungandonorlainLayouts = (LinearLayout) findViewById(R.id.hubungandonorlainLayout);
        txt_hubungandonorlains = (EditText) findViewById(R.id.txt_hubungandonorlain);

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


        //clear setter
        setPenolongPersalinan("");
        setTempatPersalinan("");
        setPenolongPersalinan("");
        setHubunganPemilik("");
        setHubunganPendonor("");
        /*dbManager = new DbManager(this);
        dbManager.open();
        Cursor c = dbManager.fetchuniqueId(idIbu);
        c.moveToFirst();
        uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
        Log.e("UNIQUE======",uniqueId);
*/     dbManager = new DbManager(this);
        dbManager.open();
        Cursor cur = dbManager.fetchRencanaPersalinan(uniqueid);
        ;
        Log.e("Length of cursor",""+cur.getCount());
        if(cur.moveToFirst())
            preloadForm(cur);
        dbManager.close();

        //==========================
        // Search Nama Pemilik
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, getNamaPemilik());
        //Find TextView control
        final AutoCompleteTextView pemilikTransportasi = (AutoCompleteTextView) findViewById(R.id.transportsis);
        //Set the number of characters the user must type before the drop down list is shown
        pemilikTransportasi.setThreshold(1);
        //Set the adapter
        pemilikTransportasi.setAdapter(adapter);

        //==========================
        // Search Nama Donor
        final ArrayAdapter<String> adapterDonor = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, getNamaDonor());
        //Find TextView control
        final AutoCompleteTextView calonPendonor = (AutoCompleteTextView) findViewById(R.id.calon_pendonor);
        //Set the number of characters the user must type before the drop down list is shown
        calonPendonor.setThreshold(1);
        //Set the adapter
        calonPendonor.setAdapter(adapterDonor);


        //Log.e("NAMADUSUN",getDusun(idIbu));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String mothername = mother_names.getText().toString();
              //  String idIbu = id;
                String namaDonor = calonPendonor.getText().toString();

                //get id Donor
                dbManager.open();
                dbManager.setSelection(DbHelper.NAME_PENDONOR +"='"+namaDonor+"'");
                Cursor donor_cur = dbManager.fetchBankDarah("","");
                if(donor_cur != null) {
                    if (donor_cur.moveToFirst()) {
                        DonorId = donor_cur.getString(donor_cur.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    }
                    else
                        DonorId  ="";
                }
                donor_cur.close();
                dbManager.close();

                String namaTransportasi = pemilikTransportasi.getText().toString();
                //get id trans
                dbManager.open();
                dbManager.setSelection(DbHelper.NAME +"='"+namaTransportasi+"'");
                Cursor cursor = dbManager.fetchTrans("","");
                if(cursor != null){
                    if(cursor.moveToFirst()){
                         TransId = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.UNIQUEID));
                    }
                    else
                        TransId = "";

                }
                cursor.close();
                dbManager.close();

                Log.e("donor", DonorId);
                Log.e("TRANS", TransId);

                String txt_penolognPersalinan = getPenolongPersalinan();
                String txt_tempatBersalin = getTempatPersalinan();
                String txt_pendampingPersalinan = getPendampingPersalinan();
                String txt_hubunganPemilik = getHubunganPemilik();
                String txt_hubunganPendonor = getHubunganPendonor();
                Date currentTime = Calendar.getInstance().getTime();
                String textDusun = getDusun(idIbu);



                String penolongLain = txt_penolonglains.getText().toString();
                String tempatLain = txt_tempatlains.getText().toString();
                String pendampingLain = txt_pendampinglains.getText().toString();
                String hubTransLain = txt_hubungantranslains.getText().toString();
                String hubDonorLain = txt_hubungandonorlains.getText().toString();



                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.ID_IBU,uniqueid);
                    dataArray.put(DbHelper.ID_TRANS,TransId);
                    dataArray.put(DbHelper.ID_DONOR,DonorId);
                    dataArray.put(DbHelper.NAME_PENDONOR, namaDonor);
                    dataArray.put(DbHelper.TEMPAT_PERSALINAN,txt_tempatBersalin);
                    dataArray.put(DbHelper.PENOLONG_PERSALINAN,txt_penolognPersalinan);
                    dataArray.put(DbHelper.PENDAMPING_PERSALINAN,txt_pendampingPersalinan);
                    dataArray.put(DbHelper.HUBUNGAN_DENGAN_IBU,txt_hubunganPemilik);
                    dataArray.put(DbHelper.HUBUNGAN_PENDONOR_IBU,txt_hubunganPendonor);
                    dataArray.put(DbHelper.NAME_PEMILIK,namaTransportasi);
                    dataArray.put(DbHelper.DUSUN,textDusun);
                    dataArray.put(DbHelper.PENOLONG_LAIN,penolongLain);
                    dataArray.put(DbHelper.TEMPAT_LAIN,tempatLain);
                    dataArray.put(DbHelper.PENDAMPING_LAIN,pendampingLain);
                    dataArray.put(DbHelper.HUB_TRANS_LAIN,hubTransLain);
                    dataArray.put(DbHelper.HUB_DONOR_LAIN,hubDonorLain);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());


                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }

                if( namaTransportasi.isEmpty() || namaDonor.isEmpty() || txt_penolognPersalinan.isEmpty() | txt_tempatBersalin.isEmpty() | txt_pendampingPersalinan.isEmpty() || txt_hubunganPemilik.isEmpty() || txt_hubunganPendonor.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Semua data harus diisi!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if( TransId.equalsIgnoreCase("") || DonorId.equalsIgnoreCase("") ) {
                    Toast.makeText(getApplicationContext(), "Data Pendonor / Pemilik Transportasi tidak valid, " +
                                    "silahkan isi data di  Form transportasi / Donor Darah terlebih dahulu!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if( namaDonor.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                    return;
                }


                else {
                    dbManager.open();
                    if(isPreloaded) {
                        dbManager.updateRencanaPersalinan(uniqueid,TransId,DonorId, namaDonor, txt_tempatBersalin, txt_penolognPersalinan, txt_pendampingPersalinan, txt_hubunganPemilik, txt_hubunganPendonor, namaTransportasi, System.currentTimeMillis(),penolongLain,tempatLain,pendampingLain,hubTransLain,hubDonorLain,textDusun);
                        dbManager.insertsyncTable("rencana_persalinan_edit", System.currentTimeMillis(), textDusun,dataArray.toString(), 0, 0);
                    }else {
                        dbManager.insertRencanaPersalinan(uniqueid, TransId,DonorId,namaDonor, txt_tempatBersalin, txt_penolognPersalinan, txt_pendampingPersalinan, txt_hubunganPemilik, txt_hubunganPendonor, namaTransportasi, System.currentTimeMillis(),penolongLain,tempatLain,pendampingLain,hubTransLain,hubDonorLain,textDusun);
                        dbManager.insertsyncTable("rencana_persalinan", System.currentTimeMillis(),textDusun, dataArray.toString(), 0, 0);
                    }dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormRencanaPersalinan.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);

                }

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
                    penolonglainLayouts.setVisibility(GONE);

                break;
            case R.id.bidan:
                if (checked)
                    setPenolongPersalinan("bidan");
                    penolonglainLayouts.setVisibility(GONE);

                break;
            case R.id.dukun:
                if (checked)
                    setPenolongPersalinan("dukun");
                    penolonglainLayouts.setVisibility(GONE);

                break;
            case R.id.lainnya:
                if (checked){
                    setPenolongPersalinan("lainnya");
                    penolonglainLayouts.setVisibility(View.VISIBLE);}
                break;
            case R.id.rs:
                if (checked)
                    setTempatPersalinan("rumahsakit");
                    tempatlainLayouts.setVisibility(GONE);

                break;
            case R.id.polindes:
                if (checked)
                    setTempatPersalinan("polindes");
                    tempatlainLayouts.setVisibility(GONE);

                break;
            case R.id.puskesmas:
                if (checked)
                    setTempatPersalinan("puskesmas");
                    tempatlainLayouts.setVisibility(GONE);

                break;
            case R.id.rumah:
                if (checked)
                    setTempatPersalinan("rumah");
                    tempatlainLayouts.setVisibility(GONE);

                break;
            case R.id.tempat_lainnya:
                if (checked){
                    setTempatPersalinan("lainnya");
                    tempatlainLayouts.setVisibility(View.VISIBLE);}
                break;

            case R.id.hub_suami:
                if (checked)
                    setHubunganPemilik("suami");
                    hubungantranslainLayouts.setVisibility(GONE);

                break;

            case R.id.anggota:
                if (checked)
                    setHubunganPemilik("anggotaKeluarga");
                    hubungantranslainLayouts.setVisibility(GONE);

                break;

            case R.id.tetangga:
                if (checked)
                    setHubunganPemilik("tetangga");
                    hubungantranslainLayouts.setVisibility(GONE);

                break;

            case R.id.hub_lainnya:
                if (checked){
                    hubungantranslainLayouts.setVisibility(View.VISIBLE);
                    setHubunganPemilik("lainnya");}
                break;

            case R.id.pend_suami:
                if (checked)
                    setHubunganPendonor("suami");
                    hubungandonorlainLayouts.setVisibility(GONE);

                break;
            case R.id.pend_saudara:
                if (checked)
                    setHubunganPendonor("saudara");
                    hubungandonorlainLayouts.setVisibility(GONE);

                break;
            case R.id.pend_ibu:
                if (checked)
                    setHubunganPendonor("ibu");
                    hubungandonorlainLayouts.setVisibility(GONE);

                break;
            case R.id.pend_ayah:
                if (checked)
                    setHubunganPendonor("ayah");
                    hubungandonorlainLayouts.setVisibility(GONE);
                break;
            case R.id.pend_lainnya:
                if (checked){
                    setHubunganPendonor("lainnya");
                    hubungandonorlainLayouts.setVisibility(View.VISIBLE);}

                break;



            }
        }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.pendamping_lainnya:
                if (checked) {
                  //  setFaktorResiko("Lainya");
                    pendampinglainLayouts.setVisibility(VISIBLE);
                }
                else if(!checked) {
                    pendampinglainLayouts.setVisibility(View.GONE);
                }
                else
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
        String hubunganPendonor = c.getString(c.getColumnIndexOrThrow(DbHelper.HUBUNGAN_PENDONOR_IBU));

        String pemilikKendaraan = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME_PEMILIK));
        String namePendonor = c.getString(c.getColumnIndexOrThrow(DbHelper.NAME_PENDONOR));



        checkPenolongPersalinan(penolongPersalinan);
        checkTempatBersalin(tempatPersalinan);
        checkPendampingPersalinan(pendampingPersalinan);
        pemilikTransportasi.setText(pemilikKendaraan);
        checkPemilikTransportasi(hubunganIbu);
        calonPendonor.setText(namePendonor);
        checkDonor(hubunganPendonor);

    }

    private void checkPenolongPersalinan(String value){
        if(value==null)
            return;
        setPenolongPersalinan(value);
        switch (value){
            case "dokter" : dokter.setChecked(true);
            case "bidan" : bidan.setChecked(true);
            case "dukun" : dukun.setChecked(true);
            case "lainnya" : penolongLainnya.setChecked(true);
        }
    }

    private void checkTempatBersalin(String value){
        if(value==null)
            return;
        setTempatPersalinan(value);
        switch (value){
            case "rumahsakit" : rumahSakit.setChecked(true);break;
            case "polindes" : polindes.setChecked(true);break;
            case "puskesmas" : puskesmas.setChecked(true);break;
            case "rumah" : rumah.setChecked(true);break;
            case "lainnya" : tempatLainnya.setChecked(true);break;
        }
    }

    private void checkPendampingPersalinan(String value){
        setPendampingPersalinan(value);
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
        if(value==null)
            return;
        setHubunganPemilik(value);
        switch (value){
            case "suami" : suami.setChecked(true);break;
            case "anggotaKeluarga" : keluarga.setChecked(true);break;
            case "tetangga" : tetangga.setChecked(true);break;
            case "lainnya" : pemilikLain.setChecked(true);break;
        }
    }

    private void checkDonor(String value){
        if(value==null)
            return;
        setHubunganPendonor(value);
        switch (value){
            case "suami" : donorSuami.setChecked(true);break;
            case "saudara" : donorSaudara.setChecked(true);break;
            case "ibu" : donorIbu.setChecked(true);break;
            case "ayah" : donorAyah.setChecked(true);break;
            case "lainnya" : donorLainnya.setChecked(true);break;
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
        navi.backtoIbu();

    }


}
