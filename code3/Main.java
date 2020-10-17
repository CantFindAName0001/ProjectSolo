import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
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
        BSuffixStructures solver = new BSuffixStructures();
        solver.solve(1, in, out);
        out.close();
    }
 
    static class BSuffixStructures {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            String s1 = in.nextString(), s2 = in.nextString();
 
            if (s1.equals(s2)) {
                out.println("array");
                return;
            }
 
            int l1 = s1.length();
            int l2 = s2.length();
 
            int[] a1 = new int[26];
            int[] a2 = new int[26];
            for (int i = 0; i < l1; i++) {
                a1[s1.charAt(i) - 'a']++;
            }
            for (int i = 0; i < l2; i++) {
                a2[s2.charAt(i) - 'a']++;
            }
 
            if (Arrays.equals(a1, a2)) {
                out.println("array");
                return;
            }
 
            for (int i = 0; i < 26; i++) {
                if (a2[i] > a1[i]) {
                    out.println("need tree");
                    return;
                }
            }
 
            out.println(lcs(s1, s2));
        }
 
        private String lcs(String s1, String s2) {
            int l1 = s1.length(), l2 = s2.length();
            int i = 0, j = 0;
            while (i < l1 && j < l2) {
                while (i < l1 && s2.charAt(j) != s1.charAt(i)) {
                    i++;
                }
                if (i < l1 && s2.charAt(j) == s1.charAt(i)) {
                    i++;
                    j++;
                }
            }
 
            if (j == l2) {
                return "automaton";
            }
            return "both";
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
}
