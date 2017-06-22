package name.juhasz.judit.udacity.swissnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        return getDummyData();
    }

    // Dummy Data, do not need to use strings.xml
    private List<Article> getDummyData() {

        ArrayList<Article> articles = new ArrayList<>();
        articles.add(new Article("Theresa May rules out participating in TV debates before election",
                "politics"));
        articles.add(new Article("The Snap: the best-worst reasons to miss the TV leaders' debates",
                "politics"));

        return articles;
    }

}