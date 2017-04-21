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
import java.util.Date;

/**
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class ObjectID3v2LyricLineTest extends TestCase {

    /**
     *
     */
    public ObjectID3v2LyricLineTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectID3v2LyricLineTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectID3v2LyricLineTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        ObjectID3v2LyricLine object1;
        ObjectID3v2LyricLine object2;
        long now = (new Date()).getTime();
        object1 = new ObjectID3v2LyricLine("");
        object1.setTimeStamp(now);
        object1.setText("text");
        assertFalse(object1.equals(null));
        object2 = new ObjectID3v2LyricLine("different");
        object2.setTimeStamp(now);
        object2.setText("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectID3v2LyricLine("");
        object2.setTimeStamp(now);
        object2.setText("different");
        assertFalse(object1.equals(object2));
        object2 = new ObjectID3v2LyricLine("");
        object2.setTimeStamp(now + 1);
        object2.setText("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectID3v2LyricLine("");
        object2.setTimeStamp(now + 1);
        object2.setText("different");
        assertFalse(object1.equals(object2));
        object2 = new ObjectID3v2LyricLine("");
        object2.setTimeStamp(now);
        object2.setText("text");
        assertTrue(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetSize() { /* @TODO */
    } // text.length + 1 + 4;

    /**
     *
     */
    public void testGetText() { /* @TODO */
    } // accessor method

    /**
     *
     */
    public void testGetTimeStamp() { /* @TODO */
    } // accessor method

    /**
     *
     */
    public void testReadByteArray() {
        byte[] byteArray1 = {(byte) 't', (byte) 'e', (byte) 'x', (byte) 't', (byte) 2, (byte) 1, (byte) 2, (byte) 1};
        ObjectID3v2LyricLine object1;
        int timestamp = 1 + (256 * (2 + (256 * (1 + (256 * 2)))));
        object1 = new ObjectID3v2LyricLine("");
        object1.readByteArray(byteArray1);
        assertTrue(object1.getText().equals("text"));
        assertEquals(timestamp, object1.getTimeStamp());
        byte[] byteArray2 = {(byte) 2, (byte) 1, (byte) 2, (byte) 1};
        object1.readByteArray(byteArray2);
        assertTrue(object1.getText().equals(""));
        assertEquals(timestamp, object1.getTimeStamp());
    }

    /**
     *
     */
    public void testSetText() { /* @TODO */
    } // modifier method

    /**
     *
     */
    public void testSetTimeStamp() { /* @TODO */
    } // modifier method

    /**
     *
     */
    public void testToString() { /* @TODO */
    } // timeStamp + text

    /**
     *
     */
    public void testWriteByteArray() {
        int timestamp = 1 + (256 * (2 + (256 * (1 + (256 * 2)))));
        byte[] byteArray1 = {(byte) 't',
                             (byte) 'e',
                             (byte) 'x',
                             (byte) 't',
                             (byte) 0,
                             (byte) 2,
                             (byte) 1,
                             (byte) 2,
                             (byte) 1};
        byte[] byteArray2 = {(byte) 0, (byte) 2, (byte) 1, (byte) 2, (byte) 1};
        ObjectID3v2LyricLine object1;
        object1 = new ObjectID3v2LyricLine("");
        object1.setTimeStamp(timestamp);
        object1.setText("text");
        assertTrue(Arrays.equals(object1.writeByteArray(), byteArray1));
        object1.setText("");
        assertTrue(Arrays.equals(object1.writeByteArray(), byteArray2));
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
