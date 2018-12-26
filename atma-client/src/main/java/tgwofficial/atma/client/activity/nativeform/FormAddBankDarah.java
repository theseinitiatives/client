package tgwofficial.atma.client.activity.nativeform;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.BankDarahActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class FormAddBankDarah extends AppCompatActivity {
    EditText mother_names;
    EditText nama_donors;
    String rhesus;
    EditText gubugs;
    EditText dusun;
    String setUniqueId;

    public String getSetUniqueId() {
        return setUniqueId;
    }

    public void setSetUniqueId(String setUniqueId) {
        this.setUniqueId = setUniqueId;
    }
    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }
    public String getDarah() {
        return darah;
    }

    public void setDarah(String darah) {
        this.darah = darah;
    }

    String darah;
    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    String statuss;

    public String getStatuss2() {
        return statuss2;
    }

    public void setStatuss2(String statuss2) {
        this.statuss2 = statuss2;
    }

    String statuss2;
    String goldarahs;
    EditText notelpons;
    EditText tgl_donor;

    private DbManager dbManager;
    Button btnLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_bank_darah);
        dbManager = new DbManager(this);

        nama_donors = (EditText) findViewById(R.id.nama_donor);
        gubugs = (EditText) findViewById(R.id.gubug);
        tgl_donor = (EditText) findViewById(R.id.terakhirdonor);
        notelpons = (EditText) findViewById(R.id.notelpon);
        dusun = (EditText) findViewById(R.id.dusun_s);

        //==========================
        String[] dusunsList = {
                "Menges"	,
                "Penandak"	,
                "Menyiuh"	,
                "Selebung Lauk"	,
                "Selebung Daye"	,
                "Melar"	,
                "Jali"	,
                "Nyangget Lauk"	,
                "Nyangget Daye"	,
                "Pucung"	,
                "Selebung Tengak"	,
                "Mekar Sari"
        };
        // Search Nama Donor
        final ArrayAdapter<String> adapterDusun = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, dusunsList);
        //Find TextView control
        final AutoCompleteTextView dusun = (AutoCompleteTextView) findViewById(R.id.dusun_s);
        //Set the number of characters the user must type before the drop down list is shown
        dusun.setThreshold(1);
        //Set the adapter
        dusun.setAdapter(adapterDusun);

        tgl_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s=monthOfYear+1;
                        String a = dayOfMonth+"-"+s+"-"+year;
                        tgl_donor.setText(""+a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(FormAddBankDarah.this, dpd, date.year ,date.month, date.monthDay);
                d.show();

            }
        });

        final String id = getIntent().getStringExtra("id");
        Intent intent = getIntent();
       // id = intent.getStringExtra("id");
        if(id != null)
            if (!id.equalsIgnoreCase(""))
                preloadFromData(id);
        btnLogin = (Button) findViewById(R.id.saved);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String mothername = mother_names.getText().toString();
                String donor = nama_donors.getText().toString();
                String notelponss = notelpons.getText().toString();
                String text_gubug = gubugs.getText().toString();
                String text_dusun = dusun.getText().toString();
                String radioStatus = getStatuss();
                String radiogolDarah = getDarah() +" - "+ getRhesus();
                String tglmendonor = tgl_donor.getText().toString();
                String UUID = java.util.UUID.randomUUID().toString();



                if( donor.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (donor.isEmpty() || notelponss.isEmpty() || radiogolDarah.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Harus Diisi Semua!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    JSONObject dataArray = new JSONObject();
                    try {
                        dataArray.put(DbHelper.NAME_PENDONOR, donor);
                        dataArray.put(DbHelper.TELP,notelponss);
                        dataArray.put(DbHelper.GUBUG,text_gubug);
                        dataArray.put(DbHelper.DUSUN,text_dusun);
                        dataArray.put(DbHelper.GOL_DARAH,radiogolDarah);
                        dataArray.put(DbHelper.TGL_DONOR,tglmendonor);
                        if(valueExist(id)) {
                            dataArray.put(DbHelper.UNIQUEID, getSetUniqueId());
                        }else {
                            dataArray.put(DbHelper.UNIQUEID, UUID);
                        }
                    }catch (Exception e) {
                        Log.d("Data array", e.getMessage());
                    }
                    dbManager.open();
                    if(id!=null){
                        dbManager.updatebankdarah(id, donor, text_gubug, text_dusun, notelponss, radioStatus, radiogolDarah, tglmendonor,System.currentTimeMillis());
                        dbManager.insertsyncTable("bank_darah_edit",System.currentTimeMillis(),dataArray.toString(),0,0);
                        Log.e("Data", dataArray.toString());
                        Log.e("Data====", getSetUniqueId());
                    }
                    else {
                        dbManager.insertbankdarah(UUID,donor, text_gubug, text_dusun, notelponss, radioStatus, radiogolDarah, tglmendonor,System.currentTimeMillis());
                        dbManager.insertsyncTable("bank_darah", System.currentTimeMillis(), dataArray.toString(), 0, 0);

                    }
                        dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormAddBankDarah.this, BankDarahActivity.class);
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
            /*case R.id.suami:
                if (checked)
                    setStatuss("suami");
                break;
            case R.id.nenek:
                if (checked)
                    setStatuss("nenek");
                break;
            case R.id.kakek:
                if (checked)
                    setStatuss("kakek");
                break;
            case R.id.adik:
                if (checked)
                    setStatuss("adik");
                break;
            case R.id.lainnya:
                if (checked)
                    setStatuss("lainnya");
                break;*/
            case R.id.gol_a:
                if (checked)
                    setDarah("a");
                break;
            case R.id.gol_b:
                if (checked)
                    setDarah("b");
                break;
            case R.id.gol_ab:
                if (checked)
                    setDarah("ab");
                break;
            case R.id.gol_o:
                if (checked)
                    setDarah("o");
                break;
            // set rhesus
            case R.id.positive:
                if (checked)
                    setRhesus("positif");
                break;
            case R.id.negative:
                if (checked)
                    setRhesus("negatif");
                break;
            case R.id.tidak_tahu:
                if (checked)
                    setRhesus("tidak_tahu");
                break;
        }
        }

        private void preloadFromData(String id){
            if(dbManager == null) {
                dbManager = new DbManager(getApplicationContext());
            }
            dbManager.open();
            Cursor c = dbManager.fetchBankDarahUpdate(id);
            if ( c.moveToFirst() ) {
                nama_donors.setText(c.getString(c.getColumnIndexOrThrow("name_pendonor")));
                gubugs.setText(c.getString(c.getColumnIndexOrThrow("gubug")));
                dusun.setText(c.getString(c.getColumnIndexOrThrow("dusun")));
                notelpons.setText(c.getString(c.getColumnIndexOrThrow("telp")));
                setGolonganDarahClicked(c.getString(c.getColumnIndexOrThrow("gol_darah")));
                setSetUniqueId(c.getString(c.getColumnIndexOrThrow("unique_id")));
            }
            dbManager.close();
        }

        private void setGolonganDarahClicked(String value){
            String[]values = value.split(" - ");
            switch(values[0].toLowerCase()){
                case "a" : ((RadioButton)findViewById(R.id.gol_a)).setChecked(true); break;
                case "b" : ((RadioButton)findViewById(R.id.gol_b)).setChecked(true); break;
                case "ab" : ((RadioButton)findViewById(R.id.gol_ab)).setChecked(true); break;
                case "o" : ((RadioButton)findViewById(R.id.gol_o)).setChecked(true); break;
                default :
                     ((RadioButton)findViewById(R.id.gol_a)).setChecked(false);
                     ((RadioButton)findViewById(R.id.gol_b)).setChecked(false);
                     ((RadioButton)findViewById(R.id.gol_ab)).setChecked(false);
                     ((RadioButton)findViewById(R.id.gol_o)).setChecked(false);
                     break;
            }
            switch(values[1].toLowerCase()){
                case "positif" :   ((RadioButton)findViewById(R.id.positive)).setChecked(true); break;
                case "negatif" :   ((RadioButton)findViewById(R.id.negative)).setChecked(true); break;
                case "tidak_tahu" :   ((RadioButton)findViewById(R.id.tidak_tahu)).setChecked(true); break;
                default:
                     ((RadioButton)findViewById(R.id.positive)).setChecked(false);
                     ((RadioButton)findViewById(R.id.negative)).setChecked(false);
                     ((RadioButton)findViewById(R.id.tidak_tahu)).setChecked(false);
                     break;
            }
        }

    private boolean valueExist(String value){
        if(value!=null)
            return !value.equalsIgnoreCase("");
        return false;
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtodarah();
    }
}
