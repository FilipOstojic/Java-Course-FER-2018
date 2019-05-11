package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {
	StudentDatabase db; 
	
	@Before
	public void initialize() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 db = new StudentDatabase(lines);
	}
	
	@Test
	public void testForJMBAG() {
		assertEquals(db.forJMBAG("0000000010"), new StudentRecord("0000000010", "Dokleja", "Luka", "3"));
	}
	
	@Test
	public void testForJMBAG2() {
		assertEquals(db.forJMBAG("0000000020"), new StudentRecord("0000000020", "Hibner", "Sonja", "5"));
	}

	@Test
	public void testFilterTrueAll() {		
		assertEquals(63, db.filter((record)->{ return true; 	}).size());
	}
	
	@Test
	public void testFilterFalseAll() {		
		assertEquals(0, db.filter((record)->{ return false; 	}).size());
	}
}
