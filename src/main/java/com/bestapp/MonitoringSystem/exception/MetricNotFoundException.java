package com.bestapp.MonitoringSystem.exception;

public class MetricNotFoundException extends NotFoundException {

    public MetricNotFoundException() {
        super("Metric not found");
    }
}
