package theseinitiatives.atma.client.activity.nativeform;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.KaderActivity;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;
import static theseinitiatives.atma.client.Utils.StringUtil.humanizes;

public class FormAddKader extends AppCompatActivity {
    private String TAG = FormAddKader.class.getSimpleName();

    final Activity activity = this;
    EditText kaders;
  //  EditText dusuns;
    EditText nohps;

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    String dusun;
    String posyandu;
    public String getPosyandu() {
        return posyandu;
    }

    public void setPosyandu(String posyandu) {
        this.posyandu = posyandu;
    }

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
        RadioGroup rgp = (RadioGroup) findViewById(R.id.dusun_radio);
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


        RadioGroup posrgp = (RadioGroup) findViewById(R.id.posyandu_radio);
        for (int i = 0; i < getlocationName("posyandu").size(); i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            Log.e("Location", getlocationName("posyandu").get(0));
            rbn.setText(getlocationName("posyandu").get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            posrgp.addView(rbn);
        }
        posrgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                setPosyandu(radioButton.getText().toString());
                // Toast.makeText(getApplicationContext(),getDusun(),Toast.LENGTH_LONG).show();
            }
        });
        


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama_kader = humanizes(kaders.getText().toString());
                String posyandu = getPosyandu();
                String namaDusun = getDusun();
                String noHp = nohps.getText().toString();
                String username = "kader"+namaDusun.replace(" ","").toLowerCase();
                String password = "kaders"+""+randomNum();
                String UUID = java.util.UUID.randomUUID().toString();
                JSONObject dataArray = new JSONObject();
                try {
                    dataArray.put(DbHelper.NAME, nama_kader);
                    dataArray.put(DbHelper.DUSUN,namaDusun);
                    dataArray.put(DbHelper.TELP,noHp);
                    dataArray.put(DbHelper.USERNAME,username);
                    dataArray.put(DbHelper.PASSWORD,password);
                    dataArray.put(DbHelper.UNIQUEID,UUID);
                    dataArray.put(DbHelper.TIMESTAMP,dateNow());

                }catch (Exception e) {
                    Log.d("Data array", e.getMessage());
                }
                dbManager.open();
                dbManager.insertKader(UUID,nama_kader,posyandu,namaDusun,noHp,username, password);
                dbManager.insertsyncTable("kader", System.currentTimeMillis(),getPosyandu(),getDusun(), dataArray.toString(), 0, 0);

                dbManager.close();
                Map<String, String> Params = new HashMap<>();
                Params.put("Save",dateNow().toString());
                FlurryHelper.logOneTimeEvent(activity.getClass().getSimpleName(), Params);
                FlurryHelper.endFlurryLog(activity);
                finish();
                Intent myIntent = new Intent(FormAddKader.this, KaderActivity.class);
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

    public int randomNum(){
        final int random = new Random().nextInt(100) + 800;

        return random;
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
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FlurryHelper.startFlurryLog(this);
    }
}