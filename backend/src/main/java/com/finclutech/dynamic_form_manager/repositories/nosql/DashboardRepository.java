package com.finclutech.dynamic_form_manager.repositories.nosql;

import com.finclutech.dynamic_form_manager.dtos.response.DashboardStats;

import java.util.List;

public interface DashboardRepository {
    List<DashboardStats.ServiceStats> getTotalSubmissionsPerService();
    List<DashboardStats.TrendStats> getSubmissionTrends();
}
