package id.sapasampah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAddressActivity extends AppCompatActivity {

    TextView addrUsername, addrFull;
    private FirebaseAuth mAuth;
    private CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        addrUsername = findViewById(R.id.addressName);
        addrFull = findViewById(R.id.addressFull);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName();
            String uid = user.getUid();
            addrUsername.setText(username);

            mColRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String province = documentSnapshot.getString("province");
                        String city = documentSnapshot.getString("city");
                        String district = documentSnapshot.getString("district");
                        String postal = documentSnapshot.getString("postal");
                        String fullAddr = documentSnapshot.getString("fullAddr");
                        addrFull.setText(fullAddr + ", " + district + ", " + city + ", " + province + ", " + postal);
                    }
                }
            });
        }
        Toolbar toolbar = findViewById(R.id.userAddrToolbar);
        Button addrEditBtn = findViewById(R.id.addrEditBtn);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addrEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getApplicationContext(), UserAddressSettingActivity.class);
                startActivity(editIntent);
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
