/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

import java.math.BigDecimal;

/**
 *
 * @author HANGOCHAN
 */
public class ThongKeDoanhThu {

    private String maSP;
    private String tenSP;
    private BigDecimal doanhThu;
    private BigDecimal giamGia;

    public ThongKeDoanhThu() {
    }

    public ThongKeDoanhThu(String maSP, String tenSP, BigDecimal doanhThu, BigDecimal giamGia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.doanhThu = doanhThu;
        this.giamGia = giamGia;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

}
