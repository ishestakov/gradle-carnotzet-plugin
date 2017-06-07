package com.github.ishestakov.carnotzet.gradle.domain

import org.gradle.api.Project

class GradleCarnotzetExtension {
    private final Project project;
    def resourcesPath;
    def moduleResourcesPath;
    def moduleNameFilterRegex
    def dockerRegistry
    def propertyFiles

    GradleCarnotzetExtension(Project project) {
        this.project = project
        resourcesPath = project.buildDir.toPath().resolve("carnotzet").resolve(project.name).toString()
        moduleResourcesPath = "src/main/resources"
        moduleNameFilterRegex
    }


}
