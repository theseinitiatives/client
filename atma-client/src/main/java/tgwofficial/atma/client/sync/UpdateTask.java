package tgwofficial.atma.client.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;


import tgwofficial.atma.client.activity.IdentitasIbuActivity;

public class UpdateTask extends AsyncTask<Void, Void, Void> {
private RestApi restApi = new RestApi();
  //  AsyncHttpClient client = new AsyncHttpClient("https://atma.theseforall.org");
    private Context mCon;

    public UpdateTask(Context con)
    {
        mCon = con;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Set a time to simulate a long update process.
        try {
            Thread.sleep(4000);

        } catch (Exception e) {
            return null;
        }
            restApi.pull();
            return null;

    }

    @Override
    protected void onPostExecute(Void param) {
        // Give some feedback on the UI.
        Toast.makeText(mCon, "Sync Finished!",
                Toast.LENGTH_LONG).show();

        // Change the menu back
        ((IdentitasIbuActivity) mCon).resetUpdating();
    }


}
