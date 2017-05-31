package com.github.ishestakov.carnotzet.gradle.task

/**
 * Created by ishestakov on 5/25/17.
 */
public class ZetCleanTask extends AbstractZetTask {

    ZetCleanTask() {
        super(com.github.ishestakov.carnotzet.gradle.task.ZetCleanTask.class)
    }

    @Override
    void executeInternal() {
        getRuntime().clean();
    }
}