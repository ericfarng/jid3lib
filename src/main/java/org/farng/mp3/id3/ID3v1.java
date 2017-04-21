package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagNotFoundException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <TABLE border=0> <TBODY> <TR> <TD class=h2>What is ID3 (v1)?</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR
 * vAlign=top> <TD> <P>The audio format MPEG layer I, layer II and layer III (MP3) has no native way of saving
 * information about the contents, except for some simple yes/no parameters like "private", "copyrighted" and "original
 * home" (meaning this is the original file and not a copy). A solution to this problem was introduced with the program
 * "Studio3" by Eric Kemp alias NamkraD in 1996. By adding a small chunk of extra data in the end of the file one could
 * get the MP3 file to carry information about the audio and not just the audio itself.</P>
 * <p/>
 * <P>The placement of the tag, as the data was called, was probably chosen as there were little chance that it should
 * disturb decoders. In order to make it easy to detect a fixed size of 128 bytes was chosen. The tag has the following
 * layout (as hinted by the scheme to the right):</P> <CENTER>
 * <p/>
 * <TABLE cellSpacing=0 cellPadding=2 border=1> <TBODY> <TR> <TD>Song title</TD> <TD>30 characters</TD></TR> <TR>
 * <TD>Artist</TD> <TD>30 characters</TD></TR> <TR> <TD>Album</TD> <TD>30 characters</TD></TR> <TR> <TD>Year</TD> <TD>4
 * characters</TD></TR> <TR> <TD>Comment</TD> <TD>30 characters</TD></TR> <TR> <TD>Genre</TD> <TD>1
 * byte</TD></TR></TBODY></TABLE> </P></CENTER>
 * <p/>
 * <P class=t>If you one sum the the size of all these fields we see that 30+30+30+4+30+1 equals 125 bytes and not 128
 * bytes. The missing three bytes can be found at the very end of the tag, before the song title. These three bytes are
 * always "TAG" and is the identification that this is indeed a ID3 tag. The easiest way to find a ID3v1/1.1 tag is to
 * look for the word "TAG" 128 bytes from the end of a file.</P>
 * <p/>
 * <P class=t>As all artists doesn't have a 30 character name it is said that if there is some bytes left after the
 * information is entered in the field, those bytes should be fille with the binary value 0. You might also think that
 * you cannot write that much in the genre field, being one byte big, but it is more clever than that. The byte value
 * you enter in the genre field corresponds to a value in a predefined list. The list that Eric Kemp created had 80
 * entries, ranging from 0 to 79.</P></TD> </TR></TBODY></TABLE>
 *
 * @author Eric Farng
 * @version $Revision: 1.6 $
 */
public class ID3v1 extends AbstractID3v1 {

    protected String album = "";
    protected String artist = "";
    protected String comment = "";
    protected String title = "";
    protected String year = "";
    protected byte genre = -1;

    /**
     * Creates a new ID3v1 object.
     */
    public ID3v1() {
        // base empty constructor
    }

    /**
     * Creates a new ID3v1 object.
     */
    public ID3v1(final ID3v1 copyObject) {
        super(copyObject);
        this.album = new String(copyObject.album);
        this.artist = new String(copyObject.artist);
        this.comment = new String(copyObject.comment);
        this.title = new String(copyObject.title);
        this.year = new String(copyObject.year);
        this.genre = copyObject.genre;
    }

    /**
     * Creates a new ID3v1 object.
     */
    public ID3v1(final AbstractMP3Tag mp3tag) {
        if (mp3tag != null) {
            final ID3v1_1 convertedTag;
            if (mp3tag instanceof ID3v1 && !(mp3tag instanceof ID3v1_1)) {
                throw new UnsupportedOperationException("Copy Constructor not called. Please type cast the argument");
            }
            if (mp3tag instanceof ID3v1_1) {
                convertedTag = (ID3v1_1) mp3tag;
            } else {
                convertedTag = new ID3v1_1(mp3tag);
            }
            this.album = new String(convertedTag.album.trim());
            this.artist = new String(convertedTag.artist.trim());
            this.comment = new String(convertedTag.comment.trim());
            this.title = new String(convertedTag.title.trim());
            this.year = new String(convertedTag.year.trim());
            this.genre = convertedTag.genre;
        }
    }

    /**
     * Creates a new ID3v1 object.
     */
    public ID3v1(final RandomAccessFile file) throws TagNotFoundException, IOException {
        this.read(file);
    }

    public void setAlbum(final String album) {
        this.album = TagUtility.truncate(album, 30);
    }

    public String getAlbum() {
        return this.album;
    }

    public void setArtist(final String artist) {
        this.artist = TagUtility.truncate(artist, 30);
    }

    public String getArtist() {
        return this.artist;
    }

    public void setComment(final String comment) {
        this.comment = TagUtility.truncate(comment, 30);
    }

    public String getComment() {
        return this.comment;
    }

    public void setGenre(final byte genre) {
        this.genre = genre;
    }

    public byte getGenre() {
        return this.genre;
    }

