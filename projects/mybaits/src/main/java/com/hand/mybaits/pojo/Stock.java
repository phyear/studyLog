package com.hand.mybaits.pojo;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "stock")
public class Stock {

    private String stockId;
    @Column(name = "share_name")
    private String  stockName;

    private String  companyName;
    @Column(name = "status")
    private int status;

    private  String  shareCoding;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareCoding() {
        return shareCoding;
    }

    public void setShareCoding(String shareCoding) {
        this.shareCoding = shareCoding;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", stockName='" + stockName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", status=" + status +
                ", shareCoding='" + shareCoding + '\'' +
                '}';
    }
}
