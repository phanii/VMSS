package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 11/12/2015.
 */
public class Spinner_ValuesBean {


    private String $id;
    private Integer Id;
    private String TypeMasterCode;
    private String TypeDetailsCode;
    private String TypeDetailValueLang1;
    private String TypeDetailValueLang2;
    private String CreatedBy;
    private String CreatedDate;
    private String ModifyDate;
    private String ModifyBy;
    private String IsDeleted;

    public Spinner_ValuesBean(String typeDetailsCode, String typeDetailValueLang1, String typeDetailValueLang2) {
        TypeDetailsCode = typeDetailsCode;
        TypeDetailValueLang1 = typeDetailValueLang1;
        TypeDetailValueLang2 = typeDetailValueLang2;
    }

    /**
     * @return The $id
     */
    public String get$id() {
        return $id;
    }

    /**
     * @param $id The $id
     */
    public void set$id(String $id) {
        this.$id = $id;
    }

    /**
     * @return The Id
     */
    public Integer getId() {
        return Id;
    }

    /**
     * @param Id The Id
     */
    public void setId(Integer Id) {
        this.Id = Id;
    }

    /**
     * @return The TypeMasterCode
     */
    public String getTypeMasterCode() {
        return TypeMasterCode;
    }

    /**
     * @param TypeMasterCode The TypeMasterCode
     */
    public void setTypeMasterCode(String TypeMasterCode) {
        this.TypeMasterCode = TypeMasterCode;
    }

    /**
     * @return The TypeDetailsCode
     */
    public String getTypeDetailsCode() {
        return TypeDetailsCode;
    }

    /**
     * @param TypeDetailsCode The TypeDetailsCode
     */
    public void setTypeDetailsCode(String TypeDetailsCode) {
        this.TypeDetailsCode = TypeDetailsCode;
    }

    /**
     * @return The TypeDetailValueLang1
     */
    public String getTypeDetailValueLang1() {
        return TypeDetailValueLang1;
    }

    /**
     * @param TypeDetailValueLang1 The TypeDetailValueLang1
     */
    public void setTypeDetailValueLang1(String TypeDetailValueLang1) {
        this.TypeDetailValueLang1 = TypeDetailValueLang1;
    }

    /**
     * @return The TypeDetailValueLang2
     */
    public String getTypeDetailValueLang2() {
        return TypeDetailValueLang2;
    }

    /**
     * @param TypeDetailValueLang2 The TypeDetailValueLang2
     */
    public void setTypeDetailValueLang2(String TypeDetailValueLang2) {
        this.TypeDetailValueLang2 = TypeDetailValueLang2;
    }

    /**
     * @return The CreatedBy
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * @param CreatedBy The CreatedBy
     */
    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    /**
     * @return The CreatedDate
     */
    public String getCreatedDate() {
        return CreatedDate;
    }

    /**
     * @param CreatedDate The CreatedDate
     */
    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    /**
     * @return The ModifyDate
     */
    public String getModifyDate() {
        return ModifyDate;
    }

    /**
     * @param ModifyDate The ModifyDate
     */
    public void setModifyDate(String ModifyDate) {
        this.ModifyDate = ModifyDate;
    }

    /**
     * @return The ModifyBy
     */
    public String getModifyBy() {
        return ModifyBy;
    }

    /**
     * @param ModifyBy The ModifyBy
     */
    public void setModifyBy(String ModifyBy) {
        this.ModifyBy = ModifyBy;
    }

    /**
     * @return The IsDeleted
     */
    public String getIsDeleted() {
        return IsDeleted;
    }

    /**
     * @param IsDeleted The IsDeleted
     */
    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }


}
