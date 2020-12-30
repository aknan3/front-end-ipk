package com.example.tintok;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tintok.DataLayer.DataRepositoryController;
import com.example.tintok.Model.Post;
import com.example.tintok.Model.UserProfile;

public class MainPages_MyProfile_ViewModel extends AndroidViewModel {
    public MainPages_MyProfile_ViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<UserProfile> mUserProfile;
    private DataRepositoryController controller;


    public void updateUserProfile(UserProfile userProfile){
        new updateUserProfileTask().execute(userProfile);
      //  DataRepositoryController.getInstance().updateUser(userProfile);
    }


    public UserProfile getUserProfile(){
        return DataRepositoryController.getInstance().getUser();
    }

    public void submitNewPost(Post newPost) {
        DataRepositoryController.getInstance().submitNewPost(getApplication(), newPost);
    }


    private class updateUserProfileTask extends AsyncTask<UserProfile, Void, Void>{

        @Override
        protected Void doInBackground(UserProfile... userProfiles) {
            Log.d("ViewModel", "ViewModel do in Background");
            Log.d("ViewModel", userProfiles[0].toString());
            DataRepositoryController.getInstance().updateUser(userProfiles[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplication(), "profile updated", Toast.LENGTH_SHORT).show();
            Log.d("ViewModel", "ViewModel onPostExecute");
          //  getUserProfile().getMutableUserProfile().postValue(getUserProfile());
        }

    }



}
