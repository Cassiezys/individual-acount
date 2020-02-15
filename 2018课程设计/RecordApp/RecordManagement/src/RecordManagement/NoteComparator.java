package RecordManagement;

import java.util.Comparator;

public class NoteComparator implements Comparator<Note> {

	@Override
	public int compare(Note o1, Note o2) {
		// TODO Auto-generated method stub
		if (o1.getYear() == o2.getYear() && o1.getMonth() == o2.getMonth() && o1.getDay() == o2.getDay())
			return 0;
		if (o1.getYear() < o2.getYear()) {
			return -1;
		} else if (o1.getYear() == o2.getYear()) {
			if (o1.getMonth() < o2.getMonth()) {
				return -1;
			} else if (o1.getMonth() == o2.getMonth()) {
				if (o1.getDay() < o2.getDay()) {
					return -1;
				} else {
					return 1;
				}
			} else
				return 1;
		} else {
			return 1;
		}

	}

}
