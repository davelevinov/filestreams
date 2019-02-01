package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {

    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them
     * in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        int firstRead;
        int secondRead;
        try {
            long n = file.length();
            for (long i = 0L; i < n - 1; i++) {
                for (long j = 0L; j < n - i - 1; j++) {
                    file.seek(j);
                    firstRead = file.read();
                    secondRead = file.read();
                    if (firstRead > secondRead) {
                        file.seek(j);
                        file.write(secondRead);
                        file.seek(j + 1);
                        file.write(firstRead);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
     * them in-place using a bubble-sort algorithm.
     * You may not read the whole file into memory!
     *
     * @param file
     * @throws IOException
     */

    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        int[] current = new int[3];
        int[] previous = new int[3];

        try {
            long fileLen = file.length();

            for (long i = 0L; i < fileLen; i += 3) {
                for (long j = 3L; j < fileLen - i; j += 3) {
                    file.seek(j - 3);
                    for (int k = 0; k < 3; k++) {
                        previous[k] = file.read();
                    }
                    for (int p = 0; p < 3; p++) {
                        current[p] = file.read();
                    }
                    if (previous[0] > current[0]) {
                        file.seek(j - 3);
                        writeAux(file, current);
                        writeAux(file, previous);
                    }
                    if (previous[0] == current[0] && previous[1] > current[1]) {
                        file.seek(j - 3);
                        writeAux(file, current);
                        writeAux(file, previous);
                    }
                    if (previous[0] == current[0] && previous[1] == current[1] && previous[2] > current[2]) {
                        file.seek(j - 3);
                        writeAux(file, current);
                        writeAux(file, previous);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        }
    }
    // helper method to write into the file
    private static void writeAux(RandomAccessFile dst, int[] toWrite) {
        try {
            for (int i = 0; i < toWrite.length; i++) {
                dst.write(toWrite[i]);
            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        }

    }
}

