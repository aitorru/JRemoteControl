MVN=mvn

build: pom.xml
	$(MVN) clean compile assembly:single

run:
	cd target && java -jar *.jar && cd ..