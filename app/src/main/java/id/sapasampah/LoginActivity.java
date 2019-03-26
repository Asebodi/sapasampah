package id.sapasampah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailLogin, passwordLogin;
    TextView register;
    Button loginBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        }

        emailLogin = findViewById(R.id.signinEmail);
        passwordLogin = findViewById(R.id.signinPassword);
        progressBar = findViewById(R.id.loginProgressBar);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        register = findViewById(R.id.registerTextview);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerTextview :
                Intent registerIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(registerIntent);
                finish();
                break;

            case R.id.loginBtn :
                loginUser();
                break;
        }
    }

    private void loginUser() {

        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (email.isEmpty()) {
            emailLogin.setError("Email belum terisi!");
            emailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLogin.setError("Email tidak valid!");
            emailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordLogin.setError("Password belum terisi!");
            passwordLogin.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Selamat datang kembali!", Toast.LENGTH_SHORT).show();
                    Intent goToNext = new Intent(LoginActivity.this, MainActivity.class);
                    //goToNext.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goToNext);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
