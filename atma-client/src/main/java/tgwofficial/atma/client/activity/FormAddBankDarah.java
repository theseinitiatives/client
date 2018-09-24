package tgwofficial.atma.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import tgwofficial.atma.client.R;
import tgwofficial.atma.client.db.DbManager;

public class FormAddBankDarah extends AppCompatActivity {
    EditText mother_names;
    EditText nama_donors;

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
        mother_names = (EditText) findViewById(R.id.mother_name);
        nama_donors = (EditText) findViewById(R.id.nama_donor);

        notelpons = (EditText) findViewById(R.id.notelpon);

        btnLogin = (Button) findViewById(R.id.saved);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mothername = mother_names.getText().toString();
                String donor = nama_donors.getText().toString();
                String notelponss = notelpons.getText().toString();
                String radioStatus = getStatuss();
                String radiogolDarah = getStatuss2();

                dbManager.open();
                dbManager.insertbankdarah(mothername,donor,notelponss,radioStatus,radiogolDarah);
                dbManager.close();

                Intent myIntent = new Intent(FormAddBankDarah.this, BankDarahActivity.class);
                startActivity(myIntent);
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
            case R.id.suami:
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
                break;
            case R.id.a:
                if (checked)
                    setStatuss2("a");
                break;
            case R.id.b:
                if (checked)
                    setStatuss2("b");
                break;
            case R.id.ab:
                if (checked)
                    setStatuss2("ab");
                break;
            case R.id.o:
                if (checked)
                    setStatuss2("o");
                break;
        }
        }
}
