package groovy.com.github.ishestakov.carnotzet.gradle.test

import com.github.ishestakov.carnotzet.gradle.CarnotzetPlugin
import com.github.ishestakov.carnotzet.gradle.task.ZetAddressesTask
import com.github.ishestakov.carnotzet.gradle.task.ZetCleanTask
import com.github.ishestakov.carnotzet.gradle.task.ZetLogsTask
import com.github.ishestakov.carnotzet.gradle.task.ZetPsTask
import com.github.ishestakov.carnotzet.gradle.task.ZetPullTask
import com.github.ishestakov.carnotzet.gradle.task.ZetRestartTask
import com.github.ishestakov.carnotzet.gradle.task.ZetRunTask
import com.github.ishestakov.carnotzet.gradle.task.ZetStartTask
import com.github.ishestakov.carnotzet.gradle.task.ZetStopTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ApplyPluginTest extends Specification {

    def "apply gradle Carnotzet plugin"() {
        setup:
        Project project = ProjectBuilder.builder().build();
        project.pluginManager.apply CarnotzetPlugin
        expect:
            taskClass.isInstance project.tasks[taskName]
        where:
            taskName    || taskClass
            'zetStart'  || ZetStartTask
            'zetRun'    || ZetRunTask
            'zetStop'   || ZetStopTask
            'zetRestart'|| ZetRestartTask
            'zetPs'     || ZetPsTask
            'zetClean'  || ZetCleanTask
            'zetAddrs'  || ZetAddressesTask
            'zetLogs'   || ZetLogsTask
            'zetPull'   || ZetPullTask
    }
}
