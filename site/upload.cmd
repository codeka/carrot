set GAE=D:\ide\eclipse\plugins\com.google.appengine.eclipse.sdkbundle.1.3.1_1.3.1.v201002101412\appengine-java-sdk-1.3.1\bin\
java -cp ../bin/ WebGenerator
call "%GAE%appcfg.cmd" update war
pause