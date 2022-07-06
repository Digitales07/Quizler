package com.assorttech.myquizler;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mToolbar = findViewById(R.id.settings_toolbar);
        mTextView = findViewById(R.id.tv_version);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Settings");

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mTextView.setText("Version: "+version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(findViewById(R.id.fragment_container)!=null)
        {
            if(savedInstanceState!=null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container,new SettingsFragment()).commit();
        }
    }
}
