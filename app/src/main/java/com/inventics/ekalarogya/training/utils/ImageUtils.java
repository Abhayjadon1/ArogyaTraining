package com.inventics.ekalarogya.training.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sonu on 14:41, 19/04/18
 * Copyright (c) 2018 . All rights reserved.
 */
public class ImageUtils {


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String resizeBase64Image(String base64image){

        int IMG_WIDTH=300,IMG_HEIGHT=300;

        byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);

        if(image.getHeight() <= 300 && image.getWidth() <= 300){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, IMG_WIDTH, IMG_HEIGHT, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public static void setDrawableImage(Context context, ImageView imageView, int res) {
        Glide.with(context).load(res)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void changeImageColor(ImageView imageView, Context context, int color) {
        imageView.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
    }

    public static Drawable changeShapeColor(Context context, int drawable, int color) {
        Drawable mDrawable = ContextCompat.getDrawable(context, drawable);
        if (mDrawable != null) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_IN));
        }
        return mDrawable;
    }

    public static void loadImageUrl(Context context, ImageView imageView, int placeHolder, String imageLink) {
        if (context == null || TextUtils.isEmpty(imageLink) || imageView == null)
            return;
        String imageNewLink = "";
        if (!imageLink.contains("http")) {
            imageNewLink = ExtraUtils.baseImg + imageLink;
        } else imageNewLink = imageLink;

        if (placeHolder == 0) {
            Glide.with(context).load(imageNewLink)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.signature(new ObjectKey(System.currentTimeMillis()))
                    .into(imageView);
        } else {
            Glide.with(context).load(imageNewLink)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.signature(new ObjectKey(System.currentTimeMillis()))
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(imageView);
        }

    }
    public static Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    public static Bitmap convertImagePathToBitmap(String imagePath, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, new BitmapFactory.Options());
        if (width == 0 || height == 0) {
            return Bitmap.createBitmap(bitmap);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
    public static String getBase64Image(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm
       // is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage,
                Base64.DEFAULT);
        return encodedImage;
    }
    public static String convertBitmapIntoBase64(Bitmap bitmap) {
        if (bitmap == null)
            return "";
        return Base64.encodeToString(getBytesFromBitmap(bitmap),Base64.NO_WRAP);
    }

    private static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, stream);
        }else{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        return stream.toByteArray();

//        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
//        bitmap.copyPixelsToBuffer(buffer);
//        byte[] data = buffer.array();
//        return data;
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            }
            if (cursor != null) {
                cursor.moveToFirst();
            }
            String string = null;
            if (cursor != null) {
                string = cursor.getString(column_index);
            }
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void changeShapeColor(View view, int color) {
        GradientDrawable bgShape = (GradientDrawable) view.getBackground();
        bgShape.setColor(color);
    }

    public static void loadImageURI(Context context, ImageView imageView, Uri imageUri) {
        Glide.with(context).load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(imageView);
    }
//    public static void loadImageURI(Context context, Uri imageURI, ImageView imageView) {
//        Glide.with(context).load(imageURI)
//                .dontAnimate()
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_launcher)
//                .into(imageView);
//    }

    public static void changeStrokeColor(View view, int color, int strokeWidth) {
        GradientDrawable myGrad = (GradientDrawable) view.getBackground();
        myGrad.setStroke(strokeWidth, color);
    }
    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    public static ImageUtils mInstant;

    public static ImageUtils getInstant(){
        if(mInstant==null){
            mInstant = new ImageUtils();
        }
        return mInstant;
    }
    public  Bitmap getCompressedBitmap(String imagePath) {
        float maxHeight = 1920.0f;
        float maxWidth = 1080.0f;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);

        byte[] byteArray = out.toByteArray();

        Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return updatedBitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static Bitmap decodeBitmap(Context context, Uri uri, int maxWidth, int maxHeight) throws IOException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(imageStream, null, options);
        if (imageStream != null)
            imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = getInstant().calculateInSampleSize(options, maxWidth, maxHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(uri);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

//        img = rotateImageIfRequired(context, img, uri);
        return img;
    }

}
