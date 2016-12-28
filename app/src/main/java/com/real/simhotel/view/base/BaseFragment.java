package com.real.simhotel.view.base;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.real.simhotel.presenter.base.Presenter;
import com.real.simhotel.view.iview.IBaseView;
import com.real.simhotel.view.loading.VaryViewHelperController;



import butterknife.ButterKnife;

/**
 * Created by liudan on 2016/12/7.
 */
public abstract class BaseFragment<T extends Presenter> extends Fragment implements IBaseView {
    //与Fragment绑定的activity对象
    protected BaseActivity mActivity;
    //当前View的Presenter
    protected T mPresenter;
    private View contentView;

    private VaryViewHelperController mVaryViewHelperController;

    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化数据 页面加载完毕调用
     */
    protected abstract void initData();

    /**
     * 切换到页面需要重新加载数据的实现此方法
     */
    public abstract void loadData();

    //获取布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            initView(contentView, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
        }
        if (null == mVaryViewHelperController)
            mVaryViewHelperController = new VaryViewHelperController(getLoaingTargetView());
        if (null == mPresenter)
            mPresenter = getChildPresenter();
        return contentView;
    }

    protected abstract T getChildPresenter();

    protected abstract View getLoaingTargetView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (null != this.getView()) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenter)
            mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mPresenter)
            mPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (null != mPresenter)
            mPresenter.destroy();
    }

    @Override
    public BaseActivity getContext() {
        return mActivity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Google bug
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


    protected String getRequestParams() {
        return null;
    }


    @Override
    public void showLoading() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.showLoading();
    }

    @Override
    public void refreshView() {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.restore();
    }

    @Override
    public void showError(String msg) {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        //点击重新加载
        mVaryViewHelperController.showNetworkError(msg,v -> {
            showLoading();
            mPresenter.requestData(getRequestParams());
        });
    }

    @Override
    public void showEmptyView(String msg) {
        if (mVaryViewHelperController == null) {
            throw new IllegalStateException("no ViewHelperController");
        }
        mVaryViewHelperController.showEmpty(msg);
    }

    @Override
    public void showToast(String text){
        Toast.makeText(getHoldingActivity(),text,Toast.LENGTH_SHORT).show();
    }
}
