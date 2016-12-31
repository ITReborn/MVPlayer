package com.itheima.mvplayer32.network;

import com.itheima.mvplayer32.model.MVPageBean;
import com.itheima.mvplayer32.utils.URLProviderUtils;

public class MVPageRequest extends MVPlayer32Request<MVPageBean> {
    public static final String TAG = "MVPageRequest";

    public MVPageRequest(String url, NetworkListener listener) {
        super(url, listener);
    }

    public static MVPageRequest getMVPageRequest(String area, int offset, NetworkListener listener) {
        return new MVPageRequest(URLProviderUtils.getMVListUrl(area, offset, DEFAULT_PAGE_SIZE), listener);
    }
 }
