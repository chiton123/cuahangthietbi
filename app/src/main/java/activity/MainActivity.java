package activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ViewFlipper;
import android.content.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbi.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import model.LoaiSP;


public class MainActivity extends AppCompatActivity {
    String urlGetData = "http://10.3.74.66:8888/server/getloaisp.php";
    String urlSanPham = "http://10.3.74.66:8888/server/getsanpham.php";
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> mangLoaiSP;
    LoaiSPAdapter loaiSPAdapter;
    int id = 0;
    String tenLoaiSP = "";
    String hinhAnhLoaiSP = "";
    ArrayList<Sanpham> sanphamArrayList;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> mangGioHang;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
           getDuLieuLoaiSP();
           getDuLieuSanPham();
            listViewManHinhChinh.setAdapter(loaiSPAdapter);
            catchOnItemListView();
        }
        else {
            CheckConnection.showToast(getApplicationContext(), "Kiểm tra lại kết nối");
            finish();
        }
        loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(), mangLoaiSP);
        listViewManHinhChinh.setAdapter(loaiSPAdapter);

    }

    private void catchOnItemListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idsanpham", mangLoaiSP.get(3).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idsanpham", mangLoaiSP.get(4).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToast(getApplicationContext(), "Kiem tra lai ket noi");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                }
            }
        });
    }

    private void getDuLieuSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlSanPham, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String tenSP = "";
                    int giaSP = 0;
                    String hinhanhSP = "";
                    String mota = "";
                    int IDsanpham = 0;
                    for(int i=0; i < response.length(); i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("id");
                            tenSP = object.getString("tensp");
                            giaSP = object.getInt("giasp");
                            hinhanhSP = object.getString("hinhanhsp");
                            mota = object.getString("motasp");
                            IDsanpham = object.getInt("idsanpham");
                          //  Toast.makeText(MainActivity.this, tenSP.toString(), Toast.LENGTH_SHORT).show();
                            sanphamArrayList.add(new Sanpham(ID, tenSP, giaSP, hinhanhSP, mota, IDsanpham));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaiSP(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,urlGetData, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            for(int i=0; i < response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    id = object.getInt("id");
                                    tenLoaiSP = object.getString("tenloaisp");
                                    hinhAnhLoaiSP = object.getString("hinhanhloaisp");
                                  //  Toast.makeText(MainActivity.this, tenLoaiSP, Toast.LENGTH_SHORT).show();
                                    mangLoaiSP.add(new LoaiSP(id, tenLoaiSP, hinhAnhLoaiSP));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }


                        loaiSPAdapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void ActionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://taodoituong.com/wp-content/uploads/2018/07/facebook-ads-13-720x375.jpg");
        mangQuangCao.add("https://i.ytimg.com/vi/PAuMs3WCd68/maxresdefault.jpg");
        mangQuangCao.add("https://www.chotot.com/kinhnghiem/wp-content/uploads/2018/05/dien-thoai-samsung-cho-tot.jpg");
        mangQuangCao.add("https://ggstorage.oxii.vn/images/oxii-2019-5-6/728x436/4-mau-dien-thoai-tam-trung-cua-samsung-dang-mua-nhat-hien-naythumb.jpg");
        for (int i=0; i<mangQuangCao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
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
    private void anhxa(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFliper);
        recyclerViewManHinhChinh = (RecyclerView) findViewById(R.id.recycleview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewManHinhChinh = (ListView) findViewById(R.id.listviewmanhinhchinh);

        mangLoaiSP = new ArrayList<>();
        mangLoaiSP.add(0,new LoaiSP(0, "Trang chính", "https://cdn3.iconfinder.com/data/icons/ui-design-2/64/_Round_Main-512.png"));
        mangLoaiSP.add( new LoaiSP(0, "Liên Hệ", "https://w7.pngwing.com/pngs/535/146/png-transparent-business-company-service-wellington-graphic-supplies-job-contact-icon.png"));
        mangLoaiSP.add(new LoaiSP(0, "Thông tin", "https://png.pngtree.com/png-clipart/20190520/original/pngtree-info-icon-png-image_3550246.jpg"));
        sanphamArrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanphamArrayList);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewManHinhChinh.setAdapter(sanPhamAdapter);
        if(mangGioHang != null){

        } else {
            mangGioHang = new ArrayList<>();
        }



    }
}
