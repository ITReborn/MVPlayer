package com.itheima.mvplayer32.utils;

import android.util.Log;

import com.itheima.mvplayer32.model.LyricBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LyricParser {
    public static final String TAG = "LyricParser";

    private static final String DIRECTORY = "/storage/sdcard0/Download/audio/";

    public static List<LyricBean> parseLyric(String lyricFileName) {
        List<LyricBean> lyrics = new ArrayList<LyricBean>();
        String path = DIRECTORY + lyricFileName + ".txt";
        File file = new File(path);
        if (!file.exists()) {
            path = DIRECTORY + lyricFileName + ".lrc";
            file = new File(path);
            if (!file.exists()) {
                return lyrics;//没有找到相应歌词文件
            }
        }
        Log.d(TAG, "parseLyric: found lyric");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                //[01:22.04][02:35.04]寂寞的夜和谁说话
                List<LyricBean> lineBeans = parseLine(line);
                lyrics.addAll(lineBeans);

                line = reader.readLine();//读取下一行
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
       }

        Collections.sort(lyrics, new Comparator<LyricBean>() {
            @Override
            public int compare(LyricBean o1, LyricBean o2) {
                //时间戳的升序排列
                return o1.getTimestamp() - o2.getTimestamp();
            }
        });

        return lyrics;
    }

    /**
     * 解析一行歌词文本
     * @param line
     * @return
     */
    private static List<LyricBean> parseLine(String line) {
        List<LyricBean> lyricBeen = new ArrayList<LyricBean>();
        //[01:22.04][02:35.04]寂寞的夜和谁说话
        String[] split = line.split("]");
        //[01:22.04    [02:35.04       寂寞的夜和谁说话
        for (int i = 0; i <split.length - 1 ; i++) {
            LyricBean bean = new LyricBean();
            bean.setLyric(split[split.length - 1]);//设置歌词
            int timestamp = parseTimestamp(split[i]);
            bean.setTimestamp(timestamp);
            lyricBeen.add(bean);
        }
        return lyricBeen;
    }

    /**
     * 解析时间戳
     * @param s
     * @return
     */
    private static int parseTimestamp(String s) {
        //[01:22.04
        String[] array1 = s.split(":");
        //[01     22.04
        int minute = Integer.parseInt(array1[0].substring(1));
        //22.04
        String[] array2 = array1[1].split("\\.");
        // 22  04
        int second = Integer.parseInt(array2[0]);
        int millis = Integer.parseInt(array2[1]);

        return minute * 60 * 1000 + second * 1000 + millis;
    }
}
