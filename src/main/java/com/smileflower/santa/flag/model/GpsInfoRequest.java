package com.smileflower.santa.flag.model;

public class GpsInfoRequest {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public GpsInfoRequest(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAltitude() {
        return altitude;
    }
}
