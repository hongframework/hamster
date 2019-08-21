package com.hframework.datacenter.hamster.workes.bean;

import com.hframe.hamster.node.cannal.bean.EventType;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;

import java.util.*;

public class DataSet {
    private long tableId = -1;//hamster内部维护的一套tableId，与manager中得到的tableId对应
    private String tableName;
    private String schemaName;
    private List<DataField> fields = new ArrayList<>();
    private List<DataRow> rows = new ArrayList<>();

    public DataSet() {
    }

    public DataSet(long tableId, String tableName, String schemaName) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.schemaName = schemaName;
    }

    public DataSet clone(){
        DataSet dataSet = new DataSet(tableId, tableName, schemaName);
        dataSet.setFields(fields);
        dataSet.setRows(rows);
        return dataSet;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public List<DataField> getFields() {
        return fields;
    }

    public void setFields(List<DataField> fields) {
        this.fields = new ArrayList(fields);
    }

    public void cleanRows(){
        this.rows = new ArrayList<>();
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public void setRows(List<DataRow> rows) {
        this.rows = rows;
    }

    public void addRow(DataRow row) {
        this.rows.add(row);
    }


    public synchronized DataRow getOrAddRow(long executeTime, String binLogPosition, List<String> rowValue) {
        for (DataRow row: rows) {
            if(row.getExecuteTime() == executeTime && row.getBinLogPosition() == binLogPosition) {
                boolean valueEq = true;
                for (int i = 0; i < rowValue.size(); i++) {
                    String origVal = rowValue.get(i) == null? "": rowValue.get(i);
                    String targetVal = row.getValues().get(i) == null? "": row.getValues().get(i);
                    if(!origVal.equals(targetVal)) {
                        valueEq = false;
                        break;
                    }
                }
                if(valueEq){
                    return row;
                }
            }
        }

        return addRowAndWithNull(executeTime, binLogPosition, rowValue);
    }

    public synchronized DataRow addRowAndWithNull(long executeTime, String binLogPosition, List<String> rowValue) {
        List<String> newRowValue = new ArrayList<>(rowValue);
        for (int i = 0; i < fields.size() - rowValue.size(); i++) {
            newRowValue.add(null);
        }
        return addRow(executeTime, binLogPosition, newRowValue);
    }

    public DataRow addRow(long executeTime, String binLogPosition, List<String> rowValue) {
        DataRow row = new DataRow();
        row.setValues(rowValue);
        row.setOldValues(new ArrayList<>());
        row.setUpdates(new ArrayList<>());
        row.setBinLogPosition(binLogPosition);
        row.setExecuteTime(executeTime);
        row.setEventType(EventType.UPDATE);
        addRow(row);
        return row;
    }

    // 为所有row的values新增值为null的一列
    public void addNullRowsField() {
        for (DataRow row : rows) {
            row.getValues().add(null);
        }
    }

    public void removeField(List<Integer> fieldIndexs) {
        Collections.sort(fieldIndexs, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for (Integer fieldIndex : fieldIndexs) {
            //这里必须强转为int类型，表明调用的是remove(int index)，而不是remove(Object o)
            fields.remove((int)fieldIndex);
            for (DataRow row : rows) {
                if (row.getValues().size() > fieldIndex) {
                    row.getValues().remove((int) fieldIndex);
                }
                if(row.getOldValues() != null && !row.getOldValues().isEmpty() && row.getOldValues().size() > fieldIndex) {
                    row.getOldValues().remove((int)fieldIndex);
                }
                if(row.getUpdates() != null && !row.getUpdates().isEmpty() && row.getUpdates().size() > fieldIndex) {
                    row.getUpdates().remove((int)fieldIndex);
                }
            }
        }
    }

    public DataField getField(String fieldName) {
        for (int i = 0; i < fields.size(); i++) {
            if(fieldName.equals(fields.get(i).getColumnName())) {
                return fields.get(i);
            }
        }
        return null;
    }

    public DataField getField(int index) {
        if(index < fields.size()) {
            return fields.get(index);
        }
        return null;
    }
    public int getFieldIndex(String fieldName){
        int fieldIndex = -1;
        for (int i = 0; i < fields.size(); i++) {
            if(fieldName.equals(fields.get(i).getColumnName())) {
                fieldIndex = i;
            }
        }
        return fieldIndex;
    }

    public List<String> getFieldValues(String fieldName) {
        final int fieldIndex  = getFieldIndex(fieldName);
        if(fieldIndex < 0) throw new RuntimeException("data set " + fieldName + " not exists !");

        List<String> fieldValues = CollectionUtils.fetch(rows, new Fetcher<DataRow, String>() {
            @Override
            public String fetch(DataRow dataRow) {
                return dataRow.getValues().get(fieldIndex);
            }
        });
        return fieldValues;
    }



    public void setFieldValues(String fieldName, List<String> values){
        int fieldIndex = getFieldIndex(fieldName);
        if(fieldIndex < 0) throw new RuntimeException("data set " + fieldName + " not exists !");
        if(values.size() != rows.size()) throw new RuntimeException("data set values size = " + values.size() + ", row size =" + rows.size() + "!");
        for (int i = 0; i < rows.size(); i++) {
            DataRow dataRow = rows.get(i);
            if(dataRow.getValues().size() > fieldIndex) {
                dataRow.getValues().set(fieldIndex, values.get(i));
            }else {
                dataRow.getValues().add(values.get(i));
            }
        }
    }

    public List<Map<String, String>> getFieldValues(List<String> fieldNames) {
        final Map<String, Integer> fieldNameIndex = new HashMap<>();
        for (String fieldName : fieldNames) {
            for (int i = 0; i < fields.size(); i++) {
                if(fieldName.equals(fields.get(i).getColumnName())) {
                    fieldNameIndex.put(fieldName, i);
                }
            }
        }


        List<Map<String, String>> fieldValues = CollectionUtils.fetch(rows, new Fetcher<DataRow, Map<String, String>>() {
            @Override
            public Map<String, String> fetch(DataRow dataRow) {
                List<String> values = dataRow.getValues();
                Map<String, String> row = new HashMap<>();
                for (Map.Entry<String, Integer> entry : fieldNameIndex.entrySet()) {
                    row.put(entry.getKey(), values.get(entry.getValue()));
                }
                return row;
            }
        });
        return fieldValues;
    }



}
