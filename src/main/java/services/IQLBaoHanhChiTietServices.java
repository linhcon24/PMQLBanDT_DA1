/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BaoHanhChiTiet;
import java.util.List;

/**
 *
 * @author HANGOCHAN
 */
public interface IQLBaoHanhChiTietServices {
    public List<BaoHanhChiTiet> getAll(String ma);
    public boolean add(BaoHanhChiTiet m);
    public boolean update(BaoHanhChiTiet m);
    public boolean delete(BaoHanhChiTiet m);
    public BaoHanhChiTiet seachbyMa(String ma);
    public void xoaBaoHanh(String ma);
}
