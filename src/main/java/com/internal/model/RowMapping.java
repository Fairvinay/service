package com.internal.model;

import java.util.List;
import java.util.Map;

public class RowMapping {
	private Map<String, List<CellMapping>> rowCells;

	public Map<String, List<CellMapping>> getRowCells() {
		return rowCells;
	}

	public void setRowCells(Map<String, List<CellMapping>> rowCells) {
		this.rowCells = rowCells;
	}
	
	
}
