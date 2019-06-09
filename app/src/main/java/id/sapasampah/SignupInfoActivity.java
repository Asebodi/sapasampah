package id.sapasampah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupInfoActivity extends AppCompatActivity {

    EditText signupUsername, signupNik, signupProvince, signupCity, signupDistrict, signupPostal, signupFullAddress;
    ImageView nextImg;
    ProgressBar progressbar;
    static final String balance = "0";
    private FirebaseAuth mAuth;
    private CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");
    private CollectionReference mActive = FirebaseFirestore.getInstance().collection("active");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_info);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        signupUsername = findViewById(R.id.signupUsername);
        signupNik = findViewById(R.id.signupNik);
        signupProvince = findViewById(R.id.signupProvince);
        signupCity = findViewById(R.id.signupCity);
        signupDistrict = findViewById(R.id.signupDistrict);
        signupPostal = findViewById(R.id.signupPostal);
        signupFullAddress = findViewById(R.id.signupFullAddress);

        nextImg = findViewById(R.id.signupInfoBtn);
        progressbar = findViewById(R.id.signupInfoProgressbar);

        nextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    final String uid = user.getUid();

                    String username = signupUsername.getText().toString();
                    String nik = signupNik.getText().toString();
                    String province = signupProvince.getText().toString();
                    String city = signupCity.getText().toString();
                    String district = signupDistrict.getText().toString();
                    String postal = signupPostal.getText().toString();
                    String fullAddress = signupFullAddress.getText().toString();

                    if (username.isEmpty()) {
                        signupUsername.setError("Tidak bolek kosong!");
                        signupUsername.requestFocus();
                        return;
                    }

                    if (nik.isEmpty()) {
                        signupNik.setError("Tidak bolek kosong!");
                        signupNik.requestFocus();
                        return;
                    }

                    if (province.isEmpty()) {
                        signupProvince.setError("Tidak bolek kosong!");
                        signupProvince.requestFocus();
                        return;
                    }

                    if (city.isEmpty()) {
                        signupCity.setError("Tidak bolek kosong!");
                        signupCity.requestFocus();
                        return;
                    }

                    if (district.isEmpty()) {
                        signupDistrict.setError("Tidak bolek kosong!");
                        signupDistrict.requestFocus();
                        return;
                    }

                    if (postal.isEmpty()) {
                        signupPostal.setError("Tidak bolek kosong!");
                        signupPostal.requestFocus();
                        return;
                    }

                    if (fullAddress.isEmpty()) {
                        signupFullAddress.setError("Tidak bolek kosong!");
                        signupFullAddress.requestFocus();
                        return;
                    }

                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    user.updateProfile(profileUpdate);

                    Map<String, Object> userData = new HashMap<String, Object>();
                    userData.put("username", username);
                    userData.put("nik", nik);
                    userData.put("province",province);
                    userData.put("city",city);
                    userData.put("district",district);
                    userData.put("postal",postal);
                    userData.put("fullAddr",fullAddress);
                    userData.put("balance",balance);
                    
                    final Map<String, Object> activeData = new HashMap<>();
                    activeData.put("username", username);
                    activeData.put("province",province);
                    activeData.put("city",city);
                    activeData.put("district",district);
                    activeData.put("postal",postal);
                    activeData.put("fullAddr",fullAddress);
                    
                    mColRef.document(uid).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mActive.document(uid).set(activeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignupInfoActivity.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                                        Intent goNext = new Intent(SignupInfoActivity.this, MainActivity.class);
                                        startActivity(goNext);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignupInfoActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                
                            } else {
                                Toast.makeText(SignupInfoActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
