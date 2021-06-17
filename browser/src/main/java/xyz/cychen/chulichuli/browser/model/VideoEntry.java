package xyz.cychen.chulichuli.browser.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "videos")
@Data
public class VideoEntry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fileName", unique = true)
    private String fileName;
//    @Id
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "displayName")
//    private String displayName;
//
//    @Column(name = "origFileName")
//    private String origFileName;
//
//    @Column(name = "p360FileName")
//    private String p360FileName;
//
//    @Column(name = "p720FileName")
//    private String p720FileName;

    public VideoEntry(Integer id, String fileName){ //, String displayName, String origFileName, String p360FileName,String p720FileName) {
        this.id = id;
        this.fileName = fileName;
//        this.displayName = displayName;
//        this.origFileName = origFileName;
//        this.p360FileName = p360FileName;
//        this.p720FileName = p720FileName;
    }

    public VideoEntry() {

    }
}
