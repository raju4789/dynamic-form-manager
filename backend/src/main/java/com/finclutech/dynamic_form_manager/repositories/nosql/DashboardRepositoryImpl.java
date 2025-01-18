package com.finclutech.dynamic_form_manager.repositories.nosql;

import com.finclutech.dynamic_form_manager.dtos.response.DashboardStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for performing aggregation queries on the "user_forms" collection.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class DashboardRepositoryImpl implements DashboardRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * Fetches the total number of submissions grouped by service.
     *
     * @return A list of service statistics containing service IDs and their total submissions.
     */
    public List<DashboardStats.ServiceStats> getTotalSubmissionsPerService() {
        log.info("Executing aggregation to fetch total submissions per service...");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("service_id") // Group by "service_id"
                        .count().as("totalSubmissions"),
                Aggregation.project("totalSubmissions")
                        .and("_id").as("serviceId"), // Map "_id" to "serviceId"
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalSubmissions"))
        );

        AggregationResults<DashboardStats.ServiceStats> results = mongoTemplate.aggregate(
                aggregation, "user_forms", DashboardStats.ServiceStats.class
        );

        log.info("Aggregation completed. Total services found: {}", results.getMappedResults().size());
        return results.getMappedResults();
    }

    /**
     * Fetches the submission trends over time, grouped by day.
     *
     * @return A list of trend statistics containing dates and their total submissions.
     */
    public List<DashboardStats.TrendStats> getSubmissionTrends() {
        log.info("Executing aggregation to fetch submission trends over time...");

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("{$dateToString: {format: '%Y-%m-%d', date: '$created_at'}}").as("date"), // Use "created_at"
                Aggregation.group("date")
                        .count().as("totalSubmissions"),
                Aggregation.project("totalSubmissions")
                        .and("_id").as("date"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "date"))
        );

        AggregationResults<DashboardStats.TrendStats> results = mongoTemplate.aggregate(
                aggregation, "user_forms", DashboardStats.TrendStats.class
        );

        log.info("Aggregation completed. Total trend entries found: {}", results.getMappedResults().size());
        return results.getMappedResults();
    }
}