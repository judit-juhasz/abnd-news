package name.juhasz.judit.udacity.swissnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String JSON_KEY_ARTICLES = "response";
    private static final String JSON_KEY_ARTICLE_RESULTS = "results";
    private static final String JSON_KEY_ARTICLE_TITLE = "webTitle";
    private static final String JSON_KEY_ARTICLE_SECTION_NAME = "sectionName";
    private static final String JSON_KEY_ARTICLE_WEBURL = "webUrl";
    private static final String JSON_KEY_ARTICLE_FIELDS = "fields";
    private static final String JSON_KEY_ARTICLE_THUMBNAIL_PATH = "thumbnail";

    private QueryUtils() {
    }

    public static List<Article> fetchArticleData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Article> articles = extractFeatureFromJson(jsonResponse);

        return articles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Article> extractFeatureFromJson(String articleJSON) {
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(articleJSON);
            JSONObject jsonResponse = baseJsonResponse.getJSONObject(JSON_KEY_ARTICLES);
            JSONArray articleArray = jsonResponse.getJSONArray(JSON_KEY_ARTICLE_RESULTS);

            for (int i = 0; i < articleArray.length(); i++) {
                JSONObject currentArticle = articleArray.getJSONObject(i);
                String title = currentArticle.getString(JSON_KEY_ARTICLE_TITLE);
                String sectionName = currentArticle.getString(JSON_KEY_ARTICLE_SECTION_NAME);
                String webUrl = currentArticle.getString(JSON_KEY_ARTICLE_WEBURL);

                String coverImagePath = null;
                JSONObject articleImages = currentArticle.optJSONObject(JSON_KEY_ARTICLE_FIELDS);
                if (null != articleImages) {
                    coverImagePath = articleImages.optString(JSON_KEY_ARTICLE_THUMBNAIL_PATH);
                }

                Article article = new Article(title, sectionName, webUrl, coverImagePath);
                articles.add(article);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the article JSON results", e);
        }

        return articles;
    }
}