package com.example.studentportalportfolio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class DMCI extends AppCompatActivity {
    private static String m_DMCI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmci);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        final EditText taskEditText2 = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("URL")
                .setMessage("www.example.com")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_DMCI = String.valueOf(taskEditText.getText());
                        loadPage(m_DMCI);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void loadPage(String web){
        if (web.length() == 0) {
            //nothing
        } else {
            WebView webView;
            webView = (WebView) findViewById(R.id.webView1);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://"+web);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //list.add
        }
    }

}
