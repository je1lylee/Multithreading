package top.linxixiangxin.multithreading;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class anr extends AppCompatActivity{
    private static final int MESSAG_UPDATE = 101;
    private static final int MESSAGE_COMPLETED = 102;
    private Handler handler;
//    private Button button;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        textView = findViewById(R.id.txt1);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) { //msg是从消息队列中取出的消息
                switch (msg.what) {
                    case MESSAG_UPDATE:
                        textView.setText(msg.arg1+"");
                        break;
                    case MESSAGE_COMPLETED:
                        textView.setText("更新完成！");
                        break;
                    default:
                        break;
                }
            }
        };
    }
    public void startANR(View view) { // onClick
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count < 50) {
                    count += 2;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.arg1 = count;
                    message.what = MESSAG_UPDATE;
                    handler.sendMessage(message);
                }
                handler.sendEmptyMessage(MESSAGE_COMPLETED); //数据发送完成标志
            }
        }).start();
    }
}
