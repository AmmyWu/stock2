package com.stock.common.tool.http;

import java.util.List;

/**
 * 分页信息
 */
public class PageInfo<T> extends Paginator {
    /**  */
    private static final long serialVersionUID = -1956843019506342980L;
    /**
     * 分页的实体存储
     */
    private List<T>           list;

    public PageInfo() {
    };

    private PageInfo(List<T> list, int recordsTotal) {
        super(recordsTotal);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 工厂方法，取得PageInfo的唯一途径
     * 
     * @param list
     * @param recordsTotal
     * @return
     */
    public static <I> PageInfo<I> build(List<I> list, int recordsTotal) {
        PageInfo<I> result = new PageInfo<I>(list, recordsTotal);
        return result;
    }
}
