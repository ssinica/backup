set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_40
java -jar -Xms8M -Xmx32M -XX:PermSize=8M -XX:MaxPermSize=32M -Xss512K ./lib/bc-node-${project.version}.jar