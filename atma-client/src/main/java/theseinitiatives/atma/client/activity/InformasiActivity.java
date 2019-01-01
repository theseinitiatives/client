package theseinitiatives.atma.client.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import theseinitiatives.atma.client.NavigationmenuController;
import theseinitiatives.atma.client.R;

public class InformasiActivity extends Activity {
    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    /** Called when the activity is first created. */@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.informasi_layout);

        this.webview = (WebView) findViewById(R.id.webView);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(InformasiActivity.this, "", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(InformasiActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl("https://kie.atma.theseforall.org/");
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }
}




/*extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informasi_layout);
        webview =(WebView)findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(Main.this, "Showing ProgressDialog", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(Main.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl("https://kie.atma.theseforall.org/");
        // chromium, enable hardware acceleration
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        NavigationmenuController navi= new NavigationmenuController(this);
        navi.backtoIbu();

    }
}*/