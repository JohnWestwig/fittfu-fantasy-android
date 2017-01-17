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
import johnwestwig.fittfufantasy.Models.Standing;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/16/17.
 */

public class StandingArrayAdapter extends ArrayAdapter {
    private static final String TAG = "Standings Array Adapter";

    private final ArrayList<Standing> standings;

    public StandingArrayAdapter(Context context, int listViewResourceId, ArrayList<Standing> standings) {
        super(context, listViewResourceId, standings);
        this.standings = standings;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.standing_template, parent, false);
        }

        Standing standing = standings.get(position);
        if (standing != null) {
            ((TextView) convertView.findViewById(R.id.standing_template_rank_textView)).
                    setText("" + standing.getRank());
            ((TextView) convertView.findViewById(R.id.standing_template_owner_textView)).
                    setText(standing.getOwnerName());
            ((TextView) convertView.findViewById(R.id.standing_template_lineup_textView)).
                    setText(standing.getLineupName());
            ((TextView) convertView.findViewById(R.id.standing_template_points_textView)).
                    setText("" + standing.getNumPoints());

            return convertView;
        } else {
            Log.v(TAG, "Standing is NULL");
        }

        return convertView;
    }
}
