package textures;

public class ModelTexture {
	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparenty = false;
	private boolean useFakeLighting = false;
	
	private int numberOfRows = 1;
	
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public float getShineDamper() {
		return shineDamper;
	}
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
	public float getReflectivity() {
		return reflectivity;
	}
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	public ModelTexture(int id){
		this.textureID = id;
	}
	public int getTextureID(){
		return this.textureID;
	}
	public boolean isHasTransparenty() {
		return hasTransparenty;
	}
	public void setHasTransparenty(boolean hasTransparenty) {
		this.hasTransparenty = hasTransparenty;
	}
	public boolean isUseFakeLighting(){
		return useFakeLighting;
	}
	public void setUseFakeLighting(boolean useFakeLighting){
		this.useFakeLighting = useFakeLighting;
	}
	
}
