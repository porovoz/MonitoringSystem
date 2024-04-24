package com.bestapp.MonitoringSystem.controller;

import com.bestapp.MonitoringSystem.dto.MetricDTO;
import com.bestapp.MonitoringSystem.service.MetricsConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/metrics")
@Tag(name = "Metrics", description = "API for metric management")
public class MetricsConsumerController {

    private final MetricsConsumerService metricsConsumerService;

    /**
     * Getting metric by id.
     * @param id metric identification number.
     * @return the response with the found metric in JSON format and the HTTP 200 status code (Ok).<br>
     * If the metric not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find metric by id",
            description = "Search by metric id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Metric was successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MetricDTO.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "Metric was successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Metric not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MetricDTO> getMetricById(@PathVariable("id") Long id) {
        MetricDTO foundMetricDTO = metricsConsumerService.findMetricById(id);
        if (foundMetricDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundMetricDTO);
    }

    /**
     * Getting all metrics pageable.
     * @param pageNumber page number
     * @param pageSize page size number
     * @return the response with the found metric list in JSON format and the HTTP 200 status code (Ok).<br>
     * If the metric list not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find all metrics pageable",
            description = "Search all metrics pageable",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All metrics successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MetricDTO.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "All metrics successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Metrics not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping
    public ResponseEntity<List<MetricDTO>> findAllMetrics(@RequestParam("pageNumber") Integer pageNumber,
                                                          @RequestParam("pageSize") Integer pageSize) {
        List<MetricDTO> foundMetricDTOS = metricsConsumerService.findAllMetrics(pageNumber, pageSize);
        if (foundMetricDTOS == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundMetricDTOS);
    }
}
