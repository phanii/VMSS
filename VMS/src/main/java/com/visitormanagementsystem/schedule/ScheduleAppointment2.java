package com.visitormanagementsystem.schedule;

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
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.pojos.BookAnAppointment;
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

public class ScheduleAppointment2 extends Fragment implements OnClickListener, Validator.ValidationListener {
    private static final String TAG = "";
    private static final int REQUEST_CODE = 256;

    ArrayList<Spinner_ValuesBean> dept_array;// = new ArrayList<>();
    ArrayList<Spinner_ValuesBean> sub_dept_array;//= new ArrayList<>();
    ArrayList<Spinner_ValuesBean> reasonForVisit_array;
    //String dept_array[] = {"Department Name", "India", "Saudi"};
    //  String sub_dept_array[] = {"sub Department Name", "Central", "East", "West", "North"};
   // String reasonForVisit_array[] = {"Reason For Visit", "Official", "Interview", "Meeting"};
    Validator validator;
    TextView tvNext, tvPrevious;
    ImageView sliderpanel_image, back;
    String sch_firstName, sch_middleName, sch_lastName, sch_employId, sch_Designation, sch_companyName, sch_Category, sch_dateOfBirth, sch_strSpnrNationality, sch_workPermitNo,
            sch_visitVisaNo, sch_idType, sch_IdNo, sch_mobileNo, sch_landlineNo, sch_sextype;
    @NotEmpty
    @Bind(R.id.sch2_firstName)
    EditText sch2_firstName;
    @NotEmpty
    @Bind(R.id.sch2_middleName)
    EditText sch2_middleName;
    @NotEmpty
    @Length(min = 10, max = 10)
    @Bind(R.id.sch2_mobNo)
    EditText sch2_mobNo;
    @Bind(R.id.sch2_landlineNo)
    EditText sch2_landlineNo;
    @NotEmpty
    @Email
    @Bind(R.id.sch2_eamil)
    EditText sch2_eamil;
    @Bind(R.id.sch2_reasonForVisit)
    Spinner sch2_reasonForVisit;
    @Bind(R.id.sch2_othinfo1)
    EditText sch2_othinfo1;
    @Bind(R.id.sch2_othinfo2)
    EditText sch2_othinfo2;
    @Bind(R.id.sch2_deptName)
    Spinner sch2_deptName;
    @Bind(R.id.sch2_subDeptName)
    Spinner sch2_subDeptName;
    @Bind(R.id.profileImage)
    ImageView profileImage;
    @Bind(R.id.captureImage)
    ImageView captureImage;
    @Bind(R.id.uploadImage)
    ImageView uploadImage;
    SharedPreferences sharedPreferences;
    String deptName, subdeptName, reasonforvisit;
    PostClient postClient = new PostClient();
    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.schedule_appointment2, null);
        ButterKnife.bind(this, view);

        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }

        validator = new Validator(this);
        validator.setValidationListener(this);
        dept_array = new ArrayList<>();
        sub_dept_array = new ArrayList<>();
        reasonForVisit_array=new ArrayList<>();
        dept_array.clear();
        sub_dept_array.clear();
       /* Bundle bundle = getArguments();
        sch_firstName = bundle.getString("sch_firstName");
        sch_middleName = bundle.getString("sch_middleName");

        sch_lastName = bundle.getString("sch_lastName");
        sch_employId = bundle.getString("sch_employId");

        sch_Designation = bundle.getString("sch_Designation");
        sch_companyName = bundle.getString("sch_companyName");

        sch_Category = bundle.getString("sch_Category");
        sch_dateOfBirth = bundle.getString("sch_dateOfBirth");

        sch_strSpnrNationality = bundle.getString("sch_strSpnrNationality");
        sch_workPermitNo = bundle.getString("sch_workPermitNo");

        sch_visitVisaNo = bundle.getString("sch_visitVisaNo");
        sch_idType = bundle.getString("sch_idType");

        sch_IdNo = bundle.getString("sch_IdNo");
        sch_mobileNo = bundle.getString("sch_mobileNo");

        sch_landlineNo = bundle.getString("sch_landlineNo");
        sch_sextype = bundle.getString("sch_sextype");*/
