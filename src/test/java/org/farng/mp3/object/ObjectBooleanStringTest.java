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
public class ObjectBooleanStringTest extends TestCase {

    /**
     *
     */
    public ObjectBooleanStringTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectBooleanStringTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectBooleanStringTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        ObjectBooleanString object1;
        ObjectBooleanString object2;
        String string1 = "010";
        String string2 = "101";

        // test nulls
        object1 = new ObjectBooleanString("");
        object2 = new ObjectBooleanString("");
        assertFalse(object1.equals(null));
        object1.readString(string1, 0);
        assertFalse(object1.equals(null));
        object1.setValue(null);
        object2.setValue(null);
        assertTrue(object1.equals(object2));
        object1.setValue(Boolean.TRUE);
        assertFalse(object1.equals(object2));
        object1.setValue(null);
        object2.setValue(Boolean.TRUE);
        assertFalse(object1.equals(object2));

        // test others
        object1 = new ObjectBooleanString("");
        object2 = new ObjectBooleanString("different");
        assertFalse(object1.equals(object2));
        object2 = new ObjectBooleanString("");
        assertTrue(object1.equals(object2));
        object1.readString(string1);
        assertFalse(object1.equals(object2));
        object2.readString(string2);
        assertFalse(object1.equals(object2));
        object2.readString(string1);
        assertTrue(object1.equals(object2));
        object1.readString(string1, 0);
        object2.readString(string2, 0);
        assertFalse(object1.equals(object2));
        object1.readString(string1, 0);
        object2.readString(string2, 1);
        assertTrue(object1.equals(object2));
        object1.readString(string1, 0);
        object2.readString(string2, 2);
        assertFalse(object1.equals(object2));
        object1.readString(string1, 1);
        object2.readString(string2, 0);
        assertTrue(object1.equals(object2));
        object1.readString(string1, 1);
        object2.readString(string2, 1);
        assertFalse(object1.equals(object2));
        object1.readString(string1, 1);
        object2.readString(string2, 2);
        assertTrue(object1.equals(object2));
        object1.readString(string1, 2);
        object2.readString(string2, 0);
        assertFalse(object1.equals(object2));
        object1.readString(string1, 2);
        object2.readString(string2, 1);
        assertTrue(object1.equals(object2));
        object1.readString(string1, 2);
        object2.readString(string2, 2);
        assertFalse(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetSize() { /* @TODO */
    } // return 1;

    /**
     *
     */
    public void testReadString() {
        ObjectBooleanString object1;
        String string1 = "010";
        String string2 = "101";

        // test nulls
        object1 = new ObjectBooleanString("");
        try {
            object1.readString(null);
            fail("NullPointerException was not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        assertNull(object1.getValue());
        try {
            object1.readString(string1, -1);
            fail("IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        assertNull(object1.getValue());
        try {
            object1.readString(string1, 3);
            fail("IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        assertNull(object1.getValue());
        object1.readString(string1);
        assertTrue(object1.getValue().equals(Boolean.FALSE));
        object1.readString(string1, 0);
        assertTrue(object1.getValue().equals(Boolean.FALSE));
        object1.readString(string1, 1);
        assertTrue(object1.getValue().equals(Boolean.TRUE));
        object1.readString(string1, 2);
        assertTrue(object1.getValue().equals(Boolean.FALSE));
        object1.readString(string2);
        assertTrue(object1.getValue().equals(Boolean.TRUE));
        object1.readString(string2, 0);
        assertTrue(object1.getValue().equals(Boolean.TRUE));
        object1.readString(string2, 1);
        assertTrue(object1.getValue().equals(Boolean.FALSE));
        object1.readString(string2, 2);
        assertTrue(object1.getValue().equals(Boolean.TRUE));
    }

    /**
     *
     */
    public void testToString() { /* @TODO */
    } // boolean.toString()

    /**
     *
     */
    public void testWriteString() {
        ObjectBooleanString object1;

        // test nulls
        object1 = new ObjectBooleanString("");
        assertTrue(object1.writeString().length() == 1);
        object1.readString("1");
        assertTrue(object1.writeString().equals("1"));
        object1.readString("0");
        assertTrue(object1.writeString().equals("0"));
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
