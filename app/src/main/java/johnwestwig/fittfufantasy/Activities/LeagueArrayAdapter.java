package johnwestwig.fittfufantasy.Activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import johnwestwig.fittfufantasy.Models.League;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/15/17.
 */

public class LeagueArrayAdapter extends ArrayAdapter {
    private static final String TAG = "League List Adapter";

    private final ArrayList<League> leagues;

    public LeagueArrayAdapter(Context context, int listViewResourceId, ArrayList<League> leagues) {
        super(context, listViewResourceId, leagues);
        this.leagues = leagues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.league_template, parent, false);
        }

        League league = leagues.get(position);
        if (league != null) {
            ((TextView) convertView.findViewById(R.id.league_template_name_textView)).
                    setText(league.getName());
            ((TextView) convertView.findViewById(R.id.league_template_details_textView)).
                    setText(league.getDetails());
            return convertView;
        } else {
            Log.v(TAG, "League is NULL");
        }

        return convertView;
    }

}
