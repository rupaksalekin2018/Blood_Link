package uiu.app.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.view.View.GONE;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static ClickListener clickListener;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String userKey = "phoneKey";
    public static final String passKey = "passKey";
    public static final String imgKey = "imageBitmap";
    public static final String nameKey = "nameOfuser";
    public static final String bloodKey = "b_groupofuser";

    private Activity activity;
    Context context;
    private ArrayList<Feed_Data> feeds;
    String passBG="";

    public FeedAdapter(Context context, ArrayList<Feed_Data> feeds) {
        this.context = context;
        this.feeds = feeds;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.feed_layout, viewGroup, false);
        sharedpreferences = context.getApplicationContext().getSharedPreferences(mypreference, 0); // 0 - for private mode

        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        String b_group = null;
        if(sharedpreferences.contains(bloodKey)){
            b_group = sharedpreferences.getString(bloodKey,"");
            passBG = b_group;
        }
        if(feeds.get(position).b_group.equals(b_group))
        {
            holder.bloodgroup.setText(feeds.get(position).b_group);
            holder.address.setText(feeds.get(position).add_hospital);
            holder.gender.setText(feeds.get(position).gender);
            holder.amt_of_blood.setText(feeds.get(position).amount_blood);
            holder.eDate.setText(feeds.get(position).date);
            holder.eTime.setText(feeds.get(position).time);
            holder.message.setText(feeds.get(position).msg);
            holder.fullBoardLL.setBackgroundResource(R.drawable.feed_backgroud);
            holder.accept.setVisibility(TextView.VISIBLE);
            holder.decline.setVisibility(TextView.VISIBLE);
        }
        else
        {
            holder.bloodgroup.setText(feeds.get(position).b_group);
            holder.bloodgroup2.setText(feeds.get(position).b_group);
            holder.address.setText(feeds.get(position).add_hospital);
            holder.gender.setText(feeds.get(position).gender);
            holder.amt_of_blood.setText(feeds.get(position).amount_blood);
            holder.eDate.setText(feeds.get(position).date);
            holder.eTime.setText(feeds.get(position).time);
            holder.message.setText(feeds.get(position).msg);
            holder.fullBoardLL.setBackgroundResource(R.drawable.feed_backgroud_one);
            //holder.accept.setVisibility(GONE);
            //holder.decline.setVisibility(GONE);
            holder.buttons.setVisibility(GONE);
            holder.bloodgroup2.setVisibility(View.VISIBLE);
            holder.bloodgroup.setVisibility(GONE);
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        TextView bloodgroup, address, gender, amt_of_blood, eDate, eTime, message, accept, decline,bloodgroup2;
        LinearLayout fullBoardLL,buttons;

        public FeedViewHolder(@NonNull View itemView) {

            super(itemView);

            bloodgroup = itemView.findViewById(R.id.feedLayoutBloodGroup);
            bloodgroup2 = itemView.findViewById(R.id.feedLayoutBloodGroup2);
            address = itemView.findViewById(R.id.feedLayoutAddress);
            gender = itemView.findViewById(R.id.feedLayoutGender);
            amt_of_blood = itemView.findViewById(R.id.feedLayoutBloodBag);
            eDate = itemView.findViewById(R.id.feedLayoutExpectedDate);
            eTime = itemView.findViewById(R.id.feedLayoutExpectedTime);
            message = itemView.findViewById(R.id.feedLayoutMessage);

            accept = itemView.findViewById(R.id.feedLayoutAccept);
            decline = itemView.findViewById(R.id.feedLayoutDecline);

            fullBoardLL = itemView.findViewById(R.id.fullBoardLL);
            buttons = itemView.findViewById(R.id.feedLayoutLinearOnOff);

            fullBoardLL.setOnLongClickListener(this);
            fullBoardLL.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


//            //clickListener.onItemClick(getAdapterPosition(), v);
//            Dialog dialog = new Dialog();
//            dialog.address = feeds.get(getAdapterPosition()).add_hospital;
//            dialog.sendMsg = feeds.get(getAdapterPosition()).b_group + " blood is needed.\n" + "\nAmount of blood: " + feeds.get(getAdapterPosition()).amount_blood+" bag(s).\n" +
//                    "Date: "+ feeds.get(getAdapterPosition()).date + ", Time: "+feeds.get(getAdapterPosition()).time + ".\n" + "\nPhone number: " + feeds.get(getAdapterPosition()).user + ".\n" +
//                    "\nAddress: " + feeds.get(getAdapterPosition()).add_hospital + ".\n" + "\nMessage:\n" + feeds.get(getAdapterPosition()).msg;
//            dialog.show();

            Intent i = new Intent(context, FeedShow.class);
            i.putExtra("Blood_Group", feeds.get(getAdapterPosition()).b_group);
            i.putExtra("Gender", feeds.get(getAdapterPosition()).gender);
            i.putExtra("Amount_of_Blood", feeds.get(getAdapterPosition()).amount_blood);
            i.putExtra("Expected_Date", feeds.get(getAdapterPosition()).date);
            i.putExtra("Expected_Time", feeds.get(getAdapterPosition()).time);
            i.putExtra("Address", feeds.get(getAdapterPosition()).add_hospital);
            i.putExtra("Message", feeds.get(getAdapterPosition()).msg);
            i.putExtra("Phone", feeds.get(getAdapterPosition()).user);
            i.putExtra("Pass_Blood_Group", passBG);
            context.startActivity(i);

        }

        @Override
        public boolean onLongClick(View v) {
           // clickListener.onItemLongClick(getAdapterPosition(), v);
            String sendText = feeds.get(getAdapterPosition()).b_group + " blood is needed.\n" + "\nAmount of blood: " + feeds.get(getAdapterPosition()).amount_blood+" bag(s).\n" +
                    "Date: "+ feeds.get(getAdapterPosition()).date + ", Time: "+feeds.get(getAdapterPosition()).time + ".\n" + "\nPhone number: " + feeds.get(getAdapterPosition()).user + ".\n" +
                    "\nAddress: " + feeds.get(getAdapterPosition()).add_hospital + ".\n" + "\nMessage:\n" + feeds.get(getAdapterPosition()).msg + "\n\nShared from 'BloodLink' App";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sendText);
            context.startActivity(intent.createChooser(intent, "Share to..."));
            return false;
        }
    }

    public interface ClickListener{
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        FeedAdapter.clickListener = clickListener;
    }
}
