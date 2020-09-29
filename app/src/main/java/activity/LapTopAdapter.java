package activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LapTopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLaptop;

    public LapTopAdapter(Context context, ArrayList<Sanpham> arrayDienThoai) {
        this.context = context;
        this.arrayLaptop = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenLaptop, txtGiaLaptop, txtMoTaLaptop;
        public ImageView imageViewHinhLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.txtTenLaptop = (TextView)convertView.findViewById(R.id.textviewTenLaptop);
            viewHolder.txtGiaLaptop = (TextView)convertView.findViewById(R.id.textviewGiaLaptop);
            viewHolder.txtMoTaLaptop = (TextView) convertView.findViewById(R.id.textviewMoTaLaptop);
            viewHolder.imageViewHinhLaptop = (ImageView) convertView.findViewById(R.id.imgeviewLaptop);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLaptop.setText("Gia "+ decimalFormat.format(sanpham.getGiasanpham()) + " d");
        viewHolder.txtTenLaptop.setText(sanpham.getTensanpham());

        viewHolder.txtMoTaLaptop.setMaxLines(2);
        viewHolder.txtMoTaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaLaptop.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imageViewHinhLaptop);

        return convertView;
    }
}
