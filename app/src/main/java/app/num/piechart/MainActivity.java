package app.num.piechart;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
//import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


public class MainActivity extends AppCompatActivity {

    String myJSON;
    //SwipeRefreshLayout mSwipe;

    private static final int SIZE = 8; //flag 갯수
    private static final String TAG_RESULTS = "result"; //php에서 가져오는 부분
    //private static final String TAG_POSTURE = "posture"; // 자세이름
    private static final String TAG_FLAG = "flag"; // 자세이름
    private static final int[] items = new int[SIZE]; // 누적데이터 인덱스에 맞게 count 저장

    private int sum = 1;
    JSONArray postures = null;
    PieChart pieChart; //따로 선언
/*
    class swipeListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            getData("http://192.168.1.100/php.php"); //data 저장된 주소
            //getData("http://210.121.154.235:3000");
            mSwipe.setRefreshing(false); // 새로고침 버튼
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,Splash.class));
        //getData("http://210.121.154.235:3000");
        getData("http://192.168.1.100/php.php");

        //mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        //mSwipe.setOnRefreshListener(new swipeListener());

        pieChart = (PieChart) findViewById(R.id.chart); // piechart 선언
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry((float) (items[1] / sum), 0));
        entries.add(new Entry((float) (items[2] / sum), 1));
        entries.add(new Entry((float) (items[3] / sum), 2));
        entries.add(new Entry((float) (items[4] / sum), 3));
        entries.add(new Entry((float) (items[5] + items[6] / sum), 4));

        for (int i = 0; i < SIZE; i++) {
            sum += items[i];  //sum 구하기
        }

        PieDataSet dataset = new PieDataSet(entries, "자세통계");
        ArrayList<String> labels = new ArrayList<String>();

        labels.add("뒤로기움");
        labels.add("오른쪽으로기움");
        labels.add("왼쪽으로기움");
        labels.add("앞으로기움");
        labels.add("다리꼼");

        PieData data = new PieData(labels, dataset); // pie chart에 이름, data 넣어주기
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // 색 지정
        pieChart.setDescription("내옆의 친구,바로"); // 이름
        pieChart.setData(data);
        pieChart.animateY(5000);// data 세팅

    }

    protected void showPie() {

        for (int i = 0; i < SIZE; i++) {
            items[i] = 0;  //item 배열 초기화
        }

        try {
            if (myJSON == null) {
                Toast.makeText(getApplicationContext(),"Null occured",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonObj = new JSONObject(myJSON); // json 객체 만들기
            postures = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < postures.length(); i++) {
                JSONObject c = postures.getJSONObject(i);
                String posture = c.getString(TAG_FLAG);
                int numInt = Integer.parseInt(posture);

                if (numInt == 0 || numInt ==7) continue;
                else if(numInt >= 8) continue;
                else items[numInt]++; // flag를 index로 해서 각각의 count 계산
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showPie();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
