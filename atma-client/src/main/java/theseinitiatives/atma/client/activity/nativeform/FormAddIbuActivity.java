package theseinitiatives.atma.client.activity.nativeform;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

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
    String faktorResiko;
    LinearLayout lay_lainnya;
    String setUniqueId;

    public String getSetUniqueId() {
        return setUniqueId;
    }

    public void setSetUniqueId(String setUniqueId) {
        this.setUniqueId = setUniqueId;
    }

    CheckBox kek, anemia, terlaluMuda, terlaluTua, gravidaBanyak, lainnya;
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

    String htpss = "";

    public String getFaktorResiko() {
        String riskFactor = "" +
                (kek.isChecked() ? getString(R.string.kek)+"," : "")+
                (anemia.isChecked() ? getString(R.string.anemia)+"," : "")+
                (terlaluMuda.isChecked() ? getString(R.string.terlalu_muda)+"," : "")+
                (terlaluTua.isChecked() ? getString(R.string.terlalu_tua)+"," : "")+
                (gravidaBanyak.isChecked() ? getString(R.string.gravida_banyak)+"," : "")+
                (lainnya.isChecked() ? getString(R.string.lainnya)+"," : "")
                ;
        if(riskFactor.length()>2)
            riskFactor = riskFactor.substring(0,riskFactor.length()-1);
        return riskFactor;
    }

    public void setFaktorResiko(String faktorResiko) {
        this.faktorResiko = faktorResiko;
    }

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
        lay_lainnya = (LinearLayout) findViewById(R.id.lainnya_layout) ;
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

        kek = (CheckBox) findViewById(R.id.checkbox_kek);
        anemia = (CheckBox) findViewById(R.id.checkbox_anemia);
        terlaluMuda = (CheckBox) findViewById(R.id.checkbox_muda);
        terlaluTua = (CheckBox) findViewById(R.id.checskbox_tua);
        gravidaBanyak = (CheckBox) findViewById(R.id.checskbox_gravida);
        lainnya = (CheckBox) findViewById(R.id.checskbox_lainnya);

        //==========================
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
        final AutoCompleteTextView dusun = (AutoCompleteTextView) findViewById(R.id.dusun);
        //Set the number of characters the user must type before the drop down list is shown
        dusun.setThreshold(1);
        //Set the adapter
        dusun.setAdapter(adapterDusun);

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
                String dobss = AllConstants.convertToYYYYMMDD(dobs.getText().toString());
                String dusunss = dusun.getText().toString();
                String hphtss = AllConstants.convertToYYYYMMDD(hphts.getText().toString());

                String goldarahss = getDarah() + " - "+getRhesus();
                String notelponss = notelpons.getText().toString();
                String radioStatus2 = getStatuss2();
                String fResiko = getFaktorResiko();
                String gubug = gubugs.getText().toString();
                String UUID = java.util.UUID.randomUUID().toString();




                //hphtss = "2008-01-01";  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(AllConstants.convertToYYYYMMDD(hphtss)));
                    c.add(Calendar.MONTH, 9);
                    c.add(Calendar.DATE,7);// number of days to add
                    htpss = sdf.format(c.getTime());  // dt is now the new date
                    Log.e("HTP==========",htpss);
                } catch( Exception e )
                {
                    Log.d("TAG_NAME", e.getMessage()  );
                }

             //   Date currentTime = Calendar.getInstance().getTime();
                //add into sync tables
                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.NAME, mothername);
                    dataArray.put(DbHelper.SPOUSENAME,husbandname);
                    dataArray.put(DbHelper.TGL_LAHIR,dobss);
                    dataArray.put(DbHelper.DUSUN,dusunss);
                    dataArray.put(DbHelper.HPHT,hphtss);
                    dataArray.put(DbHelper.HTP,htpss);
                    dataArray.put(DbHelper.GOL_DARAH,goldarahss);
                    dataArray.put(DbHelper.TELP,notelponss);
                    dataArray.put(DbHelper.RESIKO,fResiko!=null?fResiko:"");
                    dataArray.put(DbHelper.GUBUG,gubug);
                    dataArray.put(DbHelper.NIFAS_SELESAI,"");
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());
                    if(valueExist(id))
                        dataArray.put(DbHelper.UNIQUEID,getSetUniqueId());
                    else
                        dataArray.put(DbHelper.UNIQUEID,UUID);
                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }

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
                    if(valueExist(id)) {
                        //update ibu main tables
                        dbManager.updateIbu(id, mothername, husbandname, dobss, dusunss, hphtss, htpss, goldarahss, "", notelponss, radioStatus2, fResiko, gubug, "", System.currentTimeMillis());
                        //add into sync tables

                        dbManager.insertsyncTable("identitas_ibu_edit",System.currentTimeMillis(),dataArray.toString(),0,0);

                    }
                    else {
                        //insert new data
                        dbManager.insertibu(UUID, mothername, husbandname, dobss, dusunss, hphtss, htpss, goldarahss, "", notelponss, radioStatus2, fResiko, gubug, "",System.currentTimeMillis());
                        dbManager.insertsyncTable("identitas_ibu", System.currentTimeMillis(), dataArray.toString(), 0, 0);
                    }
                    dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormAddIbuActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);

                }

                //validate form
              //  if(validateinput(mothername,husbandname,dobss,dusunss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2)){
                  //  dbManager.open();
                  //  dbManager.insertibu(mothername,husbandname,dobss,dusunss,hphtss,htpss,goldarahss,kaderss,notelponss,radioStatus,radioStatus2);
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
            case R.id.tidaktahu:
                if (checked)
                    setDarah("tidaktahu");
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


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_kek:
//                if (checked)
//                    setFaktorResiko("kek");
//                else
//                    //
//                    break;
//            case R.id.checkbox_anemia:
//                if (checked)
//                    setFaktorResiko("anemia");
//                else
//                    break;
//            case R.id.checkbox_muda:
//                if (checked)
//                    setFaktorResiko("usia muda");
//                else
//                    break;
//            case R.id.checskbox_tua:
//                if (checked)
//                    setFaktorResiko("usi tua");
//                else
//                    break;
//            case R.id.checskbox_gravida:
//                if (checked)
//                    setFaktorResiko("gravida banyak");
//                else
//                    break;
//            case R.id.checskbox_lainnya:
//                if (checked) {
//                    setFaktorResiko("Lainya");
//                    lay_lainnya.setVisibility(VISIBLE);
//                }
//                else if(!checked) {
//                    lay_lainnya.setVisibility(View.GONE);
//                    }
//                else
//                    break;
//                // TODO: Veggie sandwich
//        }
    }

    private void fillField(String id){
        if(dbManager == null) {
            dbManager = new DbManager(getApplicationContext());
        }
        dbManager.open();
        Cursor cursor = dbManager.fetchdetaildata(id);

        preloadEditVariable(cursor);
        mother_names.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        husband_names.setText(cursor.getString(cursor.getColumnIndexOrThrow("spousename")));
        dobs.setText(AllConstants.convertToDDMMYYYY(cursor.getString(cursor.getColumnIndexOrThrow("tgl_lahir"))));
        dusun.setText(cursor.getString(cursor.getColumnIndexOrThrow("dusun")));
        gubugs.setText(cursor.getString(cursor.getColumnIndexOrThrow("gubug")));
        hphts.setText(AllConstants.convertToDDMMYYYY(cursor.getString(cursor.getColumnIndexOrThrow("hpht"))));
//        htps.setText(cursor.getString(cursor.getColumnIndexOrThrow("htp")));
        setDarahRhChecked(cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")));
//        setStatusChecked(cursor.getString(cursor.getColumnIndexOrThrow("status")));
      //  kaders.setText(cursor.getString(cursor.getColumnIndexOrThrow("kader")));
        notelpons.setText(cursor.getString(cursor.getColumnIndexOrThrow("telp")));
       // faktorResiko.setText(cursor.getString(cursor.getColumnIndexOrThrow("resiko")));
        setSetUniqueId(cursor.getString(cursor.getColumnIndexOrThrow("unique_id")));
        dbManager.close();
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
        setCheckBoxSelected(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.RESIKO)));
//        setStatuss2(cursor.getString(cursor.getColumnIndexOrThrow("status")));
 //       setStatuss(cursor.getString(cursor.getColumnIndexOrThrow("status")));

    }

    private void setCheckBoxSelected(String value){
        if(value==null)
            return;
        kek.setChecked(value.contains(getString(R.string.kek)));
        anemia.setChecked(value.contains(getString(R.string.anemia)));
        terlaluMuda.setChecked(value.contains(getString(R.string.terlalu_muda)));
        terlaluTua.setChecked(value.contains(getString(R.string.terlalu_tua)));
        gravidaBanyak.setChecked(value.contains(getString(R.string.gravida_banyak)));
        lainnya.setChecked(value.contains(getString(R.string.lainnya)));
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