//captureImage
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String photopath = Prefs.getString("photopath", "nopath");
        if (!photopath.equalsIgnoreCase("nopath")) {
            File file = new File(photopath);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                profileImage.setImageBitmap(bitmap);
            }

        }




        Call<List<Spinner_ValuesBean>> GetDepartmentName_listCall = postClient.myApiEndpointInterface.getSPINNER_GetDepartmentName_OBSERVABLE();
        GetDepartmentName_listCall.enqueue(new Callback<List<Spinner_ValuesBean>>() {
            @Override
            public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {
                VMSConstants.mylog("Size " + String.valueOf(response.body().size()) + "\n" + response.raw());

                ArrayList<String> stringArrayList = new ArrayList<String>();

                stringArrayList.clear();
               // stringArrayList.add("  Select Dept ");
                for (int i = 0; i < response.body().size(); i++) {
                    dept_array.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                            response.body().get(i).getTypeDetailValueLang2()));

                    if (MyApplication.mylanguage.equalsIgnoreCase("en")) {
                        stringArrayList.add(dept_array.get(i).getTypeDetailValueLang1());

                    } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")) {
                        stringArrayList.add(dept_array.get(i).getTypeDetailValueLang2());
                    }
                }
                ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                        stringArrayList);
                nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                sch2_deptName.setAdapter(nations_adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        sch2_deptName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptName = parent.getItemAtPosition(position).toString();
                System.out.println("spIdTypevalue " + deptName + "  " + dept_array.get(position).getTypeDetailsCode());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Call<List<Spinner_ValuesBean>> GetSubDepartmentName_listCall = postClient.myApiEndpointInterface.getSPINNER_GetSubDepartmentName_OBSERVABLE();
        GetSubDepartmentName_listCall.enqueue(new Callback<List<Spinner_ValuesBean>>() {
            @Override
            public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {
                VMSConstants.mylog("Size " + String.valueOf(response.body().size()) + "\n" + response.raw());

                ArrayList<String> stringArrayList = new ArrayList<String>();

                stringArrayList.clear();
               // stringArrayList.add("  Select ID  ");
                for (int i = 0; i < response.body().size(); i++) {
                    sub_dept_array.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                            response.body().get(i).getTypeDetailValueLang2()));

                    if (MyApplication.mylanguage.equalsIgnoreCase("en")) {
                        stringArrayList.add(sub_dept_array.get(i).getTypeDetailValueLang1());

                    } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")) {
                        stringArrayList.add(sub_dept_array.get(i).getTypeDetailValueLang2());
                    }
                }
                ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                        stringArrayList);
                nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                sch2_subDeptName.setAdapter(nations_adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        sch2_subDeptName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subdeptName = parent.getItemAtPosition(position).toString();
                System.out.println("sch2_subDeptName " + subdeptName + " position   " + position);
              /*  if(sub_dept_array.size()>0)
                {
                System.out.println("sch2_subDeptName " + subdeptName + " position   " +position+"  "+ sub_dept_array.get(position));//.getTypeDetailsCode());

                }
                else
                {
                    VMSConstants.mysysout(String.valueOf(sub_dept_array.size()));
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Call<List<Spinner_ValuesBean>> getSPINNER_PurposeForVisit_OBSERVABLE_listCall = postClient.myApiEndpointInterface.getSPINNER_PurposeForVisit_OBSERVABLE();
        getSPINNER_PurposeForVisit_OBSERVABLE_listCall.enqueue(new Callback<List<Spinner_ValuesBean>>() {
            @Override
            public void onResponse(Response<List<Spinner_ValuesBean>> response, Retrofit retrofit) {
                VMSConstants.mylog("Size " + String.valueOf(response.body().size()) + "\n" + response.raw());

                ArrayList<String> stringArrayList = new ArrayList<String>();

                stringArrayList.clear();
                // stringArrayList.add("  Select Dept ");
                for (int i = 0; i < response.body().size(); i++) {
                    reasonForVisit_array.add(new Spinner_ValuesBean(response.body().get(i).getTypeDetailsCode(), response.body().get(i).getTypeDetailValueLang1(),
                            response.body().get(i).getTypeDetailValueLang2()));

                    if (MyApplication.mylanguage.equalsIgnoreCase("en")) {
                        stringArrayList.add(reasonForVisit_array.get(i).getTypeDetailValueLang1());

                    } else if (MyApplication.mylanguage.equalsIgnoreCase("ar")) {
                        stringArrayList.add(reasonForVisit_array.get(i).getTypeDetailValueLang2());
                    }
                }
                ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                        stringArrayList);
                nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                sch2_reasonForVisit.setAdapter(nations_adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        sch2_reasonForVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reasonforvisit = parent.getItemAtPosition(position).toString();
                VMSConstants.mysysout(reasonforvisit + " " + reasonForVisit_array.get(position).getTypeDetailsCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvNext = (TextView) view.findViewById(R.id.next);
        tvNext.setOnClickListener(this);
        tvPrevious = (TextView) view.findViewById(R.id.home);
        tvPrevious.setOnClickListener(this);

        return view;
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
                Prefs.putString("photopath", finalFile.getAbsolutePath());

                //editor.commit();

                System.out.println("photo path :" + finalFile.getAbsolutePath());
                profileImage.setImageBitmap(image);
            } else {
                Toast.makeText(getActivity(), "Please capture image", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == REQUEST_CODE && null != data) {

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
        if (v.equals(tvNext)) {
            validator.validate();
        } else if (v.equals(tvPrevious)) {
            getActivity().getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onValidationSucceeded() {
        Fragment mr = new ScheduleAppointment3();
       /* Bundle bundle = new Bundle();

        BookAnAppointment bookAnAppointment = new BookAnAppointment();
        bookAnAppointment.setPersonalBelongings(sch2_othinfo1.getText().toString());
        bundle.putString("sch_firstName", sch_firstName);
        bundle.putString("sch_middleName", sch_middleName);
        bundle.putString("sch_lastName", sch_lastName);
        bundle.putString("sch_employId", sch_employId);
        bundle.putString("sch_Designation", sch_Designation);
        bundle.putString("sch_companyName", sch_companyName);
        bundle.putString("sch_Category", sch_Category);
        bundle.putString("sch_dateOfBirth", sch_dateOfBirth);
        bundle.putString("sch_strSpnrNationality", sch_strSpnrNationality);
        bundle.putString("sch_workPermitNo", sch_workPermitNo);
        bundle.putString("sch_visitVisaNo", sch_visitVisaNo);
        bundle.putString("sch_idType", sch_idType);
        bundle.putString("sch_IdNo", sch_IdNo);
        bundle.putString("sch_mobileNo", sch_mobileNo);
        bundle.putString("sch_landlineNo", sch_landlineNo);
        bundle.putString("sch_sextype", sch_sextype);


        bundle.putString("sch2_firstName", sch2_firstName.getText().toString());
        bundle.putString("sch2_middleName", sch2_middleName.getText().toString());

        bundle.putString("sch2_deptName", deptName);
        bundle.putString("sch2_subDeptName", subdeptName);


        bundle.putString("sch2_mobNo", sch2_mobNo.getText().toString());
        bundle.putString("sch2_landlineNo", sch2_landlineNo.getText().toString());
        bundle.putString("sch2_eamil", sch2_eamil.getText().toString());

        bundle.putString("sch2_reasonForVisit", reasonforvisit);

        bundle.putString("sch2_othinfo1", sch2_othinfo1.getText().toString());
        bundle.putString("sch2_othinfo2", sch2_othinfo2.getText().toString());*/
        BookAnAppointment sch2BookAnAppointment = new BookAnAppointment(sch2_firstName.getText().toString(), sch2_middleName.getText().toString(), deptName, subdeptName,
                sch2_mobNo.getText().toString(), sch2_eamil.getText().toString(), reasonforvisit, sch2_othinfo1.getText().toString(), sch2_othinfo2.getText().toString());
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SCH_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Prefs.putString("SCH_SCR2", gson.toJson(sch2BookAnAppointment));
        // editor.commit();


        //mr.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("mobile");
        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            ft.replace(R.id.frame_container1, mr, "service").addToBackStack(null).commit();
        }
        else {
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
