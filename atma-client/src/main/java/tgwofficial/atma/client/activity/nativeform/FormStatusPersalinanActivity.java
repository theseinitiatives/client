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
import android.widget.RadioButton;
import android.widget.Toast;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormStatusPersalinanActivity extends AppCompatActivity {

    private RadioButton hamil,nifas,risti;
    EditText tgl_bersalin;
    String statusibu;
    String kondisiibu;
    String kondisianak;
    String nifasberakhir;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_status_persalinan);
       // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");
        tgl_bersalin = (EditText) findViewById(R.id.tgl_persalinan);

      //  htps = (EditText) findViewById(R.id.htp);
//        goldarahs = (EditText) findViewById(R.id.goldarah);
       // kaders = (EditText) findViewById(R.id.kader);

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

                String tgl_persalinn = tgl_bersalin.getText().toString();
                String radioStatus2 = getStatuss2();
                String ibubersalin = getStatusibu();
                String kondisi_ibu = getKondisiibu();
                String kondisi_anak = getKondisianak();
                String locId = "Dusun_test";
                String userId = "user_test";

                    dbManager.open();
                    dbManager.insertStatusPersalinan(idIbu,tgl_persalinn,ibubersalin,kondisi_ibu,kondisi_anak);
                                dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormStatusPersalinanActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);



                //validate form
              //  if(validateinput(mothername,husbandname,dobss,gubugss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2)){
                  //  dbManager.open();
                  //  dbManager.insertibu(mothername,husbandname,dobss,gubugss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2);
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


            // set nifas
            case R.id.hamil:
                if (checked)
                    setStatusibu("hamil");

                break;
            case R.id.nifas:
                if (checked)
                    setStatusibu("nifas");
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
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

}
