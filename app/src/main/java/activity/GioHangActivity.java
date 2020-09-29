package activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangthietbi.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGioHang;
    GioHangAdapter adapter;
    ArrayList<GioHang> arrayList;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbarGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        ActionToolBar();
        CheckData();
        EventUntil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGioHang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "Giỏ hàng bạn chưa có gì!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void CatchOnItemListView() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GioHangActivity.this);
                alert.setTitle("XÁC NHẬN XÓA");
                alert.setMessage("Bạn có muốn xáo món hàng này không ?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.mangGioHang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.mangGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            EventUntil();
                            if(MainActivity.mangGioHang.size() <= 0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else {
                                MainActivity.mangGioHang.remove(position);
                                adapter.notifyDataSetChanged();
                                EventUntil();
                            }
                        }
                    }
                });
                alert.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                        EventUntil();
                    }
                });
                AlertDialog builder = alert.create();
                builder.show();

                return false;
            }
        });
    }

    public static void EventUntil() {
        long tongtien = 0;
        for(int i=0; i < MainActivity.mangGioHang.size(); i++){
            tongtien += MainActivity.mangGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien) + "Đồng");
    }

    private void CheckData() {
        if(MainActivity.mangGioHang.size() <=0){
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        }else {
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        listViewGioHang = (ListView) findViewById(R.id.listviewGioHang);
        txtThongBao = (TextView) findViewById(R.id.textviewThongBao);
        txtTongTien = (TextView) findViewById(R.id.textviewTongsotien);
        btnThanhToan = (Button) findViewById(R.id.buttonThanhToan);
        btnTiepTucMua = (Button) findViewById(R.id.buttonTiepTucMuaHang);
        toolbarGioHang = (Toolbar) findViewById(R.id.toolBarGioHang);
        adapter = new GioHangAdapter(getApplicationContext(), MainActivity.mangGioHang);
        listViewGioHang.setAdapter(adapter);

    }
}
