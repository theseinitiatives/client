package theseinitiatives.atma.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.BankDarahActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuDetailActivity;
import theseinitiatives.atma.client.activity.InformasiActivity;
import theseinitiatives.atma.client.activity.KaderActivity;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.application.App;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class NavigationmenuController {
    private static final String TAG =NavigationmenuController.class.getCanonicalName() ;
    private final Activity activity;


    public NavigationmenuController(Activity activity) {
        this.activity = activity;
    }

    public void startIdentitasIbu() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startTransportasi() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startBankDarah() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startInformasi() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, InformasiActivity.class));
    }

    public void addKader() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, KaderActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void backtoIbu(){
        backtoIbu("");
    }

    public void backtoIbu(String s){
        FlurryHelper.endFlurryLog(activity,s);
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void backtoTrans(){
        backtoTrans("");
    }
    public void backtoTrans(String s){
        FlurryHelper.endFlurryLog(activity,s);
        activity.finish();
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void backtodarah(){
        backtodarah("");
    }
    public void backtodarah(String s){
        FlurryHelper.endFlurryLog(activity,s);
        activity.finish();
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
        activity.overridePendingTransition(0,0);
    }


    public void backtoDetail() {
        FlurryHelper.endFlurryLog(activity);
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuDetailActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void gotoKIA() {
        FlurryHelper.endFlurryLog(activity);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://kie.atma.theseforall.org/"));
        activity.startActivity(browserIntent);
        activity.finish();
    }

    public void navigateTo(MenuItem item, boolean forbidden){
        // Handle navigation view item clicks here.
        NavigationmenuController navi= new NavigationmenuController(activity);
        int id = item.getItemId();
        if (id == R.id.nav_identitas_ibu) {
            if (!activity.getLocalClassName().equals(IdentitasIbuActivity.class.getCanonicalName()))
                navi.startIdentitasIbu();
        }
        if (id == R.id.nav_transportasi) {
            if (!activity.getLocalClassName().equals(TransportasiActivity.class.getCanonicalName()))
            navi.startTransportasi();
        }

        if (id == R.id.nav_bank_darah) {
            if (!activity.getLocalClassName().equals(BankDarahActivity.class.getCanonicalName()))
             navi.startBankDarah();
        }
        if(id == R.id.nav_logout){
//            super.onBackPressed();
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            SharedPreferences sharedPref = activity.getSharedPreferences(AllConstants.SHARED_PREF,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.remove(activity.getString(R.string.loggedin));
                            editor.apply();
                            Intent intent = new Intent(activity,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            FlurryHelper.endFlurryLog(activity);
                            activity.finish();
                            Log.d(TAG, "onEndSession: "+activity.getClass().getSimpleName()+" -> "+dateNow().toString());
                            FlurryAgent.onEndSession(App.getAppContext());
                            activity.startActivity(intent);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Anda yakin untuk \"Keluar\" dari aplikasi?").setPositiveButton("Ya", dialogClickListener)
                    .setNegativeButton("Tidak", dialogClickListener).show();
        }
        if(id == R.id.info){
            if (!activity.getLocalClassName().equals(InformasiActivity.class.getCanonicalName())){
                navi.startInformasi();
            }
        }
        if(id == R.id.kader_add){
            if (!activity.getLocalClassName().equals(KaderActivity.class.getCanonicalName())){
                if(!forbidden) {
                    navi.addKader();
                }
                else
                    Toast.makeText(activity, "Maaf fitur ini hanya untuk bidan!",
                            Toast.LENGTH_LONG).show();
            }


        }
        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
