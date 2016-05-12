package com.visitormanagementsystem.userprofile_related;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.EmployeeDashboard;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.pojos.Spinner_ValuesBean;
import com.visitormanagementsystem.pojos.Visitordetails;
import com.visitormanagementsystem.utils.MyApplication;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EditProfile extends Fragment implements OnClickListener, DatePickerDialogFragment.DatePickerDialogHandler {

    private static final int REQUEST_CODE = 256;
    String nations_array[] = {"Identification Type", "Driving Licence", "PAN", "Adhar Number", "Passport Number",
            "Voter Card Number"};
    Spinner spIdType;
    ImageView sliderpanel_image, back;
    @Bind(R.id.ed_category)
    EditText ed_category;
    @Bind(R.id.ed_companyname)
    EditText ed_companyname;
    @Bind(R.id.ed_desg)
    EditText ed_desg;
    @Bind(R.id.ed_dob)
    TextView ed_dob;
    @Bind(R.id.ed_empdispname)
    EditText ed_empdispname;
    @Bind(R.id.ed_firstName)
    EditText ed_firstName;
    @Bind(R.id.ed_idnumber)
    EditText ed_idnumber;
    @Bind(R.id.ed_idType)
    Spinner sp_idType;
    @Bind(R.id.ed_landno)
    EditText ed_landno;
    @Bind(R.id.ed_mobno)
    EditText ed_mobno;
    @Bind(R.id.ed_nationality)
    EditText ed_nationality;
    @Bind(R.id.ed_visanumber)
    EditText ed_visanumber;
    @Bind(R.id.ed_workpermitnumber)
    EditText ed_workpermitnumber;
    @Bind(R.id.ed_myRadioGroup)
    RadioGroup ed_myRadioGroup;
    @Bind(R.id.captureImage)
    ImageView captureImage;
    @Bind(R.id.profileImage)
    ImageView profileImage;
    @Bind(R.id.uploadImage)
    ImageView uploadImage;
    @Bind(R.id.ed_male)
    RadioButton ed_male;
    @Bind(R.id.ed_female)
    RadioButton ed_female;
    SharedPreferences sharedPreferences;
    byte[] byteArrayImage;
    String byteArrayImageString;
    ProgressDialog progressDialog;
    String sexType, SelectedIdName;
    final Calendar calendar = Calendar.getInstance();
    ArrayList<Spinner_ValuesBean> idTypeArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.edit_profile, null);
        ButterKnife.bind(this, view);
        idTypeArray = new ArrayList<>();
        getActivity().setTitle("Edit Profile");


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


                sp_idType.setAdapter(nations_adapter);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return view;
    }

    @OnItemSelected(R.id.ed_idType)
    void onItemSelected(int position) {
        SelectedIdName = sp_idType.getItemAtPosition(position).toString();
        VMSConstants.mysysout(sp_idType.getItemAtPosition(position).toString() + " " + idTypeArray.get(position).getTypeDetailsCode());

    }


    @OnClick(R.id.ed_dob)
    public void getDob(final TextView ed_dob) {

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    final Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {

            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                Date date = sdf.parse(sdf.format(myCalendar.getTime()));
                Date mydobdate = date;
                Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();
                String aptfromdate = "\"" + String.valueOf(mydobdate) + "\"";


                Date fromdate = gson.fromJson(aptfromdate, Date.class);

                java.sql.Date fromdate_afterdate_val = new java.sql.Date(fromdate.getTime());
                java.sql.Date sqlDate = new java.sql.Date(fromdate.getTime());
                System.out.println("***************\n sqlDatefrom  " + sqlDate + "  " + fromdate_afterdate_val.getTime() + "\n *********************");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String displaydateformat = "dd MMM yyyy";
            SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
            String mydobstring = sdf1.format(myCalendar.getTime());
            ed_dob.setText(mydobstring);


        }

    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);

