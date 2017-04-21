package org.farng.mp3.filename;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.ID3v2_4;

import java.util.Iterator;

/**
 * This class builds a <code>FilenameTag</code>. The main method to call is <code>createFilenameTagFromMP3File</code>.
 * Other methods are public in order to update parts of the tag if needed.
 *
 * @author Eric Farng
 * @version $Revision: 1.2 $
 */
public class FilenameTagBuilder {

    private FilenameTagBuilder() {
        super();
    }

    /**
     * Creates a FilenameComposite tree with the given string. It is parsed according to the different values that can
     * be set in <code>TagOptionSingleton</code> class
     *
     * @param token filename to parse
     *
     * @return FilenameComposite tree representing the given token.
     *
     * @throws TagException is thrown if there are unmatched parenthesis
     */
    public static AbstractFilenameComposite createCompositeFromToken(final String token) throws TagException {
        String[] splitToken;
        AbstractFilenameComposite composite = null;
        final AbstractFilenameComposite beforeComposite;
        final AbstractFilenameComposite middleComposite;
        final AbstractFilenameComposite afterComposite;
        splitToken = parseParenthesis(token);
        if (splitToken != null) {
            composite = new FilenameParenthesis();
            ((FilenameParenthesis) composite).setOpenDelimiter(splitToken[0]);
            beforeComposite = createCompositeFromToken(splitToken[2]);
            ((FilenameParenthesis) composite).setBeforeComposite(beforeComposite);
            middleComposite = createCompositeFromToken(splitToken[3]);
            ((FilenameParenthesis) composite).setMiddleComposite(middleComposite);
            afterComposite = createCompositeFromToken(splitToken[4]);
            ((FilenameParenthesis) composite).setAfterComposite(afterComposite);
            composite.setOriginalToken(token);
            return composite;
        }
        splitToken = parseDelimiter(token);
        if (splitToken != null) {
            composite = new FilenameDelimiter();
            ((FilenameDelimiter) composite).setDelimiter(splitToken[0]);
            beforeComposite = createCompositeFromToken(splitToken[1]);
            ((FilenameDelimiter) composite).setBeforeComposite(beforeComposite);
            afterComposite = createCompositeFromToken(splitToken[2]);
            ((FilenameDelimiter) composite).setAfterComposite(afterComposite);
            composite.setOriginalToken(token);
            return composite;
        }
        splitToken = parseStartWordDelimiter(token);
        if (splitToken != null) {
            composite = new FilenameStartWordDelimiter();
            ((FilenameDelimiter) composite).setDelimiter(splitToken[0]);
            beforeComposite = createCompositeFromToken(splitToken[1]);
            ((FilenameStartWordDelimiter) composite).setBeforeComposite(beforeComposite);
            afterComposite = createCompositeFromToken(splitToken[2]);
            ((FilenameStartWordDelimiter) composite).setAfterComposite(afterComposite);
            composite.setOriginalToken(token);
            return composite;
        }
        splitToken = parseEndWordDelimiter(token);
        if (splitToken != null) {
            composite = new FilenameEndWordDelimiter();
            ((FilenameDelimiter) composite).setDelimiter(splitToken[0]);
            beforeComposite = createCompositeFromToken(splitToken[1]);
            ((FilenameEndWordDelimiter) composite).setBeforeComposite(beforeComposite);
            afterComposite = createCompositeFromToken(splitToken[2]);
            ((FilenameEndWordDelimiter) composite).setAfterComposite(afterComposite);
            composite.setOriginalToken(token);
            return composite;
        }
        if (token != null && token.trim().length() > 0) {
            composite = new FilenameToken();
            ((FilenameToken) composite).setToken(token.trim());
            composite.setOriginalToken(token);
            return composite;
        }
        return composite;
    }

    public static FilenameTag createEmptyFilenameTag() {
        final FilenameTag filenameTag = new FilenameTag();
        filenameTag.setId3tag(new ID3v2_4());
        return filenameTag;
    }

    /**
     * This method will create a complete FilenameTag from the given mp3File and from the options in
     * <code>TagOptionSingleton</code>. This will call all other necessary methods in this builder class.
     *
     * @param mp3File MP3 file to create the FilenameTag from.
     *
     * @return FilenameTag of the mp3File argument
     *
     * @throws Exception is thrown on any IO errors, or parsing errors such as unmatched parenthesis
     */
    public static FilenameTag createFilenameTagFromMP3File(final MP3File mp3File) throws Exception {
        FilenameTag filenameTag = null;
        if (mp3File.getMp3file() != null) {
            filenameTag = new FilenameTag();
            final AbstractFilenameComposite composite;
            final ID3v2_4 id3tag;
            String filename = mp3File.getMp3file().getName();
            final int index = filename.lastIndexOf((int) '.');
            if (index >= 0) {
                filenameTag.setExtension(filename.substring(index + 1));
                filename = filename.substring(0, index);
            }

            // create composite
            composite = createCompositeFromToken(filename);
            updateCompositeFromAllTag(composite, mp3File);
            updateCompositeFromAllOption(composite);

            // create tag
            id3tag = composite.createId3Tag();

            // assign values;
            filenameTag.setMp3file(mp3File);
            filenameTag.setComposite(composite);
            filenameTag.setId3tag(id3tag);
        }
        return filenameTag;
    }

