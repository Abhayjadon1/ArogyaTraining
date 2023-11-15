package com.ispl.ekalarogya.training.main.support_ticket.another_client;


import com.ispl.ekalarogya.training.rest.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceSupport {
    @FormUrlEncoded
    @POST("list_ticket")
    Call<BaseResponse> getSupportTicketDetails(@Field("phone") String connection_id,
                                               @Field("token") String type);

 @FormUrlEncoded
    @POST("view_ticket")
    Call<BaseResponse> getSupportTicketDetailsData(
            @Field("ticket_id") String ticket_id,
            @Field("token") String token);



    @FormUrlEncoded
    @POST("chat")
    Call<BaseResponse> addSupportChat(
            @Field("ticket_id") String ticket_id,
            @Field("phone") String mobile,
            @Field("comment") String message,
            @Field("status") String status,
            @Field("firstname") String firstname,
            @Field("token") String token
    );


    @FormUrlEncoded
    @POST("upload_image")
    Call<BaseResponse> uploadImageTicket(
            @Field("image") String connection_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("create_ticket")
    Call<BaseResponse> createTicket(
            @Field("subject") String subject,
            @Field("mobile") String mobile,
            @Field("token") String token,
            @Field("firstname") String firstname,
            @Field("village_id") String village_id,
            @Field("message") String message,
            @Field("app_id") String app_id,//archaya 1, karykarta 2,sanchalk 3, arogya 4
            @Field("image") String image);
}
