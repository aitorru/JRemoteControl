MVN=mvn

PLATFORM = $(shell uname -s)
EXT = $(osdetected)
ifeq ($(OS),Windows_NT)
	PLATFORM = win-x64
else
	PLATFORM = linux-x64
endif

jar: pom.xml
	$(MVN) clean compile assembly:single

exe: Updator/Program.cs
	cd Updator && dotnet publish -r $(PLATFORM) -c Release /p:PublishSingleFile=true -o runner/$(PLATFORM)

all: jar exe

run:
	cd target && java -jar *.jar && cd ..

clean:
	$(MVN) clean