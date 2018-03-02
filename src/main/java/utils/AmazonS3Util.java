package utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lianjia.plats.store.link.utils.entity.AmazonS3Conf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author dangdandan on 17/7/5.
 */
public class AmazonS3Util {

    public AmazonS3Util() {
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名称
     * @param file     上传的文件实体
     */
    public static void upload(AmazonS3Client client, AmazonS3Conf s3Conf, String fileName, File file) {
        client.putObject(s3Conf.getBucketName(), fileName, file);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public static void delete(AmazonS3Client client, AmazonS3Conf s3Conf, String fileName) {
        client.deleteObject(s3Conf.getBucketName(), fileName);
    }

    /**
     * 把文件填充入输出流中
     *
     * @param fileName 文件名
     * @param os       输出流
     * @throws IOException
     */
    public static void writeToOutStream(AmazonS3Client client, AmazonS3Conf s3Conf, String fileName, OutputStream os) {
        S3Object s3Object = client.getObject(s3Conf.getBucketName(), fileName);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        byte[] b = new byte[4 * 1024];
        int hasRead;
        try {
            while ((hasRead = objectContent.read(b)) != -1) {
                os.write(b, 0, hasRead);
            }
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                s3Object.close();
            } catch (IOException e) {

                throw new AmazonS3Exception("关闭S3链接发生异常");
            }
        }
    }

    /**
     * 上传文件
     *
     * @param client      客户端
     * @param s3Conf      配置
     * @param fileName    存储文件名
     * @param is          输出流
     * @param contentType 类型和大小
     */
    public static void upload(AmazonS3Client client, AmazonS3Conf s3Conf, String fileName, InputStream is, String contentType) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        client.putObject(s3Conf.getBucketName(), fileName, is, objectMetadata);
    }
}
