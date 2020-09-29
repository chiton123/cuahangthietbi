package model;

public class LoaiSP {
    private int Id;
    private String Tenloaisp;
    private String HinhAnhLoaiSP;

    public LoaiSP(int id, String tenloaisp, String hinhAnhLoaiSP) {
        Id = id;
        Tenloaisp = tenloaisp;
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHinhAnhLoaiSP() {
        return HinhAnhLoaiSP;
    }

    public void setHinhAnhLoaiSP(String hinhAnhLoaiSP) {
        HinhAnhLoaiSP = hinhAnhLoaiSP;
    }
}
