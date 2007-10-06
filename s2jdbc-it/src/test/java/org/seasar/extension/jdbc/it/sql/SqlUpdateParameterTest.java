/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.it.sql;

import java.math.BigDecimal;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.unit.S2TestCase;

/**
 * @author taedium
 * 
 */
public class SqlUpdateParameterTest extends S2TestCase {

    private JdbcManager jdbcManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jdbc.dicon");
    }

    /**
     * 
     * @throws Exception
     */
    public void test_no_parameterTx() throws Exception {
        String sql = "delete from Employee";
        int actual = jdbcManager.updateBySql(sql).execute();
        assertEquals(14, actual);
    }

    /**
     * 
     * @throws Exception
     */
    public void test_parameterTx() throws Exception {
        String sql = "delete from Employee where department_Id = ? and salary > ?";
        int actual = jdbcManager.updateBySql(sql, String.class,
                BigDecimal.class).params(3, new BigDecimal(1000)).execute();
        assertEquals(5, actual);
    }
}
