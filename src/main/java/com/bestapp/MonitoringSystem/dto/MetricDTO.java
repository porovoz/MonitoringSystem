package com.bestapp.MonitoringSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetricDTO {

    private Long id;
    private String name;
    private double maxMemory;
    private double usedMemory;
    private LocalDateTime createdAt;
}
