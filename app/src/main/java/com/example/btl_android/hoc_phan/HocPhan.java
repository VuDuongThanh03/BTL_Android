package com.example.btl_android.hoc_phan;

import java.io.Serializable;

/**
 * @noinspection ALL
 */
public class HocPhan implements Serializable {
    private String maHp, tenHp;
<<<<<<< Updated upstream
    private int soTc, soTietLt, soTietTh, hocKy;
=======
    private Float soTinChiLt, soTinChiTh;
    private int soTietLt, soTietTh, hocKy;
>>>>>>> Stashed changes
    private String hinhThucThi, heSo, lop;
    private Float tx1, tx2, giuaKy, cuoiKy, diemKyVong;
    private int vangLt, vangTh;

    public HocPhan() {
        this.maHp = "HP000";
        this.tenHp = "Unknown";
<<<<<<< Updated upstream
        this.soTc = 3;
=======
        this.soTinChiLt = 3.0f;
        this.soTinChiTh = 0.5f;
>>>>>>> Stashed changes
        this.soTietLt = 30;
        this.soTietTh = 30;
        this.hocKy = 1;
        this.hinhThucThi = "TL";
        this.heSo = "20-20-60";
        this.lop = "Class 0";
        this.tx1 = 0.0f;
        this.tx2 = 0.0f;
        this.giuaKy = 0.0f;
        this.cuoiKy = 0.0f;
        this.diemKyVong = 0.0f;
        this.vangLt = 0;
        this.vangTh = 0;
    }

    public HocPhan(String maHp, String tenHp, int soTietLt, int hocKy) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.soTietLt = soTietLt;
        this.hocKy = hocKy;
    }

    public HocPhan(String maHp, String tenHp, int soTietLt, int soTietTh, int hocKy, String hinhThucThi, String heSo) {
        this.maHp = maHp;
        this.tenHp = tenHp;
        this.soTietLt = soTietLt;
        this.soTietTh = soTietTh;
        this.hocKy = hocKy;
        this.hinhThucThi = hinhThucThi;
        this.heSo = heSo;
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

<<<<<<< Updated upstream
    public int getSoTc() {
        return soTc;
    }

    public void setSoTc(int soTc) {
        this.soTc = soTc;
=======
    public Float getSoTinChiLt() {
        return soTinChiLt;
    }

    public void setSoTinChiLt(Float soTinChiLt) {
        this.soTinChiLt = soTinChiLt;
    }

    public Float getSoTinChiTh() {
        return soTinChiTh;
    }

    public void setSoTinChiTh(Float soTinChiTh) {
        this.soTinChiTh = soTinChiTh;
>>>>>>> Stashed changes
    }

    public int getSoTietLt() {
        return soTietLt;
    }

    public void setSoTietLt(int soTietLt) {
        this.soTietLt = soTietLt;
    }

    public int getSoTietTh() {
        return soTietTh;
    }

    public void setSoTietTh(int soTietTh) {
        this.soTietTh = soTietTh;
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

    public Float getDiem10() {
        String[] heSoList = heSo.trim().split("-");
        if (heSoList.length == 3) {
            return tx1 * Float.parseFloat(heSoList[0]) / 100.0f +
                   tx2 * Float.parseFloat(heSoList[1]) / 100.0f + cuoiKy * Float.parseFloat(heSoList[2]) / 100.0f;
        }
        if (heSoList.length == 4) {
            return tx1 * Float.parseFloat(heSoList[0]) / 100.0f + tx2 * Float.parseFloat(heSoList[1]) / 100.0f +
                   giuaKy * Float.parseFloat(heSoList[2]) / 100.0f + cuoiKy * Float.parseFloat(heSoList[3]) / 100.0f;
        }
        return null;
    }

    public String getDiemChu() {
        Float diem10 = getDiem10();
        if (diem10 == -1) return "-";
        if (diem10 < 4.0f) return "F";
        if (diem10 < 4.7f) return "D";
        if (diem10 < 5.5f) return "D+";
        if (diem10 < 6.2f) return "C";
        if (diem10 < 7.0f) return "C+";
        if (diem10 < 7.7f) return "B";
        if (diem10 < 8.5f) return "B+";
        return "A";
    }

    public String getXepLoai() {
        Float diem10 = getDiem10();
        if (diem10 == -1) return "-";
        if (diem10 < 4.0f) return "Kém";
        if (diem10 < 6.2f) return "Yếu";
        if (diem10 < 7.0f) return "Trung bình";
        if (diem10 < 8.5f) return "Khá";
        return "Giỏi";
    }

    public String getDieuKien() {
        if (soTietLt > 0 && soTietTh == 0) {
            if (vangLt / soTietLt <= 0.3f) return "Đủ điều kiện";
            else return "Cấm thi";
        }
        if (soTietLt == 0 && soTietTh > 0) {
            if (vangTh / soTietTh <= 0.3f) return "Đủ điều kiện";
            else return "Cấm thi";
        }
        if (vangLt / soTietLt <= 0.3f && vangTh / soTietTh <= 0.3f) return "Đủ điều kiện";
        else return "Cấm thi";
    }
}