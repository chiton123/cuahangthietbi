package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChiTietSP;
    TextView txtTenSP, txtGiaSP, txtMotaSP;
    Spinner spinner;
    ImageView imageViewChiTiet;
    Button btnDatmua;
    int Id = 0;
    String TenChiTiet = "";
    long GiaChiTiet = 0;
    String HinhAnhChiTiet = "";
    String MotaChiTiet = "";
    int IdSanPham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        actiobToolBar();
        getInfomation();
        CatchEventSpinner();
        EventButton();

    }

    private void EventButton() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGioHang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist = false;
                    for(int i=0; i < MainActivity.mangGioHang.size(); i++){
                        if(MainActivity.mangGioHang.get(i).getIdsp() == Id){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if(MainActivity.mangGioHang.get(i).getSoluongsp() > 10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(MainActivity.mangGioHang.get(i).getSoluongsp() * GiaChiTiet);
                            exist = true;

                        }
                    }
                    if(exist == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * GiaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(Id, TenChiTiet, giamoi,HinhAnhChiTiet, soluong));
                    }


                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * GiaChiTiet;
                    MainActivity.mangGioHang.add(new GioHang(Id, TenChiTiet, giamoi,HinhAnhChiTiet, soluong));

                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
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
    private void getInfomation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        Id = sanpham.getID();
        TenChiTiet = sanpham.getTensanpham();
        GiaChiTiet = sanpham.getGiasanpham();
        HinhAnhChiTiet = sanpham.getHinhanhsanpham();
        MotaChiTiet = sanpham.getMotasanpham();
        IdSanPham = sanpham.getIDsanpham();
        txtTenSP.setText(TenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSP.setText("Giá : "+ decimalFormat.format(GiaChiTiet) + " đồng");
        txtMotaSP.setText(MotaChiTiet);
        Picasso.with(getApplicationContext()).load(HinhAnhChiTiet).placeholder(R.drawable.noimage).error(R.drawable.error).into(imageViewChiTiet);


    }

    private void actiobToolBar() {
        setSupportActionBar(toolbarChiTietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void anhxa() {
        toolbarChiTietSP = (Toolbar) findViewById(R.id.toolBarChiTietSP);
        txtTenSP = (TextView) findViewById(R.id.textviewTenSanPham);
        txtGiaSP = (TextView) findViewById(R.id.textviewGiaChitietsp);
        txtMotaSP = (TextView) findViewById(R.id.textviewMotaChiTietSP);
        imageViewChiTiet = (ImageView) findViewById(R.id.imageViewHinhAnhChiTietSP);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnDatmua = (Button) findViewById(R.id.buttonThemGioHang);
    }
}
