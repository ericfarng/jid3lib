package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringDate;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.24.&nbsp;&nbsp; Commercial frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame enables several competing offers in the same tag by<br>
 * <p/>
 * &nbsp;&nbsp; bundling all needed information. That makes this frame rather complex<br> &nbsp;&nbsp; but it's an
 * easier solution than if one tries to achieve the same<br> &nbsp;&nbsp; result with several frames. The frame begins,
 * after the frame ID,<br> &nbsp;&nbsp; size and encoding fields, with a price string field. A price is<br> &nbsp;&nbsp;
 * constructed by one three character currency code, encoded according<br>
 * <p/>
 * &nbsp;&nbsp; to ISO 4217 [ISO-4217] alphabetic currency code, followed by a<br> &nbsp;&nbsp; numerical value where
 * &quot;.&quot; is used as decimal separator. In the price<br> &nbsp;&nbsp; string several prices may be concatenated,
 * separated by a &quot;/&quot;<br> &nbsp;&nbsp; character, but there may only be one currency of each type.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The price string is followed by an 8 character date string in the<br> &nbsp;&nbsp; format YYYYMMDD,
 * describing for how long the price is valid. After<br> &nbsp;&nbsp; that is a contact URL, with which the user can
 * contact the seller,<br> &nbsp;&nbsp; followed by a one byte 'received as' field. It describes how the<br>
 * &nbsp;&nbsp; audio is delivered when bought according to the following list:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $00&nbsp; Other<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $01&nbsp; Standard CD album with other songs<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; Compressed
 * audio on CD<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $03&nbsp; File over the Internet<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $04&nbsp; Stream over the Internet<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $05&nbsp; As note sheets<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $06&nbsp; As note sheets in a book with other sheets<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $07&nbsp; Music on other media<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $08&nbsp; Non-musical merchandise</p>
 * <p/>
 * <p>&nbsp;&nbsp; Next follows a terminated string with the name of the seller followed<br> &nbsp;&nbsp; by a
 * terminated string with a short description of the product. The<br> &nbsp;&nbsp; last thing is the ability to include
 * a company logotype. The first of<br> &nbsp;&nbsp; them is the 'Picture MIME type' field containing information
 * about<br> &nbsp;&nbsp; which picture format is used. In the event that the MIME media type<br>
 * <p/>
 * &nbsp;&nbsp; name is omitted, &quot;image/&quot; will be implied. Currently only &quot;image/png&quot;<br>
 * &nbsp;&nbsp; and &quot;image/jpeg&quot; are allowed. This format string is followed by the<br> &nbsp;&nbsp; binary
 * picture data. This two last fields may be omitted if no<br>
 * <p/>
 * &nbsp;&nbsp; picture is attached. There may be more than one 'commercial frame' in<br> &nbsp;&nbsp; a tag, but no two
 * may be identical.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Commercial frame', ID: &quot;COMR&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Price string&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Valid until&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Contact URL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp; Received as&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Name of seller&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to encoding&gt; $00
 * (00)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to
 * encoding&gt; $00 (00)<br> &nbsp;&nbsp;&nbsp;&nbsp; Picture MIME type&nbsp; &lt;string&gt; $00<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Seller logo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyCOMR extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyCOMR object.
     */
    public FrameBodyCOMR() {
        super();
    }

    /**
     * Creates a new FrameBodyCOMR object.
     */
    public FrameBodyCOMR(final FrameBodyCOMR body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyCOMR object.
     */
    public FrameBodyCOMR(final byte textEncoding,
                         final String priceString,
                         final String validUntil,
                         final String contactUrl,
                         final byte recievedAs,
                         final String nameOfSeller,
                         final String description,
                         final String mimeType,
                         final byte[] sellerLogo) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Price String", priceString);
        setObject("Valid Until", validUntil);
        setObject("Contact URL", contactUrl);
        setObject("Recieved As", new Byte(recievedAs));
        setObject("Name Of Seller", nameOfSeller);
        setObject("Description", description);
        setObject("Picture MIME Type", mimeType);
        setObject("Seller Logo", sellerLogo);
    }

    /**
     * Creates a new FrameBodyCOMR object.
     */
    public FrameBodyCOMR(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        String str = "COMR";
        final java.util.Iterator iterator = getObjectListIterator();
        while (iterator.hasNext()) {
            str += (((char) 0) + getOwner());
        }
        return str;
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    public void getOwner(final String description) {
        setObject("Owner", description);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringNullTerminated("Price String"));
        appendToObjectList(new ObjectStringDate("Valid Until"));
        appendToObjectList(new ObjectStringNullTerminated("Contact URL"));
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.RECIEVED_AS, 1));
        appendToObjectList(new ObjectStringNullTerminated("Name Of Seller"));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectStringNullTerminated("Picture MIME Type"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Seller Logo"));
    }
}