package com.creative.utilitypackage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import com.creative.utilitypackage.interfaces.ImageSelectedFrom;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class ImagePickerDialog {

    public static void dialogToChooseImage(Context context,final ImageSelectedFrom selectedFrom) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");
        builder.setItems(new CharSequence[] {"Take Photo", "Pick From Gallery"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectedFrom.selectedImage(true);
                                break;
                            case 1:
                                selectedFrom.selectedImage(false);
                                break;
                        }
                    }
                });

        builder.show();
    }

    private static File createDir(Activity activity) {

        String directoryName = activity.getResources().getString(R.string.app_name);

        File cameraFolder;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cameraFolder = new File(Environment.getExternalStorageDirectory(), directoryName);
        else
            cameraFolder= activity.getCacheDir();
        if(!cameraFolder.exists())
            cameraFolder.mkdirs();

        //To enable file system forcefully at Nougat
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        String timeStamp = dateFormat.format(new Date());
        String imageFileName = "picture_" + timeStamp + ".jpg";

        return new File(Environment.getExternalStorageDirectory(), directoryName + imageFileName);
    }

    public static Uri createDirAndLaunchCamera(Activity activity) {

        Uri outputUri = null;

        File file = ImagePickerDialog.createDir(activity);
        outputUri = ImagePickerDialog.launchCamera(activity, file);
        return outputUri;
    }

    private static Uri launchCamera(Activity activity, File file) {

        Intent getCameraImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        activity.startActivityForResult(getCameraImage, Constants.ACTION_REQUEST_CAMERA);

        return Uri.fromFile(file);
    }

    public static String getFileName(Context context, Uri uri) {

        File file = null;

        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                file = new File(picturePath);
                cursor.close();
            }

            return file != null ? file.getName() : "";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void openGallery(Context context, int spanCount, ArrayList<Uri> path, int maxSelection) {

        FishBun.with((Activity) context)
                .setImageAdapter(new GlideAdapter())
                .setPickerSpanCount(spanCount)
                .setMaxCount(maxSelection)
                .setSelectedImages(path)
                .setActionBarColor(context.getResources().getColor(R.color.colorPrimary),context.getResources().getColor(R.color.colorPrimary), true)
                .setActionBarTitleColor(context.getResources().getColor(R.color.colorWhite))
                .setAlbumSpanCount(1, 2)
                .setButtonInAlbumActivity(true)
                .setCamera(false)
                .exceptGif(true)
                .setReachLimitAutomaticClose(false)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_black_24dp))
                .setOkButtonDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check_black_24dp))
                .setAllViewTitle(context.getResources().getString(R.string.allOfYourPhotos))
                .setActionBarTitle(context.getResources().getString(R.string.app_name))
                .textOnImagesSelectionLimitReached(context.getResources().getString(R.string.youCantSelectAnyMore))
                .textOnNothingSelected(context.getResources().getString(R.string.needAPhoto))
                .startAlbum();
    }

}
