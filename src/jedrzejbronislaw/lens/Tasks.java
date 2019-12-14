package jedrzejbronislaw.lens;

import java.lang.reflect.InvocationTargetException;

import jedrzejbronislaw.lens.sideStripes.SideStripesTask;
import lombok.Getter;

public enum Tasks {
	SideStripes(SideStripesTask.class, "Side Stripes");
	
	private Class<? extends Task> c;
	@Getter
	private String name;

	Tasks(Class<? extends Task> c, String name) {
		this.c = c;
		this.name = name;
	}

	public Task create() {
		try {
			return c.getDeclaredConstructor().newInstance(null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
