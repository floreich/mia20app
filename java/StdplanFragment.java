package ml.mia20.mia20app.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import ml.mia20.mia20app.R;



public class StdplanFragment extends Fragment {


    public StdplanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      final  View rootView = inflater.inflate(R.layout.fragment_stdplan, container, false);

        WebView wv = (WebView) rootView.findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {
                //hide loading image
                rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
                //show webview
                rootView.findViewById(R.id.webview).setVisibility(View.VISIBLE);
            }


        });

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
           wv.loadUrl("https://stundenplan.mia20.ml/appstdplan");
        } else {
            wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
           wv.loadUrl("https://stundenplan.mia20.ml/appstdplan");
        }


        return rootView ;
    }}

