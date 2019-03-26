package id.sapasampah;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }

    ListView history_listview;
    String[] date;
    String[] time;
    String[] amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        Resources res = getResources();
        history_listview = (ListView) view.findViewById(R.id.historyListview);
        date = res.getStringArray(R.array.historyDate);
        time = res.getStringArray(R.array.historyTime);
        amount = res.getStringArray(R.array.historyAmount);

        itemAdapter adapter = new itemAdapter(getContext(),date,time,amount);
        history_listview.setAdapter(adapter);

        return view;

    }

}
