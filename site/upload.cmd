set GAE=D:/ide/eclipse/plugins/com.google.appengine.eclipse.sdkbundle.1.3.0_1.3.0.v200912141120/appengine-java-sdk-1.3.0/bin/
java -cp ../bin/ WebGenerator
call "%GAE%appcfg.cmd" update war
pause