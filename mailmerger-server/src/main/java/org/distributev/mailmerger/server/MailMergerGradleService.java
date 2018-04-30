package org.distributev.mailmerger.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Starts Gradle to run the {@code mailMerger} task
 */
@Service
public class MailMergerGradleService {

    private static final Logger logger = LogManager.getLogger(MailMergerGradleService.class);

    @Value("${project.dir}")
    private String projectDir;

    @Value("${config.file}")
    private String configFile;

    @Value("${watch.dir}")
    private String watchDir;

    @Value("${completed.dir}")
    private String completedDir;

    /**
     * Runs the Gradle mailMerger task in continuous mode
     */
    @Async
    public void startGradleWatch() {
        ProjectConnection connection =
            GradleConnector.newConnector().forProjectDirectory(new File(projectDir)).connect();
        try {
            connection.newBuild()
                      .forTasks("mailMerger")
                      .withArguments("--continuous", "-PwatchDir=" + watchDir, "-PcompletedDir=" + completedDir, "-PconfigFile=" + configFile)
                      .run();
        } catch (Exception e) {
            logger.error("Error running Grade task", e);
        } finally {
            connection.close();
        }
    }
}
