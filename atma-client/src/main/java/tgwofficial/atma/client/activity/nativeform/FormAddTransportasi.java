package tgwofficial.atma.client.activity.nativeform;

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
import android.widget.Toast;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.BankDarahActivity;
import tgwofficial.atma.client.activity.TransportasiActivity;
import tgwofficial.atma.client.db.DbHelper;
import tgwofficial.atma.client.db.DbManager;

public class FormAddTransportasi extends AppCompatActivity {
    EditText nama_pemiliks;
    String jenis;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    EditText kapasitass;
    EditText no_hp;
    EditText gubugs;
    EditText dusuns;
    EditText profesis;
    EditText kets;
    private DbManager dbManager;
    Button btnLogin;

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
     //   dusuns = (EditText) findViewById(R.id.dusun);
        profesis = (EditText) findViewById(R.id.profesi);
        kets = (EditText) findViewById(R.id.ket);
      //  LinearLayout kapasitas_layoutss=(LinearLayout)this.findViewById(R.id.kapasitas_layout);
        kapasitass.setVisibility(View.INVISIBLE);


        //==========================
        String[] dusunsList = {
                "Menges	"	,
                "Penandak	"	,
                "Menyiuh	"	,
                "Selebung Lauk	"	,
                "Selebung Daye	"	,
                "Melar	"	,
                "Jali	"	,
                "Nyangget Lauk	"	,
                "Nyangget Daye	"	,
                "Pucung	"	,
                "Selebung Tengak	"	,
                "Mekar Sari	"
        };
        // Search Nama Donor
        final ArrayAdapter<String> adapterDusun = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, dusunsList);
        //Find TextView control
        final AutoCompleteTextView dusuns = (AutoCompleteTextView) findViewById(R.id.dusun);
        //Set the number of characters the user must type before the drop down list is shown
        dusuns.setThreshold(1);
        //Set the adapter
        dusuns.setAdapter(adapterDusun);


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
                String text_pemiliks  = nama_pemiliks.getText().toString();
                String text_gubug = gubugs.getText().toString();
                String text_kapasitass = kapasitass.getText().toString();
                String text_dusuns = dusuns.getText().toString();
                String text_profesis = profesis.getText().toString();
                String text_kets = kets.getText().toString();
                String text_nohp = no_hp.getText().toString();
                String jeniss = getJenis();
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
                    if(id!=null)
                        dbManager.updatebanktransportasi(id,text_pemiliks, jeniss,text_nohp, text_gubug, text_kapasitass, text_dusuns, text_profesis, text_kets);
                    else
                        dbManager.insertbanktransportasi(text_pemiliks, jeniss,text_nohp, text_gubug, text_kapasitass, text_dusuns, text_profesis, text_kets);
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
                break;
            case R.id.motor:
                if (checked)
                    setJenis("motor");
                break;
            case R.id.cidomo:
                if (checked)
                    setJenis("cidomo");
                break;
            case R.id.pickup:
                if (checked)
                    setJenis("mobil_pickup");
                break;
            case R.id.id_lainnya:
                if (checked)
                    setJenis("id_lainnya");
                break;

        }
        }

    private void preloadForm(Cursor c){
        nama_pemiliks.setText(c.getString(c.getColumnIndexOrThrow("name")));
        gubugs.setText(c.getString(c.getColumnIndexOrThrow("gubug")));
        no_hp.setText(c.getString(c.getColumnIndexOrThrow("telp")));
        dusuns.setText(c.getString(c.getColumnIndexOrThrow("dusun")));
        setJenisKendaraanChecked(c.getString(c.getColumnIndexOrThrow("jenis_kendaraan")));
        if(c.getString(c.getColumnIndexOrThrow("jenis_kendaraan")).equalsIgnoreCase("mobil"))
            kapasitass.setVisibility(View.VISIBLE);
        kapasitass.setText(c.getString(c.getColumnIndexOrThrow("kapasitas_kendaraan")));
        kets.setText(c.getString(c.getColumnIndexOrThrow("keterangan")));
        profesis.setText(c.getString(c.getColumnIndexOrThrow("profesi")));
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
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoTrans();

    }
}
