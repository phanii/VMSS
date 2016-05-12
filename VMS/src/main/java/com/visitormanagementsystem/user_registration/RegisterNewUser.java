package com.visitormanagementsystem.user_registration;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.pojos.Spinner_ValuesBean;
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
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterNewUser extends Fragment implements OnClickListener, Validator.ValidationListener {
    private static final int REQUEST_CODE = 256;
    Spinner spSelectQuestion, spNatinality;
    Validator validator;
    String questions_array[] = {"What is your mothers name", "What is your first car colour", "What was the house number and street name you lived in as a child?"
            , "What primary school did you attend?", "In what town or city was your first full time job?", "In what town or city did you meet your spouse/partner?",
            "What is the middle name of your oldest child?", "What is your grandmothers (on your mothers side) maiden name?", "What is your spouse or partners mothers maiden name?"
            , "In what town or city did your mother and father meet?"};
   /* String nations_array[] = {"Select Nationality", "Indian", "American", "Saudi", "Australian", "Banglashi", "Brazilian", "British", "Canadian", "Chinese"
            , "Colombian", "Dutch", "Egyptian", "Indonesian", "Italian", "Mexican"};*/
   ArrayList<Spinner_ValuesBean> nations_array;
    TextView tvNext;
    @NotEmpty(message = "First Name is Required !!")
    EditText etFirstName;
    @NotEmpty(message = "Middle Name is Required !!")
    EditText etMiddleName;
    @NotEmpty(message = "Last Name is Required !!")
    EditText etLastName;
    EditText etEmployId, etDesignation, etCompName, etCategory;
    TextView etDOB;
    String strFirstName, strMiddleName, strLastName, strEmployId, strDesignation, strCompName, strCategory, strDOB, strSpnrNationality, mydobstring;

    Date mydobdate;
    java.sql.Date sqlDate, sqlDateto = null;
    SharedPreferences sharedPreferences;

    @Bind(R.id.profileImage)
    ImageView profileImage;

    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;

    @Bind(R.id.back)
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.register_new_user, null);
        ButterKnife.bind(this, view);


        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }
        spSelectQuestion = (Spinner) view.findViewById(R.id.selectquestion);
        spNatinality = (Spinner) view.findViewById(R.id.reg_nationality);
        nations_array = new ArrayList<>();

        if (nations_array.size() > 0) {

            nations_array.clear();
        }
		/*ArrayAdapter<String> states_adapter=new ArrayAdapter<String>(this, R.layout.sp_selectquestion, questions_array);
        states_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSelectQuestion.setAdapter(states_adapter);*/

          /*

        get nationality spinner
         */

        PostClient postClient = new PostClient();
        Call<List<Spinner_ValuesBean>> spinner_nationalityListCall = postClient.myApiEndpointInterface.getSPINNER_NATIONALITY_OBSERVABLE();

        spinner_nationalityListCall.enqueue(new Callback<List<Spinner_ValuesBean>>()

                                            {
                                                @Override
                                                public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {
                                                    VMSConstants.mylog("Size spinner " + String.valueOf(response.body().size()) + "\n" + response.raw());

                                                    ArrayList<String> stringArrayList = new ArrayList<String>();

                                                    stringArrayList.clear();
                                                    // stringArrayList.add("Select Nationality");
                                                    for (int i = 0; i < response.body().size(); i++) {
                                                        nations_array.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                                                                response.body().get(i).getTypeDetailValueLang2()));

                                                        if (MyApplication.mylanguage.equalsIgnoreCase("en")||String.valueOf(getActivity().getResources().getConfiguration().locale).equalsIgnoreCase("en")) {
                                                            stringArrayList.add(nations_array.get(i).getTypeDetailValueLang1());

                                                        } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")||String.valueOf(getActivity().getResources().getConfiguration().locale).equalsIgnoreCase("ar")) {
                                                            stringArrayList.add(nations_array.get(i).getTypeDetailValueLang2());
                                                        }
                                                    }
                                                    ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                                                            stringArrayList);
                                              //      nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spNatinality.setAdapter(nations_adapter);
                                                   // VMSConstants.mysysout("adapter length " + nations_adapter.getCount() + "  stringArrayList "+stringArrayList);

                                                }

                                                @Override
                                                public void onFailure(Throwable t) {
                                                    VMSConstants.mylog("onFailure");
                                                }
                                            }

        );


        spNatinality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSpnrNationality = parent.getItemAtPosition(position).toString();
                System.out.println("SElected spinner value is " + strSpnrNationality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDOB = (TextView) view.findViewById(R.id.dob);
        etFirstName = (EditText) view.findViewById(R.id.firstName);
        etMiddleName = (EditText) view.findViewById(R.id.middleName);
        etLastName = (EditText) view.findViewById(R.id.lastName);
        etEmployId = (EditText) view.findViewById(R.id.empId);
        etDesignation = (EditText) view.findViewById(R.id.designation);
        etCompName = (EditText) view.findViewById(R.id.compName);
        etCategory = (EditText) view.findViewById(R.id.category);


        tvNext = (TextView) view.findViewById(R.id.next);
        tvNext.setOnClickListener(this);


        validator = new Validator(this);
        validator.setValidationListener(this);

        // back.setVisibility(View.GONE);
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
                    mydobdate = date;
                    Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();
                    String aptfromdate = "\"" + String.valueOf(mydobdate) + "\"";


                    Date fromdate = gson.fromJson(aptfromdate, Date.class);

                    java.sql.Date fromdate_afterdate_val = new java.sql.Date(fromdate.getTime());
                    sqlDate = new java.sql.Date(fromdate.getTime());
                    System.out.println("***************\n sqlDatefrom  " + sqlDate + "  " + fromdate_afterdate_val.getTime() + "\n *********************");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String displaydateformat = "dd MMM yyyy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
                mydobstring = sdf1.format(myCalendar.getTime());
                etDOB.setText(mydobstring);


            }

        };

        etDOB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    private void RetrieveEditTextData() {
        // TODO Auto-generated method stub

        strFirstName = etFirstName.getText().toString();
        strMiddleName = etMiddleName.getText().toString();
        strLastName = etLastName.getText().toString();
        strEmployId = etEmployId.getText().toString();
        strDesignation = etDesignation.getText().toString();
        strCompName = etCompName.getText().toString();
        strCategory = etCategory.getText().toString();
        strDOB = etDOB.getText().toString();

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
                Prefs.putString("photopath", finalFile.getAbsolutePath());

                // editor.commit();


                System.out.println("photo path :" + finalFile.getAbsolutePath());
                profileImage.setImageBitmap(image);
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
                }
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                Prefs.putString("photopath", file.getAbsolutePath());


                editor.commit();

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

