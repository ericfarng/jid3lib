package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringHashMap;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.22.&nbsp;&nbsp; Terms of use frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame contains a brief description of the terms of use and<br> &nbsp;&nbsp; ownership of the
 * file. More detailed information concerning the legal<br> &nbsp;&nbsp; terms might be available through the
 * &quot;WCOP&quot; frame. Newlines are<br> &nbsp;&nbsp; allowed in the text. There may be more than one 'Terms of use'
 * frame<br>
 * <p/>
 * &nbsp;&nbsp; in a tag, but only one with the same 'Language'.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Terms of use frame', ID: &quot;USER&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * Language&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; The actual text&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to encoding&gt;<br>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyUSER extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyUSER object.
     */
    public FrameBodyUSER() {
        super();
    }

    /**
     * Creates a new FrameBodyUSER object.
     */
    public FrameBodyUSER(final FrameBodyUSER body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyUSER object.
     */
    public FrameBodyUSER(final byte textEncoding, final String language, final String text) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Language", language);
        setObject("Text", text);
    }

    /**
     * Creates a new FrameBodyUSER object.
     */
    public FrameBodyUSER(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "USER" + ((char) 0) + getLanguage();
    }

    public String getLanguage() {
        return (String) getObject(ObjectStringHashMap.LANGUAGE);
    }

    public void setOwner(final String language) {
        setObject(ObjectStringHashMap.LANGUAGE, language);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringHashMap(ObjectStringHashMap.LANGUAGE, 3));
        appendToObjectList(new ObjectStringSizeTerminated("Text"));
    }
}