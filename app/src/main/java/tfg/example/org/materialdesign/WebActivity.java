package tfg.example.org.materialdesign;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = WebActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "81c39ae2556fdc0f032d9a965252d8be";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView mWebView = (WebView) findViewById(R.id.web_view);
        //requestToken();

        getIncomingIntent();

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
              Uri url = request.getUrl();
              if( url.toString().contains("/allow")) {
                  WebActivity.this.finish();
              }
              return true;
            }
        });

        mWebView.loadUrl("https://www.themoviedb.org/authenticate/" +token);

        //mWebView.loadUrl(web);

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("token")){
            token = getIntent().getStringExtra("token");
        }
    }

    public void requestToken(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieAPIService movieApiService = retrofit.create(MovieAPIService.class);
        //URLEncoder.encode(query, "UTF-8");
        Call<RequestToken> call = movieApiService.getNewToken(API_KEY);

        call.enqueue(new Callback<RequestToken>(){
            @Override
            public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                token = response.body().getRequest_token();

            }

            @Override
            public void onFailure(Call<RequestToken> call, Throwable t) {
                Log.e(TAG, t.toString());
            }

        });
    }
}
