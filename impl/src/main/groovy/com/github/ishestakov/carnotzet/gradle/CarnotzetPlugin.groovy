package com.github.ishestakov.carnotzet.gradle

import com.github.ishestakov.carnotzet.gradle.task.*
import com.github.swissquote.carnotzet.core.Carnotzet
import org.gradle.api.Plugin
import org.gradle.api.Project

class CarnotzetPlugin implements Plugin<Project> {
    Carnotzet carnotzet;

    @Override
    void apply(Project project) {
        project.configurations.create("carnotzet")
        project.task("zetStart", type: ZetStartTask)
        project.task("zetRestart")

        project.task("zetStop", type: ZetStopTask)

        project.task("zetRun", type: ZetRunTask)

        project.task("zetClean", type: ZetCleanTask)

        project.task("zetLogs", type: ZetLogsTask)
        project.task("zetShell", type: ZetShellTask)

        project.task("zetPull", type: ZetPullTask)

        project.task("zetPs", type: ZetPsTask)

        project.task("zetAddresses", type: ZetAddressesTask)

    }
}