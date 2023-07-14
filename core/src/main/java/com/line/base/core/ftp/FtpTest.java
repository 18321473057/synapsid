package com.line.base.core.ftp;

import com.jcraft.jsch.JSchException;

/**
 * @Author: yangcs
 * @Date: 2022/4/12 10:10
 * @Description:
 */
public class FtpTest {

    public static void main(String[] args) throws JSchException {
        FtpConfig fc = new FtpConfig();
        fc.setHost("192.168.52.59");
        fc.setPort(22);
        fc.setUserName("ftpuser");
        fc.setPassWord("ycs19930606");

        SftpClient client = new SftpClient(fc);
        client.connect();
//        client.upload("/home/ftp/ycs/0412",new File("D:\\info.log"),"ycs_info.log");
//        client.download("/home/ftp/ycs/0412","ycs_info.log","D:\\info111.log");
        client.delete("/home/ftp/ycs/0412" , "ycs_info.log");
    }
}
