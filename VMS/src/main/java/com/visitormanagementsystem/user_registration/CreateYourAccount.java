package com.visitormanagementsystem.user_registration;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.pojos.User;
import com.visitormanagementsystem.utils.PostClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Response;
import retrofit.Retrofit;

//import com.mingle.widget.ShapeLoadingDialog;

public class CreateYourAccount extends Fragment implements OnClickListener, Validator.ValidationListener {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final int REQUEST_CODE = 256;
    String nations_array[] = {"Select a Security Question*", "What is your mothers name", "What is your first car colour", "What was the house number and street name you lived in as a child?"
            , "What primary school did you attend?", "In what town or city was your first full time job?", "In what town or city did you meet your spouse/partner?",
            "What is the middle name of your oldest child?", "What is your grandmothers (on your mothers side) maiden name?", "What is your spouse or partners mothers maiden name?"
            , "In what town or city did your mother and father meet?"};
    Spinner spQuestionType;
    TextView tvReset, tvSubmit;
    ImageView sliderpanel_image, back;
    Validator validator;
    @NotEmpty
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    EditText etPassword;

    @ConfirmPassword
    EditText etConfirmPassword;
    EditText etProvideAnswer;
    String strUserId, strPassword, strConfirmPassword, strProvideAnswer;
    String strFirstName, strMiddleName, strLastName, strEmployId, strDesignation, strCompName, strCategory, strDOB, strSpnrNationality;
    String strWorkPermitNo, strVisitVisaNo, strIdNo, strMobileNo, strLandlineNo;
    //VMSUtilities iu;
    JSONArray jarray = null;
    JSONObject jsonObject = null;
    SharedPreferences sharedPreferences;
    @Bind(R.id.profileImage)
    ImageView profileImage;
    String byteArrayImageString;
    @Bind(R.id.createll)
    LinearLayout createll;
    ProgressDialog progressDialog;
    OkHttpClient client = new OkHttpClient();
    //private ShapeLoadingDialog shapeLoadingDialog;
    @NotEmpty
    @Email
    private EditText etUserId;

    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.create_your_account, null);
        ButterKnife.bind(this, view);
        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }


       /* shapeLoadingDialog = new ShapeLoadingDialog(getActivity());
        shapeLoadingDialog.setLoadingText(getActivity().getString(R.string.loading));
        shapeLoadingDialog.setCanceledOnTouchOutside(false);*/

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);

        Bundle bundle = getArguments();
        strFirstName = bundle.getString("firstName");
        strMiddleName = bundle.getString("middleName");
        strLastName = bundle.getString("lastName");
        strEmployId = bundle.getString("employId");
        strDesignation = bundle.getString("Designation");
        strCompName = bundle.getString("companyName");
        strCategory = bundle.getString("Category");
        strDOB = bundle.getString("dateOfBirth");
        strSpnrNationality = bundle.getString("strSpnrNationality");

        strWorkPermitNo = bundle.getString("workPermitNo");
        strVisitVisaNo = bundle.getString("visitVisaNo");
        strIdNo = bundle.getString("IdNo");
        strMobileNo = bundle.getString("mobileNo");
        strLandlineNo = bundle.getString("landlineNo");


        etUserId = (EditText) view.findViewById(R.id.userId);
        etPassword = (EditText) view.findViewById(R.id.password);
        etConfirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        etProvideAnswer = (EditText) view.findViewById(R.id.provideAnswer);


        spQuestionType = (Spinner) view.findViewById(R.id.questionType);
        ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion, nations_array);
        nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuestionType.setAdapter(nations_adapter);

        tvSubmit = (TextView) view.findViewById(R.id.submit);
        tvSubmit.setOnClickListener(this);
        //tvNext.setOnClickListener(this);
      //  iu = new VMSUtilities(getActivity());
        validator = new Validator(this);
        validator.setValidationListener(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String photopath = Prefs.getString("photopath", "nopath");
        if (!photopath.equalsIgnoreCase("nopath")) {
            File file = new File(photopath);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                profileImage.setImageBitmap(bitmap);
                byteArrayImageString = bitmapToBase64(bitmap);
            }

        }
        return view;
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @OnClick(R.id.captureImage)
    void changeprofileimage_capture(ImageView captureImage) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 111);
    }

    @OnClick(R.id.uploadImage)
    void changeprofileimage_gallery(ImageView imageView) {

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {

            if (!data.equals(null)) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getActivity(), image);

                File finalFile = new File(getRealPathFromURI(tempUri));
                sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                //  Prefs.putString("photopath", finalFile.getAbsolutePath());

                //   editor.commit();

                System.out.println("photo path :" + finalFile.getAbsolutePath());
                profileImage.setImageBitmap(image);
                byteArrayImageString = bitmapToBase64(image);
            } else {
                Toast.makeText(getActivity(), "Please capture image", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == REQUEST_CODE && null != data) {

            final Uri uri = data.getData();
            Log.i("TAG", "Uri = " + uri.toString());
            try {
                // Get the file path from the URI
                final String path = FileUtils.getPath(getActivity(), uri);
                sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                File file = new File(path);
                if (file.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    profileImage.setImageBitmap(bitmap);
                    byteArrayImageString = bitmapToBase64(bitmap);
                }
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                //editor.putString("photopath", file.getAbsolutePath());


                //  editor.commit();

            } catch (Exception e) {
                Log.e("FileSelectorTestActivity", "File select error", e);
            }

            //ivProfileImage.setImageURI(selectedImage);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private void doinBackground() {
        // shapeLoadingDialog.show();
        progressDialog.show();
        List<String> stringList = new ArrayList<>();
        stringList.add("");
        final PostClient postClient = new PostClient();

        System.out.println(strFirstName + " " + strMiddleName + "  " + strMobileNo + " " + etUserId.getText().toString());


        final User user = new User(etUserId.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString(), strDOB,
                strFirstName, strMiddleName,
                strLastName, strEmployId, strDesignation, strCompName, strCategory
                , strWorkPermitNo, strVisitVisaNo, strMobileNo, strLandlineNo, strSpnrNationality, byteArrayImageString);


       /* OkHttpClient client = new OkHttpClient();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String myjson = gson.toJson(user);

                try {
                    post(postClient.BASE_URL + "Account/Register", myjson, new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            System.out.println("REspose status " + response.isSuccessful());
                            System.out.println("resp body " + response.body().string());


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/

        retrofit.Call<User> call = postClient.myApiEndpointInterface.createUser(user);
        call.enqueue(new retrofit.Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User user1 = response.body();
                if (response.isSuccess() == true) {

                    System.out.println("REsp " + getClass().getName() + " " + new Gson().toJson(user1) + "\n" + "response.isSuccess()  " + response.isSuccess() + "\n " + response.raw() + "\n" + new Gson().toJson(user));
                    //  if (user1.getId() != null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Registration success !!!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.remove("photopath");
                    // editor.apply();
                    Prefs.clear();
                    // shapeLoadingDialog.dismiss();
                    progressDialog.dismiss();
                    getActivity().finish();

                    Intent intent = new Intent(getActivity(), VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    // }
                } else {
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(getView(), "Registration Failed !!!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.retry, new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // shapeLoadingDialog.show();
                            progressDialog.dismiss();
                           // doinBackground();
                        }
                    });
                    snackbar.setActionTextColor(ContextCompat.getColor(getActivity(), R.color.red));
                    snackbar.show();


                    //   Toast.makeText(getActivity().getApplicationContext(), "Registration Failed !!!", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
            }
        });

        //// TODO Auto-generated method stub
        //String server_mobile_response = iu.submitRegisterUserrequest(strUserId,strPassword);

        //Log.i("VMS", "response : " + server_mobile_response);
        //Toast.makeText(getActivity(), server_mobile_response.toString(), Toast.LENGTH_LONG).show();
//		try {
//			jarray = new JSONArray(server_mobile_response);
//			jsonObject = jarray.getJSONObject(0);
//			
//			String UserName = jsonObject.getString("Expires");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        RetrieveEditTextData();
        if (v.equals(tvSubmit)) {
        /*    if (strUserId.equalsIgnoreCase("") || strUserId.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter User Id", Toast.LENGTH_SHORT).show();
            } else if (strPassword.equalsIgnoreCase("") || strPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter Password", Toast.LENGTH_SHORT).show();
            } else if ((strConfirmPassword.equalsIgnoreCase("")) && !(strConfirmPassword.equals(strPassword))) {
                Toast.makeText(getActivity(), "Please Confirm Password", Toast.LENGTH_SHORT).show();
            } else if (!(strUserId.contains("@")) && !(strUserId.contains("."))) {
                Toast.makeText(getActivity(), "Please Confirm Password", Toast.LENGTH_SHORT).show();
            } else {
                doinBackground();
            }
*/
            validator.validate();


        }
    }

    private void RetrieveEditTextData() {
        // TODO Auto-generated method stub
        strUserId = etUserId.getText().toString();
        strPassword = etPassword.getText().toString();
        strConfirmPassword = etConfirmPassword.getText().toString();
        strProvideAnswer = etProvideAnswer.getText().toString();
    }

    @Override
    public void onValidationSucceeded() {

        doinBackground();


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);


            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    @OnClick(R.id.back)
    public void backevent(ImageView back)
    {
        getActivity().getFragmentManager().popBackStack();
    }
}
