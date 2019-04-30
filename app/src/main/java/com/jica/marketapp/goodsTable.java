package com.jica.marketapp;

public class goodsTable {
    String id, name, entCode, UnitDivcode, BaseCnt, SmlclsCod, dMean, tCnt, tDivcode;

    public goodsTable() {
    }

    public goodsTable(String goodId, String goodName, String productEntpCode, String goodUnitDivCode, String goodBaseCnt, String goodSmlclsCode, String detailMean, String goodTotalCnt, String goodTotalDivCode){
        this.id = goodId;
        this.name = goodName;
        this.entCode = productEntpCode;
        this.UnitDivcode = goodUnitDivCode;
        this.BaseCnt = goodBaseCnt;
        this.SmlclsCod = goodSmlclsCode;
        this.dMean = detailMean;
        this.tCnt = goodTotalCnt;
        this.tDivcode = goodTotalDivCode;
    }

    @Override
    public String toString() {
        return "goodsTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", entCode=" + entCode +
                ", UnitDivcode=" + UnitDivcode +
                ", BaseCnt=" + BaseCnt +
                ", SmlclsCod=" + SmlclsCod +
                ", dMean=" + dMean +
                ", tCnt=" + tCnt +
                ", tDivcode=" + tDivcode +
                '}';
    }
}
