package name.juhasz.judit.udacity.swissnews;

public class Article {
    private String mTitle;
    private String mSectionName;
    private String mUrl;

    public Article(String title, String sectionName, String url) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getUrl() { return  mUrl; }
}