    public ID3v1 getID3tag(final RandomAccessFile file) throws IOException {
        ID3v1 id3v1tag = new ID3v1_1();

        // look for id3v1_1 tag
        if (id3v1tag.seek(file) == true) {
            try {
                id3v1tag.read(file);
                id3v1tag.delete(file);
            } catch (TagNotFoundException ex) {
                id3v1tag = null;
            }
        } else {
            id3v1tag = null;
        }
        if (id3v1tag == null) {
            // look for id3v1 tag
            id3v1tag = new ID3v1();
            if (id3v1tag.seek(file) == true) {
                try {
                    id3v1tag.read(file);
                    id3v1tag.delete(file);
                } catch (TagNotFoundException ex) {
                    id3v1tag = null;
                }
            } else {
                id3v1tag = null;
            }
        }
        return id3v1tag;
    }

    public String getIdentifier() {
        return "ID3v1.00";
    }

    public int getSize() {
        return 128;
    }

    public void setTitle(final String title) {
        this.title = TagUtility.truncate(title, 30);
    }

    public String getTitle() {
        return this.title;
    }

    public void setYear(final String year) {
        this.year = TagUtility.truncate(year, 4);
    }

    public String getYear() {
        return this.year;
    }

    public void append(final AbstractMP3Tag tag) {
        final ID3v1 oldTag = this;
        final ID3v1 newTag;
        if (tag != null) {
            if (tag instanceof ID3v1) {
                newTag = (ID3v1) tag;
            } else {
                newTag = new ID3v1();
            }
            if (tag instanceof org.farng.mp3.lyrics3.AbstractLyrics3) {
                TagOptionSingleton.getInstance().setId3v1SaveYear(false);
                TagOptionSingleton.getInstance().setId3v1SaveComment(false);
            }
            oldTag.title = (TagOptionSingleton.getInstance().isId3v1SaveTitle() && (oldTag.title.length() == 0)) ?
                           newTag.title :
                           oldTag.title;
            oldTag.artist = (TagOptionSingleton.getInstance().isId3v1SaveArtist() && (oldTag.artist.length() == 0)) ?
                            newTag.artist :
                            oldTag.artist;
            oldTag.album = (TagOptionSingleton.getInstance().isId3v1SaveAlbum() && (oldTag.album.length() == 0)) ?
                           newTag.album :
                           oldTag.album;
            oldTag.year = (TagOptionSingleton.getInstance().isId3v1SaveYear() && (oldTag.year.length() == 0)) ?
                          newTag.year :
                          oldTag.year;
            oldTag.comment = (TagOptionSingleton.getInstance().isId3v1SaveComment() && (oldTag.comment.length() == 0)) ?
                             newTag.comment :
                             oldTag.comment;
            oldTag.genre = (TagOptionSingleton.getInstance().isId3v1SaveGenre() && (oldTag.genre < 0)) ?
                           newTag.genre :
                           oldTag.genre;

            // we don't need to reset the tag options because
            // we want to save all fields (default)
        }
    }

