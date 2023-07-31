package com.sword.framework.plugins.oss;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sword.framework.plugins.oss.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shujian.ou
 * @since 2022/12/28 10:48
 */
@RequiredArgsConstructor
@Slf4j
public class OssTemplate implements InitializingBean {

    private final OssProperties properties;

    private OSS ossClient;

    /**
     * 上传对象
     */
    public PutObjectResult putObject(String objectName, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), objectName, inputStream);
        return this.ossClient.putObject(putObjectRequest);
    }

    /**
     * 上传文件
     */
    public PutObjectResult putFile(String objectName, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucketName(), objectName, file);
        return this.ossClient.putObject(putObjectRequest);
    }

    /**
     * 删除对象
     */
    public void deleteObject(String bucketName, String objectName) {
        this.ossClient.deleteObject(bucketName, objectName);
    }

    /**
     * 获取对象访问地址
     *
     * @param objectName 对象名称
     * @param expiration 过期时间
     * @return 对象访问地址
     */
    public String generatePresignedUrl(String objectName, Date expiration) {
        return this.ossClient.generatePresignedUrl(properties.getBucketName(), objectName, expiration).toString();
    }

    /**
     * 获取对象访问地址(cdn)
     *
     * @param cdn        cdn域名
     * @param objectName 对象名称
     * @param expiration 过期时间
     * @return 对象访问地址
     */
    public String generateCdnUrl(String cdn, String objectName, Date expiration) {
        String url = this.ossClient.generatePresignedUrl(properties.getBucketName(), objectName, expiration).toString();
        return cdn + StrUtil.sub(url, StrUtil.ordinalIndexOf(url, "/", 3), url.length());
    }


    public AssumeRoleResponse generateSTS() throws ClientException {
        DefaultProfile.addEndpoint(properties.getStsRegionId(), "Sts", properties.getStsEndpoint());
        IClientProfile profile = DefaultProfile.getProfile(properties.getStsRegionId(), properties.getStsAccessKeyId(),
                properties.getStsAccessKeySecret());
        // 构造client。
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(properties.getStsRoleArn());
        request.setRoleSessionName(properties.getStsRoleSessionName());
        // 如果policy为空，则用户将获得该角色下所有权限

        //request.setPolicy("{ \"Version\": \"1\", \"Statement\": [ { \"Effect\": \"Allow\", \"Action\": [ \"oss:GetObject\", \"oss:PutObject\", \"oss:GetObjectAcl\", \"oss:PutObjectAcl\" ], \"Resource\": [ \"acs:oss:*:*:app/*\" ], \"Condition\": {} } ] }");
        // 设置临时访问凭证的有效时间为3600秒
        request.setDurationSeconds(3600L);

        return client.getAcsResponse(request);
    }

    public Map<String, Object> generatePolicy() {
        //https://md-ossbucket.oss-cn-beijing.aliyuncs.com/QQ%E6%88%AA%E5%9B%BE20210609114525.png  host的格式为 bucketname.endpoint
        String host = "https://" + properties.getBucketName() + "." + properties.getEndpoint();

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); //格式化一个当前的服务器时间

        String dir = format + "/"; // 用户上传文件时指定的前缀,我们希望以日期作为一个目录

        Map<String, Object> respMap = null; //返回结果

        try {
            long expireTime = 180;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = this.ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<>();
            respMap.put("accessid", properties.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", expireEndTime);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return respMap;
    }


    @Override
    public void afterPropertiesSet() {
        ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }
}
