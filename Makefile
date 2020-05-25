MVN=mvn

jar: pom.xml
	$(MVN) clean compile assembly:single

exe: Updator/Program.cs
	cd updator && dotnet publish -o runner

all: jar exe

run:
	cd target && java -jar *.jar && cd ..

clean:
	$(MVN) clean