/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair;

/**
 *
 * @author alqods
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Playfair {

    static char[][] mat = new char[5][5];

    public static void main(String[] args) {

        File encryption_file = new File("exampleEncrypt.txt");
        try {
            encryptfile(encryption_file, "gravity");

        } catch (Exception e) {
            System.out.println(e);
        }
        //Test decrypt file method
        File decryption_file = new File("exampleDecrypt.txt");
        try {
            decryptfile(decryption_file, "gravity");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    static char[] removeDuplicates(String s) {
        int j, index = 0;

        char c[] = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {

            for (j = 0; j < i; j++) {

                if (c[i] == c[j] || c[i] == ' ') {
                    break;
                }
            }

            if (i == j) {
                c[index++] = c[i];
            }
        }

        return Arrays.copyOf(c, index);
    }

    static String AllLetter(char[] c) {
        boolean f;
        String s = "";
        char[] e = new char[26];
        for (int i = 0; i < 26; i++) {
            f = true;
            for (int j = 0; j < c.length; j++) {
                if ((char) (i + 97) == c[j]) {
                    f = false;

                }
            }
            if (f && i != 9) {
                s += (char) (i + 97);
            }
        }

        String s1;
        s1 = new String(Arrays.copyOf(c, c.length));
        s1 += s;
        System.out.println(s1.length());

        return s1;
    }

    static char[][] FillMatrix(String keyWord) {
        String key = keyWord;
        int counter_for_key = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (counter_for_key < key.length()) {
                    mat[i][j] = key.charAt(counter_for_key);
                    counter_for_key++;
                } else {
                    break;

                }
            }
        }
        return mat;
    }

    static char[] split(String plainText) {
        plainText = plainText.replaceAll(" ", "");
        int index = 0;
        char[] c = plainText.toCharArray();
        List<Character> listC = new ArrayList<>();

        for (char t : c) {
            listC.add(t);
        }

        while (index != listC.size() - 1) {
            if (Objects.equals(listC.get(index), listC.get(index + 1))) {
                listC.add(index + 1, 'x');

            }
            index++;
        }
        if (listC.size() % 2 != 0) {
            listC.add('x');
        }
        // System.out.println(plainText);
        System.out.println(listC.size());
        char[] d = new char[listC.size()];
        for (int i = 0; i < listC.size(); i++) {
            d[i] = listC.get(i);
        }

        return d;
    }

    static String encrypt(String plainText, String key) {
        int i1 = 0, j1 = 0, i2 = 0, j2 = 0, counter = 0; 
        char[] keyS = removeDuplicates(key);
        String letters = AllLetter(keyS);
        mat = FillMatrix(letters);
        char[] plain = split(plainText);
        char[] temp = new char[plain.length];
        while (counter != plain.length) {
            for (int j = 0; j < mat.length; j++) {
                for (int k = 0; k < mat[j].length; k++) {
                    // System.out.println(SS[counter]);
                    if (plain[counter] == mat[j][k]) {
                        i1 = j;
                        j1 = k;
                        break;
                    }

                }
            }
            for (int j = 0; j < mat.length; j++) {
                for (int k = 0; k < mat[j].length; k++) {
                    if (plain[counter + 1] == mat[j][k]) {
                        i2 = j;
                        j2 = k;

                        break;
                    }

                }
            }
            if (i1 == i2) {
                temp[counter] = mat[i1 % 5][(j1 + 1) % 5];
                temp[counter + 1] = mat[i2 % 5][(j2 + 1) % 5];
            } else if (j1 == j2) {
                temp[counter] = mat[(i1 + 1) % 5][j1 % 5];
                temp[counter + 1] = mat[(i2 + 1) % 5][(j2) % 5];
            } else {
                temp[counter] = mat[i1 % 5][j2 % 5];
                temp[counter + 1] = mat[(i2) % 5][(j1) % 5];
            }
            counter += 2;
        }
        return new String(Arrays.copyOf(temp, temp.length));
    }

    static String decrypt(String plainText, String key) {  
         int i1 = 0, j1 = 0, i2 = 0, j2 = 0, counter = 0;
        char[] keyS = removeDuplicates(key);
        String letters = AllLetter(keyS);
        mat = FillMatrix(letters);
        char[] plain = split(plainText);
        char[] temp = new char[plain.length];
        while (counter != plain.length) {
            for (int j = 0; j < mat.length; j++) {
                for (int k = 0; k < mat[j].length; k++) {
                    if (plain[counter] == mat[j][k]) {
                        i1 = j;
                        j1 = k;
                        break;
                    }

                }
            }
            for (int j = 0; j < mat.length; j++) {
                for (int k = 0; k < mat[j].length; k++) {
                    if (plain[counter + 1] == mat[j][k]) {
                        i2 = j;
                        j2 = k;

                        break;
                    }

                }
            }
            if (i1 == i2) {
                temp[counter] = mat[i1 % 5][((j1 + 5) - 1) % 5];
                temp[counter + 1] = mat[i2 % 5][((j2 + 5) - 1) % 5];
            } else if (j1 == j2) {
                temp[counter] = mat[((i1 + 5) - 1) % 5][j1 % 5];
                temp[counter + 1] = mat[((i2 + 5) - 1) % 5][(j2) % 5];
            } else {
                temp[counter] = mat[i1 % 5][j2 % 5];
                temp[counter + 1] = mat[(i2) % 5][(j1) % 5];
            }
            counter += 2;
        }
        return new String(Arrays.copyOf(temp, temp.length));
    }

    static void encryptfile(File plainTextFile, String key) throws IOException {

        Scanner scanner = new Scanner(plainTextFile);

        File output = new File("encrypt.txt");
        PrintWriter pw = new PrintWriter(output);

        while (scanner.hasNext()) {
            pw.println(encrypt(scanner.nextLine(), key));
        }

        pw.flush();
        pw.close();
    }

    static void decryptfile(File cipherTextFile, String key) throws IOException {

        Scanner input = new Scanner(cipherTextFile);

        File output = new File("decrypt.txt");
        PrintWriter pw = new PrintWriter(output);

        while (input.hasNext()) {
            pw.println(decrypt(input.nextLine(), key));
        }

        pw.flush();
        pw.close();
    }
}

// TODO code application logic here

