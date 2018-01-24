package silajit.com.expensetracker;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    private ScrollView scrollView;
    final int PICK_IMAGE_REQUEST=1;
    private EditText mDateOfBirth;
    private int mDay;
    private Calendar mCalendar;
    private int mMonth;
    private int mYear;
    private DatePickerDialog mDatePickerDialog ;
    private ImageView mRegistrationProfileImage;
    private static final int REQUEST = 112;
    private static final int SELECT_PHOTO = 2;
    private static final int CAPTURE_PHOTO = 1;

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mMobileNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private RadioGroup mGender;
    private RadioButton mMale;
    private RadioButton mFemale;
    private Button mSaveButton;
    private TextView appCompatTextViewLoginLink;


    private static final String TAG = "RegistrationActivity";



    private InputValidation inputValidation;
    private RegistrationDatabaseHelper registrationDatabaseHelper;
    private User user;
    Bitmap thumbnail;
    private final AppCompatActivity activity=RegistrationActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        initListeners();
        initObjects();


//        Drawable drawable=mProfileImage.getDrawable();
//        Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] b = baos.toByteArray();
//        Intent intent=new Intent(RegistrationActivity.this,SignInActivity.class);
//        intent.putExtra("picture", b);
//        startActivity(intent);
    }


    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.register_scroll_view);
        mRegistrationProfileImage = (ImageView) findViewById(R.id.profile_image);
        mFirstName = (EditText) findViewById(R.id.first_name_edittext);
        mLastName = (EditText) findViewById(R.id.last_name_edittext);
        mEmail = (EditText) findViewById(R.id.email_id_edittext);
        mMobileNumber = (EditText) findViewById(R.id.mobile_number_edittext);
        mPassword = (EditText) findViewById(R.id.password_edittext);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password_edittext);
        mGender = (RadioGroup) findViewById(R.id.gender_radio_group);
        mDateOfBirth = (EditText) findViewById(R.id.date_of_birth_edittext);
        mSaveButton = (Button) findViewById(R.id.save_button);
        appCompatTextViewLoginLink = (TextView) findViewById(R.id.appCompatTextViewLoginLink);

    }


    private void initListeners() {
        appCompatTextViewLoginLink.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mRegistrationProfileImage.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            mRegistrationProfileImage.setEnabled(false);
            ActivityCompat.requestPermissions(RegistrationActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            mRegistrationProfileImage.setEnabled(true);
        }

        registrationDatabaseHelper = new RegistrationDatabaseHelper(this);


        mDateOfBirth.setOnTouchListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };
            if (!Utils.hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST);
            }
        }

    }


    @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override
        public boolean onTouch (View view, MotionEvent motionEvent){
            final int DRAWABLE_RIGHT = 2;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (mDateOfBirth.getRight() - mDateOfBirth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    Calendar calendar = Calendar.getInstance();
                     mYear = calendar.get(Calendar.YEAR);
                     mMonth = calendar.get(Calendar.MONTH);
                     mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    mDatePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker mDatePicker, int year, int month, int day) {
                            mDateOfBirth.setText(day + "/" + (month + 1) + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
                    mDatePickerDialog.show();
                }
            }
            return false;
        }

     private void initObjects() {
        inputValidation = new InputValidation(activity);
        registrationDatabaseHelper = new RegistrationDatabaseHelper(activity);
        user = new User();

    }

      @Override
      public void onClick(View v) {
          switch (v.getId()) {
              case R.id.profile_image:
                   selectImage();
                  break;

              case R.id.save_button:
                  postDataToSQLite();
                  break;

              case R.id.appCompatTextViewLoginLink:
                  finish();
                  break;
          }

      }

    private void selectImage() {

        String[] imageOptions = getResources().getStringArray(R.array.image_options);
        AlertDialog.Builder mOptionBuilder = new AlertDialog.Builder(RegistrationActivity.this);
        mOptionBuilder.setTitle(R.string.add_photo_text);
        mOptionBuilder.setItems(imageOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        if (checkRuntimePermissions()) {
                            Intent mCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(mCameraIntent, CAPTURE_PHOTO);
                        }
                        break;
                    case 1:
                        openGallery();
                        break;
                    default:
                        mRegistrationProfileImage.setImageResource(R.drawable.ic_navigation_header_icon_image);
                        break;
                }
            }
        });
        mOptionBuilder.show();
    }

    private void openGallery() {
        Intent mGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mGalleryIntent.setType("image/*");
        mGalleryIntent.putExtra("crop", true);
        mGalleryIntent.putExtra("scale", true);
        mGalleryIntent.putExtra("return data", true);
        startActivityForResult(mGalleryIntent, 2);
    }

