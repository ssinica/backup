# Backup

This is a description and architecture of backup solution, which will cover my personal needs.

## Requirements ##

* There are few data types, which I need to backup. They can be discribed as:
  * Big, changes infrequently, important, long-lived. Like: personal images and videos. 
  * Small, changes regulary, important, long-lived. Like: documents, ids, scans, bills.
  * Small, not important, but sad to lose. Like: books, manuals.
  * Small, recoverable from other sources, need to be able to restore quickly. All related to work: source code, settings, portable apps, env configs.
* It should be possible to backup to:
  * Local NAS (NAS - linux with mounted big drive. Not a RAID)
  * Remote NAS. Accessible other Internet.
  * S3
* The backup should be incremental
* Backup to S3 should be cost effective. I want to backup to S3 only really important data. I will read data from S3 only to recover NAS.
* Support WIN backup clients.
* Backup stats in one web interface.
* Alerts if something goes wrong.

