package com.example.tintok.DataLayer;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tintok.Communication.Communication;
import com.example.tintok.Communication.RestAPI;
import com.example.tintok.Communication.RestAPI_model.PostForm;
import com.example.tintok.Communication.RestAPI_model.UserForm;
import com.example.tintok.Model.MediaEntity;
import com.example.tintok.Model.Post;
import com.example.tintok.Model.UserProfile;
import com.example.tintok.Utils.FileUtil;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository_CurrentUser {
    UserProfile currentUser;
    DataRepositoryController controller;

    public DataRepository_CurrentUser(DataRepositoryController controller){
        this.controller = controller;
    }

    public void initData() {
        RestAPI api = Communication.getInstance().getApi();
        api.getUser().enqueue(new Callback<UserForm>() {
            @Override
            public void onResponse(Call<UserForm> call, Response<UserForm> response) {
                if(response.isSuccessful()){
                    UserForm form = response.body();
                    currentUser = new UserProfile();
                    currentUser.setUserName(form.getUsername());
                    currentUser.setUserID(form.getId());
                    currentUser.setEmail(form.getEmail());
                    currentUser.setGender(form.getGender());
                    currentUser.setLocation(form.getLocation());
                    currentUser.setDescription(form.getDescription());
                    currentUser.getMutableUserProfile().setValue(currentUser);

                    Log.e("aaaaaaaaaaaa","number = " + form.getPosts().size());
                    ArrayList<Post> photos = currentUser.getMyPosts().getValue();

                    /*
                        TODO: ...
                     */
                    // controller.getUserProfile().setValue(currentUser);


                    for(PostForm post : form.getPosts()){
                        Post tmp = new Post(post.getId(), post.getStatus(), post.getAuthor_id(), post.getAuthor_name(), new MediaEntity(post.getImageUrl()));
                        photos.add(tmp);
                    }
                    currentUser.myPosts.postValue(photos);
                } else {
                    Log.e("Info", "Response fails");
                }
            }

            @Override
            public void onFailure(Call<UserForm> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Log.e("Error", "Some errors occur");
            }
        });
    }

    public void submitNewPost(Context mContext, Post newPost) {
        RestAPI api = Communication.getInstance().getApi();
        if(api != null){
            MultipartBody.Part part = FileUtil.prepareImageFileBody(mContext, "upload", newPost.getImage());
            RequestBody user_id = RequestBody.create(MultipartBody.FORM, newPost.getAuthor_id());
            RequestBody username = RequestBody.create(MultipartBody.FORM, newPost.getAuthor_name());
            RequestBody status = RequestBody.create(MultipartBody.FORM, newPost.getStatus());
            api.uploadFile(part, user_id, username, status).enqueue(new Callback<PostForm>() {
                @Override
                public void onResponse(Call<PostForm> call, Response<PostForm> response) {
                    if(response.isSuccessful()){
                        // set id for the post
                        PostForm form = response.body();
                        newPost.setId(form.getId());
                        newPost.getImage().url = form.getImageUrl();
                        Log.e("Url image ", form.getImageUrl());
                        Log.e("Id of post ", form.getId());

                        // do something with newPost ...
                        ArrayList<Post> mPosts = currentUser.myPosts.getValue();
                        mPosts.add(0,newPost);
                        currentUser.myPosts.postValue(mPosts);

                    } else {
                        // Toast.makeText(getApplication(), "Fail to get response", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PostForm> call, Throwable t) {
                    try {
                        throw t;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    //Toast.makeText(getApplication(), "Connection fails", Toast.LENGTH_LONG).show();
                }
            });
        } else {
           // Toast.makeText(this.getApplication(), "No file to upload", Toast.LENGTH_LONG).show();
        }
    }


    //TODO:
    public void updateUser(UserProfile userProfile) {
        RestAPI api = Communication.getInstance().getApi();
        UserForm userForm = new UserForm(
                userProfile.getUserName(),
                userProfile.getGender(),
                userProfile.getLocation(),
                userProfile.getDescription());

        Log.d("DataRepo", userProfile.getLocation());
        Log.d("DataRepo", DataRepositoryController.getInstance().getUser().getLocation());

        api.updateUser(userProfile.getUserID(), userForm).enqueue(new Callback<UserForm>() {
            @Override
            public void onResponse(Call<UserForm> call, Response<UserForm> response) {
                Log.d("DataRepo", "on  Response");
                if(response.isSuccessful()){
                    UserForm data = response.body();

                    currentUser.setUserName(data.getUsername());
                    currentUser.setGender(data.getGender());
                    currentUser.setDescription(data.getDescription());
                    currentUser.setLocation(data.getLocation());
                    Log.d("DataRepo", "user: " + currentUser.toString());
                    currentUser.getMutableUserProfile().postValue(currentUser);
                    Log.d("DataRepo", "on  Response successful");
                }

            }

            @Override
            public void onFailure(Call<UserForm> call, Throwable t) {
                Log.e("Error", "Connection error");
                //Log.d("DataRepo", "onResponse failed");
            }
        });


    }
}
