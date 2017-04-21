package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.30.&nbsp;&nbsp; Audio seek point index</h3>
 * <p/>
 * <p>&nbsp;&nbsp; Audio files with variable bit rates are intrinsically difficult to<br> &nbsp;&nbsp; deal with in the
 * case of seeking within the file. The ASPI frame<br> &nbsp;&nbsp; makes seeking easier by providing a list a seek
 * points within the<br> &nbsp;&nbsp; audio file. The seek points are a fractional offset within the audio<br>
 * &nbsp;&nbsp; data, providing a starting point from which to find an appropriate<br>
 * <p/>
 * &nbsp;&nbsp; point to start decoding. The presence of an ASPI frame requires the<br> &nbsp;&nbsp; existence of a TLEN
 * frame, indicating the duration of the file in<br> &nbsp;&nbsp; milliseconds. There may only be one 'audio seek point
 * index' frame in<br> &nbsp;&nbsp; a tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Seek Point Index', ID: &quot;ASPI&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Indexed data start (S)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Indexed data length (L)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Number of
 * index points (N)&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Bits per index point (b)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; Then for every index point the following data is included;</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Fraction at index (Fi)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx
 * (xx)</p>
 * <p/>
 * <p>&nbsp;&nbsp; 'Indexed data start' is a byte offset from the beginning of the file.<br> &nbsp;&nbsp; 'Indexed data
 * length' is the byte length of the audio data being<br> &nbsp;&nbsp; indexed. 'Number of index points' is the number
 * of index points, as<br> &nbsp;&nbsp; the name implies. The recommended number is 100. 'Bits per index<br>
 * &nbsp;&nbsp; point' is 8 or 16, depending on the chosen precision. 8 bits works<br>
 * <p/>
 * &nbsp;&nbsp; well for short files (less than 5 minutes of audio), while 16 bits is<br> &nbsp;&nbsp; advantageous for
 * long files. 'Fraction at index' is the numerator of<br> &nbsp;&nbsp; the fraction representing a relative position in
 * the data. The<br> &nbsp;&nbsp; denominator is 2 to the power of b.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Here are the algorithms to be used in the calculation. The known data<br> &nbsp;&nbsp; must be the
 * offset of the start of the indexed data (S), the offset<br> &nbsp;&nbsp; of the end of the indexed data (E), the
 * number of index points (N),<br> &nbsp;&nbsp; the offset at index i (Oi). We calculate the fraction at index i<br>
 * &nbsp;&nbsp; (Fi).</p>
 * <p/>
 * <p>&nbsp;&nbsp; Oi is the offset of the frame whose start is soonest after the point<br> &nbsp;&nbsp; for which the
 * time offset is (i/N * duration).</p>
 * <p/>
 * <p>&nbsp;&nbsp; The frame data should be calculated as follows:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Fi = Oi/L * 2^b&nbsp;&nbsp;&nbsp; (rounded down to the nearest integer)</p>
 * <p/>
 * <p>&nbsp;&nbsp; Offset calculation should be calculated as follows from data in the<br> &nbsp;&nbsp; frame:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Oi = (Fi/2^b)*L&nbsp;&nbsp;&nbsp; (rounded up to the nearest integer)<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class FrameBodyASPI extends AbstractID3v2FrameBody {

    private short[] fraction = null;
    private int bitsPerPoint = 0;
    private int dataLength = 0;
    private int dataStart = 0;
    private int indexPoints = 0;

    /**
     * Creates a new FrameBodyASPI object.
     */
    public FrameBodyASPI() {
        super();
    }

    /**
     * Creates a new FrameBodyASPI object.
     */
    public FrameBodyASPI(final FrameBodyASPI copyObject) {
        super(copyObject);
        fraction = (short[]) copyObject.fraction.clone();
        bitsPerPoint = copyObject.bitsPerPoint;
        dataLength = copyObject.dataLength;
        dataStart = copyObject.dataStart;
        indexPoints = copyObject.indexPoints;
    }

    /**
     * Creates a new FrameBodyASPI object.
     */
    public FrameBodyASPI(final int dataStart,
                         final int dataLength,
                         final int indexPoints,
                         final int bitsPerPoint,
                         final short[] fraction) {
        super();
        this.dataStart = dataStart;
        this.dataLength = dataLength;
        this.indexPoints = indexPoints;
        this.bitsPerPoint = bitsPerPoint;
        this.fraction = new short[fraction.length];
        System.arraycopy(fraction, 0, this.fraction, 0, fraction.length);
    }

    /**
     * Creates a new FrameBodyASPI object.
     */
    public FrameBodyASPI(final RandomAccessFile file) throws IOException, InvalidTagException {
        super();
        read(file);
    }

    public String getIdentifier() {
        return "ASPI";
    }

    public int getSize() {
        return 4 + 4 + 2 + 1 + fraction.length << 1;
    }

    /**
     * This method is not yet supported.
     *
     * @throws UnsupportedOperationException This method is not yet supported
     */
    public void equals() {
        // todo Implement this java.lang.Object method
        throw new UnsupportedOperationException("Method equals() not yet implemented.");
    }

    protected void setupObjectList() {
//        throw new UnsupportedOperationException();
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        final int size = readHeader(file);
        if (size == 0) {
            throw new InvalidTagException("Empty Frame");
        }
        dataStart = file.readInt();
        dataLength = file.readInt();
        indexPoints = (int) file.readShort();
        bitsPerPoint = (int) file.readByte();
        fraction = new short[indexPoints];
        for (int i = 0; i < indexPoints; i++) {
            if (bitsPerPoint == 8) {
                fraction[i] = (short) file.readByte();
            } else if (bitsPerPoint == 16) {
                fraction[i] = file.readShort();
            } else {
                throw new InvalidTagException("ASPI bits per point wasn't 8 or 16");
            }
        }
    }

    public String toString() {
        return getIdentifier() + ' ' + this
                .dataStart + ' ' + this
                .dataLength + ' ' + this
                .indexPoints + ' ' + this
                .bitsPerPoint + ' ' + this.fraction
                .toString();
    }

    public void write(final RandomAccessFile file) throws IOException {
        writeHeader(file, getSize());
        file.writeInt(dataStart);
        file.writeInt(dataLength);
        file.writeShort(indexPoints);
        file.writeByte(16);
        for (int i = 0; i < indexPoints; i++) {
            file.writeShort((int) fraction[i]);
        }
    }
}