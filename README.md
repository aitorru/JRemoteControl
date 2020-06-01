# JRemoteControl
![Travis (.com)](https://img.shields.io/travis/com/aitorru/JRemoteControl) ![GitHub All Releases](https://img.shields.io/github/downloads/aitorru/JRemoteControl/total) ![GitHub last commit](https://img.shields.io/github/last-commit/aitorru/JRemoteControl) ![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/aitorru/JRemoteControl)

Control other computers from one.

## Notes about the WIP

## WIP
- [X] Updater
- [X] Cryptography
- [X] Command exec
- [X] File sending
- [ ] Created Server for communications
- [ ] GUI for admin?
- [ ] More to come...

## Notes about the WIP

The updater for now only have binaries for linux. Windows will come.

The criptography is not using SSL but proper RSA.

The command exec does not return any status.

## How to build it
> You need Maven and dotnet to build this project
```bash
make all
```
## How to run it
You need to run the ServerStarter to make sure the app is uploaded
```bash
./ServerStarter.out
```
or
```bash
ServerStarter.exe
```