//    void showMessage(String message) {
//       Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();
//
//    }


    private boolean checkRuntimePermissions() {
        if (ActivityCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        AlertDialog.Builder mCameraPermissionAlertDialog = new AlertDialog.Builder(RegistrationActivity.this);
        mCameraPermissionAlertDialog.setTitle(R.string.permission_enable_error_text);
        mCameraPermissionAlertDialog.setMessage(Html.fromHtml(getString(R.string.camera_message_text)))
                .setCancelable(false)
                .setPositiveButton(R.string.open_settings_text ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent mIntentSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                mIntentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                mIntentSettings.setData(uri);
                                startActivity(mIntentSettings);
                            }
                        })
        .setNegativeButton(R.string.text_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog mCameraOpenAlertDialog = mCameraPermissionAlertDialog.create();
        mCameraOpenAlertDialog.show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap mCameraPicBitmap = (Bitmap) data.getExtras().get("data");
                mRegistrationProfileImage.setImageBitmap(mCameraPicBitmap);
            } else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Log.d("Gallary", String.valueOf(bitmap));

                    ImageView mRegistrationProfileImage = (ImageView) findViewById(R.id.profile_image);
                    mRegistrationProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == CAPTURE_PHOTO) {

                if (resultCode == RESULT_OK) {
                    onCaptureImageResult(data);
                }
            }
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                Uri selectedImageUri = data.getData();
//
//                Bitmap mCameraPicBitmap = (Bitmap) data.getExtras().get("data");
//                mRegistrationProfileImage.setImageBitmap(mCameraPicBitmap);
//
//
//                if (null != selectedImageUri) {
//
//                    // Saving to Database...
//                    if (saveImageInDB(selectedImageUri)) {
//                        showMessage("Image Saved in Database...");
//                       mRegistrationProfileImage.setImageURI(selectedImageUri);
//
//                        if (loadImageFromDB()) {
//                                showMessage("Image Loaded from Database...");
//                            }
//                        }
//
//                }
//
//            }
//
//    } else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//                Uri uri = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    Log.d("Gallary", String.valueOf(bitmap));
//
//                    ImageView mRegistrationProfileImage = (ImageView) findViewById(R.id.profile_image);
//                    mRegistrationProfileImage.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (requestCode == CAPTURE_PHOTO) {
//
//                if (resultCode == RESULT_OK) {
//                    onCaptureImageResult(data);
//                }
//            }
//        }

    private void onCaptureImageResult(Intent data) {

        thumbnail = (Bitmap) data.getExtras().get("data");
        mRegistrationProfileImage.setMaxWidth(200);
        mRegistrationProfileImage.setImageBitmap(thumbnail);

    }


    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(mFirstName, getString(R.string.first_name_error_message))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mLastName, getString(R.string.last_name_error_message))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(mEmail, getString(R.string.error_message_email))) {
            return;
        }
       // if (!inputValidation.isInputEditTextFilled(mMobileNumber, getString(R.string.mobile_number_error_text))) {
         //   return;
       // }
        if (!inputValidation.isInputEditTextFilled(mPassword, getString(R.string.enter_password_text))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(mPassword, mConfirmPassword,
                getString(R.string.password_match))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mDateOfBirth,  getString(R.string.error_message_date_of_birth))) {
            return;
        }
        if (!inputValidation.isRadioButtonChecked(mGender,getApplicationContext())){
            return;
        }


        if (!registrationDatabaseHelper.checkUser(mEmail.getText().toString().trim(),
                mEmail.getText().toString().trim())) {

            user.setFirstname(mFirstName.getText().toString().trim());

            user.setLastname(mLastName.getText().toString().trim());
            user.setEmail(mEmail.getText().toString().trim());
          // user.setMobileNumber(mMobileNumber.getText().toString().trim());
            user.setPassword(mPassword.getText().toString().trim());
            user.setDateofbirth(mDateOfBirth.getText().toString().trim());

          // user.setGender(mGender.getText().toString().trim());

            mRegistrationProfileImage.setDrawingCacheEnabled(true);
            mRegistrationProfileImage.buildDrawingCache();
            Bitmap bitmap =mRegistrationProfileImage.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            user.setImage(data);

            registrationDatabaseHelper.addUser(user);

            mRegistrationProfileImage.setImageResource(R.drawable.ic_navigation_header_icon_image);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.email_already_exist_text), Snackbar.LENGTH_LONG).show();
        }


    }



//    Boolean saveImageInDB(Uri selectedImageUri) {
//
//        try {
//            registrationDatabaseHelper.open();
//            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
//            byte[] inputData = Utils.getBytes(iStream);
//            registrationDatabaseHelper.insertImage(inputData);
//            registrationDatabaseHelper.close();
//            return true;
//        } catch (IOException ioe) {
//            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
//           registrationDatabaseHelper.close();
//            return false;
//        }
//
//    }
//
//    Boolean loadImageFromDB() {
//        try {
//           registrationDatabaseHelper.open();
//            byte[] bytes = registrationDatabaseHelper.retreiveImageFromDB();
//            registrationDatabaseHelper.close();
//            // Show Image from DB in ImageView
//            mRegistrationProfileImage.setImageBitmap(Utils.getImage(bytes));
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//           registrationDatabaseHelper.close();
//            return false;
//        }
//    }
//


    private void emptyInputEditText() {
       mFirstName.setText(null);
        mLastName.setText(null);
        mEmail.setText(null);
        mMobileNumber.setText(null);
        mPassword.setText(null);
        mConfirmPassword.setText(null);
        mDateOfBirth.setText(null);
        mRegistrationProfileImage.setImageBitmap(null);
       // mGender.setText(null);


    }

}





