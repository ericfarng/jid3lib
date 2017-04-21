package org.farng.mp3.id3;

/**
 * <p>&nbsp;&nbsp; For a more accurate description of the tempo of a musical piece, this<br> &nbsp;&nbsp; frame might be
 * used. After the header follows one byte describing<br> &nbsp;&nbsp; which time stamp format should be used. Then
 * follows one or more<br>
 * <p/>
 * &nbsp;&nbsp; tempo codes. Each tempo code consists of one tempo part and one time<br> &nbsp;&nbsp; part. The tempo is
 * in BPM described with one or two bytes. If the<br> &nbsp;&nbsp; first byte has the value $FF, one more byte follows,
 * which is added<br> &nbsp;&nbsp; to the first giving a range from 2 - 510 BPM, since $00 and $01 is<br> &nbsp;&nbsp;
 * reserved. $00 is used to describe a beat-free time period, which is<br>
 * <p/>
 * &nbsp;&nbsp; not the same as a music-free time period. $01 is used to indicate one<br> &nbsp;&nbsp; single
 * beat-stroke followed by a beat-free period.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The tempo descriptor is followed by a time stamp. Every time the<br> &nbsp;&nbsp; tempo in the music
 * changes, a tempo descriptor may indicate this for<br> &nbsp;&nbsp; the player. All tempo descriptors MUST be sorted
 * in chronological<br>
 * <p/>
 * &nbsp;&nbsp; order. The first beat-stroke in a time-period is at the same time as<br> &nbsp;&nbsp; the beat
 * description occurs. There may only be one &quot;SYTC&quot; frame in<br> &nbsp;&nbsp; each tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Synchronised tempo codes', ID: &quot;SYTC&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Time stamp format&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Tempo
 * data&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;binary data&gt;</p>
 * <p/>
 * <p>&nbsp;&nbsp; Where time stamp format is:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; Absolute time, 32 bit sized, using MPEG [MPEG] frames as unit<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; Absolute time, 32 bit sized, using milliseconds as unit</p>
 * <p/>
 * <p>&nbsp;&nbsp; Absolute time means that every stamp contains the time from the<br>
 * <p/>
 * &nbsp;&nbsp; beginning of the file.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class FrameBodySYTC extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodySYTC object.
     */
    public FrameBodySYTC() {
        super();
    }

    /**
     * Creates a new FrameBodySYTC object.
     */
    public FrameBodySYTC(final FrameBodySYTC body) {
        super(body);
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }
}