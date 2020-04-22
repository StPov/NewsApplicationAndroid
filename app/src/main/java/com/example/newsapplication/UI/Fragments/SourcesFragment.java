package com.example.newsapplication.UI.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.example.newsapplication.Helpers.Adapters.SourcesAdapter;
import com.example.newsapplication.Data.Models.SearchedSource;
import com.example.newsapplication.Data.Models.SearchedSources;
import com.example.newsapplication.Data.Network.ApiClient;
import com.example.newsapplication.Data.Network.ApiInterface;
import com.example.newsapplication.Helpers.SourceRecyclerViewSupport.FeatureLinearLayoutManager;
import com.example.newsapplication.Helpers.SourceRecyclerViewSupport.FeaturedRecyclerView;
import com.example.newsapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.newsapplication.UI.Fragments.NewsFragment.API_KEY;

public class SourcesFragment extends Fragment {

    List<SearchedSource> sources = new ArrayList<>();
    FeaturedRecyclerView featuredRecyclerView;
    FeatureLinearLayoutManager layoutManager;
    SourcesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);

        featuredRecyclerView = view.findViewById(R.id.featured_recycler_view);
        layoutManager = new FeatureLinearLayoutManager(getContext());
        featuredRecyclerView.setLayoutManager(layoutManager);
        featuredRecyclerView.setItemAnimator(new DefaultItemAnimator());
        featuredRecyclerView.setNestedScrollingEnabled(false);

        loadJson();

        return view;
    }

    public void loadJson() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SearchedSources> call;
        call = apiInterface.getSources(API_KEY);
        Log.d(TAG, "loadJson: " + call.request().url());

        call.enqueue(new Callback<SearchedSources>() {
            @Override
            public void onResponse(Call<SearchedSources> call, Response<SearchedSources> response) {
                if (response.isSuccessful() && response.body().getSourceList() != null) {
                    if (!sources.isEmpty()) {
                        sources.clear();
                    }
                    sources = response.body().getSourceList();
                    adapter = new SourcesAdapter(sources, getContext());
                    featuredRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No results", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchedSources> call, Throwable t) {

            }
        });
    }
}
