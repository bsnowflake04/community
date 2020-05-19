package com.mylawyer.community.provider;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @Author bsnowflake04
 * Date on 2020/5/18  17:15
 */
@Component
public class OSSProvider {
    @Value("${OSS.accesskey.id}")
    private String OSSAccessKeyId;
    @Value("${OSS.accesskey.secret}")
    private String OSSAccessKeySecret;
    @Value("${OSS.endpoint}")
    private String OSSEndPoint;
    @Value("${OSS.bucket.name}")
    private String OSSBucketName;

    public String upload(InputStream fileStream, String objName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OSSEndPoint;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = OSSAccessKeyId;
        String accessKeySecret = OSSAccessKeySecret;
        String bucketName = OSSBucketName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(bucketName, objName, fileStream);

            Date expiration = new Date(new Date().getTime() + 1000 * 60 * 60 );
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objName, HttpMethod.GET);
            req.setExpiration(expiration);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            return signedUrl.toString();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
