# mailmerger

The scope of this project is to build a simple prototype around - https://github.com/cuba-platform/yarg

The code will be written in Java8 using Gradle as a build system.

The project will have 2 sub-folders (sub-projects for gradle) 

<ol>
<li>mailmerger-cmdline</li><br>
<li>mailmerger-server</li>
</ol>

Both folders will follow the standard Maven project folder structure - https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html

The main functionality will be built in mailmerger-cmdline (as a command line application) while mailmerger-server should only be a light "server" wrapper around the previous functionality from mailmerger-cmdline

Both mailmerger-cmdline and mailmerger-server will be built using standard Gradle multi-project (mailmerger-server will have mailmerger-cmdline defined as dependency)

**mailmerger-cmdline**

mailmerger-cmdline will contain standard Junit 5 tests to prove the functionality works fine.

mailmerger-cmdline will be a straightforward wrapper around yarg reporting engine and will

<ul>
<li>parse an input CSV file containing report data rows</li>
<li>for each row in the input CSV generate 1 ouput PDF file using yarg - for instance if input CSV has 4 rows this will generate 4 output PDF files, one per input CSV row</li>
</ul>

You could look at the most basic yarg sample (yarg comes with samples) and build mailmerger-cmdline around that sample.

MailMerge command line usage will be the following

<ul>
<li>-c, --c path_to_xml_configuration_file - Optional argument, if not provided a "default" configuration file will be used</li>
<li>-f, --file path_to_csv_data_file - Required argument with the path to the CSV containing report rows</li>
</ul>

Libraries / Dependencies

	https://github.com/cuba-platform/yarg (Report Generator)
	https://commons.apache.org/proper/commons-csv/ (csv parser)
	https://freemarker.apache.org (templating)
	https://commons.apache.org/proper/commons-cli/ (command line)
	https://github.com/google/guava (utilities library - Google core libraries for Java)
	https://commons.apache.org/proper/commons-configuration/ ( generic configuration interface)
	https://logging.apache.org/log4j/2.x/ (logging library)

**mailmerger-server**

mailmerger-server will basically take the previous mailmerger-cmdline functionality and "decorate" this as a "server" app which 
will always "watch" a specified folder for new CSV files to process. Whenever a new CSV file is found in the "poll" folder it will be picked up and processed automatically. That's all. gradle-watch-plugin will be used to "watch" the "poll" folder

Libraries / Dependencies

	spring-boot-2 (with groovy as language) - https://start.spring.io
	https://gradle.org 
	https://github.com/bluepapa32/gradle-watch-plugin (Run predefined tasks whenever watched file patterns are added, changed or deleted.)

**XML configuration file**







 
