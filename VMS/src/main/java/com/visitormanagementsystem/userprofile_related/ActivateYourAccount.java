package com.visitormanagementsystem.userprofile_related;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.pojos.ActivateAccountPojo;
import com.visitormanagementsystem.utils.PostClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//import com.mingle.widget.ShapeLoadingDialog;

public class ActivateYourAccount extends Fragment implements OnClickListener, Validator.ValidationListener {

    TextView tvHome, tvSendMail;
    ImageView sliderpanel_image, back;
    Fragment imr;

    //VMSUtilities iu;
    String strToMail;
    Validator validator;
    @Email(message = "Enter a Valid Email ")
    @Bind(R.id.eMail)
    EditText etEmail;
    ProgressDialog progressDialog;
   // private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activate_your_account, null);
        ButterKnife.bind(this, view);
        /*shapeLoadingDialog = new ShapeLoadingDialog(getActivity());
        shapeLoadingDialog.setLoadingText(getActivity().getString(R.string.loading));
        shapeLoadingDialog.setCanceledOnTouchOutside(false);*/


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);


        tvHome = (TextView) view.findViewById(R.id.home);

        tvSendMail = (TextView) view.findViewById(R.id.sendMail);


        tvSendMail.setOnClickListener(this);

        tvHome.setOnClickListener(this);

        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(this);
        //iu = new VMSUtilities(getActivity());
        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        strToMail = etEmail.getText().toString();
        // TODO Auto-generated method stub
        if (v.equals(tvHome)) {
            startActivity(new Intent(getActivity(), VMSMainActivity.class));
        }  else if (v.equals(back)) {
            startActivity(new Intent(getActivity(), VMSMainActivity.class));
        } else if (v.equals(tvSendMail)) {

            validator.validate();
        }
    }

    private void doinBackground() {
        //   shapeLoadingDialog.show();
        progressDialog.show();
        final PostClient postClient = new PostClient();
        ActivateAccountPojo activateAccount = new ActivateAccountPojo(etEmail.getText().toString());
        final Gson gson = new Gson();
        System.out.println(gson.toJson(activateAccount));
        Call<ActivateAccountPojo> activateAccountPojoCall = postClient.myApiEndpointInterface.activateAccount(activateAccount);
        activateAccountPojoCall.enqueue(new Callback<ActivateAccountPojo>() {
            @Override
            public void onResponse(Response<ActivateAccountPojo> response, Retrofit retrofit) {
                ActivateAccountPojo activateAccountPojo = response.body();
                if (response.isSuccess()) {
                    System.out.println(getClass().getName()+ "  " + response.raw() + "  " + response.errorBody() + " " + response.message());
                    // shapeLoadingDialog.dismiss();
                    Intent intent = new Intent(getActivity(), VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                // shapeLoadingDialog.dismiss();
                System.out.println("failed");
                Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
            }
        });

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
}
