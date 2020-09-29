package activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangthietbi.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> sanphamArrayList;

    public SanPhamAdapter(Context context, ArrayList<Sanpham> sanphamArrayList) {
        this.context = context;
        this.sanphamArrayList = sanphamArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_pham, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sp = sanphamArrayList.get(position);
        holder.txtTenSP.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: "+ decimalFormat.format(sp.getGiasanpham()) + " đồng");
        Picasso.with(context).load(sp.getHinhanhsanpham()).placeholder(R.drawable.noimage).error(R.drawable.error).into(holder.imgHinhAnhSP);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhAnhSP;
        public TextView txtTenSP, txtGiaSP;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imgHinhAnhSP = (ImageView) itemView.findViewById(R.id.imgSanPham);
            txtTenSP = (TextView) itemView.findViewById(R.id.tensp);
            txtGiaSP = (TextView) itemView.findViewById(R.id.giasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class); // coi chung sai o duoi
                    intent.putExtra("thongtinsanpham", sanphamArrayList.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
}
