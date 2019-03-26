package id.sapasampah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeItemAdapter extends BaseAdapter {

    String[] date;
    String[] time;
    String[] amount;
    String[] weight;
    LayoutInflater mInflater;

    public HomeItemAdapter(Context c, String[] d, String[] t, String[] a, String[] w){
        date = d;
        time = t;
        amount = a;
        weight = w;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return date[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.home_listview, null);
        TextView homeDate = (TextView) view.findViewById(R.id.homeDate);
        TextView homeTime = (TextView) view.findViewById(R.id.homeTime);
        TextView homeAmount = (TextView) view.findViewById(R.id.homeAmount);
        TextView homeWeight = (TextView) view.findViewById(R.id.homeWeight);

        String dispDate = date[position];
        String dispTime = time[position];
        String dispAmount = amount[position];
        String dispWeight = weight[position];

        homeDate.setText(dispDate);
        homeTime.setText(dispTime);
        homeAmount.setText(dispAmount);
        homeWeight.setText(dispWeight);

        return view;
    }
}
