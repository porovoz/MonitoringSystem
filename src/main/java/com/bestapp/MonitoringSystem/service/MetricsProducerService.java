package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;

public interface MetricsProducerService {

    /**
     * Sending metric to Kafka
     * @param metricDTO object containing all necessary information for sending a metric object
     */
    void sendMetricToKafka(MetricDTO metricDTO);

    /**
     * Creation of a metric object
     */
    void createMetric();
}
