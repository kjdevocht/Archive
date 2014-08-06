package shared.comms;

public class DownloadFileResult {
	byte[] byteFile= null;
	
	public DownloadFileResult(byte[] byteFile0){
		byteFile = byteFile0;
	}

	/**
	 * @return the byteFile
	 */
	public byte[] getByteFile() {
		return byteFile;
	}

	/**
	 * @param byteFile the byteFile to set
	 */
	public void setByteFile(byte[] byteFile) {
		this.byteFile = byteFile;
	}
}
