package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Fragment;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagUtility;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This abstract class is each frame header inside a ID3v2 tag <P>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public abstract class AbstractID3v2Frame extends AbstractMP3Fragment {

    /**
     * Creates a new AbstractID3v2Frame object.
     */
    protected AbstractID3v2Frame() {
        super();
    }

    /**
     * Creates a new AbstractID3v2Frame object.
     */
    protected AbstractID3v2Frame(final AbstractID3v2FrameBody body) {
        super(body);
    }

    /**
     * Creates a new AbstractID3v2Frame object.
     */
    protected AbstractID3v2Frame(final AbstractID3v2Frame frame) {
        super(frame);
    }

    public String getIdentifier() {
        String identifier = "";
        if (getBody() != null) {
            identifier = getBody().getIdentifier();
        }
        return identifier;
    }

    public static boolean isValidID3v2FrameIdentifier(final String identifier) {
        char character;
        boolean valid = true;
        for (int i = 0; i < identifier.length(); i++) {
            character = identifier.charAt(i);
            if (character >= 'A' && character <= 'Z' || (character >= '0' && character <= '9')) {
                // nothing
            } else {
                valid = false;
            }
        }
        return valid;
    }

    public String toString() {
        String string = "";
        if (getBody() != null) {
            string = getBody().toString();
        }
        return string;
    }

    protected static AbstractID3v2FrameBody readBody(final String identifier, final RandomAccessFile file)
            throws IOException, InvalidTagException {
        final String frameIdentifier;
        final AbstractID3v2FrameBody newBody;
        if (TagUtility.isID3v2_2FrameIdentifier(identifier)) {
            frameIdentifier = TagUtility.convertFrameID2_2to2_4(identifier);
        } else {
            frameIdentifier = identifier;
        }
        if ("APIC".equals(frameIdentifier)) {
            newBody = new FrameBodyAPIC(file);
        } else if ("COMM".equals(frameIdentifier)) {
            newBody = new FrameBodyCOMM(file);
        } else if ("ENCR".equals(frameIdentifier)) {
            newBody = new FrameBodyENCR(file);
        } else if ("GEOB".equals(frameIdentifier)) {
            newBody = new FrameBodyGEOB(file);
        } else if ("GRID".equals(frameIdentifier)) {
            newBody = new FrameBodyGRID(file);
        } else if ("MCDI".equals(frameIdentifier)) {
            newBody = new FrameBodyMCDI(file);
        } else if ("PCNT".equals(frameIdentifier)) {
            newBody = new FrameBodyPCNT(file);
        } else if ("POPM".equals(frameIdentifier)) {
            newBody = new FrameBodyPOPM(file);
        } else if ("PRIV".equals(frameIdentifier)) {
            newBody = new FrameBodyPRIV(file);
        } else if ("RVAD".equals(frameIdentifier)) {
            newBody = new FrameBodyRVAD(file);
        } else if ("SYLT".equals(frameIdentifier)) {
            newBody = new FrameBodySYLT(file);
        } else if ("TALB".equals(frameIdentifier)) {
            newBody = new FrameBodyTALB(file);
        } else if ("TBPM".equals(frameIdentifier)) {
            newBody = new FrameBodyTBPM(file);
        } else if ("TCOM".equals(frameIdentifier)) {
            newBody = new FrameBodyTCOM(file);
        } else if ("TCON".equals(frameIdentifier)) {
            newBody = new FrameBodyTCON(file);
        } else if ("TCOP".equals(frameIdentifier)) {
            newBody = new FrameBodyTCOP(file);
        } else if ("TDAT".equals(frameIdentifier)) {
            newBody = new FrameBodyTDAT(file); // Deprecated
        } else if ("TDEN".equals(frameIdentifier)) {
            newBody = new FrameBodyTDEN(file);
        } else if ("TDLY".equals(frameIdentifier)) {
            newBody = new FrameBodyTDLY(file);
        } else if ("TDOR".equals(frameIdentifier)) {
            newBody = new FrameBodyTDOR(file);
        } else if ("TDRC".equals(frameIdentifier)) {
            newBody = new FrameBodyTDRC(file);
        } else if ("TDRL".equals(frameIdentifier)) {
            newBody = new FrameBodyTDRL(file);
        } else if ("TDTG".equals(frameIdentifier)) {
            newBody = new FrameBodyTDTG(file);
        } else if ("TENC".equals(frameIdentifier)) {
            newBody = new FrameBodyTENC(file);
        } else if ("TEXT".equals(frameIdentifier)) {
            newBody = new FrameBodyTEXT(file);
        } else if ("TFLT".equals(frameIdentifier)) {
            newBody = new FrameBodyTFLT(file);
        } else if ("TIME".equals(frameIdentifier)) {
            newBody = new FrameBodyTIME(file); // Deprecated
        } else if ("TIPL".equals(frameIdentifier)) {
            newBody = new FrameBodyTIPL(file);
        } else if ("TIT1".equals(frameIdentifier)) {
            newBody = new FrameBodyTIT1(file);
        } else if ("TIT2".equals(frameIdentifier)) {
            newBody = new FrameBodyTIT2(file);
        } else if ("TIT3".equals(frameIdentifier)) {
            newBody = new FrameBodyTIT3(file);
        } else if ("TKEY".equals(frameIdentifier)) {
            newBody = new FrameBodyTKEY(file);
        } else if ("TLAN".equals(frameIdentifier)) {
            newBody = new FrameBodyTLAN(file);
        } else if ("TLEN".equals(frameIdentifier)) {
            newBody = new FrameBodyTLEN(file);
        } else if ("TMCL".equals(frameIdentifier)) {
            newBody = new FrameBodyTMCL(file);
        } else if ("TMED".equals(frameIdentifier)) {
            newBody = new FrameBodyTMED(file);
        } else if ("TMOO".equals(frameIdentifier)) {
            newBody = new FrameBodyTMOO(file);
        } else if ("TOAL".equals(frameIdentifier)) {
            newBody = new FrameBodyTOAL(file);
        } else if ("TOFN".equals(frameIdentifier)) {
            newBody = new FrameBodyTOFN(file);
        } else if ("TOLY".equals(frameIdentifier)) {
            newBody = new FrameBodyTOLY(file);
        } else if ("TOPE".equals(frameIdentifier)) {
            newBody = new FrameBodyTOPE(file);
        } else if ("TORY".equals(frameIdentifier)) {
            newBody = new FrameBodyTORY(file); // Deprecated
        } else if ("TOWN".equals(frameIdentifier)) {
            newBody = new FrameBodyTOWN(file);
        } else if ("TPE1".equals(frameIdentifier)) {
            newBody = new FrameBodyTPE1(file);
        } else if ("TPE2".equals(frameIdentifier)) {
            newBody = new FrameBodyTPE2(file);
        } else if ("TPE3".equals(frameIdentifier)) {
            newBody = new FrameBodyTPE3(file);
        } else if ("TPE4".equals(frameIdentifier)) {
            newBody = new FrameBodyTPE4(file);
        } else if ("TPOS".equals(frameIdentifier)) {
            newBody = new FrameBodyTPOS(file);
        } else if ("TPRO".equals(frameIdentifier)) {
            newBody = new FrameBodyTPRO(file);
        } else if ("TPUB".equals(frameIdentifier)) {
            newBody = new FrameBodyTPUB(file);
        } else if ("TRCK".equals(frameIdentifier)) {
            newBody = new FrameBodyTRCK(file);
        } else if ("TRDA".equals(frameIdentifier)) {
            newBody = new FrameBodyTRDA(file); // Deprecated
        } else if ("TRSN".equals(frameIdentifier)) {
            newBody = new FrameBodyTRSN(file);
        } else if ("TRSO".equals(frameIdentifier)) {
            newBody = new FrameBodyTRSO(file);
        } else if ("TSIZ".equals(frameIdentifier)) {
            newBody = new FrameBodyTSIZ(file); // Deprecated
        } else if ("TSOA".equals(frameIdentifier)) {
            newBody = new FrameBodyTSOA(file);
        } else if ("TSOP".equals(frameIdentifier)) {
            newBody = new FrameBodyTSOP(file);
        } else if ("TSOT".equals(frameIdentifier)) {
            newBody = new FrameBodyTSOT(file);
        } else if ("TSRC".equals(frameIdentifier)) {
            newBody = new FrameBodyTSRC(file);
        } else if ("TSSE".equals(frameIdentifier)) {
            newBody = new FrameBodyTSSE(file);
        } else if ("TSST".equals(frameIdentifier)) {
            newBody = new FrameBodyTSST(file);
        } else if ("TXXX".equals(frameIdentifier)) {
            newBody = new FrameBodyTXXX(file);
        } else if ("TYER".equals(frameIdentifier)) {
            newBody = new FrameBodyTYER(file); // Deprecated
        } else if ("UFID".equals(frameIdentifier)) {
            newBody = new FrameBodyUFID(file);
        } else if ("USLT".equals(frameIdentifier)) {
            newBody = new FrameBodyUSLT(file);
        } else if ("WCOM".equals(frameIdentifier)) {
            newBody = new FrameBodyWCOM(file);
        } else if ("WCOP".equals(frameIdentifier)) {
            newBody = new FrameBodyWCOP(file);
        } else if ("WOAF".equals(frameIdentifier)) {
            newBody = new FrameBodyWOAF(file);
        } else if ("WOAR".equals(frameIdentifier)) {
            newBody = new FrameBodyWOAR(file);
        } else if ("WOAS".equals(frameIdentifier)) {
            newBody = new FrameBodyWOAS(file);
        } else if ("WORS".equals(frameIdentifier)) {
            newBody = new FrameBodyWORS(file);
        } else if ("WPAY".equals(frameIdentifier)) {
            newBody = new FrameBodyWPAY(file);
        } else if ("WPUB".equals(frameIdentifier)) {
            newBody = new FrameBodyWPUB(file);
        } else if ("WXXX".equals(frameIdentifier)) {
            newBody = new FrameBodyWXXX(file);
        } else {
            newBody = new FrameBodyUnsupported(file);
        }
        return newBody;
    }
}