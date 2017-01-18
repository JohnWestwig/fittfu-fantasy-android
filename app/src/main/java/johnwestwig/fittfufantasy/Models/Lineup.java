package johnwestwig.fittfufantasy.Models;

import org.json.JSONObject;

/**
 * Created by John on 1/16/17.
 */

public class Lineup {
    private static final String TAG = "Lineup";

    private int id;
    private String name;
    private int moneyTotal;
    private int moneySpent;

    public Lineup(JSONObject lineup) {
        id = lineup.optInt("id", -1);
        name = lineup.optString("name", "");
        moneySpent = lineup.optInt("money_spent", 0);
        moneyTotal = lineup.optInt("money_total", 0);
    }

    public int getId() {
        return id;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getMoneyTotal() {
        return moneyTotal;
    }

    public int getMoneyRemaining() {
        return moneyTotal - moneySpent;
    }

    public String getName() {
        return name;
    }
}
