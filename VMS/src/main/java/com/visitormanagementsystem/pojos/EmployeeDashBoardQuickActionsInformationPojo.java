package com.visitormanagementsystem.pojos;

/**
 * Created by Phani.Gullapalli on 08/12/2015.
 */
public class EmployeeDashBoardQuickActionsInformationPojo {

    private String $id;
    private String ActionCode;
    private String ActionName;
    private Integer ActionCount;

    public EmployeeDashBoardQuickActionsInformationPojo(String $id, String actionCode, String actionName, Integer actionCount) {
        this.$id = $id;
        ActionCode = actionCode;
        ActionName = actionName;
        ActionCount = actionCount;
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
     * @return The ActionCode
     */
    public String getActionCode() {
        return ActionCode;
    }

    /**
     * @param ActionCode The ActionCode
     */
    public void setActionCode(String ActionCode) {
        this.ActionCode = ActionCode;
    }

    /**
     * @return The ActionName
     */
    public String getActionName() {
        return ActionName;
    }

    /**
     * @param ActionName The ActionName
     */
    public void setActionName(String ActionName) {
        this.ActionName = ActionName;
    }

    /**
     * @return The ActionCount
     */
    public Integer getActionCount() {
        return ActionCount;
    }

    /**
     * @param ActionCount The ActionCount
     */
    public void setActionCount(Integer ActionCount) {
        this.ActionCount = ActionCount;
    }


}
