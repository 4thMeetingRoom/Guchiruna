package xcat.daiyonkaigi.guchiruna.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.StringTokenizer;

import xcat.daiyonkaigi.guchiruna.R;
import xcat.daiyonkaigi.guchiruna.db.GuchiCommonDBOpenHelper;

/**
 * Created by anago on 2015/10/13.
 */
public class RankingActivity extends Activity {
    private Button menuButton;
    private Button guchiButton;
    private Button negapoziButton;
    private Intent intent;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ranking_layout);

        listView = (ListView)findViewById(R.id.showData);

        //DB作成
        GuchiCommonDBOpenHelper rankingDB = new GuchiCommonDBOpenHelper(this);
        SQLiteDatabase db = rankingDB.getReadableDatabase();

        //SQL
        Cursor c = db.rawQuery("select word as _id, count(*) from rankings group by word order by count(*) desc", null);

        boolean mov = c.moveToFirst();
        String[] rankArr = makeRanking(c);
        String[] rankViewArr = new String[rankArr.length];
        int count = 0;

        while (mov){
            String word = c.getString(0);
            String rank = "（" + c.getString(1) + "回）";
            rankViewArr[count] = word + "," + rank + "," + rankArr[count];
            mov = c.moveToNext();
            count++;
        }

        RankingListAdapter adpviewList = new RankingListAdapter(this, rankViewArr);
        listView.setAdapter(adpviewList);

        //メニューボタンの表示処理
        View menu = findViewById(R.id.menu_layout);
        menu.findViewById(R.id.negapozi).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton menuButton = (ImageButton) menu.findViewById(R.id.negapozi);
                menuButton.setOnClickListener(this);
                //次画面での表示処理
                Intent negaIntent = new Intent(RankingActivity.this, NegapoziActivity.class);
                startActivity(negaIntent);
            }});

        menu.findViewById(R.id.tweet).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton menuButton = (ImageButton) menu.findViewById(R.id.tweet);
                menuButton.setOnClickListener(this);
                //次画面での表示処理
                Intent inputIntent = new Intent(RankingActivity.this, InputActivity.class);
                startActivity(inputIntent);
            }});

        menu.findViewById(R.id.timeline).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View menu = findViewById(R.id.menu_layout);
                ImageButton timeButton = (ImageButton) menu.findViewById(R.id.timeline);
                timeButton.setOnClickListener(this);
                //次画面での表示処理
                Intent timeIntent = new Intent(RankingActivity.this, TimelineActivity.class);
                startActivity(timeIntent);
            }
        });

        /** テスト用ソース（っぽい）ためコメントアウト
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String s1 = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
        String s2 = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();

        Log.v("tama", "position=" + s1);
        Log.v("tama", "position=" + s2);
        }
        });
         **/
    }

    /**
     * ランキング順位用の文字列を生成するメソッドです。
     * @param c ランキングテーブルのカーソルオブジェクト
     * @return ランキング順位リストを格納した文字列配列
     */
    private String[] makeRanking(Cursor c) {
        //ランキング表示用文字列を初期化
        int totalCnt = c.getCount();
        String[] returnCnt = new String[totalCnt];

        //ランキングが何位まで存在するかを判定
        for(int i = 0; i < totalCnt; i++) {
            returnCnt[i] = Integer.toString(i + 1) + "位";
        }
        return returnCnt;
    }

    /**
     * ランキング画面表示用のListAdapter拡張クラスです。
     *
     */
    private class RankingListAdapter extends ArrayAdapter<String> {
        private LayoutInflater myInflater;
        private TextView rank;
        private TextView word;
        private TextView count;

        public RankingListAdapter(Context context, String[] objects) {
            super(context, 0, objects);
            myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.ranking_col, null);
            }
            rank = (TextView) convertView.findViewById(R.id.rank);
            word = (TextView) convertView.findViewById(R.id.word);
            count = (TextView) convertView.findViewById(R.id.count);

            //adapter生成時に渡された配列を取得
            String rankArr = this.getItem(position);

            if(null != rankArr) {
                String[] str = new String[3];
                StringTokenizer stk = new StringTokenizer(rankArr, ",");
                int i = 0;

                //文字列から文字列配列に変換
                while(stk.hasMoreTokens()) {
                    str[i] = stk.nextToken();
                    i++;
                }
                word.setText(str[0]);
                count.setText(str[1]);
                rank.setText(str[2]);
            }
            return convertView;
        }
    }
}