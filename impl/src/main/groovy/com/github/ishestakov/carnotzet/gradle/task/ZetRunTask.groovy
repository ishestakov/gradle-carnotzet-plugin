package com.github.ishestakov.carnotzet.gradle.task

import com.github.swissquote.carnotzet.core.Carnotzet
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import com.github.swissquote.carnotzet.core.runtime.log.LogListener
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException

class ZetRunTask extends AbstractZetTask {

    @Override
    void executeInternal() {
        execute(getRuntime(), getCarnotzet(), getService());
    }

    public static void execute(ContainerOrchestrationRuntime runtime, Carnotzet carnotzet, String service) throws MojoExecutionException,
            MojoFailureException {
        if (service == null) {
            runtime.start();
        } else {
            runtime.start(service);
        }
        Runtime.getRuntime().addShutdownHook(new Thread({runtime.stop}));
        LogListener printer = new StdOutLogPrinter(getServiceNames(carnotzet), null, true);
        if (service != null) {
            printer.setEventFilter({event -> event.getService().equals(service)})
        }
        runtime.registerLogListener(printer);

        waitForUserInterrupt();
    }

}
