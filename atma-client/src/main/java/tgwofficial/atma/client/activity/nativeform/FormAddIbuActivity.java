package tgwofficial.atma.client.activity.nativeform;

import android.app.DatePickerDialog;
import android.content.Context;
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
import tgwofficial.atma.client.activity.BankDarahActivity;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormAddIbuActivity extends AppCompatActivity {
    EditText mother_names;
    EditText husband_names;
    EditText dobs;
    EditText dusun;
    EditText gubugs;
    EditText hphts;
    EditText htps;
    EditText goldarahs;
    EditText notelpons;
    EditText faktorResiko;



    private RadioButton a,b,ab,o;
    private RadioButton rhPositive,rhNegative,rhUnknown;
    private RadioButton hamil,nifas,risti;

    String rhesus;

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    private DbManager dbManager;
    private String id ="";

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
        setContentView(R.layout.form_add_ibu);
        dbManager = new DbManager(this);
        mother_names = (EditText) findViewById(R.id.mother_name);
        husband_names = (EditText) findViewById(R.id.husband_name);
        dobs = (EditText) findViewById(R.id.dob);
        dusun = (EditText) findViewById(R.id.dusun);
        gubugs = (EditText) findViewById(R.id.gubug);
        hphts = (EditText) findViewById(R.id.hpht);
        faktorResiko = (EditText) findViewById(R.id.resiko) ;
        notelpons = (EditText) findViewById(R.id.notelpon);
        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();

        a = (RadioButton)findViewById(R.id.a);
        b = (RadioButton)findViewById(R.id.b);
        ab = (RadioButton)findViewById(R.id.ab);
        o = (RadioButton)findViewById(R.id.o);

        rhPositive = (RadioButton)findViewById(R.id.rh_positive);
        rhNegative = (RadioButton)findViewById(R.id.rh_negative);
        rhUnknown = (RadioButton)findViewById(R.id.rh_tidak_tahu);

        //hamil = (RadioButton)findViewById(R.id.hamil);
        //nifas = (RadioButton)findViewById(R.id.nifas);
        //risti = (RadioButton)findViewById(R.id.risti);

        dobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s=monthOfYear+1;
                        String a = dayOfMonth+"-"+s+"-"+year;
                        dobs.setText(""+a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(FormAddIbuActivity.this, dpd, date.year ,date.month, date.monthDay);
                d.show();

            }
        });

        hphts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s=monthOfYear+1;
                        String a = dayOfMonth+"-"+s+"-"+year;
                        hphts.setText(""+a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(FormAddIbuActivity.this, dpd, date.year ,date.month, date.monthDay);
                d.show();

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mothername = mother_names.getText().toString();
                String husbandname = husband_names.getText().toString();
                String dobss = dobs.getText().toString();
                String gubugss = dusun.getText().toString();
                String hphtss = hphts.getText().toString();
                String htpss = "";
                String goldarahss = getDarah() + " - "+getRhesus();
                String notelponss = notelpons.getText().toString();
                String radioStatus2 = getStatuss2();
                String fResiko = faktorResiko.getText().toString();
                String gubug = gubugs.getText().toString();
                if(mothername.contains("'") || husbandname.contains("'")) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (mothername.isEmpty() || husbandname.isEmpty() || dobss.isEmpty() || hphtss.isEmpty() || goldarahss.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Data Harus Diisi Semua!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.open();
                    if(valueExist(id))
                        dbManager.updateIbu(id,mothername, husbandname, dobss, gubugss, hphtss, htpss, goldarahss, "", notelponss,  radioStatus2, fResiko,gubug,"");
                    else
                        dbManager.insertibu(mothername, husbandname, dobss, gubugss, hphtss, htpss, goldarahss, "", notelponss,  radioStatus2,fResiko,gubug,"");
                    dbManager.close();
                    Intent myIntent = new Intent(FormAddIbuActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);
                    finish();
                }

                //validate form
              //  if(validateinput(mothername,husbandname,dobss,gubugss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2)){
                  //  dbManager.open();
                  //  dbManager.insertibu(mothername,husbandname,dobss,gubugss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2);
                  //  dbManager.close();
              //  }
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if(id != null)
            if (!id.equalsIgnoreCase(""))
                fillField(id);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            /*case R.id.hamil:
                if (checked)
                    setStatuss2("hamil");
                break;
            case R.id.nifas:
                if (checked)
                    setStatuss2("nifas");
                break;
            case R.id.risti:
                if (checked)
                    setStatuss2("risti");
                break;*/

                //set gol darah
            case R.id.a:
                if (checked)
                    setDarah("a");
                break;
            case R.id.b:
                if (checked)
                    setDarah("b");
                break;
            case R.id.ab:
                if (checked)
                    setDarah("ab");
                break;
            case R.id.o:
                if (checked)
                    setDarah("o");
                break;

                // set rhesus
            case R.id.rh_positive:
                if (checked)
                    setRhesus("positif");
                break;
            case R.id.rh_negative:
                if (checked)
                    setRhesus("negatif");
                break;
            case R.id.rh_tidak_tahu:
                if (checked)
                    setRhesus("tidak_tahu");
                break;


        }
    }

    private void fillField(String id){
        if(dbManager == null) {
            dbManager = new DbManager(getApplicationContext());
        }
        dbManager.open();
        Cursor cursor = dbManager.fetchdetaildata(id);
        dbManager.close();
        preloadEditVariable(cursor);
        mother_names.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        husband_names.setText(cursor.getString(cursor.getColumnIndexOrThrow("spousename")));
        dobs.setText(cursor.getString(cursor.getColumnIndexOrThrow("tgl_lahir")));
        dusun.setText(cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
//        gubugs.setText(cursor.getString(cursor.getColumnIndexOrThrow("gubugss")));
        hphts.setText(cursor.getString(cursor.getColumnIndexOrThrow("hpht")));
//        htps.setText(cursor.getString(cursor.getColumnIndexOrThrow("htp")));
        setDarahRhChecked(cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")));
//        setStatusChecked(cursor.getString(cursor.getColumnIndexOrThrow("status")));
      //  kaders.setText(cursor.getString(cursor.getColumnIndexOrThrow("kader")));
        notelpons.setText(cursor.getString(cursor.getColumnIndexOrThrow("telp")));
        faktorResiko.setText(cursor.getString(cursor.getColumnIndexOrThrow("resiko")));

    }

    private boolean valueExist(String value){
        if(value!=null)
            return !value.equalsIgnoreCase("");
        return false;
    }

    private void preloadEditVariable(Cursor cursor){
        if(cursor==null)
            return;
        String[]data = cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")).split(" - ");
        setDarah(data[0]);
        setRhesus(data[1]);
//        setStatuss2(cursor.getString(cursor.getColumnIndexOrThrow("status")));
 //       setStatuss(cursor.getString(cursor.getColumnIndexOrThrow("status")));

    }

    private void setDarahRhChecked(String value){
        if(value==null)
            return;
        String[]data = value.contains(" - ") ? value.split(" - ") : new String[]{"",""};
        setDarahChecked(data[0]);
        setRhesusChecked(data[1]);
    }

    private void setDarahChecked(String value){
        if(value==null)
            return;
        switch(value.toLowerCase()){
            case "a" : a.setChecked(true); break;
            case "b" : b.setChecked(true); break;
            case "ab" : ab.setChecked(true);break;
            case "o" : o.setChecked(true); break;
            default:
                a.setChecked(false);
                b.setChecked(false);
                ab.setChecked(false);
                o.setChecked(false);
                break;
        }
    }

    private void setRhesusChecked(String value){
        if(value==null)
            return;
        switch(value.toLowerCase()){
            case "positif" : rhPositive.setChecked(true); break;
            case "negatif" : rhNegative.setChecked(true); break;
            case "tidak_tahu" : rhUnknown.setChecked(true); break;
            default:
                rhPositive.setChecked(false);
                rhNegative.setChecked(false);
                rhUnknown.setChecked(false);
                break;
        }
    }

    private void setStatusChecked(String value){
        if(value==null)
            return;
        switch(value.toLowerCase()){
            case "hamil" : hamil.setChecked(true); break;
            case "nifas" : nifas.setChecked(true); break;
            case "risti": risti.setChecked(true); break;
            default:
                hamil.setChecked(false);
                nifas.setChecked(false);
                risti.setChecked(false);
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
