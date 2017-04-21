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

import java.util.Arrays;

/**
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class ObjectBooleanByteTest extends TestCase {

    /**
     *
     */
    public ObjectBooleanByteTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectBooleanByteTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectBooleanByteTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        ObjectBooleanByte object1;
        ObjectBooleanByte object2;
        object1 = new ObjectBooleanByte("one", 1);
        object2 = new ObjectBooleanByte("two", 1);
        assertFalse(object1.equals(object2));
        object1 = new ObjectBooleanByte("one", 0);
        object2 = new ObjectBooleanByte("one", 1);
        assertFalse(object1.equals(object2));
        object1 = new ObjectBooleanByte("one", 0);
        object2 = new ObjectBooleanByte("two", 1);
        assertFalse(object1.equals(object2));
        object1 = new ObjectBooleanByte("one", 0);
        object2 = new ObjectBooleanByte("one", 0);
        assertTrue(object1.equals(object2));
        byte[] byteArray1 = {(byte) 1, (byte) 1};
        byte[] byteArray2 = {(byte) 0, (byte) 0};
        object1.readByteArray(byteArray1, 0);
        object2.readByteArray(byteArray2, 0);
        assertFalse(object1.equals(object2));
        object2.readByteArray(byteArray1, 0);
        assertTrue(object1.equals(object2));

        // test nulls
        assertFalse(object1.equals(null));
        object1.setValue(null);
        object2.setValue(null);
        assertTrue(object1.equals(object2));
        object1.setValue(Boolean.TRUE);
        assertFalse(object1.equals(object2));
        object1.setValue(null);
        object2.setValue(Boolean.TRUE);
        assertFalse(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetBitPosition() { /* @TODO */
    } // get method only

    /**
     *
     */
    public void testGetSize() { /* @TODO */
    } // return 1;

    /**
     *
     */
    public void testReadByteArray() {
        ObjectBooleanByte object;
        byte[] byteArray1 = {(byte) 1, (byte) 0, (byte) 1};
        byte[] byteArray2 = {(byte) 0, (byte) 1, (byte) 0};
        byte[] byteArray3 = {(byte) 0, (byte) 1, (byte) 2, (byte) 4, (byte) 8, (byte) 16, (byte) 32, (byte) 64};

        // test nulls
        object = new ObjectBooleanByte("one", 0);
        try {
            object.readByteArray(null);
            fail("NullPointerException was not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        assertNull(object.getValue());
        try {
            object.readByteArray(byteArray1, -1);
            fail("IndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        assertNull(object.getValue());
        try {
            object.readByteArray(byteArray1, 3);
            fail("IndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        assertNull(object.getValue());

        // test others
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray1, 0);
        assertTrue(object.getValue().equals(Boolean.TRUE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray1, 1);
        assertTrue(object.getValue().equals(Boolean.FALSE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray1, 2);
        assertTrue(object.getValue().equals(Boolean.TRUE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray2, 0);
        assertTrue(object.getValue().equals(Boolean.FALSE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray2, 1);
        assertTrue(object.getValue().equals(Boolean.TRUE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray2, 2);
        assertTrue(object.getValue().equals(Boolean.FALSE));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray3, 0);
        assertTrue(object.getValue().equals(Boolean.FALSE));
        for (int i = 1; i < 8; i++) {
            object = new ObjectBooleanByte("one", i - 1);
            object.readByteArray(byteArray3, i);
            assertTrue(object.getValue().equals(Boolean.TRUE));
        }
    }

    /**
     *
     */
    public void testToString() { /* @TODO */
    } // boolean.toString()

    /**
     *
     */
    public void testWriteByteArray() {
        ObjectBooleanByte object;
        byte[] byteArray1 = {(byte) 1};
        byte[] byteArray2 = {(byte) 0};
        byte[] byteArray3 = {(byte) 0, (byte) 1, (byte) 2, (byte) 4, (byte) 8, (byte) 16, (byte) 32, (byte) 64};
        byte[] writeArray = null;

        // test nulls
        object = new ObjectBooleanByte("one", 0);
        writeArray = object.writeByteArray();
        assertTrue(writeArray.length == 1);
        try {
            object.readByteArray(null);
            fail("NullPointerException not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        writeArray = object.writeByteArray();
        assertTrue(writeArray.length == 1);

        // test others
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray1);
        writeArray = object.writeByteArray();
        assertTrue(Arrays.equals(writeArray, byteArray1));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray2);
        writeArray = object.writeByteArray();
        assertTrue(Arrays.equals(writeArray, byteArray2));
        object = new ObjectBooleanByte("one", 0);
        object.readByteArray(byteArray3, 0);
        assertTrue(object.getValue().equals(Boolean.FALSE));
        for (int i = 1; i < 8; i++) {
            object = new ObjectBooleanByte("one", i - 1);
            object.readByteArray(byteArray3, i);
            writeArray = object.writeByteArray();
            assertEquals(writeArray[0], byteArray3[i]);
        }
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
