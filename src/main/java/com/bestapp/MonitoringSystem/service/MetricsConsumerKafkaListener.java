package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.exception.MetricNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * This class is used to get messages from Kafka and make operations with them
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MetricsConsumerKafkaListener {

    private final MetricsConsumerService metricsConsumerService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Getting messages of metrics-topic from Kafka and saving metric to a database.<br>
     * - {@link MetricsConsumerService#saveMetric(MetricDTO)} used to save a metric. Must not be null.<br>
     * @throws MetricNotFoundException in case the given {@literal metric} is {@literal null}.<br>
     * metricDTO - an object containing all necessary information for saving a metric object
     */
    @KafkaListener(id = "metricGroup", topics = "metrics-topic")
    public void listen(String message) {
        log.info("Kafka message received: {}", message);
        MetricDTO metricDTO = parseMetricDTO(message);
        if (metricDTO != null) {
            metricsConsumerService.saveMetric(metricDTO);
            log.info("Metric successfully saved: {}", metricDTO);
        } else {
            log.error("Null metric object received after parsing JSON");
            throw new MetricNotFoundException();
        }
    }

    /**
     * Deserialize JSON content from given JSON content string message from Kafka to metric data transfer object. <br>
     * @param json content string message from Kafka. <br>
     * @return new MetricDTO object
     */
    private MetricDTO parseMetricDTO(String json) {
        try {
            return objectMapper.readValue(json, MetricDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error occurred during parsing JSON: " + e.getMessage(), e);
            return new MetricDTO();
        }
    }

    /**
     * Getting messages of metrics-topic.DLT from Kafka.<br>
     */
    @KafkaListener(id = "dltGroup", topics = "metrics-topic.DLT")
    public void dltListen(byte[] in) {
        log.info("Received from DLT: {}", new String(in));
    }
}
