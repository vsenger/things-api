CLASSPATH=%JAVA_HOME%/jre/lib/jfxrt.jar
CLASSPATH=%CLASSPATH;lib/cglib-2.1.88.jar
CLASSPATH=%CLASSPATH;lib/guava-14.0.1.jar
CLASSPATH=%CLASSPATH;lib/hk2-api-2.1.88.jar
CLASSPATH=%CLASSPATH;lib/hk2-locator-2.1.88.jar
CLASSPATH=%CLASSPATH;lib/hk2-utils-2.1.88.jar
CLASSPATH=%CLASSPATH;lib/javax.inject-2.1.88.jar
CLASSPATH=%CLASSPATH;lib/javax.ws.rs-api-2.0.jar
CLASSPATH=%CLASSPATH;lib/jersey-client.jar
CLASSPATH=%CLASSPATH;lib/jersey-common.jar
CLASSPATH=%CLASSPATH;AutomationFX.jar

java -Dcom.sun.javafx.isEmbedded=false -Djavafx.platform=eglfb -cp %CLASSPATH% automationfx.AutomationFX
