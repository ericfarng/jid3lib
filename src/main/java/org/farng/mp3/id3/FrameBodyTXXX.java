package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <p>&nbsp;&nbsp; This frame is intended for one-string text information concerning the<br> &nbsp;&nbsp; audio file in
 * a similar way to the other &quot;T&quot;-frames. The frame body<br> &nbsp;&nbsp; consists of a description of the
 * string, represented as a terminated<br> &nbsp;&nbsp; string, followed by the actual string. There may be more than
 * one<br>
 * <p/>
 * &nbsp;&nbsp; &quot;TXXX&quot; frame in each tag, but only one with the same description.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'User defined text information frame', ID: &quot;TXXX&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to encoding&gt;
 * $00 (00)<br> &nbsp;&nbsp;&nbsp;&nbsp; Value&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;text string according to encoding&gt;<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTXXX extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyTXXX object.
     */
    public FrameBodyTXXX() {
        super();
    }

    /**
     * Creates a new FrameBodyTXXX object.
     */
    public FrameBodyTXXX(final FrameBodyTXXX body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTXXX object.
     */
    public FrameBodyTXXX(final byte textEncoding, final String description, final String text) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Description", description);
        setObject("Text", text);
    }

    /**
     * Creates a new FrameBodyTXXX object.
     */
    public FrameBodyTXXX(final RandomAccessFile file) throws IOException, InvalidTagException {
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
        return "TXXX" + ((char) 0) + getDescription();
    }

    public void setText(final String text) {
        setObject("Text", text);
    }

    public String getText() {
        return (String) getObject("Text");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Text Encoding", 1));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectStringSizeTerminated("Text"));
    }
}