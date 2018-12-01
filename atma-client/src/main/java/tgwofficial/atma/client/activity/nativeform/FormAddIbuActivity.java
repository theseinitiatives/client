package tgwofficial.atma.client.activity.nativeform;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import tgwofficial.atma.client.R;
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
    EditText kaders;
    EditText notelpons;

    EditText tgl_bersalin;


    private RadioButton a,b,ab,o;
    private RadioButton rhPositive,rhNegative,rhUnknown;
    private RadioButton hamil,nifas,risti;


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
       // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        mother_names = (EditText) findViewById(R.id.mother_name);
        husband_names = (EditText) findViewById(R.id.husband_name);
        dobs = (EditText) findViewById(R.id.dob);
        dusun = (EditText) findViewById(R.id.dusun);
        gubugs = (EditText) findViewById(R.id.gubug);
        hphts = (EditText) findViewById(R.id.hpht);
        tgl_bersalin = (EditText) findViewById(R.id.tgl_persalinan);


      //  htps = (EditText) findViewById(R.id.htp);
//        goldarahs = (EditText) findViewById(R.id.goldarah);
        kaders = (EditText) findViewById(R.id.kader);
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

        hamil = (RadioButton)findViewById(R.id.hamil);
        nifas = (RadioButton)findViewById(R.id.nifas);
        risti = (RadioButton)findViewById(R.id.risti);

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
                String kaderss = kaders.getText().toString();
                String notelponss = notelpons.getText().toString();
                String tgl_persalinn = tgl_bersalin.getText().toString();
                String radioStatus2 = getStatuss2();
                String ibubersalin = getStatusibu();
                String kondisi_ibu = getKondisiibu();
                String kondisi_anak = getKondisianak();
                String nifas_berakhir = getNifasberakhir();

                if(mothername.contains("'") || husbandname.contains("'")) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (mothername.isEmpty() || husbandname.isEmpty() || dobss.isEmpty() || hphtss.isEmpty() || goldarahss.isEmpty() || kaderss.isEmpty() || radioStatus2.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Harus Diisi Semua!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.open();
                    if(valueExist(id))
                        dbManager.updateIbu(id,mothername, husbandname, dobss, gubugss, hphtss, htpss, goldarahss, kaderss, notelponss,  radioStatus2,tgl_persalinn, ibubersalin,kondisi_ibu,kondisi_anak, nifas_berakhir);
                    else
                        dbManager.insertibu(mothername, husbandname, dobss, gubugss, hphtss, htpss, goldarahss, kaderss, notelponss,  radioStatus2,tgl_persalinn, ibubersalin,kondisi_ibu,kondisi_anak, nifas_berakhir);
                    dbManager.close();
                    Intent myIntent = new Intent(FormAddIbuActivity.this, IdentitasIbuActivity.class);
                    startActivity(myIntent);
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

            case R.id.hamil:
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
                break;

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

            // set nifas
            case R.id.bersalin_ya:
                if (checked)
                    setStatusibu("ya");
                break;
            case R.id.bersalin_tidak:
                if (checked)
                    setStatusibu("tidak");
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
            case R.id.nifas_ya:
                if (checked)
                    setNifasberakhir("ya");
                break;
            case R.id.nifas_tidak:
                if (checked)
                    setNifasberakhir("tidak");
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
        setStatusChecked(cursor.getString(cursor.getColumnIndexOrThrow("status")));
        kaders.setText(cursor.getString(cursor.getColumnIndexOrThrow("kader")));
        notelpons.setText(cursor.getString(cursor.getColumnIndexOrThrow("telp")));
        String temp = cursor.getString(cursor.getColumnIndexOrThrow("kondisi_ibu"));
        setKondisiIbuChecked(cursor.getString(cursor.getColumnIndexOrThrow("kondisi_ibu")));
        setKondisiAnakChecked(cursor.getString(cursor.getColumnIndexOrThrow("kondisi_anak")));
        tgl_bersalin.setText(cursor.getString(cursor.getColumnIndexOrThrow("tgl_persalinan")));

    }

    private boolean valueExist(String value){
        if(value!=null)
            return !value.equalsIgnoreCase("");
        return false;
    }

    private void preloadEditVariable(Cursor cursor){
        String[]data = cursor.getString(cursor.getColumnIndexOrThrow("gol_darah")).split(" - ");
        setDarah(data[0]);
        setRhesus(data[1]);
        setStatuss2(cursor.getString(cursor.getColumnIndexOrThrow("status")));
        setStatuss(cursor.getString(cursor.getColumnIndexOrThrow("status")));
        setKondisiibu(cursor.getString(cursor.getColumnIndexOrThrow("kondisi_ibu")));
        setKondisianak(cursor.getString(cursor.getColumnIndexOrThrow("kondisi_anak")));
    }

    private void setDarahRhChecked(String value){
        String[]data = value.contains(" - ") ? value.split(" - ") : new String[]{"",""};
        setDarahChecked(data[0]);
        setRhesusChecked(data[1]);
    }

    private void setDarahChecked(String value){
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

    private void setKondisiIbuChecked(String value){
        switch(value.toLowerCase()){
            case "hidup" : ((RadioButton)findViewById(R.id.ibu_hidup)).setChecked(true); break;
            case "meninggal" : ((RadioButton)findViewById(R.id.ibu_mati)).setChecked(true); break;
            default:
                ((RadioButton)findViewById(R.id.ibu_hidup)).setChecked(false);
                ((RadioButton)findViewById(R.id.ibu_mati)).setChecked(false);
                break;
        }
    }

    private void setKondisiAnakChecked(String value){
        switch(value.toLowerCase()){
            case "hidup" : ((RadioButton)findViewById(R.id.anak_hidup)).setChecked(true); break;
            case "meninggal" : ((RadioButton)findViewById(R.id.anak_mati)).setChecked(true); break;
            default:
                ((RadioButton)findViewById(R.id.anak_hidup)).setChecked(false);
                ((RadioButton)findViewById(R.id.anak_mati)).setChecked(false);
                break;
        }
    }



}
