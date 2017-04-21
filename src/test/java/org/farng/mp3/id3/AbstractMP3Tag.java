package org.farng.mp3.id3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.farng.mp3.lyrics3.Lyrics3v1;
import org.farng.mp3.lyrics3.Lyrics3v2;

/**
 * Created by IntelliJ IDEA. User: Eric Farng Date: Mar 12, 2006 Time: 7:20:28 PM To change this template use File |
 * Settings | File Templates.
 */
public class AbstractMP3Tag extends TestCase {

    private String songTitle = "My Song Title";
    private String leadArtist = "My Lead Artist";
    private String albumTitle = "My Album Title";
    private String yearReleased = "1990";
    private String songComment = "My Comment";
    private String songGenre = "31";
    private String trackNumberOnAlbum = "3";
    private String songLyric = "My Song Lyrics";
    private String authorComposer = "My Author / Composer";

    /**
     *
     */
    public AbstractMP3Tag() {
        super();
    }

    /**
     * @param arg0
     */
    public AbstractMP3Tag(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new TestSuite(AbstractMP3Tag.class);
    }

    /**
     *
     */
    protected void setUp() {
        songTitle = "My Song Title";
        leadArtist = "My Lead Artist";
        albumTitle = "My Album Title";
        yearReleased = "1990";
        songComment = "My Comment";
        songGenre = "31";
        trackNumberOnAlbum = "3";
        songLyric = "My Song Lyrics";
        authorComposer = "My Author or Composer";
    }

    /**
     *
     */
    protected void tearDown() {
        // none required
    }

