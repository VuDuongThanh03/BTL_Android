package com.example.btl_android.them_hoc_phan;

import java.io.Serializable;

public class HocPhan implements Serializable {
    private String maHp;
    private String tenHp;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;
    private int hocKy;
    private String hinhThucThi;
    private String heSo;

    public HocPhan(String maHp, String tenHp, int soTinChiLyThuyet, int soTinChiThucHanh, int hocKy, String hinhThucThi, String heSo) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.soTinChiLyThuyet = soTinChiLyThuyet;
        this.soTinChiThucHanh = soTinChiThucHanh;
        this.hocKy = hocKy;
        this.hinhThucThi = hinhThucThi;
        this.heSo = heSo;
    }

    // Getter và Setter
    public String getMaHp() {
        return maHp;
    }

    public void setMaHp(String maHp) {
        this.maHp = maHp;
    }

    public String getTenHp() {
        return tenHp;
    }

    public void setTenHp(String tenHp) {
        this.tenHp = tenHp;
    }

    public int getSoTinChiLyThuyet() {
        return soTinChiLyThuyet;
    }

    public void setSoTinChiLyThuyet(int soTinChiLyThuyet) {
        this.soTinChiLyThuyet = soTinChiLyThuyet;
    }

    public int getSoTinChiThucHanh() {
        return soTinChiThucHanh;
    }

    public void setSoTinChiThucHanh(int soTinChiThucHanh) {
        this.soTinChiThucHanh = soTinChiThucHanh;
    }

    public int getHocKy() {
        return hocKy;
    }

    public void setHocKy(int hocKy) {
        this.hocKy = hocKy;
    }

    public String getHinhThucThi() {
        return hinhThucThi;
    }

    public void setHinhThucThi(String hinhThucThi) {
        this.hinhThucThi = hinhThucThi;
    }

    public String getHeSo() {
        return heSo;
    }

    public void setHeSo(String heSo) {
        this.heSo = heSo;
    }
}
