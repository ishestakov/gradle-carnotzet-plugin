package com.github.ishestakov.carnotzet.gradle.task

import com.github.ishestakov.carnotzet.gradle.domain.GradleCarnotzetExtension
import com.github.ishestakov.carnotzet.gradle.task.util.GradleCommandRunner
import com.github.swissquote.carnotzet.core.Carnotzet
import com.github.swissquote.carnotzet.core.CarnotzetConfig
import com.github.swissquote.carnotzet.core.maven.CarnotzetModuleCoordinates
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import com.github.swissquote.carnotzet.core.runtime.log.LogListener
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter
import com.github.swissquote.carnotzet.runtime.docker.compose.DockerComposeRuntime
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.internal.ExecActionFactory

import javax.inject.Inject
import java.nio.file.Paths

import static java.util.stream.Collectors.toList

abstract class AbstractZetTask extends DefaultTask {

    String group = 'Carnotzet'
    Carnotzet carnotzet;
    ContainerOrchestrationRuntime runtime;
    boolean follow;
    String service;

    @Inject
    protected ExecActionFactory getExecActionFactory() {
        throw new UnsupportedOperationException();
    }


    @TaskAction
    void exec() {
        GradleCarnotzetExtension extension = getProject().extensions.getByName("carnotzet");
        CarnotzetConfig config = CarnotzetConfig.builder()
                .topLevelModuleId(new CarnotzetModuleCoordinates(getProject().getGroup(), getProject().name, getProject().getVersion()))
                .resourcesPath(Paths.get(extension.resourcesPath))
                .moduleFilterPattern(extension.moduleNameFilterRegex)
                .topLevelModuleResourcesPath(Paths.get(extension.moduleResourcesPath))
                .defaultDockerRegistry(extension.dockerRegistry)
                .build();
        carnotzet = new Carnotzet(config);
        runtime = new DockerComposeRuntime(carnotzet, getProject().name, new GradleCommandRunner(getExecActionFactory()));

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

    void waitForUserInterrupt() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e) {
            getLogger().error(e);
            Thread.currentThread().interrupt();
        }
    }

    static List<String> getServiceNames(Carnotzet carnotzet) {
        return carnotzet.getModules().stream().map({ it.getName() }).sorted().collect(toList());
    }
}