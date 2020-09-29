package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.util.*;
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView ten = (TextView) convertView.findViewById(R.id.textviewLoaiSP);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewHinhAnh);
        LoaiSP sp = loaiSPList.get(position);
        ten.setText(sp.getTenloaisp());
        Picasso.with(context).load(sp.getHinhAnhLoaiSP()).placeholder(R.drawable.noimage).error(R.drawable.error).into(imageView);


        return convertView;
    }
}
