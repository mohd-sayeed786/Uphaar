package com.sayeed.bloodbank;

import android.Manifest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Date;
import java.util.UUID;

public class Attendance_Activity extends AppCompatActivity {

    private TextView create;
    private TextView gestures;
    private TextView date;
    private TextView left;
    private TextView right;
    private TextView eyes;
    private TextView upload;
    private ImageView pic;
    private ImageView rightpic;
    private ImageView leftpic;
    private ImageView eyespic;
    private TextView submit;
    private  TextView capture;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private StorageReference mStorage;



    private static final long START_TIME_IN_MILLIS=180000;
    private boolean mTimeRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    TextView countdown;
    private CountDownTimer mCountDownTimer;

    int flag=0;
    String mCurrentPhotoPath = "";

    Uri photoURI=null;
    File photoFile = null;
    private String mImageFileLocation = "";

    String newpath;

    private static final int CAMERA_CODE = 1;

    private int REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_);

        create = findViewById(R.id.create);
        gestures = findViewById(R.id.textView2);
        date = findViewById(R.id.textView3);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        eyes = findViewById(R.id.eyes);
        upload = findViewById(R.id.upload);
        pic = findViewById(R.id.imageView);
        rightpic = findViewById(R.id.rightpic);
        leftpic = findViewById(R.id.leftpic);
        eyespic = findViewById(R.id.eyespic);
        submit = findViewById(R.id.submit);
        capture = findViewById(R.id.capture);
        countdown = findViewById(R.id.countdown);

        mStorage = FirebaseStorage.getInstance().getReference();

        /*upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
            }
        });*/


        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CaptureImage();
            }
        });

    }

    public int[] Gestures_RightHand = {
            R.drawable.handgun2,
            R.drawable.muscle1,
            R.drawable.okay,
            R.drawable.promise2,
            R.drawable.thumbsup,
            R.drawable.victory,
            R.drawable.wave1,
    };

    public int[] Gestures_LeftHand = {
            R.drawable.handgun2,
            R.drawable.muscle1,
            R.drawable.okay,
            R.drawable.promise2,
            R.drawable.thumbsup,
            R.drawable.victory,
            R.drawable.wave1,
    };

    public int[] Gestures_eyes = {
            R.drawable.closeeye,
            R.drawable.openeye,
            R.drawable.winkeye,

    };

    public void CreateGesture(View view) {
        Random r = new Random();

        int randomlefthand = r.nextInt(Gestures_LeftHand.length);

        int randomrighthand = r.nextInt(Gestures_RightHand.length);

        int randomeyes = r.nextInt(Gestures_eyes.length);


        gestures.setVisibility(View.INVISIBLE);
        right.setVisibility(View.VISIBLE);
        rightpic.setImageResource(Gestures_RightHand[randomrighthand]);
        left.setVisibility(View.VISIBLE);
        leftpic.setImageResource(Gestures_LeftHand[randomlefthand]);
        eyes.setVisibility(View.VISIBLE);
        eyespic.setImageResource(Gestures_eyes[randomeyes]);



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dates = new Date();

        date.setText(dateFormat.format(dates));

        startTimer();



    }

    private void startTimer(){

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                    if(mTimeRunning == true) {
                        Toast.makeText(getApplicationContext(), "Timer Finished. File not Uploaded", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    mTimeRunning = false;

            }
        }.start();

        mTimeRunning = true;

    }

    private  void updateCountDownText()
    {
        int minutes = (int) (mTimeLeftInMillis/1000)/60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countdown.setText(timeLeftFormatted);

    }




    public void CaptureImage() {


        if ((ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else {


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();

                    //displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                    Log.i("Sayeed",mCurrentPhotoPath);

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                         photoURI = FileProvider.getUriForFile(this,
                                "com.sayeed.bloodbank.captureimage.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_CODE);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),ex.getMessage().toString());
                }


            }else
            {
                displayMessage(getBaseContext(),"Nullll");
            }
        }
        }


    public static String compressImage(String filePath) {


        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();



        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;

        int actualWidth = options.outWidth;


        float maxHeight = 2000.0f;
        float maxWidth = 1500.0f;

        float imgRatio = actualWidth / actualHeight;

        float maxRatio = maxWidth / maxHeight;



        if (actualHeight > maxHeight || actualWidth > maxWidth)
        {

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



        options.inSampleSize = calculateInSampleSize(options, actualWidth,
                actualHeight);


        //      inJustDecodeBounds set to false to load the actual bitmap

        options.inJustDecodeBounds = false;


        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;

        options.inInputShareable = true;

        options.inTempStorage = new byte[16 * 1024];


        try {

            //          load the bitmap from its path

            bmp = BitmapFactory.decodeFile(filePath, options);

        } catch (OutOfMemoryError exception) {

            exception.printStackTrace();

        }
        try {

            scaledBitmap = Bitmap.createBitmap(actualWidth,
                    actualHeight,Bitmap.Config.ARGB_8888);

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


        //      check the rotation of the image and display it properly

        ExifInterface exif;

        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);

            Log.d("EXIF", "Exif: " + orientation);

            Matrix matrix = new Matrix();

            if (orientation == 6) {

                matrix.postRotate(90);

                Log.d("EXIF", "Exif: " + orientation);

            } else if (orientation == 3) {

                matrix.postRotate(180);

                Log.d("EXIF", "Exif: " + orientation);

            } else if (orientation == 8) {

                matrix.postRotate(270);

                Log.d("EXIF", "Exif: " + orientation);

            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

        } catch (IOException e) {

            e.printStackTrace();
        }

        FileOutputStream out = null;

        String filename = getFilename();

        try {
            out = new FileOutputStream(filename);


            //          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);


        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        return filename;

    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Foldername/Images");

        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");

        return uriSting;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height/ (float)
                    reqHeight);

            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

        }       final float totalPixels = width * height;

        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /*public static Bitmap highlightImage(Bitmap src) {

        // create new bitmap, which will be painted and becomes result image

        Bitmap bmOut = Bitmap.createBitmap(src.getWidth() + 96, src.getHeight() + 96, Bitmap.Config.ARGB_8888);

        // setup canvas for painting

        Canvas canvas = new Canvas(bmOut);

        // setup default color

        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        // create a blur paint for capturing alpha

        Paint ptBlur = new Paint();

        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

        int[] offsetXY = new int[2];

        // capture alpha into a bitmap

        Bitmap bmAlpha = src.extractAlpha(ptBlur, offsetXY);

        // create a color paint
        Paint ptAlphaColor = new Paint();

        ptAlphaColor.setColor(0xFFFFFFFF);

        // paint color for captured alpha region (bitmap)

        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);

        // free memory

        bmAlpha.recycle();

        // paint the image source
        canvas.drawBitmap(src, 0, 0, null);

        // return out final image
        return bmOut;
    }
*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

           // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

          //  startActivityForResult(intent, CAMERA_CODE);

           /* File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }



            String authorities = getApplicationContext().getPackageName() + ".fileprovider";

            Uri uri = FileProvider.getUriForFile(this,authorities,photoFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
*/
        /*final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading . . .");
        progressDialog.show();

        StorageReference filepath = mStorage.child("Photos").child(UUID.randomUUID().toString());
        filepath.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(Attendance_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Attendance_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                   }
                });






        }

            /*File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureName = getPictureName();

            File imagefile=new File(pictureDirectory,pictureName);
            Uri uri = Uri.fromFile(imagefile);
*/
           // intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);



  /*  private String getPictureName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Plane Face Image"+timestamp+".jpg";
    }
*/
  /*  File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED)
            {
                CaptureImage();
            }
            else
            {
                Toast.makeText(this,"Please give Permissions",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void goback(View view)
    {

        if(mTimeLeftInMillis > 0 && photoURI != null) {

            Toast.makeText(getApplicationContext(),"File Uploaded Successfully",Toast.LENGTH_LONG).show();
            mTimeRunning = false;


        }
        else
        {
            Toast.makeText(getApplicationContext(),"File not Uploaded.",Toast.LENGTH_LONG).show();
            mTimeRunning = false;

        }
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void UploadImage(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);

            } else {
                pickImageFromGallery();
            }
        }
        else {
        pickImageFromGallery();
        }*/




    /*private void pickImageFromGallery() {
        Intent intent = new Intent( Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length >0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText( this,  "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            pic.setImageURI(data.getData());
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null &&data.getData()!=null)
        {

                Uri uri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);
*/
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading . . .");
                progressDialog.show();

                StorageReference filepath = mStorage.child("Photos").child(UUID.randomUUID().toString());
                filepath.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();

                                Toast.makeText(Attendance_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();

                                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                Picasso.with(Attendance_Activity.this).load(downloadUrl).into(pic);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Attendance_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploading " + (int) progress + "%");
                            }
                        });


        }
        else if(requestCode == CAMERA_CODE && resultCode == RESULT_OK ) {

            //Uri uri = data.getData();

            //Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            // pic.setImageBitmap(bitmap);

            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            /*ByteArrayOutputStream out = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);*/
            //pic.setImageBitmap(myBitmap);

            newpath = compressImage(mCurrentPhotoPath);

            Uri uri = Uri.fromFile(new File(newpath));

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading . . .");
            progressDialog.show();

            StorageReference filepath = mStorage.child("Photos").child(UUID.randomUUID().toString());
            filepath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(Attendance_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Picasso.with(Attendance_Activity.this).load(downloadUrl).into(pic);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Attendance_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploading " + (int) progress + "%");
                        }
                    });
        }
        else
            {
                displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
            }

            /*

             Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                pic.setImageBitmap(bitmap);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading . . .");
            progressDialog.show();

            StorageReference filepath = mStorage.child("Photos").child(UUID.randomUUID().toString());
            filepath.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(Attendance_Activity.this, "Upload Done", Toast.LENGTH_LONG).show();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Picasso.with(Attendance_Activity.this).load(downloadUrl).into(pic);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Attendance_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

                    */


        }


/*
    public class BackgroundImageResize extends AsyncTask<Uri,Integer,byte[]>{

        Bitmap bitmap;

        public BackgroundImageResize(Bitmap bitmap)
        {
            if(bitmap!= null)
            {
                this.bitmap = bitmap;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected byte[] doInBackground(Uri... uris) {

            if(bitmap == null)
            {
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),params[0])
                }
                catch (IOException)
                {

                }
            }


        }


        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
        }
    }



    public static  byte[] getBytesFromBitmap(Bitmap bitmap , int quality)
    {
        ByteArrayInputStream stream = new ByteArrayInputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);
        return stream.toByteArray();
    }*/

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }



}




