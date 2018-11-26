package com.group.zhtx.bean;

import java.util.ArrayList;
import java.util.List;

public class UserPage {
    private int currentPage;//当前页
    private int totalPage;//总页数
    private int count;//一页多少条数据
    private long totalCount;//数据总条数
    private List<Object> objectList=new ArrayList<>();//当前页的数据
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void add(Object object){
        objectList.add(object);
    }
}
