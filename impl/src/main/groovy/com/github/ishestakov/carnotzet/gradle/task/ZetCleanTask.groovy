package com.github.ishestakov.carnotzet.gradle.task

public class ZetCleanTask extends AbstractZetServiceOrientedTask {

    String description = 'Remove resources for container(s)';


    @Override
    void executeInternal() {
        if (getService()) {
            getRuntime().clean(getService());
        } else {
            getRuntime().clean();
        }
    }
}