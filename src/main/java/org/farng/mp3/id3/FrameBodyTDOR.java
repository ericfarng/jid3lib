package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringDateTime;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Original release time' frame contains a timestamp describing<br>
 * <p/>
 * &nbsp;&nbsp; when the original recording of the audio was released. Timestamp<br> &nbsp;&nbsp; format is described in
 * the ID3v2 structure document [ID3v2-strct].</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTDOR extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTDOR object.
     */
    public FrameBodyTDOR() {
        super();
    }

    /**
     * Creates a new FrameBodyTDOR object.
     */
    public FrameBodyTDOR(final FrameBodyTDOR body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTDOR object.
     */
    public FrameBodyTDOR(final byte textEncoding, final String text) {
        setObject(ObjectNumberHashMap.TEXT_ENCODING, new Byte(textEncoding));
        setObject("Date Time", text);
    }

    /**
     * Creates a new FrameBodyTDOR object.
     */
    public FrameBodyTDOR(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TDOR";
    }

    public void setText(final String text) {
        setObject("Date Time", text);
    }

    public String getText() {
        return (String) getObject("Date Time");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringDateTime("Date Time"));
    }
}