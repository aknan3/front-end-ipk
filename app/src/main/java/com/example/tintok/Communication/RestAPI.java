package com.example.tintok.Communication;

import com.example.tintok.Communication.RestAPI_model.ChatForm;
import com.example.tintok.Communication.RestAPI_model.PostForm;
import com.example.tintok.Communication.RestAPI_model.UserForm;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestAPI {
    @POST("register")
    Call<ResponseBody> postRegisterData(@Body UserForm data);

    @POST("login")
    Call<UserForm> postLoginData(@Body UserForm data);

    @POST("password_forget")
    Call<ResponseBody> resetPassword(@Body UserForm data);

    @Multipart
    @POST("upload_post")
    Call<PostForm> uploadFile(@Part MultipartBody.Part file, @Part("id") RequestBody id, @Part("author") RequestBody author_name, @Part("status") RequestBody post_status);

    @Multipart
    @POST("upload_file_from_message")
    Call<ResponseBody> uploadFileFromMessage(@Part MultipartBody.Part file, @Part("room_id") RequestBody room_id, @Part("sender_id") RequestBody sender, @Part("receiver_id") RequestBody receiver);

    @GET("get_user_profile/{author_id}")
    Call<UserForm> getUserProfile(@Path("author_id") String author_id);

    @GET("get_comments/{post_id}")
    Call<PostForm> getComments(@Path("post_id") String post_id);

    @POST("get_user")
    Call<UserForm> getUser();

    @PATCH("updateuser/{id}")
    Call<UserForm> updateUser(@Path("id") String id, @Body UserForm data);

    /**
     * @param roomId
     * @return
     */
    @POST("get_chat_room_byId")
    Call<ChatForm> getChatRoom(@Body HashMap<String,String> roomId);

    @POST("create_chat_room")
    Call<ChatForm> createChatRoom(@Body HashMap<String, String> body);

    @POST("get_all_chat_room_ofUser")
    Call<ArrayList<ChatForm>> getAllChatRooms();

    // just to get posts
    @GET("posts")
    Call<ArrayList<PostForm>> getPosts();

    @GET("get_all_users")
    Call<ArrayList<UserForm>> getAllUsers();
}
