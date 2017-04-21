package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.12.Relative volume adjustment</h3>
 * <p/>
 * <p class=t> This is a more subjective function than the previous ones. It allows the user to say how much he wants to
 * increase/decrease the volume on each channel while the file is played. The purpose is to be able to align all files
 * to a reference volume, so that you don't have to change the volume constantly. This frame may also be used to balance
 * adjust the audio. If the volume peak levels are known then this could be described with the 'Peak volume right' and
 * 'Peak volume left' field. If Peakvolume is not known these fields could be left zeroed or, if no other data follows,
 * be completely omitted. There may only be one "RVAD" frame in each tag.
 * <p/>
 * </p>
 * <p/>
 * <p><center> <table border=0> <tr><td colspan=2>&lt;Header for 'Relative volume adjustment', ID: "RVAD"&gt;</td></tr>
 * <tr><td>Increment/decrement</td><td>%00xxxxxx</td></tr> <tr><td>Bits used for volume descr.</td><td>$xx</td></tr>
 * <tr><td>Relative volume change, right</td><td>$xx xx (xx ...)</td></tr>
 * <p/>
 * <tr><td>Relative volume change, left</td><td>$xx xx (xx ...)</td></tr> <tr><td>Peak volume right</td><td>$xx xx (xx
 * ...)</td></tr> <tr><td>Peak volume left</td><td>$xx xx (xx ...)</td></tr> </table> </center>
 * <p/>
 * <p class=t>
 * <p/>
 * In the increment/decrement field bit 0 is used to indicate the right channel and bit 1 is used to indicate the left
 * channel. 1 is increment and 0 is decrement. </p>
 * <p/>
 * <p class=t> The 'bits used for volume description' field is normally $10 (16 bits) for <a href="#MPEG">MPEG</a> 2
 * layer I, II and III and MPEG 2.5. This value may not be $00. The volume is always represented with whole bytes,
 * padded in the beginning (highest bits) when 'bits used for volume description' is not a multiple of eight. </p>
 * <p/>
 * <p class=t> This datablock is then optionally followed by a volume definition for the left and right back channels.
 * If this information is appended to the frame the first two channels will be treated as front channels. In the
 * increment/decrement field bit 2 is used to indicate the right back channel and bit 3 for the left back channel. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>Relative volume change, right back</td><td>$xx xx (xx ...)</td></tr>
 * <tr><td>Relative volume change, left back</td><td>$xx xx (xx ...)</td></tr> <tr><td>Peak volume right
 * back</td><td>$xx xx (xx ...)</td></tr> <tr><td>Peak volume left back</td><td>$xx xx (xx ...)</td></tr>
 * <p/>
 * </table> </center>
 * <p/>
 * <p class=t> If the center channel adjustment is present the following is appended to the existing frame, after the
 * left and right back channels. The center channel is represented by bit 4 in the increase/decrease field. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>Relative volume change, center</td><td>$xx xx (xx ...)</td></tr>
 * <tr><td>Peak volume center</td><td>$xx xx (xx ...)</td></tr>
 * <p/>
 * </table> </center>
 * <p/>
 * <p class=t> If the bass channel adjustment is present the following is appended to the existing frame, after the
 * center channel. The bass channel is represented by bit 5 in the increase/decrease field. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>Relative volume change, bass</td><td>$xx xx (xx ...)</td></tr>
 * <tr><td>Peak volume bass</td><td>$xx xx (xx ...)</td></tr>
 * <p/>
 * </table> </center>
 *
 * @author Eric Farng`
 * @version $Revision: 1.5 $
 */
public class FrameBodyRVAD extends AbstractID3v2FrameBody {

    byte bytesUsed = 16;
    byte increment = 0;
    long peakBass = 0;
    long peakCenter = 0;
    long peakLeft = 0;
    long peakLeftBack = 0;
    long peakRight = 0;
    long peakRightBack = 0;
    long relativeBass = 0;
    long relativeCenter = 0;
    long relativeLeft = 0;
    long relativeLeftBack = 0;
    long relativeRight = 0;
    long relativeRightBack = 0;

    /**
     * Creates a new FrameBodyRVAD object.
     */
    public FrameBodyRVAD() {
        super();
    }

    /**
     * Creates a new FrameBodyRVAD object.
     */
    public FrameBodyRVAD(final FrameBodyRVAD copyObject) {
        super(copyObject);
        this.bytesUsed = copyObject.bytesUsed;
        this.increment = copyObject.increment;
        this.peakBass = copyObject.peakBass;
        this.peakCenter = copyObject.peakCenter;
        this.peakLeft = copyObject.peakLeft;
        this.peakLeftBack = copyObject.peakLeftBack;
        this.peakRight = copyObject.peakRight;
        this.peakRightBack = copyObject.peakRightBack;
        this.relativeBass = copyObject.relativeBass;
        this.relativeCenter = copyObject.relativeCenter;
        this.relativeLeft = copyObject.relativeLeft;
        this.relativeLeftBack = copyObject.relativeLeftBack;
        this.relativeRight = copyObject.relativeRight;
        this.relativeRightBack = copyObject.relativeRightBack;
    }

    /**
     * Creates a new FrameBodyRVAD object.
     */
    public FrameBodyRVAD(final byte increment,
                         final byte bitsUsed,
                         final long relativeRight,
                         final long relativeLeft,
                         final long peakRight,
                         final long peakLeft,
                         final long relativeRightBack,
                         final long relativeLeftBack,
                         final long peakRightBack,
                         final long peakLeftBack,
                         final long relativeCenter,
                         final long peakCenter,
                         final long relativeBass,
                         final long peakBass) {
        this.increment = increment;
        this.bytesUsed = (byte) (((bitsUsed - 1) / 8) + 1); // convert to bytes.
        this.relativeRight = relativeRight;
        this.relativeLeft = relativeLeft;
        this.peakRight = peakRight;
        this.peakLeft = peakLeft;
        this.relativeRightBack = relativeRightBack;
        this.relativeLeftBack = relativeLeftBack;
        this.peakRightBack = peakRightBack;
        this.peakLeftBack = peakLeftBack;
        this.relativeCenter = relativeCenter;
        this.peakCenter = peakCenter;
        this.relativeBass = relativeBass;
        this.peakBass = peakBass;
    }

    /**
     * Creates a new FrameBodyRVAD object.
     */
    public FrameBodyRVAD(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "RVAD";
    }

    public int getSize() {
        int size = 2 + (this.bytesUsed * 4);
        if ((this.relativeRightBack != 0) ||
            (this.relativeLeftBack != 0) ||
            (this.peakRightBack != 0) ||
            (this.peakLeftBack != 0)) {
            size += (this.bytesUsed * 4);
        }
        if ((this.relativeCenter != 0) || (this.peakCenter != 0)) {
            size += (this.bytesUsed * 2);
        }
        if ((this.relativeBass != 0) || (this.peakBass != 0)) {
            size += (this.bytesUsed * 2);
        }
        return size;
    }

    /**
     * This method is not yet supported.
     *
     * @throws java.lang.UnsupportedOperationException
     *          This method is not yet supported
     */
    public void equals() {
        //todo Implement this java.lang.Object method
        throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
    }

    protected void setupObjectList() {
        throw new UnsupportedOperationException();
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        final int size;
        int offset = 0;
        final byte[] buffer;
        size = readHeader(file);
        buffer = new byte[size];
        file.read(buffer);
        this.increment = buffer[offset++];
        this.bytesUsed = (byte) (((buffer[offset++] - 1) / 8 * 8) + 1);
        for (int i = 0; i < this.bytesUsed; i++) {
            this.relativeRight = (this.relativeRight << 8) + buffer[i + offset];
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            this.relativeLeft = (this.relativeLeft << 8) + buffer[i + offset];
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            this.peakRight = (this.peakRight << 8) + buffer[i + offset];
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            this.peakLeft = (this.peakLeft << 8) + buffer[i + offset];
        }
        offset += this.bytesUsed;
        if (size > (2 + (this.bytesUsed * 4))) {
            for (int i = 0; i < this.bytesUsed; i++) {
                this.relativeRightBack = (this.relativeRightBack << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                this.relativeLeftBack = (this.relativeLeftBack << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                this.peakRightBack = (this.peakRightBack << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                this.peakLeftBack = (this.peakLeftBack << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
        }
        if (size > (2 + (this.bytesUsed * 8))) {
            for (int i = 0; i < this.bytesUsed; i++) {
                this.relativeCenter = (this.relativeCenter << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                this.peakCenter = (this.peakCenter << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
        }
        if (size > (2 + (this.bytesUsed * 10))) {
            for (int i = 0; i < this.bytesUsed; i++) {
                this.relativeBass = (this.relativeBass << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                this.peakBass = (this.peakBass << 8) + buffer[i + offset];
            }
            offset += this.bytesUsed;
        }
    }

    public String toString() {
        return this
                .increment +
                           " " +
                           (this.bytesUsed * 8) +
                           " " +
                           // convert back to bits
                           this
                                   .relativeRight +
                                                  " " +
                                                  this
                                                          .relativeLeft +
                                                                        " " +
                                                                        this
                                                                                .peakRight +
                                                                                           " " +
                                                                                           this
                                                                                                   .peakLeft +
                                                                                                             " " +
                                                                                                             this
                                                                                                                     .relativeRightBack +
                                                                                                                                        " " +
                                                                                                                                        this
                                                                                                                                                .relativeLeftBack +
                                                                                                                                                                  " " +
                                                                                                                                                                  this
                                                                                                                                                                          .peakRightBack +
                                                                                                                                                                                         " " +
                                                                                                                                                                                         this
                                                                                                                                                                                                 .peakLeftBack +
                                                                                                                                                                                                               " " +
                                                                                                                                                                                                               this
                                                                                                                                                                                                                       .relativeCenter +
                                                                                                                                                                                                                                       " " +
                                                                                                                                                                                                                                       this
                                                                                                                                                                                                                                               .peakCenter +
                                                                                                                                                                                                                                                           " " +
                                                                                                                                                                                                                                                           this
                                                                                                                                                                                                                                                                   .relativeBass +
                                                                                                                                                                                                                                                                                 " " +
                                                                                                                                                                                                                                                                                 this
                                                                                                                                                                                                                                                                                         .peakBass;
    }

    public void write(final RandomAccessFile file) throws IOException {
        final byte[] buffer;
        int offset = 0;
        writeHeader(file, this.getSize());
        buffer = new byte[this.getSize()];
        buffer[offset++] = this.increment;
        buffer[offset++] = this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            buffer[i + offset] = (byte) (this.relativeRight >> ((this.bytesUsed - i - 1) * 8));
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            buffer[i + offset] = (byte) (this.relativeLeft >> ((this.bytesUsed - i - 1) * 8));
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            buffer[i + offset] = (byte) (this.peakRight >> ((this.bytesUsed - i - 1) * 8));
        }
        offset += this.bytesUsed;
        for (int i = 0; i < this.bytesUsed; i++) {
            buffer[i + offset] = (byte) (this.peakLeft >> ((this.bytesUsed - i - 1) * 8));
        }
        offset += this.bytesUsed;
        if ((this.relativeRightBack != 0) ||
            (this.relativeLeftBack != 0) ||
            (this.peakRightBack != 0) ||
            (this.peakLeftBack != 0)) {
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.relativeRightBack >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.relativeLeftBack >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.peakRightBack >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.peakLeftBack >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
        }
        if ((this.relativeCenter != 0) || (this.peakCenter != 0)) {
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.relativeCenter >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.peakCenter >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
        }
        if ((this.relativeBass != 0) || (this.peakBass != 0)) {
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.relativeBass >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
            for (int i = 0; i < this.bytesUsed; i++) {
                buffer[i + offset] = (byte) (this.peakBass >> ((this.bytesUsed - i - 1) * 8));
            }
            offset += this.bytesUsed;
        }
        file.write(buffer);
    }
}