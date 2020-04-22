package com.example.newsapplication.Helpers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.Data.Models.SearchedSource;
import com.example.newsapplication.Helpers.SourceRecyclerViewSupport.FeatureRecyclerViewAdapter;
import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SourcesAdapter extends FeatureRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_FEATURED = 0;
    private static final int ITEM_TYPE_DUMMY = 1;

    private List<SearchedSource> data;
    private int[] images = new int[10];

    private Context context;

    public SourcesAdapter(List<SearchedSource> data, Context context) {
        this.data = data;
        this.context = context;

        images[0] = R.drawable.news1;
        images[1] = R.drawable.news2;
        images[2] = R.drawable.news3;
        images[3] = R.drawable.news4;
        images[4] = R.drawable.news5;
        images[5] = R.drawable.news6;
        images[6] = R.drawable.news7;
        images[7] = R.drawable.news8;
        images[8] = R.drawable.news9;
        images[9] = R.drawable.news10;

    }

    @Override
    public RecyclerView.ViewHolder onCreateFeaturedViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_FEATURED:
                return new FeaturedViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_featured_item, parent, false));
            case ITEM_TYPE_DUMMY:
            default:
                return new DummyViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_dummy_item, parent, false));
        }
    }

    @Override
    public void onBindFeaturedViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            Picasso.get().load(images[position % 10]).into(featuredViewHolder.ivBackground);
            featuredViewHolder.tvHeading.setText(data.get(position).getName());
            featuredViewHolder.tvInfo.setText(data.get(position).getCategory() + " " + data.get(position).getCountry());
            featuredViewHolder.tvDescription.setText(data.get(position).getDescription());
        } else if (holder instanceof DummyViewHolder) {
            //Do nothing
        }
    }

    @Override
    public int getFeaturedItemsCount() {
        return data.size() + 2; /* Return 2 extra dummy items */
    }

    @Override
    public int getItemViewType(int position) {
        return position >= 0 && position < data.size() ? ITEM_TYPE_FEATURED : ITEM_TYPE_DUMMY;
    }

    @Override
    public void onSmallItemResize(RecyclerView.ViewHolder holder, int position, float offset) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            featuredViewHolder.tvHeading.setAlpha(offset / 100f);
        }
    }

    @Override
    public void onBigItemResize(RecyclerView.ViewHolder holder, int position, float offset) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            featuredViewHolder.tvHeading.setAlpha(offset / 100f);
        }
    }


    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBackground;
        TextView tvHeading, tvInfo, tvDescription;

        FeaturedViewHolder(View itemView) {
            super(itemView);

            ivBackground = itemView.findViewById(R.id.iv_background);
            tvHeading = itemView.findViewById(R.id.tv_heading);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }

    static class DummyViewHolder extends RecyclerView.ViewHolder {

        DummyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
