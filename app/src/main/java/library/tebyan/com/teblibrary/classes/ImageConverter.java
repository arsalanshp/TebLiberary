package library.tebyan.com.teblibrary.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Created by yahyapour on 11/15/16.
 * https://inducesmile.com/android/how-to-make-circular-imageview-and-rounded-corner-imageview-in-android/
 * https://github.com/hdodenhof/CircleImageView
 */
public class ImageConverter {

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap result) {

        Bitmap imageRounded = Bitmap.createBitmap(result.getWidth(), result.getHeight(), result.getConfig());
//                        Bitmap imageRounded = Bitmap.createBitmap(370, 270, result.getConfig());
//                        Bitmap imageRounded = Bitmap.createScaledBitmap(result,370,250 , false);
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(result, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        canvas.drawCircle(result.getWidth()/4,result.getWidth()/4,result.getWidth()/8,mpaint);
        canvas.drawRoundRect((new RectF(0, 0, result.getWidth(), result.getHeight())), 25, 25, mpaint);// Rou
        return imageRounded;
    }
}
