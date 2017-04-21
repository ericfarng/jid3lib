package org.farng.mp3;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This contais all ID3 frame descriptions and Lyric3 field description. It also has bit masks for all the flags in the
 * MP3 Header.
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class TagConstant {

    // Bit numbering starts with the most significat bit as 7
    /**
     * defined for convenience
     */
    public static final int BIT7 = 0x80;
    /**
     * defined for convenience
     */
    public static final int BIT6 = 0x40;
    /**
     * defined for convenience
     */
    public static final int BIT5 = 0x20;
    /**
     * defined for convenience
     */
    public static final int BIT4 = 0x10;
    /**
     * defined for convenience
     */
    public static final int BIT3 = 0x08;
    /**
     * defined for convenience
     */
    public static final int BIT2 = 0x04;
    /**
     * defined for convenience
     */
    public static final int BIT1 = 0x02;
    /**
     * defined for convenience
     */
    public static final int BIT0 = 0x01;
    /**
     * System seperators
     */
    public static final String SEPERATOR_LINE = System.getProperty("line.separator");
    public static final String SEPERATOR_FILE = System.getProperty("file.separator");
    public static final String SEPERATOR_PATH = System.getProperty("path.separator");
    /**
     * MP3 save mode lowest numbered index
     */
    public static final int MP3_FILE_SAVE_FIRST = 1;
    /**
     * MP3 save mode matching <code>write</code> method
     */
    public static final int MP3_FILE_SAVE_WRITE = 1;
    /**
     * MP3 save mode matching <code>overwrite</code> method
     */
    public static final int MP3_FILE_SAVE_OVERWRITE = 2;
    /**
     * MP3 save mode matching <code>append</code> method
     */
    public static final int MP3_FILE_SAVE_APPEND = 3;
    /**
     * MP3 save mode highest numbered index
     */
    public static final int MP3_FILE_SAVE_LAST = 3;
    /**
     * ID3v2.2 Header bit mask
     */
    public static final int MASK_V22_UNSYNCHRONIZATION = BIT7;
    /**
     * ID3v2.2 Header bit mask
     */
    public static final int MASK_V22_COMPRESSION = BIT7;
    /**
     * ID3v2.2 BUF Frame bit mask
     */
    public static final int MASK_V22_EMBEDDED_INFO_FLAG = BIT1;
    /**
     * ID3v2.3 Header bit mask
     */
    public static final int MASK_V23_UNSYNCHRONIZATION = BIT7;
    /**
     * ID3v2.3 Header bit mask
     */
    public static final int MASK_V23_EXTENDED_HEADER = BIT6;
    /**
     * ID3v2.3 Header bit mask
     */
    public static final int MASK_V23_EXPERIMENTAL = BIT5;
    /**
     * ID3v2.3 Extended Header bit mask
     */
    public static final int MASK_V23_CRC_DATA_PRESENT = BIT7;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_TAG_ALTER_PRESERVATION = BIT7;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_FILE_ALTER_PRESERVATION = BIT6;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_READ_ONLY = BIT5;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_COMPRESSION = BIT7;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_ENCRYPTION = BIT6;
    /**
     * ID3v2.3 Frame bit mask
     */
    public static final int MASK_V23_GROUPING_IDENTITY = BIT5;
    /**
     * ID3v2.3 RBUF frame bit mask
     */
    public static final int MASK_V23_EMBEDDED_INFO_FLAG = BIT1;
    /**
     * ID3v2.4 Header bit mask
     */
    public static final int MASK_V24_UNSYNCHRONIZATION = BIT7;
    /**
     * ID3v2.4 Header bit mask
     */
    public static final int MASK_V24_EXTENDED_HEADER = BIT6;
    /**
     * ID3v2.4 Header bit mask
     */
    public static final int MASK_V24_EXPERIMENTAL = BIT5;
    /**
     * ID3v2.4 Header bit mask
     */
    public static final int MASK_V24_FOOTER_PRESENT = BIT4;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_TAG_UPDATE = BIT6;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_CRC_DATA_PRESENT = BIT5;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_TAG_RESTRICTIONS = BIT4;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_TAG_SIZE_RESTRICTIONS = (byte) BIT7 | BIT6;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_TEXT_ENCODING_RESTRICTIONS = BIT5;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_TEXT_FIELD_SIZE_RESTRICTIONS = BIT4 | BIT3;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_IMAGE_ENCODING = BIT2;
    /**
     * ID3v2.4 Extended header bit mask
     */
    public static final int MASK_V24_IMAGE_SIZE_RESTRICTIONS = BIT2 | BIT1;

    /**
     * ID3v2.4 Header Footer are the same as the header flags. WHY?!?! move the
     * flags from thier position in 2.3??????????
     */
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_TAG_ALTER_PRESERVATION = BIT6;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_FILE_ALTER_PRESERVATION = BIT5;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_READ_ONLY = BIT4;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_GROUPING_IDENTITY = BIT6;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_COMPRESSION = BIT4;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_ENCRYPTION = BIT3;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_FRAME_UNSYNCHRONIZATION = BIT2;
    /**
     * ID3v2.4 Header Footer bit mask
     */
    public static final int MASK_V24_DATA_LENGTH_INDICATOR = BIT1;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_ID = BIT3;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_VERSION = BIT4 | BIT3;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_LAYER = BIT2 | BIT1;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_PROTECTION = BIT0;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_BITRATE = BIT7 | BIT6 | BIT5 | BIT4;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_FREQUENCY = BIT3 + BIT2;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_PADDING = BIT1;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_PRIVACY = BIT0;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_MODE = BIT7 | BIT6;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_MODE_EXTENSION = BIT5 | BIT4;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_COPY = BIT3;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_HOME = BIT2;
    /**
     * MP3 Frame Header bit mask
     */
    public static final int MASK_MP3_EMPHASIS = BIT1 | BIT0;
    /**
     * <code>HashMap</code> translating the three letter ID into a human understandable string
     */
    public static final HashMap id3v2_2FrameIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the four letter ID into a human understandable string
     */
    public static final HashMap id3v2_3FrameIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the four letter ID into a human understandable string
     */
    public static final HashMap id3v2_4FrameIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined string into the three letter ID
     */
    public static final HashMap id3v2_2FrameStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined string into the four letter ID
     */
    public static final HashMap id3v2_3FrameStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined string into the four letter ID
     */
    public static final HashMap id3v2_4FrameStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined string into the three letter ID
     */
    public static final HashMap lyrics3v2FieldIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined string into the three letter ID
     */
    public static final HashMap lyrics3v2FieldStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the three letter ID3v2.2 ID to the corresponding ID3v2.4 ID
     */
    public static final HashMap id3v2_3ToId3v2_4 = new HashMap();
    /**
     * <code>HashMap</code> translating the three letter ID3v2.2 ID to the corresponding ID3v2.3 ID
     */
    public static final HashMap id3v2_2ToId3v2_3 = new HashMap();
    /**
     * <code>HashMap</code> translating the three letter ID3v2.2 ID to the corresponding ID3v2.4 ID
     */
    public static final HashMap id3v2_4ToId3v2_3 = new HashMap();
    /**
     * <code>HashMap</code> translating the three letter ID3v2.2 ID to the corresponding ID3v2.3 ID
     */
    public static final HashMap id3v2_3ToId3v2_2 = new HashMap();
    /**
     * <code>HashMap</code> translating the ID3v1 genre bit into a human readable string
     */
    public static final HashMap genreIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined genre string into the ID3v1 genre bit
     */
    public static final HashMap genreStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the language ID to a human readable string. [ISO-639-2] ISO/FDIS 639-2
     */
    public static final HashMap languageIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating the predefined language string into the ID. [ISO-639-2] ISO/FDIS 639-2
     */
    public static final HashMap languageStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating the bitrate read in from the MP3 Header into a base-10 integer
     */
    public static final HashMap bitrate = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap textEncodingIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap textEncodingStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap interpolationMethodIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap interpolationMethodStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap pictureTypeIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap pictureTypeStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap timeStampFormatIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap timeStampFormatStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap typeOfEventIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap typeOfEventStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap typeOfChannelIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap typeOfChannelStringToId = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap recievedAsIdToString = new HashMap();
    /**
     * <code>HashMap</code> translating table found in ID3 tags
     */
    public static final HashMap recievedAsStringToId = new HashMap();

    static {
        id3v2_2FrameIdToString.put("BUF", "Recommended buffer size");
        id3v2_2FrameIdToString.put("CNT", "Play counter");
        id3v2_2FrameIdToString.put("COM", "Comments");
        id3v2_2FrameIdToString.put("CRA", "Audio encryption");
        id3v2_2FrameIdToString.put("CRM", "Encrypted meta frame");
        id3v2_2FrameIdToString.put("ETC", "Event timing codes");
        id3v2_2FrameIdToString.put("EQU", "Equalization");
        id3v2_2FrameIdToString.put("GEO", "General encapsulated object");
        id3v2_2FrameIdToString.put("IPL", "Involved people list");
        id3v2_2FrameIdToString.put("LNK", "Linked information");
        id3v2_2FrameIdToString.put("MCI", "Music CD Identifier");
        id3v2_2FrameIdToString.put("MLL", "MPEG location lookup table");
        id3v2_2FrameIdToString.put("PIC", "Attached picture");
        id3v2_2FrameIdToString.put("POP", "Popularimeter");
        id3v2_2FrameIdToString.put("REV", "Reverb");
        id3v2_2FrameIdToString.put("RVA", "Relative volume adjustment");
        id3v2_2FrameIdToString.put("SLT", "Synchronized lyric/text");
        id3v2_2FrameIdToString.put("STC", "Synced tempo codes");
        id3v2_2FrameIdToString.put("TAL", "Text: Album/Movie/Show title");
        id3v2_2FrameIdToString.put("TBP", "Text: BPM (Beats Per Minute)");
        id3v2_2FrameIdToString.put("TCM", "Text: Composer");
        id3v2_2FrameIdToString.put("TCO", "Text: Content type");
        id3v2_2FrameIdToString.put("TCR", "Text: Copyright message");
        id3v2_2FrameIdToString.put("TDA", "Text: Date");
        id3v2_2FrameIdToString.put("TDY", "Text: Playlist delay");
        id3v2_2FrameIdToString.put("TEN", "Text: Encoded by");
        id3v2_2FrameIdToString.put("TFT", "Text: File type");
        id3v2_2FrameIdToString.put("TIM", "Text: Time");
        id3v2_2FrameIdToString.put("TKE", "Text: Initial key");
        id3v2_2FrameIdToString.put("TLA", "Text: Language(s)");
        id3v2_2FrameIdToString.put("TLE", "Text: Length");
        id3v2_2FrameIdToString.put("TMT", "Text: Media type");
        id3v2_2FrameIdToString.put("TOA", "Text: Original artist(s)/performer(s)");
        id3v2_2FrameIdToString.put("TOF", "Text: Original filename");
        id3v2_2FrameIdToString.put("TOL", "Text: Original Lyricist(s)/text writer(s)");
        id3v2_2FrameIdToString.put("TOR", "Text: Original release year");
        id3v2_2FrameIdToString.put("TOT", "Text: Original album/Movie/Show title");
        id3v2_2FrameIdToString.put("TP1", "Text: Lead artist(s)/Lead performer(s)/Soloist(s)/Performing group");
        id3v2_2FrameIdToString.put("TP2", "Text: Band/Orchestra/Accompaniment");
        id3v2_2FrameIdToString.put("TP3", "Text: Conductor/Performer refinement");
        id3v2_2FrameIdToString.put("TP4", "Text: Interpreted, remixed, or otherwise modified by");
        id3v2_2FrameIdToString.put("TPA", "Text: Part of a set");
        id3v2_2FrameIdToString.put("TPB", "Text: Publisher");
        id3v2_2FrameIdToString.put("TRC", "Text: ISRC (International Standard Recording Code)");
        id3v2_2FrameIdToString.put("TRD", "Text: Recording dates");
        id3v2_2FrameIdToString.put("TRK", "Text: Track number/Position in set");
        id3v2_2FrameIdToString.put("TSI", "Text: Size");
        id3v2_2FrameIdToString.put("TSS", "Text: Software/hardware and settings used for encoding");
        id3v2_2FrameIdToString.put("TT1", "Text: Content group description");
        id3v2_2FrameIdToString.put("TT2", "Text: Title/Songname/Content description");
        id3v2_2FrameIdToString.put("TT3", "Text: Subtitle/Description refinement");
        id3v2_2FrameIdToString.put("TXT", "Text: Lyricist/text writer");
        id3v2_2FrameIdToString.put("TXX", "User defined text information frame");
        id3v2_2FrameIdToString.put("TYE", "Text: Year");
        id3v2_2FrameIdToString.put("UFI", "Unique file identifier");
        id3v2_2FrameIdToString.put("ULT", "Unsychronized lyric/text transcription");
        id3v2_2FrameIdToString.put("WAF", "URL: Official audio file webpage");
        id3v2_2FrameIdToString.put("WAR", "URL: Official artist/performer webpage");
        id3v2_2FrameIdToString.put("WAS", "URL: Official audio source webpage");
        id3v2_2FrameIdToString.put("WCM", "URL: Commercial information");
        id3v2_2FrameIdToString.put("WCP", "URL: Copyright/Legal information");
        id3v2_2FrameIdToString.put("WPB", "URL: Publishers official webpage");
        id3v2_2FrameIdToString.put("WXX", "User defined URL link frame");
        String key;
        String value;
        Iterator iterator = id3v2_2FrameIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) id3v2_2FrameIdToString.get(key);
            id3v2_2FrameStringToId.put(value, key);
        }
        id3v2_3FrameIdToString.put("AENC", "Audio encryption");
        id3v2_3FrameIdToString.put("APIC", "Attached picture");
        id3v2_3FrameIdToString.put("COMM", "Comments");
        id3v2_3FrameIdToString.put("COMR", "Commercial frame");
        id3v2_3FrameIdToString.put("ENCR", "Encryption method registration");
        id3v2_3FrameIdToString.put("EQUA", "Equalization");
        id3v2_3FrameIdToString.put("ETCO", "Event timing codes");
        id3v2_3FrameIdToString.put("GEOB", "General encapsulated object");
        id3v2_3FrameIdToString.put("GRID", "Group identification registration");
        id3v2_3FrameIdToString.put("IPLS", "Involved people list");
        id3v2_3FrameIdToString.put("LINK", "Linked information");
        id3v2_3FrameIdToString.put("MCDI", "Music CD identifier");
        id3v2_3FrameIdToString.put("MLLT", "MPEG location lookup table");
        id3v2_3FrameIdToString.put("OWNE", "Ownership frame");
        id3v2_3FrameIdToString.put("PRIV", "Private frame");
        id3v2_3FrameIdToString.put("PCNT", "Play counter");
        id3v2_3FrameIdToString.put("POPM", "Popularimeter");
        id3v2_3FrameIdToString.put("POSS", "Position synchronisation frame");
        id3v2_3FrameIdToString.put("RBUF", "Recommended buffer size");
        id3v2_3FrameIdToString.put("RVAD", "Relative volume adjustment");
        id3v2_3FrameIdToString.put("RVRB", "Reverb");
        id3v2_3FrameIdToString.put("SYLT", "Synchronized lyric/text");
        id3v2_3FrameIdToString.put("SYTC", "Synchronized tempo codes");
        id3v2_3FrameIdToString.put("TALB", "Text: Album/Movie/Show title");
        id3v2_3FrameIdToString.put("TBPM", "Text: BPM (beats per minute)");
        id3v2_3FrameIdToString.put("TCOM", "Text: Composer");
        id3v2_3FrameIdToString.put("TCON", "Text: Content type");
        id3v2_3FrameIdToString.put("TCOP", "Text: Copyright message");
        id3v2_3FrameIdToString.put("TDAT", "Text: Date");
        id3v2_3FrameIdToString.put("TDLY", "Text: Playlist delay");
        id3v2_3FrameIdToString.put("TENC", "Text: Encoded by");
        id3v2_3FrameIdToString.put("TEXT", "Text: Lyricist/Text writer");
        id3v2_3FrameIdToString.put("TFLT", "Text: File type");
        id3v2_3FrameIdToString.put("TIME", "Text: Time");
        id3v2_3FrameIdToString.put("TIT1", "Text: Content group description");
        id3v2_3FrameIdToString.put("TIT2", "Text: Title/songname/content description");
        id3v2_3FrameIdToString.put("TIT3", "Text: Subtitle/Description refinement");
        id3v2_3FrameIdToString.put("TKEY", "Text: Initial key");
        id3v2_3FrameIdToString.put("TLAN", "Text: Language(s)");
        id3v2_3FrameIdToString.put("TLEN", "Text: Length");
        id3v2_3FrameIdToString.put("TMED", "Text: Media type");
        id3v2_3FrameIdToString.put("TOAL", "Text: Original album/movie/show title");
        id3v2_3FrameIdToString.put("TOFN", "Text: Original filename");
        id3v2_3FrameIdToString.put("TOLY", "Text: Original lyricist(s)/text writer(s)");
        id3v2_3FrameIdToString.put("TOPE", "Text: Original artist(s)/performer(s)");
        id3v2_3FrameIdToString.put("TORY", "Text: Original release year");
        id3v2_3FrameIdToString.put("TOWN", "Text: File owner/licensee");
        id3v2_3FrameIdToString.put("TPE1", "Text: Lead performer(s)/Soloist(s)");
        id3v2_3FrameIdToString.put("TPE2", "Text: Band/orchestra/accompaniment");
        id3v2_3FrameIdToString.put("TPE3", "Text: Conductor/performer refinement");
        id3v2_3FrameIdToString.put("TPE4", "Text: Interpreted, remixed, or otherwise modified by");
        id3v2_3FrameIdToString.put("TPOS", "Text: Part of a set");
        id3v2_3FrameIdToString.put("TPUB", "Text: Publisher");
        id3v2_3FrameIdToString.put("TRCK", "Text: Track number/Position in set");
        id3v2_3FrameIdToString.put("TRDA", "Text: Recording dates");
        id3v2_3FrameIdToString.put("TRSN", "Text: Internet radio station name");
        id3v2_3FrameIdToString.put("TRSO", "Text: Internet radio station owner");
        id3v2_3FrameIdToString.put("TSIZ", "Text: Size");
        id3v2_3FrameIdToString.put("TSRC", "Text: ISRC (international standard recording code)");
        id3v2_3FrameIdToString.put("TSSE", "Text: Software/Hardware and settings used for encoding");
        id3v2_3FrameIdToString.put("TYER", "Text: Year");
        id3v2_3FrameIdToString.put("TXXX", "User defined text information frame");
        id3v2_3FrameIdToString.put("UFID", "Unique file identifier");
        id3v2_3FrameIdToString.put("USER", "Terms of use");
        id3v2_3FrameIdToString.put("USLT", "Unsychronized lyric/text transcription");
        id3v2_3FrameIdToString.put("WCOM", "URL: Commercial information");
        id3v2_3FrameIdToString.put("WCOP", "URL: Copyright/Legal information");
        id3v2_3FrameIdToString.put("WOAF", "URL: Official audio file webpage");
        id3v2_3FrameIdToString.put("WOAR", "URL: Official artist/performer webpage");
        id3v2_3FrameIdToString.put("WOAS", "URL: Official audio source webpage");
        id3v2_3FrameIdToString.put("WORS", "URL: Official internet radio station homepage");
        id3v2_3FrameIdToString.put("WPAY", "URL: Payment");
        id3v2_3FrameIdToString.put("WPUB", "URL: Publishers official webpage");
        id3v2_3FrameIdToString.put("WXXX", "User defined URL link frame");
        iterator = id3v2_3FrameIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) id3v2_3FrameIdToString.get(key);
            id3v2_3FrameStringToId.put(value, key);
        }
        id3v2_4FrameIdToString.put("AENC", "Audio encryption");
        id3v2_4FrameIdToString.put("APIC", "Attached picture");
        id3v2_4FrameIdToString.put("ASPI", "Audio seek point index");
        id3v2_4FrameIdToString.put("COMM", "Comments");
        id3v2_4FrameIdToString.put("COMR", "Commercial frame");
        id3v2_4FrameIdToString.put("ENCR", "Encryption method registration");
        id3v2_4FrameIdToString.put("EQU2", "Equalisation (2)");
        id3v2_4FrameIdToString.put("ETCO", "Event timing codes");
        id3v2_4FrameIdToString.put("GEOB", "General encapsulated object");
        id3v2_4FrameIdToString.put("GRID", "Group identification registration");
        id3v2_4FrameIdToString.put("LINK", "Linked information");
        id3v2_4FrameIdToString.put("MCDI", "Music CD identifier");
        id3v2_4FrameIdToString.put("MLLT", "MPEG location lookup table");
        id3v2_4FrameIdToString.put("OWNE", "Ownership frame");
        id3v2_4FrameIdToString.put("PRIV", "Private frame");
        id3v2_4FrameIdToString.put("PCNT", "Play counter");
        id3v2_4FrameIdToString.put("POPM", "Popularimeter");
        id3v2_4FrameIdToString.put("POSS", "Position synchronisation frame");
        id3v2_4FrameIdToString.put("RBUF", "Recommended buffer size");
        id3v2_4FrameIdToString.put("RVA2", "Relative volume adjustment (2)");
        id3v2_4FrameIdToString.put("RVRB", "Reverb");
        id3v2_4FrameIdToString.put("SEEK", "Seek frame");
        id3v2_4FrameIdToString.put("SIGN", "Signature frame");
        id3v2_4FrameIdToString.put("SYLT", "Synchronised lyric/text");
        id3v2_4FrameIdToString.put("SYTC", "Synchronised tempo codes");
        id3v2_4FrameIdToString.put("TALB", "Text: Album/Movie/Show title");
        id3v2_4FrameIdToString.put("TBPM", "Text: BPM (beats per minute)");
        id3v2_4FrameIdToString.put("TCOM", "Text: Composer");
        id3v2_4FrameIdToString.put("TCON", "Text: Content type (genre)");
        id3v2_4FrameIdToString.put("TCOP", "Text: Copyright message");
        id3v2_4FrameIdToString.put("TDEN", "Text: Encoding time");
        id3v2_4FrameIdToString.put("TDLY", "Text: Playlist delay");
        id3v2_4FrameIdToString.put("TDOR", "Text: Original release time");
        id3v2_4FrameIdToString.put("TDRC", "Text: Recording time");
        id3v2_4FrameIdToString.put("TDRL", "Text: Release time");
        id3v2_4FrameIdToString.put("TDTG", "Text: Tagging time");
        id3v2_4FrameIdToString.put("TENC", "Text: Encoded by");
        id3v2_4FrameIdToString.put("TEXT", "Text: Lyricist/Text writer");
        id3v2_4FrameIdToString.put("TFLT", "Text: File type");
        id3v2_4FrameIdToString.put("TIPL", "Text: Involved people list");
        id3v2_4FrameIdToString.put("TIT1", "Text: Content group description");
        id3v2_4FrameIdToString.put("TIT2", "Text: Title/songname/content description");
        id3v2_4FrameIdToString.put("TIT3", "Text: Subtitle/Description refinement");
        id3v2_4FrameIdToString.put("TKEY", "Text: Initial key");
        id3v2_4FrameIdToString.put("TLAN", "Text: Language(s)");
        id3v2_4FrameIdToString.put("TLEN", "Text: Length");
        id3v2_4FrameIdToString.put("TMCL", "Text: Musician credits list");
        id3v2_4FrameIdToString.put("TMED", "Text: Media type");
        id3v2_4FrameIdToString.put("TMOO", "Text: Mood");
        id3v2_4FrameIdToString.put("TOAL", "Text: Original album/movie/show title");
        id3v2_4FrameIdToString.put("TOFN", "Text: Original filename");
        id3v2_4FrameIdToString.put("TOLY", "Text: Original lyricist(s)/text writer(s)");
        id3v2_4FrameIdToString.put("TOPE", "Text: Original artist(s)/performer(s)");
        id3v2_4FrameIdToString.put("TOWN", "Text: File owner/licensee");
        id3v2_4FrameIdToString.put("TPE1", "Text: Lead performer(s)/Soloist(s)");
        id3v2_4FrameIdToString.put("TPE2", "Text: Band/orchestra/accompaniment");
        id3v2_4FrameIdToString.put("TPE3", "Text: Conductor/performer refinement");
        id3v2_4FrameIdToString.put("TPE4", "Text: Interpreted, remixed, or otherwise modified by");
        id3v2_4FrameIdToString.put("TPOS", "Text: Part of a set");
        id3v2_4FrameIdToString.put("TPRO", "Text: Produced notice");
        id3v2_4FrameIdToString.put("TPUB", "Text: Publisher");
        id3v2_4FrameIdToString.put("TRCK", "Text: Track number/Position in set");
        id3v2_4FrameIdToString.put("TRSN", "Text: Internet radio station name");
        id3v2_4FrameIdToString.put("TRSO", "Text: Internet radio station owner");
        id3v2_4FrameIdToString.put("TSOA", "Text: Album sort order");
        id3v2_4FrameIdToString.put("TSOP", "Text: Performer sort order");
        id3v2_4FrameIdToString.put("TSOT", "Text: Title sort order");
        id3v2_4FrameIdToString.put("TSRC", "Text: ISRC (international standard recording code)");
        id3v2_4FrameIdToString.put("TSSE", "Text: Software/Hardware and settings used for encoding");
        id3v2_4FrameIdToString.put("TSST", "Text: Set subtitle");
        id3v2_4FrameIdToString.put("TXXX", "User defined text information frame");
        id3v2_4FrameIdToString.put("UFID", "Unique file identifier");
        id3v2_4FrameIdToString.put("USER", "Terms of use");
        id3v2_4FrameIdToString.put("USLT", "Unsynchronised lyric/text transcription");
        id3v2_4FrameIdToString.put("WCOM", "URL: Commercial information");
        id3v2_4FrameIdToString.put("WCOP", "URL: Copyright/Legal information");
        id3v2_4FrameIdToString.put("WOAF", "URL: Official audio file webpage");
        id3v2_4FrameIdToString.put("WOAR", "URL: Official artist/performer webpage");
        id3v2_4FrameIdToString.put("WOAS", "URL: Official audio source webpage");
        id3v2_4FrameIdToString.put("WORS", "URL: Official Internet radio station homepage");
        id3v2_4FrameIdToString.put("WPAY", "URL: Payment");
        id3v2_4FrameIdToString.put("WPUB", "URL: Publishers official webpage");
        id3v2_4FrameIdToString.put("WXXX", "User defined URL link frame");
        iterator = id3v2_4FrameIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) id3v2_4FrameIdToString.get(key);
            id3v2_4FrameStringToId.put(value, key);
        }
        lyrics3v2FieldIdToString.put("IND", "Indications field");
        lyrics3v2FieldIdToString.put("LYR", "Lyrics multi line text");
        lyrics3v2FieldIdToString.put("INF", "Additional information multi line text");
        lyrics3v2FieldIdToString.put("AUT", "Lyrics/Music Author name");
        lyrics3v2FieldIdToString.put("EAL", "Extended Album name");
        lyrics3v2FieldIdToString.put("EAR", "Extended Artist name");
        lyrics3v2FieldIdToString.put("ETT", "Extended Track Title");
        lyrics3v2FieldIdToString.put("IMG", "Link to an image files");
        iterator = lyrics3v2FieldIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) lyrics3v2FieldIdToString.get(key);
            lyrics3v2FieldStringToId.put(value, key);
        }
        id3v2_2ToId3v2_3.put("BUF", "RBUF");
        id3v2_2ToId3v2_3.put("CNT", "PCNT");
        id3v2_2ToId3v2_3.put("COM", "COMM");
        id3v2_2ToId3v2_3.put("CRA", "AENC");

        //id3v2_2ToId3v2_4.put("CRM", "CRM"); // removed in ID3v2.3
        id3v2_2ToId3v2_3.put("ETC", "ETCO");
        id3v2_2ToId3v2_3.put("EQU", "EQUA"); // changed from EQUA to EQU2 in

        // ID3v2.4
        id3v2_2ToId3v2_3.put("GEO", "GEOB"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("IPL", "IPLS");
        id3v2_2ToId3v2_3.put("LNK", "LINK");
        id3v2_2ToId3v2_3.put("MCI", "MCDI");
        id3v2_2ToId3v2_3.put("MLL", "MLLT");
        id3v2_2ToId3v2_3.put("PIC", "APIC"); // the APIC spec is different from

        // PIC
        id3v2_2ToId3v2_3.put("POP", "POPM");
        id3v2_2ToId3v2_3.put("REV", "RVRB");
        id3v2_2ToId3v2_3.put("RVA", "RVAD"); // changed from RVAD to RVA2 in

        // ID3v2.4
        id3v2_2ToId3v2_3.put("SLT", "SYLT");
        id3v2_2ToId3v2_3.put("STC", "SYTC");
        id3v2_2ToId3v2_3.put("TAL", "TALB");
        id3v2_2ToId3v2_3.put("TBP", "TBPM");
        id3v2_2ToId3v2_3.put("TCM", "TCOM");
        id3v2_2ToId3v2_3.put("TCO", "TCON");
        id3v2_2ToId3v2_3.put("TCR", "TCOP");
        id3v2_2ToId3v2_3.put("TDA", "TDAT"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("TDY", "TDLY");
        id3v2_2ToId3v2_3.put("TEN", "TENC");
        id3v2_2ToId3v2_3.put("TFT", "TFLT");
        id3v2_2ToId3v2_3.put("TIM", "TIME"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("TKE", "TKEY");
        id3v2_2ToId3v2_3.put("TLA", "TLAN");
        id3v2_2ToId3v2_3.put("TLE", "TLEN");
        id3v2_2ToId3v2_3.put("TMT", "TMED");
        id3v2_2ToId3v2_3.put("TOA", "TOPE");
        id3v2_2ToId3v2_3.put("TOF", "TOFN");
        id3v2_2ToId3v2_3.put("TOL", "TOLY");
        id3v2_2ToId3v2_3.put("TOR", "TORY"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("TOT", "TOAL");
        id3v2_2ToId3v2_3.put("TP1", "TPE1");
        id3v2_2ToId3v2_3.put("TP2", "TPE2");
        id3v2_2ToId3v2_3.put("TP3", "TPE3");
        id3v2_2ToId3v2_3.put("TP4", "TPE4");
        id3v2_2ToId3v2_3.put("TPA", "TPOS");
        id3v2_2ToId3v2_3.put("TPB", "TPUB");
        id3v2_2ToId3v2_3.put("TRC", "TSRC");
        id3v2_2ToId3v2_3.put("TRD", "TRDA"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("TRK", "TRCK");
        id3v2_2ToId3v2_3.put("TSI", "TSIZ"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("TSS", "TSSE");
        id3v2_2ToId3v2_3.put("TT1", "TIT1");
        id3v2_2ToId3v2_3.put("TT2", "TIT2");
        id3v2_2ToId3v2_3.put("TT3", "TIT3");
        id3v2_2ToId3v2_3.put("TXT", "TEXT");
        id3v2_2ToId3v2_3.put("TXX", "TXXX");
        id3v2_2ToId3v2_3.put("TYE", "TYER"); // Deprecated in ID3v2.4
        id3v2_2ToId3v2_3.put("UFI", "UFID");
        id3v2_2ToId3v2_3.put("ULT", "USLT");
        id3v2_2ToId3v2_3.put("WAF", "WOAF");
        id3v2_2ToId3v2_3.put("WAR", "WOAR");
        id3v2_2ToId3v2_3.put("WAS", "WOAS");
        id3v2_2ToId3v2_3.put("WCM", "WCOM");
        id3v2_2ToId3v2_3.put("WCP", "WCOP");
        id3v2_2ToId3v2_3.put("WPB", "WPUB");
        id3v2_2ToId3v2_3.put("WXX", "WXXX");
        iterator = id3v2_2ToId3v2_3.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) id3v2_2ToId3v2_3.get(key);
            id3v2_3ToId3v2_2.put(value, key);
        }

        id3v2_3ToId3v2_4.put("EQUA", "EQU2"); // changed from EQUA to EQU2 in ID3v2.4
        id3v2_3ToId3v2_4.put("RVAD", "RVA2"); // changed from RVAD to RVA2 in ID3v2.4
        id3v2_3ToId3v2_4.put("TYER", "TDRL"); // changed
        id3v2_3ToId3v2_4.put("GEOB", null); // Deprecated in ID3v2.4
        id3v2_3ToId3v2_4.put("TDAT", null); // Deprecated in ID3v2.4
        id3v2_3ToId3v2_4.put("TIME", null); // Deprecated in ID3v2.4
        id3v2_3ToId3v2_4.put("TORY", null); // Deprecated in ID3v2.4
        id3v2_3ToId3v2_4.put("TRDA", null); // Deprecated in ID3v2.4
        id3v2_3ToId3v2_4.put("TSIZ", null); // Deprecated in ID3v2.4
        iterator = id3v2_3ToId3v2_4.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if (key != null) {
                value = (String) id3v2_3ToId3v2_4.get(key);
                id3v2_4ToId3v2_3.put(value, key);
            }
        }
        genreIdToString.put(new Long(0), "Blues");
        genreIdToString.put(new Long(1), "Classic Rock");
        genreIdToString.put(new Long(2), "Country");
        genreIdToString.put(new Long(3), "Dance");
        genreIdToString.put(new Long(4), "Disco");
        genreIdToString.put(new Long(5), "Funk");
        genreIdToString.put(new Long(6), "Grunge");
        genreIdToString.put(new Long(7), "Hip-Hop");
        genreIdToString.put(new Long(8), "Jazz");
        genreIdToString.put(new Long(9), "Metal");
        genreIdToString.put(new Long(10), "New Age");
        genreIdToString.put(new Long(11), "Oldies");
        genreIdToString.put(new Long(12), "Other");
        genreIdToString.put(new Long(13), "Pop");
        genreIdToString.put(new Long(14), "R&B");
        genreIdToString.put(new Long(15), "Rap");
        genreIdToString.put(new Long(16), "Reggae");
        genreIdToString.put(new Long(17), "Rock");
        genreIdToString.put(new Long(18), "Techno");
        genreIdToString.put(new Long(19), "Industrial");
        genreIdToString.put(new Long(20), "Alternative");
        genreIdToString.put(new Long(21), "Ska");
        genreIdToString.put(new Long(22), "Death Metal");
        genreIdToString.put(new Long(23), "Pranks");
        genreIdToString.put(new Long(24), "Soundtrack");
        genreIdToString.put(new Long(25), "Euro-Techno");
        genreIdToString.put(new Long(26), "Ambient");
        genreIdToString.put(new Long(27), "Trip-Hop");
        genreIdToString.put(new Long(28), "Vocal");
        genreIdToString.put(new Long(29), "Jazz+Funk");
        genreIdToString.put(new Long(30), "Fusion");
        genreIdToString.put(new Long(31), "Trance");
        genreIdToString.put(new Long(32), "Classical");
        genreIdToString.put(new Long(33), "Instrumental");
        genreIdToString.put(new Long(34), "Acid");
        genreIdToString.put(new Long(35), "House");
        genreIdToString.put(new Long(36), "Game");
        genreIdToString.put(new Long(37), "Sound Clip");
        genreIdToString.put(new Long(38), "Gospel");
        genreIdToString.put(new Long(39), "Noise");
        genreIdToString.put(new Long(40), "AlternRock");
        genreIdToString.put(new Long(41), "Bass");
        genreIdToString.put(new Long(42), "Soul");
        genreIdToString.put(new Long(43), "Punk");
        genreIdToString.put(new Long(44), "Space");
        genreIdToString.put(new Long(45), "Meditative");
        genreIdToString.put(new Long(46), "Instrumental Pop");
        genreIdToString.put(new Long(47), "Instrumental Rock");
        genreIdToString.put(new Long(48), "Ethnic");
        genreIdToString.put(new Long(49), "Gothic");
        genreIdToString.put(new Long(50), "Darkwave");
        genreIdToString.put(new Long(51), "Techno-Industrial");
        genreIdToString.put(new Long(52), "Electronic");
        genreIdToString.put(new Long(53), "Pop-Folk");
        genreIdToString.put(new Long(54), "Eurodance");
        genreIdToString.put(new Long(55), "Dream");
        genreIdToString.put(new Long(56), "Southern Rock");
        genreIdToString.put(new Long(57), "Comedy");
        genreIdToString.put(new Long(58), "Cult");
        genreIdToString.put(new Long(59), "Gangsta");
        genreIdToString.put(new Long(60), "Top 40");
        genreIdToString.put(new Long(61), "Christian Rap");
        genreIdToString.put(new Long(62), "Pop/Funk");
        genreIdToString.put(new Long(63), "Jungle");
        genreIdToString.put(new Long(64), "Native American");
        genreIdToString.put(new Long(65), "Cabaret");
        genreIdToString.put(new Long(66), "New Wave");
        genreIdToString.put(new Long(67), "Psychadelic");
        genreIdToString.put(new Long(68), "Rave");
        genreIdToString.put(new Long(69), "Showtunes");
        genreIdToString.put(new Long(70), "Trailer");
        genreIdToString.put(new Long(71), "Lo-Fi");
        genreIdToString.put(new Long(72), "Tribal");
        genreIdToString.put(new Long(73), "Acid Punk");
        genreIdToString.put(new Long(74), "Acid Jazz");
        genreIdToString.put(new Long(75), "Polka");
        genreIdToString.put(new Long(76), "Retro");
        genreIdToString.put(new Long(77), "Musical");
        genreIdToString.put(new Long(78), "Rock & Roll");
        genreIdToString.put(new Long(79), "Hard Rock");
        genreIdToString.put(new Long(80), "Folk");
        genreIdToString.put(new Long(81), "Folk-Rock");
        genreIdToString.put(new Long(82), "National Folk");
        genreIdToString.put(new Long(83), "Swing");
        genreIdToString.put(new Long(84), "Fast Fusion");
        genreIdToString.put(new Long(85), "Bebob");
        genreIdToString.put(new Long(86), "Latin");
        genreIdToString.put(new Long(87), "Revival");
        genreIdToString.put(new Long(88), "Celtic");
        genreIdToString.put(new Long(89), "Bluegrass");
        genreIdToString.put(new Long(90), "Avantgarde");
        genreIdToString.put(new Long(91), "Gothic Rock");
        genreIdToString.put(new Long(92), "Progressive Rock");
        genreIdToString.put(new Long(93), "Psychedelic Rock");
        genreIdToString.put(new Long(94), "Symphonic Rock");
        genreIdToString.put(new Long(95), "Slow Rock");
        genreIdToString.put(new Long(96), "Big Band");
        genreIdToString.put(new Long(97), "Chorus");
        genreIdToString.put(new Long(98), "Easy Listening");
        genreIdToString.put(new Long(99), "Acoustic");
        genreIdToString.put(new Long(100), "Humour");
        genreIdToString.put(new Long(101), "Speech");
        genreIdToString.put(new Long(102), "Chanson");
        genreIdToString.put(new Long(103), "Opera");
        genreIdToString.put(new Long(104), "Chamber Music");
        genreIdToString.put(new Long(105), "Sonata");
        genreIdToString.put(new Long(106), "Symphony");
        genreIdToString.put(new Long(107), "Booty Bass");
        genreIdToString.put(new Long(108), "Primus");
        genreIdToString.put(new Long(109), "Porn Groove");
        genreIdToString.put(new Long(110), "Satire");
        genreIdToString.put(new Long(111), "Slow Jam");
        genreIdToString.put(new Long(112), "Club");
        genreIdToString.put(new Long(113), "Tango");
        genreIdToString.put(new Long(114), "Samba");
        genreIdToString.put(new Long(115), "Folklore");
        genreIdToString.put(new Long(116), "Ballad");
        genreIdToString.put(new Long(117), "Power Ballad");
        genreIdToString.put(new Long(118), "Rhythmic Soul");
        genreIdToString.put(new Long(119), "Freestyle");
        genreIdToString.put(new Long(120), "Duet");
        genreIdToString.put(new Long(121), "Punk Rock");
        genreIdToString.put(new Long(122), "Drum Solo");
        genreIdToString.put(new Long(123), "Acapella");
        genreIdToString.put(new Long(124), "Euro-House");
        genreIdToString.put(new Long(125), "Dance Hall");

        // ID's are typed as Integer because the combo box expects it
        Long lkey;
        iterator = genreIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) genreIdToString.get(lkey);
            genreStringToId.put(value, lkey);
        }

        // MPEG-1, Layer I (E)
        bitrate.put(new Long(0x1E), new Long(32));
        bitrate.put(new Long(0x2E), new Long(64));
        bitrate.put(new Long(0x3E), new Long(96));
        bitrate.put(new Long(0x4E), new Long(128));
        bitrate.put(new Long(0x5E), new Long(160));
        bitrate.put(new Long(0x6E), new Long(192));
        bitrate.put(new Long(0x7E), new Long(224));
        bitrate.put(new Long(0x8E), new Long(256));
        bitrate.put(new Long(0x9E), new Long(288));
        bitrate.put(new Long(0xAE), new Long(320));
        bitrate.put(new Long(0xBE), new Long(352));
        bitrate.put(new Long(0xCE), new Long(384));
        bitrate.put(new Long(0xDE), new Long(416));
        bitrate.put(new Long(0xEE), new Long(448));

        // MPEG-1, Layer II (C)
        bitrate.put(new Long(0x1C), new Long(32));
        bitrate.put(new Long(0x2C), new Long(48));
        bitrate.put(new Long(0x3C), new Long(56));
        bitrate.put(new Long(0x4C), new Long(64));
        bitrate.put(new Long(0x5C), new Long(80));
        bitrate.put(new Long(0x6C), new Long(96));
        bitrate.put(new Long(0x7C), new Long(112));
        bitrate.put(new Long(0x8C), new Long(128));
        bitrate.put(new Long(0x9C), new Long(160));
        bitrate.put(new Long(0xAC), new Long(192));
        bitrate.put(new Long(0xBC), new Long(224));
        bitrate.put(new Long(0xCC), new Long(256));
        bitrate.put(new Long(0xDC), new Long(320));
        bitrate.put(new Long(0xEC), new Long(384));

        // MPEG-1, Layer III (A)
        bitrate.put(new Long(0x1A), new Long(32));
        bitrate.put(new Long(0x2A), new Long(40));
        bitrate.put(new Long(0x3A), new Long(48));
        bitrate.put(new Long(0x4A), new Long(56));
        bitrate.put(new Long(0x5A), new Long(64));
        bitrate.put(new Long(0x6A), new Long(80));
        bitrate.put(new Long(0x7A), new Long(96));
        bitrate.put(new Long(0x8A), new Long(112));
        bitrate.put(new Long(0x9A), new Long(128));
        bitrate.put(new Long(0xAA), new Long(160));
        bitrate.put(new Long(0xBA), new Long(192));
        bitrate.put(new Long(0xCA), new Long(224));
        bitrate.put(new Long(0xDA), new Long(256));
        bitrate.put(new Long(0xEA), new Long(320));

        // MPEG-2, Layer I (6)
        bitrate.put(new Long(0x16), new Long(32));
        bitrate.put(new Long(0x26), new Long(48));
        bitrate.put(new Long(0x36), new Long(56));
        bitrate.put(new Long(0x46), new Long(64));
        bitrate.put(new Long(0x56), new Long(80));
        bitrate.put(new Long(0x66), new Long(96));
        bitrate.put(new Long(0x76), new Long(112));
        bitrate.put(new Long(0x86), new Long(128));
        bitrate.put(new Long(0x96), new Long(144));
        bitrate.put(new Long(0xA6), new Long(160));
        bitrate.put(new Long(0xB6), new Long(176));
        bitrate.put(new Long(0xC6), new Long(192));
        bitrate.put(new Long(0xD6), new Long(224));
        bitrate.put(new Long(0xE6), new Long(256));

        // MPEG-2, Layer II (4)
        bitrate.put(new Long(0x14), new Long(8));
        bitrate.put(new Long(0x24), new Long(16));
        bitrate.put(new Long(0x34), new Long(24));
        bitrate.put(new Long(0x44), new Long(32));
        bitrate.put(new Long(0x54), new Long(40));
        bitrate.put(new Long(0x64), new Long(48));
        bitrate.put(new Long(0x74), new Long(56));
        bitrate.put(new Long(0x84), new Long(64));
        bitrate.put(new Long(0x94), new Long(80));
        bitrate.put(new Long(0xA4), new Long(96));
        bitrate.put(new Long(0xB4), new Long(112));
        bitrate.put(new Long(0xC4), new Long(128));
        bitrate.put(new Long(0xD4), new Long(144));
        bitrate.put(new Long(0xE4), new Long(160));

        // MPEG-2, Layer III (2)
        bitrate.put(new Long(0x12), new Long(8));
        bitrate.put(new Long(0x22), new Long(16));
        bitrate.put(new Long(0x32), new Long(24));
        bitrate.put(new Long(0x42), new Long(32));
        bitrate.put(new Long(0x52), new Long(40));
        bitrate.put(new Long(0x62), new Long(48));
        bitrate.put(new Long(0x72), new Long(56));
        bitrate.put(new Long(0x82), new Long(64));
        bitrate.put(new Long(0x92), new Long(80));
        bitrate.put(new Long(0xA2), new Long(96));
        bitrate.put(new Long(0xB2), new Long(112));
        bitrate.put(new Long(0xC2), new Long(128));
        bitrate.put(new Long(0xD2), new Long(144));
        bitrate.put(new Long(0xE2), new Long(160));
        languageIdToString.put("aar", "Afar");
        languageIdToString.put("abk", "Abkhazian");
        languageIdToString.put("ace", "Achinese");
        languageIdToString.put("ach", "Acoli");
        languageIdToString.put("ada", "Adangme");
        languageIdToString.put("afa", "Afro-Asiatic (Other)");
        languageIdToString.put("afh", "Afrihili");
        languageIdToString.put("afr", "Afrikaans");
        languageIdToString.put("aka", "Akan");
        languageIdToString.put("akk", "Akkadian");
        languageIdToString.put("alb", "Albanian");
        languageIdToString.put("ale", "Aleut");
        languageIdToString.put("alg", "Algonquian languages");
        languageIdToString.put("amh", "Amharic");
        languageIdToString.put("ang", "English, Old (ca.450-1100)");
        languageIdToString.put("apa", "Apache languages");
        languageIdToString.put("ara", "Arabic");
        languageIdToString.put("arc", "Aramaic");
        languageIdToString.put("arm", "Armenian");
        languageIdToString.put("arn", "Araucanian");
        languageIdToString.put("arp", "Arapaho");
        languageIdToString.put("art", "Artificial (Other)");
        languageIdToString.put("arw", "Arawak");
        languageIdToString.put("asm", "Assamese");
        languageIdToString.put("ast", "Asturian; Bable");
        languageIdToString.put("ath", "Athapascan languages");
        languageIdToString.put("aus", "Australian languages");
        languageIdToString.put("ava", "Avaric");
        languageIdToString.put("ave", "Avestan");
        languageIdToString.put("awa", "Awadhi");
        languageIdToString.put("aym", "Aymara");
        languageIdToString.put("aze", "Azerbaijani");
        languageIdToString.put("bad", "Banda");
        languageIdToString.put("bai", "Bamileke languages");
        languageIdToString.put("bak", "Bashkir");
        languageIdToString.put("bal", "Baluchi");
        languageIdToString.put("bam", "Bambara");
        languageIdToString.put("ban", "Balinese");
        languageIdToString.put("baq", "Basque");
        languageIdToString.put("bas", "Basa");
        languageIdToString.put("bat", "Baltic (Other)");
        languageIdToString.put("bej", "Beja");
        languageIdToString.put("bel", "Belarusian");
        languageIdToString.put("bem", "Bemba");
        languageIdToString.put("ben", "Bengali");
        languageIdToString.put("ber", "Berber (Other)");
        languageIdToString.put("bho", "Bhojpuri");
        languageIdToString.put("bih", "Bihari");
        languageIdToString.put("bik", "Bikol");
        languageIdToString.put("bin", "Bini");
        languageIdToString.put("bis", "Bislama");
        languageIdToString.put("bla", "Siksika");
        languageIdToString.put("bnt", "Bantu (Other)");
        languageIdToString.put("bod", "Tibetan");
        languageIdToString.put("bos", "Bosnian");
        languageIdToString.put("bra", "Braj");
        languageIdToString.put("bre", "Breton");
        languageIdToString.put("btk", "Batak (Indonesia)");
        languageIdToString.put("bua", "Buriat");
        languageIdToString.put("bug", "Buginese");
        languageIdToString.put("bul", "Bulgarian");
        languageIdToString.put("bur", "Burmese");
        languageIdToString.put("cad", "Caddo");
        languageIdToString.put("cai", "Central American Indian (Other)");
        languageIdToString.put("car", "Carib");
        languageIdToString.put("cat", "Catalan");
        languageIdToString.put("cau", "Caucasian (Other)");
        languageIdToString.put("ceb", "Cebuano");
        languageIdToString.put("cel", "Celtic (Other)");
        languageIdToString.put("ces", "Czech");
        languageIdToString.put("cha", "Chamorro");
        languageIdToString.put("chb", "Chibcha");
        languageIdToString.put("che", "Chechen");
        languageIdToString.put("chg", "Chagatai");
        languageIdToString.put("chi", "Chinese");
        languageIdToString.put("chk", "Chuukese");
        languageIdToString.put("chm", "Mari");
        languageIdToString.put("chn", "Chinook jargon");
        languageIdToString.put("cho", "Choctaw");
        languageIdToString.put("chp", "Chipewyan");
        languageIdToString.put("chr", "Cherokee");
        languageIdToString.put("chu",
                               "Church Slavic; Old Slavonic; Old Church Slavonic; Church Slavonic; Old Bulgarian");
        languageIdToString.put("chv", "Chuvash");
        languageIdToString.put("chy", "Cheyenne");
        languageIdToString.put("cmc", "Chamic languages");
        languageIdToString.put("cop", "Coptic");
        languageIdToString.put("cor", "Cornish");
        languageIdToString.put("cos", "Corsican");
        languageIdToString.put("cpe", "Creoles and pidgins, English based (Other)");
        languageIdToString.put("cpf", "Creoles and pidgins, French-based (Other)");
        languageIdToString.put("cpp", "Creoles and pidgins,");
        languageIdToString.put("cre", "Cree");
        languageIdToString.put("crp", "Creoles and pidgins (Other)");
        languageIdToString.put("cus", "Cushitic (Other)");
        languageIdToString.put("cym", "Welsh");
        languageIdToString.put("cze", "Czech");
        languageIdToString.put("dak", "Dakota");
        languageIdToString.put("dan", "Danish");
        languageIdToString.put("day", "Dayak");
        languageIdToString.put("del", "Delaware");
        languageIdToString.put("den", "Slave (Athapascan)");
        languageIdToString.put("deu", "German");
        languageIdToString.put("dgr", "Dogrib");
        languageIdToString.put("din", "Dinka");
        languageIdToString.put("div", "Divehi");
        languageIdToString.put("doi", "Dogri");
        languageIdToString.put("dra", "Dravidian (Other)");
        languageIdToString.put("dua", "Duala");
        languageIdToString.put("dum", "Dutch, Middle (ca.1050-1350)");
        languageIdToString.put("dut", "Dutch");
        languageIdToString.put("dyu", "Dyula");
        languageIdToString.put("dzo", "Dzongkha");
        languageIdToString.put("efi", "Efik");
        languageIdToString.put("egy", "Egyptian (Ancient)");
        languageIdToString.put("eka", "Ekajuk");
        languageIdToString.put("ell", "Greek, Modern (1453-)");
        languageIdToString.put("elx", "Elamite");
        languageIdToString.put("eng", "English");
        languageIdToString.put("enm", "English, Middle (1100-1500)");
        languageIdToString.put("epo", "Esperanto");
        languageIdToString.put("est", "Estonian");
        languageIdToString.put("eus", "Basque");
        languageIdToString.put("ewe", "Ewe");
        languageIdToString.put("ewo", "Ewondo");
        languageIdToString.put("fan", "Fang");
        languageIdToString.put("fao", "Faroese");
        languageIdToString.put("fas", "Persian");
        languageIdToString.put("fat", "Fanti");
        languageIdToString.put("fij", "Fijian");
        languageIdToString.put("fin", "Finnish");
        languageIdToString.put("fiu", "Finno-Ugrian (Other)");
        languageIdToString.put("fon", "Fon");
        languageIdToString.put("fra", "French");
        languageIdToString.put("frm", "French, Middle (ca.1400-1800)");
        languageIdToString.put("fro", "French, Old (842-ca.1400)");
        languageIdToString.put("fry", "Frisian");
        languageIdToString.put("ful", "Fulah");
        languageIdToString.put("fur", "Friulian");
        languageIdToString.put("gaa", "Ga");
        languageIdToString.put("gay", "Gayo");
        languageIdToString.put("gba", "Gbaya");
        languageIdToString.put("gem", "Germanic (Other)");
        languageIdToString.put("geo", "Georgian");
        languageIdToString.put("ger", "German");
        languageIdToString.put("gez", "Geez");
        languageIdToString.put("gil", "Gilbertese");
        languageIdToString.put("gla", "Gaelic; Scottish Gaelic");
        languageIdToString.put("gle", "Irish");
        languageIdToString.put("glg", "Gallegan");
        languageIdToString.put("glv", "Manx");
        languageIdToString.put("gmh", "German, Middle High (ca.1050-1500)");
        languageIdToString.put("goh", "German, Old High (ca.750-1050)");
        languageIdToString.put("gon", "Gondi");
        languageIdToString.put("gor", "Gorontalo");
        languageIdToString.put("got", "Gothic");
        languageIdToString.put("grb", "Grebo");
        languageIdToString.put("grc", "Greek, Ancient (to 1453)");
        languageIdToString.put("gre", "Greek, Modern (1453-)");
        languageIdToString.put("grn", "Guarani");
        languageIdToString.put("guj", "Gujarati");
        languageIdToString.put("gwi", "Gwich�in");
        languageIdToString.put("hai", "Haida");
        languageIdToString.put("hau", "Hausa");
        languageIdToString.put("haw", "Hawaiian");
        languageIdToString.put("heb", "Hebrew");
        languageIdToString.put("her", "Herero");
        languageIdToString.put("hil", "Hiligaynon");
        languageIdToString.put("him", "Himachali");
        languageIdToString.put("hin", "Hindi");
        languageIdToString.put("hit", "Hittite");
        languageIdToString.put("hmn", "Hmong");
        languageIdToString.put("hmo", "Hiri Motu");
        languageIdToString.put("hrv", "Croatian");
        languageIdToString.put("hun", "Hungarian");
        languageIdToString.put("hup", "Hupa");
        languageIdToString.put("hye", "Armenian");
        languageIdToString.put("iba", "Iban");
        languageIdToString.put("ibo", "Igbo");
        languageIdToString.put("ice", "Icelandic");
        languageIdToString.put("ido", "Ido");
        languageIdToString.put("ijo", "Ijo");
        languageIdToString.put("iku", "Inuktitut");
        languageIdToString.put("ile", "Interlingue");
        languageIdToString.put("ilo", "Iloko");
        languageIdToString.put("ina", "Interlingua (International Auxiliary)");
        languageIdToString.put("inc", "Indic (Other)");
        languageIdToString.put("ind", "Indonesian");
        languageIdToString.put("ine", "Indo-European (Other)");
        languageIdToString.put("ipk", "Inupiaq");
        languageIdToString.put("ira", "Iranian (Other)");
        languageIdToString.put("iro", "Iroquoian languages");
        languageIdToString.put("isl", "Icelandic");
        languageIdToString.put("ita", "Italian");
        languageIdToString.put("jav", "Javanese");
        languageIdToString.put("jpn", "Japanese");
        languageIdToString.put("jpr", "Judeo-Persian");
        languageIdToString.put("jrb", "Judeo-Arabic");
        languageIdToString.put("kaa", "Kara-Kalpak");
        languageIdToString.put("kab", "Kabyle");
        languageIdToString.put("kac", "Kachin");
        languageIdToString.put("kal", "Kalaallisut");
        languageIdToString.put("kam", "Kamba");
        languageIdToString.put("kan", "Kannada");
        languageIdToString.put("kar", "Karen");
        languageIdToString.put("kas", "Kashmiri");
        languageIdToString.put("kat", "Georgian");
        languageIdToString.put("kau", "Kanuri");
        languageIdToString.put("kaw", "Kawi");
        languageIdToString.put("kaz", "Kazakh");
        languageIdToString.put("kha", "Khasi");
        languageIdToString.put("khi", "Khoisan (Other)");
        languageIdToString.put("khm", "Khmer");
        languageIdToString.put("kho", "Khotanese");
        languageIdToString.put("kik", "Kikuyu; Gikuyu");
        languageIdToString.put("kin", "Kinyarwanda");
        languageIdToString.put("kir", "Kirghiz");
        languageIdToString.put("kmb", "Kimbundu");
        languageIdToString.put("kok", "Konkani");
        languageIdToString.put("kom", "Komi");
        languageIdToString.put("kon", "Kongo");
        languageIdToString.put("kor", "Korean");
        languageIdToString.put("kos", "Kosraean");
        languageIdToString.put("kpe", "Kpelle");
        languageIdToString.put("kro", "Kru");
        languageIdToString.put("kru", "Kurukh");
        languageIdToString.put("kua", "Kuanyama; Kwanyama");
        languageIdToString.put("kum", "Kumyk");
        languageIdToString.put("kur", "Kurdish");
        languageIdToString.put("kut", "Kutenai");
        languageIdToString.put("lad", "Ladino");
        languageIdToString.put("lah", "Lahnda");
        languageIdToString.put("lam", "Lamba");
        languageIdToString.put("lao", "Lao");
        languageIdToString.put("lat", "Latin");
        languageIdToString.put("lav", "Latvian");
        languageIdToString.put("lez", "Lezghian");
        languageIdToString.put("lin", "Lingala");
        languageIdToString.put("lit", "Lithuanian");
        languageIdToString.put("lol", "Mongo");
        languageIdToString.put("loz", "Lozi");
        languageIdToString.put("ltz", "Luxembourgish; Letzeburgesch");
        languageIdToString.put("lua", "Luba-Lulua");
        languageIdToString.put("lub", "Luba-Katanga");
        languageIdToString.put("lug", "Ganda");
        languageIdToString.put("lui", "Luiseno");
        languageIdToString.put("lun", "Lunda");
        languageIdToString.put("luo", "Luo (Kenya and Tanzania)");
        languageIdToString.put("lus", "lushai");
        languageIdToString.put("mac", "Macedonian");
        languageIdToString.put("mad", "Madurese");
        languageIdToString.put("mag", "Magahi");
        languageIdToString.put("mah", "Marshallese");
        languageIdToString.put("mai", "Maithili");
        languageIdToString.put("mak", "Makasar");
        languageIdToString.put("mal", "Malayalam");
        languageIdToString.put("man", "Mandingo");
        languageIdToString.put("mao", "Maori");
        languageIdToString.put("map", "Austronesian (Other)");
        languageIdToString.put("mar", "Marathi");
        languageIdToString.put("mas", "Masai");
        languageIdToString.put("may", "Malay");
        languageIdToString.put("mdr", "Mandar");
        languageIdToString.put("men", "Mende");
        languageIdToString.put("mga", "Irish, Middle (900-1200)");
        languageIdToString.put("mic", "Micmac");
        languageIdToString.put("min", "Minangkabau");
        languageIdToString.put("mis", "Miscellaneous languages");
        languageIdToString.put("mkd", "Macedonian");
        languageIdToString.put("mkh", "Mon-Khmer (Other)");
        languageIdToString.put("mlg", "Malagasy");
        languageIdToString.put("mlt", "Maltese");
        languageIdToString.put("mnc", "Manchu");
        languageIdToString.put("mni", "Manipuri");
        languageIdToString.put("mno", "Manobo languages");
        languageIdToString.put("moh", "Mohawk");
        languageIdToString.put("mol", "Moldavian");
        languageIdToString.put("mon", "Mongolian");
        languageIdToString.put("mos", "Mossi");
        languageIdToString.put("mri", "Maori");
        languageIdToString.put("msa", "Malay");
        languageIdToString.put("mul", "Multiple languages");
        languageIdToString.put("mun", "Munda languages");
        languageIdToString.put("mus", "Creek");
        languageIdToString.put("mwr", "Marwari");
        languageIdToString.put("mya", "Burmese");
        languageIdToString.put("myn", "Mayan languages");
        languageIdToString.put("nah", "Nahuatl");
        languageIdToString.put("nai", "North American Indian");
        languageIdToString.put("nau", "Nauru");
        languageIdToString.put("nav", "Navajo; Navaho");
        languageIdToString.put("nbl", "South Ndebele");
        languageIdToString.put("nde", "North Ndebele");
        languageIdToString.put("ndo", "Ndonga");
        languageIdToString.put("nds", "Low German; Low Saxon; German, Low; Saxon, Low");
        languageIdToString.put("nep", "Nepali");
        languageIdToString.put("new", "Newari");
        languageIdToString.put("nia", "Nias");
        languageIdToString.put("nic", "Niger-Kordofanian (Other)");
        languageIdToString.put("niu", "Niuean");
        languageIdToString.put("nld", "Dutch");
        languageIdToString.put("nno", "Norwegian Nynorsk");
        languageIdToString.put("nob", "Norwegian Bokm�l");
        languageIdToString.put("non", "Norse, Old");
        languageIdToString.put("nor", "Norwegian");
        languageIdToString.put("nso", "Sotho, Northern");
        languageIdToString.put("nub", "Nubian languages");
        languageIdToString.put("nya", "Chichewa; Chewa; Nyanja");
        languageIdToString.put("nym", "Nyamwezi");
        languageIdToString.put("nyn", "Nyankole");
        languageIdToString.put("nyo", "Nyoro");
        languageIdToString.put("nzi", "Nzima");
        languageIdToString.put("oci", "Occitan (post 1500); Proven�al");
        languageIdToString.put("oji", "Ojibwa");
        languageIdToString.put("ori", "Oriya");
        languageIdToString.put("orm", "Oromo");
        languageIdToString.put("osa", "Osage");
        languageIdToString.put("oss", "Ossetian; Ossetic");
        languageIdToString.put("ota", "Turkish, Ottoman (1500-1928)");
        languageIdToString.put("oto", "Otomian languages");
        languageIdToString.put("paa", "Papuan (Other)");
        languageIdToString.put("pag", "Pangasinan");
        languageIdToString.put("pal", "Pahlavi");
        languageIdToString.put("pam", "Pampanga");
        languageIdToString.put("pan", "Panjabi");
        languageIdToString.put("pap", "Papiamento");
        languageIdToString.put("pau", "Palauan");
        languageIdToString.put("peo", "Persian, Old (ca.600-400 B.C.)");
        languageIdToString.put("per", "Persian");
        languageIdToString.put("per", "Persian");
        languageIdToString.put("phi", "Philippine (Other)");
        languageIdToString.put("phn", "Phoenician");
        languageIdToString.put("pli", "Pali");
        languageIdToString.put("pol", "Polish");
        languageIdToString.put("pon", "Pohnpeian");
        languageIdToString.put("por", "Portuguese");
        languageIdToString.put("pra", "Prakrit languages");
        languageIdToString.put("pro", "Proven�al, Old (to 1500)");
        languageIdToString.put("pus", "Pushto");
        languageIdToString.put("que", "Quechua");
        languageIdToString.put("raj", "Rajasthani");
        languageIdToString.put("rap", "Rapanui");
        languageIdToString.put("rar", "Rarotongan");
        languageIdToString.put("roa", "Romance (Other)");
        languageIdToString.put("roh", "Raeto-Romance");
        languageIdToString.put("rom", "Romany");
        languageIdToString.put("ron", "Romanian");
        languageIdToString.put("rum", "Romanian");
        languageIdToString.put("run", "Rundi");
        languageIdToString.put("rus", "Russian");
        languageIdToString.put("sad", "Sandawe");
        languageIdToString.put("sag", "Sango");
        languageIdToString.put("sah", "Yakut");
        languageIdToString.put("sai", "South American Indian (Other)");
        languageIdToString.put("sal", "Salishan languages");
        languageIdToString.put("sam", "Samaritan Aramaic");
        languageIdToString.put("san", "Sanskrit");
        languageIdToString.put("sas", "Sasak");
        languageIdToString.put("sat", "Santali");
        languageIdToString.put("scc", "Serbian");
        languageIdToString.put("sco", "Scots");
        languageIdToString.put("scr", "Croatian");
        languageIdToString.put("sel", "Selkup");
        languageIdToString.put("sem", "Semitic (Other)");
        languageIdToString.put("sga", "Irish, Old (to 900)");
        languageIdToString.put("sgn", "Sign languages");
        languageIdToString.put("shn", "Shan");
        languageIdToString.put("sid", "Sidamo");
        languageIdToString.put("sin", "Sinhales");
        languageIdToString.put("sio", "Siouan languages");
        languageIdToString.put("sit", "Sino-Tibetan (Other)");
        languageIdToString.put("sla", "Slavic (Other)");
        languageIdToString.put("slk", "Slovak");
        languageIdToString.put("slo", "Slovak");
        languageIdToString.put("slv", "Slovenian");
        languageIdToString.put("sma", "Southern Sami");
        languageIdToString.put("sme", "Northern Sami");
        languageIdToString.put("smi", "Sami languages (Other)");
        languageIdToString.put("smj", "Lule Sami");
        languageIdToString.put("smn", "Inari Sami");
        languageIdToString.put("smo", "Samoan");
        languageIdToString.put("sms", "Skolt Sami");
        languageIdToString.put("sna", "Shona");
        languageIdToString.put("snd", "Sindhi");
        languageIdToString.put("snk", "Soninke");
        languageIdToString.put("sog", "Sogdian");
        languageIdToString.put("som", "Somali");
        languageIdToString.put("son", "Songhai");
        languageIdToString.put("sot", "Sotho, Southern");
        languageIdToString.put("spa", "Spanish; Castilia");
        languageIdToString.put("sqi", "Albanian");
        languageIdToString.put("srd", "Sardinian");
        languageIdToString.put("srp", "Serbian");
        languageIdToString.put("srr", "Serer");
        languageIdToString.put("ssa", "Nilo-Saharan (Other)");
        languageIdToString.put("sus", "Susu");
        languageIdToString.put("sux", "Sumerian");
        languageIdToString.put("swa", "Swahili");
        languageIdToString.put("swe", "Swedish");
        languageIdToString.put("syr", "Syriac");
        languageIdToString.put("tah", "Tahitian");
        languageIdToString.put("tai", "Tai (Other)");
        languageIdToString.put("tam", "Tamil");
        languageIdToString.put("tat", "Tatar");
        languageIdToString.put("tel", "Telugu");
        languageIdToString.put("tem", "Timne");
        languageIdToString.put("ter", "Tereno");
        languageIdToString.put("tet", "Tetum");
        languageIdToString.put("tgk", "Tajik");
        languageIdToString.put("tgl", "Tagalog");
        languageIdToString.put("tha", "Thai");
        languageIdToString.put("tib", "Tibetan");
        languageIdToString.put("tig", "Tigre");
        languageIdToString.put("tir", "Tigrinya");
        languageIdToString.put("tiv", "Tiv");
        languageIdToString.put("tkl", "Tokelau");
        languageIdToString.put("tli", "Tlingit");
        languageIdToString.put("tmh", "Tamashek");
        languageIdToString.put("tog", "Tonga (Nyasa)");
        languageIdToString.put("ton", "Tonga (Tonga Islands)");
        languageIdToString.put("tpi", "Tok Pisin");
        languageIdToString.put("tsi", "Tsimshian");
        languageIdToString.put("tsn", "Tswana");
        languageIdToString.put("tso", "Tsonga");
        languageIdToString.put("tuk", "Turkmen");
        languageIdToString.put("tum", "Tumbuka");
        languageIdToString.put("tup", "Tupi languages");
        languageIdToString.put("tur", "Turkish");
        languageIdToString.put("tut", "Altaic (Other)");
        languageIdToString.put("tvl", "Tuvalu");
        languageIdToString.put("twi", "Twi");
        languageIdToString.put("tyv", "Tuvinian");
        languageIdToString.put("uga", "Ugaritic");
        languageIdToString.put("uig", "Uighur");
        languageIdToString.put("ukr", "Ukrainian");
        languageIdToString.put("umb", "Umbundu");
        languageIdToString.put("und", "Undetermined");
        languageIdToString.put("urd", "Urdu");
        languageIdToString.put("uzb", "Uzbek");
        languageIdToString.put("vai", "Vai");
        languageIdToString.put("ven", "Venda");
        languageIdToString.put("vie", "Vietnamese");
        languageIdToString.put("vol", "Volap�k");
        languageIdToString.put("vot", "Votic");
        languageIdToString.put("wak", "Wakashan languages");
        languageIdToString.put("wal", "Walamo");
        languageIdToString.put("war", "Waray");
        languageIdToString.put("was", "Washo");
        languageIdToString.put("wel", "Welsh");
        languageIdToString.put("wen", "Sorbian languages");
        languageIdToString.put("wln", "Walloon");
        languageIdToString.put("wol", "Wolof");
        languageIdToString.put("xho", "Xhosa");
        languageIdToString.put("yao", "Yao");
        languageIdToString.put("yap", "Yapese");
        languageIdToString.put("yid", "Yiddish");
        languageIdToString.put("yor", "Yoruba");
        languageIdToString.put("ypk", "Yupik languages");
        languageIdToString.put("zap", "Zapotec");
        languageIdToString.put("zen", "Zenaga");
        languageIdToString.put("zha", "Zhuang; Chuang");
        languageIdToString.put("zho", "Chinese");
        languageIdToString.put("znd", "Zande");
        languageIdToString.put("zul", "Zulu");
        languageIdToString.put("zun", "Zuni");
        iterator = languageIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = (String) languageIdToString.get(key);
            languageStringToId.put(value, key);
        }
        textEncodingIdToString.put(new Long(0), "ISO-8859-1");
        textEncodingIdToString.put(new Long(1), "UTF-16");
        textEncodingIdToString.put(new Long(2), "UTF-16BE");
        textEncodingIdToString.put(new Long(3), "UTF-8");
        iterator = textEncodingIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) textEncodingIdToString.get(lkey);
            textEncodingStringToId.put(value, lkey);
        }
        interpolationMethodIdToString.put(new Long(0), "Band");
        interpolationMethodIdToString.put(new Long(1), "Linear");
        iterator = interpolationMethodIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) interpolationMethodIdToString.get(lkey);
            interpolationMethodStringToId.put(value, lkey);
        }
        pictureTypeIdToString.put(new Long(0), "Other");
        pictureTypeIdToString.put(new Long(1), "32x32 pixels 'file icon' (PNG only)");
        pictureTypeIdToString.put(new Long(2), "Other file icon");
        pictureTypeIdToString.put(new Long(3), "Cover (front)");
        pictureTypeIdToString.put(new Long(4), "Cover (back)");
        pictureTypeIdToString.put(new Long(5), "Leaflet page");
        pictureTypeIdToString.put(new Long(6), "Media (e.g. label side of CD)");
        pictureTypeIdToString.put(new Long(7), "Lead artist/lead performer/soloist");
        pictureTypeIdToString.put(new Long(8), "Artist/performer");
        pictureTypeIdToString.put(new Long(9), "Conductor");
        pictureTypeIdToString.put(new Long(10), "Band/Orchestra");
        pictureTypeIdToString.put(new Long(11), "Composer");
        pictureTypeIdToString.put(new Long(12), "Lyricist/text writer");
        pictureTypeIdToString.put(new Long(13), "Recording Location");
        pictureTypeIdToString.put(new Long(14), "During recording");
        pictureTypeIdToString.put(new Long(15), "During performance");
        pictureTypeIdToString.put(new Long(16), "Movie/video screen capture");
        pictureTypeIdToString.put(new Long(17), "A bright coloured fish");
        pictureTypeIdToString.put(new Long(18), "Illustration");
        pictureTypeIdToString.put(new Long(19), "Band/artist logotype");
        pictureTypeIdToString.put(new Long(20), "Publisher/Studio logotype");
        iterator = pictureTypeIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) pictureTypeIdToString.get(lkey);
            pictureTypeStringToId.put(value, lkey);
        }
        timeStampFormatIdToString.put(new Long(1), "Absolute time using MPEG [MPEG] frames as unit");
        timeStampFormatIdToString.put(new Long(2), "Absolute time using milliseconds as unit");
        iterator = timeStampFormatIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) timeStampFormatIdToString.get(lkey);
            timeStampFormatStringToId.put(value, lkey);
        }
        typeOfEventIdToString.put(new Long(0x00), "Padding (has no meaning)");
        typeOfEventIdToString.put(new Long(0x01), "End of initial silence");
        typeOfEventIdToString.put(new Long(0x02), "Intro start");
        typeOfEventIdToString.put(new Long(0x03), "Main part start");
        typeOfEventIdToString.put(new Long(0x04), "Outro start");
        typeOfEventIdToString.put(new Long(0x05), "Outro end");
        typeOfEventIdToString.put(new Long(0x06), "Verse start");
        typeOfEventIdToString.put(new Long(0x07), "Refrain start");
        typeOfEventIdToString.put(new Long(0x08), "Interlude start");
        typeOfEventIdToString.put(new Long(0x09), "Theme start");
        typeOfEventIdToString.put(new Long(0x0A), "Variation start");
        typeOfEventIdToString.put(new Long(0x0B), "Key change");
        typeOfEventIdToString.put(new Long(0x0C), "Time change");
        typeOfEventIdToString.put(new Long(0x0D), "Momentary unwanted noise (Snap, Crackle & Pop)");
        typeOfEventIdToString.put(new Long(0x0E), "Sustained noise");
        typeOfEventIdToString.put(new Long(0x0F), "Sustained noise end");
        typeOfEventIdToString.put(new Long(0x10), "Intro end");
        typeOfEventIdToString.put(new Long(0x11), "Main part end");
        typeOfEventIdToString.put(new Long(0x12), "Verse end");
        typeOfEventIdToString.put(new Long(0x13), "Refrain end");
        typeOfEventIdToString.put(new Long(0x14), "Theme end");
        typeOfEventIdToString.put(new Long(0x15), "Profanity");
        typeOfEventIdToString.put(new Long(0x16), "Profanity end");
        typeOfEventIdToString.put(new Long(0xFD), "Audio end (start of silence)");
        typeOfEventIdToString.put(new Long(0xFE), "Audio file ends");
        iterator = typeOfEventIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) typeOfEventIdToString.get(lkey);
            typeOfEventStringToId.put(value, lkey);
        }
        typeOfChannelIdToString.put(new Long(0x00), "Other");
        typeOfChannelIdToString.put(new Long(0x01), "Master volume");
        typeOfChannelIdToString.put(new Long(0x02), "Front right");
        typeOfChannelIdToString.put(new Long(0x03), "Front left");
        typeOfChannelIdToString.put(new Long(0x04), "Back right");
        typeOfChannelIdToString.put(new Long(0x05), "Back left");
        typeOfChannelIdToString.put(new Long(0x06), "Front centre");
        typeOfChannelIdToString.put(new Long(0x07), "Back centre");
        typeOfChannelIdToString.put(new Long(0x08), "Subwoofer");
        iterator = typeOfChannelIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) typeOfChannelIdToString.get(lkey);
            typeOfChannelStringToId.put(value, lkey);
        }
        recievedAsIdToString.put(new Long(0x00), "Other");
        recievedAsIdToString.put(new Long(0x01), "Standard CD album with other songs");
        recievedAsIdToString.put(new Long(0x02), "Compressed audio on CD");
        recievedAsIdToString.put(new Long(0x03), "File over the Internet");
        recievedAsIdToString.put(new Long(0x04), "Stream over the Internet");
        recievedAsIdToString.put(new Long(0x05), "As note sheets");
        recievedAsIdToString.put(new Long(0x06), "As note sheets in a book with other sheets");
        recievedAsIdToString.put(new Long(0x07), "Music on other media");
        recievedAsIdToString.put(new Long(0x08), "Non-musical merchandise");
        iterator = recievedAsIdToString.keySet().iterator();
        while (iterator.hasNext()) {
            lkey = (Long) iterator.next();
            value = (String) recievedAsIdToString.get(lkey);
            recievedAsStringToId.put(value, lkey);
        }
    }

    /**
     * Creates a new TagConstant object.
     */
    private TagConstant() {
        // keep people from instantiating this
    }
}