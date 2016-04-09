package com.ezweather.app.util;

import android.text.TextUtils;

import com.ezweather.app.model.City;
import com.ezweather.app.model.County;
import com.ezweather.app.db.EzWeatherDB;
import com.ezweather.app.model.Province;

/**
 * Created by Oniros on 2016/4/7.
 */
public class Utility {

    /**
     * 解析处理服务器返回的省级数据
     */

    public synchronized static boolean handleProvinceResponse(EzWeatherDB ezWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String [] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String [] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储
                    ezWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(EzWeatherDB ezWeatherDB, String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            String [] allCities = response.split(",");
            if (allCities != null && allCities.length > 0 ){
                for (String c : allCities){
                    String [] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //存储数据到City表
                    ezWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountiesResponse(EzWeatherDB ezWeatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String [] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0 ){
                for (String c : allCounties){
                    String [] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    //存储数据到City表
                    ezWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }


}
