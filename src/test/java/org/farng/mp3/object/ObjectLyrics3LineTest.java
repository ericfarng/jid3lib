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
public class ObjectLyrics3LineTest extends TestCase {

    /**
     *
     */
    public ObjectLyrics3LineTest() {
        super();
    }

    /**
     * @param arg0
     */
    public ObjectLyrics3LineTest(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new TestSuite(ObjectLyrics3ImageTest.class);
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

    /**
     *
     */
    protected void testEquals() {
        ObjectLyrics3TimeStamp timestamp = new ObjectLyrics3TimeStamp("timestamp");
        ObjectLyrics3Line line1 = new ObjectLyrics3Line("line");
        ObjectLyrics3Line line2 = new ObjectLyrics3Line("line");
        assertFalse(line1.equals(line2));
        assertTrue(line1.equals(line2));
        line1.setLyric("lyric");
        assertFalse(line1.equals(line2));
        line1 = new ObjectLyrics3Line("asdf");
        line1.setTimeStamp(timestamp);
        assertFalse(line1.equals(line2));
    }

    /**
     *
     */
    protected void testGetMinute() { /* @TODO */
    } // accessor / settor method

    /**
     *
     */
    protected void testGetSecond() { /* @TODO */
    } // accessor / settor method

    /**
     *
     */
    protected void testGetSize() { /* @TODO */
    } // accessor / settor method

    /**
     *
     */
    protected void testReadString() { /* @TODO */
    }

    /**
     *
     */
    protected void testSetMinute() { /* @TODO */
    } // accessor / settor method

    /**
     *
     */
    protected void testSetSecond() { /* @TODO */
    } // accessor / settor method

    /**
     *
     */
    protected void testSetTimeStamp() { /* @TODO */
    }

    /**
     *
     */
    protected void testToString() { /* @TODO */
    } // calls writeString();

    /**
     *
     */
    protected void testWriteString() { /* @TODO */
    }
}
