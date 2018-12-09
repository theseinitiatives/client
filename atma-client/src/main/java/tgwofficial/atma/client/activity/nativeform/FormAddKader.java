package tgwofficial.atma.client.activity.nativeform;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormAddKader extends AppCompatActivity {

    EditText kaders;
    EditText dusuns;
    EditText nohps;



    private DbManager dbManager;


    String Statuss2;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_kader);
        // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");
        kaders = (EditText) findViewById(R.id.kader);
       // dusuns = (EditText) findViewById(R.id.dusun);
        nohps = (EditText) findViewById(R.id.hp);

        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();

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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama_kader = kaders.getText().toString();
                String namaDusun = dusuns.getText().toString();
                String noHp = nohps.getText().toString();

                dbManager.open();
                dbManager.insertKader(nama_kader,namaDusun,noHp);
                dbManager.close();
                finish();
                Intent myIntent = new Intent(FormAddKader.this, IdentitasIbuActivity.class);
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


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

}