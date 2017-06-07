package com.github.ishestakov.carnotzet.gradle.task

import org.gradle.api.Task;

class ZetStartTask extends AbstractZetTask {

    String description = 'Start the container(s). You can specify concrete container using -Pservice=${service.name}';

    @Override
    void executeInternal() {
        wrapWithLogFollowIfNeeded(command).run();
    }

    private Runnable command = {
        if (getService() == null) {
            getRuntime().start();
        } else {
            getRuntime().start(getService());
        }
    }

}