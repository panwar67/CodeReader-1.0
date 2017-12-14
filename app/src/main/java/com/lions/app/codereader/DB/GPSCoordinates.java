package com.lions.app.codereader.DB;

/**
 * Created by Panwar on 14/12/17.
 */

public class GPSCoordinates {

    double Latitude;
    double Longitude;

    public GPSCoordinates(double latitude, double longitude) {

        this.Latitude = latitude;
        this.Longitude = longitude;

    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
