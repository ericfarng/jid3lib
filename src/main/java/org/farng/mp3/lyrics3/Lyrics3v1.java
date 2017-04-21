package org.farng.mp3.lyrics3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagNotFoundException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.ID3v1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <TABLE border=0> <TBODY> <TR> <TD class=h1>&nbsp;Lyrics3 made easy&nbsp;</TD></TR> </TBODY></TABLE> <BR>&nbsp; <TABLE
 * border=0> <TBODY> <TR> <TD class=h2>What is Lyrics3?</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR
 * vAlign=top> <TD> <P>When Winamp introduced its plugin capabilities Kuo (Djohan) Shiang-shiang's Lyrics Displayer was
 * one of the first plugins, and probably the first program that made a connection between MP3 audio and lyrics. The
 * lyrics displayed by Kuo's Lyrics Displayer were stored in separate text files from which the program got it.</P>
 * <p/>
 * <P>Petr Strnad saw the problems in this so he decided to make a lyrics tag, enabling the text to reside inside the
 * audio file. This is done by creating a chunk of data which begins with "LYRICSBEGIN", ends with "LYRICSEND" and has
 * the lyrics between these keywords. This data block is then saved in the audio file between the audio and the ID3 tag.
 * If no ID3 tag is present one must be attached.</P>
 * <p/>
 * <P>The following simple rules applies to the lyrics inserted between the keywords:</P>
 * <p/>
 * <p/>
 * <UL> <LI>The keywords "LYRICSBEGIN" and "LYRICSEND" must not be present in the lyrics. <LI>The text is encoded with
 * ISO-8859-1 character set <LI>A byte in the text must not have the binary value 255. <LI>The maximum length of the
 * lyrics is 5100 bytes. <LI>Newlines are made with CR+LF sequence.</LI></UL>
 * <p/>
 * <P></P></TD> </TR></TBODY></TABLE>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class Lyrics3v1 extends AbstractLyrics3 {

    private String lyric = "";

    /**
     * Creates a new Lyrics3v1 object.
     */
    public Lyrics3v1() {
        super();
    }

    /**
     * Creates a new Lyrics3v1 object.
     */
    public Lyrics3v1(final Lyrics3v1 copyObject) {
        super(copyObject);
        this.lyric = new String(copyObject.lyric);
    }

    /**
     * Creates a new Lyrics3v1 object.
     */
    public Lyrics3v1(final AbstractMP3Tag mp3Tag) {
        if (mp3Tag != null) {
            final Lyrics3v2 lyricTag;
            if (mp3Tag instanceof Lyrics3v1) {
                throw new UnsupportedOperationException("Copy Constructor not called. Please type cast the argument");
            } else if (mp3Tag instanceof Lyrics3v2) {
                lyricTag = (Lyrics3v2) mp3Tag;
            } else {
                lyricTag = new Lyrics3v2(mp3Tag);
            }
            final FieldBodyLYR lyricField;
            lyricField = (FieldBodyLYR) lyricTag.getField("LYR").getBody();
            this.lyric = new String(lyricField.getLyric().trim());
        }
    }

    /**
     * Creates a new Lyrics3v1 object.
     */
    public Lyrics3v1(final RandomAccessFile file) throws TagNotFoundException, java.io.IOException {
        this.read(file);
    }

    public String getIdentifier() {
        return "Lyrics3v1.00";
    }

    public void setLyric(final String lyric) {
        this.lyric = TagUtility.truncate(lyric, 5100);
    }

    public String getLyric() {
        return this.lyric;
    }

    public int getSize() {
        return "LYRICSBEGIN".length() + this.lyric.length() + "LYRICSEND".length();
    }

    public boolean isSubsetOf(final Object object) {
        if ((object instanceof Lyrics3v1) == false) {
            return false;
        }
        return (((Lyrics3v1) object).lyric.indexOf(this.lyric) >= 0);
    }

    public void append(final AbstractMP3Tag tag) {
        final Lyrics3v1 oldTag = this;
        final Lyrics3v1 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v1) {
                newTag = (Lyrics3v1) tag;
            } else {
                newTag = new Lyrics3v1();
            }
            this.lyric = oldTag.lyric + "\n" + newTag.lyric;
        }
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof Lyrics3v1) == false) {
            return false;
        }
        final Lyrics3v1 lyrics3v1 = (Lyrics3v1) obj;
        if (this.lyric.equals(lyrics3v1.lyric) == false) {
            return false;
        }
        return super.equals(obj);
    }

    public Iterator iterator() {
        // todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new java.lang.UnsupportedOperationException("Method iterator() not yet implemented.");
    }

    public void overwrite(final AbstractMP3Tag tag) {
        final Lyrics3v1 oldTag = this;
        final Lyrics3v1 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v1) {
                newTag = (Lyrics3v1) tag;
            } else {
                newTag = new Lyrics3v1();
            }
            this.lyric = TagOptionSingleton.getInstance().isLyrics3Save() ? newTag.lyric : oldTag.lyric;
        }
    }

    public void read(final RandomAccessFile file) throws TagNotFoundException, IOException {
        final byte[] buffer = new byte[5100 + 9 + 11];
        final String lyricBuffer;
        if (seek(file) == false) {
            throw new TagNotFoundException("ID3v1 tag not found");
        }
        file.read(buffer);
        lyricBuffer = new String(buffer);
        this.lyric = lyricBuffer.substring(0, lyricBuffer.indexOf("LYRICSEND"));
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[5100 + 9 + 11];
        String lyricsEnd;
        final String lyricsStart;
        long offset;

        // check right before the ID3 1.0 tag for the lyrics tag
        file.seek(file.length() - 128 - 9);
        file.read(buffer, 0, 9);
        lyricsEnd = new String(buffer, 0, 9);
        if (lyricsEnd.equals("LYRICSEND")) {
            offset = file.getFilePointer();
        } else {
            // check the end of the file for a lyrics tag incase an ID3
            // tag wasn't placed after it.
            file.seek(file.length() - 9);
            file.read(buffer, 0, 9);
            lyricsEnd = new String(buffer, 0, 9);
            if (lyricsEnd.equals("LYRICSEND")) {
                offset = file.getFilePointer();
            } else {
                return false;
            }
        }

        // the tag can at most only be 5100 bytes
        offset -= (5100 + 9 + 11);
        file.seek(offset);
        file.read(buffer);
        lyricsStart = new String(buffer);

        // search for the tag
        final int i = lyricsStart.indexOf("LYRICSBEGIN");
        if (i == -1) {
            return false;
        }
        file.seek(offset + i + 11);
        return true;
    }

    public String toString() {
        final String str = getIdentifier() + " " + this.getSize() + "\n";
        return str + this.lyric;
    }

    public void write(final AbstractMP3Tag tag) {
        final Lyrics3v1 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v1) {
                newTag = (Lyrics3v1) tag;
            } else {
                newTag = new Lyrics3v1();
            }
            this.lyric = newTag.lyric;
        }
    }

    public void write(final RandomAccessFile file) throws IOException {
        String str;
        int offset;
        final byte[] buffer;
        final ID3v1 id3v1tag;
        id3v1tag = (new ID3v1()).getID3tag(file);
        delete(file);
        file.seek(file.length());
        buffer = new byte[this.lyric.length() + 11 + 9];
        str = "LYRICSBEGIN";
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        offset = str.length();
        str = TagUtility.truncate(this.lyric, 5100);
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += str.length();
        str = "LYRICSEND";
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += str.length();
        file.write(buffer, 0, offset);
        if (id3v1tag != null) {
            id3v1tag.write(file);
        }
    }

    public String getSongTitle() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getLeadArtist() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getAlbumTitle() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getYearReleased() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongComment() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongGenre() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getTrackNumberOnAlbum() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongLyric() {
        return getLyric().trim();
    }

    public String getAuthorComposer() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongTitle(String songTitle) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setLeadArtist(String leadArtist) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setAlbumTitle(String albumTitle) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setYearReleased(String yearReleased) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongComment(String songComment) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongGenre(String songGenre) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongLyric(String songLyrics) {
        setLyric(songLyrics.trim());
    }

    public void setAuthorComposer(String authorComposer) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }
}