package org.example.projectmanagement.config;

import org.example.projectmanagement.handlers.AgileTaskStatusHandler;
import org.example.projectmanagement.handlers.KanbanTaskStatusHandler;
import org.example.projectmanagement.handlers.RupTaskStatusHandler;
import org.example.projectmanagement.handlers.TaskStatusHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskStatusHandlerConfig {

    @Bean
    public TaskStatusHandler taskStatusHandlerChain(AgileTaskStatusHandler agileHandler, KanbanTaskStatusHandler kanbanHandler, RupTaskStatusHandler rupHandler) {
        agileHandler.setNextHandler(kanbanHandler);
        kanbanHandler.setNextHandler(rupHandler);
        return agileHandler;
    }
}