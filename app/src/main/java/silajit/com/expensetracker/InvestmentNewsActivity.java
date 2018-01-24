package silajit.com.expensetracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InvestmentNewsActivity extends AppCompatActivity {
    private WebView mWebNews;
    private ProgressBar mProgressBar;


    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_news);
        mProgressBar = (ProgressBar) findViewById(R.id.InvestmentprogressBar);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        mWebNews = (WebView) findViewById(R.id.InvestmentwebNews);
        WebSettings webSettings = mWebNews.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebNews.loadUrl("http://www.moneycontrol.com");
        mWebNews.setWebViewClient(new WebViewClient());
        mWebNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                if (progress.isShowing()) {
                    progress.dismiss();
                }
                Log.d("Website", "onPageFinished");

            }

        });

    }

    @Override
    public void onBackPressed() {
        if (mWebNews.canGoBack()) {
            mWebNews.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.investment_news_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_call_adviser:
                Intent mCallIntent = new Intent(Intent.ACTION_DIAL);
                startActivity(mCallIntent);
                break;

            case R.id.action_send_message:
                Intent mMesageIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"));
                startActivity(mMesageIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
     }
}