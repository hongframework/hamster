/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hframe.hamster.common.db.dialect.oracle;

import com.hframe.hamster.common.db.dialect.AbstractDbDialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * 基于oracle的一些特殊处理定义
 * 
 * @author ** 2011-10-27 下午01:44:46
 * @version 4.0.0
 */
public class OracleDialect extends AbstractDbDialect {

    public OracleDialect(JdbcTemplate jdbcTemplate, LobHandler lobHandler){
        super(jdbcTemplate, lobHandler);
        sqlTemplate = new OracleSqlTemplate();
    }

    public OracleDialect(JdbcTemplate jdbcTemplate, LobHandler lobHandler, String name, int majorVersion,
                         int minorVersion){
        super(jdbcTemplate, lobHandler, name, majorVersion, minorVersion);
        sqlTemplate = new OracleSqlTemplate();
    }

    public boolean isCharSpacePadded() {
        return true;
    }

    public boolean isCharSpaceTrimmed() {
        return false;
    }

    public boolean isEmptyStringNulled() {
        return true;
    }

    public boolean storesUpperCaseNamesInCatalog() {
        return true;
    }

    public boolean isSupportMergeSql() {
        return true;
    }

    public String getDefaultCatalog() {
        return null;
    }

    public String getDefaultSchema() {
        return (String) jdbcTemplate.queryForObject("SELECT sys_context('USERENV', 'CURRENT_SCHEMA') FROM dual",
                                                    String.class);
    }

}
