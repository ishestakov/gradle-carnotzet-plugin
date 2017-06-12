package com.github.ishestakov.carnotzet.gradle.task;

import org.gradle.api.internal.tasks.options.Option;


public abstract class AbstractZetServiceOrientedTask extends AbstractZetTask {
    @Option(option = "service", description = "Service to apply the task")
    String service;
}
