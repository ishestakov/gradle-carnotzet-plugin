package com.github.ishestakov.carnotzet.gradle.task

import com.github.swissquote.carnotzet.core.runtime.api.Container
import com.github.swissquote.carnotzet.core.runtime.api.ContainerOrchestrationRuntime
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.tools.ant.taskdefs.Ant
import org.gradle.api.logging.Logger

/**
 * Created by ishestakov on 5/25/17.
 */
class ZetShellTask extends AbstractZetTask {

    ZetShellTask() {
        super(com.github.ishestakov.carnotzet.gradle.task.ZetShellTask.class)
    }

    @Override
    void executeInternal() {
        execute(getRuntime(), getLogger(), getService(), getAnt());
    }

    static void execute(ContainerOrchestrationRuntime runtime, Logger log, String service, AntBuilder ant)
            throws MojoExecutionException, MojoFailureException {
        List<Container> containers = runtime.getContainers();
        if (containers.isEmpty()) {
            log.info("There doesn't seem to be any containers created yet for this carnotzet, please make sure the carnotzet is started");
            return;
        }
        Container container = containers.stream().filter({ c -> c.getServiceName().equals(service) }).findFirst().orElse(null);
        if (container == null) {
            container = promptForContainer(containers, log, ant);
        }

        runtime.shell(container);
    }

    /**
     * Lists services and prompts the user to choose one
     */
    private static Container promptForContainer(List<Container> containers, Logger log, AntBuilder ant) throws MojoExecutionException {

        log.info("");
        log.info("SERVICE");
        log.info("");
        Map<Integer, Container> options = new HashMap<>();
        Integer i = 1;

        for (Container container : containers) {
            options.put(i, container);
            log.info(String.format("%2d", i) + " : " + container.getServiceName());
            i++;
        }
        log.info("");
        ant.input(message: 'Enter container ID:', addproperty: 'serviceInput', defaultValue : '')
        def prompt = ant.properties.serviceInput
        return options.get(Integer.valueOf(prompt));
    }

}