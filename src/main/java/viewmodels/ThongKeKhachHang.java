/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

/**
 *
 * @author HANGOCHAN
 */
public class ThongKeKhachHang {
    private String maKH ;
    private String tenKH ;
    private Long soLanMua ;

    public ThongKeKhachHang() {
    }

    public ThongKeKhachHang(String maKH, String tenKH, Long soLanMua) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soLanMua = soLanMua;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Long getSoLanMua() {
        return soLanMua;
    }

    public void setSoLanMua(Long soLanMua) {
        this.soLanMua = soLanMua;
    }
    
    
}
