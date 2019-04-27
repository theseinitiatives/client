package theseinitiatives.atma.client.Utils;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;

import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.R;
import theseinitiatives.atma.client.db.DbHelper;
import theseinitiatives.atma.client.db.DbManager;

public class FilterActivity extends Activity {
    private Button button;
    private CheckBox checkbox;
    private Spinner htpSpinner;
    private Spinner posyanduSpinner;
    private Spinner dusunSpinner;
    private View dusunfilter;
    private View posyandufilter;
    private DbManager dbManager;
    private String iddesa;
    private int mode=0;
    private boolean isIbu = false;

    private TextView filterTextView;

    private String filterByPosyandu =  null;
    private String filterByDusun =  null;
    private String filterByHPHT = null;
    private boolean filterByRisti = false;

    private String [][] posyanduString;
    private String [][] dusunString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setTitle("Menu Filter");

        this.dbManager = new DbManager(getApplicationContext());
        this.iddesa = getIntent().getStringExtra("iddesa");
        this.mode = getIntent().getIntExtra("source",0);
        this.isIbu = getIntent().getBooleanExtra("isIbu",false);

        posyanduString = posyanduString();
        initComponenets();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        getWindow().setLayout((int)(width*.8),WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initComponenets(){
        htpSpinner = (Spinner) findViewById(R.id.filter_by_HTP);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(htpSpinner);

            popupWindow.setHeight(400);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        filterTextView = (TextView) findViewById(R.id.filter2textview);
        if(mode<0){
            htpSpinner.setVisibility(View.INVISIBLE);
            filterTextView.setVisibility(View.INVISIBLE);
            mode = 1;
        }
        filterTextView.setText("Tampilkan hanya "+textView()[mode]);
        htpSpinner.setAdapter(spinnerAdapter(stringPool[mode][0]));
        htpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)AllConstants.filters.put(DbHelper.HTP,stringPool[mode][1][i]);
                filterByHPHT = stringPool[mode][1][i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filterByHPHT = null;
            }
        });

        //POSYANDU
        posyandufilter = findViewById(R.id.posyandufilter);
        posyanduSpinner = (Spinner) findViewById(R.id.filter_by_posyandu);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(posyanduSpinner);

            popupWindow.setHeight(400);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        posyanduSpinner.setAdapter(spinnerAdapter(posyanduString[0]));
        posyanduSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)AllConstants.filters.put(DbHelper.POSYANDU,posyanduString[1][i]);
                filterByPosyandu = posyanduString[1][i];

                if (posyanduString[1][i]!="~"){
                    dusunfilter.setVisibility(View.VISIBLE);
                }else{
                    dusunfilter.setVisibility(View.GONE);
                }

                dusunString = dusunString(posyanduString[2][i]);

                dusunSpinner.setAdapter(spinnerAdapter(dusunString[0]));
                dusunSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i!=0)AllConstants.filters.put(DbHelper.DUSUN,dusunString[1][i]);
                        filterByDusun = dusunString[1][i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        AllConstants.filters.remove(DbHelper.DUSUN);
                        filterByDusun = null;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                AllConstants.filters.remove(DbHelper.POSYANDU);
                filterByPosyandu = null;
            }
        });

        //DUSUN
        dusunSpinner = (Spinner) findViewById(R.id.filter_by_dusun);
        dusunfilter = findViewById(R.id.dusunfilter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(dusunSpinner);

            popupWindow.setHeight(400);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }



        if (!isIbu) {
            posyandufilter.setVisibility(View.GONE);
            dusunfilter.setVisibility(View.VISIBLE);
            dusunString = dusunString();

            dusunSpinner.setAdapter(spinnerAdapter(dusunString[0]));
            dusunSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i!=0)AllConstants.filters.put(DbHelper.DUSUN,dusunString[1][i]);
                    filterByDusun = dusunString[1][i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    AllConstants.filters.remove(DbHelper.DUSUN);
                    filterByDusun = null;
                }
            });
        }


        checkbox = (CheckBox) findViewById(R.id.filter_check);
        checkbox.setVisibility(mode==0 ? View.VISIBLE : View.INVISIBLE);
        AllConstants.filters.remove(DbHelper.RESIKO);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filterByRisti = b;
                if (filterByRisti) AllConstants.filters.put(DbHelper.RESIKO,Boolean.toString(b));
                else AllConstants.filters.remove(DbHelper.RESIKO);
            }
        });
        button = (Button) findViewById(R.id.filter_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterByHPHT == null)
                    filterByHPHT = "~";
                if(filterByDusun == null)
                    filterByDusun =  "~";
                AllConstants.params = String.format("%s%s%s%s%s",
                        filterByHPHT,
                        AllConstants.FLAG_SEPARATOR,
                        filterByDusun,
                        AllConstants.FLAG_SEPARATOR,
                        filterByRisti?"yes":"no");
                finish();
            }
        });

        Button clear = (Button) findViewById(R.id.filter_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllConstants.filters.clear();
                AllConstants.params = "####";
                finish();
            }
        });


    }

    private ArrayAdapter<String> spinnerAdapter(String data[]){
        return new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, data);
    }

    private String [][] htpString(){
        return new String[][]{
                {"---", "januari", "februari", "maret",    "april",    "mei",  "juni", "juli", "agustus",  "september",    "oktober",  "november", "desember"},
                {"~", "-01-",      "-02-",     "-03-",     "-04-",     "-05-", "-06-", "-07-", "-08-",     "-09-",         "-10-",     "-11-",     "-12-"}
        };
    }

    private String[][] vehicleString(){
        return new String[][]{
                {"---","Mobil","Motor","Cidomo","Pick Up","Lainnya"},
                {"~","mobil","motor","cidomo","pickup","lainnya"}
        };
    }

    private String [][] bloodString(){
        return new String[][]{
                {"---","A","B","AB","O"},
                {"~","a -","b -%' AND "+DbHelper.GOL_DARAH + " NOT LIKE 'a%","ab -","o -"}
        };
    }

    private String [] textView(){
        return new String[]{"HTP Bulan:","Kendaraan Berjenis:","Darah Bergolongan:"};
    }

    private String [][][] stringPool = {htpString(),vehicleString(),bloodString()};

    private String [][]posyanduString(){
        dbManager.open();
        dbManager.clearClause();
        dbManager.setSelection(DbHelper.PARENT_LOCATION+" = ?");
        dbManager.setSelectionArgs(new String[]{iddesa});
        Cursor c = dbManager.fetchLocationTree();
        dbManager.clearClause();
        c.moveToFirst();
        String temp[][] = new String[3][c.getCount()+1];
        temp[0][0] = "---";temp[1][0]="~";temp[2][0]="0";
        for(int i=0;i<c.getCount();i++){
            temp[0][i+1] = temp[1][i+1] = c.getString(c.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
            temp[2][i+1] = c.getString(c.getColumnIndexOrThrow(DbHelper.LOCATION_ID));
            c.moveToNext();
        }
        dbManager.close();
        return temp;
    }

    private String [][]dusunString(String parentId){
        dbManager.open();
        dbManager.clearClause();
        dbManager.setSelection(DbHelper.LOCATION_TAG+" = ? AND "+DbHelper.PARENT_LOCATION+" = ?");
        dbManager.setSelectionArgs(new String[]{"dusun",parentId});
        Cursor c = dbManager.fetchLocationTree();
        dbManager.clearClause();
        c.moveToFirst();
        String temp[][] = new String[2][c.getCount()+1];
        temp[0][0] = "---";temp[1][0]="~";
        for(int i=0;i<c.getCount();i++){
            temp[0][i+1] = temp[1][i+1] = c.getString(c.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
            c.moveToNext();
        }
        dbManager.close();
        return temp;
    }

    private String [][]dusunString(){
        dbManager.open();
        dbManager.clearClause();
        dbManager.setSelection(DbHelper.LOCATION_TAG+" = ?");
        dbManager.setSelectionArgs(new String[]{"dusun"});
        Cursor c = dbManager.fetchLocationTree();
        dbManager.clearClause();
        c.moveToFirst();
        String temp[][] = new String[2][c.getCount()+1];
        temp[0][0] = "---";temp[1][0]="~";
        for(int i=0;i<c.getCount();i++){
            temp[0][i+1] = temp[1][i+1] = c.getString(c.getColumnIndexOrThrow(DbHelper.LOCATION_NAME));
            c.moveToNext();
        }
        dbManager.close();
        return temp;
    }

}
