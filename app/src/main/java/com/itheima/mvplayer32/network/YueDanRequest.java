package com.itheima.mvplayer32.network;

import com.itheima.mvplayer32.model.YueDanBean;
import com.itheima.mvplayer32.utils.URLProviderUtils;

public class YueDanRequest extends MVPlayer32Request<YueDanBean> {
    public static final String TAG = "YueDanRequest";

    public YueDanRequest(String url, NetworkListener listener) {
        super(url, listener);
    }

    public static YueDanRequest getYueDanRequest(int offset, NetworkListener listener) {
        return new YueDanRequest(URLProviderUtils.getYueDanUrl(offset, DEFAULT_PAGE_SIZE), listener);
    }
}
