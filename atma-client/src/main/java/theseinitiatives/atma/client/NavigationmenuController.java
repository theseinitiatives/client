package theseinitiatives.atma.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import theseinitiatives.atma.client.activity.BankDarahActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuDetailActivity;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.activity.nativeform.FormAddKader;

public class NavigationmenuController {
    private Activity activity;


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
        activity.startActivity(new Intent(activity, FormAddKader.class));
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
}
