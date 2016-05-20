package com.fivefire.app.gdutcontacts.widget.dialpad.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MicroStudent on 2016/5/19.
 */
public class NineKeyQuery implements IQuery<Object> {
    private final List<Object> filteredModelListByNumber = new ArrayList<>();
    private final List<Object> filteredModelListByName = new ArrayList<>();
    private final List<Object> result = new ArrayList<>();

    @Override
    public List<Object> filter(List<Object> data, String queryString) {
        result.clear();
        result.addAll(queryByNumber(data, queryString));
//        result.addAll(queryByName(data, queryString));
        return null;
    }

    private List<Object> queryByName(List<Object> data, String queryString) {
        filteredModelListByName.clear();
        return null;
    }


    private List<Object> queryByNumber(List<Object> data, String queryString) {
        filteredModelListByNumber.clear();
        for (Object object : data) {
            if (object.toString().contains(queryString)) {
                filteredModelListByNumber.add(object);
            }
        }
        return filteredModelListByNumber;
    }


}
