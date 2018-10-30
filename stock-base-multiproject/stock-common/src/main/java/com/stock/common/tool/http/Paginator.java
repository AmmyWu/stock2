package com.stock.common.tool.http;

import java.io.Serializable;

/**
 * 分页器
 * 
 */
public class Paginator implements Serializable {
    /**  */
    private static final long serialVersionUID = 3790911351117457893L;
    /**
     * 总条数
     */
    private int               recordsTotal;

    public Paginator() {
    }

    public Paginator(int recordsTotal) {
        this.setRecordsTotal(recordsTotal);
    }

    /**
     * recordsTotal. 
     * @return  the recordsTotal 
     * @since   JDK 1.7 
     */
    public int getRecordsTotal() {
        return recordsTotal;
    }

    /**
     * recordsTotal. 
     * @param   recordsTotal    the recordsTotal to set 
     * @since   JDK 1.7 
     */
    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}
