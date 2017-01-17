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
import android.widget.TextView;
import android.widget.Toast;

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

public class LeagueActivity extends AppCompatActivity {
    private static final String TAG = "League Activity";

    private ListView leagueListView;
    private LeagueArrayAdapter leagueArrayAdapter;
    private ArrayList<League> leagues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        /* Initialize UI Elements */
        leagueListView = (ListView) findViewById(R.id.league_listView);
        leagueArrayAdapter = new LeagueArrayAdapter(this, R.id.league_listView, leagues);
        leagueListView.setAdapter(leagueArrayAdapter);
        leagueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(LeagueActivity.this, HomeActivity.class);
                intent.putExtra("leagueId", leagues.get(position).getId());
                startActivity(intent);
            }
        });

        loadLeagues();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLeagues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.league_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_league:
                Intent intent = new Intent(this, LeagueSearchActivity.class);
                startActivity(intent);
                break;
            default:
                return false;
        }
        return true;
    }

    private void loadLeagues() {
        APIRequest request = new APIRequest(getApplicationContext()) {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    JSONArray leagueArray = response.getJSONArray("leagues");
                    leagues.clear();
                    for (int i = 0; i < leagueArray.length(); i++) {
                        leagues.add(i, new League(leagueArray.getJSONObject(i)));
                    }
                    leagueArrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.v(TAG, e.getMessage());
                }
            }

            @Override
            public void onError(JSONObject error) {

            }
        };
        request.send("/api/leagues", Request.Method.GET, null);
    }
}
