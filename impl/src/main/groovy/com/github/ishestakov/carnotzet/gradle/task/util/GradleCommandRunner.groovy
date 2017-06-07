package com.github.ishestakov.carnotzet.gradle.task.util

import com.github.swissquote.carnotzet.core.runtime.CommandRunner
import org.gradle.process.ExecResult
import org.gradle.process.internal.ExecAction
import org.gradle.process.internal.ExecActionFactory

class GradleCommandRunner implements CommandRunner {

    private final ExecActionFactory actionFactory;

    GradleCommandRunner(ExecActionFactory actionFactory) {
        this.actionFactory = actionFactory
    }

    @Override
    int runCommand(String... command) {
        return runCommand(new File("/"), command)

    }

    @Override
    int runCommand(Boolean inheritIo, String... command) {
        return runCommand(new File("/"), command)

    }

    @Override
    int runCommand(File directoryForRunning, String... command) {
        ExecResult execResult =configure(directoryForRunning, command).execute();
        return execResult.exitValue;
    }

    @Override
    int runCommand(Boolean inheritIo, File directoryForRunning, String... command) {
        ExecResult execResult =configure(directoryForRunning, command).execute();
        return execResult.exitValue;
    }

    @Override
    String runCommandAndCaptureOutput(String... command) {
        return runCommandAndCaptureOutput(new File("/"), command);
    }

    @Override
    String runCommandAndCaptureOutput(File directoryForRunning, String... command) {
        ExecAction action = configure(directoryForRunning, command);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream()
        action.setStandardOutput(byteStream);
        action.execute();
        return byteStream.toString();
    }

    private ExecAction configure(File workingDirectory, String... command) {
        ExecAction action = this.actionFactory.newExecAction();
        action.commandLine command;
        action.workingDir workingDirectory;
        action.setStandardInput(System.in);
        return action;

    }
}
