package tgwofficial.atma.client.sync;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tgwofficial.atma.client.model.syncmodel.ApiModel;

public interface ApiService {

    @GET("api/pull?location-id=Dusun_test&update-id={update_id}&batch-size=100")
   Call<List<ApiModel>> getData(@Path("update_id") int updateId);
   // Call<ApiModel> getAnswers();


    @Headers( "Content-Type: application/json; charset=utf-8")
    @POST("api/push")
    Call<String> savePost(@Body RequestBody req);

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ResponseBody> authLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}