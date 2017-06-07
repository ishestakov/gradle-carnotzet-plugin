package com.github.ishestakov.carnotzet.gradle.task

/**
 * Created by ishestakov on 5/25/17.
 */
public class ZetCleanTask extends AbstractZetTask {

    String description = 'Remove all non-running containers';

    @Override
    void executeInternal() {
        getRuntime().clean();
    }
}