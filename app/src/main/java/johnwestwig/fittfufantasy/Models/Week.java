package johnwestwig.fittfufantasy.Models;

import org.json.JSONObject;

/**
 * Created by John on 1/16/17.
 */

public class Week {
    private static final String TAG = "Week";

    private int id;
    private int number;

/*    var editStart: Date = Date.distantFuture
    var editEnd: Date = Date.distantFuture
    var liveStart: Date = Date.distantFuture
    var liveEnd: Date = Date.distantFuture*/

    public Week (JSONObject week) {
        id = week.optInt("id", -1);
        number = week.optInt("number", 0);
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }
}
