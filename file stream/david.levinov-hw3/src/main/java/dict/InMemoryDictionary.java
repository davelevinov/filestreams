package dict;

import java.io.*;
import java.util.TreeMap;


/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * <p>
 * The file format has one keyword per line:
 * <pre>word:def1:def2:def3,...</pre>
 *
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre>
 *
 * @author talm
 */
public class InMemoryDictionary extends TreeMap<String, String> implements PersistentDictionary {
    private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
    private File file;

    public InMemoryDictionary(File dictFile) {
        this.file = dictFile;
    }

    @Override
    public void open() throws IOException {
        // file creation verification
        if (!file.exists()){
            if (!this.file.createNewFile()){
                throw new IOException("no existing file");
            }
        }

        BufferedReader bf = null;
        String lineRead;
        int i;
        try {
            bf = new BufferedReader(new FileReader(this.file));
            lineRead = bf.readLine();
            while (lineRead != null) {
                i = lineRead.indexOf(":");
                this.put(lineRead.substring(0, i), lineRead.substring(i + 1));
                lineRead = bf.readLine();
            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        } finally {
            if (bf != null) bf.close();
        }
    }

    @Override
    public void close() throws IOException {
        // file creation verification
        if (!file.exists()){
            if (!this.file.createNewFile()){
                throw new IOException("no existing file");
            }
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.file));
            for (String key : this.keySet()) {
                bw.write(key + ":" + this.get(key));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

}

