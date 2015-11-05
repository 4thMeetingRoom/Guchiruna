package xcat.daiyonkaigi.guchiruna.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import xcat.daiyonkaigi.guchiruna.R;
import xcat.daiyonkaigi.guchiruna.db.GuchiCommonDBOpenHelper;

/**
 * データベースの実体を表示させるためのクラスです。
 * activity_timeline.xmlと紐付いたActivityです。
 *
 */
public class TimelineActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

        //TODO 使いまわせるようにリファクタリングする
        //メニューボタンの表示処理
        View menu = findViewById(R.id.menu_layout);
        menu.findViewById(R.id.negapozi).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton menuButton = (ImageButton) menu.findViewById(R.id.negapozi);
                menuButton.setOnClickListener(this);
                //setContentView(R.layout.activity_negapozi);
                //次画面での表示処理
                Intent negaIntent = new Intent(TimelineActivity.this, NegapoziActivity.class);
                startActivity(negaIntent);
            }});

        menu.findViewById(R.id.tweet).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton menuButton = (ImageButton) menu.findViewById(R.id.tweet);
                menuButton.setOnClickListener(this);
                //setContentView(R.layout.activity_input);
                //次画面での表示処理
                Intent inputIntent = new Intent(TimelineActivity.this, InputActivity.class);
                startActivity(inputIntent);
            }});

        menu.findViewById(R.id.timeline).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton inputButton = (ImageButton) menu.findViewById(R.id.timeline);
                inputButton.setOnClickListener(this);
                //setContentView(R.layout.activity_timeline);
                //次画面での表示処理
                Intent rankIntent = new Intent(TimelineActivity.this, RankingActivity.class);
                startActivity(rankIntent);
            }
        });

        GuchiCommonDBOpenHelper helper = new GuchiCommonDBOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ListView listView = (ListView)findViewById(R.id.timeList);

        Cursor c = db.rawQuery("select date as _id, article from article", null);

        String[] from = {"_id","article"};
        int[] to = {R.id.TextView01, R.id.TextView02};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.timeline_col,c,from,to,0);
        listView.setAdapter(adapter);
    }
}