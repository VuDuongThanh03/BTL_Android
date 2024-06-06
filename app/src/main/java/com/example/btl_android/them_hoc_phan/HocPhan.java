package com.example.btl_android.them_hoc_phan;

/** @noinspection ALL*/
public class HocPhan {
    private String maHp;
    private String tenHp;
    private int soTinChiLyThuyet;
    private int soTinChiThucHanh;
    private int hocKy;
    private String hinhThucThi;
    private String heSo;

    public HocPhan() {
    }

    public HocPhan(final String tenHp, final String maHp, final int soTinChiLyThuyet, final int soTinChiThucHanh, final int hocKy, final String hinhThucThi, final String heSo) {
        this.tenHp = tenHp;
        this.maHp = maHp;
        this.soTinChiLyThuyet = soTinChiLyThuyet;
        this.soTinChiThucHanh = soTinChiThucHanh;
        this.hocKy = hocKy;
        this.hinhThucThi = hinhThucThi;
        this.heSo = heSo;
    }

    public String getMaHp() {
        return this.maHp;
    }

    public void setMaHp(final String maHp) {
        this.maHp = maHp;
    }

    public String getTenHp() {
        return this.tenHp;
    }

    public void setTenHp(final String tenHp) {
        this.tenHp = tenHp;
    }

    public int getSoTinChiLyThuyet() {
        return this.soTinChiLyThuyet;
    }

    public void setSoTinChiLyThuyet(final int soTinChiLyThuyet) {
        this.soTinChiLyThuyet = soTinChiLyThuyet;
    }

    public int getSoTinChiThucHanh() {
        return this.soTinChiThucHanh;
    }

    public void setSoTinChiThucHanh(final int soTinChiThucHanh) {
        this.soTinChiThucHanh = soTinChiThucHanh;
    }

    public int getHocKy() {
        return this.hocKy;
    }

    public void setHocKy(final int hocKy) {
        this.hocKy = hocKy;
    }

    public String getHinhThucThi() {
        return this.hinhThucThi;
    }

    public void setHinhThucThi(final String hinhThucThi) {
        this.hinhThucThi = hinhThucThi;
    }

    public String getHeSo() {
        return this.heSo;
    }

    public void setHeSo(final String heSo) {
        this.heSo = heSo;
    }
}