package com.itheima.mvplayer32.network;

import com.itheima.mvplayer32.model.AreaBean;
import com.itheima.mvplayer32.utils.URLProviderUtils;

import java.util.List;

public class MVAreaRequest extends MVPlayer32Request<List<AreaBean>> {
    public static final String TAG = "MVAreaRequest";

    public MVAreaRequest(String url, NetworkListener listener) {
        super(url, listener);
    }

    public static MVAreaRequest getMVAreaRequest(NetworkListener listener) {
        return new MVAreaRequest(URLProviderUtils.getMVareaUrl(), listener);
    }
}
