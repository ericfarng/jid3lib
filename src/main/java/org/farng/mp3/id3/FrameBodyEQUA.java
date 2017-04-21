package org.farng.mp3.id3;

/**
 * <h3>4.13.Equalisation</h3>
 * <p/>
 * <p class=t> This is another subjective, alignment frame. It allows the user to predefine an equalisation curve within
 * the audio file. There may only be one "EQUA" frame in each tag. </p>
 * <p/>
 * <p><center> <table border=0>
 * <p/>
 * <tr><td colspan=2>&lt;Header of 'Equalisation', ID: "EQUA"&gt;</td></tr> <tr><td>Adjustment
 * bits</td><td>$xx</td></tr> </table> </center>
 * <p/>
 * <p class=t> The 'adjustment bits' field defines the number of bits used for representation of the adjustment. This is
 * normally $10 (16 bits) for <a href="#MPEG">MPEG</a> 2 layer I, II and III and MPEG 2.5. This value may not be $00.
 * <p/>
 * </p>
 * <p/>
 * <p class=t> This is followed by 2 bytes + ('adjustment bits' rounded up to the nearest byte) for every equalisation
 * band in the following format, giving a frequency range of 0 - 32767Hz: </p>
 * <p/>
 * <p><center> <table border=0> <tr><td>Increment/decrement</td><td>%x (MSB of the Frequency)</td></tr>
 * <tr><td>Frequency </td><td>(lower 15 bits)</td></tr>
 * <p/>
 * <tr><td>Adjustment</td><td>$xx (xx ...)</td></tr> </Table> </center>
 * <p/>
 * <p class=t> The increment/decrement bit is 1 for increment and 0 for decrement. The equalisation bands should be
 * ordered increasingly with reference to frequency. All frequencies don't have to be declared. The equalisation curve
 * in the reading software should be interpolated between the values in this frame. Three equal adjustments for three
 * subsequent frequencies. A frequency should only be described once in the frame. </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class FrameBodyEQUA extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyEQUA object.
     */
    public FrameBodyEQUA() {
        super();
    }

    /**
     * Creates a new FrameBodyEQUA object.
     */
    public FrameBodyEQUA(final FrameBodyEQUA body) {
        super(body);
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }
}