/*        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Gson revGson = new Gson();
                String jsonval = Prefs.getString("profile_id", "");

                Visitordetails visitordetails = revGson.fromJson(jsonval, Visitordetails.class);
                ed_empdispname.setText(visitordetails.getVisitorName());
                ed_firstName.setText(visitordetails.getEmployeeID());
                ed_companyname.setText(visitordetails.getCompanyName());
                ed_desg.setText(visitordetails.getDesignation());
                ed_category.setText(visitordetails.getSectorCompCategory());
                ed_idnumber.setText(visitordetails.getNationalID());
                ed_dob.setText(visitordetails.getDOB());
                if (visitordetails.getPhoto() != null) {
                    String photostring = visitordetails.getPhoto().toString();

                    byte[] imageAsBytes = Base64.decode(photostring.getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    profileImage.setImageBitmap(bitmap);
                }
                //ed_myRadioGroup


                ed_myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.ed_male:
                                //Toast.makeText(getActivity(),"male",Toast.LENGTH_SHORT).show();
                                sexType = getActivity().getString(R.string.male);
                                //VMSConstants.mysysout(" radiogroup " + sexType);
                                break;
                            case R.id.ed_female:
                                sexType = getActivity().getString(R.string.female);
                                //VMSConstants.mysysout(" radiogroup " + sexType);
                                //Toast.makeText(getActivity(),"female",Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });
                if (visitordetails.getGender() != null) {

                    if (visitordetails.getGender().contains("101")) {
                        ed_male.setChecked(true);
                        sexType = getActivity().getString(R.string.male);
                    } else if (visitordetails.getGender().contains("102")) {
                        ed_female.setChecked(true);
                        sexType = getActivity().getString(R.string.female);
                    }
                }

//
                //VMSConstants.mysysout("edit gender " + visitordetails.getGender() + "  " + sexType);
                ed_landno.setText(visitordetails.getLandlinePhoneNumber());
                ed_mobno.setText(visitordetails.getMobileNumber());
                ed_nationality.setText(visitordetails.getNationality());
                ed_visanumber.setText(visitordetails.getVisitVisaNumber());
                ed_workpermitnumber.setText(visitordetails.getWorkPermitNumber());


            }
        });*/
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

    @OnClick(R.id.captureImage)
    void changeprofileimage_capture(ImageView captureImage) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 111);
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
                editor.putString("photopath", finalFile.getAbsolutePath());

                //editor.commit();

                System.out.println("photo path :" + finalFile.getAbsolutePath());
                // profileImage.setImageBitmap(image);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath(), options);

                profileImage.setImageBitmap(bitmap);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteArrayImage = baos.toByteArray();
                byteArrayImageString = bitmapToBase64(bitmap);
                System.out.println("Appointment details in camera" + byteArrayImage);


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
                editor.putString("photopath", file.getAbsolutePath());


                //editor.commit();

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

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.updateprofile)
    public void Updateprofile(TextView updateprofile) {
        progressDialog.show();

        Visitordetails visitordetails = new Visitordetails(ed_empdispname.getText().toString(), Prefs.getString("ProfileId", ""), Prefs.getString("ProfileVisitorId", ""), ed_firstName.getText().toString(), ed_companyname.getText().toString(), ed_desg.getText().toString(),
                ed_nationality.getText().toString(), ed_category.getText().toString(), ed_dob.getText().toString(), sexType, ed_visanumber.getText().toString(),
                ed_workpermitnumber.getText().toString(), SelectedIdName, ed_idnumber.getText().toString(), ed_mobno.getText().toString(), ed_landno.getText().toString(), byteArrayImageString);


        PostClient postClient = new PostClient();
        Call<Visitordetails> visitordetailsCall = postClient.myApiEndpointInterface.updateUser(visitordetails);
        visitordetailsCall.enqueue(new Callback<Visitordetails>() {
            @Override
            public void onResponse(Response<Visitordetails> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    System.out.println("update user " + getClass().getName() + "  " + response.raw() + "\n" + new Gson().toJson(response.body()));
                    Toast.makeText(getActivity(), R.string.profileupdated, Toast.LENGTH_SHORT).show();
                    Fragment imr = new EmployeeDashboard();

                    if (imr != null) {
                        FragmentManager fmr = getFragmentManager();
                        fmr.beginTransaction().replace(R.id.frame_container, imr).addToBackStack(null).commit();
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        ed_dob.setText("Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth);
    }
}
