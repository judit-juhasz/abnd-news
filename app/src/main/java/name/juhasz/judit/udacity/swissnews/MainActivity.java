package name.juhasz.judit.udacity.swissnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final int ARTICLES_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSearch(View view) {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(ARTICLES_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        TextView searchResultTextView = (TextView) findViewById(R.id.tv_search_result);

        String articles = "";
        for (Article article : data) {
            articles = articles + article.getTitle() + "\n";
        }

        searchResultTextView.setText(articles);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }
}
