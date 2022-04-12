package com.line.base.core.ftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * @author DINGYONG
 * @version 1.0
 * @title SftpClient
 * @package com.hoau.zodiac.core.util.network
 * @description
 * @date 2017/11/18
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SftpClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final FtpConfig ftpConfig;
    /**
     * sftp连接通道对象
     */
    private Channel channel = null;

    /**
     * session对象
     */
    private Session sshSession = null;

    /**
     * sftp操作通道对象
     */
    private ChannelSftp sftp = null;


    public SftpClient(FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    /**
     * 连接sftp服务器
     *
     * @return
     */
    public void connect() throws JSchException {
        JSch jsch = new JSch();
        sshSession = jsch.getSession(ftpConfig.getUserName(),
                ftpConfig.getHost(), ftpConfig.getPort());
        ;
        sshSession.setPassword(ftpConfig.getPassWord());
        sshSession.setConfig(new Properties() {{
            put("StrictHostKeyChecking", "no");
        }});
        sshSession.connect();
        channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
    }

    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory) {
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            return sftpATTRS.isDir();
        } catch (SftpException e) {
            logger.error("sftp服务器判断文件夹是否存在发生异常,{}", e);
        }
        return false;
    }

    /**
     * 创建目录
     */
    public void createDir(String createPath) {
        try {
            if (isDirExist(createPath)) {
                sftp.cd(createPath);
                return;
            }

            String[] pathArray = createPath.split("/");
            StringBuilder filePath = new StringBuilder("/");
            for (String path : pathArray) {
                if ("".equals(path)) {
                    continue;
                }
                filePath.append(path).append("/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
        } catch (Exception e) {
            logger.error("sftp服务器创建目录失败,{}", e);
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public List<ChannelSftp.LsEntry> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     */
    public void upload(String directory, File uploadFile, String fileName) {
        try {
            upload(directory,new FileInputStream(uploadFile),fileName);
        } catch (FileNotFoundException e) {
            logger.error("上传文件到sftp服务器异常,{}", e);
        }
    }

    /**
     * 上传文件流
     *
     * @param directory   上传的目录
     * @param inputStream 要上传的文件
     */
    public void upload(String directory, InputStream inputStream, String fileName) {
        try {
            createDir(directory);
            sftp.put(inputStream, fileName);
        } catch (Exception e) {
            logger.error("上传文件到sftp服务器异常,{}", e);
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            logger.error("从sftp服务器下载文件异常,{}", e);
        }
    }

    /**
     * 读取指定文件,返回输入流
     *
     * @param directory
     * @param downloadFile
     * @return
     */
    public InputStream downloadForStream(String directory, String downloadFile) throws SftpException, IOException {
        return new ByteArrayInputStream(downloadForBytes(directory, downloadFile));
    }

    /**
     * 读取指定文件,返回字节
     *
     * @param directory
     * @param downloadFile
     * @return byte
     * @throws SftpException
     * @throws IOException
     * @author DINGYONG
     */
    public byte[] downloadForBytes(String directory, String downloadFile)
            throws SftpException, IOException {
        ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, outputStream);
            return outputStream.toByteArray();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("读取sftp文件内容异常,{}", e);
            }
        }
    }

    /**
     * 重命名文件
     *
     * @param oldPath
     * @param newPath
     * @throws SftpException
     * @throws IOException
     */
    public void renameFileName(String oldPath, String newPath)
            throws SftpException, IOException {
        sftp.rename(oldPath, newPath);
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            logger.error("删除sftp文件发生异常,{}", e);
        }
    }

    /**
     * 判断是否断开了sftp连接通道 false则为断开
     * */
    public boolean isClosed() {
        return channel.isConnected() || sshSession.isConnected();
    }


    /**
     * 关闭资源
     */
    public void close() {
        if (channel.isConnected()) {
            channel.disconnect();

        }
        if (sshSession.isConnected()) {
            sshSession.disconnect();
        }
    }
}
