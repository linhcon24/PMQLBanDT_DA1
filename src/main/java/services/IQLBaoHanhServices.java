/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.BaoHanh;
import java.util.List;

/**
 *
 * @author HANGOCHAN
 */
public interface IQLBaoHanhServices {
    public List<BaoHanh> getALL();
    public boolean add(BaoHanh m);
    public boolean update(BaoHanh m);
    public boolean delete(BaoHanh m);
    public BaoHanh seachbyMa(String ma);
    public BaoHanh layMa();
}
