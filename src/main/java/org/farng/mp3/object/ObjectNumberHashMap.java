package org.farng.mp3.object;

import org.farng.mp3.TagConstant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class ObjectNumberHashMap extends ObjectNumberFixedLength implements ObjectHashMapInterface {

    public static final String GENRE = "Genre";
    public static final String TEXT_ENCODING = "Text Encoding";
    public static final String INTERPOLATION_METHOD = "Interpolation Method";
    public static final String ID3V2_FRAME_DESCRIPTION = "ID3v2 Frame Description";
    public static final String PICTURE_TYPE = "Picture Type";
    public static final String TYPE_OF_EVENT = "Type Of Event";
    public static final String TIME_STAMP_FORMAT = "Time Stamp Format";
    public static final String TYPE_OF_CHANNEL = "Type Of Channel";
    public static final String RECIEVED_AS = "Recieved As";
    private HashMap idToString = null;
    private HashMap stringToId = null;
    private boolean hasEmptyValue = false;

    /**
     * Creates a new ObjectNumberHashMap object.
     */
    public ObjectNumberHashMap(final String identifier, final int size) {
        super(identifier, size);
        if (identifier.equals(ObjectNumberHashMap.GENRE)) {
            this.stringToId = TagConstant.genreStringToId;
            this.idToString = TagConstant.genreIdToString;
            this.hasEmptyValue = true;
        } else if (identifier.equals(ObjectNumberHashMap.TEXT_ENCODING)) {
            this.stringToId = TagConstant.textEncodingStringToId;
            this.idToString = TagConstant.textEncodingIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.INTERPOLATION_METHOD)) {
            this.stringToId = TagConstant.interpolationMethodStringToId;
            this.idToString = TagConstant.interpolationMethodIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.ID3V2_FRAME_DESCRIPTION)) {
            this.stringToId = TagConstant.id3v2_4FrameStringToId;
            this.idToString = TagConstant.id3v2_4FrameIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.PICTURE_TYPE)) {
            this.stringToId = TagConstant.pictureTypeStringToId;
            this.idToString = TagConstant.pictureTypeIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.TYPE_OF_EVENT)) {
            this.stringToId = TagConstant.typeOfEventStringToId;
            this.idToString = TagConstant.typeOfEventIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.TIME_STAMP_FORMAT)) {
            this.stringToId = TagConstant.timeStampFormatStringToId;
            this.idToString = TagConstant.timeStampFormatIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.TYPE_OF_CHANNEL)) {
            this.stringToId = TagConstant.typeOfChannelStringToId;
            this.idToString = TagConstant.typeOfChannelIdToString;
        } else if (identifier.equals(ObjectNumberHashMap.RECIEVED_AS)) {
            this.stringToId = TagConstant.recievedAsStringToId;
            this.idToString = TagConstant.recievedAsIdToString;
        } else {
            throw new IllegalArgumentException("Hashmap identifier not defined in this class: " + identifier);
        }
    }

    /**
     * Creates a new ObjectNumberHashMap object.
     */
    public ObjectNumberHashMap(final ObjectNumberHashMap copyObject) {
        super(copyObject);
        this.hasEmptyValue = copyObject.hasEmptyValue;

        // we dont' need to clone/copy the maps here because they are static
        this.idToString = copyObject.idToString;
        this.stringToId = copyObject.stringToId;
    }

    public HashMap getIdToString() {
        return this.idToString;
    }

    public HashMap getStringToId() {
        return this.stringToId;
    }

    public void setValue(final Object value) {
        if (value instanceof Byte) {
            this.value = new Long(((Byte) value).byteValue());
        } else if (value instanceof Short) {
            this.value = new Long(((Short) value).shortValue());
        } else if (value instanceof Integer) {
            this.value = new Long(((Integer) value).intValue());
        } else {
            this.value = value;
        }
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectNumberHashMap) == false) {
            return false;
        }
        final ObjectNumberHashMap objectNumberHashMap = (ObjectNumberHashMap) obj;
        if (this.hasEmptyValue != objectNumberHashMap.hasEmptyValue) {
            return false;
        }
        if (this.idToString == null) {
            if (objectNumberHashMap.idToString != null) {
                return false;
            }
        } else {
            if (this.idToString.equals(objectNumberHashMap.idToString) == false) {
                return false;
            }
        }
        if (this.stringToId == null) {
            if (objectNumberHashMap.stringToId != null) {
                return false;
            }
        } else {
            if (this.stringToId.equals(objectNumberHashMap.stringToId) == false) {
                return false;
            }
        }
        return super.equals(obj);
    }

    public Iterator iterator() {
        if (this.idToString == null) {
            return null;
        }

        // put them in a treeset first to sort them
        final TreeSet treeSet = new TreeSet(this.idToString.values());
        if (this.hasEmptyValue) {
            treeSet.add("");
        }
        return treeSet.iterator();
    }

    public String toString() {
        if (this.value == null) {
            return "";
        } else if (this.idToString.get(this.value) == null) {
            return "";
        } else {
            return this.idToString.get(this.value).toString();
        }
    }
}