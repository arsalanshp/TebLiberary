package com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines;
import com.theartofdev.edmodo.cropper.CropImageView.ScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.DataProvider;
import library.tebyan.com.teblibrary.classes.ReadJSONTaskInput;
import library.tebyan.com.teblibrary.classes.Sms.StringResult;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.model.FileUploadInput;
import library.tebyan.com.teblibrary.model.UploadFileResult;

public class DialogSelectImage extends Dialog {

	public final static int TAKE_IMAGE_CAMERA = 15;
	public final static int PICK_IMAGE_FROM_GALLERY = 16;
	public String url;
	Activity activity;
	private static File mFileTemp;
	private static File cropFilepath = null;
	String imagePath;
	Bitmap selectedImage;
	public static String imageType = "Profile";
	private int aspectX;
	private int aspectY;

	public DialogSelectImage(Activity context) {

		super(context);
		this.activity = context;
		CreateSavePath();

		// TODO Auto-generated constructor stub
	}

	private void CreateSavePath() {
		// TODO Auto-generated method stub
		/*
		 * if (Environment.getExternalStorageState().equals(
		 * Environment.MEDIA_MOUNTED)) { cameraSavePath =
		 * Environment.getExternalStorageDirectory() .getAbsolutePath() + "/" +
		 * activity.getPackageName() + "/images/";
		 * 
		 * cropFilepath = new File(Environment.getExternalStorageDirectory()
		 * .getAbsolutePath() + "/" + activity.getPackageName() +
		 * "/cropImages/"); } else { cameraSavePath =
		 * activity.getFilesDir().getAbsolutePath() + "/images/"; cropFilepath =
		 * new File(activity.getFilesDir().getAbsolutePath() + "/cropImages/");
		 * }
		 * 
		 * File mFile = new File(cameraSavePath); if (!mFile.exists())
		 * mFile.mkdirs(); if (!cropFilepath.exists()) cropFilepath.mkdirs();
		 */

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					"image.jpeg");
			cropFilepath = new File(Environment.getExternalStorageDirectory(),
					"cropImage.jpeg");
		} else {
			mFileTemp = new File(activity.getFilesDir(), "image.jpeg");
			cropFilepath = new File(activity.getFilesDir(), "cropImage.jpeg");
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_select_image);
		TextView txtProfile = (TextView) findViewById(R.id.txtProfile);
		TextView txtCover = (TextView) findViewById(R.id.txtCover);

		if (imageType.equalsIgnoreCase("Profile")) {
			aspectY = 400;
			aspectX = 400;
			url = "/Service.asmx/CropProfilePhoto";
			txtProfile.setVisibility(View.VISIBLE);
			txtCover.setVisibility(View.GONE);
		} else if (imageType.equalsIgnoreCase("Cover")) {
			aspectY = 200;
			aspectX = 850;
			url = "/ImageGallery.asmx/UpdateCoverImage";
			txtCover.setVisibility(View.VISIBLE);
			txtProfile.setVisibility(View.GONE);
		}
		findViewById(R.id.button_pickFromGalley).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						pickImageFromgallery();
					}
				});

		findViewById(R.id.button_takePhotoByCamera).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						TakeImageByCamera();
					}
				});
	}

	public void pickImageFromgallery() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		activity.startActivityForResult(photoPickerIntent,
				PICK_IMAGE_FROM_GALLERY);
		dismiss();

	}

	public void TakeImageByCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri mImageCaptureUri = Uri.fromFile(mFileTemp);
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		// intent.putExtra("return-data", true);
		activity.startActivityForResult(intent,
				DialogSelectImage.TAKE_IMAGE_CAMERA);

		dismiss();

	}

	private Uri getUri() {
		String state = Environment.getExternalStorageState();
		if (!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
			return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

		return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	}

	public void userSelectImage(int requestCode, int resultCode, Intent data)
			throws FileNotFoundException {
		if (resultCode == Activity.RESULT_OK) {

			String filePath = null;

			switch (requestCode) {
				case DialogSelectImage.PICK_IMAGE_FROM_GALLERY :
					Uri selectedImageUri = null;
					if (data != null) {

						selectedImageUri = data.getData();
						if (selectedImageUri == null) {
							return;
						}
						filePath = Utils.getPath(activity, selectedImageUri);
					}
					/*
					 * mFileTemp = new File(filePath); new
					 * Crop(Uri.fromFile(mFileTemp))
					 * .output(Uri.fromFile(cropFilepath)) .withAspect(aspectX,
					 * aspectY).start(activity);
					 */
					CropImage.activity(selectedImageUri)
							.setGuidelines(Guidelines.ON)
							.setMinCropResultSize(aspectX, aspectY)
							.setAspectRatio(aspectX, aspectY)
							.setFixAspectRatio(true)
							.setScaleType(ScaleType.CENTER_INSIDE)
							.setMinCropWindowSize(aspectX, aspectY)
							.setFixAspectRatio(true).start(activity);

					break;
				case DialogSelectImage.TAKE_IMAGE_CAMERA :

					// selectedImage = (Bitmap) data.getExtras().get("data");

					selectedImage = decodeFile(mFileTemp);
					selectedImage.compress(CompressFormat.JPEG, 100,
							new FileOutputStream(mFileTemp));
					CropImage.activity(Uri.fromFile(mFileTemp))
							.setGuidelines(Guidelines.ON)
							.setMinCropResultSize(aspectX, aspectY)
							.setAspectRatio(aspectX, aspectY)
							.setFixAspectRatio(true)
							.setScaleType(ScaleType.CENTER_INSIDE)
							.setMinCropWindowSize(aspectX, aspectY)
							.setFixAspectRatio(true).start(activity);
					break;
				case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
					CropImage.ActivityResult result = CropImage
							.getActivityResult(data);
					imagePath = result.getUri().getPath();
					if (data != null) {
						// imagePath = Crop.getOutput(data).getPath();
						if (imagePath == null) {
							return;
						}
						selectedImage = BitmapFactory
								.decodeStream(new FileInputStream(new File(
										imagePath)));
						File outputDir = activity.getCacheDir();
						File outputFile = null;
						try {
							outputFile = File.createTempFile("prefix",
									"extension", outputDir);

							OutputStream outStream = null;
							outStream = new FileOutputStream(outputFile);
							selectedImage.compress(Bitmap.CompressFormat.PNG,
									0, outStream);
							outStream.flush();
							outStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						/*
						 * Toast.makeText( activity, aspectX + ", " + aspectY +
						 * ", " + selectedImage.getWidth() + ", " +
						 * selectedImage.getHeight(), Toast.LENGTH_SHORT).show();
						 */
						if (outputFile == null) {
							return;
						}
						FileUploadInput input = new FileUploadInput();
						input.file = outputFile;
						if (imageType.equalsIgnoreCase("Profile")) {
							input.mode = 6;
						} else if (imageType.equalsIgnoreCase("Cover")) {
							input.mode = 5;
						}
						// ByteArrayOutputStream bos = new
						// ByteArrayOutputStream();
						// selectedImage.compress(CompressFormat.PNG, 0, bos);
						doUploadFile(input);
						break;
					}

			}

		}

	}

	private Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 200;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public String getSelectedImagePath() {
		return imagePath;
	}

	public Bitmap getSelectedImage() {
		return selectedImage;
	}

	public void clearMemory() {
		if (selectedImage != null) {
			selectedImage.recycle();
			selectedImage = null;
		}
		if (mFileTemp.exists())
			mFileTemp.delete();
		if (cropFilepath.exists())
			cropFilepath.delete();
	}
	public void doUploadFile(FileUploadInput input) {
		String text = activity.getResources().getString(
				R.string.please_wait);
		// progressDialog = ProgressDialog.show(Globals.currentActivity, "",
		// text);
		new ReadJSONTask().execute(input);
	}

	private class ReadJSONTask extends AsyncTask<FileUploadInput, Void, String> {

		@Override
		protected String doInBackground(FileUploadInput... inputs) {
			return DataProvider.uploadFile(inputs[0].file, inputs[0].mode);
		}

		@Override
		protected void onPostExecute(String json) {
			try {
				// progressDialog.dismiss();
				Gson gson = new Gson();
				if (json != null & json.length() > 0) {
					UploadFileResult result = gson.fromJson(json,
							UploadFileResult.class);
					if (result != null && result.success && result.id > 0) {

						cropPhoto(result.id, 0, 0, selectedImage.getWidth(),
								selectedImage.getHeight());
					} else if (!result.success || result.id == 0) {
						Toast.makeText(activity, result.message,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(activity,
								R.string.please_try_again, Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					String text = activity.getResources().getString(
							R.string.network_connection_fail);
					Toast.makeText(activity, text + " ",
							Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(json);
			} catch (Exception e) {

			}
		}
	}

	public void cropPhoto(int imageId, int x, int y, int width, int height) {
		@SuppressWarnings("unused")
		String text = activity.getResources().getString(
				R.string.please_wait);
		// progressDialog = ProgressDialog.show(current, "", text);

		JSONObject data = new JSONObject();
		try {
			data.put("imageId", imageId);
			data.put("x", x);
			data.put("y", y);
			data.put("width", width);
			data.put("height", height);
			data.put("wallId", "");
		} catch (JSONException e) {

		}
		ReadJSONTaskInput input = new ReadJSONTaskInput();

		input.url = url;
		input.data = data;
		input.needsLogin = true;
		new ReadJSONTask1().execute(input);
	}

	private class ReadJSONTask1
			extends
				AsyncTask<ReadJSONTaskInput, Void, String> {

		@Override
		protected String doInBackground(ReadJSONTaskInput... inputs) {
			return DataProvider.readJSONFeed(inputs[0]);
		}

		@Override
		protected void onPostExecute(String json) {
			try {
				// progressDialog.dismiss();
				Gson gson = new Gson();
				if (json != null & json.length() > 0) {
					StringResult result = gson.fromJson(json,
							StringResult.class);
					if (result != null) {
						String text = "";
						if (imageType.equalsIgnoreCase("Profile")) {
							text = activity.getResources().getString(
									R.string.profile_image_changed);
						} else {
							text = activity.getResources().getString(
									R.string.cover_image_changed);
						}
						Toast.makeText(activity, text + " ",
								Toast.LENGTH_SHORT).show();
					} else {
						String text = activity.getResources().getString(
								R.string.please_try_again);
						Toast.makeText(activity, text + " ",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					String text = activity.getResources().getString(
							R.string.network_connection_fail);
					Toast.makeText(activity, text + " ",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// Log.e("ERROR", e.getMessage());

			}
		}
	}

}
