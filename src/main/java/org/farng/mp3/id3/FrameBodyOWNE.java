package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringDate;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.23.&nbsp;&nbsp; Ownership frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; The ownership frame might be used as a reminder of a made transaction<br> &nbsp;&nbsp; or, if signed,
 * as proof. Note that the &quot;USER&quot; and &quot;TOWN&quot; frames are<br> &nbsp;&nbsp; good to use in conjunction
 * with this one. The frame begins, after the<br>
 * <p/>
 * &nbsp;&nbsp; frame ID, size and encoding fields, with a 'price paid' field. The<br> &nbsp;&nbsp; first three
 * characters of this field contains the currency used for<br> &nbsp;&nbsp; the transaction, encoded according to ISO
 * 4217 [ISO-4217] alphabetic<br> &nbsp;&nbsp; currency code. Concatenated to this is the actual price paid, as a<br>
 * &nbsp;&nbsp; numerical string using &quot;.&quot; as the decimal separator. Next is an 8<br>
 * <p/>
 * &nbsp;&nbsp; character date string (YYYYMMDD) followed by a string with the name<br> &nbsp;&nbsp; of the seller as
 * the last field in the frame. There may only be one<br> &nbsp;&nbsp; &quot;OWNE&quot; frame in a tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Ownership frame', ID: &quot;OWNE&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Price
 * paid&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp; Date of
 * purch.&nbsp;&nbsp;&nbsp; &lt;text string&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Seller&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string
 * according to encoding&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyOWNE extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyOWNE object.
     */
    public FrameBodyOWNE() {
        super();
    }

    /**
     * Creates a new FrameBodyOWNE object.
     */
    public FrameBodyOWNE(final FrameBodyOWNE body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyOWNE object.
     */
    public FrameBodyOWNE(final byte textEncoding,
                         final String pricePaid,
                         final String dateOfPurchase,
                         final String seller) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Price Paid", pricePaid);
        setObject("Date Of Purchase", dateOfPurchase);
        setObject("Seller", seller);
    }

    /**
     * Creates a new FrameBodyOWNE object.
     */
    public FrameBodyOWNE(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "OWNE";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringNullTerminated("Price Paid"));
        appendToObjectList(new ObjectStringDate("Date Of Purchase"));
        appendToObjectList(new ObjectStringSizeTerminated("Seller"));
    }
}