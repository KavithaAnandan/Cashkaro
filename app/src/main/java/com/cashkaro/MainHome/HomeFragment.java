package com.cashkaro.MainHome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * Created by SelvaGaneshM on 03-09-2017.
 */

public class HomeFragment extends Fragment {

    private CarouselView carouselView;
    RecyclerView recycler_view_home;


    int[] sampleImages = {R.drawable.placeholder, R.drawable.placeholder, R.drawable.placeholder, R.drawable.placeholder, R.drawable.placeholder};
    String[] sampleNetworkImageURLs = {
            "https://asset6.ckassets.com/resources/image/slider_images/ck-storepage-v2/1x4/slide2.1504413370.png",
            "https://asset6.ckassets.com/resources/image/slider_images/ck-storepage-v2/1x4/slide1.1504413070.png",
            "https://asset6.ckassets.com/resources/image/slider_images/ck-storepage-v2/1x4/slide3.1504413370.png",
            "https://asset6.ckassets.com/resources/image/slider_images/ck-storepage-v2/1x4/slide4.1504413370.png",
            "https://asset6.ckassets.com/resources/image/slider_images/ck-storepage-v2/1x4/slide1.1504413070.png"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.home_fragment, container, false);

        carouselView = (CarouselView) parentView.findViewById(R.id.carouselView);
        recycler_view_home = (RecyclerView) parentView.findViewById(R.id._view_home);
        setUpViews(parentView);

        return parentView;
    }

    private void setUpViews(View parentView) {


        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(getActivity(), "Clicked item: " + position, Toast.LENGTH_SHORT).show();
//            }
//        });

        setRecyclerViewData();

    }

    private void setRecyclerViewData() {


        recycler_view_home.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<User> UserList = new ArrayList<>();

        UserList.add(new User(R.drawable.images1,
                "Super Value Days: Earn Upto 6.5% CashKaro Rewards on top of Upto 45% Off", "Amazon",
                "amit@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.images2,
                "Earn Upto 7.5% CashKaro Rewards on all orders at Flipkart", "Flipkart",
                "john@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.images3,
                "Upto 11% CashKaro Cashback on top of 85% Off Tata Cliq Offers", "TATA",
                "rakesh@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.images4,
                "Flat 25% Off Coupon + Rs 250 CashKaro Cashback on orders over Rs 1,000", "JABONG",
                "akash@sonevalley.com",
                "2015-07-02 20:33:12"));
        UserList.add(new User(R.drawable.images5,
                "Open a Savings Account with Zero Balance & Get Flat Rs 1,000 CashKaro", "IDFC",
                "tanu@sonevalley.com",
                "2015-07-02 20:33:12"));


        AllUsersAdapter allUserAdapter = new AllUsersAdapter(getActivity(), UserList);
        recycler_view_home.setAdapter(allUserAdapter);
        recycler_view_home.setItemAnimator(new DefaultItemAnimator());


    }


    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Picasso.with(getActivity()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[3]).fit().centerCrop().into(imageView);

            //imageView.setImageResource(sampleImages[position]);
        }
    };

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_user_view, null);
            UserViewHolder viewHolder = new UserViewHolder(view);
            tinyDB = new TinyDB(getActivity());
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
                    tinyDB.putString("toolbar_values", "Offers of "+holder.tvMobile.getText().toString());
                    startActivity(new Intent(getActivity(), BestoffersActitivty.class));

                }
            });


        }

        private void setAnimation(View itemView, int i) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (i > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
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
}
