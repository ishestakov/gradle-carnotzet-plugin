package groovy.com.github.ishestakov.carnotzet.gradle.test.functional

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.Unroll

public class TasksSpec extends Specification {


    def buildFile = new File("../example-carnotzet/build.gradle");

    def setup() {
        stopContainers()
    }

    @Unroll
    def "zetStart task execution with Gradle #gradleVersion"() {
        when:
        def result = GradleRunner.create()
                .withProjectDir(buildFile.toPath().getParent().toFile())
                .withArguments("install", "zetStart")
                .withPluginClasspath()
                .withGradleVersion(gradleVersion)
                .build();

        then:
        result.output.contains("Starting");
        containerNames().size() == 6
        result.task(":zetStart").outcome == TaskOutcome.SUCCESS;
        where:
        gradleVersion << ["3.3", "2.8"]

    }

    @Unroll
    def "zetStop task execution with Gradle #gradleVersion"() {
        given:
        def startBuild = GradleRunner.create()
                .withProjectDir(buildFile.toPath().getParent().toFile())
                .withArguments("install", "zetStart")
                .withPluginClasspath()
                .build();
        expect:
        startBuild.task(':zetStart').outcome == TaskOutcome.SUCCESS;

        when:
        def result = GradleRunner.create()
                .withProjectDir(buildFile.toPath().getParent().toFile())
                .withArguments("install", "zetStop")
                .withPluginClasspath()
                .withGradleVersion(gradleVersion)
                .build();

        then:
        for (def containerName : containerNames()) {
            result.output.contains("Stopping ${containerName} ... done");
        }
        result.task(":zetStop").outcome == TaskOutcome.SUCCESS;

        where:
        gradleVersion << ["3.3", "2.8"]
    }

    List<String> containerNames() {
        def process = new ProcessBuilder()
                .command("docker", "ps", "--filter", "name=examplecarnotzet", "--format", "{{.Names}}")
                .start()
        process.waitFor()
        Scanner s = new Scanner(process.getInputStream(), "UTF-8")
        return (s.useDelimiter("\\A").hasNext() ? s.next() : "").split("\n") as List
    }

    List<String> containerIds() {
        def process = new ProcessBuilder()
                .command("docker", "ps", "--filter", "name=examplecarnotzet", "-q")
                .start()
        process.waitFor()
        Scanner s = new Scanner(process.getInputStream(), "UTF-8")
        return (s.useDelimiter("\\A").hasNext() ? s.next() : "").split("\n") as List
    }

    def stopContainers() {
        containerIds().stream().forEach({
            new ProcessBuilder().command("docker", "stop", it).start().waitFor();
        })
    }
}
