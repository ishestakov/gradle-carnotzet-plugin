package com.github.ishestakov.carnotzet.gradle.task

class ZetPsTask extends AbstractZetTask {

    String description = 'Show containers status';

    @Override
    void executeInternal() {
        getRuntime().status();
    }
}