package com.application.ksgu.Model;

import java.util.List;

public class DataKirim {

    //Data Pemohon
    private String idkantor;
    private String jenis_id;
    private String surat_no;
    private String surat_hal;
    private String surat_pengirim;
    private String surat_pengirim_kota;
    private String surat_npwp;
    private String surat_tgl;
    private String require_check;
    private Layanan layanan;
    private List<DataNota> dataNotas;
    private List<DataNota> check;
    private List<Checklist> checklists;
    private List<DataNota> dataCheck;

    //kapal atau rambu
    private String kapal_id;
    private String kapal_name;
    private String kapal_gt;
    private String kapal_cs;
    private String kapal_bendera;
    private String kapal_pemilik;
    private String kapal_posisi;

    //perusahaan
    private String namaprsh;
    private String alamatprsh;
    private String identitasprsh;
    private String jmlkapal;
    private String totalgt;

    //pelaut
    private String idpelaut;
    private String namapelaut;
    private String kodepelaut;
    private String tempatlahir;
    private String tgllahir;
    private String umur;
    private String jk;
    private String statuspelaut;
    private String sertifikat;
    private String fotopelaut;

    //List
    private List<FileBerkas> files;

    public List<FileBerkas> getFiles() {
        return files;
    }

    public void setFiles(List<FileBerkas> files) {
        this.files = files;
    }

    public List<DataNota> getDataCheck() {
        return dataCheck;
    }

    public void setDataCheck(List<DataNota> dataCheck) {
        this.dataCheck = dataCheck;
    }

    public String getIdkantor() {
        return idkantor;
    }

    public void setIdkantor(String idkantor) {
        this.idkantor = idkantor;
    }

    public String getJenis_id() {
        return jenis_id;
    }

    public void setJenis_id(String jenis_id) {
        this.jenis_id = jenis_id;
    }

    public String getSurat_no() {
        return surat_no;
    }

    public void setSurat_no(String surat_no) {
        this.surat_no = surat_no;
    }

    public String getSurat_hal() {
        return surat_hal;
    }

    public void setSurat_hal(String surat_hal) {
        this.surat_hal = surat_hal;
    }

    public String getSurat_pengirim() {
        return surat_pengirim;
    }

    public void setSurat_pengirim(String surat_pengirim) {
        this.surat_pengirim = surat_pengirim;
    }

    public String getSurat_pengirim_kota() {
        return surat_pengirim_kota;
    }

    public void setSurat_pengirim_kota(String surat_pengirim_kota) {
        this.surat_pengirim_kota = surat_pengirim_kota;
    }

    public String getSurat_npwp() {
        return surat_npwp;
    }

    public void setSurat_npwp(String surat_npwp) {
        this.surat_npwp = surat_npwp;
    }

    public String getSurat_tgl() {
        return surat_tgl;
    }

    public void setSurat_tgl(String surat_tgl) {
        this.surat_tgl = surat_tgl;
    }

    public String getRequire_check() {
        return require_check;
    }

    public void setRequire_check(String require_check) {
        this.require_check = require_check;
    }

    public Layanan getLayanan() {
        return layanan;
    }

    public void setLayanan(Layanan layanan) {
        this.layanan = layanan;
    }

    public List<DataNota> getDataNotas() {
        return dataNotas;
    }

    public void setDataNotas(List<DataNota> dataNotas) {
        this.dataNotas = dataNotas;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public String getKapal_id() {
        return kapal_id;
    }

    public void setKapal_id(String kapal_id) {
        this.kapal_id = kapal_id;
    }

    public String getKapal_name() {
        return kapal_name;
    }

    public void setKapal_name(String kapal_name) {
        this.kapal_name = kapal_name;
    }

    public String getKapal_gt() {
        return kapal_gt;
    }

    public void setKapal_gt(String kapal_gt) {
        this.kapal_gt = kapal_gt;
    }

    public String getKapal_cs() {
        return kapal_cs;
    }

    public void setKapal_cs(String kapal_cs) {
        this.kapal_cs = kapal_cs;
    }

    public String getKapal_bendera() {
        return kapal_bendera;
    }

    public void setKapal_bendera(String kapal_bendera) {
        this.kapal_bendera = kapal_bendera;
    }

    public String getKapal_pemilik() {
        return kapal_pemilik;
    }

    public void setKapal_pemilik(String kapal_pemilik) {
        this.kapal_pemilik = kapal_pemilik;
    }

    public String getKapal_posisi() {
        return kapal_posisi;
    }

    public void setKapal_posisi(String kapal_posisi) {
        this.kapal_posisi = kapal_posisi;
    }

    public String getNamaprsh() {
        return namaprsh;
    }

    public void setNamaprsh(String namaprsh) {
        this.namaprsh = namaprsh;
    }

    public String getAlamatprsh() {
        return alamatprsh;
    }

    public void setAlamatprsh(String alamatprsh) {
        this.alamatprsh = alamatprsh;
    }

    public String getIdentitasprsh() {
        return identitasprsh;
    }

    public void setIdentitasprsh(String identitasprsh) {
        this.identitasprsh = identitasprsh;
    }

    public String getJmlkapal() {
        return jmlkapal;
    }

    public void setJmlkapal(String jmlkapal) {
        this.jmlkapal = jmlkapal;
    }

    public String getTotalgt() {
        return totalgt;
    }

    public void setTotalgt(String totalgt) {
        this.totalgt = totalgt;
    }

    public String getIdpelaut() {
        return idpelaut;
    }

    public void setIdpelaut(String idpelaut) {
        this.idpelaut = idpelaut;
    }

    public String getNamapelaut() {
        return namapelaut;
    }

    public void setNamapelaut(String namapelaut) {
        this.namapelaut = namapelaut;
    }

    public String getKodepelaut() {
        return kodepelaut;
    }

    public void setKodepelaut(String kodepelaut) {
        this.kodepelaut = kodepelaut;
    }

    public String getTempatlahir() {
        return tempatlahir;
    }

    public void setTempatlahir(String tempatlahir) {
        this.tempatlahir = tempatlahir;
    }

    public String getTgllahir() {
        return tgllahir;
    }

    public void setTgllahir(String tgllahir) {
        this.tgllahir = tgllahir;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getStatuspelaut() {
        return statuspelaut;
    }

    public void setStatuspelaut(String statuspelaut) {
        this.statuspelaut = statuspelaut;
    }

    public String getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(String sertifikat) {
        this.sertifikat = sertifikat;
    }

    public String getFotopelaut() {
        return fotopelaut;
    }

    public void setFotopelaut(String fotopelaut) {
        this.fotopelaut = fotopelaut;
    }

    public List<DataNota> getCheck() {
        return check;
    }

    public void setCheck(List<DataNota> check) {
        this.check = check;
    }
}
