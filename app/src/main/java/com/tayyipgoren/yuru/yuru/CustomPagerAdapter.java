package com.tayyipgoren.yuru.yuru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tayyipgoren.yuru.CampaignDetailActivity;
import com.tayyipgoren.yuru.MainActivity;
import com.tayyipgoren.yuru.R;
import com.tayyipgoren.yuru.yuru.Campaign;

import java.util.ArrayList;
import java.util.Random;

public class CustomPagerAdapter extends PagerAdapter
{
    private Context mContext;

    private final String LOG_TAG = "CUSTOM_PAGE_A";

    public CustomPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

//        Log.v(LOG_TAG, "Position is :" + position);
//

//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
//        collection.addView(layout);

        switch (position)
        {
            case 0:
                // QR
                final View qr = LayoutInflater.from(
                        mContext).inflate(R.layout.view_qr, null, false);
                collection.addView(qr);
                return qr;
            case 1:
                final View top = LayoutInflater.from(
                        mContext).inflate(R.layout.item_top_list, null, false);

                final RecyclerView recyclerView = (RecyclerView) top.findViewById(R.id.top_rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                mContext, LinearLayoutManager.VERTICAL, false
                        )
                );
                recyclerView.setAdapter(new TopRecycleAdapter());

                collection.addView(top);

                return top;
            case 2:
                final View main = LayoutInflater.from(
                        mContext).inflate(R.layout.item_main_list, null, false);

                final RecyclerView mainrecyclerView = (RecyclerView) main.findViewById(R.id.main_rv);
                mainrecyclerView.setHasFixedSize(true);
                mainrecyclerView.setLayoutManager(new LinearLayoutManager(
                                mContext, LinearLayoutManager.VERTICAL, false
                        )
                );
                mainrecyclerView.setAdapter(new MainRecycleAdapter());

                collection.addView(main);

                return main;
        }


        final View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_vp_list, null, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContext, LinearLayoutManager.VERTICAL, false
                )
        );
        recyclerView.setAdapter(new RecycleAdapter());

        collection.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }

    public class TopRecycleAdapter extends RecyclerView.Adapter<TopRecycleAdapter.ViewHolder> {
        private String[] names = {
          "Tayyip", "Kerem", "Melih", "Aleyna",
        };

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_top, parent, false);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            view.startAnimation(fadeInAnimation);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Random rnd = new Random();
            holder.name.setText(String.format("%d. %s", position + 1, names[rnd.nextInt(4)]));
            holder.point.setText(((Integer)rnd.nextInt(10000)).toString());

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public TextView point;
            public ImageView img;


            public ViewHolder(final View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.txt_top_item_list);
                point = itemView.findViewById(R.id.txt_top_item_list_point);
                img = itemView.findViewById(R.id.top_list_image_view);
            }
        }
    }

    public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> {
        public ArrayList<Campaign> campaigns = Campaign.getFakeCampaigns();

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
            Animation fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            view.startAnimation(fadeInAnimation);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Random rnd = new Random();

            holder.img.setImageDrawable(mContext.getResources().getDrawable(campaigns.get(position).getCompany().getImage()));
            holder.name.setText(campaigns.get(position).getName());
            holder.left_text.setText(mContext.getString(R.string.left_amount) + "\n" +  ((Integer)(campaigns.get(position).getCapacity() - campaigns.get(position).getCapacity_taken())).toString());
            holder.point.setText(mContext.getString(R.string.point) + "\n" +  ((Integer)(campaigns.get(position).getPoint())).toString());


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(R.string.coupon_confirm);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            User user = User.getFakeUser();
                            boolean response = user.joinCampaign(campaigns.get(position));

                            if (response == true)
                            {
                                Snackbar.make(view,R.string.coupon_dialog_ok, Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                Snackbar.make(view,R.string.coupon_dialog_not_enough, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.setCancelable(true);
                    builder.create();
                    builder.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return campaigns.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public TextView left_text;
            public TextView point;
            public ImageView img;
            public LinearLayout layout;


            public ViewHolder(final View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.main_campaign_text);
                img = itemView.findViewById(R.id.main_list_image_view);
                left_text = itemView.findViewById(R.id.man_list_left_num_text);
                point = itemView.findViewById(R.id.man_list_point_num_text);
                layout = itemView.findViewById(R.id.main_list_layout);
            }
        }
    }



}
