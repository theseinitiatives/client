package tgwofficial.atma.client;

import android.app.Activity;
import android.content.Intent;

import tgwofficial.atma.client.activity.IdentitasIbuActivity;
import tgwofficial.atma.client.activity.TransportasiActivity;

public class NavigationmenuController {
    private Activity activity;


    public NavigationmenuController(Activity activity) {
        this.activity = activity;
    }

    public void startIdentitasIbu() {
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
    }
    public void startTransportasi() {
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
    }
    /*public void startBankDarah() {
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
    }*/

}