    public void delete(final RandomAccessFile file) throws IOException {
        if (seek(file)) {
            file.setLength(file.length() - 128);
        }
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ID3v1) == false) {
            return false;
        }
        final ID3v1 id3v1 = (ID3v1) obj;
        if (this.album.equals(id3v1.album) == false) {
            return false;
        }
        if (this.artist.equals(id3v1.artist) == false) {
            return false;
        }
        if (this.comment.equals(id3v1.comment) == false) {
            return false;
        }
        if (this.genre != id3v1.genre) {
            return false;
        }
        if (this.title.equals(id3v1.title) == false) {
            return false;
        }
        if (this.year.equals(id3v1.year) == false) {
            return false;
        }
        return super.equals(obj);
    }

    public Iterator iterator() {
        return new ID3v1Iterator(this);
    }

    public void overwrite(final AbstractMP3Tag tag) {
        final ID3v1 oldTag = this;
        final ID3v1 newTag;
        if (tag != null) {
            if (tag instanceof ID3v1) {
                newTag = (ID3v1) tag;
            } else {
                newTag = new ID3v1();
            }
            if (tag instanceof org.farng.mp3.lyrics3.AbstractLyrics3) {
                TagOptionSingleton.getInstance().setId3v1SaveYear(false);
                TagOptionSingleton.getInstance().setId3v1SaveComment(false);
            }
            oldTag.title = TagOptionSingleton.getInstance().isId3v1SaveTitle() ? newTag.title : oldTag.artist;
            oldTag.artist = TagOptionSingleton.getInstance().isId3v1SaveArtist() ? newTag.artist : oldTag.artist;
            oldTag.album = TagOptionSingleton.getInstance().isId3v1SaveAlbum() ? newTag.album : oldTag.album;
            oldTag.year = TagOptionSingleton.getInstance().isId3v1SaveYear() ? newTag.year : oldTag.year;
            oldTag.comment = TagOptionSingleton.getInstance().isId3v1SaveComment() ? newTag.comment : oldTag.comment;
            oldTag.genre = TagOptionSingleton.getInstance().isId3v1SaveGenre() ? newTag.genre : oldTag.genre;

            // we don't need to reset the tag options because
            // we want to save all fields (default)
        }
    }

    public void read(final RandomAccessFile file) throws TagNotFoundException, IOException {
        final byte[] buffer = new byte[30];
        if (seek(file) == false) {
            throw new TagNotFoundException("ID3v1 tag not found");
        }
        file.read(buffer, 0, 30);
        this.title = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 30);
        this.artist = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 30);
        this.album = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 4);
        this.year = new String(buffer, 0, 4, "ISO-8859-1").trim();
        file.read(buffer, 0, 30);
        this.comment = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 1);
        this.genre = buffer[0];
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[3];

        // If there's a tag, it's 127 bytes long and we'll find the tag
        file.seek(file.length() - 128);

        // read the TAG value
        file.read(buffer, 0, 3);
        final String tag = new String(buffer, 0, 3);
        return tag.equals("TAG");
    }

    public String toString() {
        String str = getIdentifier() + " " + this.getSize() + "\n";
        str += ("Title = " + this.title + "\n");
        str += ("Artist = " + this.artist + "\n");
        str += ("Album = " + this.album + "\n");
        str += ("Comment = " + this.comment + "\n");
        str += ("Year = " + this.year + "\n");
        str += ("Genre = " + this.genre + "\n");
        return str;
    }

    public void write(final AbstractMP3Tag tag) {
        final ID3v1 oldTag = this;
        final ID3v1 newTag;
        if (tag != null) {
            if (tag instanceof ID3v1) {
                newTag = (ID3v1) tag;
            } else {
                newTag = new ID3v1_1(tag);
            }
            oldTag.title = newTag.title;
            oldTag.artist = newTag.artist;
            oldTag.album = newTag.album;
            oldTag.year = newTag.year;
            oldTag.comment = newTag.comment;
            oldTag.genre = newTag.genre;
        }
    }

    public void write(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[128];
        int i;
        int offset = 3;
        String str;
        delete(file);
        file.seek(file.length());
        buffer[0] = (byte) 'T';
        buffer[1] = (byte) 'A';
        buffer[2] = (byte) 'G';
        if (TagOptionSingleton.getInstance().isId3v1SaveTitle()) {
            str = TagUtility.truncate(this.title, 30);
            for (i = 0; i < str.length(); i++) {
                buffer[i + offset] = (byte) str.charAt(i);
            }
        }
        offset += 30;
        if (TagOptionSingleton.getInstance().isId3v1SaveArtist()) {
            str = TagUtility.truncate(this.artist, 30);
            for (i = 0; i < str.length(); i++) {
                buffer[i + offset] = (byte) str.charAt(i);
            }
        }
        offset += 30;
        if (TagOptionSingleton.getInstance().isId3v1SaveAlbum()) {
            str = TagUtility.truncate(this.album, 30);
            for (i = 0; i < str.length(); i++) {
                buffer[i + offset] = (byte) str.charAt(i);
            }
        }
        offset += 30;
        if (TagOptionSingleton.getInstance().isId3v1SaveYear()) {
            str = TagUtility.truncate(this.year, 4);
            for (i = 0; i < str.length(); i++) {
                buffer[i + offset] = (byte) str.charAt(i);
            }
        }
        offset += 4;
        if (TagOptionSingleton.getInstance().isId3v1SaveComment()) {
            str = TagUtility.truncate(this.comment, 30);
            for (i = 0; i < str.length(); i++) {
                buffer[i + offset] = (byte) str.charAt(i);
            }
        }
        offset += 30;
        if (TagOptionSingleton.getInstance().isId3v1SaveGenre()) {
            buffer[offset] = this.genre;
        }
        file.write(buffer);
    }

    public String getSongTitle() {
        return getTitle().trim();
    }

    public String getLeadArtist() {
        return getArtist().trim();
    }

    public String getAlbumTitle() {
        return getAlbum().trim();
    }

    public String getYearReleased() {
        return getYear().trim();
    }

    public String getSongComment() {
        return getComment().trim();
    }

    public String getSongGenre() {
        return Integer.toString(getGenre());
    }

    public String getTrackNumberOnAlbum() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongLyric() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getAuthorComposer() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongTitle(String songTitle) {
        setTitle(songTitle.trim());
    }

    public void setLeadArtist(String leadArtist) {
        setArtist(leadArtist.trim());
    }

    public void setAlbumTitle(String albumTitle) {
        setAlbum(albumTitle.trim());
    }

    public void setYearReleased(String yearReleased) {
        setYear(yearReleased.trim());
    }

    public void setSongComment(String songComment) {
        setComment(songComment.trim());
    }

    public void setSongGenre(String songGenre) {
        setGenre(Byte.parseByte(songGenre.trim()));
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongLyric(String songLyrics) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setAuthorComposer(String authorComposer) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }
}