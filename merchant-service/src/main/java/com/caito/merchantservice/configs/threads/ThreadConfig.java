package com.caito.merchantservice.configs.threads;

import org.springframework.boot.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

/**
 * Configuration class for setting up thread management using virtual threads.
 * This configuration enables asynchronous processing and customizes the Tomcat protocol handler
 * to utilize virtual threads for handling requests.
 *
 * @author caito
 *
 */
@Configuration
@EnableAsync
public class ThreadConfig {

    /**
     * Defines an AsyncTaskExecutor bean that uses virtual threads for executing tasks asynchronously.
     *
     * @return an AsyncTaskExecutor backed by a virtual thread per task executor
     */
    @Bean
    AsyncTaskExecutor applicationTaskExecutor(){
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    /**
     * Customizes the Tomcat protocol handler to use virtual threads for handling incoming requests.
     *
     * @return a TomcatProtocolHandlerCustomizer that sets the executor to a virtual thread per task executor
     */
    @Bean
    TomcatProtocolHandlerCustomizer<?> tomcatProtocolHandlerCustomizer() {
        return protocolHandler -> protocolHandler
                .setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
