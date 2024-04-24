package com.bestapp.MonitoringSystem.repository;

import com.bestapp.MonitoringSystem.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface containing methods for working with a database of objects of the Metric class
 * @see Metric
 * @see com.bestapp.MonitoringSystem.service.MetricsConsumerService
 */
@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
}
