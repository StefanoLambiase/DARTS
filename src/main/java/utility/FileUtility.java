package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileUtility {

    public static String readFile(String nomeFile) throws IOException {
        InputStream is = null;
        InputStreamReader isr = null;

        StringBuffer sb = new StringBuffer();
        char[] buf = new char[1024];
        int len;

        try {
            is = new FileInputStream(nomeFile);
            isr = new InputStreamReader(is);

            while ((len = isr.read(buf)) > 0)
                sb.append(buf, 0, len);

            return sb.toString();
        } finally {
            if (isr != null)
                isr.close();
        }
    }
}
