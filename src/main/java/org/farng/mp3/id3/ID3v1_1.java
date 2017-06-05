package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <TABLE border=0> <TBODY> <TR> <TD class=h2>What is ID3v1.1?</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR
 * vAlign=top> <TD> <P>ID3v1 may well be easy to implement for programmers, but it sure is frustrating for those with
 * their own, creative ideas. Since the ID3v1 tag had a fixed size and no space marked "Reserved for future use", there
 * isn't really room for that much improvement, if you want to maintain compatibility with existing software.</P>
 * <p/>
 * <P>One who found a way out was Michael Mutschler who made a quite clever improvement on ID3v1. Since all non-filled
 * fields must be padded with zeroed bytes its a good assumption that all ID3v1 readers will stop reading the field when
 * they encounter a zeroed byte. If the second last byte of a field is zeroed and the last one isn't we have an extra
 * byte to fill with information. As the comments field is to short to write anything useful in the ID3v1.1 standard
 * declares that this field should be 28 characters, that the next byte always should be zero and that the last byte
 * before the genre byte should contain which track on the CD this music comes from.</P></TD> </TR></TBODY></TABLE>
 *
 * @author Eric Farng
 * @version $Revision: 1.7 $
 */
public class ID3v1_1 extends ID3v1 {

    protected byte track = -1;

    /**
     * Creates a new ID3v1_1 object.
     */
    public ID3v1_1() {
        super();
    }

    /**
     * Creates a new ID3v1_1 object.
     */
    public ID3v1_1(final ID3v1_1 copyObject) {
        super(copyObject);
        this.track = copyObject.track;
    }

    /**
     * Creates a new ID3v1_1 object.
     */
    public ID3v1_1(final AbstractMP3Tag mp3tag) {
        if (mp3tag != null) {
            if (mp3tag instanceof ID3v1) {
                if (mp3tag instanceof ID3v1_1) {
                    throw new UnsupportedOperationException("Copy Constructor not called. Please type cast the argument");
                }

                // id3v1_1 objects are also id3v1 objects
                final ID3v1 id3old = (ID3v1) mp3tag;
                this.title = new String(id3old.title.trim());
                this.artist = new String(id3old.artist.trim());
                this.album = new String(id3old.album.trim());
                this.comment = new String(id3old.comment.trim());
                this.year = new String(id3old.year.trim());
                this.genre = id3old.genre;
            } else {
                // first change the tag to ID3v2_4 tag.
                // id3v2_4 can take any tag.
                final ID3v2_4 id3tag;
                id3tag = new ID3v2_4(mp3tag);
                ID3v2_4Frame frame;
                String text;
                if (id3tag.hasFrame("TIT2")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TIT2");
                    text = ((FrameBodyTIT2) frame.getBody()).getText();
                    this.title = TagUtility.truncate(text, 30);
                }
                if (id3tag.hasFrame("TPE1")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TPE1");
                    text = ((FrameBodyTPE1) frame.getBody()).getText();
                    this.artist = TagUtility.truncate(text, 30);
                }
                if (id3tag.hasFrame("TALB")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TALB");
                    text = ((FrameBodyTALB) frame.getBody()).getText();
                    this.album = TagUtility.truncate(text, 30);
                }
                if (id3tag.hasFrame("TDRL")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TDRL");
                    text = ((FrameBodyTDRL) frame.getBody()).getText();
                    this.year = TagUtility.truncate(text, 4);
                }
                if (id3tag.hasFrameOfType("COMM")) {
                    final Iterator iterator = id3tag.getFrameOfType("COMM");
                    text = "";
                    while (iterator.hasNext()) {
                        frame = (ID3v2_4Frame) iterator.next();
                        text += (((FrameBodyCOMM) frame.getBody()).getText() + " ");
                    }
                    this.comment = TagUtility.truncate(text, 28);
                }
                if (id3tag.hasFrame("TCON")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TCON");
                    text = ((FrameBodyTCON) frame.getBody()).getText();
                    try {
                        this.genre = (byte) TagUtility.findNumber(text);
                    } catch (TagException ex) {
                        this.genre = 0;
                    }
                }
                if (id3tag.hasFrame("TRCK")) {
                    frame = (ID3v2_4Frame) id3tag.getFrame("TRCK");
                    text = ((FrameBodyTRCK) frame.getBody()).getText();
                    try {
                        this.track = (byte) TagUtility.findNumber(text);
                    } catch (TagException ex) {
                        this.track = 0;
                    }
                }
            }
        }
    }

    /**
     * Creates a new ID3v1_1 object.
     */
    public ID3v1_1(final RandomAccessFile file) throws TagNotFoundException, IOException {
        this.read(file);
    }

