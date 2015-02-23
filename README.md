# Backup

This is a description and architecture of backup solution, which will cover my personal needs.

## Terminology ##

* Client - host with data to backup.
* Data server - accessible from Internet linux server with big HDD mounted (not RAID). Clients store backups on data servers.
* Control server - stores information about backup solution. Data servers and clients sends information about themselves and work stats to control server. Control server have UI to overview backup solution in general. Also he is responsible to send alerts to admin. 

## Functional requirements ##

* There are few data types, which I need to backup. They can be discribed as:
  * Big, changes infrequently, important, long-lived. Like: personal images and videos. 
  * Small, changes regulary, important, long-lived. Like: documents, ids, scans, bills.
  * Small, not important, but sad to lose. Like: books, manuals.
  * Small, recoverable from other sources, need to be able to restore quickly. All related to work: source code, settings, portable apps, env configs.
* It should be possible to backup to:
  * Local data server.
  * Remote data server.
  * S3
* The backup should be incremental, if possible.
* Backup to S3 should be cost effective. I want to backup to S3 only really important data. I will read data from S3 only to recover data server.
* Need to support linux/win clients.
* Selective data replication across data servers.

## Brief solution description ##

* The core technology is RSYNC.
* Win clients uses rsync via cygwin.
* Client - java application, which calls rsync and sends usage statistics to control server.
* Data server - RaspberryPI with mounted external HDD + java app (very similar to client java app).
* Control server - REST API, js UI, minimal logic, used mostly to display data. Can not control data servers or clients. 

## Roadmap ##

* ~~Mount external HDD to RaspberryPI via powered USB hub.~~
* Setup cygwin + rsync in Win client. Verify cygwin+rsync => PI is working.
* Simple Java client.

## TOC ##

* [How to mount USB drive](mount-usb-drive.md)
* [How to setup data server](setup-data.server.md)
