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

package org.farng.mp3.object;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author $author$
 * @version $Revision: 1.3 $
 */
public class AbstractMP3ObjectTest extends TestCase {

    /**
     * Creates a new AbstractMP3ObjectTest object.
     */
    public AbstractMP3ObjectTest() {
        super();
    }

    /**
     * Creates a new AbstractMP3ObjectTest object.
     */
    public AbstractMP3ObjectTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(AbstractMP3ObjectTest.class);
    }

    /**
     *
     */
    public void testEquals() {
        AbstractMP3Object mp3Object;
        AbstractMP3Object testObject;
        String testString = "Test String";

        // ObjectBooleanByte
        mp3Object = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectBooleanString
        mp3Object = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectByteArraySizeTerminated
        mp3Object = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectGroupRepeated
        mp3Object = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectID3v2LyricLine
        mp3Object = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectLyrics3Image
        mp3Object = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectLyrics3Line
        mp3Object = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectLyrics3TimeStamp
        mp3Object = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectNumberFixedLength
        mp3Object = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectNumberHashMap
        mp3Object = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectNumberVariableLength
        mp3Object = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringDate
        mp3Object = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringDateTime
        mp3Object = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringFixedLength
        mp3Object = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringHashMap
        mp3Object = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringNullTerminated
        mp3Object = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));

        //ObjectStringSizeTerminated
        mp3Object = new ObjectStringSizeTerminated(testString);
        assertFalse(mp3Object.equals(null));
        testObject = new ObjectBooleanByte(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectBooleanString(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectByteArraySizeTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectGroupRepeated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectID3v2LyricLine(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Image(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3Line(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectLyrics3TimeStamp(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectNumberVariableLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDate(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringDateTime(testString);
        assertTrue(mp3Object.equals(testObject));
        testObject = new ObjectStringFixedLength(testString, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 4);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringNullTerminated(testString);
        assertFalse(mp3Object.equals(testObject));
        testObject = new ObjectStringSizeTerminated(testString);
        assertTrue(mp3Object.equals(testObject));
    }

    /**
     *
     */
    public void testGetIdentifier() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetValue() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testReadByteArray() { /* @TODO */
    } // this will be overridden

    /**
     *
     */
    public void testReadString() { /* @TODO */
    } // this will be overridden

    /**
     *
     */
    public void testSetValue() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testWriteByteArray() { /* @TODO */
    } //this will be overridden

    /**
     *
     */
    public void testWriteString() { /* @TODO */
    } // this will be overridden

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
