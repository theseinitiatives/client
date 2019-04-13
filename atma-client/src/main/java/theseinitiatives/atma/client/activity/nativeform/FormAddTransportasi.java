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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;
import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class FormAddTransportasi extends AppCompatActivity {
    EditText nama_pemiliks;
    String jenis;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    private RadioGroup rgp;
    String dusun;
    EditText kapasitass;
    EditText no_hp;
    EditText gubugs;
    EditText kend_lainnyas;
    EditText profesis;
    EditText kets;
    private DbManager dbManager;
    Button btnLogin;
    String setUniqueId;
    LinearLayout lay_lainnya;

    public String getSetUniqueId() {
        return setUniqueId;
    }

    public void setSetUniqueId(String setUniqueId) {
        this.setUniqueId = setUniqueId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_transportasi);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        dbManager = new DbManager(this);
        nama_pemiliks = (EditText) findViewById(R.id.nama_pemilik);
        no_hp = (EditText) findViewById(R.id.nohp);
        gubugs = (EditText) findViewById(R.id.gubug);
        kapasitass = (EditText) findViewById(R.id.kapasitas);
      //  dusuns = (EditText) findViewById(R.id.dusun);
        profesis = (EditText) findViewById(R.id.profesi);
        kets = (EditText) findViewById(R.id.ket);
        lay_lainnya = (LinearLayout)this.findViewById(R.id.lainnya_layout);
        kapasitass.setVisibility(GONE);
        kend_lainnyas = (EditText) findViewById(R.id.kend_lainnya) ;

        //==========================
        rgp = (RadioGroup) findViewById(R.id.dusun_radio);
        for (int i = 0; i < getlocationName("dusun").size(); i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            Log.e("Location", getlocationName("dusun").get(0));
            rbn.setText(getlocationName("dusun").get(i));
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


        if(id!=null){
            dbManager.open();
            dbManager.setSelection(DbHelper._ID+" = '"+id+"'");
            Cursor c = dbManager.fetchTrans("","");

            if(c!=null)
                c.moveToFirst();
            preloadForm(c);
            dbManager.close();
        }
        btnLogin = (Button) findViewById(R.id.saved);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_pemiliks  = humanizes(nama_pemiliks.getText().toString());
                String text_gubug = gubugs.getText().toString();
                String text_kapasitass = kapasitass.getText().toString();
                String text_dusuns = getDusun();
                String text_profesis = profesis.getText().toString();
                String text_kets = kets.getText().toString();
                String text_nohp = no_hp.getText().toString();
                String jeniss = getJenis();
                String UUID = java.util.UUID.randomUUID().toString();
                String kend_lainnya = kend_lainnyas.getText().toString();

                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.NAME, text_pemiliks);
                    dataArray.put(DbHelper.TELP,text_nohp);
                    dataArray.put(DbHelper.Jenis,jeniss);
                    dataArray.put(DbHelper.Jenis_LAIN,kend_lainnya);
                    dataArray.put(DbHelper.GUBUG,text_gubug);
                    dataArray.put(DbHelper.Kapasitas,text_kapasitass);
                    dataArray.put(DbHelper.DUSUN,text_dusuns);
                    dataArray.put(DbHelper.PROFESI,text_profesis);
                    dataArray.put(DbHelper.KET,text_kets);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());
                    if(id!=null)
                        dataArray.put(DbHelper.UNIQUEID,getSetUniqueId());
                    else
                        dataArray.put(DbHelper.UNIQUEID,UUID);
                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }


                if(text_pemiliks.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (text_pemiliks.isEmpty() || jeniss.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data nama dan Jenis Kendarran Harus Diisi!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.open();
                    if(id!=null) {
                        dbManager.updatebanktransportasi(id, text_pemiliks, jeniss, kend_lainnya, text_nohp, text_gubug, text_kapasitass, text_dusuns, text_profesis, text_kets,System.currentTimeMillis());
                        dbManager.insertsyncTable("transportasi_edit", System.currentTimeMillis(),getDusun(), dataArray.toString(), 0, 0);

                    } else {
                        dbManager.insertbanktransportasi(UUID, text_pemiliks, jeniss, kend_lainnya,text_nohp, text_gubug, text_kapasitass, text_dusuns, text_profesis, text_kets,System.currentTimeMillis());
                        dbManager.insertsyncTable("transportasi", System.currentTimeMillis(),getDusun(), dataArray.toString(), 0, 0);

                    }
                    dbManager.close();
                    finish();
                    Intent myIntent = new Intent(FormAddTransportasi.this, TransportasiActivity.class);
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
            case R.id.mobil:
                if (checked)
                    setJenis("mobil");
                    kapasitass.setVisibility(View.VISIBLE);
                    lay_lainnya.setVisibility(GONE);
                break;
            case R.id.motor:
                if (checked)
                    setJenis("motor");
                    kapasitass.setVisibility(GONE);
                    lay_lainnya.setVisibility(GONE);
                break;
            case R.id.cidomo:
                if (checked)
                    setJenis("cidomo");
                    kapasitass.setVisibility(View.VISIBLE);
                    lay_lainnya.setVisibility(GONE);
                break;
            case R.id.pickup:
                if (checked)
                    setJenis("mobil_pickup");
                    kapasitass.setVisibility(View.VISIBLE);
                    lay_lainnya.setVisibility(GONE);
                break;
            case R.id.id_lainnya:
                if (checked)
                    setJenis("id_lainnya");
                    kapasitass.setVisibility(VISIBLE);
                    lay_lainnya.setVisibility(VISIBLE);
                break;

        }
        }

    private void preloadForm(Cursor c){
        nama_pemiliks.setText(c.getString(c.getColumnIndexOrThrow("name")));
        gubugs.setText(c.getString(c.getColumnIndexOrThrow("gubug")));
        setDusun(c.getString(c.getColumnIndexOrThrow("dusun")));
        no_hp.setText(c.getString(c.getColumnIndexOrThrow("telp")));
//        dusuns.setText(c.getString(c.getColumnIndexOrThrow("dusun")));
        setJenisKendaraanChecked(c.getString(c.getColumnIndexOrThrow("jenis_kendaraan")));
        if(!c.getString(c.getColumnIndexOrThrow("jenis_kendaraan")).equalsIgnoreCase("motor"))
            kapasitass.setVisibility(View.VISIBLE);
        kapasitass.setText(c.getString(c.getColumnIndexOrThrow("kapasitas_kendaraan")));
        kets.setText(c.getString(c.getColumnIndexOrThrow("keterangan")));
        profesis.setText(c.getString(c.getColumnIndexOrThrow("profesi")));
        setSetUniqueId(c.getString(c.getColumnIndexOrThrow("unique_id")));
        checkDusun(c.getString(c.getColumnIndexOrThrow("dusun")));

    }

    private void setJenisKendaraanChecked(String value){
        if(value==null)
            return;
        setJenis(value);
        switch(value.toLowerCase()){
            case "mobil" : ((RadioButton)findViewById(R.id.mobil)).setChecked(true);break;
            case "motor" : ((RadioButton)findViewById(R.id.motor)).setChecked(true);break;
            case "cidomo": ((RadioButton)findViewById(R.id.cidomo)).setChecked(true);break;
            case "pickup": ((RadioButton)findViewById(R.id.pickup)).setChecked(true);break;
            case "mobil_pickup": ((RadioButton)findViewById(R.id.pickup)).setChecked(true);break;
            case "id_lainnya" : ((RadioButton)findViewById(R.id.id_lainnya)).setChecked(true);break;
            default:
                ((RadioButton)findViewById(R.id.mobil)).setChecked(true);
                ((RadioButton)findViewById(R.id.motor)).setChecked(true);
                ((RadioButton)findViewById(R.id.cidomo)).setChecked(true);
                ((RadioButton)findViewById(R.id.pickup)).setChecked(true);
                ((RadioButton)findViewById(R.id.id_lainnya)).setChecked(true);
                break;
        }
    }

    public ArrayList<String> getlocationName(String tag) {
        dbManager.open();
        dbManager.setSelection(DbHelper.LOCATION_TAG+"='"+tag+"'");
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
        ArrayList<String> listDusun = getlocationName("dusun");
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
        navi.backtoTrans();

    }
}
