package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberVariableLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.16.&nbsp;&nbsp; Play counter</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This is simply a counter of the number of times a file has been<br>
 * <p/>
 * &nbsp;&nbsp; played. The value is increased by one every time the file begins to<br> &nbsp;&nbsp; play. There may
 * only be one &quot;PCNT&quot; frame in each tag. When the<br> &nbsp;&nbsp; counter reaches all one's, one byte is
 * inserted in front of the<br> &nbsp;&nbsp; counter thus making the counter eight bits bigger.&nbsp; The counter
 * must<br>
 * <p/>
 * &nbsp;&nbsp; be at least 32-bits long to begin with.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Play counter', ID: &quot;PCNT&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Counter&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx (xx ...)<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyPCNT extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyPCNT object.
     */
    public FrameBodyPCNT() {
        super();
    }

    /**
     * Creates a new FrameBodyPCNT object.
     */
    public FrameBodyPCNT(final FrameBodyPCNT body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPCNT object.
     */
    public FrameBodyPCNT(final long counter) {
        setObject("Counter", new Long(counter));
    }

    /**
     * Creates a new FrameBodyPCNT object.
     */
    public FrameBodyPCNT(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "PCNT";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberVariableLength("Counter", 4));
    }
}