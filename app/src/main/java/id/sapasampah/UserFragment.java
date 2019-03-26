package id.sapasampah;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }

    FirebaseAuth mAuth;
    CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");
    TextView userBalanceText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        userBalanceText = view.findViewById(R.id.userBalanceText);

        ConstraintLayout userBalance = (ConstraintLayout) view.findViewById(R.id.userBalance);
        ConstraintLayout userAddr = (ConstraintLayout) view.findViewById(R.id.userAddr);
        ConstraintLayout userQrCode = (ConstraintLayout) view.findViewById(R.id.userQrCode);
        ConstraintLayout userGuide = (ConstraintLayout) view.findViewById(R.id.userGuide);
        ConstraintLayout userAbout = (ConstraintLayout) view.findViewById(R.id.userAbout);
        ConstraintLayout logoutLayout = view.findViewById(R.id.signoutLayout);
        TextView username = view.findViewById(R.id.userNameTextview);

        if (user != null) {
            username.setText(mAuth.getCurrentUser().getDisplayName());
            String uid = user.getUid();
            mColRef.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String balance = documentSnapshot.getString("balance");
                        String balanceDisp = "Rp " + balance;
                        userBalanceText.setText(balanceDisp);
                    }
                }
            });

        }

        userBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToBalance = new Intent(getActivity(), BalanceActivity.class);
                startActivity(goToBalance);
            }
        });

        userAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddr = new Intent(getActivity(), UserAddressActivity.class);
                startActivity(goToAddr);
            }
        });

        userQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToQr = new Intent(getActivity(), QrActivity.class);
                startActivity(goToQr);
            }
        });

        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGuide = new Intent(getActivity(), UserGuideActivity.class);
                startActivity(goToGuide);
            }
        });

        userAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(goToAbout);
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });


        return view;
    }

}
