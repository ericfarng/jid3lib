package org.farng.mp3.object;

import org.farng.mp3.TagUtility;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectGroupRepeated extends AbstractMP3Object {

    private ArrayList objectList;
    private ArrayList propertyList;

    /**
     * Creates a new ObjectGroupRepeated object.
     */
    public ObjectGroupRepeated(final String identifier) {
        this.identifier = identifier;
        this.propertyList = new ArrayList();
        this.objectList = new ArrayList();
    }

    /**
     * Creates a new ObjectGroupRepeated object.
     */
    public ObjectGroupRepeated(final ObjectGroupRepeated copyObject) {
        super(copyObject);
        AbstractMP3Object newObject;
        for (int i = 0; i < copyObject.objectList.size(); i++) {
            newObject = (AbstractMP3Object) TagUtility.copyObject(copyObject.objectList.get(i));
            this.objectList.add(newObject);
        }
        for (int i = 0; i < copyObject.propertyList.size(); i++) {
            newObject = (AbstractMP3Object) TagUtility.copyObject(copyObject.propertyList.get(i));
            this.propertyList.add(newObject);
        }
    }

    public ArrayList getObjectList() {
        return this.objectList;
    }

    public ArrayList getPropertyList() {
        return this.propertyList;
    }

    public int getSize() {
        int size = 0;
        AbstractMP3Object object;
        final Iterator iterator = this.objectList.listIterator();
        while (iterator.hasNext()) {
            object = (AbstractMP3Object) iterator.next();
            size += object.getSize();
        }
        return size;
    }

    public void addObject(final AbstractMP3Object object) {
        this.objectList.add(object);
    }

    public void addProperty(final AbstractMP3Object object) {
        this.propertyList.add(object);
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectGroupRepeated) == false) {
            return false;
        }
        final ObjectGroupRepeated objectGroupRepeated = (ObjectGroupRepeated) obj;
        if (this.objectList.equals(objectGroupRepeated.objectList) == false) {
            return false;
        }
        if (this.propertyList.equals(objectGroupRepeated.propertyList) == false) {
            return false;
        }
        return super.equals(obj);
    }

    public void readByteArray(final byte[] arr, int offset) {
        if (arr == null) {
            throw new NullPointerException("Byte array is null");
        }
        if ((offset < 0) || (offset >= arr.length)) {
            throw new IndexOutOfBoundsException("Offset to byte array is out of bounds: offset = " +
                                                offset +
                                                ", array.length = " +
                                                arr
                                                        .length);
        }
        AbstractMP3Object object;
        Class className;
        Iterator iterator;
        if (this.propertyList.size() > 0) {
            while (offset < arr.length) {
                iterator = this.propertyList.listIterator();
                while (iterator.hasNext()) {
                    className = iterator.next().getClass();
                    try {
                        object = (AbstractMP3Object) className.newInstance();
                        this.objectList.add(object);
                        object.readByteArray(arr, offset);
                        offset += object.getSize();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();

                        // do nothing, just skip this one
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();

                        // do nothing, just skip this one
                    }
                }
            }
        }
    }

    public String toString() {
        String str = "";
        AbstractMP3Object object;
        final Iterator iterator = this.objectList.listIterator();
        while (iterator.hasNext()) {
            object = (AbstractMP3Object) iterator.next();
            str += (object.toString() + "\n");
        }
        return str;
    }

    public byte[] writeByteArray() {
        AbstractMP3Object object;
        final byte[] totalArray = new byte[this.getSize()];
        byte[] objectArray;
        final Iterator iterator = this.objectList.listIterator();
        while (iterator.hasNext()) {
            object = (AbstractMP3Object) iterator.next();
            objectArray = object.writeByteArray();
            System.arraycopy(objectArray, 0, totalArray, 0, totalArray.length);
        }
        return totalArray;
    }
}