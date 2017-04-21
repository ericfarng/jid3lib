package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagUtility;
import org.farng.mp3.lyrics3.FieldBodyAUT;
import org.farng.mp3.lyrics3.FieldBodyEAL;
import org.farng.mp3.lyrics3.FieldBodyEAR;
import org.farng.mp3.lyrics3.FieldBodyETT;
import org.farng.mp3.lyrics3.FieldBodyINF;
import org.farng.mp3.lyrics3.FieldBodyLYR;
import org.farng.mp3.lyrics3.Lyrics3v2Field;
import org.farng.mp3.object.ObjectLyrics3Line;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <p>&nbsp;&nbsp; All ID3v2 frames consists of one frame header followed by one or more<br> &nbsp;&nbsp; fields
 * containing the actual information. The header is always 10<br> &nbsp;&nbsp; bytes and laid out as follows:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Frame ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx&nbsp; (four characters)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4 * %0xxxxxxx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Flags&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; The frame ID is made out of the characters capital A-Z and 0-9.<br> &nbsp;&nbsp; Identifiers
 * beginning with &quot;X&quot;, &quot;Y&quot; and &quot;Z&quot; are for experimental<br> &nbsp;&nbsp; frames and free
 * for everyone to use, without the need to set the<br> &nbsp;&nbsp; experimental bit in the tag header. Bear in mind
 * that someone else<br> &nbsp;&nbsp; might have used the same identifier as you. All other identifiers are<br>
 * &nbsp;&nbsp; either used or reserved for future use.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The frame ID is followed by a size descriptor containing the size of<br> &nbsp;&nbsp; the data in the
 * final frame, after encryption, compression and<br> &nbsp;&nbsp; unsynchronisation. The size is excluding the frame
 * header ('total<br> &nbsp;&nbsp; frame size' - 10 bytes) and stored as a 32 bit synchsafe integer.</p>
 * <p/>
 * <p>&nbsp;&nbsp; In the frame header the size descriptor is followed by two flag<br> &nbsp;&nbsp; bytes. These flags
 * are described in section 4.1.</p>
 * <p/>
 * <p>&nbsp;&nbsp; There is no fixed order of the frames' appearance in the tag,<br> &nbsp;&nbsp; although it is desired
 * that the frames are arranged in order of<br> &nbsp;&nbsp; significance concerning the recognition of the file. An
 * example of<br> &nbsp;&nbsp; such order: UFID, TIT2, MCDI, TRCK ...</p>
 * <p/>
 * <p>&nbsp;&nbsp; A tag MUST contain at least one frame. A frame must be at least 1<br> &nbsp;&nbsp; byte big,
 * excluding the header.</p>
 * <p/>
 * <p>&nbsp;&nbsp; If nothing else is said, strings, including numeric strings and URLs<br> &nbsp;&nbsp; [URL], are
 * represented as ISO-8859-1 [ISO-8859-1] characters in the<br> &nbsp;&nbsp; range $20 - $FF. Such strings are
 * represented in frame descriptions<br> &nbsp;&nbsp; as &lt;text string&gt;, or &lt;full text string&gt; if newlines
 * are allowed. If<br> &nbsp;&nbsp; nothing else is said newline character is forbidden. In ISO-8859-1 a<br>
 * &nbsp;&nbsp; newline is represented, when allowed, with $0A only.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Frames that allow different types of text encoding contains a text<br> &nbsp;&nbsp; encoding
 * description byte. Possible encodings:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $00&nbsp;&nbsp; ISO-8859-1 [ISO-8859-1]. Terminated with $00.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp;&nbsp; UTF-16 [UTF-16] encoded Unicode [UNICODE] with BOM. All<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; strings in the same frame SHALL have the same
 * byteorder.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Terminated with $00 00.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp;&nbsp; UTF-16BE [UTF-16] encoded Unicode [UNICODE] without BOM.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Terminated with $00 00.<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * $03&nbsp;&nbsp; UTF-8 [UTF-8] encoded Unicode [UNICODE]. Terminated with $00.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Strings dependent on encoding are represented in frame descriptions<br> &nbsp;&nbsp; as &lt;text
 * string according to encoding&gt;, or &lt;full text string<br> &nbsp;&nbsp; according to encoding&gt; if newlines are
 * allowed. Any empty strings of<br> &nbsp;&nbsp; type $01 which are NULL-terminated may have the Unicode BOM
 * followed<br> &nbsp;&nbsp; by a Unicode NULL ($FF FE 00 00 or $FE FF 00 00).</p>
 * <p/>
 * <p>&nbsp;&nbsp; The timestamp fields are based on a subset of ISO 8601. When being as<br> &nbsp;&nbsp; precise as
 * possible the format of a time string is<br> &nbsp;&nbsp; yyyy-MM-ddTHH:mm:ss (year, &quot;-&quot;, month,
 * &quot;-&quot;, day, &quot;T&quot;, hour (out of<br> &nbsp;&nbsp; 24), &quot;:&quot;, minutes, &quot;:&quot;,
 * seconds), but the precision may be reduced by<br> &nbsp;&nbsp; removing as many time indicators as wanted. Hence
 * valid timestamps<br> &nbsp;&nbsp; are<br> &nbsp;&nbsp; yyyy, yyyy-MM, yyyy-MM-dd, yyyy-MM-ddTHH, yyyy-MM-ddTHH:mm
 * and<br> &nbsp;&nbsp; yyyy-MM-ddTHH:mm:ss. All time stamps are UTC. For durations, use<br> &nbsp;&nbsp; the slash
 * character as described in 8601, and for multiple non-<br> &nbsp;&nbsp; contiguous dates, use multiple strings, if
 * allowed by the frame<br> &nbsp;&nbsp; definition.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The three byte language field, present in several frames, is used to<br> &nbsp;&nbsp; describe the
 * language of the frame's content, according to ISO-639-2<br> &nbsp;&nbsp; [ISO-639-2]. The language should be
 * represented in lower case. If the<br> &nbsp;&nbsp; language is not known the string &quot;XXX&quot; should be
 * used.</p>
 * <p/>
 * <p>&nbsp;&nbsp; All URLs [URL] MAY be relative, e.g. &quot;picture.png&quot;, &quot;../doc.txt&quot;.</p>
 * <p/>
 * <p>&nbsp;&nbsp; If a frame is longer than it should be, e.g. having more fields than<br> &nbsp;&nbsp; specified in
 * this document, that indicates that additions to the<br> &nbsp;&nbsp; frame have been made in a later version of the
 * ID3v2 standard. This<br> &nbsp;&nbsp; is reflected by the revision number in the header of the tag.<br> </p> <a
 * name="sec4.1"></a>
 * <p/>
 * <h3>4.1.&nbsp;&nbsp; Frame header flags</h3>
 * <p/>
 * <p>&nbsp;&nbsp; In the frame header the size descriptor is followed by two flag<br> &nbsp;&nbsp; bytes. All unused
 * flags MUST be cleared. The first byte is for<br> &nbsp;&nbsp; 'status messages' and the second byte is a format
 * description. If an<br> &nbsp;&nbsp; unknown flag is set in the first byte the frame MUST NOT be changed<br>
 * &nbsp;&nbsp; without that bit cleared. If an unknown flag is set in the second<br> &nbsp;&nbsp; byte the frame is
 * likely to not be readable. Some flags in the second<br> &nbsp;&nbsp; byte indicates that extra information is added
 * to the header. These<br> &nbsp;&nbsp; fields of extra information is ordered as the flags that indicates<br>
 * &nbsp;&nbsp; them. The flags field is defined as follows (l and o left out because<br> &nbsp;&nbsp; ther resemblence
 * to one and zero):</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; %0abc0000 %0h00kmnp</p>
 * <p/>
 * <p>&nbsp;&nbsp; Some frame format flags indicate that additional information fields<br> &nbsp;&nbsp; are added to the
 * frame. This information is added after the frame<br> &nbsp;&nbsp; header and before the frame data in the same order
 * as the flags that<br> &nbsp;&nbsp; indicates them. I.e. the four bytes of decompressed size will precede<br>
 * &nbsp;&nbsp; the encryption method byte. These additions affects the 'frame size'<br> &nbsp;&nbsp; field, but are not
 * subject to encryption or compression.<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp; The default status flags setting for a frame is, unless stated<br> &nbsp;&nbsp; otherwise, 'preserved
 * if tag is altered' and 'preserved if file is<br> &nbsp;&nbsp; altered', i.e. %00000000.<br> </p> <a
 * name="sec4.1.1"></a>
 * <p/>
 * <h3>4.1.1. Frame status flags</h3>
 * <p/>
 * <p>&nbsp;&nbsp; a - Tag alter preservation</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; This flag tells the tag parser what to do with this frame if it is<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; unknown and the tag is altered in any way. This applies to all<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * kinds of alterations, including adding more padding and reordering<br> &nbsp;&nbsp;&nbsp;&nbsp; the frames.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame should be preserved.<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * 1&nbsp;&nbsp;&nbsp;&nbsp; Frame should be discarded.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; b - File alter preservation</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; This flag tells the tag parser what to do with this frame if it is<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; unknown and the file, excluding the tag, is altered. This does not<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; apply when the audio is completely replaced with other audio data.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame should be preserved.<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * 1&nbsp;&nbsp;&nbsp;&nbsp; Frame should be discarded.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; c - Read only</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag, if set, tells the software that the contents of this<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; frame are intended to be read only. Changing the contents might<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; break something, e.g. a signature. If the contents are changed,<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; without knowledge of why the frame was flagged read only and<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; without taking the proper means to compensate, e.g. recalculating<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; the signature, the bit MUST be cleared.<br> </p> <a name="sec4.1.2"></a>
 * <p/>
 * <h3>4.1.2. Frame format flags</h3>
 * <p/>
 * <p>&nbsp;&nbsp; h - Grouping identity</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag indicates whether or not this frame belongs in a group<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; with other frames. If set, a group identifier byte is added to the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; frame. Every frame with the same group identifier belongs to the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; same group.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame does not contain group information<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1&nbsp;&nbsp;&nbsp;&nbsp; Frame contains group information<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; k - Compression</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag indicates whether or not the frame is compressed.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; A 'Data Length Indicator' byte MUST be included in the frame.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame is not compressed.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1&nbsp;&nbsp;&nbsp;&nbsp; Frame is compressed using zlib [zlib] deflate method.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; If set, this requires the 'Data Length Indicator'
 * bit<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; to be set as well.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; m - Encryption<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag indicates whether or not the frame is encrypted. If set,<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; one byte indicating with which method it was encrypted will be<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; added to the frame. See description of the ENCR frame for more<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; information about encryption method registration. Encryption<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; should be done after compression. Whether or not setting this flag<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; requires the presence of a 'Data Length Indicator' depends on the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; specific algorithm used.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame is not encrypted.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1&nbsp;&nbsp;&nbsp;&nbsp; Frame is encrypted.</p>
 * <p/>
 * <p>&nbsp;&nbsp; n - Unsynchronisation</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag indicates whether or not unsynchronisation was applied<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; to this frame. See section 6 for details on unsynchronisation.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; If this flag is set all data from the end of this header to the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; end of this frame has been unsynchronised. Although desirable, the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; presence of a 'Data Length Indicator' is not made mandatory by<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; unsynchronisation.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp; Frame has not been unsynchronised.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1&nbsp;&nbsp;&nbsp;&nbsp; Frame has been unsyrchronised.</p>
 * <p/>
 * <p>&nbsp;&nbsp; p - Data length indicator</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This flag indicates that a data length indicator has been added to<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; the frame. The data length indicator is the value one would write<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; as the 'Frame length' if all of the frame format flags were<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; zeroed, represented as a 32 bit synchsafe integer.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; There is no Data Length Indicator.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; A data length Indicator has been added to the
 * frame.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.6 $
 */
