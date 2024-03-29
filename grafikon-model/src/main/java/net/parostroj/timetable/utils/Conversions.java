package net.parostroj.timetable.utils;

import java.awt.Color;
import java.io.*;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

/**
 * Converting utility.
 *
 * @author jub
 */
public class Conversions {

    public static Color convertTextToColor(String text) {
        return Color.decode(text);
    }

    public static String convertColorToText(Color c) {
        return String.format("0x%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    public static String loadFile(final InputStream is) throws IOException {
        return new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return is;
            }
        }.asCharSource(Charsets.UTF_8).read();
    }
}
