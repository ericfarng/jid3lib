package org.farng.mp3.id3;

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
import org.farng.mp3.TagUtility;
import org.farng.mp3.lyrics3.Lyrics3v2;

/**
 * Created by ericfarng on 6/1/17.
 */
public class ID3v1Test extends TestCase {

    private File testMusicDirectory = null;
    private File testMusicOriginalDirectory = null;
    private String[] testMusic = {"MM Jukebox Plus Upgrade.mp3",
                                  "Theme - Fat Albert.mp3",
                                  "Theme - HeMan.mp3",
                                  "Theme - Macguyver.mp3"};
    /**  */
    private MP3File[] testMusicArray;

    public ID3v1Test() {
        super();
        setupDirectory();
    }

    public ID3v1Test(String name) {
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
        return new TestSuite(ID3v1Test.class);
    }


    public void testSavingWithNoChanges() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);
        mp3File.save(newFilename);
        Assert.assertTrue(TagUtility.compareTwoFiles(mp3File.getMp3file(), newFilename));
    }

    public void testID3v1() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);

        ID3v1 id3v1;

        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setAlbumTitle("ID3v1 Album Title");
        saveTest(mp3File, newFilename);

        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setLeadArtist("ID3v1 Lead Artist");
        saveTest(mp3File, newFilename);

        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setSongTitle("ID3v1 Song Title");
        saveTest(mp3File, newFilename);

        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setSongComment("ID3v1 Song Comment");
        saveTest(mp3File, newFilename);

        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setYear("ID31");
        saveTest(mp3File, newFilename);


        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setGenre((byte) 02);
        saveTest(mp3File, newFilename);


        id3v1 = new ID3v1();
        mp3File.setID3v1Tag(id3v1);
        id3v1.setAlbumTitle("ID3v1 Album Title");
        id3v1.setLeadArtist("ID3v1 Lead Artist");
        id3v1.setSongTitle("ID3v1 Song Title");
        id3v1.setSongComment("ID3v1 Song Comment");
        id3v1.setYear("ID31");
        id3v1.setGenre((byte) 02);
        saveTest(mp3File, newFilename);


    }

    public void testID3v1_1() throws Exception {
        MP3File mp3File = this.testMusicArray[1];

        String filename = mp3File.getMp3file().getName();

        File newFilename = new File(this.testMusicDirectory, "exactCopy_" + filename);
        TagUtility.copyFile(mp3File.getMp3file(), newFilename);

        ID3v1_1 id3v1_1;

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setAlbumTitle("ID3v1_1 Album Title");
        saveTest(mp3File, newFilename);

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setLeadArtist("ID3v1_1 Lead Artist");
        saveTest(mp3File, newFilename);

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setSongTitle("ID3v1_1 Song Title");
        saveTest(mp3File, newFilename);

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setSongComment("ID3v1_1 Song Comment");
        saveTest(mp3File, newFilename);

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setYear("ID31");
        saveTest(mp3File, newFilename);


        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setGenre((byte) 02);
        saveTest(mp3File, newFilename);

        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setTrack((byte) 03);
        saveTest(mp3File, newFilename);


        id3v1_1 = new ID3v1_1();
        mp3File.setID3v1Tag(id3v1_1);
        id3v1_1.setAlbumTitle("ID3v1_1 Album Title");
        id3v1_1.setLeadArtist("ID3v1_1 Lead Artist");
        id3v1_1.setSongTitle("ID3v1_1 Song Title");
        id3v1_1.setSongComment("ID3v1_1 Song Comment");
        id3v1_1.setYear("ID31");
        id3v1_1.setGenre((byte) 02);
        id3v1_1.setTrack((byte) 03);
        saveTest(mp3File, newFilename);


    }

    private void saveTest(MP3File mp3File, File newFilename) throws IOException,
                                                                    TagException {
        // test saving to new file and re-reading back tag and checking java objects
        mp3File.save(newFilename);
        MP3File newMp3File = new MP3File(newFilename);
        Assert.assertEquals(mp3File.getID3v1Tag(), newMp3File.getID3v1Tag());

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
        newMp3File.setID3v1Tag(null);
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
