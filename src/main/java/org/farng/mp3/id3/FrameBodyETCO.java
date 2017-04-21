package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.AbstractMP3Object;
import org.farng.mp3.object.ObjectGroupRepeated;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectNumberHashMap;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.5.&nbsp;&nbsp; Event timing codes</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame allows synchronisation with key events in the audio. The<br> &nbsp;&nbsp; header is:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Event timing codes', ID: &quot;ETCO&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Time stamp format&nbsp;&nbsp;&nbsp; $xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; Where time stamp format is:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; Absolute time, 32 bit sized, using MPEG [MPEG] frames as unit<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; Absolute time, 32 bit sized, using milliseconds as unit</p>
 * <p/>
 * <p>&nbsp;&nbsp; Absolute time means that every stamp contains the time from the<br> &nbsp;&nbsp; beginning of the
 * file.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Followed by a list of key events in the following format:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Type of event&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Time stamp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx (xx ...)</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'Time stamp' is set to zero if directly at the beginning of the<br> &nbsp;&nbsp; sound or after
 * the previous event. All events MUST be sorted in<br> &nbsp;&nbsp; chronological order. The type of event is as
 * follows:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $00&nbsp; padding (has no meaning)<br> &nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; end of initial
 * silence<br> &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; intro start<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $03&nbsp; main part start<br> &nbsp;&nbsp;&nbsp;&nbsp; $04&nbsp; outro start<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $05&nbsp; outro end<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $06&nbsp; verse start<br> &nbsp;&nbsp;&nbsp;&nbsp; $07&nbsp; refrain start<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $08&nbsp; interlude start<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $09&nbsp; theme start<br> &nbsp;&nbsp;&nbsp;&nbsp; $0A&nbsp; variation start<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $0B&nbsp; key change<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $0C&nbsp; time change<br> &nbsp;&nbsp;&nbsp;&nbsp; $0D&nbsp; momentary unwanted noise (Snap,
 * Crackle &amp; Pop)<br> &nbsp;&nbsp;&nbsp;&nbsp; $0E&nbsp; sustained noise<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $0F&nbsp; sustained noise end<br> &nbsp;&nbsp;&nbsp;&nbsp; $10&nbsp; intro end<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $11&nbsp; main part end<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $12&nbsp; verse end<br> &nbsp;&nbsp;&nbsp;&nbsp; $13&nbsp; refrain end<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; $14&nbsp; theme end<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $15&nbsp; profanity<br> &nbsp;&nbsp;&nbsp;&nbsp; $16&nbsp; profanity end</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $17-$DF&nbsp; reserved for future use</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $E0-$EF&nbsp; not predefined synch 0-F</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $F0-$FC&nbsp; reserved for future use</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $FD&nbsp; audio end (start of silence)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $FE&nbsp; audio file ends<br> &nbsp;&nbsp;&nbsp;&nbsp; $FF&nbsp; one more byte of events
 * follows (all the following bytes with<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; the value $FF have
 * the same function)</p>
 * <p/>
 * <p>&nbsp;&nbsp; Terminating the start events such as &quot;intro start&quot; is OPTIONAL. The<br> &nbsp;&nbsp; 'Not
 * predefined synch's ($E0-EF) are for user events. You might want<br> &nbsp;&nbsp; to synchronise your music to
 * something, like setting off an explosion<br> &nbsp;&nbsp; on-stage, activating a screensaver etc.</p>
 * <p/>
 * <p>&nbsp;&nbsp; There may only be one &quot;ETCO&quot; frame in each tag.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyETCO extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyETCO object.
     */
    public FrameBodyETCO() {
        super();
    }

    /**
     * Creates a new FrameBodyETCO object.
     */
    public FrameBodyETCO(final FrameBodyETCO body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyETCO object.
     */
    public FrameBodyETCO(final byte timeStampFormat, final byte event, final int timeStamp) {
        setObject("Time Stamp Format", new Long(timeStampFormat));
        this.addGroup(event, timeStamp);
    }

    /**
     * Creates a new FrameBodyETCO object.
     */
    public FrameBodyETCO(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "ETCO" + ((char) 0) + getOwner();
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    public void getOwner(final String description) {
        setObject("Owner", description);
    }

    public void addGroup(final byte event, final int timeStamp) {
        final ObjectGroupRepeated group = (ObjectGroupRepeated) this.getObject("Data");
        final AbstractMP3Object ev = new ObjectNumberHashMap("Type Of Event", 1);
        final AbstractMP3Object ts = new ObjectNumberFixedLength("Time Stamp", 4);
        group.addObject(ev);
        group.addObject(ts);
        setObject("Data", group);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Time Stamp Format", 1));
        final ObjectGroupRepeated group = new ObjectGroupRepeated("Data");
        group.addProperty(new ObjectNumberHashMap("Type Of Event", 1));
        group.addProperty(new ObjectNumberFixedLength("Time Stamp", 4));
        appendToObjectList(group);
    }
}