public class ID3v2_4Frame extends ID3v2_3Frame {

    protected boolean dataLengthIndicator = false;
    protected boolean unsynchronization = false;

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame() {
        // base empty constructor
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final ID3v2_4Frame copyObject) {
        super(copyObject);
        this.dataLengthIndicator = copyObject.dataLengthIndicator;
        this.unsynchronization = copyObject.unsynchronization;
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final AbstractID3v2FrameBody body) {
        super(body);
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final AbstractID3v2Frame frame) {
        if (frame instanceof ID3v2_4Frame) {
            final ID3v2_4Frame f = (ID3v2_4Frame) frame;
            this.unsynchronization = f.unsynchronization;
            this.dataLengthIndicator = f.dataLengthIndicator;
        }
        if (frame instanceof ID3v2_3Frame) {
            // a id3v2_4 frame is of type id3v2_3 frame also ...
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
        } else if (TagUtility.isID3v2_4FrameIdentifier(frame.getIdentifier())) {
            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
        } else if (TagUtility.isID3v2_3FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_3to2_4(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            };
        } else if (TagUtility.isID3v2_2FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_2to2_4(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            };
        }
        // @TODO support more field conversions
//        } else if (TagUtility.isID3v2_3FrameIdentifier(frame.getIdentifier())) {
//            // @TODO correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
//        } else if (TagUtility.isID3v2_2FrameIdentifier(frame.getIdentifier())) {
//            // @TODO correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final boolean readOnly,
                        final boolean groupingIdentity,
                        final boolean compression,
                        final boolean encryption,
                        final boolean unsynchronization,
                        final boolean dataLengthIndicator,
                        final AbstractID3v2FrameBody body) {
        super(body);
        this.readOnly = readOnly;
        this.groupingIdentity = groupingIdentity;
        this.compression = compression;
        this.encryption = encryption;
        this.unsynchronization = unsynchronization;
        this.dataLengthIndicator = dataLengthIndicator;
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final Lyrics3v2Field field) throws InvalidTagException {
        final String id = field.getIdentifier();
        final String value;
        if (id.equals("IND")) {
            throw new InvalidTagException("Cannot create ID3v2.40 frame from Lyrics3 indications field.");
        } else if (id.equals("LYR")) {
            final FieldBodyLYR lyric = (FieldBodyLYR) field.getBody();
            ObjectLyrics3Line line;
            final Iterator iterator = lyric.iterator();
            final FrameBodySYLT sync;
            final FrameBodyUSLT unsync;
            final boolean hasTimeStamp = lyric.hasTimeStamp();

            // we'll create only one frame here.
            // if there is any timestamp at all, we will create a sync'ed frame.
            sync = new FrameBodySYLT((byte) 0, "ENG", (byte) 2, (byte) 1, "");
            unsync = new FrameBodyUSLT((byte) 0, "ENG", "", "");
            while (iterator.hasNext()) {
                line = (ObjectLyrics3Line) iterator.next();
                if (hasTimeStamp) {
                    sync.addLyric(line);
                } else {
                    unsync.addLyric(line);
                }
            }
            if (hasTimeStamp) {
                this.setBody(sync);
            } else {
                this.setBody(unsync);
            }
        } else if (id.equals("INF")) {
            value = ((FieldBodyINF) field.getBody()).getAdditionalInformation();
            this.setBody(new FrameBodyCOMM((byte) 0, "ENG", "", value));
        } else if (id.equals("AUT")) {
            value = ((FieldBodyAUT) field.getBody()).getAuthor();
            this.setBody(new FrameBodyTCOM((byte) 0, value));
        } else if (id.equals("EAL")) {
            value = ((FieldBodyEAL) field.getBody()).getAlbum();
            this.setBody(new FrameBodyTALB((byte) 0, value));
        } else if (id.equals("EAR")) {
            value = ((FieldBodyEAR) field.getBody()).getArtist();
            this.setBody(new FrameBodyTPE1((byte) 0, value));
        } else if (id.equals("ETT")) {
            value = ((FieldBodyETT) field.getBody()).getTitle();
            this.setBody(new FrameBodyTIT2((byte) 0, value));
        } else if (id.equals("IMG")) {
            throw new InvalidTagException("Cannot create ID3v2.40 frame from Lyrics3 image field.");
        } else {
            throw new InvalidTagException("Cannot caret ID3v2.40 frame from " + id + " Lyrics3 field");
        }
    }

