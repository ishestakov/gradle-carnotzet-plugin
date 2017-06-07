package com.github.ishestakov.carnotzet.gradle.task

import org.gradle.process.internal.CurrentProcess

/**
 * Created by ishestakov on 5/25/17.
 */
class ZetPullTask extends AbstractZetTask {

    String description = 'Pull required images from docker registry';

    @Override
    void executeInternal() {
        getRuntime().pull()
    }
}
