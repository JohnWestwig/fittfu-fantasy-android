package johnwestwig.fittfufantasy.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 1/15/17.
 */

public class StoredSettings {
    private static final String PREFS_NAME = "FITTFU_FANTASY";

    private static final String TOKEN_PREF = "TOKEN";
    private static final String TOKEN_DEFAULT = "FAKE_TOKEN";

    private final SharedPreferences prefs;

    public StoredSettings(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_PREF, token);
        editor.apply();
    }

    public String getToken() {
        return prefs.getString(TOKEN_PREF, TOKEN_DEFAULT);
    }
}
