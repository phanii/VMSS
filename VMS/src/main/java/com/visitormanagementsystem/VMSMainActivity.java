package com.visitormanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.pojos.Login;
import com.visitormanagementsystem.utils.MyCustomeToast;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import com.mingle.widget.ShapeLoadingDialog;

public class VMSMainActivity extends Activity implements OnClickListener, Validator.ValidationListener {
    PostClient postClient = new PostClient();
    Button btnScheduleappointment, btnLogin;
    TextView tvRegister, tvForgotPassword, tvActivateAccount;
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE)
    @Bind(R.id.password)
    EditText etPassword;
    @NotEmpty
    @Email
    @Bind(R.id.userName)
    EditText etUserName;
    ImageView fbImageView;
    @Bind(R.id.switch_lang)
    Switch switch_lang;
    ProgressDialog progressDialog;
    ScrollView scrollView1;
    Validator validator;
    //private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_vmsmain);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        //Integer.parseInt("sjbbdhcbdc");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
       /* shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("Loading...");*/

        progressDialog = new ProgressDialog(VMSMainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);

        btnScheduleappointment = (Button) findViewById(R.id.scheduleappointment);
        btnScheduleappointment.setOnClickListener(this);
        tvRegister = (TextView) findViewById(R.id.reg);
        tvForgotPassword = (TextView) findViewById(R.id.forgot_password);
        tvActivateAccount = (TextView) findViewById(R.id.activate_account);
        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setTypeface(font);
        fbImageView = (ImageView) findViewById(R.id.fb);
        btnLogin.setOnClickListener(this);
        tvActivateAccount.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        fbImageView.setOnClickListener(this);


    }


    @OnCheckedChanged(R.id.switch_lang)
    void onChecked(boolean checked) {
        if (checked) {
            VMSConstants.setLocale(this, "ar");
        } else {
            VMSConstants.setLocale(this, "en");
        }
        //Toast.makeText(this, checked ? "Checked!" : "Unchecked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_en)
      void local_en(Button en) {
        VMSConstants.setLocale(this, "en");
    }

    @OnClick(R.id.btn_ar)
      void local_ar(Button ar) {
        VMSConstants.setLocale(this, "ar");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Prefs.clear();
        //   LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("my-event"));
        this.registerReceiver(broadcastReceiver, new IntentFilter("my-event"));
        VMSConstants.mylog("onResume called");
    }

    @Override
    protected void onPause() {
        this.unregisterReceiver(broadcastReceiver);
        // LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
        VMSConstants.mylog("onPause() called");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            VMSConstants.mylog("receiver " + message);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vmsmain, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        final Intent intent;
        // TODO Auto-generated method stub
        if (v.equals(tvForgotPassword)) {
//			startActivity(new Intent(VMSMainActivity.this,ForgotPassword.class));
            intent = new Intent(VMSMainActivity.this, ServicesFragment.class);
            intent.putExtra("service", "forgotpassword");
            startActivity(intent);

           /* Intent intent1 = new Intent("my-event");
            intent1.putExtra("message", "data");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);*/

            Intent myIntent1 = new Intent("my-event");
            //  myIntent1.putExtra("message","fate");
            //  sendBroadcast(myIntent1);
            myIntent1.setAction("Fate");
            // sendOrderedBroadcast(myIntent1, null);

        } else if (v.equals(tvRegister)) {
//			startActivity(new Intent(VMSMainActivity.this,RegisterNewUser.class));
            intent = new Intent(VMSMainActivity.this, ServicesFragment.class);
            intent.putExtra("service", "registernewuser");
            startActivity(intent);
        } else if (v.equals(tvActivateAccount)) {
//			startActivity(new Intent(VMSMainActivity.this,ActivateYourAccount.class));
            intent = new Intent(VMSMainActivity.this, ServicesFragment.class);
            intent.putExtra("service", "activateyouraccount");
            startActivity(intent);
        } else if (v.equals(btnScheduleappointment)) {
//			startActivity(new Intent(VMSMainActivity.this,ScheduleAppointment.class));
            intent = new Intent(VMSMainActivity.this, ServicesFragment.class);
            intent.putExtra("service", "scheduleappointment");
            startActivity(intent);
        } else if (v.equals(btnLogin)) {

            validator.validate();
            // shapeLoadingDialog.show();

            /*
            OR
             */

/*            userCall.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Response<Login> response, Retrofit retrofit) {
                    Login myLogin = response.body();
                    Gson gson = new Gson();
                    if (response.isSuccess()) {
                        System.out.println(" MyLogin Details " + gson.toJson(myLogin) + "\n" + response.isSuccess() + "  \n" + response.raw() + "\n" + response.body());
                        Prefs.putString("access_token", myLogin.getAccess_token());
                        Prefs.putString("userRoleId", myLogin.getUserRoleId());
                        Prefs.putString("userName", myLogin.getUserName());
                        Prefs.putString("UserId", myLogin.getUserId());
                        startActivity(intent);

                        progressDialog.dismiss();
                        // shapeLoadingDialog.dismiss();
                    } else {
                        // shapeLoadingDialog.dismiss();
                        progressDialog.dismiss();
                        Toast.makeText(VMSMainActivity.this, R.string.tryagain, Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(VMSMainActivity.this, R.string.tryagain, Toast.LENGTH_SHORT).show();

                }
            });*/


        } else if (v.equals(fbImageView)) {

            MyCustomeToast.show(this, "Fb clickedFb clickedFb ", true, true);

/*



            final Observable<List<EmployeeDashBoardQuickActionsInformationPojo>> visitordetailsObservable =
                    postClient.myApiEndpointInterface.getEmployeeDashBoardQuickActionsInformationPojoRx
                            ("50055943-0ac8-47bc-b2eb-65992f0a5ede", "5", true, "", "", "", "");
            visitordetailsObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<EmployeeDashBoardQuickActionsInformationPojo>>() {
                @Override
                public void onCompleted() {
                    VMSConstants.mysysout("comp 1");
                }

                @Override
                public void onError(Throwable e) {
                    VMSConstants.mysysout("Err 1");
                }

                @Override
                public void onNext(List<EmployeeDashBoardQuickActionsInformationPojo> employeeDashBoardQuickActionsInformationPojos) {
                    VMSConstants.mysysout("next 1");
                    visitordetailsObservable.just(employeeDashBoardQuickActionsInformationPojos,employeeDashBoardQuickActionsInformationPojos)
                            .filter(new Func1<List<EmployeeDashBoardQuickActionsInformationPojo>, Boolean>() {
                                @Override
                                public Boolean call(List<EmployeeDashBoardQuickActionsInformationPojo> employeeDashBoardQuickActionsInformationPojos) {


                                    return employeeDashBoardQuickActionsInformationPojos.get(0).getActionCode().equalsIgnoreCase("105");
                                }
                            }).subscribe(new Subscriber<List<EmployeeDashBoardQuickActionsInformationPojo>>() {
                        @Override
                        public void onCompleted() {
                            VMSConstants.mysysout("on comp2");
                        }

                        @Override
                        public void onError(Throwable e) {
                            VMSConstants.mysysout("on err 2");
                        }

                        @Override
                        public void onNext(List<EmployeeDashBoardQuickActionsInformationPojo> employeeDashBoardQuickActionsInformationPojos) {
                            VMSConstants.mysysout("on next2 " + employeeDashBoardQuickActionsInformationPojos);
                        }
                    });
                }
            });*/


            // shapeLoadingDialog.show();
     /*       Prefs.clear();
            Intent intent1 = new Intent(this, QRReader.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            System.out.println("Prefs access_token: " + Prefs.getString("access_token", ""));*/
            // Crashlytics.logException(e );
            //  Locale current = getResources().getConfiguration().locale;
            //  VMSConstants.mylog("Current Language " + current.getLanguage());


            //VMSConstants.getmyCurrentLocale();
            //throw new RuntimeException("This is a crash");
            //shapeLoadingDialog.dismiss();

        /*    PostClient postClient = new PostClient();
            Call<Visitordetails> userCall= postClient.myApiEndpointInterface.getVisitorById("5c08cef9-643b-4d5f-a316-b6f255e567f7");

           userCall.enqueue(new Callback<Visitordetails>() {
               @Override
               public void onResponse(Response<Visitordetails> response, Retrofit retrofit)
               {
                   Visitordetails visitordetails=response.body();

                   System.out.println("  Final details "+visitordetails.getUserNameID());
               }

               @Override
               public void onFailure(Throwable t) {

               }
           });*/


        }

    }


    @Override
    public void onValidationSucceeded() {
        progressDialog.show();

        final Intent intent = new Intent(VMSMainActivity.this, GDMainActivity.class);
        intent.putExtra("service", "login");

        intent.putExtra("l_uname", etUserName.getText().toString());
        intent.putExtra("l_pwd", etPassword.getText().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Prefs.putString("UserId", "2");
        Prefs.putString("userRoleId", "4");
        Prefs.putString("userName", etUserName.getText().toString());
         startActivity(intent);


        // Call<Login> userCall = postClient.myApiEndpointInterface.UserLogin(etUserName.getText().toString(), etPassword.getText().toString(), "password");

        Observable<Login> loginObservable = postClient.myApiEndpointInterface.UserLogin(etUserName.getText().toString(), etPassword.getText().toString(), "password");
        loginObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Login>() {
            @Override
            public void onCompleted() {
                try {

                    VMSConstants.mylog("Completed called ");

                    progressDialog.dismiss();
                    startActivity(intent);
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                }
            }

            @Override
            public void onError(Throwable e) {
                   /* VMSConstants.mylog("err called");
                    Toast.makeText(VMSMainActivity.this, R.string.tryagain, Toast.LENGTH_SHORT).show();*/
                VMSConstants.ShowSnackbar(scrollView1, getResources().getString(R.string.tryagain));


                progressDialog.dismiss();
            }

            @Override
            public void onNext(Login login) {
                System.out.println("Login data " + new Gson().toJson(login));
                Prefs.putString("access_token", login.getAccess_token());
                Prefs.putString("userRoleId", login.getUserRoleId());
                Prefs.putString("userName", login.getUserName());
                Prefs.putString("UserId", login.getUserId());


                // progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);


            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
