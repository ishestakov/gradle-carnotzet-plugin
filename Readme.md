# Gradle Carnotzet plugin.


The Gradle plugin version for [Carnotzet](https://github.com/swissquote/carnotzet) project.

## Configuration
Build script snippet for use in all Gradle versions:

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.ishestakov:impl:0.0.2"
  }
}

apply plugin: "com.github.ishestakov.carnotzet"
```
Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:

```
plugins {
  id "com.github.ishestakov.carnotzet" version "0.0.2"
}
```
The Carnotzet plugin extension configuration example:
```$groovy
carnotzet {
    moduleResourcesPath "src/main/resources"
    resourcesPath "carnotzet"
    moduleNameFilterRegex "^(.*)-(?:carnotzet|gradle-sandbox)$"
    dockerRegistry "https://docker.myorganization.com/"
}
```
|Extension property|Description|Default value|
| ---------------- |:----------|:-------------|
| moduleResourcesPath | The path to the foulder where Carnotzet resources are located | **mandatory** |
|resourcesPath| The path where the plugin-specific files will be located | /tmp/carnotzet_{random_value} |
|moduleNameFilterRegex| The regex for the Carnotzet module conventional name | "(.*)-carnotzet"|
|dockerRegistry| The docker regestr to pull the docker images | docker.io |


## Usage
The plugin add the following tasks:
```
$ ./gradlew tasks
...
Carnotzet tasks
---------------
zetAddrs - Shows the IP addresses for all running containers
zetClean - Remove resources for container(s)
zetLogs - Show logs for container(s)
zetPs - Show containers status
zetPull - Pull required images from docker registry
zetRestart - Restart the container(s)
zetRun - Run the container(s) and watch the output.
zetStart - Start the container(s).
zetStop - Stop the container(s).
...
```

For tasks `zetClean`, `zetLogs`, `zetPull`, `zetRestart`, `zetRun`, `zetStart`, `zetStop` you can provide an option `--service` to specify the service for which the task should apply:
```
$ ../gradlew zetStop --service=example
:zetStop
Stopping examplecarnotzet_example_1 ... done
> Building 0% > :zetStop
BUILD SUCCESSFUL

Total time: 1.243 secs
$ ../gradlew zetPs                    
:zetPs
stty: 'standard input': Inappropriate ioctl for device
              Name                            Command               State     Ports   
-------------------------------------------------------------------------------------
examplecarnotzet_example_1         docker-entrypoint.sh redis ...   Exit 0            
examplecarnotzet_postgres_1        docker-entrypoint.sh postgres    Up       5432/tcp 
examplecarnotzet_redis_1           docker-entrypoint.sh redis ...   Up       6379/tcp 
examplecarnotzet_voting-result_1   node server.js                   Up       80/tcp   
examplecarnotzet_voting-vote_1     gunicorn app:app -b 0.0.0. ...   Up       80/tcp   
examplecarnotzet_voting-worker_1   /bin/sh -c dotnet src/Work ...   Up                

BUILD SUCCESSFUL

Total time: 1.048 secs

```


