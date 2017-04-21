package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringDateTime;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Tagging time' frame contains a timestamp describing then the<br>
 * <p/>
 * &nbsp;&nbsp; audio was tagged. Timestamp format is described in the ID3v2<br> &nbsp;&nbsp; structure document
 * [ID3v2-strct].</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTDTG extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTDTG object.
     */
    public FrameBodyTDTG() {
        super();
    }

    /**
     * Creates a new FrameBodyTDTG object.
     */
    public FrameBodyTDTG(final FrameBodyTDTG body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTDTG object.
     */
    public FrameBodyTDTG(final byte textEncoding, final String text) {
        setObject(ObjectNumberHashMap.TEXT_ENCODING, new Byte(textEncoding));
        setObject("Date Time", text);
    }

    /**
     * Creates a new FrameBodyTDTG object.
     */
    public FrameBodyTDTG(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TDTG";
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