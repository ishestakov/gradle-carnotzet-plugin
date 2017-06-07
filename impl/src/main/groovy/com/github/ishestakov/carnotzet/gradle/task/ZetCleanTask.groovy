package com.github.ishestakov.carnotzet.gradle.task

/**
 * Created by ishestakov on 5/25/17.
 */
public class ZetCleanTask extends AbstractZetTask {

    String description = 'Remove resources for container(s). You can specify concrete container using -Pservice=${service.name}';

    @Override
    void executeInternal() {
        if (getService()) {
            getRuntime().clean(getService());
        } else {
            getRuntime().clean();
        }
    }
}