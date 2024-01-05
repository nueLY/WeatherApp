package model;

import java.time.LocalDate;

/**
 * Class used to create Forecast objects
 */
public class Forecast {

    private String precipitaProb;
    private double tMin;
    private double tMax;
    private String predWindDir;
    private int idWeatherType;
    private int classWindSpeed;
    private double longitude;
    private double latitude;
    private LocalDate forecastDate;

    public Forecast(String precipitaProb, double tMin, double tMax,
                    String predWindDir, int idWeatherType, int classWindSpeed,
                    double longitude, double latitude, LocalDate forecastDate) {

        this.precipitaProb = precipitaProb;
        this.tMin = tMin;
        this.tMax = tMax;
        this.predWindDir = predWindDir;
        this.idWeatherType = idWeatherType;
        this.classWindSpeed = classWindSpeed;
        this.longitude = longitude;
        this.latitude = latitude;
        this.forecastDate = forecastDate;
    }

    public String getPrecipitaProb() {
        return precipitaProb;
    }

    public double gettMin() {
        return tMin;
    }

    public double gettMax() {
        return tMax;
    }

    public String getPredWindDir() {
        return predWindDir;
    }

    public int getIdWeatherType() {
        return idWeatherType;
    }

    public int getClassWindSpeed() {
        return classWindSpeed;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public LocalDate getForecastDate() {
        return forecastDate;
    }

    public void setPrecipitaProb(String precipitaProb) {
        this.precipitaProb = precipitaProb;
    }

    public void settMin(double tMin) {
        this.tMin = tMin;
    }

    public void settMax(double tMax) {
        this.tMax = tMax;
    }

    public void setPredWindDir(String predWindDir) {
        this.predWindDir = predWindDir;
    }

    public void setIdWeatherType(int idWeatherType) {
        this.idWeatherType = idWeatherType;
    }

    public void setClassWindSpeed(int classWindSpeed) {
        this.classWindSpeed = classWindSpeed;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setForecastDate(LocalDate forecastDate) {
        this.forecastDate = forecastDate;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "precipitaProb='" + precipitaProb + '\'' +
                ", tMin=" + tMin +
                ", tMax=" + tMax +
                ", predWindDir='" + predWindDir + '\'' +
                ", idWeatherType=" + idWeatherType +
                ", classWindSpeed=" + classWindSpeed +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", forecastDate='" + forecastDate + '\'' +
                '}';
    }
}
