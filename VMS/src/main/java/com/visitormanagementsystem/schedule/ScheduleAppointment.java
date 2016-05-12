package com.visitormanagementsystem.schedule;

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
import android.provider.MediaStore.MediaColumns;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.ServicesFragment;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.pojos.BookAnAppointment;
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
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.visitormanagementsystem.utils.VMSConstants.convertFileToBase64String;

public class ScheduleAppointment extends Fragment implements OnClickListener, Validator.ValidationListener {
    private static final String TAG = "Browse a file ";
    private static final int CAMERA_PIC_REQUEST = 2500;
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int RESULT_LOAD_FILE = 150;
    private static final int REQUEST_CODE = 256;
    static ImageView ivCaptureImage, ivProfileImage, ivUploadImage;
    Validator validator;

    SharedPreferences sharedPreferences;
    Date dobdate;
    java.sql.Date MysqlDateOfBirth;
    String questions_array[] = {"What is your mothers name", "What is your first car colour",
            "What was the house number and street name you lived in as a child?", "What primary school did you attend?",
            "In what town or city was your first full time job?",
            "In what town or city did you meet your spouse/partner?", "What is the middle name of your oldest child?",
            "What is your grandmothers (on your mothers side) maiden name?",
            "What is your spouse or partners mothers maiden name?",
            "In what town or city did your mother and father meet?"};
    ArrayList<Spinner_ValuesBean> nations_array;// = new ArrayList<>();
    ArrayList<Spinner_ValuesBean> idTypeArray;


    /*= {"Select Nationality", "Indian", "American", "Saudi", "Australian", "Banglashi",
    "Brazilian", "British", "Canadian", "Chinese", "Colombian", "Dutch", "Egyptian", "Indonesian", "Italian",
    "Mexican"};*/
   /* String idTypeArray[] = {"Identification Type", "Driving Licence", "PAN", "Adhar Number", "Passport Number",
            "Voter Card Number"};*/
    TextView tvNext;
    EditText etDob;
    ImageView sliderpanel_image, back;
    RelativeLayout ll_attach_file;
    @NotEmpty
    @Bind(R.id.firstName)
    EditText firstname;
    @NotEmpty
    @Bind(R.id.middleName)
    EditText middleName;

    @Bind(R.id.attachfile_path)
    TextView attachfile_path;
    @NotEmpty
    @Bind(R.id.lastName)
    EditText lastName;

    @NotEmpty
    @Length(min = 10, max = 10)
    @Bind(R.id.mobNo)
    EditText mobNo;

    @NotEmpty
    @Bind(R.id.sch_workPermitNo)
    EditText sch_workPermitNo;

    @Bind(R.id.designation)
    EditText designation;
    @Bind(R.id.compName)
    EditText compName;

    @Bind(R.id.category)
    EditText category;
    @Bind(R.id.dob)
    EditText dob;

    @Bind(R.id.nationality)
    Spinner spNatinality;


    @Bind(R.id.landlineNo)
    EditText landlineNo;


    @Bind(R.id.sch_visanumber)
    EditText sch_visanumber;

    @Bind(R.id.idType)
    Spinner spIdType;


    @Bind(R.id.idtype_value)
    EditText idtype_value;

    @Bind(R.id.myRadioGroup)
    RadioGroup myRadioGroup;
  /*
    @Bind(R.id.selectquestion)
    Spinner selectquestion;*/

    String sexType = null;
    String nationalityvalue, spIdTypevalue, myDobstring;
    PostClient postClient = new PostClient();

    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.schedule_appointment, null);
        ButterKnife.bind(this, view);
        getActivity().setTitle("New Appointment");

        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }

        idTypeArray = new ArrayList<>();
        nations_array = new ArrayList<>();

        if (idTypeArray.size() > 0 || nations_array.size() > 0) {
            VMSConstants.mysysout("array size " + idTypeArray.size() + "  " + nations_array.size());
            idTypeArray.clear();
            nations_array.clear();
        }

        ivCaptureImage = (ImageView) view.findViewById(R.id.captureImage);
        ivProfileImage = (ImageView) view.findViewById(R.id.profileImage);
        ivUploadImage = (ImageView) view.findViewById(R.id.uploadImage);
        ll_attach_file = (RelativeLayout) view.findViewById(R.id.ll_attach_file);
        ll_attach_file.setOnClickListener(this);
        // sliderpanel_image = (ImageView) view.findViewById(R.id.sliderpanel_image);

        int val = Integer.parseInt(Prefs.getString("userRoleId", "0"));
        System.out.println("VAL " + val);
   /*     if (val > 0) {
            sliderpanel_image.setVisibility(View.VISIBLE);
        } else {
            sliderpanel_image.setVisibility(View.GONE);
        }*/

        //  sliderpanel_image.setOnClickListener(this);

        //   back = (ImageView) view.findViewById(R.id.back);
        //  back.setOnClickListener(this);
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        //Toast.makeText(getActivity(),"male",Toast.LENGTH_SHORT).show();
                        sexType = "male";
                        System.out.println(" date_picker_text " + sexType);
                        break;
                    case R.id.female:
                        sexType = "female";
                        System.out.println(" date_picker_text " + sexType);
                        //Toast.makeText(getActivity(),"female",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        ivUploadImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                openfilemanager();

            }
        });

        ivCaptureImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });




