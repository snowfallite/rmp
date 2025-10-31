package com.example.my_app.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.my_app.R;
import com.example.my_app.activities.LocationItem;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (item != null) {
            h.tvAddress.setText(item.getCity() + ", " + item.getStreet() + " " + item.getHouse());
            h.tvType.setText(item.getType());
            h.tvWork.setText(item.getWorkingHours());

            // Улучшенная логика статуса: парсим часы работы и проверяем текущее время
            boolean isOpen = isCurrentlyOpen(item.getWorkingHours());
            h.tvStatus.setText(isOpen ? "Работает" : "Закрыто");
            h.tvStatus.setTextColor(isOpen ? Color.GREEN : Color.RED);
        }
    }

    @Override
    public int getItemCount() { return items != null ? items.size() : 0; }

    /**
     * Проверяет, работает ли локация сейчас на основе строки workingHours.
     * Поддерживает форматы типа "09:00-18:00", "Круглосуточно", "24/7".
     * Если формат неизвестен или пустой, считает закрытым.
     */
    private boolean isCurrentlyOpen(String workingHours) {
        if (workingHours == null || workingHours.trim().isEmpty()) {
            return false; // Закрыто, если часы не указаны
        }

        String lower = workingHours.toLowerCase().trim();
        if (lower.contains("круглосуточно") || lower.contains("24/7") || lower.contains("24/7")) {
            return true; // Всегда открыто
        }

        // Регулярка для формата "HH:mm-HH:mm" (например, "09:00-18:00")
        Pattern pattern = Pattern.compile("(\\d{1,2}):(\\d{2})-(\\d{1,2}):(\\d{2})");
        Matcher matcher = pattern.matcher(workingHours);
        if (matcher.find()) {
            try {
                int startHour = Integer.parseInt(matcher.group(1));
                int startMinute = Integer.parseInt(matcher.group(2));
                int endHour = Integer.parseInt(matcher.group(3));
                int endMinute = Integer.parseInt(matcher.group(4));

                Calendar now = Calendar.getInstance();
                int currentHour = now.get(Calendar.HOUR_OF_DAY);
                int currentMinute = now.get(Calendar.MINUTE);
                int currentTime = currentHour * 60 + currentMinute;
                int startTime = startHour * 60 + startMinute;
                int endTime = endHour * 60 + endMinute;

                // Проверяем, если конец дня раньше начала (например, 22:00-02:00), но для простоты считаем ежедневно
                if (startTime < endTime) {
                    return currentTime >= startTime && currentTime <= endTime;
                } else {
                    // Если переходит через полночь (например, 22:00-02:00), но пока не обрабатываем — fallback
                    return false;
                }
            } catch (NumberFormatException e) {
                // Если парсинг сломался, считаем закрытым
                return false;
            }
        }

        // Если формат не распознан, считаем закрытым
        return false;
    }

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
