package id.sapasampah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Locale;

public class BalanceActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    private BalanceAdapter adapter;

    ListView balanceListview;
    TextView balanceStatementText;
    String[] date;
    String[] time;
    String[] amount;
    String[] type;
    String[] stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        Toolbar toolbar = findViewById(R.id.balanceToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        balanceStatementText = findViewById(R.id.balanceStatementText);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            mColRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Integer balance = Integer.parseInt(documentSnapshot.getString("balance"));
                        String balanceFormat = String.format(Locale.US, "%,d", balance).replace(",", ".");
                        String balanceDisp = "Rp " + balanceFormat;
                        balanceStatementText.setText(balanceDisp);
                    }
                }
            });

            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            CollectionReference mColRef = mFirestore.collection("users").document(uid).collection("pickup");

            recyclerView = findViewById(R.id.balanceRecyclerView);

            Query query = mColRef.orderBy("epoch", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<Balance> options = new FirestoreRecyclerOptions.Builder<Balance>().setQuery(query, Balance.class).build();

            adapter = new BalanceAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
/*
        Resources res = getResources();
        balanceListview = (ListView) findViewById(R.id.balanceListview);
        date = res.getStringArray(R.array.balanceDate);
        time = res.getStringArray(R.array.balanceTime);
        amount = res.getStringArray(R.array.balanceAmount);
        type = res.getStringArray(R.array.balanceType);
        stat = res.getStringArray(R.array.balanceStat);

        BalanceItemAdapter adapter = new BalanceItemAdapter(getApplicationContext(), date, time, amount, type, stat);
        balanceListview.setAdapter(adapter); */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
