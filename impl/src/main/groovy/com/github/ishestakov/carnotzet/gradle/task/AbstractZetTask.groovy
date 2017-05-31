package com.github.ishestakov.carnotzet.gradle.task

import com.github.swissquote.carnotzet.core.Carnotzet
import com.github.swissquote.carnotzet.core.CarnotzetConfig
import com.github.swissquote.carnotzet.core.maven.CarnotzetModuleCoordinates
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import com.github.swissquote.carnotzet.core.runtime.log.LogListener
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter
import com.github.swissquote.carnotzet.runtime.docker.compose.DockerComposeRuntime
import org.gradle.api.tasks.TaskAction

import java.nio.file.Paths

import static java.util.stream.Collectors.toList

abstract class AbstractZetTask extends GradleCommandRunner {

    Carnotzet carnotzet;
    ContainerOrchestrationRuntime runtime;
    boolean follow;
    String service;

    AbstractZetTask(Class<?> clazz) {
        super(clazz)
        setStandardInput(System.in)
    }

    @TaskAction
    void exec() {

        CarnotzetConfig config = CarnotzetConfig.builder()
                .topLevelModuleId(new CarnotzetModuleCoordinates(getProject().getGroup(), getProject().name, getProject().getVersion()))
                .resourcesPath(Paths.get(getProject().buildDir as String, "carnotzet", getProject().name))
                .topLevelModuleResourcesPath(getProject().projectDir.toPath().resolve("src/main/resources"))
              .build();
        carnotzet = new Carnotzet(config);
        runtime = new DockerComposeRuntime(carnotzet, getProject().name, this);

        executeInternal();
    }

    abstract void executeInternal()

    Runnable wrapWithLogFollowIfNeeded(Runnable block) {
        if (follow) {
            return {
                LogListener printer = new StdOutLogPrinter(getServiceNames(getCarnotzet()), 0, true);
                getRuntime().registerLogListener(printer);
                block.run();
                waitForUserInterrupt();
            }
        }
        return block;
    }

    static void waitForUserInterrupt() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static List<String> getServiceNames(Carnotzet carnotzet) {
        return carnotzet.getModules().stream().map({it.getName()}).sorted().collect(toList());
    }
}