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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenDienThoai, txtGiaDienThoai, txtMoTaDienThoai;
        public ImageView imageViewHinhDienThoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dien_thoai, null);
            viewHolder.txtTenDienThoai = (TextView)convertView.findViewById(R.id.textviewDienThoai);
            viewHolder.txtGiaDienThoai = (TextView)convertView.findViewById(R.id.textviewGiaDienThoai);
            viewHolder.txtMoTaDienThoai = (TextView) convertView.findViewById(R.id.textviewMoTa);
            viewHolder.imageViewHinhDienThoai = (ImageView) convertView.findViewById(R.id.imgeviewDienThoai);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienThoai.setText("Gia "+ decimalFormat.format(sanpham.getGiasanpham()) + " d");
        viewHolder.txtTenDienThoai.setText(sanpham.getTensanpham());

        viewHolder.txtMoTaDienThoai.setMaxLines(2);
        viewHolder.txtMoTaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDienThoai.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imageViewHinhDienThoai);

        return convertView;
    }
}
