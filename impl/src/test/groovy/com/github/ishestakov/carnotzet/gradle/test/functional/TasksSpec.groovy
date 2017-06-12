package groovy.com.github.ishestakov.carnotzet.gradle.test.functional

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome;
import spock.lang.Specification;

public class TasksSpec extends Specification {



	def buildFile = new File("../example-carnotzet/build.gradle");

	def "zetStart task execution"() {
		when:
			def result = GradleRunner.create()
					.withProjectDir(buildFile.toPath().getParent().toFile())
		.withArguments("install", "zetStart")
		.withPluginClasspath()
		.build();

		then:
			result.output.contains('Hello!');
			result.task(":zetStart").outcome == TaskOutcome.SUCCESS;
	}

	def "zetStop task execution"() {
		when:
		def result = GradleRunner.create()
				.withProjectDir(buildFile.toPath().getParent().toFile())
				.withArguments("install", "zetStop")
				.withPluginClasspath()
				.build();

		then:
		result.output.contains('Hello!');
		result.task(":zetStop").outcome == TaskOutcome.SUCCESS;
	}
}
