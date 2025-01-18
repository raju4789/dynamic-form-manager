package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.response.DashboardStats;
import com.finclutech.dynamic_form_manager.entities.sql.AppService;
import com.finclutech.dynamic_form_manager.repositories.nosql.DashboardRepository;
import com.finclutech.dynamic_form_manager.repositories.sql.LanguageRepository;
import com.finclutech.dynamic_form_manager.repositories.sql.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for fetching and consolidating dashboard statistics.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    private final ServiceRepository serviceRepository;

    /**
     * Fetches and consolidates all dashboard statistics.
     *
     * @return A consolidated DashboardStats object containing all metrics.
     */
    public DashboardStats getDashboardStats() {
        log.info("Fetching all services...");
        List<AppService> services = serviceRepository.findAll();

        log.info("Fetching total submissions per service...");
        List<DashboardStats.ServiceStats> totalSubmissionsPerService = mapServiceStatsToServiceNames(
                dashboardRepository.getTotalSubmissionsPerService(), services
        );

        log.info("Fetching submission trends over time...");
        List<DashboardStats.TrendStats> submissionTrends = dashboardRepository.getSubmissionTrends();

        log.info("Successfully fetched all dashboard statistics.");
        return DashboardStats.builder()
                .totalSubmissionsPerService(totalSubmissionsPerService)
                .submissionTrends(submissionTrends)
                .build();
    }

    /**
     * Maps service statistics to their corresponding service names.
     *
     * @param serviceStats List of service statistics from the repository.
     * @param services     List of all services from the service repository.
     * @return A list of service statistics with service names included.
     */
    private List<DashboardStats.ServiceStats> mapServiceStatsToServiceNames(
            List<DashboardStats.ServiceStats> serviceStats, List<AppService> services) {

        // Create a map of serviceId to serviceName for quick lookup
        Map<Long, String> serviceIdToNameMap = services.stream()
                .collect(Collectors.toMap(AppService::getServiceId, AppService::getName));

        // Map service statistics to include service names
        return serviceStats.stream()
                .map(stats -> DashboardStats.ServiceStats.builder()
                        .serviceId(stats.getServiceId())
                        .serviceName(serviceIdToNameMap.getOrDefault(stats.getServiceId(), null))
                        .totalSubmissions(stats.getTotalSubmissions())
                        .build())
                .toList();
    }
}
