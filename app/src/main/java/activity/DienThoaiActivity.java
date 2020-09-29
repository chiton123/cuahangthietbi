package activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDT;
    ListView listViewDT;
    ArrayList<Sanpham> sanphamArrayList;
    DienThoaiAdapter adapter;
    int iddt ;
    int page = 1;
    String urlDienThoai ="http://10.3.74.66:8888/server/getcacsanpham.php?page=";
    String urlTest = "http://10.3.74.66:8888/server/test.php?page=";
    View footerView ;
    boolean limitdata = false;
    boolean isloading = false;
    mHandler mHandler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            GetIDLoaiSP();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else {
            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");
            finish();
        }
    }

    private void LoadMoreData() {
        listViewDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", sanphamArrayList.get(position));
                startActivity(intent);
            }
        });
        listViewDT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isloading == false && limitdata == false){
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page)  {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
      //  String url = urlDienThoai + Page;
        String urltest = urlTest + Page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        int ID = 0;
                        String tenDT = "";
                        int giaDT = 0;
                        String hinhAnhDT = "";
                        String motaDT = "";
                        int IDSP = 0;
                        if(response != null && response.length() != 2 ){
                          //  Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            listViewDT.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    ID = object.getInt("id");
                                    tenDT = object.getString("tensanpham");
                                    giaDT = object.getInt("giasanpham");
                                    hinhAnhDT = object.getString("hinhanhsanpham");
                                    motaDT = object.getString("motasanpham");
                                    IDSP = object.getInt("idsanpham");
                                    sanphamArrayList.add(new Sanpham(ID, tenDT, giaDT, hinhAnhDT, motaDT, IDSP));
                                   // Toast.makeText(getApplicationContext(), tenDT.toString(), Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "khong lay dc", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            limitdata = true;
                            listViewDT.removeFooterView(footerView);
                            CheckConnection.showToast(getApplicationContext(), "da het du lieu");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idsanpham", String.valueOf(iddt));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIDLoaiSP() {
        iddt = getIntent().getIntExtra("idsanpham", -1);
        Log.d("idsanpham",iddt+"");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void anhxa() {
        toolbarDT = (Toolbar) findViewById(R.id.toolbarDienThoai);
        listViewDT = (ListView) findViewById(R.id.listviewDienThoai);
        sanphamArrayList = new ArrayList<>();
        adapter = new DienThoaiAdapter(getApplicationContext(), sanphamArrayList);
        listViewDT.setAdapter(adapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewDT.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isloading = false;
                    break;
            }

            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
