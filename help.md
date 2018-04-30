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
