/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.DanhMuc;
import java.util.List;
import repositories.DanhMucRepositories;

/**
 *
 * @author adm
 */
public class DanhMucServices implements IDanhMucServices {

    DanhMucRepositories repo;

    public DanhMucServices() {
        repo = new DanhMucRepositories();
    }

    @Override
    public List<DanhMuc> getALL() {
        return repo.getAll();
    }

    @Override
    public boolean add(DanhMuc d) {
        return repo.add(d);
    }

    @Override
    public boolean update(DanhMuc d) {
        return repo.update(d);
    }

    @Override
    public boolean delete(DanhMuc d) {
        return repo.delete(d);
    }

    @Override
    public DanhMuc seachbyMa(String ma) {
        return repo.seachbyMa(ma);
    }

    @Override
    public List<DanhMuc> timKiembyTrangThai(String ten) {
        return repo.timKiembyTrangThai(ten);
    }

    @Override
    public DanhMuc layMa() {
        return repo.layMa();
    }

}
