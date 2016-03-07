package com.test.bean;

/**
 * Created by snail on 2016/1/18.
 * Description: 配送费配置规则
 */
public class DeliveryFeeConfig {
    //初始运费(元)
    private double baseFee;
    //首重(kg)
    private double baseWeight;
    //基础续重(元/kg)
    private double baseAdditionalWeight;
    //每满(元)
    private double discountPerFee;
    //减免(kg)
    private double discountWeight;
    //优惠续重(元/kg)
    private double discountAdditionalWeight;

    public double getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(double baseFee) {
        this.baseFee = baseFee;
    }

    public double getBaseWeight() {
        return baseWeight;
    }

    public void setBaseWeight(double baseWeight) {
        this.baseWeight = baseWeight;
    }

    public double getBaseAdditionalWeight() {
        return baseAdditionalWeight;
    }

    public void setBaseAdditionalWeight(double baseAdditionalWeight) {
        this.baseAdditionalWeight = baseAdditionalWeight;
    }

    public double getDiscountPerFee() {
        return discountPerFee;
    }

    public void setDiscountPerFee(double discountPerFee) {
        this.discountPerFee = discountPerFee;
    }

    public double getDiscountWeight() {
        return discountWeight;
    }

    public void setDiscountWeight(double discountWeight) {
        this.discountWeight = discountWeight;
    }

    public double getDiscountAdditionalWeight() {
        return discountAdditionalWeight;
    }

    public void setDiscountAdditionalWeight(double discountAdditionalWeight) {
        this.discountAdditionalWeight = discountAdditionalWeight;
    }

    @Override
    public String toString() {
        return "DeliveryFeeConfig{" +
                "baseFee=" + baseFee +
                ", baseWeight=" + baseWeight +
                ", baseAdditionalWeight=" + baseAdditionalWeight +
                ", discountPerFee=" + discountPerFee +
                ", discountWeight=" + discountWeight +
                ", discountAdditionalWeight=" + discountAdditionalWeight +
                '}';
    }
}
