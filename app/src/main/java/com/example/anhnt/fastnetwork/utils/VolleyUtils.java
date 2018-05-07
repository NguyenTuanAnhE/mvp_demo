package com.example.anhnt.fastnetwork.utils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by MSI GL627RD on 1/12/2018.
 */

public class VolleyUtils {

    public static String makeUrl(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (i == 1) {
                stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue() );
            }
            iterator.remove(); // avoids a ConcurrentModificationException
            i++;
        }
        return stringBuilder.toString();
    }

}
