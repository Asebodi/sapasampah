package id.sapasampah;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


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

    FirebaseAuth mAuth;
    CollectionReference mColRef = FirebaseFirestore.getInstance().collection("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout homeElectr = view.findViewById(R.id.homeElectr);
        LinearLayout homeCred = view.findViewById(R.id.homeCred);
        LinearLayout homePdam = view.findViewById(R.id.homePdam);
        LinearLayout homeMore = view.findViewById(R.id.homeMore);

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
                        String balanceFormat = String.format("%,d", balance);
                        String balanceDisp = "Rp " + balanceFormat;
                        homeBalance.setText(balanceDisp);
                    }
                }
            });
        }

        ImageView homeBalanceBtn = view.findViewById(R.id.balanceBtn);

        homeCarouselview = view.findViewById(R.id.homeCarouselview);
        homeCarouselview.setPageCount(carouselSrc.length);
        homeCarouselview.setImageListener(imageListener);

        notificationFragment = new NotificationFragment();
        mNavView = view.findViewById(R.id.mainMenu);
        //final View history = mNavView.findViewById(R.id.notifMenu);

        ListView homeListview;
        String[] date;
        String[] time;
        String[] amount;
        String[] weight;

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


        //Home fragment quick history listview
        Resources res = getResources();
        homeListview = (ListView) view.findViewById(R.id.homeListview);
        date = res.getStringArray(R.array.historyDate);
        time = res.getStringArray(R.array.historyTime);
        amount = res.getStringArray(R.array.historyAmount);
        weight = res.getStringArray(R.array.historyWeight);

        HomeItemAdapter adapter = new HomeItemAdapter(getContext(),date,time,amount,weight);
        homeListview.setAdapter(adapter);

        return view;
        //TODO: Fetch data from Firebase
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(carouselSrc[position]);
        }
    };

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }

}
