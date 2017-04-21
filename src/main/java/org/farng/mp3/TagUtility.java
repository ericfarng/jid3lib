package org.farng.mp3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import org.farng.mp3.id3.AbstractID3v2FrameBody;

/**
 * This is a holder class that contains static methods that I use in my library. They may or may not be useful for
 * anyone else extending the library.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class TagUtility {

    /**
     * integer difference between ASCII 'A' and ASCII 'a'
     */
    private static final int UPPERCASE;
    /**
     * Convenience <code>HashMap</code> to help fix capitilization of words. It maps all words in
     * <code>TagConstants.upperLowerCase</code> from all lower case to their desired capitilziation.
     */
    private static final Map capitalizationMap;

    static {
        UPPERCASE = (int) 'A' - (int) 'a';
        capitalizationMap = new HashMap(32);
        final Iterator iterator = TagOptionSingleton.getInstance().getUpperLowerCaseWordListIterator();
        while (iterator.hasNext()) {
            final String word = (String) iterator.next();
            capitalizationMap.put(word.toLowerCase(), word);
        }
    }

    /**
     * Creates a new TagUtility object.
     */
    private TagUtility() {
        super();
    }

    /**
     * Given an ID, get the ID3v2 frame description or the Lyrics3 field description. This takes any kind of ID (four or
     * three letter ID3v2 IDs, and three letter Lyrics3 IDs)
     *
     * @param identifier frame identifier
     *
     * @return frame description
     */
    public static String getFrameDescription(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        String returnValue = null;
        if (identifier.length() > 2) {
            if (identifier.length() == 4) {
                final String idPrefix = identifier.substring(0, 4);
                returnValue = (String) TagConstant.id3v2_4FrameIdToString.get(idPrefix);
                if (returnValue == null) {
                    returnValue = (String) TagConstant.id3v2_3FrameIdToString.get(idPrefix);
                }
            }
            if (returnValue == null) {
                returnValue = (String) TagConstant.id3v2_2FrameIdToString.get(identifier.substring(0, 3));
            }
            if (returnValue == null) {
                returnValue = (String) TagConstant.lyrics3v2FieldIdToString.get(identifier.substring(0, 3));
            }
        }
        return returnValue;
    }

    /**
     * Returns true if the identifier is a valid ID3v2.2 frame identifier
     *
     * @param identifier string to test
     *
     * @return true if the identifier is a valid ID3v2.2 frame identifier
     */
    public static boolean isID3v2_2FrameIdentifier(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 3) {
            return false;
        } else if (identifier.length() == 3) {
            return TagConstant.id3v2_2FrameIdToString.containsKey(identifier);
        } else {
            final String upperIdentifier = identifier.toUpperCase();
            if (upperIdentifier.charAt(3) >= 'A' && upperIdentifier.charAt(3) <= 'Z') {
                return TagConstant.id3v2_2FrameIdToString.containsKey(upperIdentifier.substring(0, 4));
            }
            return TagConstant.id3v2_2FrameIdToString.containsKey(upperIdentifier.subSequence(0, 3));
        }
    }

    /**
     * Returns true if the identifier is a valid ID3v2.3 frame identifier
     *
     * @param identifier string to test
     *
     * @return true if the identifier is a valid ID3v2.3 frame identifier
     */
    public static boolean isID3v2_3FrameIdentifier(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return false;
        }
        return TagConstant.id3v2_3FrameIdToString.containsKey(identifier.substring(0, 4));
    }

    /**
     * Returns true if the identifier is a valid ID3v2.4 frame identifier
     *
     * @param identifier string to test
     *
     * @return true if the identifier is a valid ID3v2.4 frame identifier
     */
    public static boolean isID3v2_4FrameIdentifier(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return false;
        }
        return TagConstant.id3v2_4FrameIdToString.containsKey(identifier.substring(0, 4));
    }

    /**
     * Returns true if the identifier is a valid Lyrics3v2 frame identifier
     *
     * @param identifier string to test
     *
     * @return true if the identifier is a valid Lyrics3v2 frame identifier
     */
    public static boolean isLyrics3v2FieldIdentifier(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 3) {
            return false;
        }
        return TagConstant.lyrics3v2FieldIdToString.containsKey(identifier.substring(0, 3));
    }

    /**
     * Returns true if the string has matching parenthesis. This method matches all four parenthesis and also enclosed
     * parenthesis.
     *
     * @param str string to test
     *
     * @return true if the string has matching parenthesis
     */
    public static boolean isMatchingParenthesis(final String str) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        final TagOptionSingleton option = TagOptionSingleton.getInstance();
        final int length = str.length();
        for (int i = 0; i < length; i++) {
            final char ch = str.charAt(i);
            if (option.isCloseParenthesis(Character.toString(ch))) {
                return false;
            }
            if (option.isOpenParenthesis(Character.toString(ch))) {
                i = findMatchingParenthesis(str, i);
                if (i < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Given an object, try to return it as a <code>long</code>. This tries to parse a string, and takes <code>Long,
     * Short, Byte, Integer</code> objects and gets their value. An exception is not explicityly thrown here because it
     * would causes too many other methods to also throw it.
     *
     * @param value object to find long from.
     *
     * @return <code>long</code> value
     */
    public static long getWholeNumber(final Object value) {
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        final long number;
        if (value instanceof String) {
            number = Long.parseLong((String) value);
        } else if (value instanceof Byte) {
            number = ((Byte) value).byteValue();
        } else if (value instanceof Short) {
            number = ((Short) value).shortValue();
        } else if (value instanceof Integer) {
            number = ((Integer) value).intValue();
        } else if (value instanceof Long) {
            number = ((Long) value).longValue();
        } else {
            throw new IllegalArgumentException("Unsupported value class: " + value.getClass().getName());
        }
        return number;
    }

    /**
     * Add a timestamp string to a given string. This is used in the GUI and I'm not sure why it is defined here.
     *
     * @param text    textarea string to insert to
     * @param origPos current position of the cursor
     *
     * @return new string to use in the text area
     */
    public static String addTimeStampToTextArea(final String text, final int origPos) {
        //todo move this to a GUI class
        //todo fix the case of adding time stamp to EOLN, EOLN, EOF (adding
        final String newText;
        if (text.length() == 0) { // special empty case
            newText = "[00:00]";
        } else {
            int i = origPos;
            i = Math.min(i, text.length() - 1); // if at end of whole string
            if (text.charAt(i) == '\n') {
                i--; // if at the end of line
            }
            for (; i > 0; i--) {
                if (text.charAt(i) == '\n') {
                    break;
                }
            }
            if (i == 0) { // if at very first character
                newText = "[00:00]" + text;
            } else {
                i++;
                final String before = text.substring(0, i);
                final String after = text.substring(i);
                newText = before + "[00:00]" + after;
            }
        }
        return newText;
    }

    public static String appendBeforeExtension(final String filename, final String addition) {
        if (addition == null) {
            return filename;
        }
        if (filename == null) {
            return addition;
        }
        final int index = filename.lastIndexOf('.');
        if (index < 0) {
            return filename + addition;
        }
        return filename.substring(0, index) + addition + filename.substring(index);
    }

    public static String convertFrameID2_2to2_3(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 3) {
            return null;
        }
        return (String) TagConstant.id3v2_2ToId3v2_3.get(identifier.subSequence(0, 3));
    }

    public static String convertFrameID2_2to2_4(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 3) {
            return null;
        }
        String id = (String) TagConstant.id3v2_2ToId3v2_3.get(identifier.substring(0, 3));
        if (id != null) {
            id = (String) TagConstant.id3v2_3ToId3v2_4.get(id);
        }
        return id;
    }

    public static String convertFrameID2_3to2_2(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return null;
        }
        return (String) TagConstant.id3v2_3ToId3v2_2.get(identifier.substring(0, 4));
    }

    public static String convertFrameID2_3to2_4(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return null;
        }
        return (String) TagConstant.id3v2_3ToId3v2_4.get(identifier.substring(0, 4));
    }

    public static String convertFrameID2_4to2_2(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return null;
        }
        String id = (String) TagConstant.id3v2_4ToId3v2_3.get(identifier.substring(0, 4));
        if (id != null) {
            id = (String) TagConstant.id3v2_3ToId3v2_2.get(id);
        }
        return id;
    }

    public static String convertFrameID2_4to2_3(final String identifier) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        if (identifier.length() < 4) {
            return null;
        }
        return (String) TagConstant.id3v2_4ToId3v2_3.get(identifier);
    }

    public static AbstractID3v2FrameBody createFrameBody(final String identifier, final AbstractID3v2FrameBody body) {
        if (identifier == null) {
            throw new NullPointerException("Identifier is null");
        }
        try {
            Class clazz = Class.forName("org.farng.mp3.id3.FrameBody" + identifier);
            for (Constructor constructor : clazz.getConstructors()) {
                if (constructor.getParameterCount() == 1) {
                    if (constructor.getParameters()[0].getType().isAssignableFrom(body.getClass())) {
                        return (AbstractID3v2FrameBody) constructor.newInstance(body);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (InvocationTargetException ex) {
        }
        return null;
    }

    /**
     * Copy the source file to the destination file. The destination file will be deleted first before copying starts.
     */
    public static void copyFile(final File source, final File destination) throws FileNotFoundException, IOException {
        if (source == null) {
            throw new NullPointerException("Source is null");
        }
        if (destination == null) {
            throw new NullPointerException("Destination is null");
        }
        if (source.exists() == false) {
            throw new NullPointerException("Source file not found.");
        }
        FileInputStream fio = null;
        BufferedInputStream bio = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        final byte[] buffer;
        try {
            if (destination.exists()) {
                destination.delete();
            }
            fio = new FileInputStream(source);
            bio = new BufferedInputStream(fio);
            fos = new FileOutputStream(destination);
            bos = new BufferedOutputStream(fos);
            buffer = new byte[1024];
            int b = bio.read(buffer);
            while (b != -1) {
                bos.write(buffer, 0, b);
                b = bio.read(buffer);
            }
        } finally {
            if (bio != null) {
                bio.close();
            }
            if (bos != null) {
                bos.flush();
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (fio != null) {
                fio.close();
            }
        }
    }

    /**
     * Unable to instantiate abstract classes, so can't call the copy constructor. So find out the instianted class name
     * and call the copy constructor through reflection.
     */
    public static Object copyObject(final Object copyObject) {
        final Class[] constructorParameterArray;
        final Object[] parameterArray;
        if (copyObject == null) {
            return null;
        }
        try {
            constructorParameterArray = new Class[1];
            constructorParameterArray[0] = copyObject.getClass();
            parameterArray = new Object[1];
            parameterArray[0] = copyObject;
            for (Constructor constructor: copyObject.getClass().getConstructors()) {
                if (constructor.getParameterCount() == 1) {
                    if (constructor.getParameterTypes()[0].isAssignableFrom(copyObject.getClass())) {
                        return constructor.newInstance(parameterArray);
                    }
                }
            }
//        } catch (NoSuchMethodException ex) {
//            throw new IllegalArgumentException("NoSuchMethodException: Error finding constructor to create copy");
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("IllegalAccessException: No access to run constructor to create copy");
        } catch (InstantiationException ex) {
            throw new IllegalArgumentException("InstantiationException: Unable to instantiate constructor to copy");
        } catch (java.lang.reflect.InvocationTargetException ex) {
            throw new IllegalArgumentException("InvocationTargetException: Unable to invoke constructor to create copy");
        }
        return null;
    }

    /**
     * return the index of the matching of parenthesis. This will match all four parenthesis and enclosed parenthesis.
     *
     * @param str   string to search
     * @param index index of string to start searching. This index should point to the opening parenthesis.
     *
     * @return index of the matching parenthesis. -1 is returned if none is found, or if the parenthesis are
     *         unbalanced.
     */
    public static int findMatchingParenthesis(final String str, final int index) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        if ((index < 0) || (index >= str.length())) {
            throw new IndexOutOfBoundsException("Index to image string is out of bounds: offset = " +
                                                index +
                                                ", string.length()" +
                                                str.length());
        }
        final TagOptionSingleton option = TagOptionSingleton.getInstance();
        final Stack stack = new Stack();
        String chString;
        String open;
        char ch;
        if (index >= 0) {
            final int length = str.length();
            if (length == 0) {
                return 0;
            }
            for (int i = index; i < length; i++) {
                ch = str.charAt(i);
                chString = ch + "";
                if (option.isOpenParenthesis(chString)) {
                    stack.push(chString);
                }
                if (option.isCloseParenthesis(chString)) {
                    if (stack.size() <= 0) {
                        return -1;
                    }
                    open = (String) stack.pop();
                    if (option.getCloseParenthesis(open).equals(chString) == false) {
                        return -1;
                    }
                }
                if (stack.size() <= 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Find the first whole number that can be parsed from the string
     *
     * @param str string to search
     *
     * @return first whole number that can be parsed from the string
     */
    public static long findNumber(final String str) throws TagException {
        return findNumber(str, 0);
    }

    /**
     * Find the first whole number that can be parsed from the string
     *
     * @param str    string to search
     * @param offset start seaching from this index
     *
     * @return first whole number that can be parsed from the string
     */
    public static long findNumber(final String str, final int offset) throws TagException {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        if ((offset < 0) || (offset >= str.length())) {
            throw new IndexOutOfBoundsException("Offset to image string is out of bounds: offset = " +
                                                offset +
                                                ", string.length()" +
                                                str.length());
        }
        int i;
        int j;
        final long num;
        i = offset;
        while (i < str.length()) {
            if (((str.charAt(i) >= '0') && (str.charAt(i) <= '9')) || (str.charAt(i) == '-')) {
                break;
            }
            i++;
        }
        j = i + 1;
        while (j < str.length()) {
            if (((str.charAt(j) < '0') || (str.charAt(j) > '9'))) {
                break;
            }
            j++;
        }
        if ((j <= str.length()) && (j > i)) {
            num = Long.parseLong(str.substring(i, j));
        } else {
            throw new TagException("Unable to find integer in string: " + str);
        }
        return num;
    }

    /**
     * String formatting function to pad the given string with the given character
     *
     * @param str       string to pad
     * @param length    total length of new string
     * @param ch        character to pad the string with
     * @param padBefore if true, add the padding at the start of the string. if false, add the padding at the end of the
     *                  string.
     *
     * @return new padded string.
     */
    public static String padString(final String str, final int length, final char ch, final boolean padBefore) {
        if (length < 0) {
            return str;
        } else if (length == 0) {
            if (str == null) {
                return "";
            }
            return str;
        }
        int strLength = 0;
        if (str != null) {
            strLength = str.length();
        }
        if (strLength >= length) {
            return str;
        }
        final char[] buffer = new char[length];
        int next = 0;
        if (padBefore) {
            for (int i = 0; i < (length - strLength); i++) {
                buffer[next++] = ch;
            }
        }
        if (str != null) {
            for (int i = 0; i < strLength; i++) {
                buffer[next++] = str.charAt(i);
            }
        }
        if (padBefore == false) {
            for (int i = 0; i < (length - strLength); i++) {
                buffer[next++] = ch;
            }
        }
        return new String(buffer);
    }

    /**
     * Replace the Unix end of line character with the DOS end of line character.
     *
     * @param text string to search and replace
     *
     * @return replaced string
     */
    public static String replaceEOLNwithCRLF(final String text) {
        String newText = null;
        if (text != null) {
            newText = "";
            int oldPos = 0;
            int newPos = text.indexOf('\n');
            while (newPos >= 0) {
                newText += (text.substring(oldPos, newPos) + TagConstant.SEPERATOR_LINE);
                oldPos = newPos + 1;
                newPos = text.indexOf('\n', oldPos);
            }
            newText += text.substring(oldPos);
        }
        return newText;
    }

    /**
     * Search the <code>source</code> string for any occurance of <code>oldString</code> and replaced them all with
     * <code>newString</code>. This searches for the entire word of old string. A blank space is appended to the front
     * and back of <code>oldString</code>
     */
    public static String replaceWord(String source, final String oldString, String newString) {
        if (source == null) {
            throw new NullPointerException("Source is null");
        }
        if (oldString == null) {
            throw new NullPointerException("Old string (string to be replaced) is null");
        }
        if ((source.length() > 0) && (oldString.length() > 0)) {
            if (newString == null) {
                newString = "";
            }
            final StringBuffer str = new StringBuffer(source);
            int index = str.indexOf(oldString);
            final int length = oldString.length();
            while (index >= 0) {
                if (((index == 0) && Character.isWhitespace(str.charAt(index + length))) ||
                    (Character.isWhitespace(str.charAt(index - 1)) && ((index + length) >= str.length())) ||
                    (Character.isWhitespace(str.charAt(index - 1)) &&
                     Character.isWhitespace(str.charAt(index + length)))) {
                    str.replace(index, index + length, newString);
                }
                index = str.indexOf(oldString, index);
            }
            source = str.toString();
        }
        return source;
    }

    /**
     * Remove all occurances of the given character from the string argument.
     *
     * @param str String to search
     * @param ch  character to remove
     *
     * @return new String without the given charcter
     */
    public static String stripChar(final String str, final char ch) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        final char[] buffer = new char[str.length()];
        int next = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ch) {
                buffer[next++] = str.charAt(i);
            }
        }
        return new String(buffer, 0, next);
    }

    /**
     * Change the given string into sentence case. Sentence case has the first words always capitalized. Any words in
     * <code>TagConstants.upperLowerCase</code> will be capitalized that way. Any other words will be turned lower
     * case.
     *
     * @param str           String to modify
     * @param keepUppercase if true, keep a word if it is already all in UPPERCASE
     *
     * @return new string in sentence case.
     */
    public static String toSentenceCase(final String str, final boolean keepUppercase) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        final StringTokenizer tokenizer = new StringTokenizer(str);
        String token;
        final int numberTokens = tokenizer.countTokens();
        int countedTokens = 0;
        final StringBuffer newString = new StringBuffer();

        // Capitalize first word of all sentences.
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            newString.append(capitalizeWord(token, keepUppercase));
            newString.append(' ');
            countedTokens++;
        }

        // go through all remainder tokens
        while (tokenizer.hasMoreTokens() && (countedTokens < numberTokens)) {
            token = tokenizer.nextToken();
            countedTokens++;
            if (capitalizationMap.containsKey(token.toLowerCase())) {
                newString.append(capitalizationMap.get(token.toLowerCase()));
            } else if (keepUppercase && token.toUpperCase().equals(token)) {
                newString.append(token);
            } else {
                newString.append(token.toLowerCase());
            }
            newString.append(' ');
        }

        // remove trailing space
        if (newString.length() > 0) {
            newString.deleteCharAt(newString.length() - 1);
        }
        return newString.toString();
    }

    /**
     * Change the given string to title case. The first and last words of the string are always capitilized. Any words
     * in <code>TagConstants.upperLowerCase</code> will be capitalized that way. Any other words will be capitalized.
     *
     * @param str           String to modify
     * @param keepUppercase if true, keep a word if it is already all in UPPERCASE
     *
     * @return new capitlized string.
     */
    public static String toTitleCase(final String str, final boolean keepUppercase) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        final StringTokenizer tokenizer = new StringTokenizer(str);
        String token;
        final int numberTokens = tokenizer.countTokens();
        int countedTokens = 0;
        final StringBuffer newString = new StringBuffer();

        // Capitalize first word of all titles.
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            newString.append(capitalizeWord(token, keepUppercase));
            newString.append(' ');
            countedTokens++;
        }

        // go through all remainder tokens except last
        while (tokenizer.hasMoreTokens() && (countedTokens < (numberTokens - 1))) {
            token = tokenizer.nextToken();
            countedTokens++;
            if (capitalizationMap.containsKey(token.toLowerCase())) {
                newString.append(capitalizationMap.get(token.toLowerCase()));
            } else {
                newString.append(capitalizeWord(token, keepUppercase));
            }
            newString.append(' ');
        }

        // Capitalize last word of all titles.
        if (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            newString.append(capitalizeWord(token, keepUppercase));
            newString.append(' ');
        }

        // remove trailing space
        if (newString.length() > 0) {
            newString.deleteCharAt(newString.length() - 1);
        }
        return newString.toString();
    }

    /**
     * truncate a string if it longer than the argument
     *
     * @param str String to truncate
     * @param len maximum desired length of new string
     */
    public static String truncate(final String str, final int len) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        if (len < 0) {
            throw new IndexOutOfBoundsException("Length is less than zero");
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str.trim();
    }

    /**
     * Capitalize the word with the first letter upper case and all others lower case.
     *
     * @param word          word to capitalize.
     * @param keepUppercase if true, keep a word if it is already all in UPPERCASE
     *
     * @return new capitalized word.
     */
    private static StringBuffer capitalizeWord(String word, final boolean keepUppercase) {
        if (word == null) {
            return null;
        }
        final StringBuffer wordBuffer = new StringBuffer();
        int index = 0;
        if (keepUppercase && word.toUpperCase().equals(word)) {
            wordBuffer.append(word);
        } else {
            word = word.toLowerCase();
            final int len = word.length();
            char ch;
            ch = word.charAt(index);
            while (((ch < 'a') || (ch > 'z')) && (index < (len - 1))) {
                ch = word.charAt(++index);
            }
            if (index < len) {
                wordBuffer.append(word.substring(0, index));
                wordBuffer.append((char) (ch + UPPERCASE));
                wordBuffer.append(word.substring(index + 1));
            } else {
                wordBuffer.append(word);
            }
        }
        return wordBuffer;
    }
}