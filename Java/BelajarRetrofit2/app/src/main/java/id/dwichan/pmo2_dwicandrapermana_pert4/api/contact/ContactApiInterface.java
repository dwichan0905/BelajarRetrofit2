package id.dwichan.pmo2_dwicandrapermana_pert4.api.contact;

import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests.GetContact;
import id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests.PostPutDeleteContact;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ContactApiInterface {
    // GET Request
    @GET("kontak") // <-- tuliskan endpoint di parameter value!
    Call<GetContact> getContacts();

    // POST Request
    @FormUrlEncoded
    @POST("kontak")
    Call<PostPutDeleteContact> postContact(
            @Field("nama") String nama,
            @Field("nomor") String nomor
    );

    // PUT Request
    @FormUrlEncoded
    @PUT("kontak")
    Call<PostPutDeleteContact> putContact(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("nomor") String nomor
    );

    // DELETE Request
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "kontak", hasBody = true)
    Call<PostPutDeleteContact> deleteContact(
            @Field("id") String id
    );
}
