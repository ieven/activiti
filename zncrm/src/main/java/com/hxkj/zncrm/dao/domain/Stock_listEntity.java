package com.hxkj.zncrm.dao.domain;

public class Stock_listEntity {
	
	private String id;
    private String productName;
    private String model;
    private String unit;
    private String storeNumber;
    private String purchasingPrice;
    private String owner;
    private String taskTime;
    private String remark;
    private String status;

    private String create_time;
    private String last_modify;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getPurchasingPrice() {
		return purchasingPrice;
	}
	public void setPurchasingPrice(String purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getLast_modify() {
		return last_modify;
	}
	public void setLast_modify(String last_modify) {
		this.last_modify = last_modify;
	}

}
