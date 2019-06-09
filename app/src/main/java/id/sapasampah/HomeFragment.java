package id.sapasampah;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    CarouselView homeCarouselview;
    int[] carouselSrc = {R.drawable.carousel1, R.drawable.carousel2};
    FrameLayout mainFrame;
    BottomNavigationView mNavView;
    TextView homeBalance;
    NotificationFragment notificationFragment;

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private HomeRecyclerAdapter homeRecyclerAdapter;
    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout homeElectr = view.findViewById(R.id.homeElectr);
        LinearLayout homeCred = view.findViewById(R.id.homeCred);
        LinearLayout homePdam = view.findViewById(R.id.homePdam);
        LinearLayout homeMore = view.findViewById(R.id.homeMore);

        recyclerView = view.findViewById(R.id.homeRecyclerView);

        homeBalance = view.findViewById(R.id.balanceText);

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
                        homeBalance.setText(balanceDisp);
                    }
                }
            });

            setUpRecyclerView();
        } else {
            Intent goLogin = new Intent(getContext(), LoginActivity.class);
            startActivity(goLogin);
            goLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        ImageView homeBalanceBtn = view.findViewById(R.id.balanceBtn);

        homeCarouselview = view.findViewById(R.id.homeCarouselview);
        homeCarouselview.setPageCount(carouselSrc.length);
        homeCarouselview.setImageListener(imageListener);

        notificationFragment = new NotificationFragment();
        mNavView = view.findViewById(R.id.mainMenu);

        homeElectr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent electrIntent = new Intent(getActivity(), PreElectrActivity.class);
                startActivity(electrIntent);
            }
        });

        homeCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent credIntent = new Intent(getActivity(), PreCredActivity.class);
                startActivity(credIntent);
            }
        });

        homePdam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdamIntent = new Intent(getActivity(), PdamActivity.class);
                startActivity(pdamIntent);
            }
        });

        homeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreIntent = new Intent(getActivity(), TransactionActivity.class);
                startActivity(moreIntent);
            }
        });

        //balance shortcut below

        homeBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeBalance = new Intent(getActivity(), BalanceActivity.class);
                startActivity(homeBalance);
            }
        });

        homeBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeBalanceBtn = new Intent(getActivity(), BalanceActivity.class);
                startActivity(homeBalanceBtn);
            }
        });

        return view;
    }

    public void setUpRecyclerView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            CollectionReference mColRef = mFirestore.collection("users").document(uid).collection("pickup");

            Query query = mColRef.orderBy("epoch", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<HomeRecycler> options = new FirestoreRecyclerOptions.Builder<HomeRecycler>().setQuery(query, HomeRecycler.class).build();

            homeRecyclerAdapter = new HomeRecyclerAdapter(options);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(homeRecyclerAdapter);
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(carouselSrc[position]);
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        homeRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        homeRecyclerAdapter.stopListening();
    }
}
