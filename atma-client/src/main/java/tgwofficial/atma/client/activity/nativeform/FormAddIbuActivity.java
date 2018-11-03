package tgwofficial.atma.client.activity.nativeform;

import android.content.Context;
import android.content.Intent;
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
    EditText gubugs;
    EditText hphts;
    EditText htps;
    EditText goldarahs;
    EditText kaders;
    EditText notelpons;
    private DbManager dbManager;
    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    String Statuss;

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
        gubugs = (EditText) findViewById(R.id.gubug);
        hphts = (EditText) findViewById(R.id.hpht);
        htps = (EditText) findViewById(R.id.htp);
        goldarahs = (EditText) findViewById(R.id.goldarah);
        kaders = (EditText) findViewById(R.id.kader);
        notelpons = (EditText) findViewById(R.id.notelpon);

        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mothername = mother_names.getText().toString();
                String husbandname = husband_names.getText().toString();
                String dobss = dobs.getText().toString();
                String gubugss = gubugs.getText().toString();
                String hphtss = hphts.getText().toString();
                String htpss = htps.getText().toString();
                String goldarahss = goldarahs.getText().toString();
                String kaderss = kaders.getText().toString();
                String notelponss = notelpons.getText().toString();
                String radioStatus = getStatuss();
                String radioStatus2 = getStatuss2();

                if(mothername.contains("'") || husbandname.contains("'")) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (mothername.isEmpty() || husbandname.isEmpty() || dobss.isEmpty() || htpss.isEmpty() || hphtss.isEmpty() || goldarahss.isEmpty() || kaderss.isEmpty() || radioStatus.isEmpty() || radioStatus2.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Harus Diisi Semua!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.open();
                    dbManager.insertibu(mothername, husbandname, dobss, gubugss, hphtss, htpss, goldarahss, kaderss, notelponss, radioStatus, radioStatus2);
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

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.hidup:
                if (checked)
                    setStatuss("Hidup");
                    break;
            case R.id.meinggal:
                if (checked)
                    setStatuss("Meninggal");
                    break;
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
        }
    }

}
