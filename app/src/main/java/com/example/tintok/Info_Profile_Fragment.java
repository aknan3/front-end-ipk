package com.example.tintok;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tintok.Model.UserProfile;

public class Info_Profile_Fragment extends Fragment {


    //TODO:
    private TextView mAgeTextView;

    private View view;
    private TextView mGenderTextView;
    private TextView mEmailTextView;
    private TextView mDesciptionTextView;

    MainPages_MyProfile_ViewModel mViewModel;



    public Info_Profile_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Info_Profile_Fragment getInstance() {
        Info_Profile_Fragment fragment = new Info_Profile_Fragment();
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
        Log.i("INFO", "Creating view for info profile ...Info_Profile_Fragment");
        view = inflater.inflate(R.layout.profile_info_fragment, container, false);

        mGenderTextView = view.findViewById(R.id.profile_gender);
        mEmailTextView = view.findViewById(R.id.profile_email);
        mDesciptionTextView = view.findViewById(R.id.profile_description);
        mViewModel = new ViewModelProvider(this).get(MainPages_MyProfile_ViewModel.class);
        init();


        return view;
    }

    private void init() {
        UserProfile user = mViewModel.getUserProfile();
        mEmailTextView.setText(user.getEmail());

        if(!user.getGender().isEmpty()){
            mGenderTextView.setText(user.getGender());
        } else
            mGenderTextView.setText("no gender selected...");
        Log.d("INFO", "Description: " + user.getDescription());
        if(!user.getDescription().isEmpty()){
            mDesciptionTextView.setText(user.getDescription());
        } else
            mDesciptionTextView.setText("no location selected...");

        mViewModel.getUserProfile().getMutableUserProfile().observe(this.getViewLifecycleOwner(), new Observer<UserProfile>(){

            @Override
            public void onChanged(UserProfile userProfile) {
                mGenderTextView.setText(userProfile.getGender());
                mDesciptionTextView.setText(userProfile.getDescription());
                //TODO: user age
                //mAgeTextView.set
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("INFO", "Destroying view for info profile ...Info_Profile_Fragment");
    }
}
