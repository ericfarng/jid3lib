package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagUtility;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <p class=t> The extended header contains information that is not vital to the correct parsing of the tag information,
 * hence the extended header is optional. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap> Extended header size</td><td rowspan=3>&nbsp;</td><td width=100%>$xx xx
 * xx xx</td></tr> <tr><td>Extended Flags</td><td>$xx xx</td></tr> <tr><td>Size of padding</td><td>$xx xx xx xx</tr>
 * </table> </center>
 * <p/>
 * <p class=t> Where the 'Extended header size', currently 6 or 10 bytes, excludes itself. The 'Size of padding' is
 * simply the total tag size excluding the frames and the headers, in other words the padding. The extended header is
 * considered separate from the header proper, and as such is subject to unsynchronisation. </p>
 * <p/>
 * <p class=t> The extended flags are a secondary flag set which describes further attributes of the tag. These
 * attributes are currently defined as follows </p>
 * <p/>
 * <p class=ind> %x0000000 00000000 </p>
 * <p/>
 * <p class=t> x - CRC data present </p>
 * <p/>
 * <p class=ind> If this flag is set four bytes of CRC-32 data is appended to the extended header. The CRC should be
 * calculated before unsynchronisation on the data between the extended header and the padding, i.e. the frames and only
 * the frames. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap> Total frame CRC</td><td>&nbsp;</td><td>$xx xx xx xx</td></tr> </table>
 * </center>
 *
 * @author Eric Farng
 * @version $Revision: 1.7 $
 */
public class ID3v2_3Frame extends ID3v2_2Frame {

