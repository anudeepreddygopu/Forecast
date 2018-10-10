package poc.com.openweathermap;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import poc.com.openweathermap.adapters.DayForecastAdapter;
import poc.com.openweathermap.models.Forecast;
import poc.com.openweathermap.models.ForecastResponse;
import poc.com.openweathermap.viewModel.GetForecastViewModel;

import static poc.com.openweathermap.api.ApiUrls.FORECAST_ICON;

public class DashboardActivity extends FragmentActivity implements OnMapReadyCallback, DayForecastAdapter.DayForecastInterface {

    @BindView(R.id.rv_dates)
    RecyclerView rvDates;
    ProgressDialog dialog;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.tv_date)
    TextView tvDate;
    LatLng latLng = new LatLng(35, 139);
    GoogleMap mMap;
    GetForecastViewModel getForecastViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getForecastViewModel = ViewModelProviders.of(this).get(GetForecastViewModel.class);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getForecast(latLng);
    }

    private void getForecast(LatLng latLng) {
        dialog.show();
        getForecastViewModel.getForecast(latLng).observe(this, new Observer<ForecastResponse>() {
            @Override
            public void onChanged(@Nullable ForecastResponse forecastResponse) {
                dialog.dismiss();
                if (forecastResponse != null && forecastResponse.getCod() == 200) {
                    setForecast(forecastResponse);
                } else {
                    Toast.makeText
                            (DashboardActivity.this, forecastResponse != null ? forecastResponse.getMessage() : getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setForecast(ForecastResponse body) {
        if (mMap != null) {
            LatLng sydney = new LatLng(body.getCity().getCoord().getLat(), body.getCity().getCoord().getLon());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f));
        }
        setDayForecastAdapter(body.getList());
        tvLocation.setText(body.getCity().getName());
        if (body.getList() != null && body.getList().size() > 0) {
            setIndividualData(body.getList().get(0));
        }
    }

    private void setIndividualData(Forecast forecast) {
        if (forecast != null) {
            if (forecast.getWeather() != null && forecast.getWeather().size() > 0) {
                tvMain.setText(forecast.getWeather().get(0).getMain() + ", " + forecast.getWeather().get(0).getDescription());
                Picasso.get().load(FORECAST_ICON + forecast.getWeather().get(0).getIcon() + ".png").into(ivWeather);
            }
            tvTemperature.setText(forecast.getMain().getTemp() + "°");
            tvDate.setText(getForecastViewModel.getDate(forecast.getDt()));
        }
    }


    private void setDayForecastAdapter(ArrayList<Forecast> forecasts) {
        DayForecastAdapter dayForecastAdapter = new DayForecastAdapter(this, forecasts, this, getForecastViewModel);
        if (rvDates.getLayoutManager() == null) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rvDates.setLayoutManager(layoutManager);
        }
        rvDates.setAdapter(dayForecastAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void searchPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                getForecast(place.getLatLng());
                latLng = place.getLatLng();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }


    @OnClick(R.id.tv_location)
    public void onTvClickLocationClicked() {
        searchPlace();
    }

    @OnClick(R.id.tv_refresh)
    public void onTvRefreshClicked() {
        getForecast(latLng);
    }


    @Override
    public void onListClick(Forecast data) {
        setIndividualData(data);
    }


    @OnClick(R.id.v_overlay)
    public void onViewClicked() {
    }
}
