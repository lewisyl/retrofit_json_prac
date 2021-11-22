package dev.lewisliu.retrofitjson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetDataService {
    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
