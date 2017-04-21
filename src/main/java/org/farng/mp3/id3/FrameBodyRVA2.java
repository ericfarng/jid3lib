package org.farng.mp3.id3;

/**
 * <h3>4.11.&nbsp;&nbsp; Relative volume adjustment (2)</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This is a more subjective frame than the previous ones. It allows the<br> &nbsp;&nbsp; user to say
 * how much he wants to increase/decrease the volume on each<br> &nbsp;&nbsp; channel when the file is played. The
 * purpose is to be able to align<br>
 * <p/>
 * &nbsp;&nbsp; all files to a reference volume, so that you don't have to change the<br> &nbsp;&nbsp; volume
 * constantly. This frame may also be used to balance adjust the<br> &nbsp;&nbsp; audio. The volume adjustment is
 * encoded as a fixed point decibel<br> &nbsp;&nbsp; value, 16 bit signed integer representing (adjustment*512),
 * giving<br> &nbsp;&nbsp; +/- 64 dB with a precision of 0.001953125 dB. E.g. +2 dB is stored as<br>
 * <p/>
 * &nbsp;&nbsp; $04 00 and -2 dB is $FC 00. There may be more than one &quot;RVA2&quot; frame<br> &nbsp;&nbsp; in each
 * tag, but only one with the same identification string.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Relative volume adjustment (2)', ID: &quot;RVA2&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * Identification&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'identification' string is used to identify the situation and/or<br> &nbsp;&nbsp; device where
 * this adjustment should apply. The following is then<br> &nbsp;&nbsp; repeated for every channel</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Type of channel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Volume adjustment&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Bits representing peak&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Peak volume&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx (xx
 * ...)<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; Type of channel:&nbsp; $00&nbsp; Other<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * $01&nbsp; Master volume<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $02&nbsp; Front right<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $03&nbsp; Front left<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $04&nbsp; Back right<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $05&nbsp; Back left<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $06&nbsp; Front centre<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $07&nbsp; Back centre<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $08&nbsp; Subwoofer</p>
 * <p/>
 * <p>&nbsp;&nbsp; Bits representing peak can be any number between 0 and 255. 0 means<br>
 * <p/>
 * &nbsp;&nbsp; that there is no peak volume field. The peak volume field is always<br> &nbsp;&nbsp; padded to whole
 * bytes, setting the most significant bits to zero.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class FrameBodyRVA2 extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyRVA2 object.
     */
    public FrameBodyRVA2() {
        super();
    }

    /**
     * Creates a new FrameBodyRVA2 object.
     */
    public FrameBodyRVA2(final FrameBodyRVA2 body) {
        super(body);
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }
}