    /**
     * Traverse the composite and set the class field to match keywords found in TagOptionSingleton.
     *
     * @param composite composite to update.
     */
    public static void updateCompositeFromAllOption(final AbstractFilenameComposite composite) {
        final Iterator iterator = TagOptionSingleton.getInstance().getKeywordIterator();
        while (iterator.hasNext()) {
            composite.matchAgainstKeyword((Class) iterator.next());
        }
    }

    /**
     * Traverse the composite and set the class field to match frames from all three other tags that are already found
     * in the MP3 file.
     *
     * @param composite composite to update
     * @param mp3File   mp3file to match all it's tags against.
     */
    public static void updateCompositeFromAllTag(final AbstractFilenameComposite composite, final MP3File mp3File) {
        composite.matchAgainstTag(mp3File.getID3v1Tag());
        composite.matchAgainstTag(mp3File.getID3v2Tag());
        composite.matchAgainstTag(mp3File.getLyrics3Tag());
    }

    /**
     * Parses the given token into two halves with the delimiters found in <code> TagOptionSingleton</code>
     *
     * @param token token to split
     *
     * @return index 0 is the delimiter. index 1 and 2 are the before and after tokens respectively.
     */
    private static String[] parseDelimiter(final String token) {
        String[] tokenArray = null;
        if (token != null && token.length() > 0) {
            final Iterator iterator = TagOptionSingleton.getInstance().getFilenameDelimiterIterator();
            int index;
            String delimiter;
            while (iterator.hasNext()) {
                delimiter = (String) iterator.next();
                index = token.indexOf(delimiter);
                if (index >= 0) {
                    tokenArray = new String[3];
                    tokenArray[0] = delimiter;
                    tokenArray[1] = token.substring(0, index);
                    tokenArray[2] = token.substring(index + delimiter.length());
                }
            }
        }
        return tokenArray;
    }

    /**
     * Parses the given token into two halves with the delimiters found in <code> TagOptionSingleton</code>
     *
     * @param token token to split
     *
     * @return index 0 is the delimiter. index 1 and 2 are the before and after tokens respectively.
     */
    private static String[] parseEndWordDelimiter(final String token) {
        String[] tokenArray = null;
        if (token != null && token.length() > 0) {
            final Iterator iterator = TagOptionSingleton.getInstance().getEndWordDelimiterIterator();
            int index;
            String delimiter;
            while (iterator.hasNext()) {
                delimiter = (String) iterator.next();
                if (token.endsWith(delimiter)) {
                    index = token.substring(0, token.length() - delimiter.length()).indexOf(delimiter);
                } else {
                    index = token.indexOf(delimiter);
                }
                if (index > 0) {
                    tokenArray = new String[3];
                    tokenArray[0] = delimiter;
                    tokenArray[1] = token.substring(0, index);
                    tokenArray[2] = token.substring(index);
                }
            }
        }
        return tokenArray;
    }

    /**
     * Given a specific token, parse it into halves according to the <code>TagOptionSingleton</code>
     *
     * @param token token to split.
     *
     * @return index 0 and 1 are the parenthesis delimiters. index 2, 3, 4 are before, middle, and after respectively.
     */
    private static String[] parseParenthesis(final String token) throws TagException {
        String[] tokenArray = null;
        if (token != null && token.length() > 0) {
            final TagOptionSingleton option = TagOptionSingleton.getInstance();
            String tempOpen;
            String open = "";
            final String close;
            int openIndex = token.length();
            int tempIndex;
            final int closeIndex;
            final Iterator iterator = option.getOpenParenthesisIterator();

            // find first parenthesis
            while (iterator.hasNext()) {
                tempOpen = (String) iterator.next();
                tempIndex = token.indexOf(tempOpen);
                if (tempIndex >= 0 && tempIndex < openIndex) {
                    openIndex = tempIndex;
                    open = tempOpen;
                }
            }

            // we have a parenthesis
            if (openIndex >= 0 && openIndex < token.length()) {
                close = option.getCloseParenthesis(open);
                closeIndex = TagUtility.findMatchingParenthesis(token, openIndex);
                if (closeIndex < 0) {
                    throw new TagException("Unmatched parenthesis in \"" + token + "\" at position : " + openIndex);
                }
                tokenArray = new String[5];
                tokenArray[0] = open;
                tokenArray[1] = close;
                tokenArray[2] = token.substring(0, openIndex);
                tokenArray[3] = token.substring(openIndex + open.length(), closeIndex);
                tokenArray[4] = token.substring(closeIndex + close.length());
            }
        }
        return tokenArray;
    }

    /**
     * Parses the given token into two halves with the delimiters found in <code> TagOptionSingleton</code>
     *
     * @param token token to split
     *
     * @return index 0 is the delimiter. index 1 and 2 are the before and after tokens respectively.
     */
    private static String[] parseStartWordDelimiter(final String token) {
        String[] tokenArray = null;
        if (token != null && token.length() > 0) {
            final Iterator iterator = TagOptionSingleton.getInstance().getStartWordDelimiterIterator();
            int index;
            String delimiter;
            while (iterator.hasNext()) {
                delimiter = (String) iterator.next();
                if (token.startsWith(delimiter)) {
                    index = token.indexOf(delimiter, delimiter.length());
                } else {
                    index = token.indexOf(delimiter);
                }
                if (index > 0) {
                    tokenArray = new String[3];
                    tokenArray[0] = delimiter;
                    tokenArray[1] = token.substring(0, index);
                    tokenArray[2] = token.substring(index);
                }
            }
        }
        return tokenArray;
    }
}