package com.finclutech.dynamic_form_manager.services;

import com.finclutech.dynamic_form_manager.dtos.response.DashboardStats;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DashboardStatsTest {

    @Test
    void testServiceStatsBuilderAndGetters() {
        // Arrange
        Long serviceId = 1L;
        String serviceName = "Test Service";
        long totalSubmissions = 100;

        // Act
        DashboardStats.ServiceStats serviceStats = DashboardStats.ServiceStats.builder()
                .serviceId(serviceId)
                .serviceName(serviceName)
                .totalSubmissions(totalSubmissions)
                .build();

        // Assert
        assertNotNull(serviceStats);
        assertEquals(serviceId, serviceStats.getServiceId());
        assertEquals(serviceName, serviceStats.getServiceName());
        assertEquals(totalSubmissions, serviceStats.getTotalSubmissions());
    }

    @Test
    void testTrendStatsBuilderAndGetters() {
        // Arrange
        String date = "2025-01-01";
        long totalSubmissions = 50;

        // Act
        DashboardStats.TrendStats trendStats = DashboardStats.TrendStats.builder()
                .date(date)
                .totalSubmissions(totalSubmissions)
                .build();

        // Assert
        assertNotNull(trendStats);
        assertEquals(date, trendStats.getDate());
        assertEquals(totalSubmissions, trendStats.getTotalSubmissions());
    }

    @Test
    void testDashboardStatsSettersAndGetters() {
        // Arrange
        DashboardStats.ServiceStats serviceStats1 = DashboardStats.ServiceStats.builder()
                .serviceId(1L)
                .serviceName("Service A")
                .totalSubmissions(100)
                .build();

        DashboardStats.ServiceStats serviceStats2 = DashboardStats.ServiceStats.builder()
                .serviceId(2L)
                .serviceName("Service B")
                .totalSubmissions(200)
                .build();

        DashboardStats.TrendStats trendStats1 = DashboardStats.TrendStats.builder()
                .date("2025-01-01")
                .totalSubmissions(50)
                .build();

        DashboardStats.TrendStats trendStats2 = DashboardStats.TrendStats.builder()
                .date("2025-01-02")
                .totalSubmissions(75)
                .build();

        List<DashboardStats.ServiceStats> serviceStatsList = List.of(serviceStats1, serviceStats2);
        List<DashboardStats.TrendStats> trendStatsList = List.of(trendStats1, trendStats2);

        DashboardStats dashboardStats = new DashboardStats();

        // Act
        dashboardStats.setTotalSubmissionsPerService(serviceStatsList);
        dashboardStats.setSubmissionTrends(trendStatsList);

        // Assert
        assertNotNull(dashboardStats.getTotalSubmissionsPerService());
        assertNotNull(dashboardStats.getSubmissionTrends());
        assertEquals(2, dashboardStats.getTotalSubmissionsPerService().size());
        assertEquals(2, dashboardStats.getSubmissionTrends().size());
        assertEquals("Service A", dashboardStats.getTotalSubmissionsPerService().get(0).getServiceName());
        assertEquals("2025-01-01", dashboardStats.getSubmissionTrends().get(0).getDate());
    }
}