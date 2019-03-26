package id.sapasampah;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class PreElectrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_electr);

        Button electrPreBtn = (Button) findViewById(R.id.preElectrBtn);
        Spinner valElectrSpinner = (Spinner) findViewById(R.id.valElectrSpinner);
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.preElectrToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] items = new String[] {"Rp20.000", "Rp50.000", "Rp100.000","Rp200.000","Rp500.000"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        valElectrSpinner.setAdapter(adapter);

        electrPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PreElectrActivity.this, "Maaf, fitur ini belum tersedia", Toast.LENGTH_SHORT).show();
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
