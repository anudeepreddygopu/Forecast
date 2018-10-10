package poc.com.openweathermap.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import poc.com.openweathermap.R;
import poc.com.openweathermap.models.Forecast;
import poc.com.openweathermap.viewModel.GetForecastViewModel;

import static poc.com.openweathermap.api.ApiUrls.FORECAST_ICON;

public class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.ViewHolder> {

    long selectedDate;
    private DayForecastInterface dayForecastInterface;
    private List<Forecast> forecastList;
    private Context context;
    private GetForecastViewModel getForecastViewModel;

    public DayForecastAdapter(Context context, List<Forecast> data, DayForecastInterface dayForecastInterface, GetForecastViewModel getForecastViewModel) {
        forecastList = data;
        this.context = context;
        this.dayForecastInterface = dayForecastInterface;
        this.getForecastViewModel = getForecastViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_day, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Forecast forecast = forecastList.get(position);
        holder.tvTime.setText(getForecastViewModel.getCardDate(forecast.getDt()));
        Picasso.get().load(FORECAST_ICON + forecast.getWeather().get(0).getIcon() + ".png").into(holder.ivWeather);
        holder.tvTemperature.setText(forecast.getMain().getTemp() + "°");
        if (selectedDate == forecast.getDt()) {
            setColor(holder, ContextCompat.getColor(context, R.color.colorPrimary), Color.WHITE);
        } else {
            setColor(holder, Color.WHITE, ContextCompat.getColor(context, R.color.colorPrimary));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = forecast.getDt();
                dayForecastInterface.onListClick(forecast);
                notifyDataSetChanged();
            }
        });
    }

    private void setColor(ViewHolder holder, int background, int foreground) {
        holder.cardView.setCardBackgroundColor(background);
        holder.tvTime.setTextColor(foreground);
        holder.tvTemperature.setTextColor(foreground);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public interface DayForecastInterface {
        void onListClick(Forecast data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_weather)
        ImageView ivWeather;
        @BindView(R.id.tv_temperature)
        TextView tvTemperature;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
            cardView = (CardView) itemView;

        }


    }

}
