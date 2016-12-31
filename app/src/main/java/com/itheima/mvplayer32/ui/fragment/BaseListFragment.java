package com.itheima.mvplayer32.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itheima.mvplayer32.R;
import com.itheima.mvplayer32.presenter.BaseListPresenter;
import com.itheima.mvplayer32.view.BaseListView;

import butterknife.BindView;

public abstract class BaseListFragment extends BaseFragment implements BaseListView{
    public static final String TAG = "BaseListFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.Adapter mAdapter;

    private BaseListPresenter mBaseListPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void init() {
        super.init();
        mBaseListPresenter = getPresenter(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        initRecyclerView();

        mBaseListPresenter.loadDataList();
    }

    /**
     * 子类返回一个presenter的具体实现
     * @return
     */
    protected abstract BaseListPresenter getPresenter(BaseListView listView);

    private void initRecyclerView() {
        mAdapter = getAdapter();
        //设置recyclerview有固定大小，内部会做一些优化
        mRecyclerView.setHasFixedSize(true);
        //一定要设置，不然看不到条目
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    /**
     * 子类返回一个Adapter
     * @return
     */
    public abstract RecyclerView.Adapter getAdapter();

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //判断RecyclerView是否在idle状态
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //判断是否滑到底
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == mBaseListPresenter.getDataList().size()) {
                    //加载更多数据
                    //调用P层
                    mBaseListPresenter.loadMoreData();
                }

            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    };
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            //刷新调用P层API
            mBaseListPresenter.refresh();
        }
    };

    @Override
    public void onDataLoaded() {
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDataLoadFailed(String error) {

    }
}
