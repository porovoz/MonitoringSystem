package com.bestapp.MonitoringSystem.controller;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.exception.MetricNotFoundException;
import com.bestapp.MonitoringSystem.service.impl.MetricsConsumerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A class for checking methods of MetricsConsumerController class
 * @see com.bestapp.MonitoringSystem.service.impl.MetricsConsumerServiceImpl
 */
@WebMvcTest(MetricsConsumerController.class)
class MetricsConsumerControllerTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private MetricsConsumerServiceImpl metricsConsumerService;

    private final MetricDTO metricDTO = new MetricDTO();
    private final MetricDTO metricDTO2 = new MetricDTO();
    private final MetricDTO metricDTO3 = new MetricDTO();

    @BeforeEach
    public void SetUp() {
        metricDTO.setId(1L);
        metricDTO.setName("Test name");
        metricDTO.setMaxMemory(4096);
        metricDTO.setUsedMemory(135.343);
        metricDTO.setCreatedAt(LocalDateTime.now());

        metricDTO2.setId(2L);
        metricDTO2.setName("Test name2");
        metricDTO2.setMaxMemory(4096);
        metricDTO2.setUsedMemory(115.326);
        metricDTO2.setCreatedAt(LocalDateTime.now());

        metricDTO3.setId(3L);
        metricDTO3.setName("Test name3");
        metricDTO3.setMaxMemory(4096);
        metricDTO3.setUsedMemory(76.875);
        metricDTO3.setCreatedAt(LocalDateTime.now());
    }

    /**
     * Checking <b>findMetricById()</b> method of MetricsConsumerController class<br>
     * When the <b>MetricsConsumerService::findMetricById()</b> method is called, the expected object of the MetricDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Get metric by id method check")
    void getMetricByIdTest() {

        when(metricsConsumerService.findMetricById(anyLong())).thenReturn(metricDTO);

        mvc.perform(get( "/metrics/{id}", metricDTO.getId())
                        .content(objectMapper.writeValueAsString(metricDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(metricDTO.getId()))
                .andExpect(jsonPath("$.name").value(metricDTO.getName()))
                .andExpect(jsonPath("$.maxMemory").value(metricDTO.getMaxMemory()))
                .andExpect(jsonPath("$.usedMemory").value(metricDTO.getUsedMemory()))
                .andExpect(jsonPath("$.createdAt").value(metricDTO.getCreatedAt().format(DATE_TIME_FORMATTER)))
                .andDo(print());

        verify(metricsConsumerService, times(1)).findMetricById(anyLong());
    }

    /**
     * Checking for throwing an exception in the <b>findMetricById()</b> method of MetricsConsumerController class<br>
     * When the <b>MetricsConsumerService::findMetricById()</b> method is called, <b>MetricNotFoundException</b> throws
     */
    @Test
    @SneakyThrows
    @DisplayName("Checking for throwing an exception in the get metric by id method")
    void getMetricByIdExceptionTest() {
        Long id = 10L;
        when(metricsConsumerService.findMetricById(anyLong())).thenThrow(MetricNotFoundException.class);
        mvc.perform(get("/metrics/{id}", id))
                .andExpect(status().isNotFound());
    }

    /**
     * Checking <b>findAllMetrics()</b> method of MetricsConsumerController class<br>
     * When the <b>MetricsConsumerService::findAllMetrics()</b> method is called, the expected object list of the MetricDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Find all metrics method check")
    void findAllMetricsTest() {
        List<MetricDTO> metricDTOList = List.of(metricDTO, metricDTO2, metricDTO3);

        when(metricsConsumerService.findAllMetrics(anyInt(), anyInt())).thenReturn(metricDTOList);

        mvc.perform(get("/metrics").param("pageNumber", "1").param("pageSize", "20")
                        .content(objectMapper.writeValueAsString(metricDTOList))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(metricDTOList.size())))
                .andDo(print());

        verify(metricsConsumerService, times(1)).findAllMetrics(anyInt(), anyInt());
    }
}
