package com.visitormanagementsystem.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.appointments.ViewAppointmentHostory;
import com.visitormanagementsystem.pojos.AppointmentListByVisitorId;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Phani.Gullapalli on 07/12/2015.
 */
public class Recy_ItemAdapter extends RecyclerView.Adapter<Recy_ItemAdapter.ViewHolder> {
    static RecycOnItemClickListener recycOnItemClickListener;
    @Bind(R.id.item_card_view)
    CardView item_card_view;
    private List<AppointmentListByVisitorId> list_appointmentListByVisitorIds;

    public Recy_ItemAdapter(List<AppointmentListByVisitorId> list_appointmentListByVisitorIds) {
        this.list_appointmentListByVisitorIds = list_appointmentListByVisitorIds;
    }

    @Override
    public Recy_ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_applist, null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(this, view);
        item_card_view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.white));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //   holder.item_text.setText(recycler_itemDatas[position].getVisitor_name());

        holder.item_text.setText(list_appointmentListByVisitorIds.get(position).getPublicAppointmentID());
        holder.item_phone.setText(list_appointmentListByVisitorIds.get(position).getMobileNumber());
        holder.item_email.setText(list_appointmentListByVisitorIds.get(position).getVisitorName());
        String photostring = list_appointmentListByVisitorIds.get(position).getPhoto();

        if (photostring!=null) {
            byte[] imageAsBytes = Base64.decode(photostring.getBytes(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            holder.item_image.setImageBitmap(bitmap);
        } else {
            holder.item_image.setImageResource(R.drawable.profilepic);
        }


        holder.setClickListener(new RecycOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment imr = new ViewAppointmentHostory();
                Context context = view.getContext();
                // ContextCompat contextCompat = view.getContext();
                if (imr != null) {
                    FragmentManager fmr = ((Activity) context).getFragmentManager();
                    Prefs.putString("PublicAppointmentID", list_appointmentListByVisitorIds.get(position).getPublicAppointmentID());
                    Prefs.putString("AppointmentID", list_appointmentListByVisitorIds.get(position).getAppointmentID());
                    fmr.beginTransaction().replace(R.id.frame_container, imr).addToBackStack(null).commit();
                }

                System.out.println(list_appointmentListByVisitorIds.get(position).getAppointmentID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_appointmentListByVisitorIds.size();
    }

    /**
     * wrire an interface
     */

    public interface RecycOnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.row_name)
        public TextView item_text;
        @Bind(R.id.row_thumbnail)
        public ImageView item_image;
        RecycOnItemClickListener recycOnItemClickListener;
        @Bind(R.id.row_phone)
        TextView item_phone;
        @Bind(R.id.row_email)
        TextView item_email;
        @Bind(R.id.item_rl)
        RelativeLayout item_rl;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            item_rl.setOnClickListener(this);

            itemView.setTag(itemView);


        }

        public void setClickListener(RecycOnItemClickListener clickListener) {
            this.recycOnItemClickListener = clickListener;

        }


        @Override
        public void onClick(View v) {
            recycOnItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
