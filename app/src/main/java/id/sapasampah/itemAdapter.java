package id.sapasampah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class itemAdapter extends BaseAdapter {

    String[] date;
    String[] time;
    String[] amount;
    LayoutInflater mInflater;

    public itemAdapter(Context c, String[] d, String[] t, String[] a){
        date = d;
        time = t;
        amount = a;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return date.length;
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
        View view = mInflater.inflate(R.layout.history_listview, null);
        TextView historyDate = (TextView) view.findViewById(R.id.balanceDate);
        TextView historyTime = (TextView) view.findViewById(R.id.balanceTime);
        TextView historyAmount = (TextView) view.findViewById(R.id.historyAmount);

        String dispDate = date[position];
        String dispTime = time[position];
        String dispAmount = amount[position];

        historyDate.setText(dispDate);
        historyTime.setText(dispTime);
        historyAmount.setText(dispAmount);

        return view;
    }


}
