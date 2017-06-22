package name.juhasz.judit.udacity.swissnews;

public class Article {
    private String mTitle;
    private String mSectionName;
    private String mUrl;
    private String mCoverImagePath;

    public Article(String title, String sectionName, String url, String coverImagePath) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mUrl = url;
        this.mCoverImagePath = coverImagePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getUrl() { return  mUrl; }

    public String getCoverImagePath() { return  mCoverImagePath; }
}
