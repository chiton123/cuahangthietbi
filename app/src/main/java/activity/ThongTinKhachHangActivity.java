package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText editTenKhachHang, editSoDienThoai, editEmail;
    Button btnXacNhan, btnTroLai;
    String urlkhachhang = "http://10.3.74.66:8888/server/thongtinkhachhang.php";
    String urlChiTietDonHang = "http://10.3.74.66:8888/server/chitietdonhang.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        anhxa();
        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.showToast(getApplicationContext(), "Hãy kiểm tra lại sự kết nối ");
        }

    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = editTenKhachHang.getText().toString();
                final String sodt = editSoDienThoai.getText().toString();
                final String email = editEmail.getText().toString();
                if(ten.length() > 0 && sodt.length() >0 && email.length() >0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlkhachhang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String madonhang) {
                                    Log.d("madonhang", madonhang);
                                    if(madonhang.length() >0){
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, urlChiTietDonHang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if(response.equals("1")){
                                                            MainActivity.mangGioHang.clear();
                                                            CheckConnection.showToast(getApplicationContext(), "Bạn đã đặt hàng thành công");
                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            startActivity(intent);
                                                            CheckConnection.showToast(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                                        }else {
                                                            CheckConnection.showToast(getApplicationContext(), "Giỏ hàng của bạn bị lỗi");
                                                        }


                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                JSONArray jsonArray = new JSONArray();
                                                for(int i=0 ; i < MainActivity.mangGioHang.size(); i++){
                                                    JSONObject object = new JSONObject();
                                                    try {
                                                        object.put("madonhang", madonhang);
                                                        object.put("masanpham", MainActivity.mangGioHang.get(i).getIdsp());
                                                        object.put("tensanpham", MainActivity.mangGioHang.get(i).getTensp());
                                                        object.put("giasanpham", MainActivity.mangGioHang.get(i).getGiasp());
                                                        object.put("soluongsanpham", MainActivity.mangGioHang.get(i).getSoluongsp());

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(object);

                                                }
                                                HashMap<String, String> map = new HashMap<>();
                                                map.put("json", jsonArray.toString());


                                                return map;
                                            }
                                        };
                                        queue.add(request);
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("tenkhachhang", ten);
                            map.put("sodienthoai", sodt);
                            map.put("email", email);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);


                }else {
                    CheckConnection.showToast(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                }
            }
        }) ;

    }

    private void anhxa() {
        editTenKhachHang = (EditText) findViewById(R.id.edittextTenKhachHang);
        editEmail = (EditText) findViewById(R.id.edittextEmail);
        editSoDienThoai = (EditText) findViewById(R.id.edittextSodienthoai);
        btnXacNhan = (Button) findViewById(R.id.buttonXacNhan123);
        btnTroLai = (Button) findViewById(R.id.buttonTroVe);
    }
}
