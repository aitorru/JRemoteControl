# JRemoteControl
![Travis (.com)](https://img.shields.io/travis/com/aitorru/JRemoteControl) ![GitHub All Releases](https://img.shields.io/github/downloads/aitorru/JRemoteControl/total) ![GitHub last commit](https://img.shields.io/github/last-commit/aitorru/JRemoteControl) ![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/aitorru/JRemoteControl)

Control other computers from one.

## WIP
- [X] Updater
- [ ] Cryptography
- [ ] Command exec
- [ ] File sending
- [ ] Created Server for communications
- [ ] GUI for admin?
- [ ] More to come...
## How to build it
> You need Maven to build this project
```bash
make jar
```
or
```bash
mvn clean compile assembly:single
```
## How to run it
> WIP: I might add a maven plugin to create de executable directly
```bash
make run
```
or you can just.
```bash
java -jar *.jar
```
You need to cd yourself into **/target**
