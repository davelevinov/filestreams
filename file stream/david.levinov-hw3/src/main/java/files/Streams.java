package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class Streams {
    /**
     * Read from an InputStream until a quote character (") is found, then read
     * until another quote character is found and return the bytes in between the two quotes.
     * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
     *
     * @param in
     * @return A list containing the bytes between the first occurrence of a quote character and the second.
     */
    public static List<Byte> getQuoted(InputStream in) throws IOException {
        int c;
        int quoteNum = 0;
        List<Byte> result = new ArrayList<>();
        try {
            while ((c = in.read()) != -1 && quoteNum < 2) {
                if (c == '"') {
                    quoteNum = quoteNum + 1;
                    continue;
                }
                if (quoteNum == 1) {
                    result.add((byte) c);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            in.close();
        }
        if (quoteNum == 0) {
            return null;
        }
        return result;
    }


    /**
     * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
     *
     * @param in      the Reader to read from
     * @param endMark the string indicating to stop reading.
     * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
     */
    public static String readUntil(Reader in, String endMark) throws IOException {
        int c;
        int endMarkLength = endMark.length();
        boolean containsEndMark = false;
        StringBuilder result = new StringBuilder();
        try {
            while ((c = in.read()) != -1) {
                result.append((char) c);
                if (result.toString().contains(endMark)){
                    containsEndMark = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            in.close();
        }
        if (containsEndMark) {
            return result.substring(0, result.length() - endMarkLength);
        }
        return result.toString();
    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     *
     * @param in
     * @param out
     * @param badByte
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        int c;
        try {
            while ((c = in.read()) != -1) {
                if ((byte) c != badByte){
                    out.write((byte) c);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Read a 40-bit (unsigned) integer from the stream and return it. The number is represented as five bytes,
     * with the most-significant byte first.
     * If the stream ends before 5 bytes are read, return -1.
     *
     * @param in
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        int c;
        long result = 0L;
        int length = 0;
        try {
            while ((c = in.read()) != -1 && length < 5) {
                    result = (result<<8|c);
                    length++;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            in.close();
        }
        if (length < 5) return -1;
        return result;
    }
}
