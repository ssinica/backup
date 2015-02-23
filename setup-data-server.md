== Prepare data server ==

* Permit root login and allow logins via SSH only with certificate authorization
```
sudo nano /etc/ssh/sshd_config

>> PermitRootLogin no
>> PasswordAuthentication no
```
* Restart ssh
```
sudo service ssh restart
```

== Create user ==

* Add user ```bc```
```
sudo adduser bc
```
* On your client generate pub/private keys you will use to login to data server
```
ssh-keygen -t rsa -b 4096 -C bc@bc.com -f bc.backup
```
* Add public key to ```authorized_keys``` file
```
nano /home/bc/.ssh/authorized_keys
```

== Prepare backups root directory ==

```
sudo mkdir -p /data/backup
sudo chown -R bc:bc /data/backup
```
