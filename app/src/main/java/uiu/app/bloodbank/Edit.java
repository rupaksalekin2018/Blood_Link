package uiu.app.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Edit extends AppCompatActivity implements View.OnClickListener,SingleUploadBroadcastReceiver.Delegate{

    public static final String UPLOAD_URL = "https://bloodlink.mrrobi.tech/api/updatePro";
    private static final int GALLERY_REQUEST_CODE = 1;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String userKey = "phoneKey";
    public static final String passKey = "passKey";
    public static final String imgKey = "imageBitmap";
    public static final String nameKey = "nameOfuser";
    com.mikhaellopez.circularimageview.CircularImageView profilePic;

    Button done, choosePhoto;
    EditText editName;

    Bitmap bitmap, setImage;
    String FilePath;
    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();
    ProgressDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }



    @Override
    public void onProgress(int progress) {
        Log.d("PROGRESS", "progress = " + progress);
        dialog.setProgress(progress);
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        dialog.dismiss();
    }

    @Override
    public void onCancelled() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        requestStoragePermission();
        profilePic = findViewById(R.id.ivEditPersonImage);
        done = findViewById(R.id.btnDone);
        choosePhoto = findViewById(R.id.btnChoosePhoto);
        editName = findViewById(R.id.etEditName);

        done.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, 0); // 0 - for private mode
    }

    @Override
    public void onClick(View v) {
        if(v == choosePhoto)
        {
            //Create an Intent with action as ACTION_PICK
            Intent intent=new Intent(Intent.ACTION_PICK);
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.setType("image/*");
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
            // Launching the Intent
            startActivityForResult(intent,GALLERY_REQUEST_CODE);
        }
        if(v == done){
            upload_file_multipart();
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading photo, please wait.");
            dialog.setMax(100);
            dialog.setCancelable(true);
            dialog.show();

            String url = "https://bloodlink.mrrobi.tech/api/getImgPath";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(imgKey, response);
                    editor.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("user",sharedpreferences.getString(userKey, ""));
                    return params;
                }
            };
            MySingleton.getInstance(Edit.this).addToRequestQueue(stringRequest);


            /*Intent i = new Intent(this, Profile.class);
            startActivity(i);
            finish();*/
        }
    }

    public void upload_file_multipart() {



        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate((SingleUploadBroadcastReceiver.Delegate) this);
            uploadReceiver.setUploadID(uploadId);

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
            .addFileToUpload(FilePath, "image") //Adding file
            .addParameter("name", sharedpreferences.getString(nameKey,"")) //Adding text parameter to the request
            .addParameter("user",sharedpreferences.getString(userKey,""))
            .setMaxRetries(2)
            .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


///////////////////////////


	static int MAX_IMAGE_DIMENSION = 1024;
	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
			new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
				srcBitmap.getHeight(), matrix, true);
		}

		return srcBitmap;
	}
	/////////////////////////////////////




	@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
						setImage = getCorrectlyOrientedImage(getApplicationContext(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


					profilePic.setImageBitmap(setImage);
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    FilePath = imgDecodableString;
                    Toast.makeText(this,imgDecodableString+"",Toast.LENGTH_LONG).show();
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    //profilePic.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;

            }


//        if(requestCode == 1 && resultCode == RESULT_OK)
//        {
//            Uri imgUri = data.getData();
//            GetRealPath getRealPath = new GetRealPath();
//            String picturePath = getRealPath.getUriRealPath(getApplication(),imgUri);
//            Toast.makeText(this,picturePath+"",Toast.LENGTH_LONG).show();
//
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
//                profilePic.setImageBitmap(bitmap);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                String imageStr = imgToString();
//                editor.putString(imgKey,imageStr);
//                editor.commit();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            /*try {
//                InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                bitmap = BitmapFactory.decodeStream(inputStream);
//                profilePic.setImageBitmap(bitmap);
//                Toast.makeText(this, bitmap+"",Toast.LENGTH_SHORT).show();
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }*/
//        }
    }

    private String imgToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
