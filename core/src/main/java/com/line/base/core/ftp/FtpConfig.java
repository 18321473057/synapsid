package com.line.base.core.ftp;

public class FtpConfig {

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 服务器端口
     */
    private int port;

    /**
     * 用户登录名
     */
    private String userName;

    /**
     * 用户登录密码
     */
    private String passWord;

    /**
     * 上传到 FTP 文件服务器上的文件路径
     */
    private String ftpServerFilePath;

    /**
     * 正常读取的文件存放目录
     */
    private String processFilePath;

    /**
     * 失败文件的存放目录
     */
    private String errorFilePath;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFtpServerFilePath() {
        return ftpServerFilePath;
    }

    public void setFtpServerFilePath(String ftpServerFilePath) {
        this.ftpServerFilePath = ftpServerFilePath;
    }

    public String getProcessFilePath() {
        return processFilePath;
    }

    public void setProcessFilePath(String processFilePath) {
        this.processFilePath = processFilePath;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }
}
