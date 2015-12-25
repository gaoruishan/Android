package demo.grs.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gaoruishan on 15/12/23.
 */
public class LocationWithWeather implements Parcelable {

    public String city;//城市
    public String weather;//天气
    public String windDir;//风向
    public String windPower;//风力
    public String humidity;//空气湿度
    public String reportTime;//发布时间
    public Double latitude;//纬度
    public Double longitude;//经度
    public String province;//省
    public String district;//区
    public String country;//乡镇
    public String adCode;//区域编码
    public String address;//地理位置

    public LocationWithWeather(String city, String province, Double longitude, Double latitude, String district, String country, String adCode, String address) {
        this.city = city;
        this.province = province;
        this.longitude = longitude;
        this.latitude = latitude;
        this.district = district;
        this.country = country;
        this.adCode = adCode;
        this.address = address;
    }

    public LocationWithWeather(String weather, String city, String windDir, String windPower, String humidity, String reportTime) {
        this.weather = weather;
        this.city = city;
        this.windDir = windDir;
        this.windPower = windPower;
        this.humidity = humidity;
        this.reportTime = reportTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "LocationWithWeather{" +
                "city='" + city + '\'' +
                ", weather='" + weather + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windPower='" + windPower + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", country='" + country + '\'' +
                ", adCode='" + adCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public LocationWithWeather() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.weather);
        dest.writeString(this.windDir);
        dest.writeString(this.windPower);
        dest.writeString(this.humidity);
        dest.writeString(this.reportTime);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.province);
        dest.writeString(this.district);
        dest.writeString(this.country);
        dest.writeString(this.adCode);
        dest.writeString(this.address);
    }

    protected LocationWithWeather(Parcel in) {
        this.city = in.readString();
        this.weather = in.readString();
        this.windDir = in.readString();
        this.windPower = in.readString();
        this.humidity = in.readString();
        this.reportTime = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.province = in.readString();
        this.district = in.readString();
        this.country = in.readString();
        this.adCode = in.readString();
        this.address = in.readString();
    }

    public static final Creator<LocationWithWeather> CREATOR = new Creator<LocationWithWeather>() {
        public LocationWithWeather createFromParcel(Parcel source) {
            return new LocationWithWeather(source);
        }

        public LocationWithWeather[] newArray(int size) {
            return new LocationWithWeather[size];
        }
    };
}
