package com.ezweather.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ezweather.app.db.EzWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oniros on 2016/4/6.
 */
public class EzWeatherDB {
    /**
    *数据库名
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static EzWeatherDB ezWeatherDB;

    private SQLiteDatabase db;

    /**
     * 私有化构造方法
     */
    private EzWeatherDB(Context context){
        EzWeatherOpenHelper dbHelper = new EzWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取实例
     */
    public synchronized static EzWeatherDB getInstance(Context context){
        if (ezWeatherDB == null){
            ezWeatherDB = new EzWeatherDB(context);
        }
        return ezWeatherDB;
    }
    /**
     * 存储Province到数据库
     */
    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insertOrThrow("Province", null, values);
        }
    }

    /**
     *读取全国省份信息
     */
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db
                .query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                City city  = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while(cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[] {String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

}

