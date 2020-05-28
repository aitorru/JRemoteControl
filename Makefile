MVN=mvn

PLATFORM = $(shell uname -s)
EXT = $(osdetected)
ifeq ($(OS),Windows_NT)
	PLATFORM = win-x64
	MOVE=move
else
	PLATFORM = linux-x64
	MOVE=mv
endif

jar: pom.xml
	$(MVN) clean compile assembly:single && \
	$(MOVE) target/*.jar JRemoteControl.jar


exe: Updator/Program.cs
	cd Updator && \
	dotnet publish -r $(PLATFORM) -c Release /p:PublishSingleFile=true -o runner/$(PLATFORM) && \
	cd ../ServerStarter && \
	dotnet publish -r $(PLATFORM) -c Release /p:PublishSingleFile=true -o runner/$(PLATFORM) && \
	$(MOVE) runner/$(PLATFORM)/Server* ../

all: jar exe

run:
	ServerStarte*

clean:
	$(MVN) clean