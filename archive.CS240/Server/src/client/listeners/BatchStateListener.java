package client.listeners;

import java.awt.image.BufferedImage;

import client.Cell;

import shared.comms.DownloadBatchResult;

public interface BatchStateListener {
	
	public void valueChanged(Cell cell, String newValue);
	
	public void selectedCellChanged(Cell newSelectedCell);
	
	public void updated();
	
	public void inverted();
	
	public void submited();
}
