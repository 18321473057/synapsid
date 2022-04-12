- 安装vsftp
sudo apt install vsftpd
- 修改配置文件
sudo vim /etc/vsftpd.conf


部分-用户限制
#需要设置指定的根目录 才能限制切换目录
local_root=/home/ftp

#所有用户都禁止访问指定目录外的目录
chroot_local_user=YES
chroot_list_enable=NO

#部分用户被限制在监狱内
#chroot_local_user=NO
#chroot_list_enable=YES

#部分用户不被限制在监狱内
#chroot_local_user=YES
#chroot_list_enable=YES






- 创建 ftpuser 用户
sudo useradd -d /home/ftp -s /bin/bash ftpuser
- 给 ftpuser添加密码
sudo passwd ftpuser
- 创建 ftpuser的家目录并将其所属用户和所属组改为 ftpuser
sudo mkdir /home/ftp
sudo chown ftpuser:ftpuser /home/ftp
- 修改 /home/ftp 
sudo chmod 777  /home/ftp
- 重启vsftp服务
sudo service vsftpd restart





- sftp协议连接主机
- 任意用户都可以登录
- 用户属于配置的用户组 进去目录就是/home/ftp;  
- 限制用户在ftp目录内