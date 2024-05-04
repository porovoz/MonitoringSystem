package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.model.Metric;

import java.util.List;

/**
 * The {@code MetricMapper} is an interface responsible for converting metric entities (Metric) and DTO
 * (Data Transfer Object) to various formats and vice versa.
 */
public interface MetricMapper {

    MetricDTO toMetricDTO(Metric metric);

    Metric toMetric(MetricDTO metricDTO);

    List<MetricDTO> toMetricDTOList(List<Metric> metrics);
}
