/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.MauSac;
import java.util.List;

/**
 *
 * @author trungbui
 */
public interface IQLMauSacServices {
    public List<MauSac> getALL();
    public boolean add(MauSac m);
    public boolean update(MauSac m);
    public boolean delete(MauSac m);
    public MauSac seachbyMa(String ma);
    public MauSac layMa();
    public List<MauSac> timKiembyTrangThai(String ten);
}
