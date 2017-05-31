package com.github.ishestakov.carnotzet.gradle.task

class ZetPsTask extends AbstractZetTask {

    ZetPsTask() {
        super(com.github.ishestakov.carnotzet.gradle.task.ZetPsTask.class)
    }

    @Override
    void executeInternal() {
        getRuntime().status();
    }
}