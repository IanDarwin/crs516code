package com.darwinsys.crs516;

import java.lang.reflect.*;
import java.util.*;

/**
 * Write a function void display(Object) that will take any object and
 * print its class name, and all of its annotations, constructors, methods, and fields.
 * Check out the difference between getMethods() and getDeclaredMethods()
 * (and the same for fields, etc).
 */
public class Ex43BonusSolution {

	record DemoRecord(int id) {}

	enum DemoEnum { CHOCOLATE, VANILLA }

	public static void main(String[] args) {
		List.of("Hello", 123, 123.45D, DemoEnum.VANILLA, new DemoRecord(2030)).forEach(Ex43BonusSolution::display);
	}

	static void display(Object o) {
		System.out.println("*** Displaying " + o + "***");
		Class<?> c = o.getClass();
		System.out.println(c);
		System.out.println("Constructors:");
		for (Constructor con : c.getDeclaredConstructors()) {
			System.out.println(con);
		}
		System.out.println("Fields:");
		for (Field f : c.getDeclaredFields()) {
			System.out.println(f);
		}
		System.out.println("Methods:");
		for (Method m : c.getDeclaredMethods()) {
			System.out.println(m);
		}
	}
}
