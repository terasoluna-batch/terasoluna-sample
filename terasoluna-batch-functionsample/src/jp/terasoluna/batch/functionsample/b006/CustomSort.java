package jp.terasoluna.batch.functionsample.b006;

import java.util.Comparator;

/**
 * SQLIDの末尾２ケタを比較して昇順に並び変えるためのComparator実装クラス
 */
public class CustomSort implements Comparator<String> {

    public int compare(String str1, String str2) {

        String subStr1 = str1.substring(str1.length() - 2);
        String subStr2 = str2.substring(str1.length() - 2);

        return subStr1.compareTo(subStr2);
    }
}
