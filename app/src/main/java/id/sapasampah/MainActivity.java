package id.sapasampah;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavView;
    private FrameLayout mFrameLayout;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private UserFragment userFragment;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(goToLogin);
                    finish();
                }
            }
        };

        mNavView = (BottomNavigationView) findViewById(R.id.mainMenu);
        mFrameLayout = (FrameLayout) findViewById(R.id.mainFrame);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        userFragment = new UserFragment();

        setFragment(homeFragment);

        mNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homeMenu:
                        setFragment(homeFragment);
                        return true;

                    case R.id.notifMenu:
                        setFragment(notificationFragment);
                        return true;

                    case R.id.userMenu:
                        setFragment(userFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
        /*Button homeBtn = (Button) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String google = "google.com";
                Uri link = Uri.parse(google);

                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, link);
                if(gotoGoogle.resolveActivity(getPackageManager()) != null){
                    startActivity(gotoGoogle);
                }
            }
        });*/
    }

    private void setFragment(Fragment Fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, Fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
