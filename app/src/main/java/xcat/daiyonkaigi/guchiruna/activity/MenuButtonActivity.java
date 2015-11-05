package xcat.daiyonkaigi.guchiruna.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import xcat.daiyonkaigi.guchiruna.R;

/**
 * Created by sleepinbag on 2015/11/01.
 */
public class MenuButtonActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_button_activity);

        ImageButton negap = (ImageButton)findViewById(R.id.negapozi);
        ImageButton timeline  = (ImageButton)findViewById(R.id.timeline);
        ImageButton tweet  = (ImageButton)findViewById(R.id.tweet);

        //thisにしておく
        negap.setOnClickListener(this);
        timeline.setOnClickListener(this);
        tweet.setOnClickListener(this);
    }

    /**
     * メニューボタン押下時の処理です。
     * @param v ビュー
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.negapozi:
                v.findViewById(R.id.negapozi).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ImageButton nextButton = (ImageButton) findViewById(R.id.negapozi);
                        nextButton.setOnClickListener(this);
                        setContentView(R.layout.negapozi_layout);
                        };
                });
                break;
            case R.id.timeline:
                v.findViewById(R.id.timeline).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //次画面での表示処理
                        Intent timeIntent = new Intent(getApplicationContext(), TimelineActivity.class);
                        startActivity(timeIntent);
                    }
                });
                break;
            case R.id.tweet:
                v.findViewById(R.id.tweet).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //次画面での表示処理
                        Intent inputIntent = new Intent(getApplicationContext(), InputActivity.class);
                        startActivity(inputIntent);
                    }
                });
                break;
        }
    }
}
