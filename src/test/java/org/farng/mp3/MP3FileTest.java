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
import org.farng.mp3.filename.FilenameTag;
import org.farng.mp3.filename.FilenameTagBuilder;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.FrameBodyCOMM;
import org.farng.mp3.id3.FrameBodySYLT;
import org.farng.mp3.id3.FrameBodyTALB;
import org.farng.mp3.id3.FrameBodyTCOM;
import org.farng.mp3.id3.FrameBodyTCON;
import org.farng.mp3.id3.FrameBodyTDRL;
import org.farng.mp3.id3.FrameBodyTIT2;
import org.farng.mp3.id3.FrameBodyTPE1;
import org.farng.mp3.id3.FrameBodyTRCK;
import org.farng.mp3.id3.FrameBodyUSLT;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v1_1;
import org.farng.mp3.id3.ID3v2_4;
import org.farng.mp3.id3.ID3v2_4Frame;
import org.farng.mp3.lyrics3.AbstractLyrics3;
import org.farng.mp3.lyrics3.AbstractLyrics3v2FieldBody;
import org.farng.mp3.lyrics3.FieldBodyAUT;
import org.farng.mp3.lyrics3.FieldBodyEAL;
import org.farng.mp3.lyrics3.FieldBodyEAR;
import org.farng.mp3.lyrics3.FieldBodyETT;
import org.farng.mp3.lyrics3.FieldBodyINF;
import org.farng.mp3.lyrics3.FieldBodyLYR;
import org.farng.mp3.lyrics3.Lyrics3v2;
import org.farng.mp3.lyrics3.Lyrics3v2Field;

