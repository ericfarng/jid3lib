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
import org.farng.mp3.id3.FrameBodyWCOP;
import org.farng.mp3.id3.ID3v2_4;
import org.farng.mp3.id3.ID3v2_4Frame;
import org.farng.mp3.object.ObjectLyrics3ImageTest;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class MiscellaneousTest extends TestCase {

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

    /**
     *
     */

    // ideal: int[] testMusicMP3StartByte = {4096, 0, 36, 0};
    // actual: int[] testMusicMP3StartByte = {4096, 0, 36, 182};
    //    private int[] testMusicMP3StartByte = {4096, 0, 36, 0};

    /**
     *
     */

    //    private String[] testMusicOriginal = {
    //        "MM Jukebox Plus Upgrade.original.mp3",
    //        "Theme - Fat Albert.original.mp3",
    //        "Theme - HeMan.original.mp3",
    //        "Theme - Macguyver.original.mp3"
    //    };

    /**
     *
     */
    public MiscellaneousTest() {
        super();
        File parentDir = (new File("test")).getParentFile();
        this.testMusicDirectory = new File(parentDir, "working");
        this.testMusicOriginalDirectory = new File(parentDir, "music");
    }

    /**
     * @param name
     */
    public MiscellaneousTest(String name) {
        super(name);
        File parentDir = (new File("test")).getParentFile();
        this.testMusicDirectory = new File(parentDir, "working");
        this.testMusicOriginalDirectory = new File(parentDir, "music");
    }

    /**
     * @param args
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        (new MiscellaneousTest()).testJohnMartinWCOP();
    }

    public static Test suite() {
        return new TestSuite(ObjectLyrics3ImageTest.class);
    }

    /**
     * @throws Exception
     */
    public void testJohnMartinWCOP() throws Exception {
        setUp();
        File testFile = this.testMusicArray[0].getMp3file();
        MP3File mp3file = this.testMusicArray[0];
        ID3v2_4 tag = null;
        ID3v2_4Frame wcopFrame = null;
        FrameBodyWCOP wcopFrameBody = null;
        tag = new ID3v2_4(mp3file.getID3v2Tag());
        System.err.println("The tag is: " + tag);
        wcopFrame = new ID3v2_4Frame(tag.getFrame("WCOP"));
        wcopFrameBody = new FrameBodyWCOP("http://creativecommons.org");
        wcopFrameBody.setUrlLink("http://creativecommons.org");
        wcopFrame.setBody(wcopFrameBody);
        System.err.println("The wcop frame is: " + wcopFrame.toString());
        tag.setFrame(wcopFrame);
        mp3file.setID3v2Tag(tag);
        System.err.println("The WCOP frame contains: " + ((FrameBodyWCOP) wcopFrame.getBody()).getUrlLink());
        mp3file.save(testFile.toString(), TagConstant.MP3_FILE_SAVE_OVERWRITE);
        mp3file.save();
        mp3file = new MP3File(testFile);
        tag = new ID3v2_4(mp3file.getID3v2Tag());
        System.err.println("The tag is: " + tag);

        // run again
        wcopFrame = new ID3v2_4Frame(tag.getFrame("WCOP"));
        wcopFrameBody = new FrameBodyWCOP("http://creativecommons.org");
        wcopFrameBody.setUrlLink("http://creativecommons.org");
        wcopFrame.setBody(wcopFrameBody);
        System.err.println("The wcop frame is: " + wcopFrame.toString());
        tag.setFrame(wcopFrame);
        mp3file.setID3v2Tag(tag);
        System.err.println("The WCOP frame contains: " + ((FrameBodyWCOP) wcopFrame.getBody()).getUrlLink());
        mp3file.save(testFile.toString(), TagConstant.MP3_FILE_SAVE_OVERWRITE);
        mp3file.save();
        mp3file = new MP3File(testFile);
        tag = new ID3v2_4(mp3file.getID3v2Tag());
        System.err.println("The tag is: " + tag);
        tearDown();
    }

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
                System.err.println("Error copying original music file: " + originalFileArray[i].getAbsolutePath());
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
