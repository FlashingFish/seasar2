/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.extension.jdbc.gen.dialect;

import java.sql.Types;

import org.junit.Test;
import org.seasar.extension.jdbc.gen.GenDialect.Type;

import static org.junit.Assert.*;

/**
 * @author taedium
 * 
 */
public class MysqlGenDialectTest {

    private MysqlGenDialect dialect = new MysqlGenDialect();

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testGetType_binary() throws Exception {
        Type type = dialect.getType(Types.BINARY);
        assertEquals("tinyblob", type.getColumnDefinition(255, 0, 0, null));
        assertEquals("blob", type.getColumnDefinition(256, 0, 0, null));
        assertEquals("blob", type.getColumnDefinition(65535, 0, 0, null));
        assertEquals("mediumblob", type.getColumnDefinition(65536, 0, 0, null));
        assertEquals("mediumblob", type.getColumnDefinition(16777215, 0, 0,
                null));
        assertEquals("longblob", type.getColumnDefinition(16777216, 0, 0, null));
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testGetType_blob() throws Exception {
        Type type = dialect.getType(Types.BLOB);
        assertEquals("tinyblob", type.getColumnDefinition(255, 0, 0, null));
        assertEquals("blob", type.getColumnDefinition(256, 0, 0, null));
        assertEquals("blob", type.getColumnDefinition(65535, 0, 0, null));
        assertEquals("mediumblob", type.getColumnDefinition(65536, 0, 0, null));
        assertEquals("mediumblob", type.getColumnDefinition(16777215, 0, 0,
                null));
        assertEquals("longblob", type.getColumnDefinition(16777216, 0, 0, null));
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testGetType_clob() throws Exception {
        Type type = dialect.getType(Types.CLOB);
        assertEquals("tinytext", type.getColumnDefinition(255, 0, 0, null));
        assertEquals("text", type.getColumnDefinition(256, 0, 0, null));
        assertEquals("text", type.getColumnDefinition(65535, 0, 0, null));
        assertEquals("mediumtext", type.getColumnDefinition(65536, 0, 0, null));
        assertEquals("mediumtext", type.getColumnDefinition(16777215, 0, 0,
                null));
        assertEquals("longtext", type.getColumnDefinition(16777216, 0, 0, null));
    }
}
