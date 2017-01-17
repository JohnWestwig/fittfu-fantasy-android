package johnwestwig.fittfufantasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.Models.Article;
import johnwestwig.fittfufantasy.Models.Lineup;
import johnwestwig.fittfufantasy.Models.Week;
import johnwestwig.fittfufantasy.R;

import static android.R.attr.id;
import static johnwestwig.fittfufantasy.R.color.gamesButton;

/**
 * Created by John on 1/15/17.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Home Activity";

    private FloatingActionButton standingsButton, editLineupButton, gamesButton;

    private ListView articleListView;
    private ArticleArrayAdapter articleArrayAdapter;
    private ArrayList<Article> articles = new ArrayList<>();

    private Week currentWeek;
    private int leagueId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /* Unpack extras */
        leagueId = getIntent().getExtras().getInt("leagueId");
        currentWeek = new Week(new JSONObject());

        /* Setup UI Elements */
        standingsButton = (FloatingActionButton) findViewById(R.id.home_standings_button);
        editLineupButton = (FloatingActionButton) findViewById(R.id.home_lineup_edit_button);
        gamesButton = (FloatingActionButton) findViewById(R.id.home_games_button);

        standingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StandingsActivity.class);
                intent.putExtra("leagueId", leagueId);
                startActivity(intent);
            }
        });

        editLineupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LineupActivity.class);
                intent.putExtra("leagueId", leagueId);
                intent.putExtra("weekId", currentWeek.getId());
                startActivity(intent);
            }
        });


        articleListView = (ListView) findViewById(R.id.home_article_listView);
        articleArrayAdapter = new ArticleArrayAdapter(this, R.id.home_article_listView, articles);
        articleListView.setAdapter(articleArrayAdapter);

        loadCurrentWeek(leagueId);
        loadArticles(leagueId);
    }

    private void loadArticles(int leagueId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                JSONArray articleArray = response.optJSONArray("articles");
                articles.clear();
                for (int i = 0; i < articleArray.length(); i++) {
                    articles.add(i, new Article(articleArray.optJSONObject(i)));
                }
                articleArrayAdapter.notifyDataSetChanged();
            }
        };
        request.send("/api/leagues/" + leagueId + "/articles", Request.Method.GET, null);
    }

    private void loadCurrentWeek(int leagueId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                currentWeek = new Week(response.optJSONObject("week"));
                Log.v(TAG, "WEEK" + currentWeek.getId());
            }
        };
        request.send("/api/leagues/" + leagueId + "/weeks/current", Request.Method.GET, null);
    }
}
