package com.cashkaro.MainHome;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashkaro.CommonModules.TinyDB;
import com.cashkaro.R;
import com.cashkaro.SplashScreen.SplashScreenActivity;

import java.util.ArrayList;

/**
 * Created by SelvaGaneshM on 03-09-2017.
 */

public class BestoffersActitivty extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recycler_view_home;
    TinyDB tinyDB;
    ImageView menuLeft;
    TextView menuright;
    private PendingIntent pendingIntent;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_offers);

        recycler_view_home = (RecyclerView) findViewById(R.id.recycler_view_home);
        tinyDB = new TinyDB(this);
        menuLeft = (ImageView) findViewById(R.id.menuLeft);
        menuright = (TextView) findViewById(R.id.menuright);


        //create Intent
        Intent myIntent = new Intent(this, SplashScreenActivity.class);
        //Initialize PendingIntent
        pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
        //Initialize NotificationManager using Context.NOTIFICATION_SERVICE
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        recycler_view_home.setLayoutManager(new LinearLayoutManager(this));

        menuright.setText(tinyDB.getString("toolbar_values"));

        ArrayList<User> UserList = new ArrayList<>();

        UserList.add(new User(R.drawable.image1,
                "cheapest travel deals", "Amazon",
                "amit@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.image2,
                "Recharge deals", "Flipkart",
                "john@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.image3,
                "Health & Beauty deals", "TATA",
                "rakesh@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.image4,
                "Hot mobile deails", "JABONG",
                "akash@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.image5,
                "Food & restaurant deals", "IDFC",
                "tanu@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.image6,
                "fashion & Apparel deals", "IDFC",
                "tanu@sonevalley.com",
                "2015-07-02 20:33:12"));




        AllUsersAdapter allUserAdapter = new AllUsersAdapter(this, UserList);
        recycler_view_home.setAdapter(allUserAdapter);
        recycler_view_home.setItemAnimator(new DefaultItemAnimator());


        menuLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menuLeft:
                finish();
                break;
        }
    }

    private class User {

        int imageResourceId;
        String userName;
        String userMobile;
        String userEmail;
        String userCreatedDate;

        public User(int imageResourceId, String userName, String userMobile, String userEmail, String userCreatedDate) {
            this.imageResourceId = imageResourceId;
            this.userName = userName;
            this.userMobile = userMobile;
            this.userEmail = userEmail;
            this.userCreatedDate = userCreatedDate;
        }

    }


    private class AllUsersAdapter extends RecyclerView.Adapter {

        private Context mContext;
        ArrayList<User> userList;
        private int lastPosition = -1;
        private TinyDB tinyDB;

        public AllUsersAdapter(Context mContext, ArrayList<User> userList) {
            this.mContext = mContext;
            this.userList = userList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_user_view1, null);
            UserViewHolder viewHolder = new UserViewHolder(view);
            tinyDB = new TinyDB(BestoffersActitivty.this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            final UserViewHolder holder = (UserViewHolder) viewHolder;

            // Here you apply the animation when the view is bound
            setAnimation(holder.itemView, i);

            holder.ivProfile.setImageResource(userList.get(i).imageResourceId);
            holder.tvName.setText(userList.get(i).userName);
            holder.tvMobile.setText(userList.get(i).userMobile);
            holder.tvEmail.setText(userList.get(i).userEmail);
            holder.tvCreatedDate.setText(userList.get(i).userCreatedDate);

            holder.open_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tinyDB.putString("toolbar_values", "Offers of " + holder.tvMobile.getText().toString());

                    Log.i("NextActivity", "startNotification");

                    // Sets an ID for the notification
                    int mNotificationId = 001;

                    // Build Notification , setOngoing keeps the notification always in status bar
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(BestoffersActitivty.this)
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setContentTitle("CashKaro")
                                    .setContentText("Congratulations you have clicked on"+holder.tvName.getText().toString())
                                    .setOngoing(true);

                    // Create pending intent, mention the Activity which needs to be
                    //triggered when user clicks on notification(StopScript.class in this case)

                    PendingIntent contentIntent = PendingIntent.getActivity(BestoffersActitivty.this, 0,
                            new Intent(BestoffersActitivty.this, SplashScreenActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


                    mBuilder.setContentIntent(contentIntent);


                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());

                    startActivity(new Intent(BestoffersActitivty.this, WebViewActitivty.class));

                }
            });


        }

        private void setAnimation(View itemView, int i) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (i > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(BestoffersActitivty.this, android.R.anim.slide_in_left);
                itemView.startAnimation(animation);
                lastPosition = i;
            }
        }


        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivProfile;
            private TextView tvName;
            private TextView tvMobile;
            private TextView tvEmail;
            private TextView tvCreatedDate;
            private Button open_btn;

            public UserViewHolder(View itemView) {
                super(itemView);
                ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
                tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
                tvCreatedDate = (TextView) itemView.findViewById(R.id.tvCreatedDate);
                open_btn = (Button) itemView.findViewById(R.id.open_btn);
            }
        }
    }

    private void startNotification(){

    }
}
