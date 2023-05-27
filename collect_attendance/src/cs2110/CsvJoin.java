package cs2110;

import java.io.*;
import java.util.Scanner;

public class CsvJoin {

    /**
     * Load a table from a Simplified CSV file and return a row-major list-of-lists representation.
     * The CSV file is assumed to be in the platform's default encoding. Throws an IOException if
     * there is a problem reading the file.
     */
    public static Seq<Seq<String>> csvToList(String file) throws IOException {
        Seq<Seq<String>> ans2 = new LinkedSeq<>();
        String path = file;
        Reader in = new FileReader(path);
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            Seq<String> ans = new LinkedSeq<>();
            String line = sc.nextLine();
            String[] tokens = line.split(",", -1);
            for (String s : tokens) {
                ans.append(s);
            }
            ans2.append(ans);
        }
        return ans2;
    }

    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    private static boolean preconditionForJoin(Seq<Seq<String>> tables) {
        int initColCount = 0;
        int colCount = 0;
        boolean sameSize = true;
        boolean entered = false;
        for (Seq<String> s : tables) {
            colCount = 0;
            for (String cols : s) {
                colCount++;
            }
            if (!entered) {
                initColCount = colCount;
                entered = true;
            }
            if (initColCount != colCount) {
                sameSize = false;
            }
        }
        return sameSize && initColCount >= 1;

    }

    public static Seq<Seq<String>> join(Seq<Seq<String>> left, Seq<Seq<String>> right) {

        assert preconditionForJoin(left);
        assert preconditionForJoin(right);

        Seq<Seq<String>> ans2 = new LinkedSeq<>();
        for (int j = 0; j < left.size(); j++) {
            Seq<String> a = left.get(j);
            boolean hasRow = false;
            int numElem = 0;
            for (int k = 0; k < right.size(); k++) {
                Seq<String> b = right.get(k);
                if (numElem < b.size()) {
                    numElem = b.size();
                }
                if (b.contains(a.get(0))) {
                    Seq<String> temp = new LinkedSeq<>();
                    for (String s : a) {
                        temp.append(s);
                    }
                    for (int l = 1; l < b.size(); l++) {
                        temp.append(b.get(l));
                    }
                    ans2.append(temp);
                    hasRow = true;
                }
            }
            if (!hasRow) {
                Seq<String> temp = new LinkedSeq<>();
                for (Object s : a) {
                    temp.append(s.toString());
                }
                for (int i = 1; i < numElem; i++) {
                    temp.append("");
                }
                ans2.append(temp);
            }

        }
        return ans2;

    }

    private static void csvFormat(Seq<Seq<String>> toBeFormat) {
        for (int i = 0; i < toBeFormat.size(); i++) {
            Seq<String> line = toBeFormat.get(i);
            for (int j = 0; j < line.size() - 1; j++) {
                System.out.print(line.get(j) + ",");
            }
            System.out.print(line.get(line.size() - 1));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            if (args[0] == null || args[1] == null) {
                throw new IndexOutOfBoundsException();
            }
            Seq<Seq<String>> left = csvToList(args[0]);
            Seq<Seq<String>> right = csvToList(args[1]);
            if (!(preconditionForJoin(left) && preconditionForJoin(right))) {
                throw new IllegalArgumentException();
            }
            Seq<Seq<String>> output = join(left, right);
            csvFormat(output);
        } catch (IOException e) {
            System.out.println("There is an issue with reading the file");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Could not read input tables.");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Usage: cs2110.CsvJoin <left_table.csv> <right_table.csv>");
            System.exit(1);
        }
    }
}


