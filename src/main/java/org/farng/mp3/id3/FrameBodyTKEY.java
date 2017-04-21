package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Initial key' frame contains the musical key in which the sound<br> &nbsp;&nbsp; starts. It is
 * represented as a string with a maximum length of three<br>
 * <p/>
 * &nbsp;&nbsp; characters. The ground keys are represented with &quot;A&quot;,&quot;B&quot;,&quot;C&quot;,&quot;D&quot;,&quot;E&quot;,
 * <br> &nbsp;&nbsp; &quot;F&quot; and &quot;G&quot; and halfkeys represented with &quot;b&quot; and &quot;#&quot;.
 * Minor is<br>
 * <p/>
 * &nbsp;&nbsp; represented as &quot;m&quot;, e.g. &quot;Dbm&quot; $00. Off key is represented with an<br> &nbsp;&nbsp;
 * &quot;o&quot; only.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTKEY extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTKEY object.
     */
    public FrameBodyTKEY() {
        super();
    }

    /**
     * Creates a new FrameBodyTKEY object.
     */
    public FrameBodyTKEY(final FrameBodyTKEY body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTKEY object.
     */
    public FrameBodyTKEY(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTKEY object.
     */
    public FrameBodyTKEY(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TKEY";
    }
}