package com.example.newsapplication.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsapplication.Data.Models.Article;
import com.example.newsapplication.Helpers.Utils;
import com.example.newsapplication.R;
import com.example.newsapplication.UI.MainActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NewsDetailFragment extends Fragment implements FragmentManager.OnBackStackChangedListener, AppBarLayout.OnOffsetChangedListener {

    public Article article;

    private ImageView imageView;
    private TextView appbarTitle, appbarSubtitle, date, time, title;
    private Boolean isHidenToolbar = false;
    private FrameLayout date_behavior;
    private LinearLayout titleAppbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String mUrl, mImg, mTitle, mDate, mSource, mAuthor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_NoActionBar);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View view = localInflater.inflate(R.layout.fragment_news_detail, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        date_behavior = view.findViewById(R.id.date_behavior);
        titleAppbar = view.findViewById(R.id.title_appbar);
        imageView = view.findViewById(R.id.backdrop);
        appBarLayout = view.findViewById(R.id.title_on_appbar);
        appbarSubtitle = view.findViewById(R.id.subtitle_on_appbar);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);

        mUrl = article.getUrl();
        mTitle = article.getTitle();
        mSource = article.getSource().getName();
        mAuthor = article.getAuthor();
        mDate = article.getPublishedAt();
        mImg = article.getUrlToImage();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());

        Glide.with(this).load(mImg).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

        appbarTitle.setText(mSource);
        appbarSubtitle.setText(mUrl);
        date.setText(Utils.DateFormat(mDate));
        title.setText(mTitle);

        String author = null;
        if (mAuthor != null || mAuthor != "") {
            author = " \u2022" + mAuthor;
        } else {
            author = "";
        }

        time.setText(mSource + author + " \u2022" + Utils.DateToTimeFormat(mDate));

        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mUrl);

        return view;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHidenToolbar) {
            date_behavior.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            isHidenToolbar = !isHidenToolbar;

        } else if (percentage < 1f && !isHidenToolbar) {
            date_behavior.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            isHidenToolbar = !isHidenToolbar;
        }
    }

    @Override
    public void onBackStackChanged() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

}
