package com.itheima.mvplayer32.network;

import com.itheima.mvplayer32.model.HomeListItemBean;
import com.itheima.mvplayer32.utils.URLProviderUtils;

import java.util.List;

public class HomeRequest extends MVPlayer32Request<List<HomeListItemBean>> {
    public static final String TAG = "HomeRequest";

    public HomeRequest(String url, NetworkListener listener) {
        super(url, listener);
    }

    public static HomeRequest getHomeRequest(int offset, NetworkListener listener) {
        return new HomeRequest(URLProviderUtils.getHomeUrl(offset, DEFAULT_PAGE_SIZE), listener);
    }
}
