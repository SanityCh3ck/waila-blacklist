package com.pennywis3.wailablacklist.utils;

import java.util.Collection;

/**
 * Created by Pennywis3 on 21.08.2015.
 */
public class StringHelper {

    public static String[] dismantle(String input, char delimiter) {
        if(input.isEmpty()) return new String[0];
        int length = 1;
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == delimiter) length++;
        }
        String[] result = new String[length];
        int index = 0;
        for(int i = 0; i < length; i++) {
            StringBuilder b = new StringBuilder("");
            while (index < input.length()) {
                if(input.charAt(index) == delimiter) {
                    index++;
                    break;
                }
                b.append(input.charAt(index++));
            }

            result[i] = b.toString();
        }

        return result;
    }

    public static String build(Collection<String> input, char delimiter){
        return build(input.toArray(new String[input.size()]), delimiter);
    }

    public static String build(String[] input, char delimiter){
        if(input == null || input.length == 0) return "";
        StringBuilder builder = new StringBuilder("");

        for(int i = 0; i < input.length; i++) {
            builder.append(input[i]);
            if(i < input.length-1) {
                builder.append(delimiter);
            }
        }

        return builder.toString();
    }

    public static String build(int[] input, char delimiter) {
        if(input == null || input.length == 0) return "";
        StringBuilder builder = new StringBuilder("");

        for(int i = 0; i < input.length; i++) {
            builder.append(input[i]);
            if(i < input.length-1) {
                builder.append(delimiter);
            }
        }
        return builder.toString();
    }
}
