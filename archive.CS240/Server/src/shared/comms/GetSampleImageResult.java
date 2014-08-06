package shared.comms;

public class GetSampleImageResult {
	String imageURl = "";

	
	public GetSampleImageResult(){
		
	}
	
	public GetSampleImageResult(String url){
		imageURl = url;
	}

	/**
	 * @return the imageURl
	 */
	public String getImageURl() {
		return imageURl;
	}

	/**
	 * @param imageURl the imageURl to set
	 */
	public void setImageURl(String imageURl) {
		this.imageURl = imageURl;
	}
}
