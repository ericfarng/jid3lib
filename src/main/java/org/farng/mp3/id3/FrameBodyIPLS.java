package org.farng.mp3.id3;

/**
 * <h3>4.4.Involved people list</h3>
 * <p/>
 * <p class=t> Since there might be a lot of people contributing to an audio file in various ways, such as musicians and
 * technicians, the 'Text information frames' are often insufficient to list everyone involved in a project. The
 * 'Involved people list' is a frame containing the names of those involved, and how they were involved. The body simply
 * contains a terminated string with the involvement directly followed by a terminated string with the involvee followed
 * by a new involvement and so on. There may only be one "IPLS" frame in each tag. </p>
 * <p/>
 * <p><center> <table border=0>
 * <p/>
 * <tr><td colspan=2> &lt;Header for 'Involved people list', ID: "IPLS"&gt;</td></tr> <tr><td>Text
 * encoding</td><td>$xx</td></tr> <tr><td>People list strings</td><td>&lt;text strings according to
 * encoding&gt;</td></tr> </table> </center>
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class FrameBodyIPLS extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyIPLS object.
     */
    public FrameBodyIPLS() {
        super();
    }

    /**
     * Creates a new FrameBodyIPLS object.
     */
    public FrameBodyIPLS(final FrameBodyIPLS body) {
        super(body);
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("This frame has not been implemented.");
    }
}