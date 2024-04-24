package com.bestapp.MonitoringSystem.service.impl;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.model.Metric;
import com.bestapp.MonitoringSystem.service.MetricMapper;
import com.bestapp.MonitoringSystem.service.MetricsProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of the producer service to work with the metric
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsProducerServiceImpl implements MetricsProducerService {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MetricMapper metricMapper;

    /**
     * Sending metric to Kafka
     * - {@link ObjectMapper#writeValueAsString(Object)} method is used to convert metric data transfer object into a string
     * - {@link KafkaTemplate#send(String, Object)} method is used to send metric to Kafka
     * @param metricDTO object containing all necessary information for sending a metric object
     */
    @Override
    public void sendMetricToKafka(MetricDTO metricDTO) {
        try {
            String jsonMetricDTO = objectMapper.writeValueAsString(metricDTO);
            log.info("Sending metric to Kafka: {}", jsonMetricDTO);
            kafkaTemplate.send("metrics-topic", jsonMetricDTO);
            log.info("Metric successfully sent to Kafka: {}", jsonMetricDTO);
        } catch (JsonProcessingException e) {
            log.error("Error occurred during converting metric to JSON", e);
        }
    }


    /**
     * Creation of a metric object and sending it to Kafka
     */
    @Override
    public void createMetric() {
        log.info("Create metric method was invoked");
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        double maxHeapMemory = heapMemoryUsage.getMax() / (1024.0 * 1024.0);
        double usedHeapMemory = heapMemoryUsage.getUsed() / (1024.0 * 1024.0);
        BigDecimal bigDecimal = new BigDecimal(Double.toString(usedHeapMemory));
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        double usedHeapMemoryRound = bigDecimal.doubleValue();

        Metric metric = Metric.builder()
                .name("Memory")
                .maxMemory(maxHeapMemory)
                .usedMemory(usedHeapMemoryRound)
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        MetricDTO metricDTO = metricMapper.toMetricDTO(metric);
        log.info("Metric {} was created successfully", metricDTO);
        sendMetricToKafka(metricDTO);
    }
}