package com.longcity.manage.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：分页请求 baseVO
 */

public class QueryBaseVO<T> {

    // 默认第一页
    private final Integer DEFAULT_INDEX = 1;
    // 默认每页大小是10条
    private final Integer DEFAULT_PAGESIZE = 10;
    // 当前页
    private int index = DEFAULT_INDEX;
    // 每页大小
    private int pageSize;
    // 总数
    private int total = 0;
    //默认查询id
    private int lastId = 1;

    private List<T> rows = new ArrayList<T>();

    public int getIndex() {
        if (index <= 0) {
            index = DEFAULT_INDEX;
        }
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGESIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getStart() {
        return (getIndex() - 1) * (getPageSize());
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }
}
