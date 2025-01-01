package org.example.projectmanagement.config;

import org.example.projectmanagement.bridge.TaskBridge;
import org.example.projectmanagement.bridge.TaskBridgeProvider;
import org.example.projectmanagement.bridge.impl.DefaultTaskBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskBridgeConfig {

    @Bean
    public TaskBridge defaultTaskBridge() {
        TaskBridge taskBridge = new DefaultTaskBridge();
        TaskBridgeProvider.setTaskBridge(taskBridge);
        return taskBridge;
    }
}


