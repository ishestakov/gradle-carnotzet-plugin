package com.github.ishestakov.carnotzet.gradle.task
/**
 * Created by ishestakov on 5/25/17.
 */
class ZetPullTask extends AbstractZetServiceOrientedTask {

    String description = 'Pull required images from docker registry';

    @Override
    void executeInternal() {
        if (getService()) {
            getRuntime().pull(getService())
        } else {
            getRuntime().pull()
        }
    }
}
