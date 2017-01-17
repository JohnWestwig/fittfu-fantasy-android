package johnwestwig.fittfufantasy.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import johnwestwig.fittfufantasy.Models.Player;
import johnwestwig.fittfufantasy.R;

/**
 * Created by John on 1/16/17.
 */

public class PlayerArrayAdapter extends ArrayAdapter {
    private static final String TAG = "Player List Adapter";
    private final ArrayList<Player> players;

    public PlayerArrayAdapter(Context context, int listViewResourceId, ArrayList<Player> players) {
        super(context, listViewResourceId, players);
        this.players = players;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.player_template, parent, false);
        }

        Player player = players.get(position);
        if (player != null) {
            final ImageView playerImage = (ImageView) convertView.findViewById(R.id.player_template_image);
            playerImage.setImageBitmap(null);
            if (player.getImageUri() != null) {
                Log.v(TAG, "Position " + position);
                DownloadImageTask task = new DownloadImageTask(playerImage, position) {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                    }
                };
                task.execute(player.getImageUri().toString());
            } else {
                Log.v(TAG, "Setting default image for position " + position);
                playerImage.setImageResource(R.drawable.ic_person_black_24dp);
            }
            Log.v(TAG, "Image view address: " + playerImage);
            ((TextView) convertView.findViewById(R.id.player_template_owned)).
                    setText(player.isOwned() ? "\u2713" : "");
            ((TextView) convertView.findViewById(R.id.player_template_price)).
                    setText("$" + player.getPrice());
            ((TextView) convertView.findViewById(R.id.player_template_name)).
                    setText(player.getFirstName() + " " + player.getLastName());
            ((TextView) convertView.findViewById(R.id.player_template_details)).
                    setText(player.getNickname() + " • " + player.getYear());

            return convertView;
        } else {
            Log.v(TAG, "Player is NULL");
        }
        return convertView;
    }
}
