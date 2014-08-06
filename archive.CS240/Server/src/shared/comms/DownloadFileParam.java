package shared.comms;

public class DownloadFileParam {
	
	String url = "";
	
	public DownloadFileParam(){
		
	}
	
	public DownloadFileParam(String url0){
		url = url0;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
