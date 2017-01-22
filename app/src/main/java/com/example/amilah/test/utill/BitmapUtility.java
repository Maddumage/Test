package com.example.amilah.test.utill;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by amilah on 27-Dec-16.
 */

public class BitmapUtility {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(image);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }
    public static String getString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Could be Bitmap.CompressFormat.PNG or Bitmap.CompressFormat.WEBP
        byte[] bai = baos.toByteArray();

        String base64Image = Base64.encodeToString(bai, Base64.DEFAULT);
        return base64Image;
    }
    public static Bitmap getImage(String base64Image)
    {
        byte[] data = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bm;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        bm = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
        return bm;
    }
}
