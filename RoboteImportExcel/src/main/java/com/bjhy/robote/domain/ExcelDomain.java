package com.bjhy.robote.domain;
/**
 * excel上传实体类
 * @author chenhao
 *
 * 2018年5月16日
 */
public class ExcelDomain {
	
		private String id;
		//属性名称
		private String name;
		//代码名称
		private String fieldName;
		//数据类型
		private String dataType;
		//表单类型
		private String inputType;
		//表单值
		private String inputValue;
		//序号
		private String pxNum;
		//表名id --实际为domain.id
		private String domainId;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getInputType() {
			return inputType;
		}
		public void setInputType(String inputType) {
			this.inputType = inputType;
		}
		public String getInputValue() {
			return inputValue;
		}
		public void setInputValue(String inputValue) {
			this.inputValue = inputValue;
		}
		public String getPxNum() {
			return pxNum;
		}
		public void setPxNum(String pxNum) {
			this.pxNum = pxNum;
		}
		public String getDomainId() {
			return domainId;
		}
		public void setDomainId(String domainId) {
			this.domainId = domainId;
		}
		
		
}
