package ravensproject;

import java.util.HashMap;

public class MockRavensObject extends RavensObject {
    public HashMap<String,String> attributes;

	public MockRavensObject(String name) {
		super(name);
	}
	
    public HashMap<String,String> getAttributes() {
        return this.attributes;
    }
}
