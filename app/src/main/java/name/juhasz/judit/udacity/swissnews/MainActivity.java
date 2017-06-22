package name.juhasz.judit.udacity.swissnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final int ARTICLES_LOADER_ID = 1;

    private TextView mEmptyStateTextView;
    private ListView mArticleListView;
    private SwipeRefreshLayout mSwipeContainer;

    private ArticleAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.tv_message_display);

        mArticleListView = (ListView) findViewById(R.id.lv_articles);
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        mArticleListView.setAdapter(mArticleAdapter);

        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ARTICLES_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.srl_articles);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(ARTICLES_LOADER_ID, null, MainActivity.this);
            }
        });

        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article currentArticle =mArticleAdapter.getItem(position);
                Uri articleUri = Uri.parse(currentArticle.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        mEmptyStateTextView.setText(R.string.error_no_news);

        mArticleAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mSwipeContainer.setRefreshing(false);
            mArticleAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mArticleAdapter.clear();

        mEmptyStateTextView.setText(R.string.error_no_news);

        mSwipeContainer.setRefreshing(false);
    }
}