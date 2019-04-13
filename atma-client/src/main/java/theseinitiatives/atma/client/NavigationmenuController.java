package theseinitiatives.atma.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.BankDarahActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuDetailActivity;
import theseinitiatives.atma.client.activity.InformasiActivity;
import theseinitiatives.atma.client.activity.KaderActivity;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.activity.nativeform.FormAddKader;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class NavigationmenuController {
    private static final String TAG =NavigationmenuController.class.getCanonicalName() ;
    private final Activity activity;


    public NavigationmenuController(Activity activity) {
        this.activity = activity;
    }

    public void startIdentitasIbu() {
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startTransportasi() {
        activity.finish();
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startBankDarah() {
        activity.finish();
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void addKader() {
        activity.finish();
        activity.startActivity(new Intent(activity, KaderActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void backtoIbu(){
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void backtoTrans(){
        activity.finish();
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void backtodarah(){
        activity.finish();
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
        activity.overridePendingTransition(0,0);
    }


    public void backtoDetail() {
        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuDetailActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void gotoKIA() {
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
                            Intent intent = new Intent(activity,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.finish();
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
                Intent intent = new Intent(activity, InformasiActivity.class);
                activity.startActivity(intent);
                activity.finish();
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
