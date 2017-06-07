package com.github.ishestakov.carnotzet.gradle

import com.github.ishestakov.carnotzet.gradle.domain.GradleCarnotzetExtension
import com.github.ishestakov.carnotzet.gradle.task.*
import com.github.swissquote.carnotzet.core.Carnotzet
import org.gradle.api.Plugin
import org.gradle.api.Project

class CarnotzetPlugin implements Plugin<Project> {
    Carnotzet carnotzet;

    @Override
    void apply(Project project) {
        project.extensions.create("carnotzet", GradleCarnotzetExtension, project)
        project.task("zetStart", type: ZetStartTask)
        project.task("zetRestart", type: ZetRestartTask)
        project.task("zetStop", type: ZetStopTask)
        project.task("zetRun", type: ZetRunTask)
        project.task("zetClean", type: ZetCleanTask)
        project.task("zetLogs", type: ZetLogsTask)
        project.task("zetPull", type: ZetPullTask)
        project.task("zetPs", type: ZetPsTask)
        project.task("zetAddrs", type: ZetAddressesTask)
    }
}