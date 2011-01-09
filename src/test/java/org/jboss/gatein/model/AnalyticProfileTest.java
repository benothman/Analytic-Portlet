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
package org.jboss.gatein.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
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
public class AnalyticProfileTest {

    private static Validator validator;

    public AnalyticProfileTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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

    @Test
    public void testValidateTrackId() {
        System.out.println("getTrackId");
        AnalyticProfile instance = new AnalyticProfile();
        instance.setTrackId("XS-ACDEF-01");
        instance.addGroup("unine");
        Set<ConstraintViolation<AnalyticProfile>> constraintViolations = validator.validate(instance);
        assertEquals(0, constraintViolations.size());
        //assertNotSame("Invalid format Track ID", constraintViolations.iterator().next().getMessage());
    }

    /**
     * Test of getTrackId method, of class AnalyticProfile.
     */
    @Test
    public void testGetTrackId() {
        System.out.println("getTrackId");
        AnalyticProfile instance = new AnalyticProfile();
        instance.setTrackId("XS-ACDEF-01");
        instance.addGroup("unine");
        String expResult = "XS-ACDEF-01";
        String result = instance.getTrackId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTrackId method, of class AnalyticProfile.
     */
    @Test
    public void testSetTrackId() {
        System.out.println("setTrackId");
        String trackId = "XX-UUUUUU-94";
        AnalyticProfile instance = new AnalyticProfile();
        instance.setTrackId(trackId);

        // TODO add the hibernate validation

        assertEquals(trackId, instance.getTrackId());
    }

    /**
     * Test of getGroupName method, of class AnalyticProfile.
     */
    @Test
    public void testGetGroups() {
        System.out.println("getGroupName");

        AnalyticProfile instance = new AnalyticProfile();
        instance.setTrackId("XS-ACDEF-01");
        instance.addGroup("groupX");

        assertTrue(instance.getGroups().size() != 0);
        assertEquals("groupX", instance.getGroups().get(0));
        for (String g : instance.getGroups()) {
            assertNotNull(g);
        }
    }

    /**
     * Test of setGroupName method, of class AnalyticProfile.
     */
    @Test
    public void testSetGroups() {
        System.out.println("setGroups");
        AnalyticProfile instance = new AnalyticProfile();
        List<String> groups = new ArrayList<String>();
        groups.add("unine");
        groups.add("jboss-team");
        instance.setGroups(groups);
        assertEquals(groups, instance.getGroups());
    }
}
