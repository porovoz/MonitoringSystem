package com.bestapp.MonitoringSystem.controller;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.service.impl.MetricsProducerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/metrics")
@Tag(name = "Metrics", description = "API for metric sending")
public class MetricsProducerController {

    private final MetricsProducerServiceImpl metricsProducerService;

    /**
     * Sending a new metric.<br>
     * The response with the HTTP 200 status code (Ok).<br>
     */
    @Operation(
            summary = "Send a new metric",
            description = "Send a new metric with metric id",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Metric was successfully sent",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MetricDTO.class)
                            )),
                    @ApiResponse(responseCode = "201", description = "Metric was successfully sent", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<Void> sendMetrics(@RequestBody MetricDTO metricDTO) {
        metricsProducerService.sendMetricToKafka(metricDTO);
        return ResponseEntity.ok().build();
    }
}
