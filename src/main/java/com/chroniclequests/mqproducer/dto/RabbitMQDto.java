package com.chroniclequests.mqproducer.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class RabbitMQDto{

    private String sessionId;
    private double lat;
    private double lon;
    private double radius;

    public RabbitMQDto(String sessionId, double lat, double lon, double radius) {
        this.sessionId = sessionId;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
