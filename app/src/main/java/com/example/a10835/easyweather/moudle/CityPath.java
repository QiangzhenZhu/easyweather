package com.example.a10835.easyweather.moudle;


import org.litepal.crud.DataSupport;

/**
 * Created by 10835 on 2017/10/12.
 */

public class CityPath extends DataSupport {
    private int id;
    private String path;
    private String name;
    private String codeId;

    public CityPath() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
