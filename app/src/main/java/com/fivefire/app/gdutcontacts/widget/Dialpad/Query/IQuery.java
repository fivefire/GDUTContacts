package com.fivefire.app.gdutcontacts.widget.Dialpad.Query;

import java.util.List;

/**
 * 策略模式。
 * 查询器的接口
 * Created by MicroStudent on 2016/5/19.
 */
public interface IQuery<T> {
    /**
     * 针对相同的查询条件，我们可能会有不同的查询策略，对此我们进行不同的实现
     * @param data 需要进行筛选的List
     * @param queryString 查询的条件语句
     * @return 筛选后的List
     */
    List<T> filter(List<T> data, String queryString);
}
