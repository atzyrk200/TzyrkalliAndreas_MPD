package atzyrk200.com.rssfeeds;

/**
 * Created by Tzyrkally Andreas S1435553
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RssFeedListAdapter

        extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder>{

    private List<RssFeedModel> mRssFeedModels;
    private List<String> item_rss_feed;
    public Context mcontext;

    public RssFeedListAdapter(List<String> list, Context context) {
        item_rss_feed = list;

    }



    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

//        public TextView titleText;

        public FeedModelViewHolder(View v) {
            super(v);
            rssFeedView = v;


//            titleText = (TextView) v.findViewById(R.id.titleText);

        }
    }






    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {
        mRssFeedModels = rssFeedModels;
    }

    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, final int position) {
        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);
        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText(rssFeedModel.title);
//        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(rssFeedModel.description);

//        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });


//        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        ((TextView)holder.rssFeedView.findViewById(R.id.georssText)).setText(rssFeedModel.georss);

//        ((TextView) holder.rssFeedView.findViewById(R.id.georssText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        ((TextView)holder.rssFeedView.findViewById(R.id.authorText)).setText(rssFeedModel.author);

//        ((TextView)holder.rssFeedView.findViewById(R.id.authorText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        ((TextView)holder.rssFeedView.findViewById(R.id.commentsText)).setText(rssFeedModel.comments);
//        ((TextView)holder.rssFeedView.findViewById(R.id.commentsText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mcontext,  item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//
//
//            }
//        });

        ((TextView)holder.rssFeedView.findViewById(R.id.pubDateText)).setText(rssFeedModel.pubDate);
//        ((TextView)holder.rssFeedView.findViewById(R.id.pubDateText)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mcontext, item_rss_feed.get(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
    }




    //Returns the total number of items in the data set hold by the adapter.



    @Override
    public int getItemCount() {

        return mRssFeedModels.size();
    }

    public List<RssFeedModel> getmRssFeedModels() {
        return mRssFeedModels;
    }

//    final List<String> getdate = new ArrayList<>();
//    {
//
//        for (RssFeedModel item : mRssFeedModels) {
//            item.getPubDate();
//            getdate.add(item.getPubDate());
//        }
//   return
//    }

}