    /**
     * Creates a new ID3v2_4Frame object.
     */
    public ID3v2_4Frame(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public int getSize() {
        return this.getBody().getSize() + 4 + 2 + 4;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ID3v2_4Frame) == false) {
            return false;
        }
        final ID3v2_4Frame id3v2_4Frame = (ID3v2_4Frame) obj;
        if (this.unsynchronization != id3v2_4Frame.unsynchronization) {
            return false;
        }
        if (this.dataLengthIndicator != id3v2_4Frame.dataLengthIndicator) {
            return false;
        }
        return super.equals(obj);
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        long filePointer;
        final byte[] buffer = new byte[4];
        byte b;

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
            throw new InvalidTagException(identifier + " is not a valid ID3v2.40 frame");
        }
        filePointer = file.getFilePointer();

        // skip the 4 byte size
        file.skipBytes(4);

        // read the flag bytes
        file.read(buffer, 0, 2);
        this.tagAlterPreservation = (buffer[0] & TagConstant.MASK_V24_TAG_ALTER_PRESERVATION) != 0;
        this.fileAlterPreservation = (buffer[0] & TagConstant.MASK_V24_FILE_ALTER_PRESERVATION) != 0;
        this.readOnly = (buffer[0] & TagConstant.MASK_V24_READ_ONLY) != 0;
        this.groupingIdentity = (buffer[1] & TagConstant.MASK_V24_GROUPING_IDENTITY) != 0;
        this.compression = (buffer[1] & TagConstant.MASK_V24_COMPRESSION) != 0;
        this.encryption = (buffer[1] & TagConstant.MASK_V24_ENCRYPTION) != 0;
        this.unsynchronization = (buffer[1] & TagConstant.MASK_V24_FRAME_UNSYNCHRONIZATION) != 0;
        this.dataLengthIndicator = (buffer[1] & TagConstant.MASK_V24_DATA_LENGTH_INDICATOR) != 0;
        file.seek(filePointer);
        this.setBody(readBody(identifier, file));
    }

    public void write(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[4];
        final long filePointer;
        String str = null;
        final String identifier = getIdentifier();
        if (TagUtility.isID3v2_4FrameIdentifier(identifier)) {
            str = identifier;
        } else if (TagUtility.isID3v2_3FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_3to2_4(identifier);
        } else if (TagUtility.isID3v2_2FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_2to2_4(identifier);
        }
        if (str == null) str = TagUtility.truncate(getIdentifier(), 4);
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        file.write(buffer, 0, str.length());
        filePointer = file.getFilePointer();

        // skip the size bytes
        file.skipBytes(4);
        setAlterPreservation();
        buffer[0] = 0;
        buffer[1] = 0;
        if (this.tagAlterPreservation) {
            buffer[0] |= TagConstant.MASK_V24_TAG_ALTER_PRESERVATION;
        }
        if (this.fileAlterPreservation) {
            buffer[0] |= TagConstant.MASK_V24_FILE_ALTER_PRESERVATION;
        }
        if (this.readOnly) {
            buffer[0] |= TagConstant.MASK_V24_READ_ONLY;
        }
        if (this.groupingIdentity) {
            buffer[1] |= TagConstant.MASK_V24_GROUPING_IDENTITY;
        }
        if (this.compression) {
            buffer[1] |= TagConstant.MASK_V24_COMPRESSION;
        }
        if (this.encryption) {
            buffer[1] |= TagConstant.MASK_V24_ENCRYPTION;
        }
        if (this.unsynchronization) {
            buffer[1] |= TagConstant.MASK_V24_FRAME_UNSYNCHRONIZATION;
        }
        if (this.dataLengthIndicator) {
            buffer[1] |= TagConstant.MASK_V24_DATA_LENGTH_INDICATOR;
        }
        file.write(buffer, 0, 2);
        file.seek(filePointer);
        this.getBody().write(file);
    }
}