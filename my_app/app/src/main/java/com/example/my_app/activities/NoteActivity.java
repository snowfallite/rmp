package com.example.my_app.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.my_app.R;
import com.example.my_app.base.MyBaseActivity;
import com.example.my_app.model.Note;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends MyBaseActivity {

    private EditText editTitle;
    private EditText editContent;
    private Button okButton;
    private Button btnDateTime;
    private TextView textDateTimeDisplay;
    private Note originalNote;
    private int originalPosition = -1;
    private Calendar selectedDateTime;
    private SimpleDateFormat displayFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        okButton = findViewById(R.id.okButton);
        btnDateTime = findViewById(R.id.btnDateTime);
        textDateTimeDisplay = findViewById(R.id.textDateTimeDisplay);

        displayFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        // Получаем Intent, с помощью которого была запущена активность
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Если передан объект Note, отображаем его данные
            originalNote = (Note) extras.getSerializable(EXTRA_NOTE);
            originalPosition = extras.getInt(EXTRA_ID, -1);

            if (originalNote != null) {
                editTitle.setText(originalNote.getTitle());
                editContent.setText(originalNote.getContent());
                selectedDateTime = Calendar.getInstance();
                selectedDateTime.setTime(originalNote.getDateTime());
                updateDateTimeDisplay();
                editTitle.setSelection(originalNote.getTitle().length()); // Устанавливаем курсор в конец
            }
        }

        // Если это новая запись, устанавливаем текущее время
        if (originalNote == null) {
            selectedDateTime = Calendar.getInstance();
            updateDateTimeDisplay();
        }

        // Изначально делаем кнопку недоступной
        okButton.setEnabled(false);

        // Устанавливаем слушатель изменений текста для обоих EditText
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Проверяем, есть ли текст в заголовке (пробелы не считаются)
                String title = editTitle.getText().toString().trim();
                boolean hasTitle = title.length() > 0;

                // Устанавливаем доступность кнопки
                okButton.setEnabled(hasTitle);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTitle.addTextChangedListener(textWatcher);
        editContent.addTextChangedListener(textWatcher);

        // Обработчик кнопки выбора даты и времени
        btnDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        // Создаем AlertDialog с DatePicker и TimePicker
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите дату и время");

        // Создаем layout для диалога
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_datetime_picker, null);
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);

        // Устанавливаем текущую дату и время в пикеры
        datePicker.init(selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH), null);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(selectedDateTime.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(selectedDateTime.get(Calendar.MINUTE));

        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Применяем выбранные значения
            applyDateTimeSelection(datePicker, timePicker);
            updateDateTimeDisplay();
        });

        builder.setNegativeButton("Отмена", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void applyDateTimeSelection(DatePicker datePicker, TimePicker timePicker) {
        // Применяем выбранные значения из DatePicker
        selectedDateTime.set(Calendar.YEAR, datePicker.getYear());
        selectedDateTime.set(Calendar.MONTH, datePicker.getMonth());
        selectedDateTime.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        // Применяем выбранные значения из TimePicker
        selectedDateTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        selectedDateTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        selectedDateTime.set(Calendar.SECOND, 0);
        selectedDateTime.set(Calendar.MILLISECOND, 0);

        // Отладочный вывод
        System.out.println("Выбранная дата/время: " + selectedDateTime.getTime().toString());
    }

    private void updateDateTimeDisplay() {
        textDateTimeDisplay.setText("Выбрано: " + displayFormat.format(selectedDateTime.getTime()));
    }

    public void onOK(View view) {
        Intent intent = getIntent(); // Получаем Intent, с помощью которого была запущена активность

        // Создаем новый объект Note или обновляем существующий
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        Date dateTime = selectedDateTime.getTime();

        Note note = new Note(title, content, dateTime);

        // Помещаем в Intent объект Note
        intent.putExtra(EXTRA_NOTE, note);

        // Если передавалась позиция, она остается в Intent
        if (originalPosition != -1) {
            intent.putExtra(EXTRA_ID, originalPosition);
        }

        // Устанавливаем результат и завершаем активность
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
