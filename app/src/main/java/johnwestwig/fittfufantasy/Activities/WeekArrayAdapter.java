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
import johnwestwig.fittfufantasy.Models.Week;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/16/17.
 */

public class WeekArrayAdapter extends ArrayAdapter {
    private static final String TAG = "Week List Adapter";

    private final ArrayList<Week> weeks;

    public WeekArrayAdapter(Context context, int listViewResourceId, ArrayList<Week> weeks) {
        super(context, listViewResourceId, weeks);
        this.weeks = weeks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount(){
        return (weeks == null ? 0 : weeks.size()) + 1;
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.week_spinner_template, parent, false);
        }
        if (position > 0) {
            Week week = weeks.get(position - 1);
            if (week != null){
                ((TextView) convertView.findViewById(R.id.week_spinner_template_week)).
                        setText("Week " + week.getNumber());
            } else {
                Log.v(TAG, "Week is NULL");
            }
        } else {
            ((TextView) convertView.findViewById(R.id.week_spinner_template_week)).
                    setText("All time");
        }

        return convertView;
    }
}
