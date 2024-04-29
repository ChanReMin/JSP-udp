package Bai1_XuLyChuoi;

public class XuLyChuoi {
    static String getReverse(String str) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = temp;
        }
        return new String(chars);
    }

    static String getUpperCase(String str) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] = (char)(chars[i] - 'a' + 'A');
            }
        }
        return new String(chars);
    }

    static String getLowerCase(String str) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char)(chars[i] - 'A' + 'a');
            }
        }
        return new String(chars);
    }

    static String getUpperLower(String str) {
        if (str == null)
            return null;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] = (char)(chars[i] - 'a' + 'A');
            } else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char)(chars[i] - 'A' + 'a');
            }
        }
        return new String(chars);
    }

    static int countWords(String str) {
        if (str == null)
            return 0;
        String[] words = str.split("\\s+");
        return words.length;
    }

    static int countVowels(String str) {
        if (str == null)
            return 0;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                count++;
            }
        }
        return count;
    }
}
