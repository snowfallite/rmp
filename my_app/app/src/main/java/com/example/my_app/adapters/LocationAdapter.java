package com.example.my_app.adapters;
import com.example.my_app.R;
import com.example.my_app.activities.LocationItem;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocationItem> items;

    public LocationAdapter(List<LocationItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        LocationItem item = items.get(i);
        h.tvAddress.setText(item.getCity() + ", " + item.getStreet() + " " + item.getHouse());
        h.tvType.setText(item.getType());
        h.tvWork.setText(item.getWorkingHours());
        boolean open = item.getWorkingHours().contains("Круглосуточно");
        h.tvStatus.setText(open ? "Работает" : "Закрыто");
        h.tvStatus.setTextColor(open ? Color.GREEN : Color.RED);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress, tvType, tvWork, tvStatus;
        ViewHolder(View v) {
            super(v);
            tvAddress = v.findViewById(R.id.tv_address);
            tvType = v.findViewById(R.id.tv_type);
            tvWork = v.findViewById(R.id.tv_work);
            tvStatus = v.findViewById(R.id.tv_status);
        }
    }
}
