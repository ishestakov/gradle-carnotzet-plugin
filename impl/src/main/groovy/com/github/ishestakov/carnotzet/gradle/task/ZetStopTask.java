package com.github.ishestakov.carnotzet.gradle.task;

import com.github.swissquote.carnotzet.core.runtime.log.LogListener;
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter;

/**
 * Created by ishestakov on 5/25/17.
 */
public class ZetStopTask extends AbstractZetTask {

	@Override
	public void executeInternal() {
		if (isFollow()) {
			LogListener printer = new StdOutLogPrinter(getServiceNames(getCarnotzet()), 0, true);
			getRuntime().registerLogListener(printer);
		}

		if (getService() == null) {
			getRuntime().stop();
		} else {
			getRuntime().stop(getService());
		}
	}
}
