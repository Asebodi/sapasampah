package id.sapasampah;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserAddressSettingActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CollectionReference mColRef;
    EditText settingName, settingProvince, settingCity, settingDisctict, settingPostal, settingFullAddr;
    Button settingUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address_setting);

        Toolbar toolbar = findViewById(R.id.addressEditToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingName = findViewById(R.id.settingName);
        settingProvince = findViewById(R.id.settingProvince);
        settingCity = findViewById(R.id.settingCity);
        settingDisctict = findViewById(R.id.settingDistrict);
        settingPostal = findViewById(R.id.settingPostal);
        settingFullAddr = findViewById(R.id.settingFullAddr);

        settingUpdateBtn = findViewById(R.id.settingUpdateBtn);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        mColRef = FirebaseFirestore.getInstance().collection("users");

        mColRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String province = documentSnapshot.getString("province");
                    String city = documentSnapshot.getString("city");
                    String district = documentSnapshot.getString("district");
                    String postal = documentSnapshot.getString("postal");
                    String fullAddr = documentSnapshot.getString("fullAddr");

                    settingName.setText(user.getDisplayName());
                    settingProvince.setText(province);
                    settingCity.setText(city);
                    settingDisctict.setText(district);
                    settingPostal.setText(postal);
                    settingFullAddr.setText(fullAddr);
                }
            }
        });

        settingUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateName = settingName.getText().toString();
                String updateProvince = settingProvince.getText().toString();
                String updateCity = settingCity.getText().toString();
                String updateDistrict = settingDisctict.getText().toString();
                String updatePostal = settingPostal.getText().toString();
                String updateFullAddr = settingFullAddr.getText().toString();

                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(updateName).build();
                user.updateProfile(profileUpdate);

                Map<String, Object> updateData = new HashMap<String, Object> ();
                updateData.put("province",updateProvince);
                updateData.put("city",updateCity);
                updateData.put("district",updateDistrict);
                updateData.put("postal",updatePostal);
                updateData.put("fullAddr",updateFullAddr);

                mColRef.document(uid).update(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UserAddressSettingActivity.this, "Update alamat berhasil!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserAddressSettingActivity.this, "Terjadi kesalahan dalam mengganti alamat", Toast.LENGTH_SHORT).show();
                    }
                });
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
