package com.visitormanagementsystem.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.appointments.CompleteListOfAppointments;
import com.visitormanagementsystem.pojos.EmployeeDashBoardQuickActionsInformationPojo;
import com.visitormanagementsystem.utils.MyCustomeToast;
import com.visitormanagementsystem.utils.VMSConstants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Phani.Gullapalli on 09/12/2015.
 */
public class Recy_SingleText_EmpDashboardAdapter extends RecyclerView.Adapter<Recy_SingleText_EmpDashboardAdapter.ViewHolder> {
    List<EmployeeDashBoardQuickActionsInformationPojo> list_QuickActionsInformationPojos;
    private int lastPosition = -1;
    private Context context;

    public Recy_SingleText_EmpDashboardAdapter(List<EmployeeDashBoardQuickActionsInformationPojo> list_QuickActionsInformationPojos) {
        this.list_QuickActionsInformationPojos = list_QuickActionsInformationPojos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singletextview, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.singletext_empdashboard_actions.setText(list_QuickActionsInformationPojos.get(position).getActionName());
        try {

            if (list_QuickActionsInformationPojos.get(position).getActionCount() > 99) {
                holder.singletext_empdashboard_count.setText("99+");
            }
            holder.singletext_empdashboard_count.setText(list_QuickActionsInformationPojos.get(position).getActionCount().toString());
        } catch (NullPointerException e) {
            VMSConstants.mylog("Error " + e.getMessage());
        }
        holder.setClickListener(new Recy_ItemAdapter.RecycOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (!holder.singletext_empdashboard_count.getText().toString().equalsIgnoreCase("0")) {

                    android.app.Fragment imr = new CompleteListOfAppointments();
                    Context context = view.getContext();
                    if (imr != null) {
                        FragmentManager fmr = ((Activity) context).getFragmentManager();

                        Prefs.putString("ED_ActionName", list_QuickActionsInformationPojos.get(position).getActionName());
                        fmr.beginTransaction().replace(R.id.frame_container, imr).addToBackStack(null).commit();
                    }
                    setAnimation(holder.singletext_rl, position);

                } else {

                    MyCustomeToast.show(view.getContext(), view.getContext().getString(R.string.msg_noappointmentsfound), true, false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_QuickActionsInformationPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.singletext_empdashboard_actions)
        TextView singletext_empdashboard_actions;
        @Bind(R.id.singletext_empdashboard_count)
        TextView singletext_empdashboard_count;
        @Bind(R.id.singletext_rl)
        RelativeLayout singletext_rl;
        Recy_ItemAdapter.RecycOnItemClickListener recycOnItemClickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            singletext_rl.setOnClickListener(this);
            itemView.setTag(itemView);
        }

        public void setClickListener(Recy_ItemAdapter.RecycOnItemClickListener clickListener) {
            this.recycOnItemClickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            recycOnItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_out_right);
            animation.setDuration(2000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
