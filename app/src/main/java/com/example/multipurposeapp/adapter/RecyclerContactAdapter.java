package com.example.multipurposeapp.adapter;


import android.content.Context;

import android.content.Intent;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipurposeapp.dbmanager.ContactManager;
import com.example.multipurposeapp.model.Contact;
import com.example.multiuseapp.R;

import com.like.LikeButton;


import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.MyViewHolder> implements View.OnClickListener {


    Context con;

    ArrayList<Contact> data;

    int indice = -1;
    ContactManager manager;

    public RecyclerContactAdapter() {
    }

    private OnItemClickListener listener;


    public RecyclerContactAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
        manager = new ContactManager(con);


    }

    public Contact getContactAt(int position) {
        return data.get(position);

    }

    @NonNull
    @Override
    public RecyclerContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creation d'un view
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.contact_view, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Contact contact = data.get(position);


        holder.nom_contact_tv.setText(contact.getNom());
        holder.prenom_contact.setText(contact.getPrenom());
        holder.numero_contact_tv.setText(contact.getNumero());


        if (contact.getSex().toLowerCase().equals("femme")) {
            holder.image_contact_iv.setImageResource(R.mipmap.ic_woman);
        } else {
            holder.image_contact_iv.setImageResource(R.mipmap.ic_man);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
        alert.setTitle("Options");
        alert.setMessage("Choose from options");

    }


    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nom_contact_tv, prenom_contact, numero_contact_tv;
        ImageView image_contact_iv;
        ImageButton btn_call,btn_sms;
        LikeButton btn_favoris;

        public MyViewHolder(@NonNull View v) {
            super(v);


            nom_contact_tv = v.findViewById(R.id.textv_nom_contact);
            prenom_contact = v.findViewById(R.id.textv_prenom_contact);
            numero_contact_tv = v.findViewById(R.id.textv_num_contact);
            image_contact_iv = v.findViewById(R.id.iv_imag_contact);
            btn_sms = v.findViewById(R.id.sms_btn);
            btn_call = v.findViewById(R.id.call_btn);
            btn_favoris = v.findViewById(R.id.favoris_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getLayoutPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(data.get(getLayoutPosition()));
                }
            });

            btn_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String numero = data.get(getAdapterPosition()).getNumero();
                    con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                            + numero)));
                }
            });


            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String numero = data.get(getAdapterPosition()).getNumero();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(numero));
                    intent.setData(Uri.parse("tel:" + numero));
                    con.startActivity(intent);
                }
            });
        }


    }
}




