package com.github.ishestakov.carnotzet.gradle.task

class ZetStartTask extends AbstractZetServiceOrientedTask {

    String description = 'Start the container(s).';

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