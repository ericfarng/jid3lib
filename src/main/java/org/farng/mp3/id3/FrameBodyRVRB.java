package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberFixedLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.13.&nbsp;&nbsp; Reverb</h3>
 * <p/>
 * <p>&nbsp;&nbsp; Yet another subjective frame, with which you can adjust echoes of<br> &nbsp;&nbsp; different kinds.
 * Reverb left/right is the delay between every bounce<br> &nbsp;&nbsp; in ms. Reverb bounces left/right is the number
 * of bounces that should<br> &nbsp;&nbsp; be made. $FF equals an infinite number of bounces. Feedback is the<br>
 * &nbsp;&nbsp; amount of volume that should be returned to the next echo bounce. $00<br>
 * <p/>
 * &nbsp;&nbsp; is 0%, $FF is 100%. If this value were $7F, there would be 50% volume<br> &nbsp;&nbsp; reduction on the
 * first bounce, 50% of that on the second and so on.<br> &nbsp;&nbsp; Left to left means the sound from the left bounce
 * to be played in the<br> &nbsp;&nbsp; left speaker, while left to right means sound from the left bounce to<br>
 * &nbsp;&nbsp; be played in the right speaker.</p>
 * <p/>
 * <p>&nbsp;&nbsp; 'Premix left to right' is the amount of left sound to be mixed in the<br> &nbsp;&nbsp; right before
 * any reverb is applied, where $00 id 0% and $FF is 100%.<br> &nbsp;&nbsp; 'Premix right to left' does the same thing,
 * but right to left.<br> &nbsp;&nbsp; Setting both premix to $FF would result in a mono output (if the<br> &nbsp;&nbsp;
 * reverb is applied symmetric). There may only be one &quot;RVRB&quot;
 * <p/>
 * frame in<br> &nbsp;&nbsp; each tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Reverb', ID: &quot;RVRB&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp; Reverb
 * left (ms)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Reverb right (ms)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Reverb bounces, left&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Reverb bounces, right&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Reverb feedback, left to left&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Reverb
 * feedback, left to right&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Reverb feedback, right to right&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Reverb feedback, right to left&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Premix left to
 * right&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Premix
 * right to left&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyRVRB extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyRVRB object.
     */
    public FrameBodyRVRB() {
        super();
    }

    /**
     * Creates a new FrameBodyRVRB object.
     */
    public FrameBodyRVRB(final FrameBodyRVRB body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyRVRB object.
     */
    public FrameBodyRVRB(final short reverbLeft,
                         final short reverbRight,
                         final byte reverbBouncesLeft,
                         final byte reverbBouncesRight,
                         final byte reverbFeedbackLeftToLeft,
                         final byte reverbFeedbackLeftToRight,
                         final byte reverbFeedbackRightToRight,
                         final byte reverbFeedbackRightToLeft,
                         final byte premixLeftToRight,
                         final byte premixRightToLeft) {
        setObject("Reverb Left", new Short(reverbLeft));
        setObject("Reverb Right", new Short(reverbRight));
        setObject("Reverb Bounces Left", new Byte(reverbBouncesLeft));
        setObject("Reverb Bounces Right", new Byte(reverbBouncesRight));
        setObject("Reverb Feedback Left To Left", new Byte(reverbFeedbackLeftToLeft));
        setObject("Reverb Feedback Left To Right", new Byte(reverbFeedbackLeftToRight));
        setObject("Reverb Feedback Right To Right", new Byte(reverbFeedbackRightToRight));
        setObject("Reverb Feedback Right to Left", new Byte(reverbFeedbackRightToLeft));
        setObject("Premix Left To Right", new Byte(premixLeftToRight));
        setObject("Premix Right To Left", new Byte(premixRightToLeft));
    }

    /**
     * Creates a new FrameBodyRVRB object.
     */
    public FrameBodyRVRB(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "RVRB";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberFixedLength("Reverb Left", 2));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Right", 2));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Bounces Left", 1));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Bounces Right", 1));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Feedback Left To Left", 1));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Feedback Left To Right", 1));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Feedback Right To Right", 1));
        appendToObjectList(new ObjectNumberFixedLength("Reverb Feedback Right to Left", 1));
        appendToObjectList(new ObjectNumberFixedLength("Premix Left To Right", 1));
        appendToObjectList(new ObjectNumberFixedLength("Premix Right To Left", 1));
    }
}