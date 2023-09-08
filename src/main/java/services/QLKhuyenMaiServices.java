/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.KhuyenMai;
import java.util.List;
import repositories.QLKhuyenMaiRepositories;

/**
 *
 * @author Admin
 */
public class QLKhuyenMaiServices implements IQLKhuyenMaiServices{
    QLKhuyenMaiRepositories repo;
    
    public QLKhuyenMaiServices(){
        repo = new QLKhuyenMaiRepositories();
    }

    @Override
    public List<KhuyenMai> getAll() {
        return repo.getAll();
        
    }

    @Override
    public boolean add(KhuyenMai khuyenMai) {
        return repo.add(khuyenMai);
    }

    @Override
    public boolean update(KhuyenMai khuyenMai) {
        return repo.update(khuyenMai);
    }

    @Override
    public boolean delete(KhuyenMai khuyenMai) {
        return repo.delete(khuyenMai);
    }

    @Override
    public KhuyenMai seachbyMa(String ma) {
        return repo.seachbyMa(ma);
    }

    @Override
    public KhuyenMai layMa() {
        return  repo.layMa();
    }

    @Override
    public List<KhuyenMai> getAllByMa(String ma) {
        return repo.getAllByMa(ma);
    }

    @Override
    public void updatebyTrangThai(String maString, Integer trangThai) {
        repo.updatebyTrangThai(maString, trangThai);
    }
    
    
}
