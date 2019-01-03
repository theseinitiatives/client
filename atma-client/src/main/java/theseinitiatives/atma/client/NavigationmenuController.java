package theseinitiatives.atma.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import theseinitiatives.atma.client.Utils.FlurryHelper;
import theseinitiatives.atma.client.activity.BankDarahActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuActivity;
import theseinitiatives.atma.client.activity.IdentitasIbuDetailActivity;
import theseinitiatives.atma.client.activity.KaderActivity;
import theseinitiatives.atma.client.activity.TransportasiActivity;
import theseinitiatives.atma.client.activity.nativeform.FormAddKader;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

public class NavigationmenuController {
    private Activity activity;


    public NavigationmenuController(Activity activity) {
        this.activity = activity;
    }

    public void startIdentitasIbu() {
        //start logging for Identitas Ibu
        Map<String, String> Params = new HashMap<String, String>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent("IdentitasIbu",Params,true);

        activity.finish();
        activity.startActivity(new Intent(activity, IdentitasIbuActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startTransportasi() {
        //start logging for Identitas Ibu
        Map<String, String> Params = new HashMap<String, String>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent("Transportasi",Params,true);
        activity.finish();
        activity.startActivity(new Intent(activity, TransportasiActivity.class));
        activity.overridePendingTransition(0,0);
    }
    public void startBankDarah() {
        //start logging for Identitas Ibu
        Map<String, String> Params = new HashMap<String, String>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent("BankDarah",Params,true);
        activity.finish();
        activity.startActivity(new Intent(activity, BankDarahActivity.class));
        activity.overridePendingTransition(0,0);
    }

    public void addKader() {
        //start logging for Identitas Ibu
        Map<String, String> Params = new HashMap<String, String>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent("Kader",Params,true);
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
}