/*

getSPINNER_ID_OBSERVABLE
 */


        Call<List<Spinner_ValuesBean>> spinner_valuesBeanCall = postClient.myApiEndpointInterface.getSPINNER_ID_OBSERVABLE();
        spinner_valuesBeanCall.enqueue(new Callback<List<Spinner_ValuesBean>>()

                                       {
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
                                       }

        );


        spIdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                           {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   spIdTypevalue = parent.getItemAtPosition(position).toString();
                                                   VMSConstants.mysysout(spIdTypevalue + "  Position " + position + "  " + idTypeArray.get(position));

                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           }

        );

        /*

        get nationality spinner
         */


        Call<List<Spinner_ValuesBean>> spinner_nationalityListCall = postClient.myApiEndpointInterface.getSPINNER_NATIONALITY_OBSERVABLE();

        spinner_nationalityListCall.enqueue(new Callback<List<Spinner_ValuesBean>>()

                                            {
                                                @Override
                                                public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {
                                                    VMSConstants.mylog("Size " + String.valueOf(response.body().size()) + "\n" + response.raw());

                                                    ArrayList<String> stringArrayList = new ArrayList<String>();

                                                    stringArrayList.clear();
                                                    // stringArrayList.add("Select Nationality");
                                                    for (int i = 0; i < response.body().size(); i++) {
                                                        nations_array.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                                                                response.body().get(i).getTypeDetailValueLang2()));

                                                        if (MyApplication.mylanguage.equalsIgnoreCase("en")) {
                                                            stringArrayList.add(nations_array.get(i).getTypeDetailValueLang1());

                                                        } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")) {
                                                            stringArrayList.add(nations_array.get(i).getTypeDetailValueLang2());
                                                        }
                                                    }
                                                    ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                                                            stringArrayList);
                                                    nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                                                    spNatinality.setAdapter(nations_adapter);

                                                }

                                                @Override
                                                public void onFailure(Throwable t) {
                                                    VMSConstants.mylog("onFailure");
                                                }
                                            }

        );


        spNatinality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                               {
                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                       nationalityvalue = parent.getItemAtPosition(position).toString();

                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {

                                                   }
                                               }

        );
        etDob = (EditText) view.findViewById(R.id.dob);
        tvNext = (TextView) view.findViewById(R.id.next);
        tvNext.setOnClickListener(this);
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String dobstrigformat = null;
                String myFormat = "yyyy-MM-dd'T'HH:mm:ss"; // In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                try {
                    Date date = sdf.parse(sdf.format(myCalendar.getTime()));
                    dobdate = date;
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                    System.out.println("********************\n" + sdf1.format(date) + "\n***************");


                    dobstrigformat = sdf.format(dobdate);
                    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new PostClient.DateDeserializer()).create();
                    String _date = "\"" + sdf1.format(date) + "\"";

                    System.out.println("date: before  " + date);

                    Date test = gson.fromJson(_date, Date.class);
                    System.out.println("date: after " + test);
                    MysqlDateOfBirth = new java.sql.Date(test.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String displaydateformat = "dd MMM yyyy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
                String date_from_date = sdf1.format(myCalendar.getTime());
                etDob.setText(date_from_date);
                myDobstring = date_from_date;
                // MysqlDateOfBirth = new java.sql.Date(displaydate.getTime());
                System.out.println("DOB MysqlDate  " + MysqlDateOfBirth);
            }

        };

        etDob.setOnClickListener(new

                                         OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                 // TODO Auto-generated method stub
                                                 new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                         myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                             }
                                         }

        );
        validator = new

                Validator(this);

        validator.setValidationListener(this);
        return view;
    }

