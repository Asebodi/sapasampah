package id.sapasampah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usernameSignup, emailSignup, passwordSignup, rePasswordSignup;
    ImageView nextImg;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        emailSignup = findViewById(R.id.signupUsername);
        passwordSignup = findViewById(R.id.signupNik);
        rePasswordSignup = findViewById(R.id.signupProvince);

        progressbar = findViewById(R.id.signupProgressbar);

        nextImg = findViewById(R.id.signupBtn);
        nextImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupBtn:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = emailSignup.getText().toString().trim();
        String password = passwordSignup.getText().toString().trim();
        String repassword = rePasswordSignup.getText().toString().trim();

        if (email.isEmpty()) {
            emailSignup.setError("Email belum terisi!");
            emailSignup.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignup.setError("Email tidak valid!");
            emailSignup.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordSignup.setError("Password belum terisi!");
            passwordSignup.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordSignup.setError("Password kurang dari 6 huruf!");
            passwordSignup.requestFocus();
            return;
        }

        if (!password.matches(repassword)) {
            passwordSignup.setError("Password tidak cocok!");
            passwordSignup.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent goToNext = new Intent(SignupActivity.this, SignupInfoActivity.class);
                    startActivity(goToNext);
                    finish();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(SignupActivity.this, "Alamat email sudah pernah didaftarkan!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
