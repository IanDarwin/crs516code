import java.lang.reflect.*;
import java.util.*;

/**
 * Write a function void display(Object) that will take any object and
 * print its class name, and all of its annotations, constructors, methods, and fields.
 * Check out the difference between getMethods() and getDeclaredMethods()
 * (and the same for fields, etc).
 */
public class Display {

	record DemoRecord(int id) {}

	enum DemoEnum { CHOCOLATE, VANILLA }

	public static void main(String[] args) {
		List.of("Hello", 123, 123.45D, DemoEnum.VANILLA, new DemoRecord(2030)).forEach(Display::display);
	}

	static void display(Object o) {
		System.out.println("Displaying " + o);
		// TODO replace this with your solution:
		System.out.println("Not written yet!!");
	}
}
