package com.github.ishestakov.carnotzet.gradle.task

/**
 * Created by ishestakov on 5/31/17.
 */
class ZetRestartTask extends AbstractZetTask {

    String description = 'Restart the container(s). You can specify concrete container using -Pservice=${service.name}';

    @Override
    void executeInternal() {
        if (getService()) {
            getRuntime().stop(getService());
            getRuntime().start(getService())
        } else {
            getRuntime().stop();
            getRuntime().start()
        }
    }
}
