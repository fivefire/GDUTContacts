package com.fivefire.app.gdutcontacts.utils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by MicroStudent on 2016/5/20.
 */
public final class PinyinUtils {
    public static String[] getPinyinString(String s) {
        String[] pinyin = new String[s.length()];
        for (int i = 0; i < s.length(); i++) {
            if (Pinyin.isChinese(s.charAt(i))) {
                pinyin[i] = Pinyin.toPinyin(s.charAt(i));
            }
        }
        return pinyin;
    }
}
