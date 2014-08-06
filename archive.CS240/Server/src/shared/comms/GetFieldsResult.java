package shared.comms;

import java.util.ArrayList;
import shared.model.Field;

public class GetFieldsResult {
	ArrayList<Field> fields = new ArrayList<Field>();

	public GetFieldsResult(ArrayList<Field> fields0) {
		fields = fields0;
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
}
