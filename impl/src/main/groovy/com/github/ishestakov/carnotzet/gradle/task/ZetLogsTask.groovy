package com.github.ishestakov.carnotzet.gradle.task

import com.github.swissquote.carnotzet.core.Carnotzet
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import com.github.swissquote.carnotzet.core.runtime.log.LogEvent
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter

public class ZetLogsTask extends AbstractZetServiceOrientedTask {


    String description = 'Show logs for container(s)';
    @Override
    void executeInternal() {
        execute(getRuntime(), getCarnotzet(), getService());
    }

    private static volatile long lastLogEventTime = System.currentTimeMillis();

    void execute(ContainerOrchestrationRuntime runtime, Carnotzet carnotzet, String service) {

        // defaults
        boolean follow = true;
        Integer tail = 200;

        String followStr = System.getProperty("follow");
        if (followStr != null) {
            follow = Boolean.valueOf(followStr);
        }
        String tailStr = System.getProperty("tail");
        if (tailStr != null) {
            tail = Integer.valueOf(tailStr);
        }

        StdOutLogPrinter printer = new StdOutLogPrinter(getServiceNames(carnotzet), tail, follow) {
            @Override
            public void acceptInternal(LogEvent event) {
                super.acceptInternal(event);
                lastLogEventTime = System.currentTimeMillis();
            }
        };

        if (service != null) {
            printer.setEventFilter({event -> service.equals(event.getService())});
        }

        runtime.registerLogListener(printer);
        if (follow) {
            waitForUserInterrupt();
        } else {
            // small hack to avoid complex synchronization: only exit main thread
            // if there were no log events printed in the last 400ms
            def process = {
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            process()
            while (System.nanoTime() - lastLogEventTime < 400) {
                process();
            }
        }
    }

}
