package tgwofficial.atma.client.activity.nativeform;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FormStatusPersalinanActivity extends AppCompatActivity {

    private RadioButton hamil,nifas,risti;
    EditText tgl_bersalin;
    String   statusibu;
    String   kondisiibu;
    String   kondisianak;
    String   nifasberakhir;
    LinearLayout layout_nifas;
    EditText jumlahBayi;
    EditText jenisKelamin;
    LinearLayout lay_tempat;
    LinearLayout lay_ibu;
    LinearLayout lay_anak;

    public String getTempatBersalin() {
        return tempatBersalin;
    }

    public void setTempatBersalin(String tempatBersalin) {
        this.tempatBersalin = tempatBersalin;
    }

    public String getKomplikasiIbu() {
        return komplikasiIbu;
    }

    public void setKomplikasiIbu(String komplikasiIbu) {
        this.komplikasiIbu = komplikasiIbu;
    }

    public String getKomplikasiAnak() {
        return komplikasiAnak;
    }

    public void setKomplikasiAnak(String komplikasiAnak) {
        this.komplikasiAnak = komplikasiAnak;
    }

    String   tempatBersalin;
    String   komplikasiIbu;
    String   komplikasiAnak;


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
        final String idIbu = getIntent().getStringExtra("id");
        tgl_bersalin = (EditText) findViewById(R.id.tgl_persalinan);
        layout_nifas = (LinearLayout) findViewById(R.id.layout_nifas);
        lay_tempat = (LinearLayout) findViewById(R.id.tempat_layout);
        lay_ibu = (LinearLayout) findViewById(R.id.ibu_layout);
        lay_anak = (LinearLayout) findViewById(R.id.anak_layout);
      //  htps = (EditText) findViewById(R.id.htp);
        jumlahBayi = (EditText) findViewById(R.id.jumlah);
        jenisKelamin = (EditText) findViewById(R.id.jenis_kel);


        dbManager = new DbManager(this);
        dbManager.open();
        Cursor c = dbManager.fetchuniqueId(idIbu);
        c.moveToFirst();
        uniqueId = c.getString(c.getColumnIndexOrThrow(DbHelper.UNIQUEID));
        dbManager.close();
        Log.e("UNIQUE======",uniqueId);



        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();

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
                jumlahBayi = (EditText) findViewById(R.id.jumlah);
                String jumlahBayis = jumlahBayi.getText().toString();
                String jenisKelamins = jenisKelamin.getText().toString();
                String tgl_persalinn = tgl_bersalin.getText().toString();
                String radioStatus2 = getStatuss2();
                String ibubersalin = getStatusibu();
                String kondisi_ibu = getKondisiibu();
                String kondisi_anak = getKondisianak();
                String komplikasiIbus = getKomplikasiIbu();
                String komplikasiAnak = getKomplikasiAnak();
                String tempat = getTempatBersalin();

                    dbManager.open();
                    dbManager.insertStatusPersalinan(uniqueId,tgl_persalinn,ibubersalin,kondisi_ibu,kondisi_anak,jumlahBayis,jenisKelamins, komplikasiIbus, komplikasiAnak,tempat);
                    dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormStatusPersalinanActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);




            }
        });
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


                //set tempat
            case R.id.rs:
                if (checked)
                    setTempatBersalin("rumahsakit");
                break;
            case R.id.polindes:
                if (checked)
                    setTempatBersalin("polindes");
                break;
            case R.id.puskesmas:
                if (checked)
                    setTempatBersalin("puskesmas");
                break;
            case R.id.rumah:
                if (checked)
                    setTempatBersalin("rumah");
                break;
            case R.id.tempat_lainnya:
                if (checked)
                    setTempatBersalin("lainnya");
                    lay_tempat.setVisibility(VISIBLE);
                break;

                //set komlikasi
            case R.id.perdarahan:
                if (checked)
                    setKomplikasiIbu("perdarahan berat");
                break;
            case R.id.peb:
                if (checked)
                    setKomplikasiIbu("peb");
                break;
            case R.id.eklamsi:
                if (checked)
                    setKomplikasiIbu("eklamsi");
                break;
            case R.id.sepsis:
                if (checked)
                    setKomplikasiIbu("sepsis");
                break;
            case R.id.k_lainnya:
                if (checked) {
                    setKomplikasiIbu("lainnya");
                    lay_ibu.setVisibility(VISIBLE);
                }
                else
                    lay_ibu.setVisibility(VISIBLE);
                break;

                //set komplikasi anak
            case R.id.prem:
                if (checked)
                    setKomplikasiAnak("prematur");
                break;
            case R.id.bblr:
                if (checked)
                    setKomplikasiAnak("bblr");
                break;
            case R.id.asfiksia:
                if (checked)
                    setKomplikasiAnak("asfiksia");
                break;
            case R.id.k_babylainnya:
                if (checked)
                    setKomplikasiAnak("lainnya");
                    lay_anak.setVisibility(VISIBLE);
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
