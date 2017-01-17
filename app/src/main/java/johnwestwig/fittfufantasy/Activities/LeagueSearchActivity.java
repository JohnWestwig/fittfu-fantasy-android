package johnwestwig.fittfufantasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.Models.League;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/15/17.
 */

public class LeagueSearchActivity extends AppCompatActivity {
    private static final String TAG = "League Search Activity";

    private ListView leagueSearchListView;
    private LeagueSearchArrayAdapter leagueSearchArrayAdapter;
    private ArrayList<League> leagues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /* Initialize UI Elements */
        leagueSearchListView = (ListView) findViewById(R.id.league_search_listView);
        leagueSearchArrayAdapter = new LeagueSearchArrayAdapter(this,
                R.id.league_search_listView, leagues);
        leagueSearchListView.setAdapter(leagueSearchArrayAdapter);

        loadLeagues();
    }

    private void loadLeagues() {
        APIRequest request = new APIRequest(getApplicationContext()) {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    JSONArray leagueArray = response.getJSONArray("leagues");
                    for (int i = 0; i < leagueArray.length(); i++) {
                        leagues.add(i, new League(leagueArray.getJSONObject(i)));
                    }
                    leagueSearchArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.v(TAG, e.getMessage());
                }
            }

            @Override
            public void onError(JSONObject error) {

            }
        };
        request.send("/api/leagues?me", Request.Method.GET, null);
    }
}
