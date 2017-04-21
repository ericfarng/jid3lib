package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.10.&nbsp;&nbsp; Comments</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame is intended for any kind of full text information that<br> &nbsp;&nbsp; does not fit in
 * any other frame. It consists of a frame header<br> &nbsp;&nbsp; followed by encoding, language and content
 * descriptors and is ended<br> &nbsp;&nbsp; with the actual comment as a text string. Newline characters are<br>
 * &nbsp;&nbsp; allowed in the comment text string. There may be more than one<br>
 * <p/>
 * &nbsp;&nbsp; comment frame in each tag, but only one with the same language and<br> &nbsp;&nbsp; content
 * descriptor.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Comment', ID: &quot;COMM&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp; Text
 * encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Language&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Short content descrip. &lt;text string according to encoding&gt; $00 (00)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; The actual text&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;full text string according to
 * encoding&gt;<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyCOMM extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyCOMM object.
     */
    public FrameBodyCOMM() {
        super();
    }

    /**
     * Creates a new FrameBodyCOMM object.
     */
    public FrameBodyCOMM(final FrameBodyCOMM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyCOMM object.
     */
    public FrameBodyCOMM(final byte textEncoding, final String language, final String description, final String text) {
        setObject(ObjectNumberHashMap.TEXT_ENCODING, new Byte(textEncoding));
        setObject(ObjectStringHashMap.LANGUAGE, language);
        setObject("Description", description);
        setObject("Text", text);
    }

    /**
     * Creates a new FrameBodyCOMM object.
     */
    public FrameBodyCOMM(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getBriefDescription() {
        return this.getText();
    }

    public void setDescription(final String description) {
        setObject("Description", description);
    }

    public String getDescription() {
        return (String) getObject("Description");
    }

    public String getIdentifier() {
        return "COMM" + ((char) 0) + getLanguage() + ((char) 0) + getDescription();
    }

    public void setLanguage(final String language) {
        setObject(ObjectStringHashMap.LANGUAGE, language);
    }

    public String getLanguage() {
        return (String) getObject(ObjectStringHashMap.LANGUAGE);
    }

    public void setText(final String text) {
        setObject("Text", text);
    }

    public String getText() {
        return (String) getObject("Text");
    }

    public void setTextEncoding(final byte textEncoding) {
        setObject(ObjectNumberHashMap.TEXT_ENCODING, new Byte(textEncoding));
    }

    public byte getTextEncoding() {
        return ((Byte) getObject(ObjectNumberHashMap.TEXT_ENCODING)).byteValue();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 3));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectStringSizeTerminated("Text"));
    }
}