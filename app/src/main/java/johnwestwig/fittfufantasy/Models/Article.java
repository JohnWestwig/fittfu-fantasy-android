package johnwestwig.fittfufantasy.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by John on 1/15/17.
 */

public class Article {
    private static final String TAG = "Article";

    private int id;
    private String title;
    private String author;
    private String datePublished;
    private String content;

    public Article(JSONObject article) {
        id = article.optInt("id", -1);
        title = article.optString("title", "");
        author = article.optString("author", "");
        datePublished = article.optString("date_published_pretty", "");
        content = article.optString("content", "");
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDatePublished() {
        return datePublished;
    }
}
