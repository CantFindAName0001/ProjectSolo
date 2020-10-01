import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.HashSet;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * @author Rohan Gandhi
 */

public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        PaintersDuel solver = new PaintersDuel();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class PaintersDuel {
        static int[][] odd = {{0, -1}, {0, 1}, {1, 1}};
        static int[][] eve = {{0, -1}, {0, 1}, {-1, -1}};

        private String move(String str, int[] arr) {
            int[] par = parse(str);
            int r = par[0] + arr[0];
            int p = par[1] + arr[1];
            return r + "-" + p;
        }

        private boolean check(String str, int s) {
            int[] arr = parse(str);
            int r = arr[0];
            int p = arr[1];
            if (r < 1 || r > s || p < 1 || p > 2 * r - 1) {
                return false;
            }
            return true;
        }

        private int getP(String str) {
            return parse(str)[1];
        }

        private int[] parse(String str) {
            String[] strs = str.split("-");
            int r = Integer.parseInt(strs[0]);
            int p = Integer.parseInt(strs[1]);
            return new int[]{r, p};
        }

        public void solve(int testNumber, Scanner in, PrintWriter out) {
            int s = in.nextInt(), ra = in.nextInt(), pa = in.nextInt(), rb = in.nextInt(), pb = in.nextInt(), c = in.nextInt();
            HashSet<String> blocked = new HashSet<>();

            for (int j = 0; j < c; j++) {
                int rc = in.nextInt();
                int pc = in.nextInt();
                blocked.add(rc + "-" + pc);
            }

            String alma = ra + "-" + pa;
            String berthe = rb + "-" + pb;
            blocked.add(alma);
            blocked.add(berthe);

            int ans = calcScore(blocked, s, alma, berthe, true, true, 0);
            out.println("Case #" + testNumber + ": " + ans);
        }

        private int calcScore(HashSet<String> blocked, int s, String alma, String berthe, boolean isalma, boolean moved, int score) {
            boolean painted = false;
            int ans;
            if (isalma) {
                int[][] arr = getP(alma) % 2 == 0? eve: odd;
                ans = Integer.MIN_VALUE;

                for (int i = 0; i < 3; i++) {
                    String next = move(alma, arr[i]);

                    if (check(next, s) && !blocked.contains(next)) {
                        painted = true;
                        blocked.add(next);
                        ans = Math.max(ans, calcScore(blocked, s, next, berthe, !isalma, true, score + 1));
                        blocked.remove(next);
                    }
                }
            } else {
                int[][] arr = getP(berthe) % 2 == 0? eve: odd;
                ans = Integer.MAX_VALUE;

                for (int i = 0; i < 3; i++) {
                    String next = move(berthe, arr[i]);

                    if (check(next, s) && !blocked.contains(next)) {
                        painted = true;
                        blocked.add(next);
                        ans = Math.min(ans, calcScore(blocked, s, alma, next, !isalma, true, score - 1));
                        blocked.remove(next);
                    }
                }
            }
            if (!painted) {
                ans = moved? calcScore(blocked, s, alma, berthe, !isalma, false, score): score;
            }
            return ans;
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

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

    }
}