import java.io.File;
import java.io.FileFilter;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class MP3FileTest extends TestCase {

    /**  */
    private File testMusicDirectory = null;
    /**  */
    private File testMusicOriginalDirectory = null;
    /**  */
    private String[] testMusic = {"MM Jukebox Plus Upgrade.mp3",
                                  "Theme - Fat Albert.mp3",
                                  "Theme - HeMan.mp3",
                                  "Theme - Macguyver.mp3"};
    /**  */
    private MP3File[] testMusicArray;
    /**  */
    // ideal: int[] testMusicMP3StartByte = {4096, 0, 36, 0};
    // actual: int[] testMusicMP3StartByte = {4096, 0, 36, 182};
    private int[] testMusicMP3StartByte = {4096, 0, 36, 0};
    /**  */
    private String[] testMusicOriginal = {"MM Jukebox Plus Upgrade.original.mp3",
                                          "Theme - Fat Albert.original.mp3",
                                          "Theme - HeMan.original.mp3",
                                          "Theme - Macguyver.original.mp3"};
    /**  */
    private int paddingSize = TagOptionSingleton.getInstance().getId3v2PaddingSize();
    /**  */
    private int[] testMusicMP3StartByteNew = {4096, this.paddingSize, this.paddingSize, this.paddingSize};

    { /* @TODO */
    } // get/set pair

    /**
     * Creates a new MP3FileTest object.
     */
    public MP3FileTest(String name) {
        super(name);
        File parentDir = (new File("src")).getParentFile();
        this.testMusicDirectory = new File(parentDir, "working");
        this.testMusicOriginalDirectory = new File(parentDir, "src/test/resources/music");
    }

    /**
     * Creates a new MP3FileTest object.
     */
    public MP3FileTest() {
        // base empty constructor
    }

    public static Test suite() {
        return new TestSuite(MP3FileTest.class);
    }

    /**
     * @throws Exception
     */
    public void testAdjustID3v2Padding() throws Exception {
        File originalFile;
        long lastModified;
        for (int i = 0; i < this.testMusicArray.length; i++) {
            lastModified = this.testMusicArray[i].getMp3file().lastModified();
            this.testMusicArray[i].adjustID3v2Padding();
            if (this.testMusicArray[i].getMp3file().lastModified() != lastModified) {
                originalFile = new File(this.testMusicDirectory, this.testMusicOriginal[i]);
                assertTrue(originalFile.getName(), originalFile.exists());
            }
            assertEquals(this.testMusicArray[i].getMp3file().getName(),
                         this.testMusicMP3StartByteNew[i],
                         this.testMusicArray[i].getMp3StartByte());
        }
        MP3File mp3file = new MP3File(new File(this.testMusicDirectory, this.testMusic[1]));
        ID3v2_4 tag = new ID3v2_4();
        ID3v2_4Frame frame = new ID3v2_4Frame();
        FrameBodyTPE1 body = new FrameBodyTPE1((byte) 0, "text");

        //        // save and re-read this to set file/tag alter preservation tags to
        // correct
        //        // values;
        //        mp3file.save();
        //        mp3file = new MP3File(new File(testMusicDirectory, "Theme - Fat
        // Albert.mp3"));
        RandomAccessFile randomAccessFile;
        frame.setBody(body);
        tag.setFrame(frame);
        assertTrue(mp3file.adjustID3v2Padding(1, true, true));
        assertEquals(1, mp3file.getMp3StartByte());
        assertTrue(mp3file.adjustID3v2Padding(2, true, true));
        assertEquals(2, mp3file.getMp3StartByte());
        assertTrue(mp3file.adjustID3v2Padding(this.paddingSize, true, true));
        assertEquals(this.paddingSize, mp3file.getMp3StartByte());

        // save tag
        mp3file.setID3v2Tag(tag);
        mp3file.save();
        assertEquals(this.paddingSize, mp3file.getMp3StartByte());
        assertFalse(mp3file.adjustID3v2Padding(0, false, true));
        assertEquals(this.paddingSize, mp3file.getMp3StartByte());

        // compare new tag
        randomAccessFile = new RandomAccessFile(mp3file.getMp3file(), "r");
        ID3v2_4 tagNew = new ID3v2_4(randomAccessFile);
        assertEquals(tag, tagNew);
//        assertTrue(tagNew.equals(tag));
        assertFalse(mp3file.adjustID3v2Padding(1, false, true));
        assertEquals(this.paddingSize, mp3file.getMp3StartByte());

        // compare new tag
        randomAccessFile = new RandomAccessFile(mp3file.getMp3file(), "r");
        tagNew = new ID3v2_4(randomAccessFile);
        assertTrue(tagNew.equals(tag));
        assertTrue(mp3file.adjustID3v2Padding(1, true, true));
        assertEquals(32, mp3file.getMp3StartByte());

        // compare new tag
        randomAccessFile = new RandomAccessFile(mp3file.getMp3file(), "r");
        tagNew = new ID3v2_4(randomAccessFile);
        assertTrue(tagNew.equals(tag));
        assertTrue(mp3file.adjustID3v2Padding(this.paddingSize, true, false));
        assertEquals(this.paddingSize, mp3file.getMp3StartByte());

//        // compare new tag
//        randomAccessFile = new RandomAccessFile(mp3file.getMp3file(), "r");
//
//        try {
//            tagNew = new ID3v2_4(randomAccessFile);
//            assertFalse(tagNew.equals(tag));
//        } catch (TagNotFoundException ex) {
//            fail(ex.getMessage());
//        }
    }

    /**
     *
     */
    public void testDelete() {
        // this method only wraps and forwards
        // the request to AbstractTag.delete();
    }

    /**
     *
     */
    public void testGetBitRate() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetEmphasis() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetFilenameTag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetFrameAcrossTags() {
        MP3File mp3file = this.testMusicArray[0];
        AbstractID3v2 id3v2 = mp3file.getID3v2Tag();
        ID3v1 id3v1 = mp3file.getID3v1Tag();
        AbstractLyrics3 lyrics3 = mp3file.getLyrics3Tag();
        FilenameTag filename = mp3file.getFilenameTag();
        AbstractID3v2Frame frame = id3v2.getFrame("TIT2");
        assertEquals(2, mp3file.getFrameAcrossTags("TIT2").size());
        assertNull(lyrics3);
        assertTrue(filename != null);
        assertFalse(filename.hasFrame("TIT2"));
        lyrics3 = new Lyrics3v2();
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail(ex.getMessage());
        }
        mp3file.setLyrics3Tag(lyrics3);
        assertEquals(3, mp3file.getFrameAcrossTags("TIT2").size());
        filename.setFrame(frame);
        assertEquals(4, mp3file.getFrameAcrossTags("TIT2").size());
        mp3file.setFilenameTag(null);
        assertEquals(3, mp3file.getFrameAcrossTags("TIT2").size());
        mp3file.setID3v2Tag(null);
        assertEquals(2, mp3file.getFrameAcrossTags("TIT2").size());
        mp3file.setID3v1Tag(null);
        assertEquals(1, mp3file.getFrameAcrossTags("TIT2").size());
        mp3file.setLyrics3Tag(null);
        assertEquals(0, mp3file.getFrameAcrossTags("TIT2").size());

        // test nulls
        assertNull(mp3file.getFrameAcrossTags(null));
        assertNull(mp3file.getFrameAcrossTags(""));

        // test all different fields
        List frameList;
        final String testString = "Test String";
        mp3file = new MP3File();
        frame = new ID3v2_4Frame(new FrameBodyTIT2((byte) 0, testString));
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        id3v1.setTitle(testString);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);

        // different nulls
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(0, frameList.size());
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(1, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(1, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(1, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(1, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(2, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
        mp3file = new MP3File();
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TEST each frame
        // TIT1
        mp3file = new MP3File();
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TIT2");
        assertEquals(4, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TPE1
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTPE1((byte) 0, testString));
        id3v1.setArtist(testString);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TPE1");
        assertEquals(4, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TALB
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTALB((byte) 0, testString));
        id3v1.setAlbum(testString);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TALB");
        assertEquals(4, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TDRL
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTDRL((byte) 0, "1992"));
        id3v1.setYear("1992");
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
            fail("Somehow created a TDRL (year) Lyrics3v2 Field");
        } catch (TagException ex) {
            // correct behavior
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TDRL");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // COMM
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyCOMM((byte) 0, "ENG", "", testString));
        id3v1.setComment(testString);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("COMM");
        assertEquals(4, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TCON
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTCON((byte) 0, "(42) Soul"));
        id3v1.setGenre((byte) 42);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
            fail("Somehow created a TCON (genre) Lyrics3v2 Field");
        } catch (TagException ex) {
            // correct behavior
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TCON");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TRCK
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTRCK((byte) 0, "42"));
        ((ID3v1_1) id3v1).setTrack((byte) 42);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
            fail("Somehow created a TRCK (track) Lyrics3v2 Field");
        } catch (TagException ex) {
            // correct behavior
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TRCK");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // SYLT
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodySYLT((byte) 0, "ENG", (byte) 2, (byte) 1, null));
        ((FrameBodySYLT) frame.getBody()).addLyric(0, testString);
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("SYLT");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // USLT
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyUSLT((byte) 0, "ENG", "", testString));
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("USLT");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }

        // TCOM
        mp3file = new MP3File();
        id3v1 = new ID3v1_1();
        id3v2 = new ID3v2_4();
        lyrics3 = new Lyrics3v2();
        filename = FilenameTagBuilder.createEmptyFilenameTag();
        frame = new ID3v2_4Frame(new FrameBodyTCOM((byte) 0, testString));
        id3v2.setFrame(frame);
        try {
            ((Lyrics3v2) lyrics3).setField(new Lyrics3v2Field(frame));
        } catch (TagException ex) {
            fail("This should not happen. " + ex.getMessage());
        }
        filename.setFrame(frame);
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(filename);
        frameList = mp3file.getFrameAcrossTags("TCOM");
        assertEquals(3, frameList.size());
        for (int i = 0; i < frameList.size(); i++) {
            assertEquals(frame, frameList.get(i));
        }
    }

    /**
     *
     */
    public void testGetFrequency() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetID3v1Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetID3v2Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetLayer() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetLyrics3Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetMode() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetModeExtension() { /* @TODO */
    } // get/set pair

    /**
     * @throws Exception
     */
    public void testGetMp3StartByte() throws Exception {
        // @TODO eventually fix this
        // default is 5 frames. doesn't match what winamp2.81 returns
        TagOptionSingleton.getInstance().setNumberMP3SyncFrame(3);
        for (int i = 0; i < this.testMusicArray.length; i++) {
            assertEquals(this.testMusicArray[i].getMp3file().getName(),
                         this.testMusicMP3StartByte[i],
                         this.testMusicArray[i].getMp3StartByte());
        }
    }

    /**
     *
     */
    public void testGetMp3file() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testGetMpegVersion() { /* @TODO */
    } // get/set pair

    /**
     * @throws Exception
     */
    public void testGetUnsynchronizedFragments() throws Exception {
        MP3File mp3 = new MP3File();
        ID3v2_4 id3v2 = new ID3v2_4();
        ID3v1 id3v1 = new ID3v1();
        Lyrics3v2 lyrics3 = new Lyrics3v2();
        String albumTitle = "<album title>";
        mp3.setID3v2Tag(id3v2);
        mp3.setID3v1Tag(id3v1);
        mp3.setLyrics3Tag(lyrics3);
        mp3.setMp3file(new File(albumTitle));
        FilenameTag filename = FilenameTagBuilder.createFilenameTagFromMP3File(mp3);
        mp3.setFilenameTag(filename);
        AbstractID3v2Frame frame;
        AbstractID3v2FrameBody body;

        // test nulls;
        assertEquals(0, mp3.getUnsynchronizedFragments().size());

        // test equals
        body = new FrameBodyTALB((byte) 0, albumTitle);
        frame = new ID3v2_4Frame(body);
        id3v2.setFrame(frame);
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        lyrics3.setField(new Lyrics3v2Field(frame));
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        id3v1.setAlbum(albumTitle);
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        filename.setFrame(frame);
        assertEquals(0, mp3.getUnsynchronizedFragments().size());

        // test differences
        body = new FrameBodyTIT2((byte) 0, "<song title>");
        frame = new ID3v2_4Frame();
        frame.setBody(body);
        filename.setFrame(frame);
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        id3v1.setTitle("different");
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        id3v1.setTitle("<song title>");
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        lyrics3.setField(new Lyrics3v2Field(new FieldBodyETT("different")));
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        lyrics3.setField(new Lyrics3v2Field(new FieldBodyETT("<song title>")));
        assertEquals(0, mp3.getUnsynchronizedFragments().size());
        body = new FrameBodyTIT2((byte) 0, "different");
        frame = new ID3v2_4Frame(body);
        id3v2.setFrame(frame);
        assertEquals(1, mp3.getUnsynchronizedFragments().size());

        // test two differences
        id3v1.setArtist("<song artist>");
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        body = new FrameBodyTPE1((byte) 0, "different");
        frame = new ID3v2_4Frame();
        frame.setBody(body);
        filename.setFrame(frame);
        assertEquals(2, mp3.getUnsynchronizedFragments().size());
        body = new FrameBodyTPE1((byte) 0, "<song artist>");
        frame = new ID3v2_4Frame();
        frame.setBody(body);
        filename.setFrame(frame);
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        lyrics3.setField(new Lyrics3v2Field(new FieldBodyEAR("different")));
        assertEquals(2, mp3.getUnsynchronizedFragments().size());
        lyrics3.setField(new Lyrics3v2Field(new FieldBodyEAR("<song artist>")));
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        body = new FrameBodyTPE1((byte) 0, "different");
        frame = new ID3v2_4Frame(body);
        id3v2.setFrame(frame);
        assertEquals(2, mp3.getUnsynchronizedFragments().size());
        body = new FrameBodyTPE1((byte) 0, "<song artist>");
        frame = new ID3v2_4Frame(body);
        id3v2.setFrame(frame);
        assertEquals(1, mp3.getUnsynchronizedFragments().size());
        mp3.getMp3file().delete();
    }

    /**
     *
     */
    public void testHasFilenameTag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testHasID3v1Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testHasID3v2Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testHasLyrics3Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsCopyProtected() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsHome() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsPadding() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsPrivacy() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsProtection() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsUnsynchronized() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testIsVariableBitRate() { /* @TODO */
    }

    /**
     * @throws Exception
     */
    public void testSave() throws Exception {
        String albumTitle = "Theme";
        String newAlbumTitle = "New alb title";
        TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
        MP3File mp3file = this.testMusicArray[1];
        ID3v2_4 id3v2 = new ID3v2_4();
        ID3v2_4 filenameId3 = new ID3v2_4();
        ID3v1_1 id3v1 = new ID3v1_1();
        Lyrics3v2 lyrics3 = new Lyrics3v2();
        File renamedFile = new File(this.testMusicDirectory, newAlbumTitle + " - Fat Albert.mp3");
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());

        // setup id3v1
        id3v1.setAlbum(albumTitle);

        // setup id3v2
        AbstractID3v2Frame frame;
        AbstractID3v2FrameBody frameBody;
        frameBody = new FrameBodyTALB((byte) 0, albumTitle);
        frame = new ID3v2_4Frame(frameBody);
        id3v2.setFrame(frame);

        // setup lyrics3v2
        Lyrics3v2Field field;
        AbstractLyrics3v2FieldBody fieldBody;
        fieldBody = new FieldBodyEAL(albumTitle);
        field = new Lyrics3v2Field(fieldBody);
        lyrics3.setField(field);

        // setup filename tag
        frameBody = new FrameBodyTALB((byte) 0, albumTitle);
        frame = new ID3v2_4Frame(frameBody);
        filenameId3.setFrame(frame);
        TagOptionSingleton.getInstance().setFilenameTagSave(true);

        // nulls
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());

        // save one at a time
        // id3v1
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v2
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertNull(mp3file.getID3v1Tag());
        assertEquals(id3v2, mp3file.getID3v2Tag());
