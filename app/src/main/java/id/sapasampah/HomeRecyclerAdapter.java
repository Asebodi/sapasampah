package id.sapasampah;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HomeRecyclerAdapter extends FirestoreRecyclerAdapter<HomeRecycler, HomeRecyclerAdapter.HomeHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HomeRecyclerAdapter(@NonNull FirestoreRecyclerOptions<HomeRecycler> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeHolder holder, int position, @NonNull HomeRecycler model) {
        holder.homeWeight.setText(model.getWeight());
        holder.homeTime.setText(model.getTime());
        holder.homeDate.setText(model.getDate());
        holder.homeAmount.setText(model.getAmount());
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_recyclerview,viewGroup,false);
        return new HomeHolder(view);
    }

    class HomeHolder extends RecyclerView.ViewHolder{

        TextView homeWeight, homeDate, homeTime, homeAmount;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            homeWeight = itemView.findViewById(R.id.homeWeight);
            homeDate = itemView.findViewById(R.id.homeDate);
            homeAmount = itemView.findViewById(R.id.homeAmount);
            homeTime = itemView.findViewById(R.id.homeTime);
        }
    }
}
