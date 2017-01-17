package johnwestwig.fittfufantasy.Activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.Models.Standing;
import johnwestwig.fittfufantasy.Models.Week;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/16/17.
 */

public class StandingsActivity extends AppCompatActivity {
    private static final String TAG = "Standings Activity";

    private int leagueId;

    private ListView standingsListView;
    private StandingArrayAdapter standingArrayAdapter;
    private ArrayList<Standing> standings = new ArrayList<>();

    private Spinner weekSpinner;
    private WeekArrayAdapter weekSpinnerAdapter;
    private ArrayList<Week> weeks  = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standings);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        standingsListView = (ListView) findViewById(R.id.standings_listView);
        standingArrayAdapter = new StandingArrayAdapter(this, R.id.standings_listView, standings);
        standingsListView.setAdapter(standingArrayAdapter);

        leagueId = getIntent().getExtras().getInt("leagueId");

        loadWeeks(leagueId);
        loadStandings(leagueId, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.standings_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);

        weekSpinner = (Spinner) MenuItemCompat.getActionView(item);
        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadStandings(leagueId, i == 0 ? -1 : weeks.get(i - 1).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing.
            }
        });

        weekSpinnerAdapter = new WeekArrayAdapter(this, R.layout.week_spinner_template, weeks);
        weekSpinner.setAdapter(weekSpinnerAdapter);

        return true;
    }

    private void loadWeeks(int leagueId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                JSONArray weekArray = response.optJSONArray("weeks");
                weeks.clear();
                for (int i = 0; i < weekArray.length(); i++) {
                    weeks.add(i, new Week(weekArray.optJSONObject(i)));
                }
                weekSpinnerAdapter.notifyDataSetChanged();
            }
        };

        request.send("/api/leagues/" + leagueId + "/weeks", Request.Method.GET, null);
    }

    private void loadStandings(int leagueId, int weekId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                JSONArray standingsArray = response.optJSONArray("standings");
                standings.clear();
                for (int i = 0; i < standingsArray.length(); i++) {
                    standings.add(new Standing(standingsArray.optJSONObject(i)));
                }
                standingArrayAdapter.notifyDataSetChanged();
            }
        };

        if (weekId == -1) {
            request.send("/api/leagues/" + leagueId + "/standings", Request.Method.GET, null);
        } else {
            request.send("/api/leagues/" + leagueId + "/standings?week_id=" + weekId,
                    Request.Method.GET, null);
        }
    }
}
