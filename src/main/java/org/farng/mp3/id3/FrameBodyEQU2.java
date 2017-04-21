package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.AbstractMP3Object;
import org.farng.mp3.object.ObjectGroupRepeated;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.12.&nbsp;&nbsp; Equalisation (2)</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This is another subjective, alignment frame. It allows the user to<br>
 * <p/>
 * &nbsp;&nbsp; predefine an equalisation curve within the audio file. There may be<br> &nbsp;&nbsp; more than one
 * &quot;EQU2&quot; frame in each tag, but only one with the same<br> &nbsp;&nbsp; identification string.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header of 'Equalisation (2)', ID: &quot;EQU2&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Interpolation method&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Identification&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'interpolation method' describes which method is preferred when<br>
 * <p/>
 * &nbsp;&nbsp; an interpolation between the adjustment point that follows. The<br> &nbsp;&nbsp; following methods are
 * currently defined:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $00&nbsp; Band<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; No
 * interpolation is made. A jump from one adjustment level to<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; another occurs in the middle between two adjustment
 * points.<br> &nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; Linear<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Interpolation between adjustment points is linear.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'identification' string is used to identify the situation and/or<br>
 * <p/>
 * &nbsp;&nbsp; device where this adjustment should apply. The following is then<br> &nbsp;&nbsp; repeated for every
 * adjustment point</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Frequency&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Volume adjustment&nbsp; $xx xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; The frequency is stored in units of 1/2 Hz, giving it a range from 0<br> &nbsp;&nbsp; to 32767
 * Hz.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The volume adjustment is encoded as a fixed point decibel value, 16<br> &nbsp;&nbsp; bit signed
 * integer representing (adjustment*512), giving +/- 64 dB<br> &nbsp;&nbsp; with a precision of 0.001953125 dB. E.g. +2
 * dB is stored as $04 00<br>
 * <p/>
 * &nbsp;&nbsp; and -2 dB is $FC 00.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Adjustment points should be ordered by frequency and one frequency<br> &nbsp;&nbsp; should only be
 * described once in the frame.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class FrameBodyEQU2 extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyEQU2 object.
     */
    public FrameBodyEQU2() {
        super();
    }

    /**
     * Creates a new FrameBodyEQU2 object.
     */
    public FrameBodyEQU2(final FrameBodyEQU2 body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyEQU2 object.
     */
    public FrameBodyEQU2(final byte interpolationMethod,
                         final String owner,
                         final short frequency,
                         final short volumeAdjustment) {
        setObject("Interpolation Method", new Byte(interpolationMethod));
        setObject("Owner", owner);
        this.addGroup(frequency, volumeAdjustment);
    }

    /**
     * Creates a new FrameBodyEQU2 object.
     */
    public FrameBodyEQU2(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "EQU2" + ((char) 0) + getOwner();
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    public void getOwner(final String description) {
        setObject("Owner", description);
    }

    public void addGroup(final short frequency, final short volumeAdjustment) {
        final ObjectGroupRepeated group = (ObjectGroupRepeated) this.getObject("Data");
        final AbstractMP3Object freq = new ObjectNumberFixedLength("Frequency", 2);
        final AbstractMP3Object volume = new ObjectNumberFixedLength("Volume Adjustment", 2);
        group.addObject(freq);
        group.addObject(volume);
        setObject("Data", group);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Interpolation Method", 1));
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        final ObjectGroupRepeated group = new ObjectGroupRepeated("Data");
        group.addProperty(new ObjectNumberFixedLength("Frequency", 2));
        group.addProperty(new ObjectNumberFixedLength("Volume Adjustment", 2));
        appendToObjectList(group);
    }
}