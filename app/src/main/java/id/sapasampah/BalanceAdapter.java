package id.sapasampah;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BalanceAdapter extends FirestoreRecyclerAdapter<Balance, BalanceAdapter.BalanceHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BalanceAdapter(@NonNull FirestoreRecyclerOptions<Balance> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BalanceHolder holder, int position, @NonNull Balance model) {
        holder.balanceDate.setText(model.getDate());
        holder.balanceTime.setText(model.getTime());
        holder.balanceAmount.setText(model.getAmount());
        holder.balanceTotal.setText("Saldo: " + model.getTotal());
    }

    @NonNull
    @Override
    public BalanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.balance_listview, viewGroup, false);
        return new BalanceHolder(v);
    }

    class BalanceHolder extends RecyclerView.ViewHolder{
        TextView balanceDate, balanceTime, balanceAmount, balanceTotal;

        public BalanceHolder(@NonNull View itemView) {
            super(itemView);
            balanceDate = itemView.findViewById(R.id.balanceDate);
            balanceTime = itemView.findViewById(R.id.balanceTime);
            balanceAmount = itemView.findViewById(R.id.balanceAmount);
            balanceTotal = itemView.findViewById(R.id.balanceTotal);
        }
    }
}

