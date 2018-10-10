package poc.com.openweathermap;

import android.app.Application;


public class App extends Application {

    public static App appContext;


    public static synchronized App getInstance() {
        return appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }


}
