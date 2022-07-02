package com.mbt.yapikredi.ik.dto.base;

import java.util.List;

public class PageModel<T> {

    private List<T> data;

    private int pageIndex;

    private int pageSize;

    private long totalElementCount;

    public PageModel() {
    }

    public PageModel(List<T> data, int pageIndex, int pageSize, long totalElementCount) {
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalElementCount = totalElementCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElementCount() {
        return totalElementCount;
    }

    public void setTotalElementCount(long totalElementCount) {
        this.totalElementCount = totalElementCount;
    }

}
