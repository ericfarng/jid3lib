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
public class ObjectByteArraySizeTerminatedTest extends TestCase {

    /**
     *
     */
    public ObjectByteArraySizeTerminatedTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectByteArraySizeTerminatedTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectByteArraySizeTerminatedTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        ObjectByteArraySizeTerminated object1;
        ObjectByteArraySizeTerminated object2;
        byte[] byteArray1 = {(byte) 1, (byte) 2, (byte) 3};
        byte[] byteArray2 = {(byte) 3, (byte) 2, (byte) 1};
        byte[] byteArray3 = {(byte) 1, (byte) 2, (byte) 3};
        object1 = new ObjectByteArraySizeTerminated("");
        object2 = new ObjectByteArraySizeTerminated("");

        // nulls
        object1.setValue(null);
        object2.setValue(null);
        assertTrue(object1.equals(object2));
        object1.setValue(byteArray1);
        assertFalse(object1.equals(object2));
        object1.setValue(null);
        object2.setValue(byteArray1);
        assertFalse(object1.equals(object2));
        object1.setValue(byteArray1);
        object2.setValue(byteArray3);
        assertTrue(object1.equals(object2));
        object1.setValue(byteArray1);
        object2.setValue(byteArray2);
        assertFalse(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetSize() {
        ObjectByteArraySizeTerminated object;
        byte[] byteArray0 = {};
        byte[] byteArray3 = {(byte) 1, (byte) 2, (byte) 3};
        byte[] byteArray2 = {(byte) 2, (byte) 3};
        byte[] byteArray1 = {(byte) 3};

        // test nulls
        object = new ObjectByteArraySizeTerminated("");
        assertEquals(0, object.getSize());
        object.readByteArray(byteArray0);
        assertEquals(0, object.getSize());
        object.readByteArray(byteArray1);
        assertEquals(1, object.getSize());
        object.readByteArray(byteArray2);
        assertEquals(2, object.getSize());
        object.readByteArray(byteArray3);
        assertEquals(3, object.getSize());
    }

    /**
     *
     */
    public void testReadByteArray() {
        ObjectByteArraySizeTerminated object;
        byte[] byteArray0 = {};
        byte[] byteArray1 = {(byte) 1, (byte) 2, (byte) 3};
        byte[] byteArray2 = {(byte) 2, (byte) 3};
        byte[] byteArray3 = {(byte) 3};

        // test nulls
        object = new ObjectByteArraySizeTerminated("");
        try {
            object.readByteArray(null);
            fail("Did not throw Null Pointer Exception");
        } catch (NullPointerException ex) {
            // this is desired
        }
        assertNull(object.getValue());
        object.readByteArray(byteArray0);

        assertTrue(((byte[])object.getValue()).length == 0);
        try {
            object.readByteArray(byteArray1, -1);
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // this is desired
        }
        try {

            object.readByteArray(byteArray1, 3);
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // this is desired
        }
        object.readByteArray(byteArray1);
        assertTrue(Arrays.equals(byteArray1, (byte[]) object.getValue()));
        object.readByteArray(byteArray1, 0);
        assertTrue(Arrays.equals(byteArray1, (byte[]) object.getValue()));
        object.readByteArray(byteArray1, 1);
        assertTrue(Arrays.equals(byteArray2, (byte[]) object.getValue()));
        object.readByteArray(byteArray1, 2);
        assertTrue(Arrays.equals(byteArray3, (byte[]) object.getValue()));
    }

    /**
     *
     */
    public void testToString() { /* @TODO */
    } // getSize() + " bytes"

    /**
     *
     */
    public void testWriteByteArray() { /* @TODO */
    } // return this.value;

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
