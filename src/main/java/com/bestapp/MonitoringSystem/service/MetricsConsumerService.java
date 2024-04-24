package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;

import java.util.List;

public interface MetricsConsumerService {

    /**
     * Saving metric to a database
     * @param metricDTO object containing all necessary information for saving a metric object
     */
    void saveMetric(MetricDTO metricDTO);

    /**
     * Getting metric by id
     * @param id metric identification number in database
     */
    MetricDTO findMetricById(Long id);

    /**
     * Getting all metrics pageable
     * @param pageNumber page number
     * @param pageSize page size number
     */
    List<MetricDTO> findAllMetrics(Integer pageNumber, Integer pageSize);
}
