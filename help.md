# Help

### Running a simple command line example

Run `org.distributev.mailmerger.MailMerger` with `examples` as the working directory with program arguments `-f planets.csv -c planets-configuration.xml`.

You should find 9 files generated inside the `outputfolder` folder.


### Running the server

Running the server requires you to specify the location of the Gradle project which will watch a folder and generate reports.

Since this Gradle project is no a sub-module of the main `mailmerger` project it requires the `mailmerger-cmdline` dependency to be installed in the local Maven repository.  This can be achieved by running the following from the root `mailmerger` project:
```
./gradlew build install
```
or
```
./gradle.bat build install
``` 

You can then run `org.distributev.mailmerger.server.Application` with the following program arguments
```
--project.dir=/full/path/to/folder/containing/watch/build.gradle
--watch.dir=/full/path/to/folder/to/watch/for/data/files
--completed.dir=/full/path/to/folder/where/data/files/will/be/placed/when/completed
--config.file=/full/path/to/mailmerger/config/file
```


### Logging


By default mailmerger-cmdline with use the log4j2.xml file located in the project's resouces folder.  It is setup to log to 2 different files.

1. logs/info.log (will contain INFO level and above messages)
2. logs/errors.log (will contain ERROR level and above messages)

These files are set to roll daily and will compress the old files using the pattern `logs/errors-%d{MM-dd-yyyy}.log.gz`.

When the mailmerger-cmdline application is run the `logs` directory will be created (if it doesn't already exist) in the working directory where the application is run from.

There is the ability to use a different log4j2 configuration file by using the VM option `-Dlog4j.configurationFile=/path/to/custom/log4j2.xml`

mailmerger-cmdline will log an INFO level log entry for every file report file generates.  Non recoverable errors are propogated to the root of the application where they should be logged once at level ERROR and then the process will exit with a non-zero exit.