    protected boolean compression = false;
    protected boolean encryption = false;
    protected boolean fileAlterPreservation = false;
    protected boolean groupingIdentity = false;
    protected boolean readOnly = false; // @todo implement this read only!
    // these are flags for each frame them selves
    protected boolean tagAlterPreservation = false;

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_3Frame() {
        setAlterPreservation();
    }

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_3Frame(final AbstractID3v2FrameBody body) {
        super(body);
        setAlterPreservation();
    }

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_3Frame(final ID3v2_3Frame copyObject) {
        super(copyObject);
        this.compression = copyObject.compression;
        this.encryption = copyObject.encryption;
        this.fileAlterPreservation = copyObject.fileAlterPreservation;
        this.groupingIdentity = copyObject.groupingIdentity;
        this.readOnly = copyObject.readOnly;
        this.tagAlterPreservation = copyObject.tagAlterPreservation;
    }

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_3Frame(final boolean readOnly,
                        final boolean groupingIdentity,
                        final boolean compression,
                        final boolean encryption,
                        final AbstractID3v2FrameBody body) {
        super(body);
        this.readOnly = readOnly;
        this.groupingIdentity = groupingIdentity;
        this.compression = compression;
        this.encryption = encryption;
        setAlterPreservation();
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_3Frame(final AbstractID3v2Frame frame) {
        if (frame instanceof ID3v2_3Frame) {
            final ID3v2_3Frame f = (ID3v2_3Frame) frame;
            this.tagAlterPreservation = f.tagAlterPreservation;
            this.fileAlterPreservation = f.fileAlterPreservation;
            this.readOnly = f.readOnly;
            this.groupingIdentity = f.groupingIdentity;
            this.compression = f.compression;
            this.encryption = f.encryption;
        }
        if (frame instanceof ID3v2_2Frame) {
            // no variables yet
        }
        if (frame.getBody() == null) {
            // do nothing
        } else if (TagUtility.isID3v2_3FrameIdentifier(frame.getIdentifier())) {
            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
        } else if (TagUtility.isID3v2_4FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_4to2_3(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            };
        } else if (TagUtility.isID3v2_2FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_2to2_3(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            };
        }
        // @TODO support more frames
//        } else if (TagUtility.isID3v2_4FrameIdentifier(frame.getIdentifier())) {
//            // @TODO correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
//        } else if (TagUtility.isID3v2_2FrameIdentifier(frame.getIdentifier())) {
//            // @TODO correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
    }

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_3Frame(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public int getSize() {
        return this.getBody().getSize() + 4 + 2 + 4;
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        byte b;
        long filePointer;
        final byte[] buffer = new byte[4];

        // lets scan for a non-zero byte;
        do {
            filePointer = file.getFilePointer();
            b = file.readByte();
            org.farng.mp3.id3.AbstractID3v2.incrementPaddingCounter();
        } while (b == 0);
        file.seek(filePointer);
        org.farng.mp3.id3.AbstractID3v2.decrementPaddingCounter();

        // read the four character identifier
        file.read(buffer, 0, 4);
        final String identifier = new String(buffer, 0, 4);

        // is this a valid identifier?
        if (isValidID3v2FrameIdentifier(identifier) == false) {
            file.seek(file.getFilePointer() - 3);
            throw new InvalidTagException(identifier + " is not a valid ID3v2.30 frame");
        }
        filePointer = file.getFilePointer();

        // skip the 4 byte size
        file.skipBytes(4);

        // read the flag bytes
        file.read(buffer, 0, 2);
        this.tagAlterPreservation = (buffer[0] & TagConstant.MASK_V23_TAG_ALTER_PRESERVATION) != 0;
        this.fileAlterPreservation = (buffer[0] & TagConstant.MASK_V23_FILE_ALTER_PRESERVATION) != 0;
        this.readOnly = (buffer[0] & TagConstant.MASK_V23_READ_ONLY) != 0;
        this.compression = (buffer[1] & TagConstant.MASK_V23_COMPRESSION) != 0;
        this.encryption = (buffer[1] & TagConstant.MASK_V23_ENCRYPTION) != 0;
        this.groupingIdentity = (buffer[1] & TagConstant.MASK_V23_GROUPING_IDENTITY) != 0;
        file.seek(filePointer);
        this.setBody(readBody(identifier, file));
    }

    public void write(final RandomAccessFile file) throws IOException {
        final long filePointer;
        final byte[] buffer = new byte[4];
        String str = null;
        final String identifier = getIdentifier();
        if (TagUtility.isID3v2_3FrameIdentifier(identifier)) {
            str = identifier;
        } else if (TagUtility.isID3v2_4FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_4to2_3(identifier);
        } else if (TagUtility.isID3v2_2FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_2to2_3(identifier);
        }
        if (str == null) {
            str = TagUtility.truncate(getIdentifier(), 4);
        }
        file.write(buffer, 0, str.length());
        filePointer = file.getFilePointer();

        // skip the size bytes
        file.skipBytes(4);
        setAlterPreservation();
        buffer[0] = 0;
        buffer[1] = 0;
        if (this.tagAlterPreservation) {
            buffer[0] |= TagConstant.MASK_V23_TAG_ALTER_PRESERVATION;
        }
        if (this.fileAlterPreservation) {
            buffer[0] |= TagConstant.MASK_V23_FILE_ALTER_PRESERVATION;
        }
        if (this.readOnly) {
            buffer[0] |= TagConstant.MASK_V23_READ_ONLY;
        }
        if (this.compression) {
            buffer[1] |= TagConstant.MASK_V23_COMPRESSION;
        }
        if (this.encryption) {
            buffer[1] |= TagConstant.MASK_V23_ENCRYPTION;
        }
        if (this.groupingIdentity) {
            buffer[1] |= TagConstant.MASK_V23_GROUPING_IDENTITY;
        }
        file.write(buffer, 0, 2);
        file.seek(filePointer);
        this.getBody().write(file);
    }

    protected void setAlterPreservation() {
        final String str = getIdentifier();
        if (str.equals("ETCO") ||
            str.equals("EQUA") ||
            str.equals("MLLT") ||
            str.equals("POSS") ||
            str.equals("SYLT") ||
            str.equals("SYTC") ||
            str.equals("RVAD") ||
            str.equals("TENC") ||
            str.equals("TLEN") ||
            str.equals("TSIZ")) {
            this.tagAlterPreservation = false;
            this.fileAlterPreservation = true;
        } else {
            this.tagAlterPreservation = false;
            this.fileAlterPreservation = true;
        }
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

        ID3v2_3Frame that = (ID3v2_3Frame) o;

        if (compression != that.compression) {
            return false;
        }
        if (encryption != that.encryption) {
            return false;
        }
        if (fileAlterPreservation != that.fileAlterPreservation) {
            return false;
        }
        if (groupingIdentity != that.groupingIdentity) {
            return false;
        }
        if (readOnly != that.readOnly) {
            return false;
        }
        return tagAlterPreservation == that.tagAlterPreservation;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (compression ? 1 : 0);
        result = 31 * result + (encryption ? 1 : 0);
        result = 31 * result + (fileAlterPreservation ? 1 : 0);
        result = 31 * result + (groupingIdentity ? 1 : 0);
        result = 31 * result + (readOnly ? 1 : 0);
        result = 31 * result + (tagAlterPreservation ? 1 : 0);
        return result;
    }
}