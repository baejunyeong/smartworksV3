package net.smartworks.server.engine.common.model;

import net.smartworks.server.engine.common.util.CommonUtil;
import net.smartworks.server.engine.common.util.XmlUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Order extends BaseObject {
	private static final long serialVersionUID = 1L;
	private static final String NAME = CommonUtil.toName(Order.class, PREFIX);
	public static final String A_FIELD = "field";
	public static final String A_ISASC = "isAsc";
	public static final String A_WHENCOLUMNNAME = "whenColumnName";
	public static final String A_WHENCOLUMNVALUE = "whenColumnValue";
	public static final String A_ORDERNUMBER = "orderNumber";

	private String field;
	private boolean isAsc = true;
	private String whenColumnName;
	private String whenColumnValue;
	private int orderNumber;

	public Order() {
		super();
	}
	public Order(String columnName, boolean isAsc) {
		super();
		this.field = columnName;
		this.isAsc = isAsc;
	}
	public Order(String whenColumnName, String whenColumnValue) {
		super();
		this.whenColumnName = whenColumnName;
		this.whenColumnValue = whenColumnValue;
	}
	public String toString(String name, String tab) {
		if (name == null || name.trim().length() == 0)
			name = NAME;
		return super.toString(name, tab);
	}
	public String toLiteString(String name, String tab) {
		if (name == null || name.trim().length() == 0)
			name = NAME;
		return super.toLiteString(name, tab);
	}
	public String toAttributesString() {
		StringBuffer buf = new StringBuffer();
		buf.append(super.toAttributesString());
		appendAttributeString(A_FIELD, field, buf);
		appendAttributeString(A_ISASC, isAsc, buf);
		appendAttributeString(A_WHENCOLUMNNAME, whenColumnName, buf);
		appendAttributeString(A_WHENCOLUMNVALUE, whenColumnValue, buf);
		appendAttributeString(A_ORDERNUMBER, orderNumber, buf);
		return buf.toString();
	}
	public static BaseObject toObject(Node node, BaseObject baseObj) throws Exception {
		if (node == null)
			return null;
		
		Order obj = null;
		if (baseObj == null || !(baseObj instanceof Order))
			obj = new Order();
		else
			obj = (Order)baseObj;
		
		BaseObject.toObject(node, obj);
		
		NamedNodeMap attrMap = node.getAttributes();
		if (attrMap != null) {
			Node columnName = attrMap.getNamedItem(A_FIELD);
			Node isAsc = attrMap.getNamedItem(A_ISASC);
			Node whenColumnName = attrMap.getNamedItem(A_WHENCOLUMNNAME);
			Node whenColumnValue = attrMap.getNamedItem(A_WHENCOLUMNVALUE);
			Node orderNumber = attrMap.getNamedItem(A_ORDERNUMBER);
			if (columnName != null)
				obj.setField(columnName.getNodeValue());
			if (isAsc != null)
				obj.setAsc(CommonUtil.toBoolean(isAsc.getNodeValue()));
			if (whenColumnName != null)
				obj.setWhenColumnName(whenColumnName.getNodeValue());
			if (whenColumnValue != null)
				obj.setWhenColumnValue(whenColumnValue.getNodeValue());
			if (orderNumber != null)
				obj.setOrderNumber(CommonUtil.toInt(orderNumber.getNodeValue()));
		}
		
		return obj;
	}
	public static BaseObject toObject(String str) throws Exception {
		if (str == null)
			return null;
		Document doc = XmlUtil.toDocument(str);
		if (doc == null)
			return null;
		return toObject(doc.getDocumentElement(), null);
	}
	public static Order[] add(Order[] objs, Order obj) {
		if (obj == null)
			return objs;
		int size = 0;
		if (objs != null)
			size = objs.length;
		Order[] newObjs = new Order[size+1];
		int i;
		for (i=0; i<size; i++)
			newObjs[i] = objs[i];
		newObjs[i] = obj;
		return newObjs;
	}
	public static Order[] remove(Order[] objs, Order obj) {
		if (obj == null)
			return objs;
		int size = 0;
		if (objs != null)
			size = objs.length;
		if (size == 0)
			return objs;
		Order[] newObjs = new Order[size-1];
		int i;
		int j = 0;
		for (i=0; i<size; i++) {
			if (objs[i].equals(obj))
				continue;
			newObjs[j++] = objs[i];
		}
		return newObjs;
	}
	public static Order[] left(Order[] objs, Order obj) {
		if (objs == null || objs.length == 0 || obj == null)
			return objs;
		int idx = -1;
		for (int i=0; i<objs.length; i++) {
			if (!objs[i].equals(obj))
				continue;
			idx = i;
			break;
		}
		if (idx < 1)
			return objs;
		Order[] newObjs = new Order[objs.length];
		for (int i=0; i<objs.length; i++) {
			if (i == idx) {
				newObjs[i] = objs[idx-1];
				continue;
			} else if (i == idx-1) {
				newObjs[i] = objs[idx];
				continue;
			}
			newObjs[i] = objs[i];
		}
		return newObjs;
	}
	public static Order[] right(Order[] objs, Order obj) {
		if (objs == null || objs.length == 0 || obj == null)
			return objs;
		int idx = -1;
		for (int i=0; i<objs.length; i++) {
			if (!objs[i].equals(obj))
				continue;
			idx = i;
			break;
		}
		if (idx == -1 || idx+1 == objs.length)
			return objs;
		Order[] newObjs = new Order[objs.length];
		for (int i=0; i<objs.length; i++) {
			if (i == idx) {
				newObjs[i] = objs[idx+1];
				continue;
			} else if (i == idx+1) {
				newObjs[i] = objs[idx];
				continue;
			}
			newObjs[i] = objs[i];
		}
		return newObjs;
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public boolean isAsc() {
		return isAsc;
	}
	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}
	public String getWhenColumnName() {
		return whenColumnName;
	}
	public void setWhenColumnName(String whenColumnName) {
		this.whenColumnName = whenColumnName;
	}
	public String getWhenColumnValue() {
		return whenColumnValue;
	}
	public void setWhenColumnValue(String whenColumnValue) {
		this.whenColumnValue = whenColumnValue;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

}