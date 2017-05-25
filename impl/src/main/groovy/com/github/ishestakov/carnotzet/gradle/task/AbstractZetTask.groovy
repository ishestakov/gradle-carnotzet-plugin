package com.github.ishestakov.carnotzet.gradle.task
import com.github.swissquote.carnotzet.core.Carnotzet
import com.github.swissquote.carnotzet.core.CarnotzetConfig
import com.github.swissquote.carnotzet.core.maven.CarnotzetModuleCoordinates
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import com.github.swissquote.carnotzet.core.runtime.log.LogListener
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter
import com.github.swissquote.carnotzet.runtime.docker.compose.DockerComposeRuntime
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Paths

import static java.util.stream.Collectors.toList

/**
 * Created by ishestakov on 5/25/17.
 */
abstract class AbstractZetTask extends DefaultTask {

    Carnotzet carnotzet;
    ContainerOrchestrationRuntime runtime;
    boolean follow;
    String service;


    @TaskAction
    void exec() {
//        List<CarnotzetExtensionsFactory> factories = new ArrayList<>(0);
//        ServiceLoader.load(CarnotzetExtensionsFactory.class).iterator().forEachRemaining({e -> factories.add e});

        CarnotzetConfig config = CarnotzetConfig.builder()
                //TODO: replace artifact
                .topLevelModuleId(new CarnotzetModuleCoordinates(getProject().getGroup(), getProject().name, getProject().getVersion()))
                .resourcesPath(Paths.get(getProject().buildDir as String, "carnotzet"))
                .topLevelModuleResourcesPath(getProject().projectDir.toPath().resolve("src/main/resources"))
//                .extensions(findRuntimeExtensions(factories))
                .build();
        carnotzet = new Carnotzet(config);
//        runtime = new DockerComposeRuntime(carnotzet, getProject().configurations.carnotzet.instanceId);
        runtime = new DockerComposeRuntime(carnotzet, null); //TODO get it from configuration

        executeInternal();

    }

    abstract void executeInternal()

//    private List<CarnotzetExtension> findRuntimeExtensions(List<CarnotzetExtensionsFactory> factories) {
//        return factories.stream()
//                .map({factory -> factory.create(findExtensionFactoryProperties(factory))})
//                .collect(Collectors.toList());
//    }

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