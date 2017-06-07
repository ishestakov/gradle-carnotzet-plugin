package com.github.ishestakov.carnotzet.gradle.task

import com.github.swissquote.carnotzet.core.runtime.api.Container
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import org.gradle.api.logging.Logger

public class ZetAddressesTask extends AbstractZetTask {

    String description = "Shows the IP addresses for all running containers"
    @Override
    void executeInternal() {
        execute(getRuntime(), getLogger());
    }

    void execute(ContainerOrchestrationRuntime runtime, Logger log) {
        List<Container> containers = runtime.getContainers();
        if (containers.isEmpty()) {
            getStandardOutput().println("There doesn't seem to be any containers created yet for this carnotzet, please make sure the carnotzet is started");
            return;
        }

        getStandardOutput().println("");
        getStandardOutput().println(String.format("%-25s", "APPLICATION") + "   IP ADDRESS");
        getStandardOutput().println("");
        for (Container container : containers) {
            getStandardOutput().println(String.format("%-25s", container.getServiceName()) + " : "
                    + (container.getIp() == null ? "No address, is container started ?"
                    : container.getIp() + " (" + container.getServiceName() + ".docker)"));
        }
        getStandardOutput().println("");
    }
}