/*
    @OnItemSelected(R.id.selectquestion)
    void onItemSelected(int position) {
        VMSConstants.mysysout(selectquestion.getItemAtPosition(position).toString() + "  " + sqTypeArray.get(position).toString());

    }*/

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.equals(tvNext)) {
            validator.validate();


            // startActivity(new
            // Intent(getActivity(),ScheduleAppointment2.class));
        } else if (v.equals(ivCaptureImage)) {
            //System.out.println(middleName.getText().toString());
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } else if (v.equals(sliderpanel_image)) {
            ServicesFragment.mSlidingPanel.openPane();
        } else if (v.equals(ll_attach_file)) {


            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            //startActivityForResult(i, RESULT_LOAD_FILE);

            openfilemanagerforall();
        }
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {

            if (!data.equals(null)) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getActivity(), image);
                File finalFile = new File(getRealPathFromURI(tempUri));
                sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                Prefs.putString("photopath", finalFile.getAbsolutePath());

                //  editor.commit();

                System.out.println("photo path :" + finalFile.getAbsolutePath());
                ivProfileImage.setImageBitmap(image);
            } else {
                Toast.makeText(getActivity(), "Please capture image", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Cursor cursor = getContentResolver().query(selectedImage,
            // filePathColumn, null, null, null);
            // cursor.moveToFirst();
            //
            // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            // String picturePath = cursor.getString(columnIndex);
            // cursor.close();
            //
            // ivProfileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ivProfileImage.setImageURI(selectedImage);

        }
        //REQUEST_CODE  221
        //
        else if (requestCode == REQUEST_CODE && null != data) {

            final Uri uri = data.getData();
            Log.i(TAG, "Uri = " + uri.toString());
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
                    ivProfileImage.setImageBitmap(bitmap);
                }
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                Prefs.putString("photopath", file.getAbsolutePath());


                // editor.commit();

            } catch (Exception e) {
                Log.e("FileSelectorTestActivity", "File select error", e);
            }

            //ivProfileImage.setImageURI(selectedImage);

        } else if (requestCode == RESULT_LOAD_FILE && null != data) {
            final Uri uri = data.getData();

            try {
                // Get the file path from the URI
                final String path = FileUtils.getPath(getActivity(), uri);


                File file = new File(path);

             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);
             */

                convertFileToBase64String(file.getAbsolutePath());

                Prefs.putString("attachfilepath", file.getAbsolutePath());
                VMSConstants.mysysout("attachfilepath  " + file.getAbsolutePath());

                attachfile_path.setText(file.getAbsolutePath().toString());
                // editor.commit();

            } catch (Exception e) {
                Log.e("FileSelectorTestActivity", "File select error", e);
            }

        }

    }

    public void openfilemanager() {

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

    public void openfilemanagerforall() {

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, RESULT_LOAD_FILE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
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

    public String getPath(Uri uri) {
        String[] projection = {MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else
            return uri.getPath();
    }

    @Override
    public void onValidationSucceeded() {
        System.out.println("  middle name " + middleName.getText().toString());
        Fragment mr = new ScheduleAppointment2();

       /* Bundle bundle = new Bundle();
        bundle.putString("sch_firstName", firstname.getText().toString());
        bundle.putString("sch_middleName", middleName.getText().toString());
        bundle.putString("sch_lastName", lastName.getText().toString());
        bundle.putString("sch_employId", middleName.getText().toString());
        bundle.putString("sch_Designation", designation.getText().toString());
        bundle.putString("sch_companyName", compName.getText().toString());
        bundle.putString("sch_Category", category.getText().toString());
        bundle.putString("sch_dateOfBirth", dob.getText().toString());
        bundle.putString("sch_strSpnrNationality", nationalityvalue);
        bundle.putString("sch_workPermitNo", sch_workPermitNo.getText().toString());
        bundle.putString("sch_visitVisaNo", sch_visanumber.getText().toString());
        bundle.putString("sch_idType", spIdTypevalue);
        bundle.putString("sch_IdNo", idtype_value.getText().toString());
        bundle.putString("sch_mobileNo", mobNo.getText().toString());
        bundle.putString("sch_landlineNo", landlineNo.getText().toString());
        bundle.putString("sch_sextype", sexType);*/
        BookAnAppointment bookAnAppointment = new BookAnAppointment(firstname.getText().toString(), firstname.getText().toString(), middleName.getText().toString(),
                lastName.getText().toString(), "New Appointment", "000", compName.getText().toString(), designation.getText().toString(), nationalityvalue, category.getText().toString(),
                myDobstring, sexType, sch_workPermitNo.getText().toString(), sch_visanumber.getText().toString(), mobNo.getText().toString(),
                landlineNo.getText().toString(), spIdTypevalue, idtype_value.getText().toString());
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SCH_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Prefs.putString("SCH_SCR1", gson.toJson(bookAnAppointment));
        // editor.commit();


        // mr.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("mobile");

        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            ft.replace(R.id.frame_container1, mr, "service").addToBackStack(null).commit();
        }
        else
        {
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
    public void backevent(ImageView back) {
        startActivity(new Intent(getActivity(), VMSMainActivity.class));
    }
}
