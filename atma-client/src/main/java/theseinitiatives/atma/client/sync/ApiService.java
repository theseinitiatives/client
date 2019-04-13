package theseinitiatives.atma.client.sync;

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
import retrofit2.http.Query;
import theseinitiatives.atma.client.model.syncmodel.ApiModel;

public interface ApiService {

    @GET("api/pull")
   Call<List<ApiModel>> getDataDesa(
            @Query("desa") String location_ids,
            @Query("update-id") long updateIds,
            @Query("batch-size") int batch
        );


    @GET("api/pull")
    Call<List<ApiModel>> getDataDusun(
            @Query("dusun") String location_ids,
            @Query("update-id") long updateIds,
            @Query("batch-size") int batch
    );

    @GET("api/pullv2")
    Call<List<ApiModel>> getData(
            @Query("loc-id") String location_id,
            @Query("loc-name") String location_name,
            @Query("update-id") long updateIds,
            @Query("batch-size") int batch
    );


    @Headers( "Content-Type: application/json; charset=utf-8")
    @POST("api/push")
    Call<ResponseBody> savePost(@Body RequestBody req);

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ResponseBody> authLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}