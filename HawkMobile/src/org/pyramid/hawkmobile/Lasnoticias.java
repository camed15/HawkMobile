package org.pyramid.hawkmobile;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class Lasnoticias extends Activity {
	ProgressDialog mProgress;

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lasnoticias);

		webview = (WebView) findViewById(R.id.webview1);
		webview.getSettings().setJavaScriptEnabled(true);
		mProgress = ProgressDialog.show(this, "Cargando",
				"Por favor espera un momento...");
		webview.setWebViewClient(new WebViewClient() {
			// load url
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			// when finish loading page
			public void onPageFinished(WebView view, String url) {
				if (mProgress.isShowing()) {
					mProgress.dismiss();
				}
				webview.scrollBy(0, 300);
			}
		});

		openURL();

	}

	/** Opens the URL in a browser */
	private void openURL() {
		webview.loadUrl("https://m.facebook.com/InstitutoTecnologicoDeDelicias");

		webview.requestFocus();
	}
	public void cierra(View view){
		this.finish();
		
		
	}
}