package com.example.multipurposeapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipurposeapp.model.Contact;
import com.example.multipurposeapp.AjoutEditContactActivity;
import com.example.multiuseapp.R;
import com.example.multipurposeapp.adapter.RecyclerContactAdapter;
import com.example.multipurposeapp.dbmanager.ContactManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    private ContactManager contactManager;
    private ArrayList<Contact> data_contact;
    private RecyclerContactAdapter adapter;
    private EditText edt_search_contact;
    private ImageButton btn_search;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton btn_ajout = root.findViewById(R.id.btn_contact_add);
        recyclerView = root.findViewById(R.id.recycler_contact);
        edt_search_contact = root.findViewById(R.id.search_view_contact);
        btn_search = root.findViewById(R.id.btn_search_contact);

        setHasOptionsMenu(true);




        contactManager = new ContactManager(root.getContext());
        data_contact = contactManager.getAllContact();
         adapter = new RecyclerContactAdapter(getActivity(),data_contact);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AjoutEditContactActivity.class));
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_search_contact.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Search text is empty", Toast.LENGTH_SHORT).show();

                }else{
                    data_contact = contactManager.searchContact(edt_search_contact.getText().toString().trim());
                    adapter = new RecyclerContactAdapter(getActivity(),data_contact);
                    recyclerView.setAdapter(adapter);
                    edt_search_contact.clearFocus();



                }


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = adapter.getContactAt(viewHolder.getLayoutPosition());
                data_contact.remove(viewHolder.getLayoutPosition());
                contactManager.removeContact(contact);
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                Toast.makeText(getActivity(), ""+contact.getNom()+" Deleted", Toast.LENGTH_SHORT).show();
                return;
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new RecyclerContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {
                Intent intent = new Intent(getActivity(), AjoutEditContactActivity.class);
                intent.putExtra(AjoutEditContactActivity.EXTRA_ID, contact.getId());
                intent.putExtra(AjoutEditContactActivity.EXTRA_NOM, contact.getNom());
                intent.putExtra(AjoutEditContactActivity.EXTRA_PRENOM, contact.getPrenom());
                intent.putExtra(AjoutEditContactActivity.EXTRA_NUMERO, contact.getNumero());
                intent.putExtra(AjoutEditContactActivity.EXTRA_SEX, contact.getSex());
                startActivity(intent);
            }
        });

        return root;

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_contacts:
                contactManager.deleteAllContacts();
                data_contact.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "All contacts deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}