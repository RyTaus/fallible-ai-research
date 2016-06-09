import java.util.ArrayList;
import java.util.Arrays;


public class Menu<T> {
	
	public ArrayList<T> options;
	public int selected; 
	
	public Menu() {
		selected = 0;
	}
	
	public Menu(T[] obj) {
		options = new ArrayList<T>(Arrays.asList(obj));
		selected = 0;
	}
	
	public T optionAt(int i) {
		return options.get(i);
	}
	
	public void addOption(T t) {
		options.add(t);
	}
	
	public void removeOption(int index) {
		options.remove(index);
	}
	
	public int size() {
		return options.size();
	}

	public void option(Direction dir) {
		System.out.println(selected + "/" + size());
		selected = ((selected + dir.toCoord().y) % (size()) + size()) % size();
	}
	
	public T select() {
		return options.get(selected);
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < size(); i ++) {
			if (i == selected) {
				s = s + "-";
			}
			s = s + options.get(i).toString() + "\n";
		}
		return s;
	}
	

}