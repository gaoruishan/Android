package demo.grs.com.android.sercive;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import demo.grs.com.android.app.AppConst;
import demo.grs.com.android.model.LocationWithWeather;


/**
 * 高德地图定位和天气，三分钟更新数据－广播和接口回调
 */
public class AMapLocationService extends Service implements
        AMapLocalWeatherListener, AMapLocationListener {
    private LocationManagerProxy mLocationManagerProxy;
    private final static List<LocationListener> mListeners = new CopyOnWriteArrayList<LocationListener>();
    private LocationWithWeather mLocationWithWeather;

    /**
     * 定位接口
     */
    public interface LocationListener {
        void onReceivedLocation(LocationWithWeather loc);

        void onLocationError();
    }

    /**
     * 注册接口 － onCreate();
     *
     * @param listener
     */
    public static void registerListener(LocationListener listener) {
        mListeners.add(listener);
    }

    /**
     * 注销接口 - onDestroy();
     *
     * @param listener
     */
    public static void unregisterListener(LocationListener listener) {
        mListeners.remove(listener);
    }

    /**
     * 更新位置信息，发送广播
     */
    private void updateLocation(LocationWithWeather location) {
        Intent intent = new Intent(AppConst.ACTION_LOCATION_UPDATE);
        intent.putExtra(AppConst.EXTRA_LOCATION, location);
        getApplicationContext().sendBroadcast(intent);
        //调用其他类实现的接口方法，locationListener的接收位置信息后 和 位置信息错误 的方法
        for (LocationListener locationListener : mListeners) {
            if (location != null) {
                locationListener.onReceivedLocation(location);
            } else {
                locationListener.onLocationError();
            }
        }
    }

    public AMapLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public AMapLocationService getService() {
            return AMapLocationService.this;
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
        init();//初始化定位
    }

    @Override
    public void onDestroy() {
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
            mLocationManagerProxy = null;
        }
        super.onDestroy();
    }

    /**
     * 初始化定位  注册天气监听
     */
    private void init() {

        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);//关闭混合定位
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，

        //1,请求位置数据:使用网络定位; 间隔时间为60s定位一次(-1或minTime >= 5 * 60 * 1000，只定位一次); 距离变化15米
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 3 * 60 * 1000, 15, this);
    }

    /**
     * 获取天气相关信息
     *
     * @param aMapLocalWeatherLive
     */
    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
        if (aMapLocalWeatherLive != null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0) {
            String city = aMapLocalWeatherLive.getCity();//城市
            String weather = aMapLocalWeatherLive.getWeather();//天气情况
            String windDir = aMapLocalWeatherLive.getWindDir();//风向
            String windPower = aMapLocalWeatherLive.getWindPower();//风力
            String humidity = aMapLocalWeatherLive.getHumidity();//空气湿度
            String reportTime = aMapLocalWeatherLive.getReportTime();//数据发布时间
            String info = "城市:" + city + "\n天气情况: " + weather + "\n风向: " + windDir + "\n风力: " + windPower + "\n空气湿度: " + humidity + "\n数据发布时间: " + reportTime;
            Log.e("==TAG", info);
            if (mLocationWithWeather == null) {
                mLocationWithWeather = new LocationWithWeather(weather, city, windDir, windPower, humidity, reportTime);
            } else {
                mLocationWithWeather.setCity(city);
                mLocationWithWeather.setWeather(weather);
                mLocationWithWeather.setWindDir(windDir);
                mLocationWithWeather.setWindPower(windPower);
                mLocationWithWeather.setHumidity(humidity);
                mLocationWithWeather.setReportTime(reportTime);
            }
            //更新
            updateLocation(mLocationWithWeather);
        } else {
            // 获取天气预报失败
            Toast.makeText(this, "获取天气预报失败:" + aMapLocalWeatherLive.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            //获取坐标信息
            Double geoLat = aMapLocation.getLatitude();//纬度
            Double geoLng = aMapLocation.getLongitude();//经度
            String city = aMapLocation.getCity(); //市
            String province = aMapLocation.getProvince(); //省
            String country = aMapLocation.getCountry(); //乡镇
            String district = aMapLocation.getDistrict(); //区,
            String adCode = aMapLocation.getAdCode(); //获取区域编码
            String address = aMapLocation.getAddress();//获取地置描述
            String info = "纬度:" + geoLat + "\n经度: " + geoLng + "\n省: " + province + "\n市: " + city + "\n区: " + district + "\n获取地置描述: " + address;
            Log.e("==TAG", info);
            if (mLocationWithWeather == null) {
                mLocationWithWeather = new LocationWithWeather(city, province, geoLng, geoLat, district, country, adCode, address);
            } else {
                mLocationWithWeather.setLatitude(geoLat);
                mLocationWithWeather.setLongitude(geoLng);
                mLocationWithWeather.setProvince(province);
                mLocationWithWeather.setCity(city);
                mLocationWithWeather.setCountry(country);
                mLocationWithWeather.setDistrict(district);
                mLocationWithWeather.setAdCode(adCode);
                mLocationWithWeather.setAddress(address);
            }
//            //更新
//            updateLocation(mLocationWithWeather);
            //2,请求天气数据
            mLocationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, this);
        } else {
            // 获取位置失败
            Toast.makeText(this, "获取位置失败:" + aMapLocation.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
