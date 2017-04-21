package org.farng.mp3.lyrics3;

import org.farng.mp3.AbstractMP3Fragment;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.AbstractFrameBodyTextInformation;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.FrameBodyCOMM;
import org.farng.mp3.id3.FrameBodySYLT;
import org.farng.mp3.id3.FrameBodyUSLT;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <TABLE border=0> <TBODY> <TR>
 * <p/>
 * <TD class=h2>Defined fields</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR vAlign=top> <TD> <P>The following
 * list is a list of currently defined field IDs. More fields might be added if needed on newer versions of the Lyrics3
 * v2.00 specifications. Unknown fields should be ignored.</P> <TABLE> <TBODY>
 * <p/>
 * <TR> <TD><U>ID</U></TD> <TD><U>Max size</U></TD> <TD><U>Description</U></TD></TR> <TR vAlign=top> <TD><B>IND</B></TD>
 * <TD>00002</TD>
 * <p/>
 * <TD>Indications field. This is always two characters big in v2.00, but might be bigger in a future standard. The
 * first byte indicates wether or not a lyrics field is present. "1" for present and "0" for otherwise. The second
 * character indicates if there is a timestamp in the lyrics. Again "1" for yes and "0" for no.</TD></TR> <TR
 * vAlign=top> <TD><B>LYR</B></TD> <TD>99999</TD> <TD>Lyrics multi line text. Timestamps can be used anywhere in the
 * text in any order. Timestamp format is [mm:ss] (no spaces allowed in the timestamps).</TD></TR> <TR vAlign=top>
 * <TD><B>INF</B></TD>
 * <p/>
 * <TD>99999</TD> <TD>Additional information multi line text.</TD></TR> <TR vAlign=top> <TD><B>AUT</B></TD>
 * <TD>00250</TD> <TD>Lyrics/Music Author name.</TD></TR>
 * <p/>
 * <TR vAlign=top> <TD><B>EAL</B></TD> <TD>00250</TD> <TD>Extended Album name.</TD></TR> <TR vAlign=top>
 * <TD><B>EAR</B></TD> <TD>00250</TD>
 * <p/>
 * <TD>Extended Artist name.</TD></TR> <TR vAlign=top> <TD><B>ETT</B></TD> <TD>00250</TD> <TD>Extended Track
 * Title.</TD></TR> <TR vAlign=top> <TD><B>IMG</B></TD>
 * <p/>
 * <TD>99999</TD> <TD>Link to an image files (BMP or JPG format). Image lines include filename, description and
 * timestamp separated by delimiter - two ASCII chars 124 ("||"). Description and timestamp are optional, but if
 * timestamp is used, and there is no description, two delimiters ("||||") should be used between the filename and the
 * timestamp. Multiple images are allowed by using a [CR][LF] delimiter between each image line. No [CR][LF] is needed
 * after the last image line. Number of images is not limited (except by the field size).<BR><B>Filename</B> can be in
 * one of these formats: <UL> <LI>Filename only - when the image is located in the same path as the MP3 file (preferred,
 * since if you move the mp3 file this will still be correct) <LI>Relative Path + Filename - when the image is located
 * in a subdirectory below the MP3 file (i.e. images\cover.jpg) <LI>Full path + Filename - when the image is located in
 * a totally different path or drive. This will not work if the image is moved or drive letters has changed, and so
 * should be avoided if possible (i.e. c:\images\artist.jpg)</LI></UL><B>Description</B> can be up to 250 chars
 * long.<BR><B>Timestamp</B> must be formatted like the lyrics timestamp which is "[mm:ss]". If an image has a
 * timestamp, then the visible image will automatically switch to that image on the timestamp play time, just the same
 * as the selected lyrics line is switched based on timestamps.</TD></TR></TBODY></TABLE>
 * <p/>
 * </TD></TR></TBODY></TABLE> *
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class Lyrics3v2Field extends AbstractMP3Fragment {

    /**
     * Creates a new Lyrics3v2Field object.
     */
    public Lyrics3v2Field() {
        // base empty constructor
    }

    /**
     * Creates a new Lyrics3v2Field object.
     */
    public Lyrics3v2Field(final Lyrics3v2Field copyObject) {
        super(copyObject);
    }

    /**
     * Creates a new Lyrics3v2Field object.
     */
    public Lyrics3v2Field(final AbstractLyrics3v2FieldBody body) {
        super(body);
    }

    /**
     * Creates a new Lyrics3v2Field object.
     */
    public Lyrics3v2Field(final AbstractID3v2Frame frame) throws TagException {
        final AbstractFrameBodyTextInformation textFrame;
        final String text;
        final String frameIdentifier = frame.getIdentifier();
        if (frameIdentifier.startsWith("USLT")) {
            this.setBody(new FieldBodyLYR(""));
            ((FieldBodyLYR) this.getBody()).addLyric((FrameBodyUSLT) frame.getBody());
        } else if (frameIdentifier.startsWith("SYLT")) {
            this.setBody(new FieldBodyLYR(""));
            ((FieldBodyLYR) this.getBody()).addLyric((FrameBodySYLT) frame.getBody());
        } else if (frameIdentifier.startsWith("COMM")) {
            text = new String(((FrameBodyCOMM) frame.getBody()).getText());
            this.setBody(new FieldBodyINF(text));
        } else if (frameIdentifier.equals("TCOM")) {
            textFrame = (AbstractFrameBodyTextInformation) frame.getBody();
            this.setBody(new FieldBodyAUT(""));
            if ((textFrame != null) && (((textFrame.getText())).length() > 0)) {
                this.setBody(new FieldBodyAUT((textFrame.getText())));
            }
        } else if (frameIdentifier.equals("TALB")) {
            textFrame = (AbstractFrameBodyTextInformation) frame.getBody();
            if ((textFrame != null) && ((textFrame.getText()).length() > 0)) {
                this.setBody(new FieldBodyEAL((textFrame.getText())));
            }
        } else if (frameIdentifier.equals("TPE1")) {
            textFrame = (AbstractFrameBodyTextInformation) frame.getBody();
            if ((textFrame != null) && ((textFrame.getText()).length() > 0)) {
                this.setBody(new FieldBodyEAR((textFrame.getText())));
            }
        } else if (frameIdentifier.equals("TIT2")) {
            textFrame = (AbstractFrameBodyTextInformation) frame.getBody();
            if ((textFrame != null) && ((textFrame.getText()).length() > 0)) {
                this.setBody(new FieldBodyETT((textFrame.getText())));
            }
        } else {
            throw new TagException("Cannot create Lyrics3v2 field from given ID3v2 frame");
        }
    }

    /**
     * Creates a new Lyrics3v2Field object.
     */
    public Lyrics3v2Field(final RandomAccessFile file) throws InvalidTagException, IOException {
        this.read(file);
    }

    public String getIdentifier() {
        if (this.getBody() == null) {
            return "";
        }
        return this.getBody().getIdentifier();
    }

    public int getSize() {
        return this.getBody().getSize() + 5 + getIdentifier().length();
    }

    public void read(final RandomAccessFile file) throws InvalidTagException, IOException {
        final byte[] buffer = new byte[6];

        // lets scan for a non-zero byte;
        long filePointer;
        byte b;
        do {
            filePointer = file.getFilePointer();
            b = file.readByte();
        } while (b == 0);
        file.seek(filePointer);

        // read the 3 character ID
        file.read(buffer, 0, 3);
        final String identifier = new String(buffer, 0, 3);

        // is this a valid identifier?
        if (TagUtility.isLyrics3v2FieldIdentifier(identifier) == false) {
            throw new InvalidTagException(identifier + " is not a valid ID3v2.4 frame");
        }
        this.setBody(readBody(identifier, file));
    }

    public String toString() {
        if (this.getBody() == null) {
            return "";
        }
        return this.getBody().toString();
    }

    public void write(final RandomAccessFile file) throws IOException {
        if (((this.getBody()).getSize() > 0) || TagOptionSingleton.getInstance().isLyrics3SaveEmptyField()) {
            final byte[] buffer = new byte[3];
            final String str = getIdentifier();
            for (int i = 0; i < str.length(); i++) {
                buffer[i] = (byte) str.charAt(i);
            }
            file.write(buffer, 0, str.length());
            this.getBody().write(file);
        }
    }

    private AbstractLyrics3v2FieldBody readBody(final String identifier, final RandomAccessFile file)
            throws InvalidTagException, IOException {
        final AbstractLyrics3v2FieldBody newBody;
        if (identifier.equals("AUT")) {
            newBody = new FieldBodyAUT(file);
        } else if (identifier.equals("EAL")) {
            newBody = new FieldBodyEAL(file);
        } else if (identifier.equals("EAR")) {
            newBody = new FieldBodyEAR(file);
        } else if (identifier.equals("ETT")) {
            newBody = new FieldBodyETT(file);
        } else if (identifier.equals("IMG")) {
            newBody = new FieldBodyIMG(file);
        } else if (identifier.equals("IND")) {
            newBody = new FieldBodyIND(file);
        } else if (identifier.equals("INF")) {
            newBody = new FieldBodyINF(file);
        } else if (identifier.equals("LYR")) {
            newBody = new FieldBodyLYR(file);
        } else {
            newBody = new FieldBodyUnsupported(file);
        }
        return newBody;
    }
}