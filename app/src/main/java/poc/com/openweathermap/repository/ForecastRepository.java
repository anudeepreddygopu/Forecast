package poc.com.openweathermap.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import poc.com.openweathermap.App;
import poc.com.openweathermap.R;
import poc.com.openweathermap.api.ApiClient;
import poc.com.openweathermap.api.ApiInterface;
import poc.com.openweathermap.models.ForecastResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastRepository {
    private ApiInterface apiInterface;

    public ForecastRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public LiveData<ForecastResponse> getForecast(LatLng latLng) {
        final MutableLiveData<ForecastResponse> data = new MutableLiveData<>();
        Call<ForecastResponse> call = apiInterface.getForecast(latLng.latitude, latLng.longitude, App.getInstance().getString(R.string.weather_forecast_id));
        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                ForecastResponse error = new ForecastResponse();
                error.setMessage(t.getMessage());
                error.setCod(503);
                data.setValue(error);
            }
        });

        return data;
    }
}
