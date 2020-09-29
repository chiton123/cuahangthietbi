package activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.LoaiSP;

public class LoaiSPAdapter extends BaseAdapter {
    Context context;
    List<LoaiSP> loaiSPList;

    public LoaiSPAdapter(Context context, List<LoaiSP> loaiSPList) {
        this.context = context;
        this.loaiSPList = loaiSPList;
    }

    @Override
    public int getCount() {
        return loaiSPList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSPList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_loai_sp, null);
            viewHolder.txttenloaisp = (TextView) convertView.findViewById(R.id.textviewLoaiSP);
            viewHolder.imgloaisp = (ImageView) convertView.findViewById(R.id.imageViewHinhAnh);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            LoaiSP sp = (LoaiSP) getItem(position);
            viewHolder.txttenloaisp.setText(sp.getTenloaisp());
            Picasso.with(context).load(sp.getHinhAnhLoaiSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgloaisp);

        }





        return convertView;
    }
}
