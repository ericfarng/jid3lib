package org.farng.mp3;

import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.FrameBodyCOMM;
import org.farng.mp3.id3.FrameBodyTIPL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Options that are used for every object and class in this library.
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public class TagOptionSingleton {

    private static final Map tagOptionTable = new HashMap();
    private static final String DEFAULT = "default";
    private static Object defaultOptions = TagOptionSingleton.DEFAULT;
    private Map keywordMap = new HashMap();
    /**
     * Map of lyric ID's to Boolean objects if we should or should not save the specific Kyrics3 field. Defaults to
     * true.
     */
    private Map lyrics3SaveFieldMap = new HashMap();
    /**
     * parenthesis map stuff
     */
    private Map parenthesisMap = new HashMap();
    /**
     * <code>HashMap</code> listing words to be replaced if found
     */
    private Map replaceWordMap = new HashMap();
    private List endWordDelimiterList = new LinkedList();
    /**
     * delimiters within a file name
     */
    private List filenameDelimiterList = new LinkedList();
    private List startWordDelimiterList = new LinkedList();
    /**
     * words to always set the case as upper or lower case
     */
    private List upperLowerCaseWordList = new LinkedList();
    /**
     * default language for any ID3v2 tags frameswhich require it. This string is in the [ISO-639-2] ISO/FDIS 639-2
     * definition
     */
    private String language = "eng";
    private boolean compositeMatchOverwrite = false;
    private boolean filenameTagSave = false;
    /**
     * if we should save any fields of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1Save = true;
    /**
     * if we should save the album field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveAlbum = true;
    /**
     * if we should save the artist field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveArtist = true;
    /**
     * if we should save the comment field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveComment = true;
    /**
     * if we should save the genre field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveGenre = true;
    /**
     * if we should save the title field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveTitle = true;
    /**
     * if we should save the track field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveTrack = true;
    /**
     * if we should save the year field of the ID3v1 tag or not. Defaults to true.
     */
    private boolean id3v1SaveYear = true;
    /**
     * if we should keep an empty ID3v2 frame while we're reading. This is different from a string of white space.
     * Defaults to false.
     */
    //todo I don't think I'm checking this right now
    private boolean id3v2KeepEmptyFrameIfRead = false;
    /**
     * When adjusting the ID3v2 padding, if should we copy the current ID3v2 tag to the new MP3 file. Defaults to true.
     */
    private boolean id3v2PaddingCopyTag = true;
    /**
     * When adjusting the ID3v2 padding, if we should shorten the length of the ID3v2 tag padding. Defaults to false.
     */
    private boolean id3v2PaddingWillShorten = false;
    /**
     * if we should save any fields of the ID3v2 tag or not. Defaults to true.
     */
    private boolean id3v2Save = true;
    /**
     * if we should save empty ID3v2 frames or not. Defaults to false.
     */
    //todo I don't think this is implemented yet.
    private boolean id3v2SaveEmptyFrame = false;
    /**
     * if we should save the ID3v2 extended header or not. Defaults to false.
     */
    //todo Not implemented yet
    private boolean id3v2SaveExtendedHeader = false;
    /**
     * if we should keep an empty Lyrics3 field while we're reading. This is different from a string of white space.
     * Defaults to false.
     */
    private boolean lyrics3KeepEmptyFieldIfRead = false;
    /**
     * if we should save any fields of the Lyrics3 tag or not. Defaults to true.
     */
    private boolean lyrics3Save = true;
    /**
     * if we should save empty Lyrics3 field or not. Defaults to false.
     */
    //todo I don't think this is implemented yet.
    private boolean lyrics3SaveEmptyField = false;
    private boolean originalSavedAfterAdjustingID3v2Padding = true;
    /**
     * default play counter size in bytes for the ID3v2 Tag.
     */
    //todo implement this.
    private byte playCounterSize = 4;
    /**
     * default text encoding for any ID3v2 tag frames which require it.
     */
    private byte textEncoding = 0;
    /**
     * default time stamp format for any ID3v2 tag frames which require it.
     */
    private byte timeStampFormat = 2;
    /**
     * factor to increase the id3v2 padding size. When the ID3v2 tag padding length is calculated and is not large
     * enough to fit the current ID3v2 tag, the padding length will be multiplied by this number until it is large
     * enough.
     */
    private float id3v2PaddingMultiplier = 2;
    private int defaultSaveMode = TagConstant.MP3_FILE_SAVE_WRITE;
    /**
     * padding length of the ID3v2 tag.
     */
    private int id3v2PaddingSize = 2048;
    /**
     * number of frames to sync when trying to find the start of the MP3 frame data. The start of the MP3 frame data is
     * the start of the music and is different from the ID3v2 frame data.
     */
    private int numberMP3SyncFrame = 5;

    /**
     * Creates a new TagOptions object. All Options are set to their default values
     */
    private TagOptionSingleton() {
        setToDefault();
    }

    public static TagOptionSingleton getInstance() {
        return getInstance(defaultOptions);
    }

    public static TagOptionSingleton getInstance(final Object instanceKey) {
        TagOptionSingleton tagOptions = (TagOptionSingleton) tagOptionTable.get(instanceKey);
        if (tagOptions == null) {
            tagOptions = new TagOptionSingleton();
            tagOptionTable.put(instanceKey, tagOptions);
        }
        return tagOptions;
    }

    public String getCloseParenthesis(final String open) {
        return (String) parenthesisMap.get(open);
    }

    public boolean isCloseParenthesis(final String close) {
        return parenthesisMap.containsValue(close);
    }

    public void setCompositeMatchOverwrite(final boolean compositeMatchOverwrite) {
        this.compositeMatchOverwrite = compositeMatchOverwrite;
    }

    public boolean isCompositeMatchOverwrite() {
        return compositeMatchOverwrite;
    }

    public void setDefaultSaveMode(final int defaultSaveMode) {
        this.defaultSaveMode = defaultSaveMode;
    }

    public int getDefaultSaveMode() {
        return this.defaultSaveMode;
    }

    public void setFilenameTagSave(final boolean filenameTagSave) {
        this.filenameTagSave = filenameTagSave;
    }

    public boolean isFilenameTagSave() {
        return this.filenameTagSave;
    }

    public boolean isId3v2SaveExtendedHeader() {
        return this.id3v2SaveExtendedHeader;
    }

    public void setInstanceKey(final Object instanceKey) {
        TagOptionSingleton.defaultOptions = instanceKey;
    }

    public static Object getInstanceKey() {
        return defaultOptions;
    }

    public Iterator getEndWordDelimiterIterator() {
        return this.endWordDelimiterList.iterator();
    }

    public Iterator getFilenameDelimiterIterator() {
        return this.filenameDelimiterList.iterator();
    }

    public void setId3v1Save(final boolean id3v1Save) {
        this.id3v1Save = id3v1Save;
    }

    public boolean isId3v1Save() {
        return this.id3v1Save;
    }

    public void setId3v1SaveAlbum(final boolean id3v1SaveAlbum) {
        this.id3v1SaveAlbum = id3v1SaveAlbum;
    }

    public boolean isId3v1SaveAlbum() {
        return this.id3v1SaveAlbum;
    }

    public void setId3v1SaveArtist(final boolean id3v1SaveArtist) {
        this.id3v1SaveArtist = id3v1SaveArtist;
    }

    public boolean isId3v1SaveArtist() {
        return this.id3v1SaveArtist;
    }

    public void setId3v1SaveComment(final boolean id3v1SaveComment) {
        this.id3v1SaveComment = id3v1SaveComment;
    }

    public boolean isId3v1SaveComment() {
        return this.id3v1SaveComment;
    }

    public void setId3v1SaveGenre(final boolean id3v1SaveGenre) {
        this.id3v1SaveGenre = id3v1SaveGenre;
    }

    public boolean isId3v1SaveGenre() {
        return this.id3v1SaveGenre;
    }

    public void setId3v1SaveTitle(final boolean id3v1SaveTitle) {
        this.id3v1SaveTitle = id3v1SaveTitle;
    }

    public boolean isId3v1SaveTitle() {
        return this.id3v1SaveTitle;
    }

    public void setId3v1SaveTrack(final boolean id3v1SaveTrack) {
        this.id3v1SaveTrack = id3v1SaveTrack;
    }

    public boolean isId3v1SaveTrack() {
        return this.id3v1SaveTrack;
    }

    public void setId3v1SaveYear(final boolean id3v1SaveYear) {
        this.id3v1SaveYear = id3v1SaveYear;
    }

    public boolean isId3v1SaveYear() {
        return this.id3v1SaveYear;
    }

    public void setId3v2KeepEmptyFrameIfRead(final boolean id3v2KeepEmptyFrameIfRead) {
        this.id3v2KeepEmptyFrameIfRead = id3v2KeepEmptyFrameIfRead;
    }

    public boolean isId3v2KeepEmptyFrameIfRead() {
        return this.id3v2KeepEmptyFrameIfRead;
    }

    public void setId3v2PaddingCopyTag(final boolean id3v2PaddingCopyTag) {
        this.id3v2PaddingCopyTag = id3v2PaddingCopyTag;
    }

    public boolean isId3v2PaddingCopyTag() {
        return this.id3v2PaddingCopyTag;
    }

    /**
     * Sets the factor to increase the id3v2 padding size. When the ID3v2 tag padding length is calculated and is not
     * large enough to fit the current ID3v2 tag, the padding length will be multiplied by this number until it is large
     * enough.
     *
     * @param mult new factor to increase the id3v2 padding size.
     */
    public void setId3v2PaddingMultiplier(final float mult) {
        if (mult > 1) {
            this.id3v2PaddingMultiplier = mult;
        }
    }

    /**
     * Returns the factor to increase the id3v2 padding size. When the ID3v2 tag padding length is calculated and is not
     * large enough to fit the current ID3v2 tag, the padding length will be multiplied by this number until it is large
     * enough.
     *
     * @return the factor to increase the id3v2 padding size
     */
    public float getId3v2PaddingMultiplier() {
        return this.id3v2PaddingMultiplier;
    }

    /**
     * Sets the initial ID3v2 padding length. This will be the minimum padding length of the ID3v2 tag. The
     * <code>willShorten</code> setting will not make the length shorter than this value.
     *
     * @param size the new initial ID3v2 padding length
     */
    public void setId3v2PaddingSize(final int size) {
        if (size >= 0) {
            this.id3v2PaddingSize = size;
        }
    }

    /**
     * Returns the initial ID3v2 padding length. This will be the minimum padding length of the ID3v2 tag. The
     * <code>willShorten</code> setting will not make the length shorter than this value.
     *
     * @return the initial ID3v2 padding length
     */
    public int getId3v2PaddingSize() {
        return this.id3v2PaddingSize;
    }

    public void setId3v2PaddingWillShorten(final boolean id3v2PaddingWillShorten) {
        this.id3v2PaddingWillShorten = id3v2PaddingWillShorten;
    }

    public boolean isId3v2PaddingWillShorten() {
        return this.id3v2PaddingWillShorten;
    }

    public void setId3v2Save(final boolean id3v2Save) {
        this.id3v2Save = id3v2Save;
    }

    public boolean isId3v2Save() {
        return this.id3v2Save;
    }

    public void setId3v2SaveEmptyFrame(final boolean id3v2SaveEmptyFrame) {
        this.id3v2SaveEmptyFrame = id3v2SaveEmptyFrame;
    }

    public boolean isId3v2SaveEmptyFrame() {
        return this.id3v2SaveEmptyFrame;
    }

    public void setId3v2SaveExtendedHeader(final boolean id3v2SaveExtendedHeader) {
        this.id3v2SaveExtendedHeader = id3v2SaveExtendedHeader;
    }

    public Iterator getKeywordIterator() {
        return this.keywordMap.keySet().iterator();
    }

    public Iterator getKeywordListIterator(final Class id3v2_4FrameBody) {
        return ((LinkedList) this.keywordMap.get(id3v2_4FrameBody)).iterator();
    }

    /**
     * Sets the default language for any ID3v2 tag frames which require it. While the value will already exist when
     * reading from a file, this value will be used when a new ID3v2 Frame is created from scratch.
     *
     * @param language language ID, [ISO-639-2] ISO/FDIS 639-2 definition
     */
    public void setLanguage(final String language) {
        if (TagConstant.languageIdToString.containsKey(language)) {
            this.language = language;
        }
    }

    /**
     * Returns the default language for any ID3v2 tag frames which require it.
     *
     * @return language ID, [ISO-639-2] ISO/FDIS 639-2 definition
     */
    public String getLanguage() {
        return this.language;
    }

    public void setLyrics3KeepEmptyFieldIfRead(final boolean lyrics3KeepEmptyFieldIfRead) {
        this.lyrics3KeepEmptyFieldIfRead = lyrics3KeepEmptyFieldIfRead;
    }

    public boolean isLyrics3KeepEmptyFieldIfRead() {
        return this.lyrics3KeepEmptyFieldIfRead;
    }

    public void setLyrics3Save(final boolean lyrics3Save) {
        this.lyrics3Save = lyrics3Save;
    }

    public boolean isLyrics3Save() {
        return this.lyrics3Save;
    }

    public void setLyrics3SaveEmptyField(final boolean lyrics3SaveEmptyField) {
        this.lyrics3SaveEmptyField = lyrics3SaveEmptyField;
    }

    public boolean isLyrics3SaveEmptyField() {
        return this.lyrics3SaveEmptyField;
    }

    /**
     * Sets if we should save the Lyrics3 field. Defaults to true.
     *
     * @param id   Lyrics3 id string
     * @param save true if you want to save this specific Lyrics3 field.
     */
    public void setLyrics3SaveField(final String id, final boolean save) {
        this.lyrics3SaveFieldMap.put(id, new Boolean(save));
    }

    /**
     * Returns true if we should save the Lyrics3 field asked for in the argument. Defaults to true.
     *
     * @param id Lyrics3 id string
     *
     * @return true if we should save the Lyrics3 field.
     */
    public boolean getLyrics3SaveField(final String id) {
        return ((Boolean) this.lyrics3SaveFieldMap.get(id)).booleanValue();
    }

    public Map getLyrics3SaveFieldMap() {
        return this.lyrics3SaveFieldMap;
    }

    public String getNewReplaceWord(final String oldWord) {
        return (String) this.replaceWordMap.get(oldWord);
    }

    /**
     * Sets the number of MP3 frames to sync when trying to find the start of the MP3 frame data. The start of the MP3
     * frame data is the start of the music and is different from the ID3v2 frame data. WinAmp 2.8 seems to sync 3
     * frames. Default is 5.
     *
     * @param numberMP3SyncFrame number of MP3 frames to sync
     */
    public void setNumberMP3SyncFrame(final int numberMP3SyncFrame) {
        this.numberMP3SyncFrame = numberMP3SyncFrame;
    }

    /**
     * Returns the number of MP3 frames to sync when trying to find the start of the MP3 frame data. The start of the
     * MP3 frame data is the start of the music and is different from the ID3v2 frame data. WinAmp 2.8 seems to sync 3
     * frames. Default is 5.
     *
     * @return number of MP3 frames to sync
     */
    public int getNumberMP3SyncFrame() {
        return this.numberMP3SyncFrame;
    }

    public Iterator getOldReplaceWordIterator() {
        return this.replaceWordMap.keySet().iterator();
    }

    public boolean isOpenParenthesis(final String open) {
        return this.parenthesisMap.containsKey(open);
    }

    public Iterator getOpenParenthesisIterator() {
        return this.parenthesisMap.keySet().iterator();
    }

    public void setOriginalSavedAfterAdjustingID3v2Padding(final boolean originalSavedAfterAdjustingID3v2Padding) {
        this.originalSavedAfterAdjustingID3v2Padding = originalSavedAfterAdjustingID3v2Padding;
    }

    public boolean isOriginalSavedAfterAdjustingID3v2Padding() {
        return this.originalSavedAfterAdjustingID3v2Padding;
    }

    /**
     * Sets the default play counter size for the PCNT ID3v2 frame. While the value will already exist when reading from
     * a file, this value will be used when a new ID3v2 Frame is created from scratch.
     *
     * @param size the default play counter size for the PCNT ID3v2 frame
     */
    public void setPlayCounterSize(final byte size) {
        if (size > 0) {
            this.playCounterSize = size;
        }
    }

    /**
     * Returns the default play counter size for the PCNT ID3v2 frame.
     *
     * @return the default play counter size for the PCNT ID3v2 frame
     */
    public byte getPlayCounterSize() {
        return this.playCounterSize;
    }

    public Iterator getStartWordDelimiterIterator() {
        return this.startWordDelimiterList.iterator();
    }

    /**
     * Sets the default text encoding for any ID3v2 tag frames which require it. While the value will already exist when
     * reading from a file, this value will be used when a new ID3v2 Frame is created from scratch.
     * <p/>
     * <P> $00 ISO-8859-1 [ISO-8859-1]. Terminated with $00. <BR> $01 UTF-16 [UTF-16] encoded Unicode [UNICODE] with
     * BOM. All strings in the same frame SHALL have the same byteorder. Terminated with $00 00. <BR> $02 UTF-16BE
     * [UTF-16] encoded Unicode [UNICODE] without BOM. Terminated with $00 00. <BR> $03 UTF-8 [UTF-8] encoded Unicode
     * [UNICODE]. Terminated with $00. <BR> </p>
     *
     * @param enc new default text encoding
     */
    public void setTextEncoding(final byte enc) {
        if ((enc >= 0) && (enc <= 3)) {
            this.textEncoding = enc;
        }
    }

    /**
     * Returns the default text encoding format for ID3v2 tags which require it.
     * <p/>
     * <P> $00 ISO-8859-1 [ISO-8859-1]. Terminated with $00. <BR> $01 UTF-16 [UTF-16] encoded Unicode [UNICODE] with
     * BOM. All strings in the same frame SHALL have the same byteorder. Terminated with $00 00. <BR> $02 UTF-16BE
     * [UTF-16] encoded Unicode [UNICODE] without BOM. Terminated with $00 00. <BR> $03 UTF-8 [UTF-8] encoded Unicode
     * [UNICODE]. Terminated with $00. <BR> </p>
     *
     * @return the default text encoding
     */
    public byte getTextEncoding() {
        return this.textEncoding;
    }

    /**
     * Sets the default time stamp format for ID3v2 tags which require it. While the value will already exist when
     * reading from a file, this value will be used when a new ID3v2 Frame is created from scratch.
     * <p/>
     * <P> $01 Absolute time, 32 bit sized, using MPEG frames as unit <br> $02 Absolute time, 32 bit sized, using
     * milliseconds as unit <br> </p>
     *
     * @param tsf the new default time stamp format
     */
    public void setTimeStampFormat(final byte tsf) {
        if ((tsf == 1) || (tsf == 2)) {
            this.timeStampFormat = tsf;
        }
    }

    /**
     * Returns the default time stamp format for ID3v2 tags which require it.
     * <p/>
     * <P> $01 Absolute time, 32 bit sized, using MPEG frames as unit <br> $02 Absolute time, 32 bit sized, using
     * milliseconds as unit <br> </p>
     *
     * @return the default time stamp format
     */
    public byte getTimeStampFormat() {
        return this.timeStampFormat;
    }

    public void setToDefault() {
        this.keywordMap = new HashMap();
        this.compositeMatchOverwrite = false;
        this.defaultSaveMode = TagConstant.MP3_FILE_SAVE_WRITE;
        this.endWordDelimiterList = new LinkedList();
        this.filenameDelimiterList = new LinkedList();
        this.filenameTagSave = false;
        this.id3v1Save = true;
        this.id3v1SaveAlbum = true;
        this.id3v1SaveArtist = true;
        this.id3v1SaveComment = true;
        this.id3v1SaveGenre = true;
        this.id3v1SaveTitle = true;
        this.id3v1SaveTrack = true;
        this.id3v1SaveYear = true;
        this.id3v2KeepEmptyFrameIfRead = false;
        this.id3v2PaddingCopyTag = true;
        this.id3v2PaddingWillShorten = false;
        this.id3v2Save = true;
        this.id3v2SaveEmptyFrame = false;
        this.id3v2SaveExtendedHeader = false;
        this.id3v2PaddingMultiplier = 2;
        this.id3v2PaddingSize = 2048;
        this.language = "eng";
        this.lyrics3KeepEmptyFieldIfRead = false;
        this.lyrics3Save = true;
        this.lyrics3SaveEmptyField = false;
        this.lyrics3SaveFieldMap = new HashMap();
        this.numberMP3SyncFrame = 5;
        this.parenthesisMap = new HashMap();
        this.playCounterSize = 4;
        this.replaceWordMap = new HashMap();
        this.startWordDelimiterList = new LinkedList();
        this.textEncoding = 0;
        this.timeStampFormat = 2;
        this.upperLowerCaseWordList = new LinkedList();

        /**
         * default all lyrics3 fields to save. id3v1 fields are individual
         * settings. id3v2 fields are always looked at to save.
         */
        Iterator iterator = TagConstant.lyrics3v2FieldIdToString.keySet().iterator();
        String fieldId;
        while (iterator.hasNext()) {
            fieldId = (String) iterator.next();
            this.lyrics3SaveFieldMap.put(fieldId, new Boolean(true));
        }
        try {
            addKeyword(FrameBodyCOMM.class, "ultimix");
            addKeyword(FrameBodyCOMM.class, "dance");
            addKeyword(FrameBodyCOMM.class, "mix");
            addKeyword(FrameBodyCOMM.class, "remix");
            addKeyword(FrameBodyCOMM.class, "rmx");
            addKeyword(FrameBodyCOMM.class, "live");
            addKeyword(FrameBodyCOMM.class, "cover");
            addKeyword(FrameBodyCOMM.class, "soundtrack");
            addKeyword(FrameBodyCOMM.class, "version");
            addKeyword(FrameBodyCOMM.class, "acoustic");
            addKeyword(FrameBodyCOMM.class, "original");
            addKeyword(FrameBodyCOMM.class, "cd");
            addKeyword(FrameBodyCOMM.class, "extended");
            addKeyword(FrameBodyCOMM.class, "vocal");
            addKeyword(FrameBodyCOMM.class, "unplugged");
            addKeyword(FrameBodyCOMM.class, "acapella");
            addKeyword(FrameBodyCOMM.class, "edit");
            addKeyword(FrameBodyCOMM.class, "radio");
            addKeyword(FrameBodyCOMM.class, "original");
            addKeyword(FrameBodyCOMM.class, "album");
            addKeyword(FrameBodyCOMM.class, "studio");
            addKeyword(FrameBodyCOMM.class, "instrumental");
            addKeyword(FrameBodyCOMM.class, "unedited");
            addKeyword(FrameBodyCOMM.class, "karoke");
            addKeyword(FrameBodyCOMM.class, "quality");
            addKeyword(FrameBodyCOMM.class, "uncensored");
            addKeyword(FrameBodyCOMM.class, "clean");
            addKeyword(FrameBodyCOMM.class, "dirty");
            addKeyword(FrameBodyTIPL.class, "f.");
            addKeyword(FrameBodyTIPL.class, "feat");
            addKeyword(FrameBodyTIPL.class, "feat.");
            addKeyword(FrameBodyTIPL.class, "featuring");
            addKeyword(FrameBodyTIPL.class, "ftng");
            addKeyword(FrameBodyTIPL.class, "ftng.");
            addKeyword(FrameBodyTIPL.class, "ft.");
            addKeyword(FrameBodyTIPL.class, "ft");
            iterator = TagConstant.genreStringToId.keySet().iterator();
            while (iterator.hasNext()) {
                addKeyword(FrameBodyCOMM.class, (String) iterator.next());
            }
        } catch (TagException ex) {
            // this shouldn't happen. if it does, we should fix it right away.
            ex.printStackTrace();
        }
        addUpperLowerCaseWord("a");
        addUpperLowerCaseWord("in");
        addUpperLowerCaseWord("of");
        addUpperLowerCaseWord("the");
        addUpperLowerCaseWord("on");
        addUpperLowerCaseWord("is");
        addUpperLowerCaseWord("it");
        addUpperLowerCaseWord("to");
        addUpperLowerCaseWord("at");
        addUpperLowerCaseWord("an");
        addUpperLowerCaseWord("and");
        addUpperLowerCaseWord("but");
        addUpperLowerCaseWord("or");
        addUpperLowerCaseWord("for");
        addUpperLowerCaseWord("nor");
        addUpperLowerCaseWord("not");
        addUpperLowerCaseWord("so");
        addUpperLowerCaseWord("yet");
        addUpperLowerCaseWord("with");
        addUpperLowerCaseWord("into");
        addUpperLowerCaseWord("by");
        addUpperLowerCaseWord("up");
        addUpperLowerCaseWord("as");
        addUpperLowerCaseWord("if");
        addUpperLowerCaseWord("feat.");
        addUpperLowerCaseWord("vs.");
        addUpperLowerCaseWord("I'm");
        addUpperLowerCaseWord("I");
        addUpperLowerCaseWord("I've");
        addUpperLowerCaseWord("I'll");
        addReplaceWord("v.", "vs.");
        addReplaceWord("vs.", "vs.");
        addReplaceWord("versus", "vs.");
        addReplaceWord("f.", "feat.");
        addReplaceWord("feat", "feat.");
        addReplaceWord("featuring", "feat.");
        addReplaceWord("ftng.", "feat.");
        addReplaceWord("ftng", "feat.");
        addReplaceWord("ft.", "feat.");
        addReplaceWord("ft", "feat.");
        addFilenameDelimiter("/");
        addFilenameDelimiter("\\");
        addFilenameDelimiter(" -");
        addFilenameDelimiter(";");
        addFilenameDelimiter("|");
        addFilenameDelimiter(":");
        iterator = this.getKeywordListIterator(FrameBodyTIPL.class);
        while (iterator.hasNext()) {
            addStartWordDelimiter((String) iterator.next());
        }
        addParenthesis("(", ")");
        addParenthesis("[", "]");
        addParenthesis("{", "}");
        addParenthesis("<", ">");
    }

    public Iterator getUpperLowerCaseWordListIterator() {
        return this.upperLowerCaseWordList.iterator();
    }

    public void addEndWordDelimiter(final String wordDelimiter) {
        this.endWordDelimiterList.add(wordDelimiter);
    }

    public void addFilenameDelimiter(final String delimiter) {
        this.filenameDelimiterList.add(delimiter);
    }

    public void addKeyword(final Class id3v2FrameBodyClass, final String keyword) throws TagException {
        if (AbstractID3v2FrameBody.class.isAssignableFrom(id3v2FrameBodyClass) == false) {
            throw new TagException("Invalid class type. Must be AbstractId3v2FrameBody " + id3v2FrameBodyClass);
        }
        if ((keyword != null) && (keyword.length() > 0)) {
            final LinkedList keywordList;
            if (this.keywordMap.containsKey(id3v2FrameBodyClass) == false) {
                keywordList = new LinkedList();
                this.keywordMap.put(id3v2FrameBodyClass, keywordList);
            } else {
                keywordList = (LinkedList) this.keywordMap.get(id3v2FrameBodyClass);
            }
            keywordList.add(keyword);
        }
    }

    public void addParenthesis(final String open, final String close) {
        this.parenthesisMap.put(open, close);
    }

    public void addReplaceWord(final String oldWord, final String newWord) {
        this.replaceWordMap.put(oldWord, newWord);
    }

    public void addStartWordDelimiter(final String wordDelimiter) {
        this.startWordDelimiterList.add(wordDelimiter);
    }

    public void addUpperLowerCaseWord(final String word) {
        this.upperLowerCaseWordList.add(word);
    }
}