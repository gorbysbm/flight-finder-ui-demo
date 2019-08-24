## Installing and Running on Mac OSX instructions


* install ```brew``` if not present:

```
        /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
 
* install ```chromedriver``` 

```
        brew tap homebrew/cask && brew cask install chromedriver
```

### Running the Tests: 

* navigate to this project's root directory and run this command while specifying the `testSuite` file: 
``` 
        gradlew clean test -PtestSuiteFile=testng-functional.xml
```
NOTE: You can see detailed test reports at: ```${projectDir}/build/tesngOutput```

=========================================================
## Installing and Running with Docker Instructions


* install ```docker``` if not present:

```
        https://www.docker.com/
```

### Running the tests with Docker: 

* download / start selenium-chrome-debug container: 
``` 
  docker run -d -p 4444:4444 -p 5900:5900 -v /dev/shm:/dev/shm -e VNC_NO_PASSWORD=1 selenium/standalone-chrome-debug:latest
```

* navigate to this project's root directory and run: 
``` 
        gradlew clean test -PtestSuiteFile=testng-functional-docker.xml
```
NOTE: This is a ```vnc``` enabled container. You can connect to  ```localhost:5900``` to see the test execution