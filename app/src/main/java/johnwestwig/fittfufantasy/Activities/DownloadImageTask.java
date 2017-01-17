package johnwestwig.fittfufantasy.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import static android.R.attr.bitmap;
import static android.R.attr.pointerIcon;
import static android.content.ContentValues.TAG;

/**
 * Created by John on 1/16/17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "Download Image Task";
    private ImageView bmImage;
    private int position;

    public DownloadImageTask(ImageView bmImage, int position) {
        this.position = position;
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        Log.v(TAG, "setting bitmap for image view " + bmImage + " at position " + position);

        return mIcon11;
    }

    protected void onPostExecute(Bitmap bitmap) {
        int borderWidth = 2;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(64, 125, 194));
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);

        bmImage.setImageBitmap(canvasBitmap);
    }
}