    public void testID3v1() {
        ID3v1 tag = new ID3v1();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        assertEquals("", tag.getYearReleased());
        assertEquals("", tag.getSongComment());
        assertEquals("-1", tag.getSongGenre());
        try {
            tag.getTrackNumberOnAlbum();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getSongLyric();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getAuthorComposer();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag = new ID3v1();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        try {
            tag.setTrackNumberOnAlbum(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setSongLyric(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setAuthorComposer(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        ID3v1 newTag = new ID3v1(convertTag);
        assertEquals(tag, newTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getYearReleased(), tag.getYearReleased());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongGenre(), tag.getSongGenre());
        ID3v1 copyTag = new ID3v1(tag);
        assertEquals(copyTag, tag);
    }

    public void testID3v1_1() {
        ID3v1_1 tag = new ID3v1_1();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        assertEquals("", tag.getYearReleased());
        assertEquals("", tag.getSongComment());
        assertEquals("-1", tag.getSongGenre());
        assertEquals("-1", tag.getTrackNumberOnAlbum());
        try {
            tag.getSongLyric();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getAuthorComposer();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag = new ID3v1_1();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        try {
            tag.setSongLyric(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setAuthorComposer(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        ID3v1_1 newTag = new ID3v1_1(convertTag);
        assertEquals(tag, newTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getYearReleased(), tag.getYearReleased());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongGenre(), tag.getSongGenre());
        assertEquals(newTag.getTrackNumberOnAlbum(), tag.getTrackNumberOnAlbum());
        ID3v1_1 copyTag = new ID3v1_1(tag);
        assertEquals(copyTag, tag);
    }

    public void testLyrics3v1() {
        Lyrics3v1 tag = new Lyrics3v1();
        try {
            tag.getSongTitle();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getLeadArtist();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getAlbumTitle();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getYearReleased();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getSongComment();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getSongGenre();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getTrackNumberOnAlbum();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        assertEquals("", tag.getSongLyric());
        try {
            tag.getAuthorComposer();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag = new Lyrics3v1();
        try {
            tag.setSongTitle(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setLeadArtist(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setAlbumTitle(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setYearReleased(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setSongComment(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setSongGenre(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setTrackNumberOnAlbum(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag.setSongLyric(songLyric);
        try {
            tag.setAuthorComposer(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        assertEquals(songLyric, tag.getSongLyric());

        // run again because it's a different case
        tag.setSongLyric(songLyric);
        assertEquals(songLyric, tag.getSongLyric());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        Lyrics3v1 newTag = new Lyrics3v1(convertTag);
        assertEquals(tag, newTag);
        assertEquals(newTag.getSongLyric(), tag.getSongLyric());
        Lyrics3v1 copyTag = new Lyrics3v1(tag);
        assertEquals(copyTag, tag);
    }

    public void testLyrics3v2() {
        Lyrics3v2 tag = new Lyrics3v2();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        try {
            tag.getYearReleased();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getSongGenre();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.getTrackNumberOnAlbum();
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        assertEquals("", tag.getSongLyric());
        assertEquals("", tag.getAuthorComposer());
        tag = new Lyrics3v2();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        try {
            tag.setYearReleased(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag.setSongComment(songComment);
        try {
            tag.setSongGenre(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        try {
            tag.setTrackNumberOnAlbum(null);
            fail("Did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException ex) {
        }
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setSongComment(songComment);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        Lyrics3v2 newTag = new Lyrics3v2(convertTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongLyric(), tag.getSongLyric());
        assertEquals(newTag.getAuthorComposer(), tag.getAuthorComposer());
        assertEquals(tag, newTag);
        Lyrics3v2 copyTag = new Lyrics3v2(tag);
        assertEquals(copyTag, tag);
    }

    public void testID3v2_2() {
        ID3v2_2 tag = new ID3v2_2();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        assertEquals("", tag.getYearReleased());
        assertEquals("", tag.getSongComment());
        assertEquals("", tag.getSongGenre());
        assertEquals("", tag.getTrackNumberOnAlbum());
        assertEquals("", tag.getSongLyric());
        assertEquals("", tag.getAuthorComposer());
        tag = new ID3v2_2();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        ID3v2_2 newTag = new ID3v2_2(convertTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getYearReleased(), tag.getYearReleased());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongGenre(), tag.getSongGenre());
        assertEquals(newTag.getTrackNumberOnAlbum(), tag.getTrackNumberOnAlbum());
        assertEquals(newTag.getSongLyric(), tag.getSongLyric());
        assertEquals(newTag.getAuthorComposer(), tag.getAuthorComposer());
        assertEquals(tag, newTag);
        ID3v2_2 copyTag = new ID3v2_2(tag);
        assertEquals(copyTag, tag);
    }

    public void testID3v2_3() {
        ID3v2_3 tag = new ID3v2_3();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        assertEquals("", tag.getYearReleased());
        assertEquals("", tag.getSongComment());
        assertEquals("", tag.getSongGenre());
        assertEquals("", tag.getTrackNumberOnAlbum());
        assertEquals("", tag.getSongLyric());
        assertEquals("", tag.getAuthorComposer());
        tag = new ID3v2_3();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        ID3v2_3 newTag = new ID3v2_3(convertTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getYearReleased(), tag.getYearReleased());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongGenre(), tag.getSongGenre());
        assertEquals(newTag.getTrackNumberOnAlbum(), tag.getTrackNumberOnAlbum());
        assertEquals(newTag.getSongLyric(), tag.getSongLyric());
        assertEquals(newTag.getAuthorComposer(), tag.getAuthorComposer());
        assertEquals(tag, newTag);
        ID3v2_3 copyTag = new ID3v2_3(tag);
        assertEquals(copyTag, tag);
    }

    public void testID3v2_4() {
        ID3v2_4 tag = new ID3v2_4();
        assertEquals("", tag.getSongTitle());
        assertEquals("", tag.getLeadArtist());
        assertEquals("", tag.getAlbumTitle());
        assertEquals("", tag.getYearReleased());
        assertEquals("", tag.getSongComment());
        assertEquals("", tag.getSongGenre());
        assertEquals("", tag.getTrackNumberOnAlbum());
        assertEquals("", tag.getSongLyric());
        assertEquals("", tag.getAuthorComposer());
        tag = new ID3v2_4();
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());

        // run again because it's a different case
        tag.setSongTitle(songTitle);
        tag.setLeadArtist(leadArtist);
        tag.setAlbumTitle(albumTitle);
        tag.setYearReleased(yearReleased);
        tag.setSongComment(songComment);
        tag.setSongGenre(songGenre);
        tag.setTrackNumberOnAlbum(trackNumberOnAlbum);
        tag.setSongLyric(songLyric);
        tag.setAuthorComposer(authorComposer);
        assertEquals(songTitle, tag.getSongTitle());
        assertEquals(leadArtist, tag.getLeadArtist());
        assertEquals(albumTitle, tag.getAlbumTitle());
        assertEquals(yearReleased, tag.getYearReleased());
        assertEquals(songComment, tag.getSongComment());
        assertEquals(songGenre, tag.getSongGenre());
        assertEquals(trackNumberOnAlbum, tag.getTrackNumberOnAlbum());
        assertEquals(songLyric, tag.getSongLyric());
        assertEquals(authorComposer, tag.getAuthorComposer());
        ID3v2_4 convertTag = new ID3v2_4(tag);
        ID3v2_4 newTag = new ID3v2_4(convertTag);
        assertEquals(tag, newTag);
        assertEquals(newTag.getSongTitle(), tag.getSongTitle());
        assertEquals(newTag.getLeadArtist(), tag.getLeadArtist());
        assertEquals(newTag.getAlbumTitle(), tag.getAlbumTitle());
        assertEquals(newTag.getYearReleased(), tag.getYearReleased());
        assertEquals(newTag.getSongComment(), tag.getSongComment());
        assertEquals(newTag.getSongGenre(), tag.getSongGenre());
        assertEquals(newTag.getTrackNumberOnAlbum(), tag.getTrackNumberOnAlbum());
        assertEquals(newTag.getSongLyric(), tag.getSongLyric());
        assertEquals(newTag.getAuthorComposer(), tag.getAuthorComposer());
        ID3v2_4 copyTag = new ID3v2_4(tag);
        assertEquals(copyTag, tag);
    }
}