    public void setComment(final String comment) {
        this.comment = TagUtility.truncate(comment, 28);
    }

    public String getComment() {
        return this.comment;
    }

    public String getIdentifier() {
        return "ID3v1_1.10";
    }

    public void setTrack(final byte track) {
        this.track = track;
    }

    public byte getTrack() {
        return this.track;
    }

    public void append(final AbstractMP3Tag tag) {
        final ID3v1_1 oldTag = this;
        final ID3v1_1 newTag;
        if (tag != null) {
            if (tag instanceof ID3v1_1) {
                newTag = (ID3v1_1) tag;
            } else {
                newTag = new ID3v1_1(tag);
            }
            if (tag instanceof org.farng.mp3.lyrics3.AbstractLyrics3) {
                TagOptionSingleton.getInstance().setId3v1SaveTrack(false);
            }
            oldTag.track = (TagOptionSingleton.getInstance().isId3v1SaveTrack() && (oldTag.track <= 0)) ?
                           newTag.track :
                           oldTag.track;

            // we don't need to reset the tag options because
            // we want to save all fields (default)
        }

        // we can't send newTag here because we need to keep the lyrics3
        // class type ... check super.append and you'll see what i mean.
        super.append(tag);
    }

    public void overwrite(final AbstractMP3Tag tag) {
        final ID3v1_1 oldTag = this;
        ID3v1_1 newTag = null;
        if (tag != null) {
            if (tag instanceof ID3v1_1) {
                newTag = (ID3v1_1) tag;
            } else {
                newTag = new ID3v1_1(tag);
            }
            if (tag instanceof org.farng.mp3.lyrics3.AbstractLyrics3) {
                TagOptionSingleton.getInstance().setId3v1SaveTrack(false);
            }
            oldTag.track = TagOptionSingleton.getInstance().isId3v1SaveTrack() ? newTag.track : oldTag.track;

            // we don't need to reset the tag options because
            // we want to save all fields (default)
        }
        super.overwrite(newTag);
    }

    public void read(final RandomAccessFile file) throws TagNotFoundException, IOException {
        final byte[] buffer = new byte[30];
        if (this.seek(file) == false) {
            throw new TagNotFoundException("ID3v1.1 tag not found");
        }
        file.read(buffer, 0, 30);
        this.title = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 30);
        this.artist = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 30);
        this.album = new String(buffer, 0, 30, "ISO-8859-1").trim();
        file.read(buffer, 0, 4);
        this.year = new String(buffer, 0, 4, "ISO-8859-1").trim();
        file.read(buffer, 0, 28);
        this.comment = new String(buffer, 0, 28, "ISO-8859-1").trim();

        // if this value is zero, then check the next value
        // to see if it's the track number. ID3v1.1
        file.read(buffer, 0, 2);
        if (buffer[0] == 0 && buffer[1] != 0) {
            this.track = buffer[1];
        } else {
            throw new TagNotFoundException("ID3v1.1 Tag Not found");
        }
        file.read(buffer, 0, 1);
        this.genre = buffer[0];
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[3];
        if (file.length() < 128) {
            return false;
        }

        // Check for the empty byte before the TRACK
        file.seek(file.length() - 3);
        buffer[0] = file.readByte();
        if (buffer[0] != 0) {
            return false;
        }

        // If there's a tag, it's 128 bytes long and we'll find the tag
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
        str += ("Track = " + this.track + "\n");
        return str;
    }

    public void write(final AbstractMP3Tag tag) {
        final ID3v1_1 oldTag = this;
        ID3v1_1 newTag = null;
        if (tag != null) {
            if (tag instanceof ID3v1_1) {
                newTag = (ID3v1_1) tag;
            } else {
                newTag = new ID3v1_1(tag);
            }
            oldTag.track = newTag.track;
        }
        super.write(newTag);
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
        str = TagUtility.truncate(this.title, 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = TagUtility.truncate(this.artist, 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = TagUtility.truncate(this.album, 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = TagUtility.truncate(this.year, 4);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 4;
        str = TagUtility.truncate(this.comment, 28);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 28;
        offset++;
        buffer[offset] = this.track; // skip one byte extra blank for 1.1

        // definition
        offset++;
        buffer[offset] = this.genre;
        file.write(buffer);
    }

    public String getTrackNumberOnAlbum() {
        return Integer.toString(getTrack());
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        setTrack(Byte.parseByte(trackNumberOnAlbum.trim()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ID3v1_1 id3v1_1 = (ID3v1_1) o;

        return track == id3v1_1.track;
    }

    @Override
    public int hashCode() {
        return (int) track;
    }
}