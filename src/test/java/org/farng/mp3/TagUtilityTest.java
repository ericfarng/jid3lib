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

package org.farng.mp3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * <p/>
 * Title: </p>
 * <p/>
 * <p/>
 * Description: </p>
 * <p/>
 * <p/>
 * Copyright: Copyright (c) 2003 </p>
 * <p/>
 * <p/>
 * Company: </p>
 *
 * @author $author$
 * @version 1.0
 */
public class TagUtilityTest extends TestCase {

    /**
     * Creates a new TagUtilityTest object.
     */
    public TagUtilityTest() {
        // base empty constructor
    }

    /**
     * Creates a new TagUtilityTest object.
     */
    public TagUtilityTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TagUtilityTest.class);
    }

    public void testAppendBeforeExtension() {
        // test nulls
        assertNull(TagUtility.appendBeforeExtension(null, null));
        assertEquals("", TagUtility.appendBeforeExtension(null, ""));
        assertEquals("add", TagUtility.appendBeforeExtension(null, "add"));

        // different null and empty values
        assertEquals("oneadd.two", TagUtility.appendBeforeExtension("one.two", "add"));
        assertEquals("one.two", TagUtility.appendBeforeExtension("one.two", ""));
        assertEquals("one.two", TagUtility.appendBeforeExtension("one.two", null));
        assertEquals("add", TagUtility.appendBeforeExtension("", "add"));
        assertEquals("", TagUtility.appendBeforeExtension("", ""));
        assertEquals("", TagUtility.appendBeforeExtension("", null));

        // multiple dots
        assertEquals(".one.twoadd.", TagUtility.appendBeforeExtension(".one.two.", "add"));
        assertEquals(".oneadd.two", TagUtility.appendBeforeExtension(".one.two", "add"));
        assertEquals(".onetwoadd.", TagUtility.appendBeforeExtension(".onetwo.", "add"));
        assertEquals("add.onetwo", TagUtility.appendBeforeExtension(".onetwo", "add"));
        assertEquals("one.twoadd.", TagUtility.appendBeforeExtension("one.two.", "add"));
        assertEquals("oneadd.two", TagUtility.appendBeforeExtension("one.two", "add"));
        assertEquals("onetwoadd.", TagUtility.appendBeforeExtension("onetwo.", "add"));
        assertEquals("onetwoadd", TagUtility.appendBeforeExtension("onetwo", "add"));
    }

    /**
     * Assumes the buffer size inside tagUtility is 1024 bytes
     */
    public void testCopyFile() throws IOException {
        File source = null;
        File destination = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            try {
                source = File.createTempFile("temp", ".txt");
                destination = File.createTempFile("temp", ".txt");
                fos = new FileOutputStream(source);
                fis = new FileInputStream(destination);
            } catch (IOException ex) {
                fail("Unable to create temp files needed to perform test");
            }

            // test nulls
            try {
                TagUtility.copyFile(null, null);
                fail("TagUtility.copyFile(null, null): NullPointerException was not thrown");
            } catch (NullPointerException ex) {
                // correct behavior
            }
            try {
                TagUtility.copyFile(source, null);
                fail("TagUtility.copyFile(source, null): NullPointerException not thrown");
            } catch (NullPointerException ex) {
                // correct behavior
            }
            try {
                TagUtility.copyFile(null, destination);
                fail("TagUtility.copyFile(null, destination): NullPointerException not thrown");
            } catch (NullPointerException ex) {
                // correct behavior
            }

            // test missing source file.
            destination.delete();
            try {
                TagUtility.copyFile(destination, source);
                fail("TagUtility.copyFile(MISSING, destination): NullPointerException not thrown");
            } catch (NullPointerException ex) {
                // correct behavior
            }

            // test copying empty file
            TagUtility.copyFile(source, destination);
            assertTrue(destination.exists());
            assertTrue(destination.length() == 0);
            fis.close();
            fos.close();

            // one byte
            fos = new FileOutputStream(source);
            fos.write('a');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 1);
            fis = new FileInputStream(destination);
            assertTrue(fis.read() == 'a');
            fis.close();
            fos.close();

            // 1023 bytes
            fos = new FileOutputStream(source);
            for (int i = 0; i < 1023; i++)  fos.write('b');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 1023);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 1023; i++)  assertTrue(fis.read() == 'b');
            fis.close();
            fos.close();

            // 1024 bytes
            fos = new FileOutputStream(source);
            for (int i = 0; i < 1024; i++)  fos.write('c');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 1024);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 1024; i++)  assertTrue(fis.read() == 'c');
            fis.close();
            fos.close();

            // 1025 bytes
            fos = new FileOutputStream(source);
            for (int i = 0; i < 1025; i++)  fos.write('d');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 1025);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 1025; i++)  assertTrue(fis.read() == 'd');
            fis.close();
            fos.close();

            // 2047 bytes
            fos = new FileOutputStream(source);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 2047; i++)  fos.write('e');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 2047);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 2047; i++)  assertTrue(fis.read() == 'e');
            fis.close();
            fos.close();

            // 2048 bytes
            fos = new FileOutputStream(source);
            for (int i = 0; i < 2048; i++)  fos.write('f');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 2048);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 2048; i++)  assertTrue(fis.read() == 'f');
            fis.close();
            fos.close();

            // 2049 bytes
            fos = new FileOutputStream(source);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 2049; i++)  fos.write('g');
            fos.flush();
            TagUtility.copyFile(source, destination);
            assertTrue(destination.length() == 2049);
            fis = new FileInputStream(destination);
            for (int i = 0; i < 2049; i++)  assertTrue(fis.read() == 'g');
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fis != null) {
                fis.close();
            }
            if (source != null) {
                source.delete();
            }
            if (destination != null) {
                destination.delete();
            }
        }
    }

    /**
     * Assuming default parenthesis
     */
    public void testFindMatchingParenthesis() {
        TagOptionSingleton option = TagOptionSingleton.getInstance();

        // test nulls
        try {
            TagUtility.findMatchingParenthesis(null, 0);
            fail("TagUtility.findMatchingParenthesis(null, 0): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        try {
            TagUtility.findMatchingParenthesis("test", -1);
            fail("TagUtility.findMatchingParenthesis(\"test\", -1): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        try {
            TagUtility.findMatchingParenthesis("test", 100);
            fail("TagUtility.findMatchingParenthesis(\"test\", 100): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        try {
            TagUtility.findMatchingParenthesis("", 0);
            fail("TagUtility.findMatchingParenthesis(\"\", 0): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }

        // test tag option parenthesis
        Iterator iterator = option.getOpenParenthesisIterator();
        String open = null;
        String close = null;
        while (iterator.hasNext()) {
            open = (String) iterator.next();
            close = option.getCloseParenthesis(open);
            assertEquals("open parenthesis = " + open, 2, TagUtility.findMatchingParenthesis(open + " " + close, 0));
        }

        // test valid parenthesis
        assertEquals(1, TagUtility.findMatchingParenthesis("()", 0));
        assertEquals(3, TagUtility.findMatchingParenthesis("([])", 0));
        assertEquals(2, TagUtility.findMatchingParenthesis("([])", 1));
        assertEquals(4, TagUtility.findMatchingParenthesis("(one)", 0));
        assertEquals(14, TagUtility.findMatchingParenthesis("(one(two)three)", 0));
        assertEquals(8, TagUtility.findMatchingParenthesis("(one(two)three)", 4));
        assertEquals(14, TagUtility.findMatchingParenthesis("(one[two]three)", 0));
        assertEquals(8, TagUtility.findMatchingParenthesis("(one[two]three)", 4));
        assertEquals(20, TagUtility.findMatchingParenthesis("(one[two]three{}four)", 0));
        assertEquals(8, TagUtility.findMatchingParenthesis("(one[two]three{}four)", 4));
        assertEquals(15, TagUtility.findMatchingParenthesis("(one[two]three{}four)", 14));

        // test invalid parenthesis
        assertEquals(-1, TagUtility.findMatchingParenthesis("(", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis(")", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("(()", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("([]", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("(one[two]", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("{one[two}three]", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("([two]three]", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("([)]", 0));
        assertEquals(-1, TagUtility.findMatchingParenthesis("(two([]three)", 0));
    }

    /**
     * @throws TagException
     */
    public void testFindNumber() throws TagException {
        // test nulls
        try {
            TagUtility.findNumber(null);
            fail("TagUtility.findNumber(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        try {
            TagUtility.findNumber("");
            fail("TagUtility.findNumber(\"\"): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        try {
            TagUtility.findNumber(null, -1);
            fail("TagUtility.findNumber(null, -1): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            // correct behavior
        }
        try {
            TagUtility.findNumber("foo", 100);
            fail("TagUtility.findNumber(\"foo\",100): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        try {
            TagUtility.findNumber("foo", -1);
            fail("TagUtility.findNumber(\"\",-1): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            // correct behavior
        }
        assertEquals(100, TagUtility.findNumber("100"));
        assertEquals(123, TagUtility.findNumber("123"));
        assertEquals(1, TagUtility.findNumber("1"));
        assertEquals(-1233, TagUtility.findNumber("-1233"));
        assertEquals(0, TagUtility.findNumber("0"));
        assertEquals(100, TagUtility.findNumber("100ab"));
        assertEquals(123, TagUtility.findNumber("123abc"));
        assertEquals(1, TagUtility.findNumber("1abcd"));
        assertEquals(-1233, TagUtility.findNumber("-1233abde"));
        assertEquals(0, TagUtility.findNumber("0abcdef"));
        assertEquals(100, TagUtility.findNumber("a100", 1));
        assertEquals(123, TagUtility.findNumber("ab123", 2));
        assertEquals(1, TagUtility.findNumber("abc1", 3));
        assertEquals(-1233, TagUtility.findNumber("abcd-1233", 4));
        assertEquals(0, TagUtility.findNumber("abcde0", 5));
        assertEquals(100, TagUtility.findNumber("a100abcde", 1));
        assertEquals(123, TagUtility.findNumber("ab123abcd", 2));
        assertEquals(1, TagUtility.findNumber("abc1abc", 3));
        assertEquals(-1233, TagUtility.findNumber("abcd-1233ab", 4));
        assertEquals(0, TagUtility.findNumber("abcde0a", 5));
        assertEquals(100, TagUtility.findNumber("a100"));
        assertEquals(123, TagUtility.findNumber("ab123"));
        assertEquals(1, TagUtility.findNumber("abc1"));
        assertEquals(-1233, TagUtility.findNumber("abcd-1233"));
        assertEquals(0, TagUtility.findNumber("abcde0"));
        assertEquals(123, TagUtility.findNumber("123.321"));
        assertEquals(23, TagUtility.findNumber("123.321", 1));
        assertEquals(321, TagUtility.findNumber("123.321", 3));
        assertEquals(21, TagUtility.findNumber("123.321", 5));
    }

    /**
     *
     */
    public void testGetFrameDescription() {
        // test failures
        try {
            TagUtility.getFrameDescription(null);
            fail("TagUtility.getFrameDescription(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertNull(TagUtility.getFrameDescription(""));
        assertNull(TagUtility.getFrameDescription("123"));
        assertNull(TagUtility.getFrameDescription("1234"));
        assertNull(TagUtility.getFrameDescription("none frame identifier"));

        // ID3v2.2
        assertEquals("MPEG location lookup table", TagUtility.getFrameDescription("MLL"));

        // ID3v2.3
        assertEquals("Equalization", TagUtility.getFrameDescription("EQUA"));

        // ID3v2.4
        assertEquals("Audio encryption", TagUtility.getFrameDescription("AENC"));

        // Lyrics3v2
        assertEquals("Indications field", TagUtility.getFrameDescription("IND"));
    }

    /**
     *
     */
    public void testGetWholeNumber() {
        //test nulls
        try {
            TagUtility.getWholeNumber(null);
            fail("TagUtility.getWholeNumber(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals(0, TagUtility.getWholeNumber(new Byte("0")));
        assertEquals(0, TagUtility.getWholeNumber(new Byte((byte) 0)));
        assertEquals(0, TagUtility.getWholeNumber(new Short("0")));
        assertEquals(0, TagUtility.getWholeNumber(new Short((short) 0)));
        assertEquals(0, TagUtility.getWholeNumber(new Integer("0")));
        assertEquals(0, TagUtility.getWholeNumber(new Integer(0)));
        assertEquals(0, TagUtility.getWholeNumber(new Long("0")));
        assertEquals(0, TagUtility.getWholeNumber(new Long(0)));
        assertEquals(0, TagUtility.getWholeNumber("0"));
    }

    /**
     *
     */
    public void testIsID3v2_2FrameIdentifier() {
        //test nulls
        try {
            TagUtility.isID3v2_2FrameIdentifier(null);
            fail("TagUtility.isID3v2_2FrameIdentifier(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertFalse(TagUtility.isID3v2_2FrameIdentifier(""));
        assertFalse(TagUtility.isID3v2_2FrameIdentifier("123"));
        assertFalse(TagUtility.isID3v2_2FrameIdentifier("EQUA"));
        assertTrue(TagUtility.isID3v2_2FrameIdentifier("EQU2"));
        assertFalse(TagUtility.isID3v2_2FrameIdentifier("IND"));
        assertTrue(TagUtility.isID3v2_2FrameIdentifier("MLL2"));
        assertTrue(TagUtility.isID3v2_2FrameIdentifier("MLL"));
    }

    /**
     *
     */
    public void testIsID3v2_3FrameIdentifier() {
        //test nulls
        try {
            TagUtility.isID3v2_3FrameIdentifier(null);
            fail("TagUtility.isID3v2_3FrameIdentifier(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertFalse(TagUtility.isID3v2_3FrameIdentifier(""));
        assertFalse(TagUtility.isID3v2_3FrameIdentifier("1234"));
        assertTrue(TagUtility.isID3v2_3FrameIdentifier("EQUA"));
        assertFalse(TagUtility.isID3v2_3FrameIdentifier("EQU2"));
        assertFalse(TagUtility.isID3v2_3FrameIdentifier("IND"));
        assertFalse(TagUtility.isID3v2_3FrameIdentifier("MLL"));
    }

    /**
     *
     */
    public void testIsID3v2_4FrameIdentifier() {
        //test nulls
        try {
            TagUtility.isID3v2_4FrameIdentifier(null);
            fail("TagUtility.isID3v2_4FrameIdentifier(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertFalse(TagUtility.isID3v2_4FrameIdentifier(""));
        assertFalse(TagUtility.isID3v2_4FrameIdentifier("123"));
        assertTrue(TagUtility.isID3v2_4FrameIdentifier("EQU2"));
        assertFalse(TagUtility.isID3v2_4FrameIdentifier("EQUA"));
        assertFalse(TagUtility.isID3v2_4FrameIdentifier("IND"));
        assertFalse(TagUtility.isID3v2_4FrameIdentifier("MLL"));
    }

    /**
     *
     */
    public void testIsLyrics3v2FieldIdentifier() {
        //test nulls
        try {
            TagUtility.isLyrics3v2FieldIdentifier(null);
            fail("TagUtility.isLyrics3v2FieldIdentifier(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertFalse(TagUtility.isLyrics3v2FieldIdentifier(""));
        assertFalse(TagUtility.isLyrics3v2FieldIdentifier("123"));
        assertFalse(TagUtility.isLyrics3v2FieldIdentifier("EQU2"));
        assertFalse(TagUtility.isLyrics3v2FieldIdentifier("EQUA"));
        assertTrue(TagUtility.isLyrics3v2FieldIdentifier("IND"));
        assertFalse(TagUtility.isLyrics3v2FieldIdentifier("MLL"));
    }

    /**
     *
     */
    public void testIsMatchingParenthesis() {
        //test nulls
        try {
            TagUtility.isMatchingParenthesis(null);
            fail("TagUtility.isMatchingParenthesis(null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }

        // true
        assertTrue(TagUtility.isMatchingParenthesis(""));
        assertTrue(TagUtility.isMatchingParenthesis("[]"));
        assertTrue(TagUtility.isMatchingParenthesis("(())"));
        assertTrue(TagUtility.isMatchingParenthesis("one[two]three"));
        assertTrue(TagUtility.isMatchingParenthesis("(one[]two)three"));

        // false
        assertFalse(TagUtility.isMatchingParenthesis("["));
        assertFalse(TagUtility.isMatchingParenthesis("one["));
        assertFalse(TagUtility.isMatchingParenthesis("one[two]three)"));
        assertFalse(TagUtility.isMatchingParenthesis("]"));
        assertFalse(TagUtility.isMatchingParenthesis("(one("));
        assertFalse(TagUtility.isMatchingParenthesis("one(one("));
        assertFalse(TagUtility.isMatchingParenthesis("two(two(two)"));
        assertFalse(TagUtility.isMatchingParenthesis("one(two(three)four)five)six"));
        assertFalse(TagUtility.isMatchingParenthesis("one(two[three)four]five"));
        assertFalse(TagUtility.isMatchingParenthesis("[)"));
    }

    /**
     *
     */
    public void testPadString() {
        // nulls
        assertEquals("", TagUtility.padString(null, 0, '.', true));
        assertEquals(null, TagUtility.padString(null, -1, '.', true));
        assertEquals("", TagUtility.padString("", 0, '.', true));
        assertEquals("", TagUtility.padString("", -1, '.', true));
        assertEquals("one", TagUtility.padString("one", -1, '.', true));
        assertEquals("one", TagUtility.padString("one", 2, '.', true));
        assertEquals("one", TagUtility.padString("one", 3, '.', true));
        assertEquals("", TagUtility.padString("", 0, '.', true));
        assertEquals(".", TagUtility.padString("", 1, '.', true));
        assertEquals("...", TagUtility.padString("", 3, '.', true));
        assertEquals("", TagUtility.padString("", 0, '.', false));
        assertEquals(".", TagUtility.padString("", 1, '.', false));
        assertEquals("...", TagUtility.padString("", 3, '.', false));
        assertEquals(".one", TagUtility.padString("one", 4, '.', true));
        assertEquals("..one", TagUtility.padString("one", 5, '.', true));
        assertEquals("one.", TagUtility.padString("one", 4, '.', false));
        assertEquals("one..", TagUtility.padString("one", 5, '.', false));
    }

    /**
     *
     */
    public void testReplaceEOLNwithCRLF() {
        String CRLF = TagConstant.SEPERATOR_LINE;

        // nulls
        assertNull(TagUtility.replaceEOLNwithCRLF(null));
        assertEquals("", TagUtility.replaceEOLNwithCRLF(""));
        assertEquals(CRLF, TagUtility.replaceEOLNwithCRLF("\n"));
        assertEquals("one" + CRLF, TagUtility.replaceEOLNwithCRLF("one\n"));
        assertEquals(CRLF + "two", TagUtility.replaceEOLNwithCRLF("\ntwo"));
        assertEquals("one" + CRLF + "two", TagUtility.replaceEOLNwithCRLF("one\ntwo"));
        assertEquals("one" + CRLF + "two" + CRLF + "three", TagUtility.replaceEOLNwithCRLF("one\ntwo\nthree"));
        assertEquals("one" + CRLF + "two" + CRLF, TagUtility.replaceEOLNwithCRLF("one\ntwo\n"));
        assertEquals("one" + CRLF + CRLF + "three", TagUtility.replaceEOLNwithCRLF("one\n\nthree"));
        assertEquals("one" + CRLF + CRLF, TagUtility.replaceEOLNwithCRLF("one\n\n"));
        assertEquals(CRLF + "two" + CRLF + "three", TagUtility.replaceEOLNwithCRLF("\ntwo\nthree"));
        assertEquals(CRLF + "two" + CRLF, TagUtility.replaceEOLNwithCRLF("\ntwo\n"));
        assertEquals(CRLF + CRLF + "three", TagUtility.replaceEOLNwithCRLF("\n\nthree"));
        assertEquals(CRLF + CRLF, TagUtility.replaceEOLNwithCRLF("\n\n"));
    }

    /**
     *
     */
    public void testReplaceWord() {
        //nulls
        try {
            TagUtility.replaceWord(null, null, null);
            fail("TagUtility.replaceWord(null, null, null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, null, "");
            fail("TagUtility.replaceWord(null, null, \"\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, null, "new");
            fail("TagUtility.replaceWord(null, null, \"new\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "", null);
            fail("TagUtility.replaceWord(null, \"\", null);: NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "", "");
            fail("TagUtility.replaceWord(null, \"\", \"\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "", "new");
            fail("TagUtility.replaceWord(null, \"\", \"new\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "old", null);
            fail("TagUtility.replaceWord(null, \"old\", null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "old", "");
            fail("TagUtility.replaceWord(null, \"old\", \"\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord(null, "old", "new");
            fail("TagUtility.replaceWord(null, \"old\", \"new\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord("", null, null);
            fail("TagUtility.replaceWord(\"\", null, null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord("", null, "");
            fail("TagUtility.replaceWord(\"\", null, \"\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord("", null, "new");
            fail("TagUtility.replaceWord(\"\", null, \"new\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals("", TagUtility.replaceWord("", "", null));
        assertEquals("", TagUtility.replaceWord("", "", ""));
        assertEquals("", TagUtility.replaceWord("", "", "new"));
        assertEquals("", TagUtility.replaceWord("", "old", null));
        assertEquals("", TagUtility.replaceWord("", "old", ""));
        assertEquals("", TagUtility.replaceWord("", "old", "new"));
        try {
            TagUtility.replaceWord("123 old 456", null, null);
            fail("TagUtility.replaceWord(\"123 old 456\", null, null): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord("123 old 456", null, "");
            fail("TagUtility.replaceWord(\"123 old 456\", null, \"\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.replaceWord("123 old 456", null, "new");
            fail("TagUtility.replaceWord(\"123 old 456\", null, \"new\"): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals("123 old 456", TagUtility.replaceWord("123 old 456", "", null));
        assertEquals("123 old 456", TagUtility.replaceWord("123 old 456", "", ""));
        assertEquals("123 old 456", TagUtility.replaceWord("123 old 456", "", "new"));
        assertEquals("123  456", TagUtility.replaceWord("123 old 456", "old", null));
        assertEquals("123  456", TagUtility.replaceWord("123 old 456", "old", ""));
        assertEquals("123 new 456", TagUtility.replaceWord("123 old 456", "old", "new"));
        assertEquals("new 123 new 456 new", TagUtility.replaceWord("old 123 old 456 old", "old", "new"));
        assertEquals("new 123 new 456", TagUtility.replaceWord("old 123 old 456", "old", "new"));
        assertEquals("new 123 456 new", TagUtility.replaceWord("old 123 456 old", "old", "new"));
        assertEquals("new 123 456", TagUtility.replaceWord("old 123 456", "old", "new"));
        assertEquals("123 new 456 new", TagUtility.replaceWord("123 old 456 old", "old", "new"));
        assertEquals("123 new 456", TagUtility.replaceWord("123 old 456", "old", "new"));
        assertEquals("123 456 new", TagUtility.replaceWord("123 456 old", "old", "new"));
        assertEquals("123 456", TagUtility.replaceWord("123 456", "old", "new"));
    }

    /**
     *
     */
    public void testStripChar() {
        //test nulls
        try {
            TagUtility.stripChar(null, '1');
            fail("TagUtility.stripChar(null, '1'): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals("", TagUtility.stripChar("", '1'));
        assertEquals("onetwo", TagUtility.stripChar("onetwo", '1'));
        assertEquals("onetwo", TagUtility.stripChar("onetwo1", '1'));
        assertEquals("onetwo", TagUtility.stripChar("one1two", '1'));
        assertEquals("onetwo", TagUtility.stripChar("one1two1", '1'));
        assertEquals("onetwo", TagUtility.stripChar("1onetwo", '1'));
        assertEquals("onetwo", TagUtility.stripChar("1onetwo1", '1'));
        assertEquals("onetwo", TagUtility.stripChar("1one1two", '1'));
        assertEquals("onetwo", TagUtility.stripChar("1one1two1", '1'));
    }

    /**
     *
     */
    public void testToSentenceCase() {
        //test nulls
        try {
            TagUtility.toSentenceCase(null, true);
            fail("TagUtility.toSentenceCase(null, true): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.toSentenceCase(null, false);
            fail("TagUtility.toSentenceCase(null, false): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals("", TagUtility.toSentenceCase("", true));
        assertEquals("", TagUtility.toSentenceCase("", false));
        assertEquals("I one I two I three", TagUtility.toSentenceCase("i one i two i THREE", false));
        assertEquals("I one I two three", TagUtility.toSentenceCase("i ONE i two three", false));
        assertEquals("I one two I three", TagUtility.toSentenceCase("i one two i three", false));
        assertEquals("I one two three", TagUtility.toSentenceCase("i ONE two THREE", false));
        assertEquals("One I two I three", TagUtility.toSentenceCase("one i TWO i three", false));
        assertEquals("One I two three", TagUtility.toSentenceCase("one i two THREE", false));
        assertEquals("One two I three", TagUtility.toSentenceCase("one two i three", false));
        assertEquals("One two three", TagUtility.toSentenceCase("ONE two three", false));
        assertEquals("I one I two I three I", TagUtility.toSentenceCase("i one i two i three i", false));
        assertEquals("I one I two three I", TagUtility.toSentenceCase("i one i two THREE i", false));
        assertEquals("I one two I three I", TagUtility.toSentenceCase("i one two i three i", false));
        assertEquals("I one two three I", TagUtility.toSentenceCase("i ONE two three i", false));
        assertEquals("One I two I three I", TagUtility.toSentenceCase("one i two i three i", false));
        assertEquals("One I two three I", TagUtility.toSentenceCase("one i TWO three i", false));
        assertEquals("One two I three I", TagUtility.toSentenceCase("one TWO i THREE i", false));
        assertEquals("One two three I", TagUtility.toSentenceCase("one TWO three i", false));
        assertEquals("I one I two I THREE", TagUtility.toSentenceCase("i one i two i THREE", true));
        assertEquals("I ONE I TWO three", TagUtility.toSentenceCase("i ONE i TWO three", true));
        assertEquals("I ONE two I three", TagUtility.toSentenceCase("i ONE two i three", true));
        assertEquals("I one two three", TagUtility.toSentenceCase("i one two three", true));
        assertEquals("ONE I two I three", TagUtility.toSentenceCase("ONE i two i three", true));
        assertEquals("One I two THREE", TagUtility.toSentenceCase("one i two THREE", true));
        assertEquals("One two I THREE", TagUtility.toSentenceCase("one two i THREE", true));
        assertEquals("One two three", TagUtility.toSentenceCase("one two three", true));
        assertEquals("I one I TWO I three I", TagUtility.toSentenceCase("i one i TWO i three i", true));
        assertEquals("I one I two three I", TagUtility.toSentenceCase("i one i two three i", true));
        assertEquals("I one two I THREE I", TagUtility.toSentenceCase("i one two i THREE i", true));
        assertEquals("I one TWO three I", TagUtility.toSentenceCase("i one TWO three i", true));
        assertEquals("One I two I three I", TagUtility.toSentenceCase("one i two i three i", true));
        assertEquals("ONE I TWO three I", TagUtility.toSentenceCase("ONE i TWO three i", true));
        assertEquals("ONE two I THREE I", TagUtility.toSentenceCase("ONE two i THREE i", true));
        assertEquals("One two three I", TagUtility.toSentenceCase("one two three i", true));
    }

    /**
     *
     */
    public void testToTitleCase() {
        //test nulls
        try {
            TagUtility.toTitleCase(null, true);
            fail("TagUtility.toTitleCase(null, true): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.toTitleCase(null, false);
            fail("TagUtility.toTitleCase(null, false): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        assertEquals("", TagUtility.toTitleCase("", true));
        assertEquals("", TagUtility.toTitleCase("", false));
        assertEquals("The Quick Brown Fox Jumps Over the Lazy Dog",
                     TagUtility.toTitleCase("THE quick BROWN fox jumps over the lazy dog", false));
        assertEquals("Quick Brown Fox Jumps Over the Lazy Dog",
                     TagUtility.toTitleCase("quick brown fox jumps over the lazy DOG", false));
        assertEquals("The Quick Brown Fox Jumps Over The",
                     TagUtility.toTitleCase("the quick BROWN fox jumps over the", false));
        assertEquals("Quick Brown Fox Jumps Over The", TagUtility.toTitleCase("quick brown fox jumps over THE", false));
        assertEquals("The Quick Brown Fox Jumps Over the Lazy Dog",
                     TagUtility.toTitleCase("the quick brown fox jumps over the lazy dog", true));
        assertEquals("Quick Brown Fox Jumps Over the Lazy DOG",
                     TagUtility.toTitleCase("quick brown fox jumps over THE lazy DOG", true));
        assertEquals("THE Quick Brown Fox JUMPS Over The",
                     TagUtility.toTitleCase("THE quick brown fox JUMPS over the", true));
        assertEquals("Quick Brown Fox Jumps Over THE", TagUtility.toTitleCase("quick brown fox jumps over THE", true));
    }

    /**
     *
     */
    public void testTruncate() {
        //test nulls
        try {
            TagUtility.truncate(null, -1);
            fail("TagUtility.truncate(null, -1): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.truncate(null, 0);
            fail("TagUtility.truncate(null, 0): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.truncate(null, 100);
            fail("TagUtility.truncate(null, 100): NullPointerException not thrown");
        } catch (NullPointerException ex) {
            //correct behavior
        }
        try {
            TagUtility.truncate("", -1);
            fail("TagUtility.truncate(\"\", -1): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            //correct behavior
        }
        try {
            TagUtility.truncate("onetwo", -1);
            fail("TagUtility.truncate(\"onetwo\", -1): IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException ex) {
            //correct behavior
        }
        assertEquals("", TagUtility.truncate("", 0));
        assertEquals("", TagUtility.truncate("", 100));
        assertEquals("", TagUtility.truncate("onetwo", 0));
        assertEquals("o", TagUtility.truncate("onetwo", 1));
        assertEquals("onetw", TagUtility.truncate("onetwo", 5));
        assertEquals("onetwo", TagUtility.truncate("onetwo", 6));
        assertEquals("onetwo", TagUtility.truncate("onetwo", 7));
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
