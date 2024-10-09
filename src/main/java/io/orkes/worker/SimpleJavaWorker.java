package io.orkes.worker;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.ConductorClient;
import com.netflix.conductor.client.http.HeaderSupplier;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import io.orkes.conductor.client.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;

public class SimpleJavaWorker implements Worker {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJavaWorker.class);

    private static final String KEY = "f1f2634e-558c-4d3f-8d66-0b54bc7cd161";
    private static final String SECRET = "yr8KV0LE7fKvUbztymWkjbkmLueiWRCnkNGjftndQ0rSymJP";
    private static final String BASE_PATH = "https://ys.orkesconductor.io/api";

    @Override
    public String getTaskDefName() {
        return "calculate-fx";
    }

    @Override
    public TaskResult execute(Task task) {
        LOGGER.info("Executing task: {} from workflow {}", task.getTaskId(), task.getWorkflowInstanceId());
        TaskResult taskResult = new TaskResult(task);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        taskResult.getOutputData().put("message", "Hello World!");
        return taskResult;
    }

    public static void main(String[] args) {
        ApiClient apiClient = ApiClient.builder()
            .basePath(BASE_PATH)
            .credentials(KEY, SECRET)
            .addHeaderSupplier(new CustomHeaderSupplier())
//            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))
//            .verifyingSsl(false)
            .build();

        TaskRunnerConfigurer runnerConfigurer = new TaskRunnerConfigurer
            .Builder(new TaskClient(apiClient), List.of(new SimpleJavaWorker()))
            .withThreadCount(10)
            .build();
        runnerConfigurer.init();
    }

    static class CustomHeaderSupplier implements HeaderSupplier {
        @Override
        public void init(ConductorClient conductorClient) {}

        @Override
        public Map<String, String> get(String s, String s1) {
            return Map.of("X-Custom-Header", "token-value");
        }
    }
}
