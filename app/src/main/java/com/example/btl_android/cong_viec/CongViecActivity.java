package com.example.btl_android.cong_viec;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @noinspection ALL
 */
public class CongViecActivity extends AppCompatActivity {

    ListView lvcongviec;
    ArrayList<CongViec> congViecArrayList = new ArrayList<>();
    CongViecAdapter cvAdapter;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_viec);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        congViecArrayList = dbHelper.getAllCongViec();
        sortCongViecList(congViecArrayList);
        showlvCongViec();
    }

    void showlvCongViec() {
        Context x = this;
        lvcongviec = findViewById(R.id.lvcongviec);

        cvAdapter = new CongViecAdapter(x, R.layout.customlv_cong_viec, congViecArrayList);
        lvcongviec.setAdapter(cvAdapter);
        registerForContextMenu(lvcongviec);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_congviec, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.congviec_edit:
                // Xử lý sự kiện chỉnh sửa
                Toast.makeText(this, "Chỉnh sửa " + congViecArrayList.get(selectedItemPosition).tenCongViec, Toast.LENGTH_SHORT).show();
                showAddEditDialog(congViecArrayList.get(selectedItemPosition),selectedItemPosition);
                return true;
            case R.id.congviec_delete:
                // Xử lý sự kiện xóa
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc chắn muốn xóa công việc này không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(CongViecActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void setSelectedItemPosition(int position){
        selectedItemPosition = position;
    }

    public void sortCongViecList(ArrayList<CongViec> congViecList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        ArrayList<CongViec> trangThai1List = new ArrayList<>();
        ArrayList<CongViec> trangThai0List = new ArrayList<>();

        for (CongViec cv : congViecList) {
            if (cv.trangThai == 1) {
                trangThai1List.add(cv);
            } else {
                trangThai0List.add(cv);
            }
        }
        for (int i = 0; i < trangThai0List.size() - 1; i++) {
            for (int j = i + 1; j < trangThai0List.size(); j++) {
                CongViec cv1 = trangThai0List.get(i);
                CongViec cv2 = trangThai0List.get(j);
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(cv1.thoiHanNgay + " " + cv1.thoiHanGio);
                    date2 = dateFormat.parse(cv2.thoiHanNgay + " " + cv2.thoiHanGio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1 != null && date2 != null) {
                    if (date1.after(date2) || (date1.equals(date2) && Integer.parseInt(cv1.mucUuTien) < Integer.parseInt(cv2.mucUuTien))) {
                        trangThai0List.set(i, cv2);
                        trangThai0List.set(j, cv1);
                    }
                }
            }
        }

        congViecList.clear();
        congViecList.addAll(trangThai0List);
        congViecList.addAll(trangThai1List);
    }
    private void showAddEditDialog(final CongViec congViec, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diaglog_congviec, null);
        builder.setView(dialogView);

        final EditText etTenCongViec = dialogView.findViewById(R.id.edt_tenviec);
        final EditText etChiTietCongViec = dialogView.findViewById(R.id.edt_chitiet);
//        final EditText etMucUuTien = dialogView.findViewById(R.id.et_mucuutien);
        final EditText etThoiHanGio = dialogView.findViewById(R.id.edt_hangio);
        final EditText etThoiHanNgay = dialogView.findViewById(R.id.edt_hanngay);

        if (congViec != null) {
            etTenCongViec.setText(congViec.getTenCongViec());
            etChiTietCongViec.setText(congViec.getChiTietCongViec());
//            etMucUuTien.setText(congViec.getMucUuTien());
            etThoiHanGio.setText(congViec.getThoiHanGio());
            etThoiHanNgay.setText(congViec.getThoiHanNgay());
        }

        builder.setPositiveButton(congViec == null ? "Thêm" : "Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenCongViec = etTenCongViec.getText().toString();
                String chiTietCongViec = etChiTietCongViec.getText().toString();
//                String mucUuTien = etMucUuTien.getText().toString();
                String thoiHanGio = etThoiHanGio.getText().toString();
                String thoiHanNgay = etThoiHanNgay.getText().toString();

                if (congViec == null) {
                    // Thêm mới công việc
//                    items.add(new CongViec(tenCongViec, chiTietCongViec, mucUuTien, thoiHanGio, thoiHanNgay, 0));

                } else {
                    // Chỉnh sửa công việc
//                    congViec.setTenCongViec(tenCongViec);
//                    congViec.setChiTietCongViec(chiTietCongViec);
//                    congViec.setMucUuTien(mucUuTien);
//                    congViec.setThoiHanGio(thoiHanGio);
//                    congViec.setThoiHanNgay(thoiHanNgay);
//                    items.set(position, congViec);
                }
                cvAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}