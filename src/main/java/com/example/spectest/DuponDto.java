package com.example.spectest;

import com.example.spectest.entity.Dupon;

import java.util.ArrayList;
import java.util.List;

public class DuponDto {
    private double profitMargin;
    private double assetTurnover;
    private double leverage;
    private double roe;

    public DuponDto(double profitMargin, double assetTurnover, double leverage) {
        this.profitMargin = profitMargin;
        this.assetTurnover = assetTurnover;
        this.leverage = leverage;
    }

    public DuponDto(Dupon dupon) {
        this.profitMargin = dupon.getIncome() / dupon.getRevenue();
        this.assetTurnover = dupon.getRevenue() / dupon.getActives();
        this.leverage = dupon.getActives() / dupon.getCapital().getTotal();
        this.roe = dupon.getIncome() / dupon.getCapital().getTotal();
    }

    private String toPercentage(double n, int digits){
        return String.format("%."+digits+"f",n*100)+"%";
    }

    public static List<DuponDto> fromDuponList(List<Dupon> dupons) {
        List<DuponDto> duponDtos = new ArrayList<>();
        for (Dupon dupon : dupons) {
            duponDtos.add(new DuponDto(dupon));
        }
        return duponDtos;
    }

    public String getRoe() {
        return toPercentage(roe, 1);
    }

    public String getProfitMargin() {
        return toPercentage(profitMargin, 1);
    }

    public DuponDto setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
        return this;
    }

    public String getAssetTurnover() {
        return toPercentage(assetTurnover, 1);
    }

    public DuponDto setAssetTurnover(double assetTurnover) {
        this.assetTurnover = assetTurnover;
        return this;
    }

    public String getLeverage() {
        return toPercentage(leverage, 1);
    }

    public DuponDto setLeverage(double leverage) {
        this.leverage = leverage;
        return this;
    }
}
