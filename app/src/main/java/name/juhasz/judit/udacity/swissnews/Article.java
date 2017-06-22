package name.juhasz.judit.udacity.swissnews;

public class Article {
    private String mTitle;
    private String mSectionName;

    public Article(String title, String sectionName) {
        this.mTitle = title;
        this.mSectionName = sectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }
}
