package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <p class=t> The two biggest design goals were to be able to implement ID3v2 without disturbing old software too much
 * and that ID3v2 should be expandable. </p>
 * <p/>
 * <p class=t> The first criterion is met by the simple fact that the <a href="#mpeg">MPEG</a> decoding software uses a
 * syncsignal, embedded in the audiostream, to 'lock on to' the audio. Since the ID3v2 tag doesn't contain a valid
 * syncsignal, no software will attempt to play the tag. If, for any reason, coincidence make a syncsignal appear within
 * the tag it will be taken care of by the 'unsynchronisation scheme' described in section 5. </p>
 * <p/>
 * <p class=t> The second criterion has made a more noticeable impact on the design of the ID3v2 tag. It is constructed
 * as a container for several information blocks, called frames, whose format need not be known to the software that
 * encounters them. At the start of every frame there is an identifier that explains the frames's format and content,
 * and a size descriptor that allows software to skip unknown frames. </p>
 * <p/>
 * <p class=t> If a total revision of the ID3v2 tag should be needed, there is a version number and a size descriptor in
 * the ID3v2 header. </p>
 * <p/>
 * <p class=t> The ID3 tag described in this document is mainly targeted to files encoded with <a href="#mpeg">MPEG-2
 * layer I, MPEG-2 layer II, MPEG-2 layer III</a> and MPEG-2.5, but may work with other types of encoded audio. </p>
 * <p/>
 * <p class=t> The bitorder in ID3v2 is most significant bit first (MSB). The byteorder in multibyte numbers is most
 * significant byte first (e.g. $12345678 would be encoded $12 34 56 78). </p>
 * <p/>
 * <p class=t> It is permitted to include padding after all the final frame (at the end of the ID3 tag), making the size
 * of all the frames together smaller than the size given in the head of the tag. A possible purpose of this padding is
 * to allow for adding a few additional frames or enlarge existing frames within the tag without having to rewrite the
 * entire file. The value of the padding bytes must be $00.<br> </p>
 * <p/>
 * <p class=t> <i>Padding is good as it increases the write speed when there is already a tag present in a file. If the
 * new tag is one byte longer than the previous tag, than the extra byte can be taken from the padding, instead of
 * having to shift the entire file one byte. Padding is of course bad in that it increases the size of the file, but if
 * the amount of padding is wisely chosen (with clustersize in mind), the impact on filesystems will be virtually none.
 * As the contents is $00, it is also easy for modems and other transmission devices/protocols to compress the padding.
 * Having a $00 filled padding also increases the ability to recover erroneous tags.</i> </p> <p class=t> The ID3v2 tag
 * header, which should be the first information in the file, is 10 bytes as follows: </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>ID3/file identifier</td><td rowspan=3>&nbsp;</td><td
 * width="100%">"ID3"</td></tr> <tr><td>ID3 version</td><td>$02 00</td></tr> <tr><td>ID3
 * flags</td><td>%xx000000</td></tr> <tr><td>ID3 size</td><td>4 * </td><td>%0xxxxxxx</td></tr> </table> </center>
 * <p/>
 * <p class=t> The first three bytes of the tag are always "ID3" to indicate that this is an ID3 tag, directly followed
 * by the two version bytes. The first byte of ID3 version is it's major version, while the second byte is its revision
 * number. All revisions are backwards compatible while major versions are not. If software with ID3v2 and below support
 * should encounter version three or higher it should simply ignore the whole tag. Version and revision will never be
 * $FF. </p>
 * <p/>
 * <p class=t><i> In the first draft of ID3v2 the identifier was "TAG", just as in ID3v1. It was later changed to "MP3"
 * as I thought of the ID3v2 as the fileheader MP3 had always been missing. When it became appearant than ID3v2 was
 * going towards a general purpose audio header the identifier was changed to "ID3". </i></p>
 * <p/>
 * <p class=t> The first bit (bit 7) in the 'ID3 flags' is indicating whether or not <a
 * href="#sec5">unsynchronisation</a> is used; a set bit indicates usage. </p>
 * <p/>
 * <p class=t> The second bit (bit 6) is indicating whether or not compression is used; a set bit indicates usage. Since
 * no compression scheme has been decided yet, the ID3 decoder (for now) should just ignore the entire tag if the
 * compression bit is set. </p>
 * <p/>
 * <p class=t><i> Currently, zlib compression is being considered for the compression, in an effort to stay out of the
 * all-too-common marsh of patent trouble. Have a look at the additions draft for the latest developments. </i></p>
 * <p/>
 * <p class=t> The ID3 tag size is encoded with four bytes where the first bit (bit 7) is set to zero in every byte,
 * making a total of 28 bits. The zeroed bits are ignored, so a 257 bytes long tag is represented as $00 00 02 01. </p>
 * <p/>
 * <p class=t><i> We really gave it a second thought several times before we introduced these awkward size descriptions.
 * The reason is that we thought it would be even worse to have a file header with no set size (as we wanted to
 * unsynchronise the header if there were any false synchronisations in it). An easy way of calculating the tag size is
 * A*2^21+B*2^14+C*2^7+D = A*2097152+B*16384+C*128+D, where A is the first byte, B the second, C the third and D the
 * fourth byte. </i></p>
 * <p/>
 * <p class=t> The ID3 tag size is the size of the complete tag after unsychronisation, including padding, excluding the
 * header (total tag size - 10). The reason to use 28 bits (representing up to 256MB) for size description is that we
 * don't want to run out of space here. </p>
 * <p/>
 * <p class=t> An ID3v2 tag can be detected with the following pattern:<br> &nbsp;&nbsp;&nbsp;&nbsp;$49 44 33 yy yy xx
 * zz zz zz zz <br> Where yy is less than $FF, xx is the 'flags' byte and zz is less than $80. </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ID3v2_2 extends AbstractID3v2 {

    protected boolean compression = false;
    protected boolean unsynchronization = false;

    /**
     * Creates a new ID3v2_2 object.
     */
    public ID3v2_2() {
        super();
        setMajorVersion((byte) 2);
        setRevision((byte) 2);
    }

//    /**
//     * Creates a new ID3v2_2 object.
//     */
//    public ID3v2_2(final ID3v2_2 copyObject) {
//        super(copyObject);
//        this.compression = copyObject.compression;
//        this.unsynchronization = copyObject.unsynchronization;
//    }

    /**
     * Creates a new ID3v2_2 object.
     */
    public ID3v2_2(final AbstractMP3Tag mp3tag) {
        if (mp3tag != null) {
            final ID3v2_2 convertedTag;
            if (mp3tag instanceof ID3v2_2) {
                convertedTag = (ID3v2_2) mp3tag;
            } else {
                convertedTag = new ID3v2_4(mp3tag);
            }
            this.compression = convertedTag.compression;
            this.unsynchronization = convertedTag.unsynchronization;
            final AbstractID3v2 id3tag = convertedTag;
            final Iterator iterator = id3tag.getFrameIterator();
            AbstractID3v2Frame frame;
            ID3v2_2Frame newFrame;
            while (iterator.hasNext()) {
                frame = (AbstractID3v2Frame) iterator.next();
                newFrame = new ID3v2_2Frame(frame);
                this.setFrame(newFrame);
            }
        }
    }

    /**
     * Creates a new ID3v2_2 object.
     */
    public ID3v2_2(final RandomAccessFile file) throws TagException, IOException {
        this.read(file);
    }

    public String getIdentifier() {
        return "ID3v2_2.20";
    }

    public int getSize() {
        int size = 3 + 2 + 1 + 4;
        final Iterator iterator = getFrameIterator();
        ID3v2_2Frame frame;
        while (iterator.hasNext()) {
            frame = (ID3v2_2Frame) iterator.next();
            size += frame.getSize();
        }
        return size;
    }

    public void append(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_2) {
            this.unsynchronization = ((ID3v2_2) tag).unsynchronization;
            this.compression = ((ID3v2_2) tag).compression;
        }
        super.append(tag);
    }

    public void overwrite(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_2) {
            this.unsynchronization = ((ID3v2_2) tag).unsynchronization;
            this.compression = ((ID3v2_2) tag).compression;
        }
        super.overwrite(tag);
    }

    public void read(final RandomAccessFile file) throws TagException, IOException {
        final int size;
        ID3v2_2Frame next;
        final byte[] buffer = new byte[4];
        if (seek(file) == false) {
            throw new TagNotFoundException("ID3v2.20 tag not found");
        }

        // read the major and minor @version number & flags byte
        file.read(buffer, 0, 3);
        if ((buffer[0] != 2) || (buffer[1] != 0)) {
            throw new TagNotFoundException(getIdentifier() + " tag not found");
        }
        setMajorVersion(buffer[0]);
        setRevision(buffer[1]);
        this.unsynchronization = (buffer[2] & TagConstant.MASK_V22_UNSYNCHRONIZATION) != 0;
        this.compression = (buffer[2] & TagConstant.MASK_V22_COMPRESSION) != 0;

        // read the size
        file.read(buffer, 0, 4);
        size = byteArrayToSize(buffer);
        this.clearFrameMap();
        final long filePointer = file.getFilePointer();

        // read all frames
        this.setFileReadBytes(size);
        resetPaddingCounter();
        while ((file.getFilePointer() - filePointer) <= size) {
            try {
                next = new ID3v2_2Frame(file);
                final String id = next.getIdentifier();
                if (this.hasFrame(id)) {
                    this.appendDuplicateFrameId(id + "; ");
                    this.incrementDuplicateBytes(getFrame(id).getSize());
                }
                this.setFrame(next);
            } catch (InvalidTagException ex) {
                if (ex.getMessage().equals("Found empty frame")) {
                    this.incrementEmptyFrameBytes(10);
                } else {
                    this.incrementInvalidFrameBytes();
                }
            }
        }
        this.setPaddingSize(getPaddingCounter());

        /**
         * int newSize = this.getSize(); if ((this.padding + newSize - 10) !=
         * size) { System.out.println("WARNING: Tag sizes don't add up");
         * System.out.println("ID3v2.20 tag size : " + newSize);
         * System.out.println("ID3v2.20 padding : " + this.padding);
         * System.out.println("ID3v2.20 total : " + (this.padding + newSize));
         * System.out.println("ID3v2.20 file size: " + size); }
         */
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[3];
        file.seek(0);

        // read the tag if it exists
        file.read(buffer, 0, 3);
        final String tag = new String(buffer, 0, 3);
        if (tag.equals("ID3") == false) {
            return false;
        }

        // read the major and minor @version number
        file.read(buffer, 0, 2);

        // read back the @version bytes so we can read and save them later
        file.seek(file.getFilePointer() - 2);
        return ((buffer[0] == 2) && (buffer[1] == 0));
    }

    public String toString() {
        final Iterator iterator = this.getFrameIterator();
        ID3v2_2Frame frame;
        String str = getIdentifier() + " - " + this.getSize() + " bytes\n";
        str += ("compression        = " + this.compression + "\n");
        str += ("unsynchronization  = " + this.unsynchronization + "\n");
        while (iterator.hasNext()) {
            frame = (ID3v2_2Frame) iterator.next();
            str += (frame.toString() + "\n");
        }
        return str + "\n";
    }

    public void write(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_2) {
            this.unsynchronization = ((ID3v2_2) tag).unsynchronization;
            this.compression = ((ID3v2_2) tag).compression;
        }
        super.write(tag);
    }

    public void write(final RandomAccessFile file) throws IOException {
        final String str;
        ID3v2_2Frame frame;
        final Iterator iterator;
        final byte[] buffer = new byte[6];
        final MP3File mp3 = new MP3File();
        mp3.seekMP3Frame(file);
        final long mp3start = file.getFilePointer();
        file.seek(0);

        // write the first 10 tag bytes
        str = "ID3";
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        buffer[3] = 2;
        buffer[4] = 0;
        if (this.unsynchronization) {
            buffer[5] |= TagConstant.MASK_V22_UNSYNCHRONIZATION;
        }
        if (this.compression) {
            buffer[5] |= TagConstant.MASK_V22_COMPRESSION;
        }
        file.write(buffer);

        //write size;
        file.write(sizeToByteArray((int) mp3start - 10));

        // write all frames
        iterator = this.getFrameIterator();
        while (iterator.hasNext()) {
            frame = (ID3v2_2Frame) iterator.next();
            frame.write(file);
        }
    }

    public String getSongTitle() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TIT2");
        if (frame != null) {
            FrameBodyTIT2 body = (FrameBodyTIT2) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getLeadArtist() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TPE1");
        if (frame != null) {
            FrameBodyTPE1 body = (FrameBodyTPE1) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getAlbumTitle() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TALB");
        if (frame != null) {
            FrameBodyTALB body = (FrameBodyTALB) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getYearReleased() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TYER");
        if (frame != null) {
            FrameBodyTYER body = (FrameBodyTYER) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongComment() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("COMM" + ((char) 0) + "eng" + ((char) 0) + "");
        if (frame != null) {
            FrameBodyCOMM body = (FrameBodyCOMM) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongGenre() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TCON");
        if (frame != null) {
            FrameBodyTCON body = (FrameBodyTCON) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getTrackNumberOnAlbum() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TRCK");
        if (frame != null) {
            FrameBodyTRCK body = (FrameBodyTRCK) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongLyric() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("SYLT");
        if (frame != null) {
            FrameBodySYLT body = (FrameBodySYLT) frame.getBody();
            text = body.getLyric();
        }
        if (text == "") {
            frame = getFrame("USLT" + ((char) 0) + "eng" + ((char) 0) + "");
            if (frame != null) {
                FrameBodyUSLT body = (FrameBodyUSLT) frame.getBody();
                text = body.getLyric();
            }
        }
        return text.trim();
    }

    public String getAuthorComposer() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TCOM");
        if (frame != null) {
            FrameBodyTCOM body = (FrameBodyTCOM) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public void setSongTitle(String songTitle) {
        AbstractID3v2Frame field = getFrame("TIT2");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTIT2((byte) 0, songTitle.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTIT2) field.getBody()).setText(songTitle.trim());
        }
    }

    public void setLeadArtist(String leadArtist) {
        AbstractID3v2Frame field = getFrame("TPE1");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTPE1((byte) 0, leadArtist.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTPE1) field.getBody()).setText(leadArtist.trim());
        }
    }

    public void setAlbumTitle(String albumTitle) {
        AbstractID3v2Frame field = getFrame("TALB");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTALB((byte) 0, albumTitle.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTALB) field.getBody()).setText(albumTitle.trim());
        }
    }

    public void setYearReleased(String yearReleased) {
        AbstractID3v2Frame field = getFrame("TYER");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTYER((byte) 0, yearReleased.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTYER) field.getBody()).setText(yearReleased.trim());
        }
    }

    public void setSongComment(String songComment) {
        AbstractID3v2Frame field = getFrame("COMM");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyCOMM((byte) 0, "eng", "", songComment.trim()));
            setFrame(field);
        } else {
            ((FrameBodyCOMM) field.getBody()).setText(songComment.trim());
        }
    }

    public void setSongGenre(String songGenre) {
        AbstractID3v2Frame field = getFrame("TCON");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTCON((byte) 0, songGenre.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTCON) field.getBody()).setText(songGenre.trim());
        }
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        AbstractID3v2Frame field = getFrame("TRCK");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTRCK((byte) 0, trackNumberOnAlbum.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTRCK) field.getBody()).setText(trackNumberOnAlbum.trim());
        }
    }

    public void setSongLyric(String songLyrics) {
        AbstractID3v2Frame field = getFrame("SYLT");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyUSLT((byte) 0, "ENG", "", songLyrics.trim()));
            setFrame(field);
        } else {
            ((FrameBodyUSLT) field.getBody()).setLyric(songLyrics.trim());
        }
    }

    public void setAuthorComposer(String authorComposer) {
        AbstractID3v2Frame field = getFrame("TCOM");
        if (field == null) {
            field = createEmptyFrame();
            field.setBody(new FrameBodyTCOM((byte) 0, authorComposer.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTCOM) field.getBody()).setText(authorComposer.trim());
        }
    }
    protected AbstractID3v2Frame createEmptyFrame() {
        return new ID3v2_2Frame();
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

        ID3v2_2 id3v2_2 = (ID3v2_2) o;

        if (compression != id3v2_2.compression) {
            return false;
        }
        return unsynchronization == id3v2_2.unsynchronization;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (compression ? 1 : 0);
        result = 31 * result + (unsynchronization ? 1 : 0);
        return result;
    }
}