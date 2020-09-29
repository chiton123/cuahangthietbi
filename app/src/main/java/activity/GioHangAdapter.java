package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHoler{
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggioihang;
        public Button btnminus, btnvalue, btnplus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if(convertView == null){
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang, null);
            viewHoler.txttengiohang = (TextView) convertView.findViewById(R.id.textviewTenGioHang);
            viewHoler.txtgiagiohang = (TextView) convertView.findViewById(R.id.textviewGiaGioHang);
            viewHoler.imggioihang = (ImageView) convertView.findViewById(R.id.imageViewGioHang);
            viewHoler.btnminus = (Button) convertView.findViewById(R.id.buttonMinus);
            viewHoler.btnvalue = (Button)convertView.findViewById(R.id.buttonValue);
            viewHoler.btnplus = (Button)convertView.findViewById(R.id.buttonPlus);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHoler.txttengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtgiagiohang.setText(decimalFormat.format(gioHang.getGiasp())  + "Đ");
        Picasso.with(context).load(gioHang.getHinhsp()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHoler.imggioihang);
        viewHoler.btnvalue.setText(gioHang.getSoluongsp() +"");
        int sl = Integer.parseInt(viewHoler.btnvalue.getText().toString());
//        if(sl >= 10){
//            viewHoler.btnplus.setVisibility(View.INVISIBLE);
//            viewHoler.btnminus.setVisibility(View.VISIBLE);
//        } else if(sl <= 1){
//            viewHoler.btnminus.setVisibility(View.INVISIBLE);
//        }
//        else {
//            viewHoler.btnminus.setVisibility(View.VISIBLE);
//            viewHoler.btnplus.setVisibility(View.VISIBLE);
//        }
        final ViewHoler finalViewHoler = viewHoler;
        viewHoler.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoinhat = Integer.parseInt(finalViewHoler.btnvalue.getText().toString()) + 1;
                int soluonghientai = MainActivity.mangGioHang.get(position).getSoluongsp();
                long giahientai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(soluongmoinhat);
                long giamoinhat = (soluongmoinhat * giahientai) / soluonghientai;
                MainActivity.mangGioHang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtgiagiohang.setText(decimalFormat.format(giamoinhat) +" đồng" );
                GioHangActivity.EventUntil();
                if(soluongmoinhat > 9){
//                    finalViewHoler.btnplus.setVisibility(View.INVISIBLE);
//                    finalViewHoler.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalue.setText(soluongmoinhat+"");
                } else {
//                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
//                    finalViewHoler.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnvalue.setText(soluongmoinhat+"");

                }
            }
        });
        viewHoler.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoinhat = Integer.parseInt(finalViewHoler.btnvalue.getText().toString()) - 1;
                int soluonghientai = MainActivity.mangGioHang.get(position).getSoluongsp();
                long giahientai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(soluongmoinhat);
                long giamoinhat = (soluongmoinhat * giahientai) / soluonghientai;
                MainActivity.mangGioHang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtgiagiohang.setText(decimalFormat.format(giamoinhat) +" đồng" );
                GioHangActivity.EventUntil();
                if(soluongmoinhat < 2){
//                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
//                    finalViewHoler.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnvalue.setText(soluongmoinhat+"");
                } else {
//                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
//                    finalViewHoler.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnvalue.setText(soluongmoinhat+"");

                }
            }

        });


        return convertView;
    }
}
