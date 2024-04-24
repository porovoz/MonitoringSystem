package com.bestapp.MonitoringSystem.service.impl;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.model.Metric;
import com.bestapp.MonitoringSystem.service.MetricMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MetricMapperImpl} class is a component responsible for converting metric entities (Metric) and DTO
 * (Data Transfer Object) to various formats and vice versa.
 */
@Component
public class MetricMapperImpl implements MetricMapper {

    @Override
    public MetricDTO toMetricDTO(Metric metric) {
        MetricDTO metricDTO = new MetricDTO();
        metricDTO.setId(metric.getId());
        metricDTO.setName(metric.getName());
        metricDTO.setMaxMemory(metric.getMaxMemory());
        metricDTO.setUsedMemory(metric.getUsedMemory());
        metricDTO.setCreatedAt(metric.getCreatedAt());
        return metricDTO;
    }

    @Override
    public Metric toMetric(MetricDTO metricDTO) {
        Metric metric = new Metric();
        metric.setId(metricDTO.getId());
        metric.setName(metricDTO.getName());
        metric.setMaxMemory(metricDTO.getMaxMemory());
        metric.setUsedMemory(metricDTO.getUsedMemory());
        metric.setCreatedAt(metricDTO.getCreatedAt());
        return metric;
    }

    @Override
    public List<MetricDTO> toMetricDTOList(List<Metric> metrics) {
        List<MetricDTO> metricDTOS = new ArrayList<>();
        for (Metric metric : metrics) {
            MetricDTO metricDTO = toMetricDTO(metric);
            metricDTOS.add(metricDTO);
        }
        return metricDTOS;
    }
}
