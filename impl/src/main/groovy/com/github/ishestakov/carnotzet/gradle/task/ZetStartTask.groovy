package com.github.ishestakov.carnotzet.gradle.task

import org.gradle.api.Task;

class ZetStartTask extends AbstractZetTask {

    public ZetStartTask() {
        super(ZetStartTask.class)
        this.dependsOn.add("install");
    }

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