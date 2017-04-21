package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Media type' frame describes from which media the sound<br> &nbsp;&nbsp; originated. This may be a
 * text string or a reference to the<br> &nbsp;&nbsp; predefined media types found in the list below. Example:<br>
 * &nbsp;&nbsp; &quot;VID/PAL/VHS&quot; $00.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; DIG&nbsp;&nbsp;&nbsp; Other digital media<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /A&nbsp;&nbsp;&nbsp; Analogue transfer from media</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; ANA&nbsp;&nbsp;&nbsp; Other analogue media<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /WAC&nbsp; Wax cylinder<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /8CA&nbsp; 8-track tape
 * cassette</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; CD&nbsp;&nbsp;&nbsp;&nbsp; CD<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /A&nbsp;&nbsp;&nbsp; Analogue transfer from media<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /DD&nbsp;&nbsp; DDD<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /AD&nbsp;&nbsp; ADD<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /AA&nbsp;&nbsp; AAD</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; LD&nbsp;&nbsp;&nbsp;&nbsp; Laserdisc</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; TT&nbsp;&nbsp;&nbsp;&nbsp; Turntable records<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /33&nbsp;&nbsp;&nbsp; 33.33 rpm<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /45&nbsp;&nbsp;&nbsp; 45 rpm<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /71&nbsp;&nbsp;&nbsp; 71.29 rpm<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /76&nbsp;&nbsp;&nbsp; 76.59 rpm<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /78&nbsp;&nbsp;&nbsp; 78.26 rpm<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /80&nbsp;&nbsp;&nbsp; 80 rpm</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; MD&nbsp;&nbsp;&nbsp;&nbsp; MiniDisc<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /A&nbsp;&nbsp;&nbsp;
 * Analogue transfer from media</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; DAT&nbsp;&nbsp;&nbsp; DAT<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /A&nbsp;&nbsp;&nbsp; Analogue transfer from media<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /1&nbsp;&nbsp;&nbsp; standard, 48 kHz/16 bits, linear<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /2&nbsp;&nbsp;&nbsp; mode 2,
 * 32 kHz/16 bits, linear<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /3&nbsp;&nbsp;&nbsp; mode 3, 32 kHz/12 bits, non-linear, low speed<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /4&nbsp;&nbsp;&nbsp; mode 4, 32 kHz/12 bits, 4 channels<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /5&nbsp;&nbsp;&nbsp; mode 5, 44.1 kHz/16 bits, linear<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /6&nbsp;&nbsp;&nbsp; mode 6, 44.1 kHz/16 bits, 'wide track' play</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; DCC&nbsp;&nbsp;&nbsp; DCC<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /A&nbsp;&nbsp;&nbsp; Analogue
 * transfer from media</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; DVD&nbsp;&nbsp;&nbsp; DVD<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /A&nbsp;&nbsp;&nbsp; Analogue
 * transfer from media</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; TV&nbsp;&nbsp;&nbsp;&nbsp; Television<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /PAL&nbsp;&nbsp;&nbsp; PAL<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /NTSC&nbsp;&nbsp;
 * NTSC<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /SECAM&nbsp; SECAM</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; VID&nbsp;&nbsp;&nbsp; Video<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /PAL&nbsp;&nbsp;&nbsp; PAL<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /NTSC&nbsp;&nbsp; NTSC<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /SECAM&nbsp; SECAM<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /VHS&nbsp;&nbsp;&nbsp; VHS<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /SVHS&nbsp;&nbsp; S-VHS<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /BETA&nbsp;&nbsp; BETAMAX</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; RAD&nbsp;&nbsp;&nbsp; Radio<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /FM&nbsp;&nbsp; FM<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /AM&nbsp;&nbsp; AM<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /LW&nbsp;&nbsp; LW<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /MW&nbsp;&nbsp; MW</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; TEL&nbsp;&nbsp;&nbsp; Telephone<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /I&nbsp;&nbsp;&nbsp;
 * ISDN</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; MC&nbsp;&nbsp;&nbsp;&nbsp; MC (normal cassette)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /4&nbsp;&nbsp;&nbsp; 4.75 cm/s (normal speed for a two sided cassette)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /9&nbsp;&nbsp;&nbsp; 9.5 cm/s<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /I&nbsp;&nbsp;&nbsp;
 * Type I cassette (ferric/normal)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /II&nbsp;&nbsp; Type II cassette (chrome)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /III&nbsp; Type III cassette (ferric chrome)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /IV&nbsp;&nbsp; Type IV cassette
 * (metal)</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp; REE&nbsp;&nbsp;&nbsp; Reel<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /9&nbsp;&nbsp;&nbsp; 9.5 cm/s<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /19&nbsp;&nbsp; 19 cm/s<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /38&nbsp;&nbsp; 38 cm/s<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /76&nbsp;&nbsp; 76 cm/s<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /I&nbsp;&nbsp;&nbsp; Type I cassette (ferric/normal)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /II&nbsp;&nbsp; Type II cassette (chrome)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /III&nbsp; Type III cassette (ferric chrome)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /IV&nbsp;&nbsp; Type IV cassette
 * (metal)</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTMED extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTMED object.
     */
    public FrameBodyTMED() {
        super();
    }

    /**
     * Creates a new FrameBodyTMED object.
     */
    public FrameBodyTMED(final FrameBodyTMED body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTMED object.
     */
    public FrameBodyTMED(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTMED object.
     */
    public FrameBodyTMED(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TMED";
    }
}