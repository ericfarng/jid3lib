package org.farng.mp3.id3;

/**
 * <h3>4.6.&nbsp;&nbsp; MPEG location lookup table</h3>
 * <p/>
 * <p>&nbsp;&nbsp; To increase performance and accuracy of jumps within a MPEG [MPEG]<br>
 * <p/>
 * &nbsp;&nbsp; audio file, frames with time codes in different locations in the file<br> &nbsp;&nbsp; might be useful.
 * This ID3v2 frame includes references that the<br> &nbsp;&nbsp; software can use to calculate positions in the file.
 * After the frame<br> &nbsp;&nbsp; header follows a descriptor of how much the 'frame counter' should be<br>
 * &nbsp;&nbsp; increased for every reference. If this value is two then the first<br>
 * <p/>
 * &nbsp;&nbsp; reference points out the second frame, the 2nd reference the 4th<br> &nbsp;&nbsp; frame, the 3rd
 * reference the 6th frame etc. In a similar way the<br> &nbsp;&nbsp; 'bytes between reference' and 'milliseconds
 * between reference' points<br> &nbsp;&nbsp; out bytes and milliseconds respectively.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Each reference consists of two parts; a certain number of bits, as<br> &nbsp;&nbsp; defined in 'bits
 * for bytes deviation', that describes the difference<br> &nbsp;&nbsp; between what is said in 'bytes between
 * reference' and the reality and<br> &nbsp;&nbsp; a certain number of bits, as defined in 'bits for milliseconds<br>
 * &nbsp;&nbsp; deviation', that describes the difference between what is said in<br>
 * <p/>
 * &nbsp;&nbsp; 'milliseconds between reference' and the reality. The number of bits<br> &nbsp;&nbsp; in every
 * reference, i.e. 'bits for bytes deviation'+'bits for<br> &nbsp;&nbsp; milliseconds deviation', must be a multiple of
 * four. There may only<br> &nbsp;&nbsp; be one &quot;MLLT&quot; frame in each tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Location lookup table', ID: &quot;MLLT&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; MPEG frames between reference&nbsp; $xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Bytes between
 * reference&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Milliseconds between reference $xx xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Bits for bytes
 * deviation&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Bits for milliseconds
 * dev.&nbsp;&nbsp;&nbsp;&nbsp; $xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; Then for every reference the following data is included;</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Deviation in bytes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; %xxx....<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Deviation in milliseconds&nbsp; %xxx....<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class FrameBodyMLLT extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyMLLT object.
     */
    public FrameBodyMLLT() {
        super();
    }

    /**
     * Creates a new FrameBodyMLLT object.
     */
    public FrameBodyMLLT(final FrameBodyMLLT body) {
        super(body);
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }
}