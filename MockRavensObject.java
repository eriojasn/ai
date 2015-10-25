package ravensproject;

import java.util.HashMap;

public class MockRavensObject extends RavensObject {
    public HashMap<String,String> attributes;

	public MockRavensObject(String name) {
		super(name);
        attributes = new HashMap<>();
        attributes.put("shape", "none");
	}

    public HashMap<String,String> getAttributes() {
        return this.attributes;
    }
}
