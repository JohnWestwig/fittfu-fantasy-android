package johnwestwig.fittfufantasy.API;

import org.json.JSONObject;

/**
 * Created by John on 1/15/17.
 */

public interface Requestable {
    void onCompleted(JSONObject response);
    void onError();
}