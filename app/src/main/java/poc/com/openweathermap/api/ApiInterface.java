package poc.com.openweathermap.api;

import poc.com.openweathermap.models.ForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET(ApiUrls.FORECAST)
    Call<ForecastResponse> getForecast(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String applicationId);
}
