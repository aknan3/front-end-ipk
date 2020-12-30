package com.example.tintok.Utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tintok.MainPages_MyProfile_ViewModel;
import com.example.tintok.Model.UserProfile;
import com.example.tintok.R;
import com.google.android.material.button.MaterialButton;


public class ModifyUserFragment extends Fragment {

    private MaterialButton saveBtn;
    //private Button deleteBtn;
    private EditText locationEditText;
    private TextView descriptionEditText;
    private EditText usernameEditText;
    MainPages_MyProfile_ViewModel mViewModel;
    Spinner spinner;
    View view;
    private UserProfile user;
    private String newGender="";

    public ModifyUserFragment() {
        // Required empty public constructor
    }
    public static ModifyUserFragment getInstance() {
        ModifyUserFragment fragment = new ModifyUserFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_modify_user, container, false);

        mViewModel = new ViewModelProvider(this).get(MainPages_MyProfile_ViewModel.class);

        this.saveBtn = view.findViewById(R.id.account_save_Button);
      //  this.deleteBtn =  view.findViewById(R.id.account_delete);
        this.locationEditText =  view.findViewById(R.id.account_location_edittext);
        this.descriptionEditText =  view.findViewById(R.id.account_description_edittext);
        this.usernameEditText =  view.findViewById(R.id.account_username_edittext);

        this.spinner =  view.findViewById(R.id.account_gender_spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setOnItemClickListener(this);
        init();

        return view;
    }

    private void init(){
        saveBtn = view.findViewById(R.id.account_save_Button);
        locationEditText = view.findViewById(R.id.account_location_edittext);
        descriptionEditText = view.findViewById(R.id.account_description_edittext);
        usernameEditText = view.findViewById(R.id.account_username_edittext);

        user = mViewModel.getUserProfile();

        usernameEditText.setHint("current name: " + user.getUserName());
        descriptionEditText.setHint("insert inspirational quote here");

        // select current gender
        if(mViewModel.getUserProfile().getGender() != null){
            String[] genderArray = getResources().getStringArray(R.array.gender_array);
            if (user.getGender().equals(genderArray[0])){
                spinner.setSelection(0);
            }else if(user.getGender().equals(genderArray[1])){
                spinner.setSelection(1);
            }else{
                spinner.setSelection(2);
            }
        };

        if(user.getLocation() != null){
            locationEditText.setText(user.getLocation());
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newGender = spinner.getSelectedItem().toString();
                String newLocation= locationEditText.getText().toString();
                String newUsername = mViewModel.getUserProfile().getUserName();
                String newDescription = descriptionEditText.getText().toString();
                //TODO: on destroy // onCreated updated vom hint etc...

             if(!(usernameEditText.getText().length() == 0)){
                newUsername = usernameEditText.getText().toString();
            }
                Log.d("ModifyUserFragment", "old User: " + mViewModel.getUserProfile().toString());
                UserProfile updatedUser = new UserProfile();
                updatedUser.setUserID(mViewModel.getUserProfile().getUserID());
                updatedUser.setUserName(newUsername);
                updatedUser.setGender(newGender);
                updatedUser.setLocation(newLocation);
                updatedUser.setDescription(newDescription);
                Log.d("ModifyUserFragment", "new User: " + updatedUser.toString());
                mViewModel.updateUserProfile(updatedUser);

                /*
                mViewModel.getUserProfile().setUserName(newUsername);
                mViewModel.getUserProfile().setLocation(newLocation);
                mViewModel.getUserProfile().setDescription(newDescription);
                mViewModel.getUserProfile().setGender(newGender);
                mViewModel.updateUserProfile(mViewModel.getUserProfile());
                 */

            }

        });


    }

}