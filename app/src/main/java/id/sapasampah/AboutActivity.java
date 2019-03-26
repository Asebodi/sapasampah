package id.sapasampah;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView version = findViewById(R.id.appVersion);
        try {
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String appVersion = info.versionName;
            version.setText("v "+appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
