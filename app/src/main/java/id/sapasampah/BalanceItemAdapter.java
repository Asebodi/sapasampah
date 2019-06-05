package id.sapasampah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BalanceItemAdapter extends BaseAdapter {

    String[] date;
    String[] time;
    String[] amount;
    String[] type;
    String[] stat;
    LayoutInflater mInflater;

    public BalanceItemAdapter(Context c, String[] d, String[] t, String[] a, String[] y, String[] s){
        date = d;
        time = t;
        amount = a;
        type = y;
        stat = s;
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
        View view = mInflater.inflate(R.layout.balance_listview, null);
        TextView balanceDate = (TextView) view.findViewById(R.id.balanceDate);
        TextView balanceTime = (TextView) view.findViewById(R.id.balanceTime);
        TextView balanceAmount = (TextView) view.findViewById(R.id.balanceAmount);
        TextView balanceType = (TextView) view.findViewById(R.id.balanceType);
        TextView balanceStat = (TextView) view.findViewById(R.id.balanceTotal);

        String dispDate = date[position];
        String dispTime = time[position];
        String dispAmount = amount[position];
        String dispType = type[position];
        String dispStat = stat[position];

        balanceDate.setText(dispDate);
        balanceTime.setText(dispTime);
        balanceAmount.setText(dispAmount);
        balanceType.setText(dispType);
        balanceStat.setText(dispStat);

        return view;
    }
}
