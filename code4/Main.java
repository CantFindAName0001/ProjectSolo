import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;
 
/**
* Built using CHelper plug-in
* Actual solution is at the top
*
* @author PersonOfInterest
*/
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        ATwoSubstrings solver = new ATwoSubstrings();
        solver.solve(1, in, out);
        out.close();
    }
 
    static class ATwoSubstrings {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            String s = in.nextString();
            Map<String, List<Integer>> map = new HashMap<>();
            map.put("AB", new ArrayList<>());
            map.put("BA", new ArrayList<>());
 
            for (int i = 0; i < s.length() - 1; i++) {
                String sub = "" + s.charAt(i) + s.charAt(i + 1);
                if (sub.equals("AB") || sub.equals("BA")) {
                    map.get(sub).add(i);
                }
            }
 
            if (map.get("AB").size() == 0 || map.get("BA").size() == 0) {
                out.println("NO");
                return;
            }
 
            int diff1 = Math.abs(map.get("AB").get(0) - map.get("BA").get(map.get("BA").size() - 1));
            int diff2 = Math.abs(map.get("BA").get(0) - map.get("AB").get(map.get("AB").size() - 1));
            if (diff1 >= 2 || diff2 >= 2) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
 
    }
 
    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;
 
        public InputReader(InputStream stream) {
            this.stream = stream;
        }
 
        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
               if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }
 
        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }
 
        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }
 
        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
 
        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
 
        }
 
    }
 
    static class OutputWriter {
        private final PrintWriter writer;
 
        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }
 
        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }
 
        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }
 
        public void println(Object... objects) {
            print(objects);
            writer.println();
        }
 
        public void close() {
           writer.close();
        }
 
    }
}
