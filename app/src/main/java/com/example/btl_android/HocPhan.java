package com.example.btl_android;

/**
 * @noinspection ALL
 */
public class HocPhan {
    private String maHp, tenHp;
    private int soTinChiLt, soTinChiTh;
    private String hocKy, hinhThucThi, heSo, lop;
    private Float tx1, tx2, giuaKy, cuoiKy, diemKyVong;
    private int vangLt, vangTh;

    public HocPhan() {
    }

    public HocPhan(String maHp, String tenHp, int soTinChiLt, String hocKy) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.soTinChiLt = soTinChiLt;
        this.hocKy = hocKy;
    }

    public HocPhan(String maHp, String tenHp, int soTinChiLt, int soTinChiTh, String hocKy, String hinhThucThi, String heSo,
                   String lop, Float tx1, Float tx2, Float giuaKy, Float cuoiKy, Float diemKyVong, int vangLt, int vangTh) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.soTinChiLt = soTinChiLt;
        this.soTinChiTh = soTinChiTh;
        this.hocKy = hocKy;
        this.hinhThucThi = hinhThucThi;
        this.heSo = heSo;
        this.lop = lop;
        this.tx1 = tx1;
        this.tx2 = tx2;
        this.giuaKy = giuaKy;
        this.cuoiKy = cuoiKy;
        this.diemKyVong = diemKyVong;
        this.vangLt = vangLt;
        this.vangTh = vangTh;
    }

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

    public int getSoTinChiLt() {
        return soTinChiLt;
    }

    public void setSoTinChiLt(int soTinChiLt) {
        this.soTinChiLt = soTinChiLt;
    }

    public int getSoTinChiTh() {
        return soTinChiTh;
    }

    public void setSoTinChiTh(int soTinChiTh) {
        this.soTinChiTh = soTinChiTh;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
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

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public Float getTx1() {
        return tx1;
    }

    public void setTx1(Float tx1) {
        this.tx1 = tx1;
    }

    public Float getTx2() {
        return tx2;
    }

    public void setTx2(Float tx2) {
        this.tx2 = tx2;
    }

    public Float getGiuaKy() {
        return giuaKy;
    }

    public void setGiuaKy(Float giuaKy) {
        this.giuaKy = giuaKy;
    }

    public Float getCuoiKy() {
        return cuoiKy;
    }

    public void setCuoiKy(Float cuoiKy) {
        this.cuoiKy = cuoiKy;
    }

    public Float getDiemKyVong() {
        return diemKyVong;
    }

    public void setDiemKyVong(Float diemKyVong) {
        this.diemKyVong = diemKyVong;
    }

    public int getVangLt() {
        return vangLt;
    }

    public void setVangLt(int vangLt) {
        this.vangLt = vangLt;
    }

    public int getVangTh() {
        return vangTh;
    }

    public void setVangTh(int vangTh) {
        this.vangTh = vangTh;
    }
}