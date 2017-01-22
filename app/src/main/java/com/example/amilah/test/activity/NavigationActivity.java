package com.example.amilah.test.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amilah.test.R;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.fragment.HomeFragment;
import com.example.amilah.test.fragment.RegisterFragment;
import com.example.amilah.test.fragment.StudentResultFragment;
import com.example.amilah.test.models.User;
import com.example.amilah.test.utill.BitmapUtility;
import com.example.amilah.test.utill.Constant;

import java.io.IOException;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String name,pword;
    ImageView profileImg;
    DBHelper dbHelper;
    int id;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hview = navigationView.getHeaderView(0);
        TextView nav_Name = (TextView)hview.findViewById(R.id.nv_headerName);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
        //layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        profileImg = (ImageView)hview.findViewById(R.id.ivProfile);
      //  profileImg.setLayoutParams(layoutParams);
        nav_Name.setGravity(Gravity.CENTER_HORIZONTAL);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
       // Bundle extras = getIntent().getExtras();
//        byte[] byteArray = extras.getByteArray("imageSet");
       // Bitmap bitmap = BitmapUtility.getImage(byteArray);
       // profileImg.setImageBitmap(bitmap);
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_navigation,homeFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //set user name in nav header
        SharedPreferences preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        name = preferences.getString("username2","");
        pword = preferences.getString("password","");
        id = preferences.getInt("id",1);
        String output = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        nav_Name.setText(output);


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileImg();
            }
        });
        Bundle extras = getIntent().getExtras();
        String base64Image = extras.getString("image");
        if(base64Image!=null) {
            Bitmap bmp = BitmapUtility.getImage(base64Image);
            profileImg.setImageBitmap(bmp);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!Constant.KEY_LOGIN) {
                super.onBackPressed();
            }
            else {
                moveTaskToBack(true);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.search:
                break;
            case R.id.add:
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_navigation,registerFragment).addToBackStack("details").commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reg) {

           RegisterFragment registerFragment = new RegisterFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_navigation,registerFragment).commit();
        } else if (id == R.id.nav_list) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_navigation,homeFragment).commit();
        }
        else if (id == R.id.nav_logout){
            SharedPreferences preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Log.i("Editor_when_logout---",editor.toString());
            Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(logout);
        }
        else if (id==R.id.nav_marks)
        {
            StudentResultFragment studentResultFragment = new StudentResultFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_navigation,studentResultFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setProfileImg() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Pictures Option");
        myAlertDialog.setMessage("Select Picture Mode");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        myAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                profileImg.setImageBitmap(imageBitmap);
                String img = BitmapUtility.getString(imageBitmap);
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                User user = new User();
                user.setImg(img);
                user.setUid(id);
                int response = dbHelper.updateUser(user);
                if (response>0) {
                    Toast.makeText(getApplicationContext(),"Update Success!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Update Failed!",Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == 2) {
                Bitmap bitmapFinal = null;
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap thumbnail = decodeSampledBitmapFromResource(filePath, 100, 100);
                Log.i("path--", filePath+"");
                try {
                    bitmapFinal = modifyOrientation(thumbnail,filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImg.setImageBitmap(bitmapFinal);
                String img = BitmapUtility.getString(bitmapFinal);
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                User user = new User();
                user.setImg(img);
                user.setUid(id);
                int response = dbHelper.updateUser(user);
                if (response>0) {
                    Toast.makeText(getApplicationContext(),"Update Success!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Update Failed!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String res,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(res, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
