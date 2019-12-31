package com.example.blue;

public class beacons {
    int major;
    int minor;
    float distance;


    public beacons(int major, int minor, float distance) {
        this.major = major;
        this.minor = minor;
        this.distance = distance;
    }


    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public beacons() {
        this.distance = 100;
        this.major = 0;
        this.minor = 0;
    }
}
