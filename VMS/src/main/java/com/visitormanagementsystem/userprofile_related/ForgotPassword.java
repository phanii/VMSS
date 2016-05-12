package com.visitormanagementsystem.userprofile_related;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;
import com.visitormanagementsystem.pojos.Spinner_ValuesBean;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgotPassword extends Fragment implements OnClickListener {
    String nations_array[] = {"Select a Security Question", "What is your mothers name",
            "What is your first car colour", "What was the house number and street name you lived in as a child?",
            "What primary school did you attend?", "In what town or city was your first full time job?",
            "In what town or city did you meet your spouse/partner?", "What is the middle name of your oldest child?",
            "What is your grandmothers (on your mothers side) maiden name?",
            "What is your spouse or partners mothers maiden name?",
            "In what town or city did your mother and father meet?"};
    Spinner spQuestionType;
    TextView tvHome;
    ImageView  back;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.forgot_password, null);


        spQuestionType = (Spinner) view.findViewById(R.id.questionType);
        tvHome = (TextView) view.findViewById(R.id.home);
        tvHome.setOnClickListener(this);


        PostClient postClient = new PostClient();
        Observable<List<Spinner_ValuesBean>> listObservable = postClient.myApiEndpointInterface.getSecurityQuestions();
        listObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<Spinner_ValuesBean>>() {
            List<Spinner_ValuesBean> spinner_valuesBeanslist = new ArrayList<Spinner_ValuesBean>();
            ArrayList<String> stringArrayList = new ArrayList<String>();


            @Override
            public void onCompleted() {
                VMSConstants.mylog("oncompleted called");
                spQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String nationalityvalue = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onError(Throwable e) {
                VMSConstants.mylog("onError called");
            }

            @Override
            public void onNext(List<Spinner_ValuesBean> spinner_valuesBeans) {
                Gson gson = new Gson();
                try {

                    VMSConstants.mysysout(getClass().getName() + "\n" + gson.toJson(spinner_valuesBeans));

                    for (int i = 0; i < spinner_valuesBeans.size(); i++) {

                        spinner_valuesBeanslist.add(new Spinner_ValuesBean(spinner_valuesBeans.get(i).getTypeDetailsCode(),
                                spinner_valuesBeans.get(i).getTypeDetailValueLang1(), spinner_valuesBeans.get(i).getTypeDetailValueLang2()));
                        VMSConstants.mysysout("Local is "+String.valueOf(getActivity().getResources().getConfiguration().locale));
                        if (String.valueOf(getActivity().getResources().getConfiguration().locale).equalsIgnoreCase("en")) {
                            stringArrayList.add(spinner_valuesBeans.get(i).getTypeDetailValueLang1());

                        } else if (String.valueOf(getActivity().getResources().getConfiguration().locale).equalsIgnoreCase("ar")) {
                            stringArrayList.add(spinner_valuesBeans.get(i).getTypeDetailValueLang2());
                        }
                    }

                    ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                            stringArrayList);
                    spQuestionType.setAdapter(nations_adapter);

                } catch (Exception e) {
                    VMSConstants.mysysout("Error @ " + getClass().getName() + " " + e);
                }
            }
        });

        // ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
        //        nations_array);
        //nations_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //  spQuestionType.setAdapter(nations_adapter);

        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(this);

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


        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.equals(tvHome)) {
            startActivity(new Intent(getActivity(), VMSMainActivity.class));
        } else if (v.equals(back)) {
            startActivity(new Intent(getActivity(), VMSMainActivity.class));
        }
    }
}
