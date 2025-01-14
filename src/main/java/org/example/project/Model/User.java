package org.example.project.Model;

public class User {
    private int id;
    private String name;
    private String xeThue ;
    private String ngayTra;
    private String ngayThue ;
    private String valid ;

    public User(int id, String name, String xeThue, String ngayThue, String ngayTra, String valid) {

    }
    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXeThue() {
        return xeThue;
    }

    public void setXeThue(String xeThue) {
        this.xeThue = xeThue;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
