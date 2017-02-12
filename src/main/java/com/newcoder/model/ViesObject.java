package com.newcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Super腾 on 2017/2/9.
 */
public class ViesObject {
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
