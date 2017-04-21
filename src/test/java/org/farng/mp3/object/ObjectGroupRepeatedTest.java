/*
 * MP3 Tag library. It includes an implementation of the ID3 tags and Lyrics3
 * tags as they are defined at www.id3.org
 *
 * Copyright (C) Eric Farng 2004
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * MP3 Tag library. It includes an implementation of the ID3 tags and Lyrics3
 * tags as they are defined at www.id3.org
 *
 * Copyright (C) Eric Farng 2003
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.farng.mp3.object;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class ObjectGroupRepeatedTest extends TestCase {

    /**
     *
     */
    public ObjectGroupRepeatedTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectGroupRepeatedTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectGroupRepeatedTest.class);
    }

    /**
     *
     */
    public void testAddObject() { /* @TODO */
    } // wrap list.add() method

    /**
     *
     */
    public void testAddProperty() { /* @TODO */
    } // wrap list.add() method

    /**
     *
     */
    public void testEquals() {
        ObjectGroupRepeated object1;
        ObjectGroupRepeated object2;
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("different");
        assertFalse(object1.equals(null));
        assertFalse(object1.equals(object2));
        object2 = new ObjectGroupRepeated("");
        assertTrue(object1.equals(object2));
        object1.addObject(new ObjectBooleanString(""));
        assertFalse(object1.equals(object2));
        object2.addObject(new ObjectBooleanString(""));
        assertTrue(object1.equals(object2));
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        assertFalse(object1.equals(object2));
        object2.addProperty(new ObjectNumberFixedLength("", 4));
        assertTrue(object1.equals(object2));
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("");
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        assertFalse(object1.equals(object2));
        object2.addProperty(new ObjectNumberFixedLength("", 4));
        assertTrue(object1.equals(object2));
        object1.addObject(new ObjectBooleanString(""));
        assertFalse(object1.equals(object2));
        object2.addObject(new ObjectBooleanString(""));
        assertTrue(object1.equals(object2));
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        assertFalse(object1.equals(object2));
        object2.addProperty(new ObjectNumberFixedLength("", 4));
        assertTrue(object1.equals(object2));
        object1.addObject(new ObjectBooleanString(""));
        assertFalse(object1.equals(object2));
        object2.addObject(new ObjectBooleanString(""));
        assertTrue(object1.equals(object2));
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("");
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        object2.addProperty(new ObjectNumberFixedLength("", 5));
        assertFalse(object1.equals(object2));
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("");
        object1.addObject(new ObjectBooleanString(""));
        object2.addObject(new ObjectBooleanString("different"));
        assertFalse(object1.equals(object2));
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("");
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        object2.addProperty(new ObjectNumberFixedLength("", 4));
        assertTrue(object1.equals(object2));
        object1.addObject(new ObjectBooleanString(""));
        object2.addObject(new ObjectBooleanString("different"));
        assertFalse(object1.equals(object2));
        object1 = new ObjectGroupRepeated("");
        object2 = new ObjectGroupRepeated("");
        object1.addObject(new ObjectBooleanString(""));
        object2.addObject(new ObjectBooleanString(""));
        assertTrue(object1.equals(object2));
        object1.addProperty(new ObjectNumberFixedLength("", 4));
        object2.addProperty(new ObjectNumberFixedLength("", 5));
        assertFalse(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetObjectList() { /* @TODO */
    } // accessor method only

    /**
     *
     */
    public void testGetPropertyList() { /* @TODO */
    } // accessor method only

    /**
     *
     */
    public void testGetSize() {
        ObjectGroupRepeated object;
        object = new ObjectGroupRepeated("");
        assertEquals(0, object.getSize());
        object.addObject(new ObjectNumberFixedLength("", 4));
        assertEquals(4, object.getSize());
        object.addObject(new ObjectBooleanByte("", 0));
        assertEquals(5, object.getSize());
    }

    /**
     *
     */
    protected void setUp() {
        // none required
    }

    /**
     *
     */
    protected void tearDown() {
        // none required
    }
}
