package org.farng.mp3.lyrics3;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.farng.mp3.FileOnlyFileFilter;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;

/**
 * Created by ericfarng on 6/1/17.
 */
public class Lyrics3Test extends TestCase {

    private File testMusicDirectory = null;
    private File testMusicOriginalDirectory = null;
    private String[] testMusic = {"MM Jukebox Plus Upgrade.mp3",
                                  "Theme - Fat Albert.mp3",
                                  "Theme - HeMan.mp3",
                                  "Theme - Macguyver.mp3"};
    /**  */
    private MP3File[] testMusicArray;

    public Lyrics3Test() {
        super();
        setupDirectory();
    }

    public Lyrics3Test(String name) {
        super(name);
        setupDirectory();
    }

    private void setupDirectory() {
        File parentDir = (new File("test")).getParentFile();
        this.testMusicDirectory = new File(parentDir, "working");
        this.testMusicDirectory.mkdirs();
        this.testMusicOriginalDirectory = new File(parentDir, "src/test/resources/music");
    }

    public static Test suite() {
        return new TestSuite(Lyrics3Test.class);
    }


    public void testSavingWithNoChanges() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);
        mp3File.save(newFilename);
        Assert.assertTrue(TagUtility.compareTwoFiles(mp3File.getMp3file(), newFilename));
    }

    public void testLyrics3v1() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);

        Lyrics3v1 lyrics3v1 = new Lyrics3v1();
        mp3File.setLyrics3Tag(lyrics3v1);
        lyrics3v1.setSongLyric("Lyrics3v1 song lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        mp3File.save(newFilename);

        MP3File newMp3File = new MP3File(newFilename);

        Assert.assertEquals(mp3File.getLyrics3Tag(), newMp3File.getLyrics3Tag());

        File lastFilename = new File(this.testMusicDirectory, "exactCopy2_" + filename);
        TagUtility.copyFile(newFilename, lastFilename);
        newMp3File.save(lastFilename);
        Assert.assertTrue(TagUtility.compareTwoFiles(newFilename, lastFilename));

        newMp3File.save();
        Assert.assertTrue(TagUtility.compareTwoFiles(newFilename, lastFilename));

        newMp3File.setLyrics3Tag(null);
        newMp3File.save(TagConstant.MP3_FILE_SAVE_OVERWRITE);
        Assert.assertTrue(TagUtility.compareTwoFiles(mp3File.getMp3file(), newFilename));
    }


    public void testLyrics3v2() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);

        Lyrics3v2 lyrics3v2;

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setAuthorComposer("Lyrics3v2 Author Composer");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setAlbumTitle("Lyrics3v2 Album Title");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setLeadArtist("Lyrics3v2 Lead Artist");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setSongTitle("Lyrics3v2 Song Title");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setSongComment("Lyrics3v2 Song Comment");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setSongLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit.", null, null);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric 22 Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 11, 13);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit.", null, null);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric 22 Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 11, 13);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric 22 Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 11, 13);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit.", null, null);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setSongLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addImage("Lyrics3v2 Image File name", "Lyrics3v2 image description", 10, 12);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addImage("Lyrics3v2 Image File name",null, null, null);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addImage("Lyrics3v2 Image File name1", "Lyrics3v2 image description", 10, 12);
        lyrics3v2.addImage("Lyrics3v2 Image File name2",null, null, null);
        saveLyrics3v2Test(mp3File, newFilename);

        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.addImage("Lyrics3v2 Image File name2",null, null, null);
        lyrics3v2.addImage("Lyrics3v2 Image File name1", "Lyrics3v2 image description", 10, 12);
        saveLyrics3v2Test(mp3File, newFilename);


        lyrics3v2 = new Lyrics3v2();
        mp3File.setLyrics3Tag(lyrics3v2);
        lyrics3v2.setAuthorComposer("Lyrics3v2 Author Composer");
        lyrics3v2.setAlbumTitle("Lyrics3v2 Album Title");
        lyrics3v2.setLeadArtist("Lyrics3v2 Lead Artist");
        lyrics3v2.setSongTitle("Lyrics3v2 Song Title");
        lyrics3v2.setSongTitle("Lyrics3v2 Song Title");
        lyrics3v2.setSongComment("Lyrics3v2 Song Comment");
        lyrics3v2.addImage("Lyrics3v2 Image File name1", "Lyrics3v2 image description", 10, 12);
        lyrics3v2.addImage("Lyrics3v2 Image File name2",null, null, null);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric 22 Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 11, 13);
        lyrics3v2.addLyric("Lyrics3v2 Song Lyric Lorem ipsum dolor sit amet, consectetur adipiscing elit.", null, null);
        saveLyrics3v2Test(mp3File, newFilename);


    }

    private void saveLyrics3v2Test(MP3File mp3File, File newFilename) throws IOException,
                                                                             TagException {
        // test saving to new file and re-reading back tag and checking java objects
        mp3File.save(newFilename);
        MP3File newMp3File = new MP3File(newFilename);
        Assert.assertEquals(mp3File.getLyrics3Tag(), newMp3File.getLyrics3Tag());

        // use re-reading tag and saving to to third file and checking file bytes
        String filename = mp3File.getMp3file().getName();
        File lastFilename = new File(this.testMusicDirectory, "exactCopy2_" + filename);
        TagUtility.copyFile(newFilename, lastFilename);
        newMp3File.save(lastFilename);
        Assert.assertTrue(TagUtility.compareTwoFiles(newFilename, lastFilename));

        // save to second file and checking file bytes with third file
        newMp3File.save();
        Assert.assertTrue(TagUtility.compareTwoFiles(newFilename, lastFilename));

        // remove tag, save and check bytes with original file wit no tag.
        newMp3File.setLyrics3Tag(null);
        newMp3File.save(TagConstant.MP3_FILE_SAVE_OVERWRITE);
        Assert.assertTrue(TagUtility.compareTwoFiles(mp3File.getMp3file(), newFilename));
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
//        FileFilter fileFilter = new FileOnlyFileFilter();
//        File[] testFileArray = this.testMusicDirectory.listFiles(fileFilter);
//        if (testFileArray != null) {
//            for (int i = 0; i < testFileArray.length; i++) {
//                testFileArray[i].delete();
//            }
//        }
//        this.testMusicDirectory.delete();
    }

}
