package com.xuexibao.teacher.util;

import org.apache.commons.httpclient.methods.PostMethod;

class UTF8PostMethod extends PostMethod{
    public UTF8PostMethod(String url){
        super(url);
    }
    @Override
    public String getRequestCharSet() {
        return "utf-8";
    }
}