package com.github.ishestakov.carnotzet.gradle.task

/**
 * Created by ishestakov on 5/25/17.
 */
class ZetPullTask extends AbstractZetTask {

    ZetPullTask() {
        super(ZetPullTask.class)
    }

    @Override
    void executeInternal() {
        getRuntime().pull()
    }
}
