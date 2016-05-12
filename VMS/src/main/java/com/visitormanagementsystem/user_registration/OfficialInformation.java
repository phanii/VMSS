package com.visitormanagementsystem.user_registration;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.pojos.Spinner_ValuesBean;
import com.visitormanagementsystem.utils.MyApplication;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class OfficialInformation extends Fragment implements OnClickListener, Validator.ValidationListener {
    private static final int REQUEST_CODE = 256;
    String nations_array[] = {"Identification Type", "Driving Licence", "PAN", "Adhar Number", "Passport Number", "Voter Card Number"};
    Spinner spIdType;
    TextView tvNext;
    ImageView sliderpanel_image, back;
    @NotEmpty(message = "Mobile Number is Required !!" )
    @Length(min = 10, max = 10)
    EditText etMobileNo;
    EditText etWorkPermitNo, etVisitVisaNo, etIdNo, etLandlineNo;
    String strFirstName, strMiddleName, strLastName, strEmployId, strDesignation, strCompName, strCategory, strDOB;
    String strWorkPermitNo, strVisitVisaNo, strIdNo, strMobileNo, strLandlineNo, strSpnrNationality;
    Validator validator;
    SharedPreferences sharedPreferences;
    ArrayList<Spinner_ValuesBean> idTypeArray;
    @Bind(R.id.profileImage)
    ImageView profileImage;
    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.official_info, null);
        ButterKnife.bind(this, view);
        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }

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
        etWorkPermitNo = (EditText) view.findViewById(R.id.workPermitNo);
        etVisitVisaNo = (EditText) view.findViewById(R.id.visitVisaNo);
        etIdNo = (EditText) view.findViewById(R.id.idNo);
        etMobileNo = (EditText) view.findViewById(R.id.mobileNo);
        etLandlineNo = (EditText) view.findViewById(R.id.landlineNo);
        idTypeArray = new ArrayList<>();
        validator = new Validator(this);
        validator.setValidationListener(this);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String photopath = Prefs.getString("photopath", "nopath");
        System.out.println("photopath " + photopath);
        if (!photopath.equalsIgnoreCase("nopath")) {
            File file = new File(photopath);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                profileImage.setImageBitmap(bitmap);

            }

        }
        spIdType = (Spinner) view.findViewById(R.id.idType);


        PostClient postClient = new PostClient();
        Call<List<Spinner_ValuesBean>> spinner_valuesBeanCall = postClient.myApiEndpointInterface.getSPINNER_ID_OBSERVABLE();

        spinner_valuesBeanCall.enqueue(new Callback<List<Spinner_ValuesBean>>() {
            @Override
            public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {

                VMSConstants.mylog("Size " + String.valueOf(response.body().size()) + "\n" + response.raw());

                ArrayList<String> stringArrayList = new ArrayList<String>();

                stringArrayList.clear();
                // stringArrayList.add("  Select ID  ");
                for (int i = 0; i < response.body().size(); i++) {
                    idTypeArray.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                            response.body().get(i).getTypeDetailValueLang2()));

                    if (MyApplication.mylanguage.equalsIgnoreCase("en")) {
                        stringArrayList.add(idTypeArray.get(i).getTypeDetailValueLang1());

                    } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")) {
                        stringArrayList.add(idTypeArray.get(i).getTypeDetailValueLang2());
                    }
                }
                ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                        stringArrayList);
                nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                spIdType.setAdapter(nations_adapter);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        tvNext = (TextView) view.findViewById(R.id.next);
        tvNext.setOnClickListener(this);


        return view;
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
            try {
                if (!data.equals(null)) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(getActivity(), image);

                    File finalFile = new File(getRealPathFromURI(tempUri));
                    sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                    Prefs.putString("photopath", finalFile.getAbsolutePath());

                    //   editor.commit();

                    System.out.println("photo path :" + finalFile.getAbsolutePath());
                    profileImage.setImageBitmap(image);
                } else {
                    Toast.makeText(getActivity(), "Please capture image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

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
                }
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                Prefs.putString("photopath", file.getAbsolutePath());


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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        RetrieveEditTextData();
        if (v.equals(tvNext)) {
            validator.validate();

//			if(strMobileNo.equalsIgnoreCase("") || strMobileNo.isEmpty())
//			{
//				Toast.makeText(getActivity(), "Please enter Mobile Number", Toast.LENGTH_SHORT).show();
//			}
//			else
//			{
           /* Fragment mr = new CreateYourAccount();

            Bundle bundle = new Bundle();
            bundle.putString("firstName", strFirstName);
            bundle.putString("middleName", strMiddleName);
            bundle.putString("lastName", strLastName);
            bundle.putString("employId", strEmployId);
            bundle.putString("Designation", strDesignation);
            bundle.putString("companyName", strCompName);
            bundle.putString("Category", strCategory);
            bundle.putString("dateOfBirth", strDOB);

            bundle.putString("workPermitNo", strWorkPermitNo);
            bundle.putString("visitVisaNo", strVisitVisaNo);
            bundle.putString("IdNo", strIdNo);
            bundle.putString("mobileNo", strMobileNo);
            bundle.putString("landlineNo", strLandlineNo);

            mr.setArguments(bundle);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("mobile");
            ft.replace(R.id.frame_container, mr, "service").addToBackStack(null).commit();*/

//			}
        }
    }

    private void RetrieveEditTextData() {
        // TODO Auto-generated method stub
        strWorkPermitNo = etWorkPermitNo.getText().toString();
        strVisitVisaNo = etVisitVisaNo.getText().toString();
        strIdNo = etIdNo.getText().toString();
        strMobileNo = etMobileNo.getText().toString();
        strLandlineNo = etLandlineNo.getText().toString();

    }

    @Override
    public void onValidationSucceeded() {
        Fragment mr = new CreateYourAccount();

        Bundle bundle = new Bundle();
        bundle.putString("firstName", strFirstName);
        bundle.putString("middleName", strMiddleName);
        bundle.putString("lastName", strLastName);
        bundle.putString("employId", strEmployId);
        bundle.putString("Designation", strDesignation);
        bundle.putString("companyName", strCompName);
        bundle.putString("Category", strCategory);
        bundle.putString("dateOfBirth", strDOB);
        bundle.putString("strSpnrNationality", strSpnrNationality);

        bundle.putString("workPermitNo", strWorkPermitNo);
        bundle.putString("visitVisaNo", strVisitVisaNo);
        bundle.putString("IdNo", strIdNo);
        bundle.putString("mobileNo", strMobileNo);
        bundle.putString("landlineNo", strLandlineNo);

        mr.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("mobile");
        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            ft.replace(R.id.frame_container1, mr, "service").addToBackStack(null).commit();

        } else {
            ft.replace(R.id.frame_container, mr, "service").addToBackStack(null).commit();

        }
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
