package com.co2.mold.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class PollingService {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentTask;
    private long intervalMs = 6000;

    public PollingService() {
        startTask();
    }

    private void startTask() {
        currentTask = executor.scheduleAtFixedRate(
                this::fetchData,
                0,
                intervalMs,
                TimeUnit.MILLISECONDS
        );
    }

    public void updateInterval(long newIntervalMs) {
        if (currentTask != null) {
            currentTask.cancel(false);
        }
        this.intervalMs = newIntervalMs;
        startTask();
    }

    private void fetchData() {
        // Hier abfragen
    }
}
