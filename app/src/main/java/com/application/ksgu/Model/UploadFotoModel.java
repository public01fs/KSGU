package com.application.ksgu.Model;

import android.net.Uri;

import java.util.List;

public class UploadFotoModel {
    public UploadFotoModel() {
    }
    public UploadFotoModel(String title, FileBerkas fileBerkas, ChecklistKirim checklistKirim) {
        this.title = title;
        this.fileBerkas = fileBerkas;
        this.checklistKirim = checklistKirim;
    }

    public UploadFotoModel(String title, List<Uri> mUri) {
        this.title = title;
        this.mUri = mUri;
    }

    public UploadFotoModel(String title, List<Uri> mUri, String uri) {
        this.title = title;
        this.mUri = mUri;
        this.uri = uri;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String title;
    private String uri;
    private List<Uri> mUri;
    private FileBerkas fileBerkas;
    private ChecklistKirim checklistKirim;

    public ChecklistKirim getChecklistKirim() {
        return checklistKirim;
    }

    public void setChecklistKirim(ChecklistKirim checklistKirim) {
        this.checklistKirim = checklistKirim;
    }

    public FileBerkas getFileBerkas() {
        return fileBerkas;
    }

    public void setFileBerkas(FileBerkas fileBerkas) {
        this.fileBerkas = fileBerkas;
    }

    public List<Uri> getmUri() {
        return mUri;
    }

    public void setmUri(List<Uri> mUri) {
        this.mUri = mUri;
    }
}
