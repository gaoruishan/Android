
package demo.grs.com.android.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.x;

import demo.grs.com.android.app.AppManager;

/**
 * 这是一个Activity的基类，所有的都要继承它
 *
 * 说明:FragmentActivity
 * android-support-v4.jar兼容包里面的，它提供了操作fragment的一些方法，其功能跟3.0及以后的版本的Activity的功能一样。
 * 说明:AppCompatActivity
 * 初期AppCompat只是让Actionbar兼容到API 7。在AppCompat 21版本中，加入主题色、Toolbar等功能。显然ActionBarActivity这个名字已经不在适用AppCompat。新版本中，推荐使用AppCompatActivity 代替ActionBarActivity。
 *
 * AutoLayoutActivity 继承AppCompatActivity 又继承FragmentActivity
 *
 */
public abstract class BaseActivity extends AutoLayoutActivity {
    protected Activity activity;
    protected String requestTag;

    //    public final int REQUEST_CODE_LOGIN_NEW_COMMENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在基类中注入
        x.view().inject(this);
        //进出和退出动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        requestTag = getClass().getName();
        activity = this;
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    public void finish() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //页面不可见时，取消所有的requestTag，RequestManager是什么呢？
//        RequestManager.getInstance().cancelAll(requestTag);
    }

//    protected void executeRequest(Request<?> request) {
//        RequestManager.getInstance().addRequest(request, requestTag);
//    }
    /**
     * 提供跳转
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }
    /**
     * app字体不随系统字体变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
