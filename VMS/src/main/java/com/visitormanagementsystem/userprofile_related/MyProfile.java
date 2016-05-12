package com.visitormanagementsystem.userprofile_related;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.pojos.Visitordetails;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MyProfile extends Fragment implements OnClickListener {
    @Bind(R.id.pf_category)
    TextView pf_category;
    @Bind(R.id.pf_companyName)
    TextView pf_companyName;

    @Bind(R.id.pf_designation)
    TextView pf_designation;

    @Bind(R.id.pf_dob)
    TextView pf_dob;


    @Bind(R.id.pf_employId)

    TextView pf_employId;

    @Bind(R.id.pf_gender)
    TextView pf_gender;

    @Bind(R.id.pf_idNumber)
    TextView pf_idNumber;

    @Bind(R.id.pf_idType)
    TextView pf_idType;


    @Bind(R.id.pf_landLineNumber)
    TextView pf_landLineNumber;
    @Bind(R.id.pf_mobileNumber)
    TextView pf_mobileNumber;

    @Bind(R.id.pf_name)
    TextView pf_name;

    @Bind(R.id.pf_nationality)
    TextView pf_nationality;

    @Bind(R.id.pf_userId)
    TextView pf_userId;
    @Bind(R.id.pf_visitVisaNumber)
    TextView pf_visitVisaNumber;
    @Bind(R.id.pf_workPermitNumber)
    TextView pf_workPermitNumber;
    @Bind(R.id.myprofileImage)
    ImageView myprofileImage;
    TextView tvEditProfile;
    ImageView sliderpanel_image, back;
    Fragment imr;
    EditText etName, etEmployId, etCompanyName, etDesignation, etNationality, etCategory, etDob, etGender, etVisitVisaNmuber, etWorkPermitNumber, etIdType,
            etIdNumber, etMobileNumber, etLandlineNmuber;
    String strName, strEmployId, strCompanyName, strDesignation, strNationality, strCategory, strDob, strGender, strVisitVisaNmuber, strWorkPermitNumber, strIdType,
            strIdNumber, strMobileNumber, strLandlineNmuber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.my_profile, null);
        ButterKnife.bind(this, view);
        tvEditProfile = (TextView) view.findViewById(R.id.editProfile);
        tvEditProfile.setOnClickListener(this);
        getActivity().setTitle("My Profile");


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        PostClient postClient = new PostClient();
        Call<Visitordetails> userCall = postClient.myApiEndpointInterface.getVisitorById(Prefs.getString("UserId", ""));

        userCall.enqueue(new Callback<Visitordetails>() {
            @Override
            public void onResponse(Response<Visitordetails> response, Retrofit retrofit) {
                final Visitordetails visitordetails = response.body();

                Gson gson = new Gson();

                System.out.println(getClass().getName() + "  " + " response.body() " + response.isSuccess() + " \n " + response.raw());
             /*   final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                pf_employId.setText(visitordetails.getEmployeeID().toString());

                    }
                }, 100);*/
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (visitordetails.getVisitorName() != null) {
                                pf_name.setText("Hello " + visitordetails.getVisitorName().toString());

                            }
                            if (visitordetails.getEmployeeID() != null) {
                                pf_employId.setText(visitordetails.getEmployeeID().toString());

                            }
                            if (visitordetails.getCompanyName() != null) {

                                pf_companyName.setText(visitordetails.getCompanyName().toString());
                            }
                            if (visitordetails.getDesignation() != null) {

                                pf_designation.setText(visitordetails.getDesignation().toString());
                            }
                            if (visitordetails.getNationality() != null) {
                                pf_nationality.setText(visitordetails.getNationality().toString());

                            }
                            pf_category.setText(visitordetails.getSectorCompCategory().toString());
                            if (visitordetails.getDOB() != null) {

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
                                Date formattedDOBdate = null;
                                try {
                                    formattedDOBdate = sdf.parse(visitordetails.getDOB().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String formattedDOBTime = output.format(formattedDOBdate);
                                if (formattedDOBTime != null) {
                                    pf_dob.setText(formattedDOBTime);

                                }
                            }
                            if (visitordetails.getGender() != null) {
                                pf_gender.setText(visitordetails.getGender().toString());
                            }
                            if (visitordetails.getVisitVisaNumber() != null) {
                                pf_visitVisaNumber.setText(visitordetails.getVisitVisaNumber().toString());

                            }
                            if (visitordetails.getWorkPermitNumber() != null) {
                                pf_workPermitNumber.setText(visitordetails.getWorkPermitNumber().toString());

                            }
                            if (visitordetails.getNationalIDType() != null) {
                                pf_idType.setText(visitordetails.getNationalIDType().toString());

                            }
                            if (visitordetails.getNationalID() != null) {

                                pf_idNumber.setText(visitordetails.getNationalID().toString());
                            }
                            if (visitordetails.getMobileNumber() != null) {
                                pf_mobileNumber.setText(visitordetails.getMobileNumber().toString());

                            }
                            if (visitordetails.getLandlinePhoneNumber() != null) {
                                pf_landLineNumber.setText(visitordetails.getLandlinePhoneNumber().toString());

                            }
                            if (visitordetails.getVisitorId() != null) {

                                pf_userId.setText(visitordetails.getVisitorId().toString());
                            }
                            if (visitordetails.getPhoto() != null) {
                                String photostring = visitordetails.getPhoto().toString();
                                Prefs.putString("nav_userpic", photostring);

                                byte[] imageAsBytes = Base64.decode(photostring.getBytes(), Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                myprofileImage.setImageBitmap(bitmap);

                            }

                            Visitordetails visitordetails1 = new Visitordetails(visitordetails.getVisitorName(), visitordetails.getId(), visitordetails.getVisitorId(), visitordetails.getEmployeeID(), visitordetails.getCompanyName(), visitordetails.getDesignation(), visitordetails.getNationality(),
                                    visitordetails.getSectorCompCategory(), visitordetails.getDOB(), visitordetails.getGender(), visitordetails.getVisitVisaNumber(),
                                    visitordetails.getWorkPermitNumber(), visitordetails.getNationalIDType(), visitordetails.getNationalID(), visitordetails.getMobileNumber()
                                    , visitordetails.getLandlinePhoneNumber(), visitordetails.getPhoto());

                            Gson gson = new Gson();
                            String s = gson.toJson(visitordetails1);
                            Prefs.putString("ProfileVisitorId", visitordetails.getVisitorId());
                            Prefs.putString("ProfileId", visitordetails.getId());
                            //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profiledata", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            //  String id = "" + visitordetails1.getEmployeeID();

                            Prefs.putString("profile_id", s);

                            // editor.commit();


                        }
                    });
                } catch (NullPointerException e) {
                    VMSConstants.mysysout("Error " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*if(v.equals(tvHome))
        {
			startActivity(new Intent(getActivity(),VMSMainActivity.class));
		}*/
        if (v.equals(tvEditProfile)) {
            Fragment imr = new EditProfile();
            if (imr != null) {
                FragmentManager fmr = getFragmentManager();
                fmr.beginTransaction().replace(R.id.frame_container, imr).addToBackStack(null).commit();
            }

        }
    }
}
