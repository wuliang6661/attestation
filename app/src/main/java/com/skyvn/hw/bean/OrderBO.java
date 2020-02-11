package com.skyvn.hw.bean;

import java.util.List;

public class OrderBO {


    /**
     * num : 1
     * size : 500
     * total : 1
     * pageCount : 1
     * data : [{"id":"4","clientId":"1217703242848575489","tenantId":"0","productId":"1","loanAmount":"10.00","days":10,"createTime":"2020-02-10 17:00:54","status":60,"smsName":null}]
     */

    private int num;
    private int size;
    private String total;
    private int pageCount;
    private List<DataBean> data;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * clientId : 1217703242848575489
         * tenantId : 0
         * productId : 1
         * loanAmount : 10.00
         * days : 10
         * createTime : 2020-02-10 17:00:54
         * status : 60
         * smsName : null
         */

        private String id;
        private String clientId;
        private String tenantId;
        private String productId;
        private String loanAmount;
        private int days;
        private String createTime;
        private int status;
        private String smsName;
        private String logoOssUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(String loanAmount) {
            this.loanAmount = loanAmount;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSmsName() {
            return smsName;
        }

        public void setSmsName(String smsName) {
            this.smsName = smsName;
        }

        public String getLogoOssUrl() {
            return logoOssUrl;
        }

        public void setLogoOssUrl(String logoOssUrl) {
            this.logoOssUrl = logoOssUrl;
        }
    }
}
