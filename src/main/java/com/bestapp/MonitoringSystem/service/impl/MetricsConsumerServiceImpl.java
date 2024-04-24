package com.bestapp.MonitoringSystem.service.impl;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.exception.MetricNotFoundException;
import com.bestapp.MonitoringSystem.model.Metric;
import com.bestapp.MonitoringSystem.repository.MetricRepository;
import com.bestapp.MonitoringSystem.service.MetricMapper;
import com.bestapp.MonitoringSystem.service.MetricsConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Implementation of the service to work with the metrics
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsConsumerServiceImpl implements MetricsConsumerService {

    private final MetricRepository metricRepository;
    private final MetricMapper metricMapper;

    /**
     * Saving a metric object to a database.<br>
     * - The repository method is used {@link MetricRepository#save(Object)}. <br>
     * - Converting metric data transfer object into metric object {@link MetricMapper#toMetric(MetricDTO)}.
     * @param metricDTO an object containing the necessary information to save a metric. Must not be null.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    @Transactional
    public void saveMetric(MetricDTO metricDTO) {
        log.info("Save metric method was invoked");
        metricRepository.save(metricMapper.toMetric(metricDTO));
        log.info("Metric {} saved successfully", metricDTO);
    }

    /** Getting metric by id.<br>
     * - Search for a metric by id {@link MetricRepository#findById(Object)}.<br>
     * - Converting found metric object into metric data transfer object {@link MetricMapper#toMetricDTO(Metric)}.
     * @param id metric identification number in database
     * @throws MetricNotFoundException if metric object was not found
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     * @return {@link MetricDTO} - found metric data transfer object
     */
    @Override
    @Transactional(readOnly = true)
    public MetricDTO findMetricById(Long id) {
        log.info("Find metric by id = {} method was invoked", id);
        Metric foundMetric = metricRepository.findById(id).orElseThrow(MetricNotFoundException::new);
        log.info("Metric with id = {} was successfully found", id);
        return metricMapper.toMetricDTO(foundMetric);
    }

    /** Getting all metrics pageable.<br>
     * - Search for all metrics pageable {@link MetricRepository#findAll(Pageable)}.<br>
     * - Converting all metric list into metric data transfer object list {@link MetricMapper#toMetricDTOList(List)}.
     * @param pageNumber page number
     * @param pageSize page size number
     * @return all metric object list
     */
    @Override
    @Transactional(readOnly = true)
    public List<MetricDTO> findAllMetrics(Integer pageNumber, Integer pageSize) {
        log.info("Find all metrics method was invoked");
        if (pageSize > 50 || pageSize <= 0) {
            pageSize = 50;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Metric> foundMetrics = metricRepository.findAll(pageRequest).getContent();
        log.info("All metrics were successfully found");
        return metricMapper.toMetricDTOList(foundMetrics);
    }
}
