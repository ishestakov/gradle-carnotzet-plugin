package com.github.ishestakov.carnotzet.gradle.task;

import com.github.swissquote.carnotzet.core.runtime.log.LogListener;
import com.github.swissquote.carnotzet.core.runtime.log.StdOutLogPrinter;

public class ZetStopTask extends AbstractZetServiceOrientedTask {

	String description = 'Stop the container(s).'

	@Override
	public void executeInternal() {
		if (isFollow()) {
			LogListener printer = new StdOutLogPrinter(getServiceNames(getCarnotzet()), 0, true);
			getRuntime().registerLogListener(printer);
		}

		if (getService() == null) {
			getRuntime().stop()
		} else {
			getRuntime().stop(getService())
		}
	}
}
