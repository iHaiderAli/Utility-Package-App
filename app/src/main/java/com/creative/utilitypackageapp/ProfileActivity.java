package com.creative.utilitypackageapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.creative.utilitypackage.AppPermissons;
import com.creative.utilitypackage.CloseSoftKeyBoardOnTouchOutside;
import com.creative.utilitypackage.Constants;
import com.creative.utilitypackage.DataValidation;
import com.creative.utilitypackage.ImagePickerDialog;
import com.creative.utilitypackage.Message;
import com.creative.utilitypackage.interfaces.ImageSelectedFrom;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileImg;
    private ScrollView profileLayout;
    private Button submitData, addProfileImage;
    private EditText userName, userEmail, userPhoneNo, companyAddress;
    private CloseSoftKeyBoardOnTouchOutside closeSoftKeyBoardOnTouchOutside;

    private Uri outputUri = null;
    private ArrayList<Uri> path = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileLayout = findViewById(R.id.profileLayout);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhoneNo = findViewById(R.id.userPhoneNo);
        companyAddress = findViewById(R.id.companyAddress);
        addProfileImage = findViewById(R.id.addProfileImage);
        profileImg = findViewById(R.id.profileImg);
        submitData = findViewById(R.id.submitData);
        submitData.setOnClickListener(this);
        addProfileImage.setOnClickListener(this);

        closeSoftKeyBoardOnTouchOutside = new CloseSoftKeyBoardOnTouchOutside();
        closeSoftKeyBoardOnTouchOutside.setupUI(profileLayout, this);

    }

    private void dataValidation() {

        int result = DataValidation.isValidInput(userName.getText().toString().trim());
        boolean validEmail = DataValidation.isValidEmail(userEmail.getText().toString().trim());
        boolean validPhoneNumber = DataValidation.isValidPhoneNumber(userPhoneNo.getText().toString().trim());
        boolean validUrl = DataValidation.isValidUrl(companyAddress.getText().toString().trim());

        if (result == 1) {
            userName.setError(getResources().getString(R.string.enterUserName));
        } else if (result == 2) {
            userName.setError(getResources().getString(R.string.validUsername));
        } else if (TextUtils.isEmpty(userEmail.getText().toString().trim())) {
            userEmail.setError(getResources().getString(R.string.enterEmail));
        } else if (!validEmail) {
            userEmail.setError(getResources().getString(R.string.validEmail));
        } else if (!validPhoneNumber) {
            userPhoneNo.setError(getResources().getString(R.string.validPhoneNo));
        } else if (TextUtils.isEmpty(userPhoneNo.getText().toString().trim())) {
            userPhoneNo.setError(getResources().getString(R.string.enterPhoneNo));
        } else if (TextUtils.isEmpty(companyAddress.getText().toString().trim())) {
            companyAddress.setError(getResources().getString(R.string.validUrl));
        } else if (!validUrl) {
            companyAddress.setError(getResources().getString(R.string.enterUrl));
        } else {
            Message.showMessage(this, getResources().getString(R.string.success));
        }
    }

    private void openCameraOrGallery() {

        ImagePickerDialog.dialogToChooseImage(ProfileActivity.this, new ImageSelectedFrom() {
            @Override
            public void selectedImage(Boolean status) {
                if (status) {
                    if (AppPermissons.CHECK_CAMERA(ProfileActivity.this)) {
                        outputUri = ImagePickerDialog.createDirAndLaunchCamera(ProfileActivity.this);
                    } else {
                        AppPermissons.REQUEST_CAMERA(ProfileActivity.this, Constants.ACTION_REQUEST_CAMERA);
                    }
                } else {
                    if (AppPermissons.CHECK_READ_STORAGE(ProfileActivity.this)) {
                        ImagePickerDialog.openGallery(ProfileActivity.this, 4, path, 15);
                    } else {
                        AppPermissons.REQUEST_READ_STORAGE(ProfileActivity.this, Constants.ACTION_REQUEST_GALLERY);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.ACTION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                outputUri = ImagePickerDialog.createDirAndLaunchCamera(ProfileActivity.this);
            } else {
                Message.showMessage(getApplicationContext(), getResources().getString(R.string.app_name));
            }

        } else if (requestCode == Constants.ACTION_REQUEST_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImagePickerDialog.openGallery(ProfileActivity.this, 4, path, 15);
            } else {
                Message.showMessage(getApplicationContext(), getResources().getString(R.string.app_name));
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    path = data.getParcelableArrayListExtra(Constants.INTENT_PATH);

                    if (path != null && path.size() > 0) {
                        outputUri = path.get(0);
                        Glide.with(getApplicationContext()).load(outputUri).into(profileImg);
                    }
                }

                break;

            case Constants.ACTION_REQUEST_CAMERA:

                Glide.with(getApplicationContext()).load(outputUri).into(profileImg);

                break;
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.submitData) {
            dataValidation();
        } else if (view.getId() == R.id.addProfileImage) {
            openCameraOrGallery();
        }

    }


}
