package johnwestwig.fittfufantasy.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by John on 1/15/17.
 */

public class League {
    private static final String TAG = "League";

    private int id;
    private String name;
    private int numWeeks = 0;
    private int numLineups = 0;

    public League(JSONObject league) {
        id = league.optInt("id", -1);
        name = league.optString("name", "");
        numWeeks = league.optInt("week_count", 0);
        numLineups = league.optInt("lineup_count", 0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumWeeks() {
        return numWeeks;
    }

    public int getNumLineups() {
        return numLineups;
    }

    public String getDetails() {
        return numWeeks + " weeks • " + numLineups + " lineups";
    }
}
