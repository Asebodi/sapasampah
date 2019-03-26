package id.sapasampah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class PdamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdam);

        Button pdamBtn = (Button) findViewById(R.id.pdamBtn);
        Spinner pdamSpinner = (Spinner) findViewById(R.id.pdamSpinner);
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.pdamToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] pdamAreas = new String[] {"----"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pdamAreas);
        pdamSpinner.setAdapter(adapter);

        pdamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PdamActivity.this, "Maaf, fitur ini belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
