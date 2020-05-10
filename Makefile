MVN=mvn

jar: pom.xml
	$(MVN) clean compile assembly:single

run:
	cd target && java -jar *.jar && cd ..