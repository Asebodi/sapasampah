package id.sapasampah;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HistoryAdapter extends FirestoreRecyclerAdapter<History, HistoryAdapter.HistoryHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<History> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryHolder holder, int position, @NonNull History model) {
        holder.dateTextview.setText(model.getDate());
        holder.timeTextview.setText(model.getTime());
        holder.amountTextview.setText(model.getAmount());
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_recyclerview, viewGroup, false);
        return new HistoryHolder(v);
    }


    class HistoryHolder extends RecyclerView.ViewHolder{

        TextView dateTextview, timeTextview, amountTextview;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            dateTextview = itemView.findViewById(R.id.historyDate);
            timeTextview = itemView.findViewById(R.id.historyTime);
            amountTextview = itemView.findViewById(R.id.historyAmount);
        }
    }
}
