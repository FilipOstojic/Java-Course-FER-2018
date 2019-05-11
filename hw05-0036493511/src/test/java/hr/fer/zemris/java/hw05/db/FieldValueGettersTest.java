package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertTrue;
import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;

import org.junit.Test;

public class FieldValueGettersTest {

	@Test
	public void test() {
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		StudentRecord record2 = new StudentRecord(JMBAG.get(record), FIRST_NAME.get(record), LAST_NAME.get(record), "2");
		assertTrue(allEqual(record, record2));
	}
	
	@Test
	public void test2() {
		StudentRecord record = new StudentRecord("0000006789", "Zinedine", "Zidane", "3");
		StudentRecord record2 = new StudentRecord(JMBAG.get(record), FIRST_NAME.get(record), LAST_NAME.get(record), "2");
		assertTrue(allEqual(record, record2));
	}
	
	@Test
	public void test3() {
		StudentRecord record = new StudentRecord("000000123", "Figo", "Louis", "4");
		StudentRecord record2 = new StudentRecord(JMBAG.get(record), FIRST_NAME.get(record), LAST_NAME.get(record), "2");
		assertTrue(allEqual(record, record2));
	}
	
	@Test
	public void test4() {
		StudentRecord record = new StudentRecord("0012345699", "Jokić", "Joža", "5");
		StudentRecord record2 = new StudentRecord(JMBAG.get(record), FIRST_NAME.get(record), LAST_NAME.get(record), "2");
		assertTrue(allEqual(record, record2));
	}
	
	public static boolean allEqual(StudentRecord record1, StudentRecord record2) {
		if (!record1.getFirstName().equals(record2.getFirstName())) return false;
		if (!record1.getLastName().equals(record2.getLastName())) return false;
		if (!record1.getJmbag().equals(record2.getJmbag())) return false;
		return true;	
	}

}
