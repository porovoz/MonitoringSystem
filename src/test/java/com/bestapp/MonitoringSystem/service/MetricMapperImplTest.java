package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.model.Metric;
import com.bestapp.MonitoringSystem.service.impl.MetricMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A class for checking methods of MetricMapperImpl class
 * @see com.bestapp.MonitoringSystem.service.impl.MetricMapperImpl
 */
@ExtendWith(MockitoExtension.class)
class MetricMapperImplTest {

    @InjectMocks
    private MetricMapperImpl metricMapper;

    private final Metric expected = new Metric();
    private final Metric expected2 = new Metric();
    private final Metric expected3 = new Metric();

    private final MetricDTO metricDTO = new MetricDTO();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setName("Test name");
        expected.setMaxMemory(4096);
        expected.setUsedMemory(135.343);
        expected.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        expected2.setId(2L);
        expected2.setName("Test name2");
        expected2.setMaxMemory(4096);
        expected2.setUsedMemory(115.326);
        expected2.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        expected3.setId(3L);
        expected3.setName("Test name3");
        expected3.setMaxMemory(4096);
        expected3.setUsedMemory(76.875);
        expected3.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        metricDTO.setId(1L);
        metricDTO.setName("Test name4");
        metricDTO.setMaxMemory(4096);
        metricDTO.setUsedMemory(55.776);
        metricDTO.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Checking <b>toMetricDTO()</b> method of MetricMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to metric DTO method")
    void shouldProperlyMapMetricToMetricDTO() {

        MetricDTO metricDTO = metricMapper.toMetricDTO(expected);

        assertNotNull(metricDTO);
        assertEquals(expected.getId(), metricDTO.getId());
        assertEquals(expected.getName(), metricDTO.getName());
        assertEquals(expected.getMaxMemory(), metricDTO.getMaxMemory());
        assertEquals(expected.getUsedMemory(), metricDTO.getUsedMemory());
        assertEquals(expected.getCreatedAt(), metricDTO.getCreatedAt());
    }

    /**
     * Checking <b>toMetric()</b> method of MetricMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to metric method")
    void shouldProperlyMapMetricDTOToMetric() {

        Metric metric = metricMapper.toMetric(metricDTO);

        assertNotNull(metric);
        assertEquals(metricDTO.getId(), metric.getId());
        assertEquals(metricDTO.getName(), metric.getName());
        assertEquals(metricDTO.getMaxMemory(), metric.getMaxMemory());
        assertEquals(metricDTO.getUsedMemory(), metric.getUsedMemory());
        assertEquals(metricDTO.getCreatedAt(), metric.getCreatedAt());
    }

    /**
     * Checking <b>toMetricDTOList()</b> method of MetricMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to metric DTO list method")
    void shouldProperlyMapMetricListToMetricDTOList() {

        List<Metric> expectedMetrics = List.of(expected, expected2, expected3);

        List<MetricDTO> actualMetrics = metricMapper.toMetricDTOList(expectedMetrics);

        Metric expectedMetric = expectedMetrics.stream().findFirst().orElse(new Metric());
        MetricDTO actualMetric = actualMetrics.stream().findFirst().orElse(new MetricDTO());

        assertEquals(expectedMetrics.size(), actualMetrics.size());

        assertEquals(expectedMetric.getId(), actualMetric.getId());
        assertEquals(expectedMetric.getName(), actualMetric.getName());
        assertEquals(expectedMetric.getMaxMemory(), actualMetric.getMaxMemory());
        assertEquals(expectedMetric.getUsedMemory(), actualMetric.getUsedMemory());
        assertEquals(expectedMetric.getCreatedAt(), actualMetric.getCreatedAt());
    }
}
