package silajit.com.expensetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class SignInActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener /*,LoaderManager.LoaderCallbacks<Cursor>*/ {

    private ImageView profileImage;
    private Button mIncomeButton;
    private Button  mExpenseButton;
    private Button mSavingButton;
    private Button mInvestmentNewsButton;
//    IncomeDatabaseManager incomeDatabase;
//    ExpensesDatabaseManager expenseDatabase;
    RegistrationDatabaseHelper registrationDatabaseHelper;
    SQLiteDatabase incomedb,expensedb;

    String mail;

    Cursor mCursor;
    private static final int IMAGES_LOADER = 0;
    private static final String TAG = "";
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        registrationDatabaseHelper = new RegistrationDatabaseHelper(this);
       // expenseDatabase=new ExpensesDatabaseManager(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // loadImageFromDB();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        profileImage = (ImageView) findViewById(R.id.navigation_header_image);
        mIncomeButton=(Button)findViewById(R.id.income_button);
        mExpenseButton=(Button)findViewById(R.id.expense_button);
        mSavingButton=(Button)findViewById(R.id.report_button);
        mInvestmentNewsButton=(Button)findViewById(R.id.investment_news_button);
        mIncomeButton.setOnClickListener(this);
        mExpenseButton.setOnClickListener(this);
        mSavingButton.setOnClickListener(this);
        mInvestmentNewsButton.setOnClickListener(this);

//        Bundle b=getIntent().getExtras();
//        mail=b.getString("mail");

//        getLoaderManager().initLoader(IMAGES_LOADER, null, this);

    }


//    Boolean loadImageFromDB() {
//        try {
//            registrationDatabaseHelper.open();
//            byte[] bytes = registrationDatabaseHelper.retreiveImageFromDB();
//           registrationDatabaseHelper.close();
//            // Show Image from DB in ImageView
//            profileImage.setImageBitmap(Utils.getImage(bytes));
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//            registrationDatabaseHelper.close();
//            return false;
//        }
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        // Define a projection that specifies the columns from the table we care about.
//        String[] projection = {
//                RegistrationDatabaseHelper.COLUMN_NAME,
//        };
//
//        // This loader will execute the ContentProvider's query method on a background thread
//        return new CursorLoader(this, // Parent activity context
//                ImagesProvider.CONTENT_URI, // Provider content URI to query
//                projection, // Columns to include in the resulting Cursor
//                null, // No selection clause
//                null, // No selection arguments
//                null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        swapCursor(null);
//    }


//    public Cursor swapCursor(Cursor c) {
//        // check if this cursor is the same as the previous cursor (mCursor)
//        if (mCursor == c) {
//            return null; // bc nothing has changed
//        }
//        Cursor temp = mCursor;
//        this.mCursor = c; // new cursor value assigned
//
//        //check if this is a valid cursor, then update the cursor
//
//        return temp;
//    }

//    private void imageRetrieve()
//    {
//        SQLiteDatabase db = registrationDatabaseHelper.getReadableDatabase();
//
//        Cursor imageCursor=db.rawQuery("select * from "+registrationDatabaseHelper.TABLE_USER+" where "
//                +  registrationDatabaseHelper.COLUMN_USER_EMAIL+"='"+mail+"'", null);
//        imageCursor.moveToFirst();
//        byte[] img=imageCursor.getBlob(6);
//        Bitmap b=BitmapFactory.decodeByteArray(img,0,img.length);
//        profileImage.setImageBitmap(b);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent mCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(mCameraIntent, 1);
        } else if (id == R.id.nav_gallery) {

            Intent mGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mGalleryIntent.setType("image/*");
            mGalleryIntent.putExtra("crop", true);
            mGalleryIntent.putExtra("scale", true);
            mGalleryIntent.putExtra("return data", true);
            startActivityForResult(mGalleryIntent, 2);
        }
        else if (id==R.id.slide_show) {
            Intent mSlideIntent=new Intent(SignInActivity.this,DefaultIntro.class);
            startActivity(mSlideIntent);
        } else if (id == R.id.nav_audio_recorder) {
            Intent mAudioIntent=new Intent(SignInActivity.this,AudioRecorderActivity.class);
            startActivity(mAudioIntent);

        }else if(id==R.id.nav_qr_code_scanner){
            Intent mScanner=new Intent(SignInActivity.this,QRCodeScannerActivity.class);
            startActivity(mScanner);

        } else if (id == R.id.nav_share) {
            Intent mShareIntent=new Intent(Intent.ACTION_SEND);
            mShareIntent.setType("text/plain");
            String shareBody="Here is the share content body";
            mShareIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject here");
            mShareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(mShareIntent,"Share Via"));

        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder mLogout = new AlertDialog.Builder(SignInActivity.this);
            mLogout.setMessage("Are you sure you want to Logout your Account?");
            mLogout.setCancelable(true);

            mLogout.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent mLogoutIntent = new Intent(SignInActivity.this,LoginActivity.class);
                            startActivity(mLogoutIntent);
                        }
                    });

            mLogout.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

           mLogout.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.income_button:
                Intent mIncomeActivity = new Intent(SignInActivity.this, IncomeActivity.class);
                startActivity(mIncomeActivity);
                break;

            case R.id.expense_button:
                Intent mExpenseActivity = new Intent(SignInActivity.this, ExpensesActivity.class);
                startActivity(mExpenseActivity);
                break;

            case R.id.report_button:
                Intent mSavingActivity = new Intent(this, SavingsActivity.class);
                String incomeData;
                incomedb=registrationDatabaseHelper.getReadableDatabase();
                Cursor incomeCursor=incomedb.rawQuery("select sum(income) from income", null);
                incomeCursor.moveToFirst();
                incomeData=incomeCursor.getString(0);

                String expenseData;
                expensedb=registrationDatabaseHelper.getReadableDatabase();
                Cursor expenseCursor=expensedb.rawQuery("select sum(expense) from expenses", null);
                expenseCursor.moveToFirst();
                expenseData=expenseCursor.getString(0);

                Bundle b = new Bundle();
                b.putString("amount", String.valueOf(incomeData));
                b.putString("expenses",String.valueOf(expenseData));
                mSavingActivity.putExtras(b);
                startActivity(mSavingActivity);
                break;

            case R.id.investment_news_button:
                Intent mInvestmentNewsActivity = new Intent(SignInActivity.this, InvestmentNewsActivity.class);
                startActivity(mInvestmentNewsActivity);
                break;
        }

        }
}
