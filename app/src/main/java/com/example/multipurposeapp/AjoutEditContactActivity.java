package com.example.multipurposeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.multipurposeapp.model.Contact;
import com.example.multiuseapp.R;
import com.example.multipurposeapp.dbmanager.ContactManager;
import com.google.android.material.textfield.TextInputLayout;

public class AjoutEditContactActivity extends AppCompatActivity {


    private Button btn_confirm;
    private TextInputLayout ed_nom_contact,ed_prenom_contact,ed_num_contact;
    private RadioGroup radioGroup;
    private RadioButton radioSex;
    private Contact contact;
    private ContactManager contactManager;
    public static final String EXTRA_ID =
            "com.example.multiuseapp.EXTRA_ID";
    public static final String EXTRA_NOM =
            "com.example.multiuseapp.EXTRA_NOM";
    public static final String EXTRA_PRENOM =
            "com.example.multiuseapp.EXTRA_PRENOM";
    public static final String EXTRA_NUMERO =
            "com.example.multiuseapp.EXTRA_NUMERO";
    public static final String EXTRA_SEX =
            "com.example.multiuseapp.EXTRA_SEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ajout_contact);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        ed_nom_contact = findViewById(R.id.ed_add_nom);
        ed_prenom_contact = findViewById(R.id.ed_add_prenom);
        ed_num_contact = findViewById(R.id.ed_add_num);

        radioGroup = (RadioGroup) findViewById(R.id.radio);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Contact");
            ed_nom_contact.getEditText().setText(intent.getStringExtra(EXTRA_NOM));
            ed_prenom_contact.getEditText().setText(intent.getStringExtra(EXTRA_PRENOM));
            ed_num_contact.getEditText().setText(intent.getStringExtra(EXTRA_NUMERO));
            if (intent.getStringExtra(EXTRA_SEX).equals("Femme"))
                radioGroup.check(R.id.radioFemale);
            else
                radioGroup.check(R.id.radioMale);


        } else {
            setTitle("Add Contact");
        }
    }
    private void saveContact() {
        String nom = ed_nom_contact.getEditText().getText().toString();
        String prenom = ed_prenom_contact.getEditText().getText().toString();
        String numero = ed_num_contact.getEditText().getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();

       // find the radiobutton by returned id
        radioSex = (RadioButton) findViewById(selectedId);
        String sex = radioSex.getText().toString();

        if (nom.trim().isEmpty() || prenom.trim().isEmpty() || numero.trim().isEmpty()) {
            Toast.makeText(this, "Please insert contact's infos", Toast.LENGTH_SHORT).show();
            return;
        }else{
        Contact contact = new Contact(nom,prenom,numero,sex);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            contact.setId(id);
            contactManager = new ContactManager(AjoutEditContactActivity.this);
            contactManager.updateContact(contact);
        }else{
            contactManager = new ContactManager(AjoutEditContactActivity.this);
            contactManager.ajouterContact(contact);
            Toast.makeText(this, "Contact "+contact.getNom()+" updated", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(AjoutEditContactActivity.this, MainActivity.class));
        finish();
    }}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ajout_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact_menu:
                saveContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    }
