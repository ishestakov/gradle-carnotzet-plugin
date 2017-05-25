package com.github.ishestakov.carnotzet.gradle.task

class ZetPsTask extends AbstractZetTask {

    @Override
    void executeInternal() {
        getRuntime().status();
    }
}