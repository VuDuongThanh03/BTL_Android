package com.example.btl_android;

/** @noinspection ALL*/
public class CongViec {
    String tenCongViec;
    String chiTietCongViec;
    String mucUuTien;
    String thoiHanGio;
    String thoiHanNgay;
    int trangThai;

    public CongViec(final String tenCongViec, final String chiTietCongViec, final String mucUuTien, final String thoiHanGio, final String thoiHanNgay, final int trangThai) {
        this.tenCongViec = tenCongViec;
        this.chiTietCongViec = chiTietCongViec;
        this.mucUuTien = mucUuTien;
        this.thoiHanGio = thoiHanGio;
        this.thoiHanNgay = thoiHanNgay;
        this.trangThai = trangThai;
    }

    public String getTenCongViec() {
        return this.tenCongViec;
    }

    public void setTenCongViec(final String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public String getChiTietCongViec() {
        return this.chiTietCongViec;
    }

    public void setChiTietCongViec(final String chiTietCongViec) {
        this.chiTietCongViec = chiTietCongViec;
    }

    public String getMucUuTien() {
        return this.mucUuTien;
    }

    public void setMucUuTien(final String mucUuTien) {
        this.mucUuTien = mucUuTien;
    }

    public String getThoiHanGio() {
        return this.thoiHanGio;
    }

    public void setThoiHanGio(final String thoiHanGio) {
        this.thoiHanGio = thoiHanGio;
    }

    public String getThoiHanNgay() {
        return this.thoiHanNgay;
    }

    public void setThoiHanNgay(final String thoiHanNgay) {
        this.thoiHanNgay = thoiHanNgay;
    }

    public int getTrangThai() {
        return this.trangThai;
    }

    public void setTrangThai(final int trangThai) {
        this.trangThai = trangThai;
    }

}