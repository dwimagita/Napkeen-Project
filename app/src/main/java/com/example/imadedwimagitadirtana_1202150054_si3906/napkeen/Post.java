package com.example.imadedwimagitadirtana_1202150054_si3906.napkeen;

//class setter getter untuk post
public class Post {
   private String postId;
    private String username;
    private String photo;
    private String photoTitle;
    private String photoAlamat;
    private String photoDaerah;
    private String photoHarga;
    private String photoTelepon;
    private String photoInformasi;
    private String photoBuka;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public String getPhotoAlamat() {
        return photoAlamat;
    }

    public void setPhotoAlamat(String photoAlamat) {
        this.photoAlamat = photoAlamat;
    }

    public String getPhotoBuka() {
        return photoBuka;
    }

    public void setPhotoBuka(String photoBuka) {
        this.photoBuka = photoBuka;
    }

    public String getPhotoInformasi() {
        return photoInformasi;
    }

    public void setPhotoInformasi(String photoInformasi) {
        this.photoInformasi = photoInformasi;
    }

    public String getPhotoTelepon() {
        return photoTelepon;
    }

    public void setPhotoTelepon(String photoTelepon) {
        this.photoTelepon = photoTelepon;
    }

    public String getPhotoharga() {
        return photoHarga;
    }

    public void setPhotoharga(String photoharga) {
        this.photoHarga = photoharga;
    }

    public String getPhotoDaerah() {
        return photoDaerah;
    }

    public void setPhotoDaerah(String photoDaerah) {
        this.photoDaerah = photoDaerah;
    }
}