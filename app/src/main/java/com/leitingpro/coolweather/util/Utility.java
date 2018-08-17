package com.leitingpro.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.leitingpro.coolweather.db.City;
import com.leitingpro.coolweather.db.County;
import com.leitingpro.coolweather.db.Province;
import com.leitingpro.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据信息
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0 ; i<allProvinces.length();i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProviceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return  true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的市级数据信息
     * @param resoponse
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String resoponse,int provinceId){
        if(!TextUtils.isEmpty(resoponse)){
            try {
                JSONArray allCities = new JSONArray(resoponse);
                for (int i = 0 ; i<allCities.length();i++){
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的县级信息
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0 ; i <allCounties.length();i++){
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     * @param response
     * @return
     */
    public static Weather handWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return  new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
