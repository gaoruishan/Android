package demo.grs.com.android.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import demo.grs.com.android.R;
import demo.grs.com.android.base.BaseActivity;
import demo.grs.com.android.utils.common.ToastUtils;
import demo.grs.com.android.utils.dialog.DialogFactory;

@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity {
    @ViewInject(R.id.pd)
    private ContentLoadingProgressBar pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog confirmDialog = DialogFactory.getConfirmDialog(this, "标题", "山东封建礼教分", "取消", "确定", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(TestActivity.this, "hhh");
            }
        });
        confirmDialog.show();
    }

}
