package com.example;

import com.netflix.conductor.sdk.workflow.executor.task.TaskContext;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Worker {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    public record GenerateRecordRequest(String type) {}
    public record GenerateRecordResponse(String type, String status) {}

    @WorkerTask(value = "generate-report", threadCount = 10)
    public GenerateRecordResponse generateReport(GenerateRecordRequest request) {
        log.info("Generating report for type: {}", request.type);
        TaskContext.get().addLog("Generating report for type: " + request.type);
        return new GenerateRecordResponse(request.type, "success");
    }
}
