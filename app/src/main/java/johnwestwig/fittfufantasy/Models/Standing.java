package johnwestwig.fittfufantasy.Models;

import org.json.JSONObject;

/**
 * Created by John on 1/16/17.
 */

public class Standing {
    private static final String TAG = "Standing";

    private int rank;
    private String ownerName;
    private String lineupName;
    private int numPoints;

    public Standing(JSONObject standing) {
        rank = standing.optInt("rank", 0);
        ownerName = standing.optString("owner_name", "");
        lineupName = standing.optString("lineup_name", "");
        numPoints = standing.optInt("num_points", 0);
    }

    public int getRank() {
        return rank;
    }

    public String getLineupName() {
        return lineupName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getNumPoints() {
        return numPoints;
    }
}
