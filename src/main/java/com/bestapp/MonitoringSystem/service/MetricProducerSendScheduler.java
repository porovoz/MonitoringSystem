package com.bestapp.MonitoringSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Send metric scheduler
 */
@Service
@RequiredArgsConstructor
public class MetricProducerSendScheduler {

    private final MetricsProducerService metricsProducerService;

    /**
     * Setting up a metric sending interval.<br>
     * - {@link MetricsProducerService#createMetric()} - method used to send a metric
     */
    @Scheduled(fixedDelay = 10000)
    public void sendMetrics() {
        metricsProducerService.createMetric();
    }
}
