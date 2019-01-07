package theseinitiatives.atma.client.activity.nativeform;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class FormStatusPersalinanActivity extends AppCompatActivity {

    private RadioButton hamil,nifas,risti;
    EditText tgl_bersalin;
    String   statusibu;
    String   kondisiibu;
    String   kondisianak;
    String   nifasberakhir;
    LinearLayout layout_nifas;
    EditText jumlahBayi;
    String jenisKelamin;
    LinearLayout lay_tempat;
    LinearLayout lay_ibu;
    LinearLayout lay_anak;
    String   tempatBersalin;
    String   komplikasiIbu;
    String   komplikasiAnak;
    EditText lainnya_tempats;

    private boolean isPreloaded = false;

    AutoCompleteTextView resikoIbuLainnya, resikoAnakLainnya;

    CheckBox ibuPendarahanBerat, ibuPEB, ibuEklampsia, ibuSepsis, ibuResikoLainnya;
    CheckBox bayiPrematur, bayiBBLR, bayiAsfiksia, bayiResikoLainnya;

    RadioButton radioHamil,radioNifas,ibuHidup,ibuMeninggal,anakHidup,anakMeninggal;
    RadioButton rumahSakit,puskesmas,polindes,rumah,tempatLainnya,laki,perempuan;


    public String getTempatBersalin() {
        return tempatBersalin;
    }

    public void setTempatBersalin(String tempatBersalin) {
        this.tempatBersalin = tempatBersalin;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String value) {
        jenisKelamin = value;
    }

    public String getKomplikasiIbu() {
        komplikasiIbu = ""+
                (ibuPendarahanBerat.isChecked() ? "pendarahan," : "")+
                (ibuPEB.isChecked() ? "peb," : "")+
                (ibuEklampsia.isChecked() ? "eklampsia," : "")+
                (ibuSepsis.isChecked() ? "sepsis," : "")+
                (ibuResikoLainnya.isChecked() ?  resikoIbuLainnya.getText()+",": "")
        ;
        if(komplikasiIbu.length()>3)
            komplikasiIbu = komplikasiIbu.substring(0,komplikasiIbu.length()-1);
        return komplikasiIbu;
    }

    public void setKomplikasiIbu(String komplikasiIbu) {
        this.komplikasiIbu = komplikasiIbu;
    }

    public String getKomplikasiAnak() {
        komplikasiAnak = ""+
                (bayiPrematur.isChecked() ? "prematur," : "")+
                (bayiBBLR.isChecked() ? "bblr," : "")+
                (bayiAsfiksia.isChecked() ? "asfiksia," : "")+
                (bayiResikoLainnya.isChecked() ?  resikoAnakLainnya.getText()+",": "")
        ;
        if(komplikasiAnak.length()>3)
            komplikasiAnak = komplikasiAnak.substring(0,komplikasiAnak.length()-1);
        return komplikasiAnak;
    }

    public void setKomplikasiAnak(String komplikasiAnak) {
        this.komplikasiAnak = komplikasiAnak;
    }



    public String getNifasberakhir() {
        return nifasberakhir;
    }

    public void setNifasberakhir(String nifasberakhir) {
        this.nifasberakhir = nifasberakhir;
    }

    public String getStatusibu() {
        return statusibu;
    }

    public void setStatusibu(String statusibu) {
        this.statusibu = statusibu;
    }

    public String getKondisiibu() {
        return kondisiibu;
    }

    public void setKondisiibu(String kondisiibu) {
        this.kondisiibu = kondisiibu;
    }

    public String getKondisianak() {
        return kondisianak;
    }

    public void setKondisianak(String kondisianak) {
        this.kondisianak = kondisianak;
    }

    private DbManager dbManager;


    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    String Statuss;

    public String getDarah() {
        return darah;
    }

    public void setDarah(String darah) {
        this.darah = darah;
    }

    String darah;
    public String getStatuss2() {
        return Statuss2;
    }

    public void setStatuss2(String statuss2) {
        Statuss2 = statuss2;
    }

    String Statuss2;
    Button btnLogin;
    String uniqueId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_status_persalinan);
       // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        initVariable();
        final String idIbu = getIntent().getStringExtra("id");
        tgl_bersalin = (EditText) findViewById(R.id.tgl_persalinan);
        layout_nifas = (LinearLayout) findViewById(R.id.layout_nifas);
        lay_tempat = (LinearLayout) findViewById(R.id.tempat_layout);
        lay_ibu = (LinearLayout) findViewById(R.id.ibu_layout);
        lay_anak = (LinearLayout) findViewById(R.id.anak_layout);



        jumlahBayi = (EditText) findViewById(R.id.jumlah);
       //        jenisKelamin = (EditText) findViewById(R.id.jenis_kel);
        jumlahBayi = (EditText) findViewById(R.id.jumlah);
        resikoIbuLainnya = (AutoCompleteTextView) findViewById(R.id.lainnya_komplikasiibu);
        resikoAnakLainnya = (AutoCompleteTextView) findViewById(R.id.lainnya_komplikasianak);
        lainnya_tempats = (EditText) findViewById(R.id.lainnya_tempat);

        dbManager = new DbManager(this);
        dbManager.open();
        Cursor c = dbManager.fetchuniqueId(idIbu);
        c.moveToFirst();
        uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));

        Cursor cur = dbManager.fetchstatuspersalinan(uniqueId);
        if(cur.getCount()>0){
            isPreloaded = true;
            preloadForm(cur);
        }

        dbManager.close();
        Log.e("UNIQUE======",uniqueId);


        tgl_bersalin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s=monthOfYear+1;
                        String a = dayOfMonth+"-"+s+"-"+year;
                        tgl_bersalin.setText(""+a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(FormStatusPersalinanActivity.this, dpd, date.year ,date.month, date.monthDay);
                d.show();

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jumlahBayis = jumlahBayi.getText().toString();
                String jenisKelamins = getJenisKelamin();
                String tgl_persalinn = tgl_bersalin.getText().toString();
                String radioStatus2 = getStatuss2();
                String ibubersalin = getStatusibu();
                String kondisi_ibu = getKondisiibu();
                String kondisi_anak = getKondisianak();
                String komplikasiIbus = getKomplikasiIbu();
                String komplikasiAnak = getKomplikasiAnak();
                String tempat = getTempatBersalin();
                String tempatLaiinya = lainnya_tempats.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                JSONObject dataArray = new JSONObject();
                String textDusun = getDusun(idIbu);
                try {
                    dataArray.put(DbHelper.ID_IBU,uniqueId);
                    dataArray.put(DbHelper.STATUS_BERSALIN, ibubersalin);
                    dataArray.put(DbHelper.TGL_PERSALINAN,tgl_persalinn);
                    dataArray.put(DbHelper.KONDISI_ANAK,kondisi_anak);
                    dataArray.put(DbHelper.KONDISI_IBU,kondisi_ibu);
                    dataArray.put(DbHelper.JUMLAHBAYI,jumlahBayis);
                    dataArray.put(DbHelper.JENISKELAMIN,jenisKelamins);
                    dataArray.put(DbHelper.KOMPLIKASIIBU,komplikasiIbus);
                    dataArray.put(DbHelper.KOMPLIKASIANAK,komplikasiAnak);

                    dataArray.put(DbHelper.DUSUN,textDusun);
                    dataArray.put(DbHelper.TEMPAT_PERSALINAN,tempat);
                    dataArray.put(DbHelper.TEMPAT_LAIN,tempatLaiinya);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());
                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }

                    dbManager.open();
                    if(isPreloaded) {
                        dbManager.updateStatusPersalinan(uniqueId, tgl_persalinn, ibubersalin, kondisi_ibu, kondisi_anak, jumlahBayis, jenisKelamins, komplikasiIbus, komplikasiAnak, tempat, tempatLaiinya, textDusun);
                        dbManager.insertsyncTable("status_persalinan_edit", System.currentTimeMillis(),textDusun, dataArray.toString(), 0, 0);
                    }
                    else{
                        dbManager.insertStatusPersalinan(uniqueId, tgl_persalinn, ibubersalin, kondisi_ibu, kondisi_anak, jumlahBayis, jenisKelamins, komplikasiIbus, komplikasiAnak, tempat,tempatLaiinya, textDusun);
                        dbManager.insertsyncTable("status_persalinan", System.currentTimeMillis(),textDusun, dataArray.toString(), 0, 0);
                    }
                    dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormStatusPersalinanActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);




            }
        });
    }

    private void preloadForm(Cursor c){
        if(c==null)
            return;
        //Data Radio Button
        String statusIbu = c.getString(c.getColumnIndexOrThrow(DbHelper.STATUS_BERSALIN));
        String kondisiIbu = c.getString(c.getColumnIndexOrThrow(DbHelper.KONDISI_IBU));
        String kondisiAnak= c.getString(c.getColumnIndexOrThrow(DbHelper.KONDISI_ANAK));
        String tempatPersalinan = c.getString(c.getColumnIndexOrThrow(DbHelper.TEMPAT_PERSALINAN));
        String gender = c.getString(c.getColumnIndexOrThrow(DbHelper.JENISKELAMIN));

//        Log.e("statusIbu",statusIbu);
//        Log.e("kondisiIbu",kondisiIbu);
//        Log.e("kondisi Anak",kondisiAnak);
//        Log.e("tempat Persalinan",tempatPersalinan);
//        Log.e("gender",gender);



        //Data Text Field
        String tanggalPersalinan = c.getString(c.getColumnIndexOrThrow(DbHelper.TGL_PERSALINAN));
        String jumlahBayi = c.getString(c.getColumnIndexOrThrow(DbHelper.JUMLAHBAYI));

        tgl_bersalin.setText(tanggalPersalinan);
        this.jumlahBayi.setText(jumlahBayi);

        //Data CheckBox
        String komplikasiIbu = c.getString(c.getColumnIndexOrThrow(DbHelper.KOMPLIKASIIBU));
        String komplikasiAnak = c.getString(c.getColumnIndexOrThrow(DbHelper.KOMPLIKASIANAK));


        checkStatus(statusIbu);
        checkKondisiIbu(kondisiIbu);
        checkKondisiAnak(kondisiAnak);
        checkTempatBersalin(tempatPersalinan);
        checkGender(gender);

        checkKomplikasiIbu(komplikasiIbu);
        checkKomplikasiAnak(komplikasiAnak);
    }

    private void initVariable(){
        ibuPendarahanBerat = (CheckBox) findViewById(R.id.perdarahan);
        ibuPEB = (CheckBox) findViewById(R.id.peb);
        ibuEklampsia = (CheckBox) findViewById(R.id.eklamsi);
        ibuSepsis = (CheckBox) findViewById(R.id.sepsis);
        ibuResikoLainnya = (CheckBox)findViewById(R.id.k_lainnya);

        bayiPrematur = (CheckBox) findViewById(R.id.prem);
        bayiBBLR = (CheckBox) findViewById(R.id.bblr);
        bayiAsfiksia = (CheckBox) findViewById(R.id.asfiksia);
        bayiResikoLainnya = (CheckBox) findViewById(R.id.k_babylainnya);

        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();

        radioHamil = (RadioButton) findViewById(R.id.hamil);
        radioNifas = (RadioButton) findViewById(R.id.nifas);
        ibuHidup = (RadioButton) findViewById(R.id.ibu_hidup);
        ibuMeninggal = (RadioButton) findViewById(R.id.ibu_mati);
        anakHidup = (RadioButton) findViewById(R.id.anak_hidup);
        anakMeninggal = (RadioButton) findViewById(R.id.anak_mati);

        rumahSakit = (RadioButton) findViewById(R.id.rs);
        puskesmas = (RadioButton) findViewById(R.id.puskesmas);
        polindes = (RadioButton) findViewById(R.id.polindes);
        rumah = (RadioButton) findViewById(R.id.rumah);
        tempatLainnya = (RadioButton) findViewById(R.id.tempat_lainnya);

        laki = (RadioButton) findViewById(R.id.lakilaki);
        perempuan = (RadioButton) findViewById(R.id.perempuan);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {


            // set nifas
            case R.id.hamil:
                if (checked)
                    setStatusibu("hamil");
                    layout_nifas.setVisibility(GONE);
                break;
            case R.id.nifas:
                if (checked)
                    setStatusibu("nifas");
                    layout_nifas.setVisibility(VISIBLE);
                break;
            case R.id.ibu_hidup:
                if (checked)
                    setKondisiibu("hidup");
                break;
            case R.id.ibu_mati:
                if (checked)
                    setKondisiibu("meninggal");
                break;
            case R.id.anak_hidup:
                if (checked)
                    setKondisianak("hidup");
                break;
            case R.id.anak_mati:
                if (checked)
                    setKondisianak("meninggal");
                break;

                // set gender
            case R.id.lakilaki:
                if (checked)
                    setJenisKelamin("laki-laki");
                break;
            case R.id.perempuan:
                if (checked)
                    setJenisKelamin("perempuan");
                break;

                //set tempat
            case R.id.rs:
                if (checked)
                    setTempatBersalin("rumahsakit");
                    lay_tempat.setVisibility(GONE);
                break;
            case R.id.polindes:
                if (checked)
                    setTempatBersalin("polindes");
                    lay_tempat.setVisibility(GONE);
                break;
            case R.id.puskesmas:
                if (checked)
                    setTempatBersalin("puskesmas");
                    lay_tempat.setVisibility(GONE);
                break;
            case R.id.rumah:
                if (checked)
                    setTempatBersalin("rumah");
                   lay_tempat.setVisibility(GONE);
                break;
            case R.id.tempat_lainnya:
                if (checked)
                    setTempatBersalin("lainnya");
                    lay_tempat.setVisibility(VISIBLE);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()){
            case R.id.k_lainnya:
                if(checked)
                    lay_ibu.setVisibility(VISIBLE);
                else
                    lay_ibu.setVisibility(INVISIBLE);
            break;
            case R.id.k_babylainnya:
                if(checked)
                    lay_anak.setVisibility(VISIBLE);
                else
                    lay_anak.setVisibility(INVISIBLE);
                break;
        }
    }

    private void checkKomplikasiIbu(String data){
        if(data==null)
            return;
        data = data+",";
        if(data.contains("pendarahan")){
            ibuPendarahanBerat.setChecked(true);
            data = data.replace("perdarahan,","");
        }
        if(data.contains("peb")){
            ibuPEB.setChecked(true);
            data = data.replace("peb,","");
        }
        if(data.contains("eklamsi")){
            ibuEklampsia.setChecked(true);
            data = data.replace("eklamsi,","");
        }
        if(data.contains("sepsis")){
            ibuSepsis.setChecked(true);
            data = data.replace("sepsis,","");
        }
        if(data.length()>3){
            ibuResikoLainnya.setChecked(true);
            resikoIbuLainnya.setText(data.substring(0,data.length()-1));
        }

    }

    private void checkKomplikasiAnak(String data){
        if(data==null)
            return;
        data = data+",";
        if(data.contains("prematur")){
            bayiPrematur.setChecked(true);
            data = data.replace("prematur,","");
        }
        if(data.contains("bblr")){
            bayiBBLR.setChecked(true);
            data = data.replace("bblr,","");
        }
        if(data.contains("asfiksia")){
            bayiAsfiksia.setChecked(true);
            data = data.replace("asfiksia,","");
        }
        if(data.length()>3){
            bayiResikoLainnya.setChecked(true);
            resikoAnakLainnya.setText(data.substring(0,data.length()-1));
        }

    }

    private void checkStatus(String view){
        if(view==null)
            return;
        setStatusibu(view);
        switch(view){
            case "hamil" : radioHamil.setChecked(true);break;
            case "nifas" : radioNifas.setChecked(true);layout_nifas.setVisibility(VISIBLE);break;
        }
    }

    private void checkKondisiIbu(String view){
        if(view==null)
            return;
        setKondisiibu(view);
        switch(view){
            case "hidup" : ibuHidup.setChecked(true);break;
            case "meninggal" : ibuMeninggal.setChecked(true);break;
        }
    }

    private void checkKondisiAnak(String view){
        if(view==null)
            return;
        Log.e("checkKondisiAnak",view);
        setKondisianak(view);
        switch(view){
            case "hidup" : anakHidup.setChecked(true);break;
            case "meninggal" : anakMeninggal.setChecked(true);break;
        }
    }

    private void checkTempatBersalin(String view){
        if(view==null)
            return;
        setTempatBersalin(view);
        Log.e("cekTempatBersalin",view);
        switch(view){
            case "rumahsakit" : rumahSakit.setChecked(true);break;
            case "puskesmas" : puskesmas.setChecked(true);break;
            case "polindes" : polindes.setChecked(true);break;
            case "rumah" : rumah.setChecked(true);break;
            case "lainnya" : tempatLainnya.setChecked(true);break;
        }
    }

    private void checkGender(String view){
        if(view==null)
            return;
        Log.e("checkGender",view);
        setJenisKelamin(view);
        switch(view){
            case "laki-laki" : laki.setChecked(true);break;
            case "perempuan" : perempuan.setChecked(true);break;
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
}
