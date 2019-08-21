package com.hframe.ext.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangquanhong on 2017/3/22.
 */
public class HBaseData {

    private String rowKey;

    private List<HBaseColumn> hBaseColumns = new ArrayList<>();

    private String remark;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public List<HBaseColumn> gethBaseColumns() {
        return hBaseColumns;
    }

    public void sethBaseColumns(List<HBaseColumn> hBaseColumns) {
        this.hBaseColumns = hBaseColumns;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void addColumn(HBaseColumn hBaseColumn) {
        hBaseColumns.add(hBaseColumn);
    }

    public  HBaseColumn getColumn(String family, String qualifier) {
        for (HBaseColumn hBaseColumn : hBaseColumns) {
            if(family.equals(hBaseColumn.getFamily()) && qualifier.equals(hBaseColumn.getQualifier())) {
                return hBaseColumn;
            }
        }
        return null;
    }

    public static class HBaseColumn{
        private String  family;
        private String qualifier;
        private String value;

        public HBaseColumn() {
        }

        public HBaseColumn(String family, String qualifier, String value) {
            this.family = family;
            this.qualifier = qualifier;
            this.value = value;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getQualifier() {
            return qualifier;
        }

        public void setQualifier(String qualifier) {
            this.qualifier = qualifier;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
