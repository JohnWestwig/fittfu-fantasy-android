package johnwestwig.fittfufantasy.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.Models.Lineup;
import johnwestwig.fittfufantasy.Models.Player;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/16/17.
 */

public class LineupActivity extends AppCompatActivity {
    private static final String TAG = "Lineup Activity";

    private int weekId;
    private Lineup lineup;

    private TextView nameTextView;
    private TextView moneyTextView;
    private ProgressBar moneyProgressBar;

    private ListView playersListView;
    private PlayerArrayAdapter playerArrayAdapter;
    private ArrayList<Player> players = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lineup);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        weekId = getIntent().getExtras().getInt("weekId");

        /* Initialize UI Elements */
        nameTextView = (TextView) findViewById(R.id.lineup_name_textView);
        moneyTextView = (TextView) findViewById(R.id.lineup_money_textView);
        moneyProgressBar = (ProgressBar) findViewById(R.id.lineup_money_progressBar);
        playersListView = (ListView) findViewById(R.id.lineup_players_listView);
        playerArrayAdapter = new PlayerArrayAdapter(this, R.layout.player_template, players);
        playersListView.setAdapter(playerArrayAdapter);

        loadLineup(weekId);
    }

    private void loadLineup(int weekId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                lineup = new Lineup(response.optJSONObject("lineup"));
                nameTextView.setText(lineup.getName());
                moneyTextView.setText("$" + lineup.getMoneyRemaining() + " of " + "$" + lineup.getMoneyTotal() + " remaining");
                moneyProgressBar.setMax(lineup.getMoneyTotal());
                moneyProgressBar.setProgress(lineup.getMoneySpent());
                loadPlayers(lineup.getId());
            }
        };

        request.send("/api/weeks/" + weekId + "/lineups/me", Request.Method.GET, null);
    }

    private void loadPlayers(int lineupId) {
        APIRequest request = new APIRequest(this) {
            @Override
            public void onCompleted(JSONObject response) {
                JSONArray playerArray = response.optJSONArray("players");
                players.clear();
                for (int i = 0; i < playerArray.length(); i++) {
                    players.add(new Player(playerArray.optJSONObject(i)));
                }
                playerArrayAdapter.notifyDataSetChanged();
            }
        };

        request.send("/api/lineups/" + lineupId + "/players", Request.Method.GET, null);
    }
}
