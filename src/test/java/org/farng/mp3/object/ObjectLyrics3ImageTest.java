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
public class ObjectLyrics3ImageTest extends TestCase {

    /**
     *
     */
    public ObjectLyrics3ImageTest() {
        super();
    }

    /**
     * @param name
     */
    public ObjectLyrics3ImageTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectLyrics3ImageTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        ObjectLyrics3Image object1;
        ObjectLyrics3Image object2;
        ObjectLyrics3TimeStamp time;
        time = new ObjectLyrics3TimeStamp("");
        time.setMinute(5);
        time.setSecond(31);
        object1 = new ObjectLyrics3Image("");
        assertFalse(object1.equals(null));
        object2 = new ObjectLyrics3Image("different");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        assertTrue(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setDescription("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setFilename("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setTimeStamp(time);
        assertFalse(object1.equals(object2));
        object1 = new ObjectLyrics3Image("");
        object1.setDescription("text");
        object2 = new ObjectLyrics3Image("");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setDescription("text");
        assertTrue(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setFilename("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setTimeStamp(time);
        assertFalse(object1.equals(object2));
        object1 = new ObjectLyrics3Image("");
        object1.setFilename("text");
        object2 = new ObjectLyrics3Image("");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setDescription("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setFilename("text");
        assertTrue(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setTimeStamp(time);
        assertFalse(object1.equals(object2));
        object1 = new ObjectLyrics3Image("");
        object1.setTimeStamp(time);
        object2 = new ObjectLyrics3Image("");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setDescription("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setFilename("text");
        assertFalse(object1.equals(object2));
        object2 = new ObjectLyrics3Image("");
        object2.setTimeStamp(time);
        assertTrue(object1.equals(object2));
        object1 = new ObjectLyrics3Image("");
        object1.setDescription("text");
        object1.setFilename("filename");
        object1.setTimeStamp(time);
        object2 = new ObjectLyrics3Image("");
        object2.setDescription("text");
        object2.setFilename("filename");
        object2.setTimeStamp(time);
        assertTrue(object1.equals(object2));
    }

    /**
     *
     */
    public void testGetDescription() { /* @TODO */
    } // accessor method

    /**
     *
     */
    public void testGetFilename() { /* @TODO */
    } // accessor method

    /**
     *
     */
    public void testGetSize() {
        ObjectLyrics3Image object;
        ObjectLyrics3TimeStamp time;
        time = new ObjectLyrics3TimeStamp("");
        time.setMinute(5);
        time.setSecond(31);
        object = new ObjectLyrics3Image("");
        assertEquals(4, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setDescription("1");
        assertEquals(5, object.getSize());
        object.setDescription("longer");
        assertEquals(10, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setFilename("1");
        assertEquals(5, object.getSize());
        object.setFilename("longer");
        assertEquals(10, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setTimeStamp(time);
        assertEquals(11, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setDescription("text");
        object.setFilename("text");
        assertEquals(12, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setDescription("text");
        object.setTimeStamp(time);
        assertEquals(15, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setFilename("text");
        object.setTimeStamp(time);
        assertEquals(15, object.getSize());
        object = new ObjectLyrics3Image("");
        object.setDescription("text");
        object.setFilename("text");
        object.setTimeStamp(time);
        assertEquals(19, object.getSize());
    }

    /**
     *
     */
    public void testGetTimeStamp() { /* @TODO */
    } // accessor method

    /**
     *
     */
    public void testReadString() {
        ObjectLyrics3Image object;
        ObjectLyrics3TimeStamp time;
        String string;
        time = new ObjectLyrics3TimeStamp("Time Stamp");
        time.setMinute(2);
        time.setSecond(13);
        object = new ObjectLyrics3Image("");
        try {
            object.readString(null);
            fail("NulPointerException not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        object = new ObjectLyrics3Image("");
        string = "filename||||";
        object.readString(string);
        assertEquals("filename", object.getFilename());
        assertEquals("", object.getDescription());
        assertEquals(null, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "||description||";
        object.readString(string);
        assertEquals("", object.getFilename());
        assertEquals("description", object.getDescription());
        assertEquals(null, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "||||[02:13]";
        object.readString(string);
        assertEquals("", object.getFilename());
        assertEquals("", object.getDescription());
        assertEquals(time, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "||description||[02:13]";
        object.readString(string);
        assertEquals("", object.getFilename());
        assertEquals("description", object.getDescription());
        assertEquals(time, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "filename||||[02:13]";
        object.readString(string);
        assertEquals("filename", object.getFilename());
        assertEquals("", object.getDescription());
        assertEquals(time, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "filename||description||";
        object.readString(string);
        assertEquals("filename", object.getFilename());
        assertEquals("description", object.getDescription());
        assertEquals(null, object.getTimeStamp());
        object = new ObjectLyrics3Image("");
        string = "filename||description||[02:13]";
        object.readString(string);
        assertEquals("filename", object.getFilename());
        assertEquals("description", object.getDescription());
        assertEquals(time, object.getTimeStamp());
    }

    /**
     *
     */
    public void testSetDescription() { /* @TODO */
    } // modifier method

    /**
     *
     */
    public void testSetFilename() { /* @TODO */
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
    } // debug method only. no need to test

    /**
     *
     */
    public void testWriteString() {
        ObjectLyrics3Image object;
        ObjectLyrics3TimeStamp time;
        time = new ObjectLyrics3TimeStamp("Time Stamp");
        time.setMinute(2);
        time.setSecond(13);
        object = new ObjectLyrics3Image("");
        assertEquals("||||", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename(null);
        object.setDescription(null);
        object.setTimeStamp(null);
        assertEquals("||||", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename("filename");
        object.setDescription(null);
        object.setTimeStamp(null);
        assertEquals("filename||||", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename(null);
        object.setDescription("description");
        object.setTimeStamp(null);
        assertEquals("||description||", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename(null);
        object.setDescription(null);
        object.setTimeStamp(time);
        assertEquals("||||[02:13]", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename(null);
        object.setDescription("description");
        object.setTimeStamp(time);
        assertEquals("||description||[02:13]", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename("filename");
        object.setDescription(null);
        object.setTimeStamp(time);
        assertEquals("filename||||[02:13]", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename("filename");
        object.setDescription("description");
        object.setTimeStamp(null);
        assertEquals("filename||description||", object.writeString());
        object = new ObjectLyrics3Image("");
        object.setFilename("filename");
        object.setDescription("description");
        object.setTimeStamp(time);
        assertEquals("filename||description||[02:13]", object.writeString());
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
