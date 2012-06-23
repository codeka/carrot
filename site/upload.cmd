set GAE=D:\IDE\eclipse\plugins\com.google.appengine.eclipse.sdkbundle_1.6.6\appengine-java-sdk-1.6.6\bin\
java -cp ../bin/ WebGenerator
call "%GAE%appcfg.cmd" update war
pause