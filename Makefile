MVN=mvn

jar: pom.xml
	$(MVN) clean compile assembly:single

exe: pom.xml
	$(MVN) clean compile assembly:single launch4j:launch4j

run:
	cd target && java -jar *.jar && cd ..

clean:
	$(MVN) clean