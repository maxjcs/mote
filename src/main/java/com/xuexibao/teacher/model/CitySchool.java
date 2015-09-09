package com.xuexibao.teacher.model;

import java.util.ArrayList;
import java.util.List;

public class CitySchool {
    
    private String city;
    private List<School> schools = new ArrayList<School>();
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public List<School> getSchools() {
        return schools;
    }
    public void setSchools(List<School> schools) {
        this.schools = schools;
    }
}
