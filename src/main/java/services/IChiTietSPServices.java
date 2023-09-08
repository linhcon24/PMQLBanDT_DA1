/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.ChiTietSP;
import java.util.List;
import viewmodels.ChiTietSPViewModels;

/**
 *
 * @author HANGOCHAN
 */
public interface IChiTietSPServices {
    public List<ChiTietSPViewModels> getALL();
    public List<ChiTietSP> getImei();
    public boolean add(ChiTietSP m);
    public boolean update(ChiTietSP m);
    public boolean delete(ChiTietSP m);
    public ChiTietSP seachbyMa(String ma);
    public ChiTietSP fill(String maSP);
    public List<ChiTietSP> getImeibyMaSP(String maSP);
    public ChiTietSPViewModels load(String maSP);
    public void updateImei(String ma );
    public void xoaImei(String ma);
    public List<ChiTietSP> count(String maSP);
    public void updateImeiTrangThai(String ma , Integer trangThai);
    public List<ChiTietSP> getImei(String imei);
}
