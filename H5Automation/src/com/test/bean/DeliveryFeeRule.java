package com.test.bean;



import com.test.util.Log;
import com.test.util.PropertiesUtils;

/**
 * Created by snail on 2016/1/18.
 * Description: 运费计算规则
 */
public class DeliveryFeeRule {
    private final String config = "deliveryfee";
    public static void main(String[] args){
        DeliveryFeeRule rule = new DeliveryFeeRule();
        rule.getDeliveryFee(7,22);
    }

    //获取运费
    public double getDeliveryFee(double weight,double orderMoney){
        DeliveryFeeConfig deliveryFeeConfig = getDeliveryFeeConfig();
        double baseDeliveryFee = getBaseDeliveryFee(weight,deliveryFeeConfig),
                discountDeliveryFee=getDiscountDeliveryFee(orderMoney,deliveryFeeConfig);
       // logger.info("[运费]  deliveryFeeConfig={},baseDeliveryFee={},discountDeliveryFee={}",
               // deliveryFeeConfig,baseDeliveryFee,discountDeliveryFee);
        double deliveryFee = baseDeliveryFee - discountDeliveryFee;
        deliveryFee = deliveryFee<0?0:deliveryFee;
        Log.logInfo("[运费]  deliveryFee="+deliveryFee);
        return deliveryFee;
    }

    //获取减免的运费
    private double getDiscountDeliveryFee(double fee,DeliveryFeeConfig deliveryFeeConfig){
        return Math.floor(fee/deliveryFeeConfig.getDiscountPerFee())*
                deliveryFeeConfig.getDiscountWeight()*deliveryFeeConfig.getDiscountAdditionalWeight();
    }

    //获取基础的运费
    private double getBaseDeliveryFee(double weight,DeliveryFeeConfig deliveryFeeConfig){
        double baseFee = deliveryFeeConfig.getBaseFee();

        if(deliveryFeeConfig.getBaseWeight()<weight){
            baseFee += (weight-deliveryFeeConfig.getBaseWeight())*deliveryFeeConfig.getBaseAdditionalWeight();
        }

        return baseFee;
    }

    //获取运费规则配置
    private DeliveryFeeConfig getDeliveryFeeConfig(){
        DeliveryFeeConfig deliveryFeeConfig = new DeliveryFeeConfig();
        deliveryFeeConfig.setBaseFee(Double.parseDouble(PropertiesUtils.getProperty(config,"base.fee")));
        deliveryFeeConfig.setBaseWeight(Double.parseDouble(PropertiesUtils.getProperty(config,"base.weight")));
        deliveryFeeConfig.setBaseAdditionalWeight(Double.parseDouble(PropertiesUtils.getProperty(config,"base.additional.weight")));

        deliveryFeeConfig.setDiscountPerFee(Double.parseDouble(PropertiesUtils.getProperty(config,"discount.per.fee")));
        deliveryFeeConfig.setDiscountWeight(Double.parseDouble(PropertiesUtils.getProperty(config,"discount.weight")));
        deliveryFeeConfig.setDiscountAdditionalWeight(Double.parseDouble(PropertiesUtils.getProperty(config,"discount.additional.weight")));
        return deliveryFeeConfig;
    }

}
