#run this when JDK installed with oprtable form 


keytool -v -alias mavensrv -import -file C:\Users\10684924\.m2/maven.cert -keystore trust.jks
mvn -DskipTests clean install -Djavax.net.ssl.trustStore=D:\openjdk-8u41-b04-windows-i586-14_jan_2020\java-se-8u41-ri\jre\lib\security/cacerts