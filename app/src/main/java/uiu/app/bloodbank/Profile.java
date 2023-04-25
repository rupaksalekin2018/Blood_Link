package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Profile extends AppCompatActivity implements View.OnClickListener{

	public static final String[] bloodGiven = {"None", "1 month ago", "2 months ago", "3 months ago"};

	SharedPreferences sharedpreferences;
	public static final String mypreference = "mypref";
	public static final String userKey = "phoneKey";
	public static final String passKey = "passKey";
	public static final String imgKey = "imageBitmap";
	public static final String nameKey = "nameOfuser";
	public static final String bloodKey = "b_groupofuser";
	public static final String emailKey = "emailofuser";
	public static final String genderKey = "genderofuser";

	com.mikhaellopez.circularimageview.CircularImageView ivProfile;
	TextView tvPersonName , tvPersonPhone, tvBloodGroup, tvPersonEmail;
	Spinner monthSpinner;
	LinearLayout llBtnMyFeed, llBtnEdit, llBtnLogout;
	ImageView gender;
	Bitmap img = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, 0); // 0 - for private mode
		monthSpinner = findViewById(R.id.spinnerMonths);


		gender = findViewById(R.id.tvPersonGender);
		tvBloodGroup = findViewById(R.id.tvPersonBloodGroup);
		llBtnEdit = findViewById(R.id.profileEdit);
		llBtnLogout = findViewById(R.id.profileLogout);
		llBtnMyFeed = findViewById(R.id.profileMyFeed);
		ivProfile = findViewById(R.id.ivPersonImage);
		tvPersonName = findViewById(R.id.tvPersonName);
		tvPersonPhone = findViewById(R.id.tvPersonPhone);
		tvPersonEmail = findViewById(R.id.tvPersonEmail);


		getImage();



		//setContentView(R.layout.activity_login);
		//setContentView(R.layout.activity_profile);
		monthSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bloodGiven));
		tvPersonName.setText(sharedpreferences.getString(nameKey,""));
		tvPersonPhone.setText(sharedpreferences.getString(userKey,""));
		tvPersonEmail.setText(sharedpreferences.getString(emailKey,""));
		tvBloodGroup.setText(sharedpreferences.getString(bloodKey,""));

		if(sharedpreferences.getString(genderKey,"").equalsIgnoreCase("male")){
			System.out.println(sharedpreferences.getString(genderKey,""));
			gender.setImageResource(R.drawable.gendermale);
		}else{
			System.out.println(sharedpreferences.getString(genderKey,""));
			gender.setImageResource(R.drawable.genderfemale);
		}

		llBtnMyFeed.setOnClickListener(this);
		llBtnLogout.setOnClickListener(this);
		llBtnEdit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == llBtnEdit)
		{
			//Toast.makeText(this, "edit",Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, Edit.class);
			startActivity(i);
		}
		else if(v == llBtnLogout)
		{
			if (sharedpreferences.contains(userKey)&&sharedpreferences.contains(passKey)) {
				SharedPreferences.Editor editor = sharedpreferences.edit();
				editor.clear();
				editor.commit();
				Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent(Profile.this, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
		else if(v == llBtnMyFeed)
		{
			Toast.makeText(this, "feed",Toast.LENGTH_SHORT).show();
		}
	}
	public Bitmap getImage(){

		String url = "";
		if(sharedpreferences.contains(imgKey)){
			 url = sharedpreferences.getString(imgKey,"");
			//Toast.makeText(this,sharedpreferences.getString(imgKey,"")+"",Toast.LENGTH_SHORT).show();
		}else{
			url = "";
		}

		ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				Uri selectedImage = null;
//				try {
					///selectedImage = getImageUri(response);
					//img = getCorrectlyOrientedImage(getApplicationContext(), selectedImage);
					img = response;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}

				ivProfile.setImageBitmap(img);
			}
		}, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});
		imageRequest.setShouldCache(false);
		MySingleton.getInstance(Profile.this).addToRequestQueue(imageRequest);
		

		return img;
	}


	///////////////////////////

	public Uri getImageUri(Bitmap inImage) throws IOException {
		File tempDir= Environment.getExternalStorageDirectory();
		tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
		tempDir.mkdir();
		File tempFile = File.createTempFile("Profile Pic", ".jpg", tempDir);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		byte[] bitmapData = bytes.toByteArray();

		//write the bytes in file
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(bitmapData);
		fos.flush();
		fos.close();
		return Uri.fromFile(tempFile);
	}

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
	//////////////////////////////////////

}
