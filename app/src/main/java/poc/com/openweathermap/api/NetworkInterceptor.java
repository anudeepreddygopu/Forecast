package poc.com.openweathermap.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import poc.com.openweathermap.App;
import poc.com.openweathermap.R;


public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        if (!new ServiceManager(App.getInstance()).isNetworkAvailable()) {
            throw (new IOException(App.getInstance().getString(R.string.no_network)));
        } else {
            response = chain.proceed(chain.request());
        }
        return response;
    }

}