//			if(strFirstName.equalsIgnoreCase("") || strFirstName.isEmpty())
//			{
//				Toast.makeText(getActivity(), "Please enter First Name", Toast.LENGTH_SHORT).show();
//			}
//			else if(strLastName.equalsIgnoreCase("")|| strLastName.isEmpty())
//			{
//				Toast.makeText(getActivity(), "Please enter Last Name", Toast.LENGTH_SHORT).show();
//			}
//			else if(strCompName.equalsIgnoreCase("")|| strCompName.isEmpty())
//			{
//				Toast.makeText(getActivity(), "Please enter Company Name", Toast.LENGTH_SHORT).show();
//			}
//			else{
                /*Fragment mr = new OfficialInformation();

				Bundle bundle = new Bundle();
	        	bundle.putString("firstName", strFirstName);
				bundle.putString("middleName", strMiddleName);
				bundle.putString("lastName", strLastName);
				bundle.putString("employId", strEmployId);
				bundle.putString("Designation", strDesignation);
				bundle.putString("companyName", strCompName);
				bundle.putString("Category", strCategory);
				bundle.putString("dateOfBirth", strDOB);
	        	
	        	mr.setArguments(bundle);
				
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ft.addToBackStack("mobile");
				ft.replace(R.id.frame_container, mr, "service").addToBackStack(null).commit();*/
//			}

        }

    }

    @Override
    public void onValidationSucceeded() {
        Fragment mr = new OfficialInformation();

        Bundle bundle = new Bundle();
        bundle.putString("firstName", strFirstName);
        bundle.putString("middleName", strMiddleName);
        bundle.putString("lastName", strLastName);
        bundle.putString("employId", strEmployId);
        bundle.putString("Designation", strDesignation);
        bundle.putString("companyName", strCompName);
        bundle.putString("Category", strCategory);
        bundle.putString("dateOfBirth", mydobstring);
        bundle.putString("strSpnrNationality", strSpnrNationality);

        mr.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("mobile");
        VMSConstants.mysysout("Integer.parseInt(Prefs.getString(\"UserId\", \"\"))  " + Prefs.getString("UserId", ""));
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
        startActivity(new Intent(getActivity(), VMSMainActivity.class));
    }
}
