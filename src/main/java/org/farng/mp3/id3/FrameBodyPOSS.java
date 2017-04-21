package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectNumberVariableLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.21.&nbsp;&nbsp; Position synchronisation frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame delivers information to the listener of how far into the<br> &nbsp;&nbsp; audio stream he
 * picked up; in effect, it states the time offset from<br> &nbsp;&nbsp; the first frame in the stream. The frame layout
 * is:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Head for 'Position synchronisation', ID: &quot;POSS&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Time stamp format&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Position&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx (xx ...)</p>
 * <p/>
 * <p>&nbsp;&nbsp; Where time stamp format is:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; Absolute time, 32 bit sized, using MPEG frames as unit<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; Absolute time, 32 bit sized, using milliseconds as unit</p>
 * <p/>
 * <p>&nbsp;&nbsp; and position is where in the audio the listener starts to receive,<br> &nbsp;&nbsp; i.e. the
 * beginning of the next frame. If this frame is used in the<br>
 * <p/>
 * &nbsp;&nbsp; beginning of a file the value is always 0. There may only be one<br> &nbsp;&nbsp; &quot;POSS&quot; frame
 * in each tag.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyPOSS extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyPOSS object.
     */
    public FrameBodyPOSS() {
        super();
    }

    /**
     * Creates a new FrameBodyPOSS object.
     */
    public FrameBodyPOSS(final FrameBodyPOSS body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPOSS object.
     */
    public FrameBodyPOSS(final byte timeStampFormat, final long position) {
        setObject(ObjectNumberHashMap.TIME_STAMP_FORMAT, new Byte(timeStampFormat));
        setObject("Position", new Long(position));
    }

    /**
     * Creates a new FrameBodyPOSS object.
     */
    public FrameBodyPOSS(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "POSS";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TIME_STAMP_FORMAT, 1));
        appendToObjectList(new ObjectNumberVariableLength("Position", 1));
    }
}