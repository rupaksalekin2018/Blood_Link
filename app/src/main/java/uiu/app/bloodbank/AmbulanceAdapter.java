package uiu.app.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.AmbulanceViewHolder> {

    public static ClickListener clickListener;

    Context context;
    private ArrayList<Ambulance_data> ambulances;

    public AmbulanceAdapter(ArrayList<Ambulance_data> ambulances, Context context) {
        this.ambulances = ambulances;
        this.context = context;
    }

    @NonNull
    @Override
    public AmbulanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.amb_layout, viewGroup, false);

        return new AmbulanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceViewHolder holder,final int position) {
        holder.tvAmbName.setText(ambulances.get(position).amb_name);
        holder.tvAmbPos.setText(ambulances.get(position).amb_pos);
        holder.tvAmbPhone.setText(ambulances.get(position).amb_phone);
        holder.tvAmbAdd.setText(ambulances.get(position).amb_addr);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+ambulances.get(position).amb_phone));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ambulances.size();
    }

    class AmbulanceViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvAmbName, tvAmbPhone, tvAmbPos, tvAmbAdd;
        LinearLayout linearLayout;

        public AmbulanceViewHolder(@NonNull View itemView) {

            super(itemView);

            tvAmbName = itemView.findViewById(R.id.ambName);
            tvAmbPhone = itemView.findViewById(R.id.ambPhone);
            tvAmbPos = itemView.findViewById(R.id.ambPos);
            tvAmbAdd = itemView.findViewById(R.id.ambAddress);

            linearLayout = itemView.findViewById(R.id.llambdet);
            //linearLayout.setOnClickListener(this);
            //linearLayout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public interface ClickListener{
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        AmbulanceAdapter.clickListener = clickListener;
    }

    public void updateList(ArrayList<Ambulance_data> newAmbulanceList)
    {

        ambulances = new ArrayList<Ambulance_data>();
        ambulances.addAll(newAmbulanceList);
        notifyDataSetChanged();

    }
}
