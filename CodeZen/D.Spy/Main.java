import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
 * @author Rohan Gandhi
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        SpyAndPalindrome solver = new SpyAndPalindrome();
        solver.solve(1, in, out);
        out.close();
    }

    static class SpyAndPalindrome {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            String s = in.next();
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < s.length()) {
                StringBuilder reps = new StringBuilder();
                while (s.charAt(i) - '0' >= 0 && s.charAt(i) - '9' <= 9) {
                    reps.append(Character.toString(s.charAt(i++)));
                }
                if (reps.length() == 0)
                    reps.append("1");
                int rep = Integer.parseInt(reps.toString());

                if (s.charAt(i) == '+')
                    i++;

                StringBuilder word = new StringBuilder();
                while (i < s.length() && s.charAt(i) - 'a' >= 0 && s.charAt(i) - 'z' <= 25) {
                    word.append(Character.toString(s.charAt(i++)));
                }
                for (int j = 0; j < rep; j++) {
                    sb.append(word);
                }
                if (i < s.length() && s.charAt(i) == '-')
                    i++;
            }
            String ans = sb.toString();
            out.println(ans.equals(sb.reverse().toString())? "Return": "Continue");
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

