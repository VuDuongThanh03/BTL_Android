package com.example.btl_android;

public class CongViec {
    public CongViec(String tenCongViec, String chiTietCongViec, String mucUuTien, String thoiHanGio, String thoiHanNgay, int trangThai) {
        this.tenCongViec = tenCongViec;
        this.chiTietCongViec = chiTietCongViec;
        this.mucUuTien = mucUuTien;
        this.thoiHanGio = thoiHanGio;
        this.thoiHanNgay = thoiHanNgay;
        this.trangThai = trangThai;
    }

    String tenCongViec;

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public String getChiTietCongViec() {
        return chiTietCongViec;
    }

    public void setChiTietCongViec(String chiTietCongViec) {
        this.chiTietCongViec = chiTietCongViec;
    }

    public String getMucUuTien() {
        return mucUuTien;
    }

    public void setMucUuTien(String mucUuTien) {
        this.mucUuTien = mucUuTien;
    }

    public String getThoiHanGio() {
        return thoiHanGio;
    }

    public void setThoiHanGio(String thoiHanGio) {
        this.thoiHanGio = thoiHanGio;
    }

    public String getThoiHanNgay() {
        return thoiHanNgay;
    }

    public void setThoiHanNgay(String thoiHanNgay) {
        this.thoiHanNgay = thoiHanNgay;
    }
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    String chiTietCongViec;
    String mucUuTien;
    String thoiHanGio;
    String thoiHanNgay;
    int trangThai;

}
