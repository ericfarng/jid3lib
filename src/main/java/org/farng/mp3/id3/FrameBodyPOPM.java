package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectNumberVariableLength;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.17.&nbsp;&nbsp; Popularimeter</h3>
 * <p/>
 * <p>&nbsp;&nbsp; The purpose of this frame is to specify how good an audio file is.<br> &nbsp;&nbsp; Many interesting
 * applications could be found to this frame such as a<br> &nbsp;&nbsp; playlist that features better audio files more
 * often than others or<br>
 * <p/>
 * &nbsp;&nbsp; it could be used to profile a person's taste and find other 'good'<br> &nbsp;&nbsp; files by comparing
 * people's profiles. The frame contains the email<br> &nbsp;&nbsp; address to the user, one rating byte and a four byte
 * play counter,<br> &nbsp;&nbsp; intended to be increased with one for every time the file is played.<br> &nbsp;&nbsp;
 * The email is a terminated string. The rating is 1-255 where 1 is<br>
 * <p/>
 * &nbsp;&nbsp; worst and 255 is best. 0 is unknown. If no personal counter is wanted<br> &nbsp;&nbsp; it may be
 * omitted. When the counter reaches all one's, one byte is<br> &nbsp;&nbsp; inserted in front of the counter thus
 * making the counter eight bits<br> &nbsp;&nbsp; bigger in the same away as the play counter (&quot;PCNT&quot;). There
 * may be<br>
 * <p/>
 * &nbsp;&nbsp; more than one &quot;POPM&quot; frame in each tag, but only one with the same<br> &nbsp;&nbsp; email
 * address.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Popularimeter', ID: &quot;POPM&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Email to user&nbsp;&nbsp; &lt;text string&gt; $00<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Rating&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Counter&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx (xx ...)<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyPOPM extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyPOPM object.
     */
    public FrameBodyPOPM() {
        super();
    }

    /**
     * Creates a new FrameBodyPOPM object.
     */
    public FrameBodyPOPM(final FrameBodyPOPM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPOPM object.
     */
    public FrameBodyPOPM(final String emailToUser, final byte rating, final long counter) {
        setObject("Email to User", emailToUser);
        setObject("Rating", new Byte(rating));
        setObject("Counter", new Long(counter));
    }

    /**
     * Creates a new FrameBodyPOPM object.
     */
    public FrameBodyPOPM(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setEmailToUser(final String description) {
        setObject("Email to User", description);
    }

    public String getEmailToUser() {
        return (String) getObject("Email to User");
    }

    public String getIdentifier() {
        return "POPM" + ((char) 0) + getEmailToUser();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Email to User"));
        appendToObjectList(new ObjectNumberFixedLength("Rating", 1));
        appendToObjectList(new ObjectNumberVariableLength("Counter", 1));
    }
}