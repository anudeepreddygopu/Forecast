package poc.com.openweathermap.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import poc.com.openweathermap.models.ForecastResponse;
import poc.com.openweathermap.repository.ForecastRepository;

public class GetForecastViewModel extends ViewModel {

    private LiveData<ForecastResponse> forecastResponseLiveData;
    private ForecastRepository forecastRepository;

    public GetForecastViewModel() {
        forecastRepository = new ForecastRepository();
    }

    public LiveData<ForecastResponse> getForecast(LatLng latLng) {
        forecastResponseLiveData = forecastRepository.getForecast(latLng);
        return forecastResponseLiveData;
    }

    public String getDate(long timeInSec) {
        Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        return formatter.format(new Date(timeInSec * 1000));
    }

    public String getCardDate(long timeInSec) {
        Format formatter = new SimpleDateFormat("dd-MMM HH:mm");
        return formatter.format(new Date(timeInSec * 1000));
    }
}
