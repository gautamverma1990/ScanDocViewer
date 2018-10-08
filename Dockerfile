################################################################
## Dockerfile template for Tomcat based services
################################################################
FROM tomcat:8.0.43-jre8-alpine
MAINTAINER gautamverma.office@gmail.com
ENV CATALINA_OPTS "-Djava.security.egd=file:/dev/./urandom -Duser.timezone=UTC"
## remove unused apps see https://hub.docker.com/r/rossbachp/apache-tomcat8/~/dockerfile/
RUN rm -rf /usr/local/tomcat/webapps/examples /usr/local/tomcat/webapps/docs /usr/local/tomcat/webapps/ROOT \
           /usr/local/tomcat/webapps/host-manager /usr/local/tomcat/webapps/manager /usr/local/tomcat/RELEASE-NOTES \
           /usr/local/tomcat/RUNNING.txt /usr/local/tomcat/bin/*.bat  /usr/local/tomcat/bin/*.tar.gz && \
           mkdir -p /usr/local/tomcat/security
#COPY target/ojdbc7-ssl.jar /usr/local/tomcat/lib/
#COPY target/*.jks /usr/local/tomcat/security/
#COPY context.xml /usr/local/tomcat/conf/
COPY logging.properties /usr/local/tomcat/conf/
ADD target/*.war /usr/local/tomcat/webapps/
