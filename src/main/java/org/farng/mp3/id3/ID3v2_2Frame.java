package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagUtility;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <p class=t> The headers of the frames are similar in their construction. They consist of one three character
 * identifier (capital A-Z and 0-9) and one three byte size field, making a total of six bytes. The header is excluded
 * from the size. Identifiers beginning with "X", "Y" and "Z" are for experimental use and free for everyone to use.
 * Have in mind that someone else might have used the same identifier as you. All other identifiers are either used or
 * reserved for future use. <i>This gives us 46656 combinations of frame identifiers. </i></p>
 * <p/>
 * <p class=t> The three character frame identifier is followed by a three byte size descriptor, making a total header
 * size of six bytes in every frame. The size is calculated as framesize excluding frame identifier and size descriptor
 * (frame size - 6). </p>
 * <p/>
 * <p class=t><i> The decision to have a 6 byte frame header was taken in an attempt to balance big frames against
 * little overhead. One might think that it's stupid to optimize away a few bytes when the entire MP3-file is soo huge.
 * On the other hand I thought it was really cool that most ID3v1 tags, when converted to ID3v2 was smaller than before.
 * Size does matter. </i></p>
 * <p/>
 * <p class=t> There is no fixed order of the frames' appearance in the tag, although it is desired that the frames are
 * arranged in order of significance concerning the recognition of the file. <i>The reason for this is to make it faster
 * to search for a specific file by scanning the ID3v2 tags; an intelligent parser wouldn't have to keep reading the
 * entire tag after having found that the file isn't the one being looked for.</i> An example of such order: <a
 * href="#ufi">UFI</a>, <a href="#mci">MCI</a>, <a href="#tt2">TT2</a> ...<br> </p>
 * <p/>
 * <p class=t> A tag must contain at least one frame. A frame must be at least 1 byte big, excluding the 6-byte header.
 * </p>
 * <p/>
 * <p class=t> If nothing else is said, a string is represented as <a href="#iso8859">ISO-8859-1</a> characters in the
 * range $20 - $FF. All <a href="#unicode">unicode</a> strings use 16-bit unicode 2.0 (ISO/IEC 10646-1:1993, UCS-2). All
 * numeric strings are always encoded as <a href="#iso8859">ISO-8859-1</a>. Terminated strings are terminated with $00
 * if encoded with <a href="#iso8859">ISO-8859-1</a> and $00 00 if encoded as unicode. If nothing else is said, newline
 * characters are forbidden. In <a href="#iso8859">ISO-8859-1</a>, a new line is represented, when allowed, with $0A
 * only. Frames that allow different types of text encoding have a text encoding description byte directly after the
 * frame size. If <a href="#iso8859">ISO-8859-1</a> is used this byte should be $00, if <a href="#unicode">unicode</a>
 * is used it should be $01. </p>
 * <p/>
 * <p class=t> The three byte language field is used to describe the language of the frame's content, according to <a
 * href="#iso639-2">ISO-639-2</a>.<br> <i>ISO-639-1 is not used since its supported languages are just a subset of those
 * in ISO-639-2.</i> </p>
 * <p/>
 * <p class=t> All <a href="#url">URL</a>s may be relative, e.g. "picture.png", "../doc.txt". </p>
 * <p/>
 * <p class=t> If a frame is longer than it should be, e.g. having more fields than specified in this document, that
 * indicates that additions to the frame have been made in a later version of the ID3 standard. This is reflected by the
 * revision number in the header of the tag.<br> <i>This allows us to fix our mistakes as well as introducing new
 * features in the already existing frames. </i></p>
 *
 * @author Eric Farng
 * @version $Revision: 1.6 $
 */
public class ID3v2_2Frame extends AbstractID3v2Frame {

    /**
     * Creates a new ID3v2_2Frame object.
     */
    public ID3v2_2Frame() {
        // base empty constructor
    }

    /**
     * Creates a new ID3v2_2Frame object.
     */
    public ID3v2_2Frame(final AbstractID3v2FrameBody body) {
        super(body);
    }

    /**
     * Creates a new ID3v2_2Frame object.
     */
    public ID3v2_2Frame(final ID3v2_2Frame frame) {
        super(frame);
    }

    /**
     * Creates a new ID3v2_3Frame object.
     */
    public ID3v2_2Frame(final AbstractID3v2Frame frame) {
        if (frame.getBody() == null) {
            // do nothing
        } else if (TagUtility.isID3v2_2FrameIdentifier(frame.getIdentifier())) {
            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
        } else if (TagUtility.isID3v2_3FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_3to2_2(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            }
        } else if (TagUtility.isID3v2_4FrameIdentifier(frame.getIdentifier())) {
            if (TagUtility.convertFrameID2_4to2_2(frame.getIdentifier()) != null) {
                this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
            };
        }
        // @TODO support more frames
//        } else if (TagUtility.isID3v2_3FrameIdentifier(frame.getIdentifier())) {
//            // @todo correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
//        } else if (TagUtility.isID3v2_4FrameIdentifier(frame.getIdentifier())) {
//            // @todo correctly convert tags
//            this.setBody((AbstractID3v2FrameBody) TagUtility.copyObject(frame.getBody()));
    }

    /**
     * Creates a new ID3v2_2Frame object.
     */
    public ID3v2_2Frame(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public int getSize() {
        return this.getBody().getSize() + 3 + 3;
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        final byte[] buffer = new byte[3];

        // lets scan for a non-zero byte;
        long filePointer;
        byte b;
        do {
            filePointer = file.getFilePointer();
            b = file.readByte();
            org.farng.mp3.id3.AbstractID3v2.incrementPaddingCounter();
        } while (b == 0);
        file.seek(filePointer);
        org.farng.mp3.id3.AbstractID3v2.decrementPaddingCounter();

        // read the 3 chracter identifier
        file.read(buffer, 0, 3);
        final String identifier = new String(buffer, 0, 3);

        // is this a valid identifier?
        if (isValidID3v2FrameIdentifier(identifier) == false) {
            file.seek(file.getFilePointer() - 2);
            throw new InvalidTagException(identifier + " is not a valid ID3v2.20 frame");
        }
        this.setBody(readBody(identifier, file));
    }

    public void write(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[4];
        final String identifier = getIdentifier();
        String str = null;
        if (TagUtility.isID3v2_2FrameIdentifier(identifier)) {
            str = identifier;
        } else if (TagUtility.isID3v2_4FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_4to2_2(identifier);
        } else if (TagUtility.isID3v2_3FrameIdentifier(identifier)) {
            str = TagUtility.convertFrameID2_3to2_2(identifier);
        }
        if (str == null) {
            str = TagUtility.truncate(getIdentifier(), 3);
        }
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        file.write(buffer, 0, str.length());
        this.getBody().write(file);
    }
}