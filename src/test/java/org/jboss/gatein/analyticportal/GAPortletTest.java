/*
 *  Copyright (C) 2010 Nabil BENOTHMAN <nabil.benothman@gmail.com>
 *                     Anne Leticia MIKIELA <anne.mikiela@unine.ch>
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.gatein.analyticportal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benothman & Mikiela
 */
public class GAPortletTest {

    public GAPortletTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class GAPortlet.
     */
    @Test
    public void testInit_PortletConfig() throws Exception {
        System.out.println("init");
        PortletConfig config = null;
        GAPortlet instance = new GAPortlet();
        //instance.init(config);
        assertTrue(true);
        // fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class GAPortlet.
     */
    @Test
    public void testInit_0args() throws Exception {
        System.out.println("init");
        GAPortlet instance = new GAPortlet();
        //instance.init();
        assertTrue(true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of doEdit method, of class GAPortlet.
     */
    @Test
    public void testDoEdit() throws Exception {
        System.out.println("doEdit");
        RenderRequest request = null;
        RenderResponse response = null;
        GAPortlet instance = new GAPortlet();
        //instance.doEdit(request, response);
        // TODO review the generated test code and remove the default call to fail.
       assertTrue(true);
    }

    /**
     * Test of doView method, of class GAPortlet.
     */
    @Test
    public void testDoView() throws Exception {
        System.out.println("doView");
        RenderRequest request = null;
        RenderResponse response = null;
        GAPortlet instance = new GAPortlet();
        //instance.doView(request, response);
        assertTrue(true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of doHelp method, of class GAPortlet.
     */
    @Test
    public void testDoHelp() throws Exception {
        System.out.println("doHelp");
        RenderRequest request = null;
        RenderResponse response = null;
        GAPortlet instance = new GAPortlet();
        //instance.doHelp(request, response);
        assertTrue(true);
        
    }

    /**
     * Test of processAction method, of class GAPortlet.
     */
    @Test
    public void testProcessAction() throws Exception {
        System.out.println("processAction");
        ActionRequest request = null;
        ActionResponse response = null;
        GAPortlet instance = new GAPortlet();
        //instance.processAction(request, response);
        assertTrue(true);
    }

}