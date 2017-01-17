package johnwestwig.fittfufantasy.Models;

import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

/**
 * Created by John on 1/16/17.
 */

public class Player {
    private static final String TAG = "Player";

    private int id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String year;
    private boolean owned;
    private Uri imageUri;
    private int price;
    private int score;
    private String teamName;

    public Player (JSONObject player) {
        Log.v(TAG, player.toString());
        id = player.optInt("id", -1);
        firstName = player.optString("first_name", "");
        lastName = player.optString("last_name", "");
        nickname = player.optString("nickname", "");
        year = player.optString("year", "");
        owned = player.optInt("owned", 0) == 1;

        String imageUriString = player.optString("image", null);
        if (imageUriString == null || imageUriString.equals("null")) {
            imageUri = null;
        } else {
            imageUri = Uri.parse(imageUriString);
        }

        price = player.optInt("price", 0);
        score = player.optInt("score", 0);
        teamName = player.optString("team_name", "");
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public boolean isOwned() {
        return owned;
    }

    public int getScore() {
        return score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getYear() {
        return year;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
