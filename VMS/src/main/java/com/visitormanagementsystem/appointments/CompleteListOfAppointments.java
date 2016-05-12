package com.visitormanagementsystem.appointments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.adapters.Recy_ItemAdapter;
import com.visitormanagementsystem.pojos.AppointmentListByVisitorId;
import com.visitormanagementsystem.utils.PostClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Phani.Gullapalli on 07/12/2015.
 */
public class CompleteListOfAppointments extends Fragment {

    @Bind(R.id.newapprecyclerView)
    RecyclerView newapprecyclerView;

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.compviewappt, null);
        ButterKnife.bind(this, view);
        getActivity().setTitle(Prefs.getString("ED_ActionName", "") + getActivity().getString(R.string.nav_list));


        // newapprecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);
        progressDialog.show();
        final List<AppointmentListByVisitorId> appointmentListByVisitorIds = new ArrayList<>();
        PostClient postClient = new PostClient();
        //5c08cef9-643b-4d5f-a316-b6f255e567f7   Prefs.getString("UserId", "")
       /*
        Call<List<AppointmentListByVisitorId>> userCall = postClient.myApiEndpointInterface.getAppointmentDetails(Prefs.getString("UserId", ""));
        VMSConstants.mylog("ED_ActionCode  " + Prefs.getString("ED_ActionCode", ""));
        userCall.enqueue(new Callback<List<AppointmentListByVisitorId>>() {
            @Override
            public void onResponse(Response<List<AppointmentListByVisitorId>> response, Retrofit retrofit) {
                if (response.isSuccess() == true) {


                    System.out.println(getClass().getName() + "  " + response.isSuccess() + "\n" + response.raw());
                    List<AppointmentListByVisitorId> appointmentListByVisitorId = response.body();
                    //  String gson=new Gson().toJson(appointmentListByVisitorId);
                    for (int i = 0; i < appointmentListByVisitorId.size(); i++) {
                        appointmentListByVisitorIds.add(new AppointmentListByVisitorId(appointmentListByVisitorId.get(i).getVisitorName(), appointmentListByVisitorId.get(i).getAppointmentID(), appointmentListByVisitorId.get(i).getPublicAppointmentID(),
                                appointmentListByVisitorId.get(i).getMobileNumber(), appointmentListByVisitorId.get(i).getEmailAddress(), appointmentListByVisitorId.get(i).getPhoto()));
                    }
                    System.out.println(appointmentListByVisitorIds.size() + " appointmentListByVisitorIds  ");
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    newapprecyclerView.setLayoutManager(llm);

                    Recy_ItemAdapter recy_itemAdapter = new Recy_ItemAdapter(appointmentListByVisitorIds);
                    newapprecyclerView.setAdapter(recy_itemAdapter);

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
*/

        /*

        This is for Dummy data
         */

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        newapprecyclerView.setLayoutManager(llm);
        appointmentListByVisitorIds.add(new AppointmentListByVisitorId("Visitor Name 1", "AID 00001111", "PID 00001111", "+91 XX XXXX XXXX", "your@email.com", null));
        appointmentListByVisitorIds.add(new AppointmentListByVisitorId("Visitor Name 2", "AID 00001112", "PID 00001112", "+91 XX XXXX XXXX", "your@email.com", null));
        appointmentListByVisitorIds.add(new AppointmentListByVisitorId("Visitor Name 3", "AID 00001113", "PID 00001113", "+91 XX XXXX XXXX", "your@email.com", null));
        appointmentListByVisitorIds.add(new AppointmentListByVisitorId("Visitor Name 4", "AID 00001114", "PID 00001114", "+91 XX XXXX XXXX", "your@email.com", null));

        Recy_ItemAdapter recy_itemAdapter = new Recy_ItemAdapter(appointmentListByVisitorIds);
        newapprecyclerView.setAdapter(recy_itemAdapter);

        progressDialog.dismiss();
        /*  this is for service
        VMSConstants.mylog("Size1 " + Prefs.getString("ED_ActionName", ""));
        Observable<List<AppointmentListByVisitorId>> listObservable = postClient.myApiEndpointInterface.getDashboradAppointmentDetailsByActionNameRx(
                Prefs.getString("UserId", ""), Prefs.getString("userRoleId", ""), Prefs.getString("userName", ""), "", "", "", "", Prefs.getString("ED_ActionName", ""));

        listObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<AppointmentListByVisitorId>>() {
            @Override
            public void onCompleted() {
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AppointmentListByVisitorId> appointmentListByVisitorIds) {
                VMSConstants.mylog("Size " + String.valueOf(appointmentListByVisitorIds.size()));
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                newapprecyclerView.setLayoutManager(llm);

                Recy_ItemAdapter recy_itemAdapter = new Recy_ItemAdapter(appointmentListByVisitorIds);
                newapprecyclerView.setAdapter(recy_itemAdapter);

            }
        });

        */
    }

}
