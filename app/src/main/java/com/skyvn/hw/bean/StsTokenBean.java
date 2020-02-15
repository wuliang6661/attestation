package com.skyvn.hw.bean;

public class StsTokenBean {


    /**
     * endpoint : A7mJWBgGX8a3xDwL2vjq/826RcSAeZjtHvffFZShzU8=
     * accessKeyId : vxAXYABhGiTZt5K/6rMaupMhy/N9JWmZR6OZVo8MSFk=
     * accessKeySecret : Pj4XLIBq7jeXeIDcnsPLGSnnxDEmf+BQnd71a/gqMws=
     * bucket : K5RWc2UTTR42Kwq2LigTNMipHWdzryu9pxbMjQL0kOA=
     * key : So0WHL3HcrxInBWuRZPdKQ==
     */

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String key;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
