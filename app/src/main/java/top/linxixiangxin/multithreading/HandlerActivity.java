package top.linxixiangxin.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private ProgressBar pbBar;
    private Handler myHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);

        pbBar = (ProgressBar) findViewById(R.id.pb);
        textView = (TextView) findViewById(R.id.tv);

        Button button = (Button) findViewById(R.id.qd);
        button.setOnClickListener(this);

        // 内部类Handler实现
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pbBar.setProgress(0);
                switch (msg.what) {
                    case CONST.UPDATE_DATA:
                        textView.setText("已接收数据 " + msg.arg1 + "% ……");
                        pbBar.setProgress(msg.arg1);
                        break;
                    case CONST.UPDATE_COMPLETED:
                        textView.setText("接收数据完成！");
                        pbBar.setVisibility(View.GONE);//隐藏进度条
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View arg0) {
        //显示ProgressBar
        pbBar.setVisibility(View.VISIBLE);
        // Handler 应用程序主线程与子线程之间的通讯
        new Thread(){
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.what = CONST.UPDATE_DATA;
                    msg.arg1 = i * 10;
                    myHandler.sendMessage(msg);
                }
                //更新完成，发送空消息，只包含状态码
                myHandler.sendEmptyMessage(CONST.UPDATE_COMPLETED);
            }

        }.start();
    }
}