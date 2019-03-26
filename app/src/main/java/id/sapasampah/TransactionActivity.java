package id.sapasampah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        LinearLayout moreCredPre = findViewById(R.id.moreCredPre);
        LinearLayout moreElectrPre = findViewById(R.id.moreElectrPre);
        LinearLayout moreCredPost = findViewById(R.id.moreCredPost);
        LinearLayout moreElectrPost = findViewById(R.id.moreElectrPost);
        LinearLayout morePdam = findViewById(R.id.morePdam);

        Toolbar transactionToolbar = findViewById(R.id.transactionToolbar);
        setSupportActionBar(transactionToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        moreCredPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent credPreIntent = new Intent(getApplicationContext(), PreCredActivity.class);
                startActivity(credPreIntent);
            }
        });

        moreElectrPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent electrPreIntent = new Intent(getApplicationContext(), PreElectrActivity.class);
                startActivity(electrPreIntent);
            }
        });

        moreCredPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent credPostIntent = new Intent(getApplicationContext(), PostCredActivity.class);
                startActivity(credPostIntent);
            }
        });

        moreElectrPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent electrPostIntent = new Intent(getApplicationContext(), PostElectrActivity.class);
                startActivity(electrPostIntent);
            }
        });

        morePdam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdamIntent = new Intent(getApplicationContext(), PdamActivity.class);
                startActivity(pdamIntent);
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
