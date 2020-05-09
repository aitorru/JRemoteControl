MVN=mvn

build: pom.xml
	$(MVN) package

run:
	cd target && java -jar *.jar && cd ..