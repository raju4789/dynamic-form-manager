package com.finclutech.dynamic_form_manager.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStats {

    private List<ServiceStats> totalSubmissionsPerService;
    private List<TrendStats> submissionTrends;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ServiceStats {
        private Long serviceId;
        private String serviceName;
        private long totalSubmissions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TrendStats {
        private String date;
        private long totalSubmissions;
    }
}
