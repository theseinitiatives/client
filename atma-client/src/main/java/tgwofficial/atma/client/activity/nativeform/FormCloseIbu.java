package tgwofficial.atma.client.activity.nativeform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import tgwofficial.atma.client.NavigationmenuController;
import tgwofficial.atma.client.R;
import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.db.DbManager;

public class FormCloseIbu extends AppCompatActivity {

    EditText alasan;



    private DbManager dbManager;


    public String getStatuss() {
        return Statuss;
    }

    public void setStatuss(String statuss) {
        Statuss = statuss;
    }

    String Statuss;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_tutup_ibu);
        // dbHelper = new DbHelper(this);
        dbManager = new DbManager(this);
        final String idIbu = getIntent().getStringExtra("id");

        alasan = (EditText) findViewById(R.id.alasans);

        btnLogin = (Button) findViewById(R.id.saved);
        //  userService = ApiUtils.getUserService();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alasans = alasan.getText().toString();
                String status_ = getStatuss();

                dbManager.open();
                dbManager.closeIbu(idIbu,status_,alasans);
                dbManager.close();
                finish();
                Intent myIntent = new Intent(FormCloseIbu.this, IdentitasIbuActivity.class);
                startActivity(myIntent);

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.tutp_tidak:
                if (checked)
                    setStatuss("tidak");
                break;
            case R.id.tutup_ya:
                if (checked)
                    setStatuss("ya");
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