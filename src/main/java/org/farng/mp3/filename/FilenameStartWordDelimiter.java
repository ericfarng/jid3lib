package org.farng.mp3.filename;

/**
 * This class is a delimiter which remains a part of the token, located at the start.
 *
 * @author Eric Farng
 * @version $Revision: 1.2 $
 */
public class FilenameStartWordDelimiter extends FilenameDelimiter {

    /**
     * Creates a new FilenameStartWordDelimiter object.
     */
    public FilenameStartWordDelimiter() {
        super();
    }

    /**
     * Creates a new FilenameStartWordDelimiter object.
     */
    public FilenameStartWordDelimiter(final FilenameStartWordDelimiter delimiter) {
        super(delimiter);
    }

    /**
     * Reconstruct the filename that is represented by this composite.
     *
     * @return the filename that is represented by this composite.
     */
    public String composeFilename() {
        final StringBuffer stringBuffer = new StringBuffer(128);
        if (getBeforeComposite() != null) {
            stringBuffer.append(getBeforeComposite().composeFilename());
            stringBuffer.append(' ');
        }
        if (getAfterComposite() != null) {
            stringBuffer.append(getAfterComposite().composeFilename());
        }
        return stringBuffer.toString().trim();
    }
}