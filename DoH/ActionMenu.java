

public class ActionMenu {
	public enum Option {
		Attack,
		Wait
	}
	
	public enum State {
		Primary,
		SelectTarget
	}
	
	Menu<Option> menu;

	public ActionMenu(Unit[] ca) {
		if (ca.length > 0) {
			menu = new Menu<Option>(new Option[] {Option.Attack, Option.Wait});
		} else {
			menu = new Menu<Option>(new Option[] {Option.Wait});
		}
	}

}
