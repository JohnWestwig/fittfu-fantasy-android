package johnwestwig.fittfufantasy.Activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import johnwestwig.fittfufantasy.Models.Article;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/15/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter {
    private static final String TAG = "Article List Adapter";

    private final ArrayList<Article> articles;

    public ArticleArrayAdapter(Context context, int listViewResourceId, ArrayList<Article> articles) {
        super(context, listViewResourceId, articles);
        this.articles = articles;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.article_template, parent, false);
        }

        Article article = articles.get(position);
        if (article != null) {
            ((TextView) convertView.findViewById(R.id.article_template_title_textView)).
                    setText(article.getTitle());
            ((TextView) convertView.findViewById(R.id.article_template_author_textView)).
                    setText(article.getAuthor());
            ((TextView) convertView.findViewById(R.id.article_template_date_published_textView)).
                    setText(article.getDatePublished());
            ((TextView) convertView.findViewById(R.id.article_template_content_textView)).
                    setText(article.getContent());

            return convertView;
        } else {
            Log.v(TAG, "Article is NULL");
        }

        return convertView;
    }
}
