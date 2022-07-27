#!/bin/sh

mvn clean package && cp -R target/Cinema.war ~/apache-tomcat-9.0.55/webapps/ && sh ~/apache-tomcat-9.0.55/bin/catalina.sh run