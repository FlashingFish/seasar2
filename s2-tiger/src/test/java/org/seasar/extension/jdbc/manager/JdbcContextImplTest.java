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
package org.seasar.extension.jdbc.manager;

import java.sql.Statement;

import junit.framework.TestCase;

import org.seasar.framework.mock.sql.MockCallableStatement;
import org.seasar.framework.mock.sql.MockConnection;
import org.seasar.framework.mock.sql.MockPreparedStatement;

/**
 * @author higa
 * 
 */
public class JdbcContextImplTest extends TestCase {

    private MockConnection con = new MockConnection();

    private JdbcContextImpl ctx = new JdbcContextImpl(con, false);

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
        con = null;
        ctx = null;
    }

    /**
     * @throws Exception
     * 
     */
    public void testGetPreparedStatement() throws Exception {
        ctx.setPreparedStatementCacheSize(1);
        MockPreparedStatement ps = (MockPreparedStatement) ctx
                .getPreparedStatement("aaa");
        assertNotNull(ps);
        assertFalse(ctx.isPreparedStatementCacheEmpty());
        assertSame(ps, ctx.getPreparedStatement("aaa"));
        ctx.getPreparedStatement("bbb");
        assertTrue(ps.isClosed());
    }

    /**
     * @throws Exception
     * 
     */
    public void testGetCursorPreparedStatement() throws Exception {
        ctx.setCursorPreparedStatementCacheSize(1);
        MockPreparedStatement ps = (MockPreparedStatement) ctx
                .getCursorPreparedStatement("aaa");
        assertNotNull(ps);
        assertFalse(ctx.isCursorPreparedStatementCacheEmpty());
        assertSame(ps, ctx.getCursorPreparedStatement("aaa"));
        ctx.getCursorPreparedStatement("bbb");
        assertTrue(ps.isClosed());
    }

    /**
     * @throws Exception
     * 
     */
    public void testGetCallableStatement() throws Exception {
        ctx.setCallableStatementCacheSize(1);
        MockCallableStatement cs = (MockCallableStatement) ctx
                .getCallableStatement("aaa");
        assertNotNull(cs);
        assertFalse(ctx.isCallableStatementCacheEmpty());
        assertSame(cs, ctx.getCallableStatement("aaa"));
        ctx.getCallableStatement("bbb");
        assertTrue(cs.isClosed());
    }

    /**
     * @throws Exception
     * 
     */
    public void testGetStatement() throws Exception {
        Statement stmt = ctx.getStatement();
        assertNotNull(stmt);
        assertSame(stmt, ctx.getStatement());
    }

    /**
     * @throws Exception
     * 
     */
    public void testDestroy() throws Exception {
        ctx.getStatement();
        ctx.getPreparedStatement("aaa");
        ctx.getCursorPreparedStatement("bbb");
        ctx.getCallableStatement("ccc");
        ctx.destroy();
        assertTrue(ctx.isStatementNull());
        assertTrue(ctx.isPreparedStatementCacheEmpty());
        assertTrue(ctx.isCursorPreparedStatementCacheEmpty());
        assertTrue(ctx.isCallableStatementCacheEmpty());
        assertTrue(ctx.isConnectionNull());
        assertTrue(con.isClosed());
    }

}
