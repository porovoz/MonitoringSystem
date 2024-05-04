package com.bestapp.MonitoringSystem.service;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.exception.MetricNotFoundException;
import com.bestapp.MonitoringSystem.model.Metric;
import com.bestapp.MonitoringSystem.repository.MetricRepository;
import com.bestapp.MonitoringSystem.service.impl.MetricMapperImpl;
import com.bestapp.MonitoringSystem.service.impl.MetricsConsumerServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * A class for checking methods of MetricsConsumerServiceImpl class
 * @see com.bestapp.MonitoringSystem.service.impl.MetricsConsumerServiceImpl
 * @see com.bestapp.MonitoringSystem.repository.MetricRepository
 */
@ExtendWith(MockitoExtension.class)
class MetricsConsumerServiceImplTest {

    @Mock
    private MetricRepository metricRepositoryMock;

    @Spy
    private MetricMapperImpl metricMapper;

    @InjectMocks
    private MetricsConsumerServiceImpl metricsConsumerService;

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

    @AfterEach
    public void clearDB() {
        metricRepositoryMock.deleteAll();
    }

    /**
     * Checking <b>saveMetric()</b> method of MetricsConsumerServiceImpl class<br>
     * When the <b>MetricRepository::save(Object)</b> method is called, the expected object of the TaskDTO class is returned
     */
    @Test
    @DisplayName("Metric saving to database check")
    void createTaskTest() {

        metricsConsumerService.saveMetric(metricDTO);

        assertNotNull(metricDTO);

        verify(metricRepositoryMock, times(1)).save(any(Metric.class));
    }

    /**
     * Checking <b>findMetricById()</b> method of MetricsConsumerServiceImpl class<br>
     * When the <b>MetricRepository::findById()</b> method is called, the expected object of the MetricDTO class is returned
     */
    @Test
    @DisplayName("Checking the search for the metric by id")
    void findMetricByIdTest() {
        Long id = 1L;

        when(metricRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expected));

        MetricDTO actual = metricsConsumerService.findMetricById(id);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMaxMemory(), actual.getMaxMemory());
        assertEquals(expected.getUsedMemory(), actual.getUsedMemory());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());

        verify(metricRepositoryMock, times(1)).findById(anyLong());
    }

    /**
     * Checking for throwing an exception in the <b>findMetricById()</b> method of the MetricsConsumerServiceImpl class<br>
     * When the <b>MetricRepository::findById()</b> method is called, an exception is thrown <b>MetricNotFoundException</b>
     */
    @Test
    @DisplayName("Checking for throwing an exception when searching for a metric by id")
    void findMetricByIdNotFoundExceptionTest() {
        Long id = 10L;

        when(metricRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MetricNotFoundException.class, () -> metricsConsumerService.findMetricById(id));

        verify(metricRepositoryMock, times(1)).findById(anyLong());
    }

    /**
     * Checking <b>findAllMetrics()</b> method of MetricsConsumerServiceImpl class<br>
     * When the <b>MetricRepository::findAll()</b> method is called, a collection of expected objects of the MetricDTO class is returned
     */
    @Test
    @DisplayName("Checking the search for a list of all metrics")
    void findAllMetricsTest() {

        List<Metric> expectedMetrics = List.of(expected, expected2, expected3);

        when(metricRepositoryMock.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMetrics));

        List<MetricDTO> actualMetrics = metricsConsumerService.findAllMetrics(1, 5);

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
