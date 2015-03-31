> NOTE: assuming you are using Debian based OS

* Find attached USB drive
  * Print block devices attributes
  ```
sudo blkid

/dev/mmcblk0p1: SEC_TYPE="msdos" LABEL="boot" UUID="7D5C-A285" TYPE="vfat"
/dev/mmcblk0p2: UUID="5d18be51-3217-4679-9c72-a54e0fc53d6b" TYPE="ext4"
/dev/sda1: UUID="CAC89AB5C89A9F6F" TYPE="ntfs"
/dev/sda2: LABEL="SAMSUNG_REC" UUID="866E65C26E65AB9D" TYPE="ntfs"
```
  * Print discs partitions tables
  ```
sudo fdisk -l
Disk /dev/sda: 1000.2 GB, 1000204886016 bytes
255 heads, 63 sectors/track, 121601 cylinders, total 1953525168 sectors
Units = sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disk identifier: 0x2cb9475b
   Device Boot      Start         End      Blocks   Id  System
/dev/sda1            2048  1908574207   954286080    7  HPFS/NTFS/exFAT
/dev/sda2      1908576256  1953523711    22473728   27  Hidden NTFS WinRE
```
  * As you can see target USB hard drive is ```/dev/sda```
* Format disc to ext4 if required
  * Check if disc is formatted
  ```
sudo file -s /dev/sda
```
  * Format to ext4
  ```
sudo mkfs -t ext4 /dev/sda
```
* Mount disc
  * Create a mount endpoint
  ```
sudo mkdir -p /data
```
  * Mount
  ```
sudo mount /dev/sda /data
```
  * Make sure disc will be auto mounted on restarts
  ```
sudo echo '/dev/sda       /data   ext4    defaults,nofail        0       2' >> /etc/fstab
```
  * Check if mounting is configured OK
  ```
sudo mount -a
```
* Restart system and check
```
sudo shutdown -r now
sudo df -h
```
