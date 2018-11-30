package tgwofficial.atma.client.activity.nativeform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.BankDarahActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormAddBankDarah extends AppCompatActivity {
    EditText mother_names;
    EditText nama_donors;
    String rhesus;
    EditText gubugs;
    EditText dusun;

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

    private DbManager dbManager;
    Button btnLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_bank_darah);
        dbManager = new DbManager(this);

        nama_donors = (EditText) findViewById(R.id.nama_donor);
        gubugs = (EditText) findViewById(R.id.gubug);
        dusun = (EditText) findViewById(R.id.dusun_s);
        notelpons = (EditText) findViewById(R.id.notelpon);

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

                if( donor.contains("'") ) {
                    Toast.makeText(getApplicationContext(), "Nama tidak Boleh Menggunakan tanda petik!",
                            Toast.LENGTH_LONG).show();
                }
                else if (donor.isEmpty() || notelponss.isEmpty() || radiogolDarah.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Data Harus Diisi Semua!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.open();
                    dbManager.insertbankdarah(donor, text_gubug, text_dusun, notelponss, radioStatus, radiogolDarah);
                    dbManager.close();

                    Intent myIntent = new Intent(FormAddBankDarah.this, BankDarahActivity.class);
                    startActivity(myIntent);
                    finish();
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
            case R.id.positive:
                if (checked)
                    setRhesus("Positif");
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
}
