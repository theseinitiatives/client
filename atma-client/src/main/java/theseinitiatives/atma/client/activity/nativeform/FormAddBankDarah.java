package theseinitiatives.atma.client.activity.nativeform;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.BankDarahActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;
import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class FormAddBankDarah extends AppCompatActivity {
    EditText mother_names;
    EditText nama_donors;
    String rhesus;
    EditText gubugs;
   // EditText dusun;
    String setUniqueId;
    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }
    private RadioGroup rgp;
    String dusun;
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
       // dusun = (EditText) findViewById(R.id.dusun_s);

        //==========================


         rgp = (RadioGroup) findViewById(R.id.dusun_radio);
        for (int i = 0; i < getlocationName().size(); i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            Log.e("Location", getlocationName().get(0));
            rbn.setText(getlocationName().get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            rgp.addView(rbn);
        }


        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                setDusun(radioButton.getText().toString());
                // Toast.makeText(getApplicationContext(),getDusun(),Toast.LENGTH_LONG).show();
            }
        });
        /*
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
        dusun.setAdapter(adapterDusun);*/

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
                String donor = humanizes(nama_donors.getText().toString());
                String notelponss = notelpons.getText().toString();
                String text_gubug = gubugs.getText().toString();
                String text_dusun = getDusun();
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
                        dataArray.put(DbHelper.TIMESTAMP,dateNow());
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
                        dbManager.insertsyncTable("bank_darah_edit",System.currentTimeMillis(),getDusun() ,dataArray.toString(),0,0);
                        Log.e("Data", dataArray.toString());
                        Log.e("Data====", getSetUniqueId());
                    }
                    else {
                        dbManager.insertbankdarah(UUID,donor, text_gubug, text_dusun, notelponss, radioStatus, radiogolDarah, tglmendonor,System.currentTimeMillis());
                        dbManager.insertsyncTable("bank_darah", System.currentTimeMillis(),getDusun(), dataArray.toString(), 0, 0);

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
                    setRhesus("tidak tahu");
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
                setDusun(c.getString(c.getColumnIndexOrThrow("dusun")));
                notelpons.setText(c.getString(c.getColumnIndexOrThrow("telp")));
                setGolonganDarahClicked(c.getString(c.getColumnIndexOrThrow("gol_darah")));
                setSetUniqueId(c.getString(c.getColumnIndexOrThrow("unique_id")));
                checkDusun(c.getString(c.getColumnIndexOrThrow("dusun")));
            }
            dbManager.close();
        }

        private void setGolonganDarahClicked(String value){
            if(value==null)
                return;
            if(!value.contains("-"))
                return;
            String[]values = value.split(" - ");
            setDarah(values[0]);
            switch(values[0].toLowerCase()){
                case "a" : ((RadioButton)findViewById(R.id.gol_a)).setChecked(true); break;
                case "b" : ((RadioButton)findViewById(R.id.gol_b)).setChecked(true); break;
                case "ab" : ((RadioButton)findViewById(R.id.gol_ab)).setChecked(true); break;
                case "o" : ((RadioButton)findViewById(R.id.gol_o)).setChecked(true); break;
            }
            setRhesus(values[1]);
            switch(values[1].toLowerCase()){
                case "positif" :   ((RadioButton)findViewById(R.id.positive)).setChecked(true); break;
                case "negatif" :   ((RadioButton)findViewById(R.id.negative)).setChecked(true); break;
                case "tidak_tahu" :   ((RadioButton)findViewById(R.id.tidak_tahu)).setChecked(true); break;
            }
        }

    private boolean valueExist(String value){
        if(value!=null)
            return !value.equalsIgnoreCase("");
        return false;
    }

    public ArrayList<String> getlocationName() {
        dbManager.open();
        dbManager.setSelection(DbHelper.LOCATION_TAG_ID+"=6");
        Cursor cursor = dbManager.fetchLocationTree();
        ArrayList<String> names = new ArrayList<String>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.LOCATION_NAME)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbManager.close();

        //  String[] languagess = { "Budi","Joni","Bravo" };
        return names;
        // return languagess;
    }

    private void checkDusun(String value){
        ArrayList<String> listDusun = getlocationName();
        for(int i=0;i<listDusun.size();i++){
            RadioButton r = (RadioButton) rgp.getChildAt(i);

            if(listDusun.get(i).equalsIgnoreCase(value)){

                r.setChecked(true);
                setDusun(value);
                // break;
            }
            r.setClickable(false);

        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtodarah();
    }
}
