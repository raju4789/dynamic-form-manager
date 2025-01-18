package com.finclutech.dynamic_form_manager.controllers;

import com.finclutech.dynamic_form_manager.dtos.response.DashboardStats;
import com.finclutech.dynamic_form_manager.services.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for exposing dashboard statistics endpoints.
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Endpoint to fetch consolidated dashboard statistics.
     *
     * @return A ResponseEntity containing the DashboardStats object.
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        log.info("Received request to fetch dashboard statistics.");
        DashboardStats stats = dashboardService.getDashboardStats();
        log.info("Successfully fetched dashboard statistics.");
        return ResponseEntity.ok(stats);
    }
}