//        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // lyrics3
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // filename tag
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());

        // save two at a time
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & id3v2
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & lyrics3
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertNull(mp3file.getID3v2Tag());
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & filename
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v2 & lyrics3
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertNull(mp3file.getID3v1Tag());
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v2 & filename
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertNull(mp3file.getID3v1Tag());
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // lyrics3 & filename
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        assertTrue(renamedFile.delete());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & id3v2 & lyrics3
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.setFilenameTag(null);
        mp3file.save();
        mp3file = new MP3File(mp3file.getMp3file());
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(1, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & id3v2 & filename
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(null);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertNull(mp3file.getLyrics3Tag());
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & lyrics3 & filename
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertNull(mp3file.getID3v2Tag());
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v2 & lyrics3 & filename
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertNull(mp3file.getID3v1Tag());
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
        tearDown();
        setUp();
        mp3file = this.testMusicArray[1];
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(albumTitle);

        // id3v1 & id3v2 & lyrics3 & filename
        mp3file.setID3v1Tag(id3v1);
        mp3file.setID3v2Tag(id3v2);
        mp3file.setLyrics3Tag(lyrics3);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        ((FrameBodyTALB) filenameId3.getFrame("TALB").getBody()).setText(newAlbumTitle);
        mp3file.getFilenameTag().setId3tag(filenameId3);
        try {
            mp3file.save();
        } catch (TagException ex) {
            if ((ex.getMessage().startsWith("Unable to delete original file:") == false) &&
                (ex.getMessage().startsWith("Unable to rename file:") == false)) {
                throw ex;
            }

            // handle this exception for now.
            // @todo fix this
        }
        assertTrue(renamedFile.exists());
        mp3file = new MP3File(renamedFile);
        assertTrue(mp3file.getID3v1Tag().equals(id3v1));
        assertTrue(mp3file.getID3v2Tag().equals(id3v2));
        assertTrue(mp3file.getLyrics3Tag().equals(lyrics3));
        assertEquals(0, (mp3file.getFilenameTag().getId3tag()).getFrameCount());
    }

    /**
     * @throws Exception
     */
    public void testSeekMP3Frame() throws Exception {
        // @TODO eventually fix this
        // default is 5 frames. doesn't match what winamp2.81 returns
        TagOptionSingleton.getInstance().setNumberMP3SyncFrame(3);
        for (int i = 0; i < this.testMusicArray.length; i++) {
            assertEquals(this.testMusicArray[i].getMp3file().getName(),
                         this.testMusicMP3StartByte[i],
                         this.testMusicArray[i].getMp3StartByte());
        }
    }

    /**
     *
     */
    public void testSetFilenameTag() { /* @TODO */
    } // get/set pair

    /**
     * @throws Exception
     */
    public void testSetFrameAcrossTags() throws Exception {
        MP3File mp3file = new MP3File();
        String testString = "Test String";
        AbstractID3v2Frame frame;
        AbstractID3v2FrameBody frameBody;
        frameBody = new FrameBodyTALB((byte) 0, testString);
        frame = new ID3v2_4Frame(frameBody);

        // nulls
        mp3file.setFrameAcrossTags(null);
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        assertNull(mp3file.getLyrics3Tag());
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        assertNull(mp3file.getLyrics3Tag());
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        assertNull(mp3file.getID3v2Tag());
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        assertNull(mp3file.getID3v2Tag());
        assertNull(mp3file.getLyrics3Tag());
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        assertNull(mp3file.getLyrics3Tag());
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        assertNull(mp3file.getID3v2Tag());
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(null);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        assertNull(mp3file.getFilenameTag());
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(null);
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        assertNull(mp3file.getLyrics3Tag());
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(null);
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        assertNull(mp3file.getID3v2Tag());
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(null);
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        mp3file.setFrameAcrossTags(frame);
        assertNull(mp3file.getID3v1Tag());
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);

        // test all
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTIT2();
        ((FrameBodyTIT2) frameBody).setText(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getTitle().equals(testString);
        ((FrameBodyTIT2) mp3file.getID3v2Tag().getFrame("TIT2").getBody()).getText().equals(testString);
        ((FieldBodyETT) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("ETT").getBody()).getTitle().equals(testString);
        ((FrameBodyTIT2) mp3file.getFilenameTag().getFrame("TIT2").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTPE1();
        ((FrameBodyTPE1) frameBody).setText(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getArtist().equals(testString);
        ((FrameBodyTPE1) mp3file.getID3v2Tag().getFrame("TPE1").getBody()).getText().equals(testString);
        ((FieldBodyEAR) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAR").getBody()).getArtist().equals(testString);
        ((FrameBodyTPE1) mp3file.getFilenameTag().getFrame("TPE1").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTALB();
        ((FrameBodyTALB) frameBody).setText(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getID3v2Tag().getFrame("TALB").getBody()).getText().equals(testString);
        ((FieldBodyEAL) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("EAL").getBody()).getAlbum().equals(testString);
        ((FrameBodyTALB) mp3file.getFilenameTag().getFrame("TALB").getBody()).getText().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTDRL();
        ((FrameBodyTDRL) frameBody).setText("2003");
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getYear().equals("2003");
        ((FrameBodyTDRL) mp3file.getID3v2Tag().getFrame("TDRL").getBody()).getText().equals("2003");
        assertEquals(0, ((Lyrics3v2) mp3file.getLyrics3Tag()).getFieldCount());
        ((FrameBodyTDRL) mp3file.getFilenameTag().getFrame("TDRL").getBody()).getText().equals("2003");
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyCOMM();
        ((FrameBodyCOMM) frameBody).setText(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getComment().equals(testString);
        ((FrameBodyCOMM) ((ID3v2_4Frame) mp3file.getID3v2Tag().getFrameOfType("COMM").next()).getBody()).getText()
                .equals(testString);
        ((FieldBodyINF) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("INF").getBody()).getAdditionalInformation()
                .equals(testString);
        ((FrameBodyCOMM) ((ID3v2_4Frame) mp3file.getFilenameTag().getFrameOfType("COMM").next()).getBody()).getText()
                .equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTCON();
        ((FrameBodyTCON) frameBody).setText("42");
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        assertEquals(42, mp3file.getID3v1Tag().getGenre());
        ((FrameBodyTCON) mp3file.getID3v2Tag().getFrame("TCON").getBody()).getText().equals("42");
        assertEquals(0, ((Lyrics3v2) mp3file.getLyrics3Tag()).getFieldCount());
        ((FrameBodyTCON) mp3file.getFilenameTag().getFrame("TCON").getBody()).getText().equals("42");
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTRCK();
        ((FrameBodyTRCK) frameBody).setText("42");
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        assertEquals(42, ((ID3v1_1) mp3file.getID3v1Tag()).getTrack());
        ((FrameBodyTRCK) mp3file.getID3v2Tag().getFrame("TRCK").getBody()).getText().equals("42");
        assertEquals(0, ((Lyrics3v2) mp3file.getLyrics3Tag()).getFieldCount());
        ((FrameBodyTRCK) mp3file.getFilenameTag().getFrame("TRCK").getBody()).getText().equals("42");
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodySYLT();
        ((FrameBodySYLT) frameBody).addLyric(0, testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        assertEquals(0, (new ID3v2_4(mp3file.getID3v1Tag())).getFrameCount());
        ((FrameBodySYLT) mp3file.getID3v2Tag().getFrame("SYLT").getBody()).getLyric().equals(testString);
        ((FieldBodyLYR) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("LYR").getBody()).getLyric().equals(testString);
        ((FrameBodySYLT) mp3file.getFilenameTag().getFrame("SYLT").getBody()).getLyric().equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyUSLT();
        ((FrameBodyUSLT) frameBody).setLyric(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        assertEquals(0, (new ID3v2_4(mp3file.getID3v1Tag())).getFrameCount());
        ((FrameBodyUSLT) ((ID3v2_4Frame) mp3file.getID3v2Tag().getFrameOfType("USLT").next()).getBody()).getLyric()
                .equals(testString);
        ((FieldBodyLYR) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("LYR").getBody()).getLyric().equals(testString);
        ((FrameBodyUSLT) ((ID3v2_4Frame) mp3file.getFilenameTag().getFrameOfType("USLT").next()).getBody()).getLyric()
                .equals(testString);
        mp3file.setID3v1Tag(new ID3v1_1());
        mp3file.setID3v2Tag(new ID3v2_4());
        mp3file.setLyrics3Tag(new Lyrics3v2());
        mp3file.setFilenameTag(FilenameTagBuilder.createEmptyFilenameTag());
        frameBody = new FrameBodyTCOM();
        ((FrameBodyTCOM) frameBody).setText(testString);
        frame.setBody(frameBody);
        mp3file.setFrameAcrossTags(frame);
        mp3file.getID3v1Tag().getComment().equals(testString);
        ((FrameBodyTCOM) mp3file.getID3v2Tag().getFrame("TCOM").getBody()).getText().equals(testString);
        ((FieldBodyAUT) ((Lyrics3v2) mp3file.getLyrics3Tag()).getField("AUT").getBody()).getAuthor().equals(testString);
        ((FrameBodyTCOM) mp3file.getFilenameTag().getFrame("TCOM").getBody()).getText().equals(testString);
    }

    /**
     *
     */
    public void testSetID3v1Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testSetID3v2Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testSetLyrics3Tag() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testSetMp3file() { /* @TODO */
    } // get/set pair

    /**
     *
     */
    public void testSetVariableBitRate() { /* @TODO */
    } // get/set pair

    /**
     * @throws Exception
     */
    protected void setUp() throws Exception {
        // copy over original files
        FileFilter fileFilter = new FileOnlyFileFilter();
        File[] testFileArray = this.testMusicDirectory.listFiles(fileFilter);
        File[] originalFileArray = this.testMusicOriginalDirectory.listFiles(fileFilter);
        File newFile;
        if (testFileArray != null) {
            for (int i = 0; i < testFileArray.length; i++) {
                testFileArray[i].delete();
            }
        }
        this.testMusicDirectory.mkdirs();
        for (int i = 0; i < originalFileArray.length; i++) {
            newFile = new File(this.testMusicDirectory, originalFileArray[i].getName());
            try {
                TagUtility.copyFile(originalFileArray[i], newFile);
            } catch (Exception ex) {
                System.out.println("Error copying original music file: " + originalFileArray[i].getAbsolutePath());
                ex.printStackTrace();
            }
        }

        // load the music files
        this.testMusicArray = new MP3File[this.testMusic.length];
        for (int i = 0; i < this.testMusic.length; i++) {
            this.testMusicArray[i] = new MP3File(new File(this.testMusicDirectory, this.testMusic[i]));
        }
    }

    /**
     *
     */
    protected void tearDown() {
        FileFilter fileFilter = new FileOnlyFileFilter();
        File[] testFileArray = this.testMusicDirectory.listFiles(fileFilter);
        if (testFileArray != null) {
            for (int i = 0; i < testFileArray.length; i++) {
                testFileArray[i].delete();
            }
        }
        this.testMusicDirectory.delete();
    }
}
