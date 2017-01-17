package johnwestwig.fittfufantasy.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import johnwestwig.fittfufantasy.API.APIRequest;
import johnwestwig.fittfufantasy.Models.League;
import johnwestwig.fittfufantasy.R;

import static android.R.attr.duration;

/**
 * Created by John on 1/15/17.
 */

public class LeagueSearchArrayAdapter extends ArrayAdapter {
    private static final String TAG = "League List Adapter";
    private Context context;
    private final ArrayList<League> leagues;

    public LeagueSearchArrayAdapter(Context context, int listViewResourceId, ArrayList<League> leagues) {
        super(context, listViewResourceId, leagues);
        this.leagues = leagues;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.league_search_template, parent, false);
        }

        League league = leagues.get(position);
        if (league != null) {
            ((TextView) convertView.findViewById(R.id.league_search_template_name_textView)).
                    setText(league.getName());
            ((TextView) convertView.findViewById(R.id.league_search_template_details_textView)).
                    setText(league.getDetails());
            convertView.findViewById(R.id.league_search_template_join_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showJoinAlert(leagues.get(position));
                }
            });
            return convertView;
        } else {
            Log.v(TAG, "League is NULL");
        }

        return convertView;
    }

    private void showJoinAlert(final League league) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Joining " + league.getName());

        final EditText input = new EditText(context);
        input.setHint("Lineup Name");
        builder.setView(input);

        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                joinLeague(league.getId(), input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void joinLeague(int leagueId, String lineupName) {
        APIRequest request = new APIRequest(context) {
            @Override
            public void onCompleted(JSONObject response) {
                Toast.makeText(context, "League successfully joined", Toast.LENGTH_LONG).show();
                ((LeagueSearchActivity) context).onBackPressed();
            }

            @Override
            public void onError(JSONObject error) {
                try {
                    int errorCode = error.getInt("errorCode");
                    switch (errorCode) {
                        case 5000:
                            Toast.makeText(context, "ERROR: Already joined", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    Log.v(TAG, e.getMessage());
                }
            }
        };
        Map<String, String> params = new HashMap<>();
        params.put("lineupName", lineupName);
        request.send("/api/leagues/" + leagueId + "/lineups", Request.Method.POST, params);
    }

}
