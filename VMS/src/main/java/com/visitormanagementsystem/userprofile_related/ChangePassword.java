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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//import com.mingle.widget.ShapeLoadingDialog;

public class ChangePassword extends Fragment implements OnClickListener, Validator.ValidationListener {
    Validator validator;
    TextView tvHome, tvConfirm;


    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    @Bind(R.id.cP_oldPassword)
    EditText cP_oldPassword;

    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    @Bind(R.id.cp_newPassword)
    EditText cp_newPassword;


    @Bind(R.id.cp_confirmPassword)
    EditText cp_confirmPassword;

    @NotEmpty
    @Email
    @Bind(R.id.cp_email)
    EditText cp_email;

    String strOldPwd, strNewPwd, strConfirmPwd, strEmail;
    Fragment fragment;
    //VMSUtilities iu;
    ProgressDialog progressDialog;
    //private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.change_password, null);
        ButterKnife.bind(this, view);
        /*shapeLoadingDialog = new ShapeLoadingDialog(getActivity());
        shapeLoadingDialog.setLoadingText(getActivity().getString(R.string.loading));
        shapeLoadingDialog.setCanceledOnTouchOutside(false);*/


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);


        tvHome = (TextView) view.findViewById(R.id.home);
        tvHome.setOnClickListener(this);
        tvHome.setVisibility(View.GONE);

        tvConfirm = (TextView) view.findViewById(R.id.confirm);
        tvConfirm.setOnClickListener(this);


//		fragment =  new Fragment();
//		fragment.getView().setOnKeyListener(new OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if( keyCode == KeyEvent.KEYCODE_BACK )
//		        {
//					startActivity(new Intent(getActivity(),VMSMainActivity.class));
//		            return true;
//		        }
//		        return false;
//				
//			}
//		});

      ///  iu = new VMSUtilities(getActivity());
        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        //RetrieveEditTextData();
        if (v.equals(tvHome)) {
            startActivity(new Intent(getActivity(), VMSMainActivity.class));
        } else if (v.equals(tvConfirm)) {
            // doinBackground();
            validator.validate();
        }
    }

    private void doinBackground() {
        progressDialog.show();

        PostClient postClient = new PostClient();

        com.visitormanagementsystem.pojos.ChangePassword changePassword =
                new com.visitormanagementsystem.pojos.ChangePassword(cP_oldPassword.getText().toString(), cp_email.getText().toString(), cp_newPassword.getText().toString(), cp_confirmPassword.getText().toString());

        Call<com.visitormanagementsystem.pojos.ChangePassword> userCall = postClient.myApiEndpointInterface.changePassword(changePassword);

        userCall.enqueue(new Callback<com.visitormanagementsystem.pojos.ChangePassword>() {
            @Override
            public void onResponse(Response<com.visitormanagementsystem.pojos.ChangePassword> response, Retrofit retrofit) {
                com.visitormanagementsystem.pojos.ChangePassword cpd = response.body();
                Gson gson = new Gson();
                if (response.isSuccess()) {
                    VMSConstants.mysysout(getClass().getName() + "\n" + gson.toJson(cpd));
                    // shapeLoadingDialog.dismiss();
                    progressDialog.dismiss();
                    Intent intent = new Intent(getActivity(), VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
                System.out.println("  onFailure called ");
            }
        });
        // TODO Auto-generated method stub
    /*    String server_mobile_response = iu.changePassword(strOldPwd, strNewPwd, strConfirmPwd, strEmail);

        Log.i("VMS", "response : " + server_mobile_response);
        Toast.makeText(getActivity(), server_mobile_response.toString(), Toast.LENGTH_LONG).show();*/
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


    private void RetrieveEditTextData() {
        // TODO Auto-generated method stub

        strOldPwd = cP_oldPassword.getText().toString();
        strNewPwd = cp_newPassword.getText().toString();
        strConfirmPwd = cp_confirmPassword.getText().toString();
        strEmail = cp_email.getText().toString();

    }

    @Override
    public void onValidationSucceeded() {
        System.out.println(cP_oldPassword.getText().toString());
        if (cp_newPassword.getText().toString().equalsIgnoreCase(cp_confirmPassword.getText().toString())) {
            doinBackground();

        } else {
            Toast.makeText(getActivity(), "Passwords are not same ", Toast.LENGTH_SHORT).show();
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
}



