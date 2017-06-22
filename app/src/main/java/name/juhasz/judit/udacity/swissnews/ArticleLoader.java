package name.juhasz.judit.udacity.swissnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private static final String QUERY_URL =
            "http://content.guardianapis.com/search?q=switzerland%20AND%20swiss&show-fields=thumbnail&api-key=test";


    public ArticleLoader(Context context) { super(context); }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        List<Article> articles = QueryUtils.fetchArticleData(QUERY_URL);
        return articles;
    }
}