package org.farng.mp3.lyrics3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v2_4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <TABLE border=0> <TBODY> <TR> <TD class=h2>What is Lyrics3 v2.00?</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY>
 * <TR vAlign=top> <TD> <P>The Lyrics3 v2.00 tag is more complicated than the previous Lyrics3 tag but has a lot more
 * capabilities. Just like the old Lyrics3 tag it resides between the audio and the ID3 tag, which must be present. The
 * tag uses only text for everything from content to size descriptors, which are represented as numerical strings. This
 * makes it easier to implement a Lyrics3 v2.00 reader. At least if BASIC is your language of choice.</P>
 * <p/>
 * <P>The Lyrics3 block, after the MP3 audio and before the ID3 tag, begins with the word "LYRICSBEGIN" after which a
 * number of field records follows. The Lyrics3 block ends with a six character size descriptor and the string
 * "LYRICS200". The size value includes the "LYRICSBEGIN" and "LYRICS200" strings.</P>
 * <p/>
 * <P>Lyrics2 v2.00 uses somthing called fields to represent information. The data in a field can consist of ASCII
 * characters in the range 01 to 254 according to the standard. As the ASCII character map is only defined from 00 to
 * 128 ISO-8859-1 might be assumed. Numerical fields are 5 or 6 characters long, depending on location, and are padded
 * with zeroes.
 * <p/>
 * <P>Only the size of the tag sets the limit for how many fields may be present. All fields uses a simple structure
 * that includes a three character field ID, six characters describing the size of the information and the actual
 * information. This makes it possible to read unknown fields and write them back when saving the tag. There are no
 * required fields in the tag, but at least one field must exist. Fields can appear in any order in the tag, except the
 * indication field which must be the first field if used. Fields that include more then one line uses [CR][LF]
 * delimiters between lines.</P></TD> </TR></TBODY></TABLE> <BR>&nbsp; <TABLE border=0> <TBODY> <TR> <TD
 * class=h2>Defined fields</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR vAlign=top> <TD> <P>The following list
 * is a list of currently defined field IDs. More fields might be added if needed on newer versions of the Lyrics3 v2.00
 * specifications. Unknown fields should be ignored.</P> <TABLE> <TBODY> <TR> <TD><U>ID</U></TD> <TD><U>Max
 * size</U></TD> <TD><U>Description</U></TD></TR> <TR vAlign=top> <TD><B>IND</B></TD> <TD>00002</TD> <TD>Indications
 * field. This is always two characters big in v2.00, but might be bigger in a future standard. The first byte indicates
 * wether or not a lyrics field is present. "1" for present and "0" for otherwise. The second character indicates if
 * there is a timestamp in the lyrics. Again "1" for yes and "0" for no.</TD></TR> <TR vAlign=top> <TD><B>LYR</B></TD>
 * <TD>99999</TD> <TD>Lyrics multi line text. Timestamps can be used anywhere in the text in any order. Timestamp format
 * is [mm:ss] (no spaces allowed in the timestamps).</TD></TR> <TR vAlign=top> <TD><B>INF</B></TD> <TD>99999</TD>
 * <TD>Additional information multi line text.</TD></TR> <TR vAlign=top> <TD><B>AUT</B></TD> <TD>00250</TD>
 * <TD>Lyrics/Music Author name.</TD></TR> <TR vAlign=top> <TD><B>EAL</B></TD> <TD>00250</TD> <TD>Extended Album
 * name.</TD></TR> <TR vAlign=top> <TD><B>EAR</B></TD> <TD>00250</TD> <TD>Extended Artist name.</TD></TR> <TR
 * vAlign=top> <TD><B>ETT</B></TD> <TD>00250</TD> <TD>Extended Track Title.</TD></TR> <TR vAlign=top>
 * <TD><B>IMG</B></TD> <TD>99999</TD> <TD>Link to an image files (BMP or JPG format). Image lines include filename,
 * description and timestamp separated by delimiter - two ASCII chars 124 ("||"). Description and timestamp are
 * optional, but if timestamp is used, and there is no description, two delimiters ("||||") should be used between the
 * filename and the timestamp. Multiple images are allowed by using a [CR][LF] delimiter between each image line. No
 * [CR][LF] is needed after the last image line. Number of images is not limited (except by the field
 * size).<BR><B>Filename</B> can be in one of these formats: <UL> <LI>Filename only - when the image is located in the
 * same path as the MP3 file (preferred, since if you move the mp3 file this will still be correct) <LI>Relative Path +
 * Filename - when the image is located in a subdirectory below the MP3 file (i.e. images\cover.jpg) <LI>Full path +
 * Filename - when the image is located in a totally different path or drive. This will not work if the image is moved
 * or drive letters has changed, and so should be avoided if possible (i.e. c:\images\artist.jpg)</LI></UL><B>Description</B>
 * can be up to 250 chars long.<BR><B>Timestamp</B> must be formatted like the lyrics timestamp which is "[mm:ss]". If
 * an image has a timestamp, then the visible image will automatically switch to that image on the timestamp play time,
 * just the same as the selected lyrics line is switched based on timestamps.</TD></TR></TBODY></TABLE>
 * </TD></TR></TBODY></TABLE> &nbsp; <TABLE border=0> <TBODY> <TR vAlign=top> <TD> <P>The extended Album, Artist and
 * Track are an extension to the fields in the ID3v1 tag - which are limited to 30 chars. If these extended fields
 * exist, make sure their first 30 chars are exactly the same as the ones in the ID3v1 tag. If they are the same,
 * display the extended field. If not, display the one from the ID tag. These 'mismatched' extended fields, should be
 * removed when saving the lyrics tag.</P>
 * <p/>
 * <P>When saving the extended fields, make sure to copy the first 30 chars of each field to the ID3 tag matching
 * fields. It is recommended NOT to save extended fields at all, if they are not larger then 30
 * chars.</P></TD></TR></TBODY></TABLE>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class Lyrics3v2 extends AbstractLyrics3 {

    private Map fieldMap = new HashMap(8);

    /**
     * Creates a new Lyrics3v2 object.
     */
    public Lyrics3v2() {
        super();
    }

    /**
     * Creates a new Lyrics3v2 object.
     */
    public Lyrics3v2(final Lyrics3v2 copyObject) {
        super(copyObject);
        final Iterator iterator = copyObject.fieldMap.keySet().iterator();
        String oldIdentifier;
        String newIdentifier;
        Lyrics3v2Field newObject;
        while (iterator.hasNext()) {
            oldIdentifier = iterator.next().toString();
            newIdentifier = oldIdentifier;
            newObject = new Lyrics3v2Field((Lyrics3v2Field) copyObject.fieldMap.get(newIdentifier));
            fieldMap.put(newIdentifier, newObject);
        }
    }

    /**
     * Creates a new Lyrics3v2 object.
     */
    public Lyrics3v2(final AbstractMP3Tag mp3tag) {
        super();
        if (mp3tag != null) {
            // upgrade the tag to lyrics3v2
            if (mp3tag instanceof Lyrics3v2) {
                throw new UnsupportedOperationException("Copy Constructor not called. Please type cast the argument");
            } else if (mp3tag instanceof Lyrics3v1) {
                final Lyrics3v1 lyricOld = (Lyrics3v1) mp3tag;
                final Lyrics3v2Field newField = new Lyrics3v2Field(new FieldBodyLYR(lyricOld.getLyric()));
                fieldMap.put(newField.getIdentifier(), newField);
            } else {
                Lyrics3v2Field newField;
                final Iterator iterator;
                iterator = (new ID3v2_4(mp3tag)).iterator();
                while (iterator.hasNext()) {
                    try {
                        newField = new Lyrics3v2Field((AbstractID3v2Frame) iterator.next());
                        this.fieldMap.put(newField.getIdentifier(), newField);
                    } catch (TagException ex) {
                        //invalid frame to create lyrics3 field. ignore and
                        // keep going
                    }
                }
            }
        }
    }

    /**
     * Creates a new Lyrics3v2 object.
     */
    public Lyrics3v2(final RandomAccessFile file) throws TagNotFoundException, IOException {
        this.read(file);
    }

    public void setField(final Lyrics3v2Field field) {
        if (field.getBody() != null) {
            this.fieldMap.put(field.getIdentifier(), field);
        }
    }

    /**
     * Gets the value of the frame identified by identifier
     *
     * @param identifier The three letter code
     *
     * @return The value associated with the identifier
     */
    public Lyrics3v2Field getField(final String identifier) {
        return (Lyrics3v2Field) this.fieldMap.get(identifier);
    }

    public int getFieldCount() {
        return this.fieldMap.size();
    }

    public String getIdentifier() {
        return "Lyrics3v2.00";
    }

    public int getSize() {
        int size = 0;
        final Iterator iterator = this.fieldMap.values().iterator();
        Lyrics3v2Field field;
        while (iterator.hasNext()) {
            field = (Lyrics3v2Field) iterator.next();
            size += field.getSize();
        }

        // include LYRICSBEGIN, but not 6 char size or LYRICSEND
        return 11 + size;
    }

    public void append(final AbstractMP3Tag tag) {
        final Lyrics3v2 oldTag = this;
        final Lyrics3v2 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v2) {
                newTag = (Lyrics3v2) tag;
            } else {
                newTag = new Lyrics3v2(tag);
            }
            Iterator iterator = newTag.fieldMap.values().iterator();
            Lyrics3v2Field field;
            AbstractLyrics3v2FieldBody body;
            while (iterator.hasNext()) {
                field = (Lyrics3v2Field) iterator.next();
                if (oldTag.hasField(field.getIdentifier()) == false) {
                    oldTag.setField(field);
                } else {
                    body = (AbstractLyrics3v2FieldBody) oldTag.getField(field.getIdentifier()).getBody();
                    final boolean save = TagOptionSingleton.getInstance().getLyrics3SaveField(field.getIdentifier());
                    if ((body.getSize() == 0) && save) {
                        oldTag.setField(field);
                    }
                }
            }

            // reset tag options to save all current fields.
            iterator = oldTag.fieldMap.keySet().iterator();
            String id;
            while (iterator.hasNext()) {
                id = (String) iterator.next();
                TagOptionSingleton.getInstance().setLyrics3SaveField(id, true);
            }
        }
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof Lyrics3v2) == false) {
            return false;
        }
        final Lyrics3v2 lyrics3v2 = (Lyrics3v2) obj;
        if (this.fieldMap.equals(lyrics3v2.fieldMap) == false) {
            return false;
        }
        return super.equals(obj);
    }

    public boolean hasField(final String identifier) {
        return this.fieldMap.containsKey(identifier);
    }

    public Iterator iterator() {
        return this.fieldMap.values().iterator();
    }

    public void overwrite(final AbstractMP3Tag tag) {
        final Lyrics3v2 oldTag = this;
        final Lyrics3v2 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v2) {
                newTag = (Lyrics3v2) tag;
            } else {
                newTag = new Lyrics3v2(tag);
            }
            Iterator iterator = newTag.fieldMap.values().iterator();
            Lyrics3v2Field field;
            while (iterator.hasNext()) {
                field = (Lyrics3v2Field) iterator.next();
                if (TagOptionSingleton.getInstance().getLyrics3SaveField(field.getIdentifier())) {
                    oldTag.setField(field);
                }
            }

            // reset tag options to save all current fields.
            iterator = oldTag.fieldMap.keySet().iterator();
            String id;
            while (iterator.hasNext()) {
                id = (String) iterator.next();
                TagOptionSingleton.getInstance().setLyrics3SaveField(id, true);
            }
        }
    }

    public void read(final RandomAccessFile file) throws TagNotFoundException, IOException {
        final long filePointer;
        final int lyricSize;
        if (seek(file)) {
            lyricSize = seekSize(file);
        } else {
            throw new TagNotFoundException("Lyrics3v2.00 Tag Not Found");
        }

        // reset file pointer to the beginning of the tag;
        seek(file);
        filePointer = file.getFilePointer();
        this.fieldMap = new HashMap();
        Lyrics3v2Field lyric;

        // read each of the fields
        while ((file.getFilePointer() - filePointer) < (lyricSize - 11)) {
            try {
                lyric = new Lyrics3v2Field(file);
                setField(lyric);
            } catch (InvalidTagException ex) {
                // keep reading until we're done
            }
        }
    }

    public void removeField(final String identifier) {
        this.fieldMap.remove(identifier);
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[11];
        String lyricEnd;
        final String lyricStart;
        long filePointer;
        final long lyricSize;

        // check right before the ID3 1.0 tag for the lyrics tag
        file.seek(file.length() - 128 - 9);
        file.read(buffer, 0, 9);
        lyricEnd = new String(buffer, 0, 9);
        if (lyricEnd.equals("LYRICS200")) {
            filePointer = file.getFilePointer();
        } else {
            // check the end of the file for a lyrics tag incase an ID3
            // tag wasn't placed after it.
            file.seek(file.length() - 9);
            file.read(buffer, 0, 9);
            lyricEnd = new String(buffer, 0, 9);
            if (lyricEnd.equals("LYRICS200")) {
                filePointer = file.getFilePointer();
            } else {
                return false;
            }
        }

        // read the 6 bytes for the length of the tag
        filePointer -= (9 + 6);
        file.seek(filePointer);
        file.read(buffer, 0, 6);
        lyricSize = Integer.parseInt(new String(buffer, 0, 6));

        // read the lyrics begin tag if it exists.
        file.seek(filePointer - lyricSize);
        file.read(buffer, 0, 11);
        lyricStart = new String(buffer, 0, 11);
        return lyricStart.equals("LYRICSBEGIN") == true;
    }

    public String toString() {
        final Iterator iterator = this.fieldMap.values().iterator();
        Lyrics3v2Field field;
        String str = getIdentifier() + " " + this.getSize() + "\n";
        while (iterator.hasNext()) {
            field = (Lyrics3v2Field) iterator.next();
            str += (field.toString() + "\n");
        }
        return str;
    }

    public void updateField(final String identifier) {
        Lyrics3v2Field lyrField;
        if (identifier.equals("IND")) {
            final boolean lyricsPresent = this.fieldMap.containsKey("LYR");
            boolean timeStampPresent = false;
            if (lyricsPresent) {
                lyrField = (Lyrics3v2Field) this.fieldMap.get("LYR");
                final FieldBodyLYR lyrBody = (FieldBodyLYR) lyrField.getBody();
                timeStampPresent = lyrBody.hasTimeStamp();
            }
            lyrField = new Lyrics3v2Field(new FieldBodyIND(lyricsPresent, timeStampPresent));
            setField(lyrField);
        }
    }

    public void write(final AbstractMP3Tag tag) {
        final Lyrics3v2 oldTag = this;
        final Lyrics3v2 newTag;
        if (tag != null) {
            if (tag instanceof Lyrics3v2) {
                newTag = (Lyrics3v2) tag;
            } else {
                newTag = new Lyrics3v2(tag);
            }
            final Iterator iterator = newTag.fieldMap.values().iterator();
            Lyrics3v2Field field;
            oldTag.fieldMap.clear();
            while (iterator.hasNext()) {
                field = (Lyrics3v2Field) iterator.next();
                oldTag.setField(field);
            }
        }
    }

    public void write(final RandomAccessFile file) throws IOException {
        int offset = 0;
        final long filePointer;
        final byte[] buffer = new byte[6 + 9];
        String str;
        Lyrics3v2Field field;
        final Iterator iterator;
        ID3v1 id3v1tag = new ID3v1();
        id3v1tag = id3v1tag.getID3tag(file);
        delete(file);
        file.seek(file.length());
        filePointer = file.getFilePointer();
        str = "LYRICSBEGIN";
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        file.write(buffer, 0, str.length());

        // IND needs to go first. lets create/update it and write it first.
        updateField("IND");
        field = (Lyrics3v2Field) this.fieldMap.get("IND");
        field.write(file);
        iterator = this.fieldMap.values().iterator();
        while (iterator.hasNext()) {
            field = (Lyrics3v2Field) iterator.next();
            final String id = field.getIdentifier();
            final boolean save = TagOptionSingleton.getInstance().getLyrics3SaveField(id);
            if ((id.equals("IND") == false) && save) {
                field.write(file);
            }
        }
        final long size;
        size = file.getFilePointer() - filePointer;
        str = Long.toString(size);
        for (int i = 0; i < (6 - str.length()); i++) {
            buffer[i] = (byte) '0';
        }
        offset += (6 - str.length());
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += str.length();
        str = "LYRICS200";
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += str.length();
        file.write(buffer, 0, offset);
        if (id3v1tag != null) {
            id3v1tag.write(file);
        }
    }

    private int seekSize(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[11];
        String lyricEnd;
        long filePointer;

        // check right before the ID3 1.0 tag for the lyrics tag
        file.seek(file.length() - 128 - 9);
        file.read(buffer, 0, 9);
        lyricEnd = new String(buffer, 0, 9);
        if (lyricEnd.equals("LYRICS200")) {
            filePointer = file.getFilePointer();
        } else {
            // check the end of the file for a lyrics tag incase an ID3
            // tag wasn't placed after it.
            file.seek(file.length() - 9);
            file.read(buffer, 0, 9);
            lyricEnd = new String(buffer, 0, 9);
            if (lyricEnd.equals("LYRICS200")) {
                filePointer = file.getFilePointer();
            } else {
                return -1;
            }
        }

        // read the 6 bytes for the length of the tag
        filePointer -= (9 + 6);
        file.seek(filePointer);
        file.read(buffer, 0, 6);
        return Integer.parseInt(new String(buffer, 0, 6));
    }

    public String getSongTitle() {
        String title = "";
        Lyrics3v2Field field = getField("ETT");
        if (field != null) {
            FieldBodyETT body = (FieldBodyETT) field.getBody();
            title = body.getTitle();
        }
        return title.trim();
    }

    public String getLeadArtist() {
        String artist = "";
        Lyrics3v2Field field = getField("EAR");
        if (field != null) {
            FieldBodyEAR body = (FieldBodyEAR) field.getBody();
            artist = body.getArtist();
        }
        return artist.trim();
    }

    public String getAlbumTitle() {
        String album = "";
        Lyrics3v2Field field = getField("EAL");
        if (field != null) {
            FieldBodyEAL body = (FieldBodyEAL) field.getBody();
            album = body.getAlbum();
        }
        return album.trim();
    }

    public String getYearReleased() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongComment() {
        String additionalInformation = "";
        Lyrics3v2Field field = getField("INF");
        if (field != null) {
            FieldBodyINF body = (FieldBodyINF) field.getBody();
            additionalInformation = body.getAdditionalInformation();
        }
        return additionalInformation.trim();
    }

    public String getSongGenre() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getTrackNumberOnAlbum() {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public String getSongLyric() {
        String lyrics = "";
        Lyrics3v2Field field = getField("LYR");
        if (field != null) {
            FieldBodyLYR body = (FieldBodyLYR) field.getBody();
            lyrics = body.getLyric();
        }
        return lyrics.trim().trim();
    }

    public String getAuthorComposer() {
        String author = "";
        Lyrics3v2Field field = getField("AUT");
        if (field != null) {
            FieldBodyAUT body = (FieldBodyAUT) field.getBody();
            author = body.getAuthor();
        }
        return author.trim();
    }

    public void setSongTitle(String songTitle) {
        Lyrics3v2Field field = getField("ETT");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyETT(songTitle.trim()));
            setField(field);
        } else {
            ((FieldBodyETT) field.getBody()).setTitle(songTitle.trim());
        }
    }

    public void setLeadArtist(String leadArtist) {
        Lyrics3v2Field field = getField("EAR");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyEAR(leadArtist.trim()));
            setField(field);
        } else {
            ((FieldBodyEAR) field.getBody()).setArtist(leadArtist.trim());
        }
    }

    public void setAlbumTitle(String albumTitle) {
        Lyrics3v2Field field = getField("EAL");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyEAL(albumTitle.trim()));
            setField(field);
        } else {
            ((FieldBodyEAL) field.getBody()).setAlbum(albumTitle.trim());
        }
    }

    public void setYearReleased(String yearReleased) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongComment(String songComment) {
        Lyrics3v2Field field = getField("INF");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyINF(songComment.trim()));
            setField(field);
        } else {
            ((FieldBodyINF) field.getBody()).setAdditionalInformation(songComment.trim());
        }
    }

    public void setSongGenre(String songGenre) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        throw new UnsupportedOperationException("This tag does not contain that information");
    }

    public void setSongLyric(String songLyrics) {
        Lyrics3v2Field field = getField("LYR");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyLYR(songLyrics.trim()));
            setField(field);
        } else {
            ((FieldBodyLYR) field.getBody()).setLyric(songLyrics.trim());
        }
    }

    public void setAuthorComposer(String authorComposer) {
        Lyrics3v2Field field = getField("AUT");
        if (field == null) {
            field = new Lyrics3v2Field(new FieldBodyAUT(authorComposer.trim()));
            setField(field);
        } else {
            ((FieldBodyAUT) field.getBody()).setAuthor(authorComposer.trim());
        }
    }
}