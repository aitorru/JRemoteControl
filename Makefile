MVN=mvn

ifeq ($(OS),Windows_NT)
	PLATFORM = win-x64
	OUT=ServerStarter
	PRE=.exe
	EXT=.exe
	MOVE=move
	SLICE=\\
	DELETE=del /Q
	
else
	PLATFORM = linux-x64
	OUT=ServerStarter
	MOVE=mv
	PRE=
	EXT=.out
	SLICE=/
	DELETE=rm
endif

jar: pom.xml
	$(MVN) clean compile assembly:single && \
	$(MOVE) target$(SLICE)*.jar JRemoteControl.jar


exe: Updator/Program.cs
	cd Updator && \
	dotnet publish -r $(PLATFORM) -c Release /p:PublishSingleFile=true -o runner/$(PLATFORM) && \
	cd ../ServerStarter && \
	dotnet publish -r $(PLATFORM) -c Release /p:PublishSingleFile=true -o runner/$(PLATFORM) && \
	$(MOVE) runner$(SLICE)$(PLATFORM)$(SLICE)$(OUT)$(PRE) ..$(SLICE)$(OUT)$(EXT)

all: jar exe

run:
	$(OUT)$(EXT)

clean:
	$(MVN) clean && \
	$(DELETE) logs$(SLICE)*