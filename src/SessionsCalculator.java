import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SessionsCalculator {

	public static long totalsessions = 0;
	public static long validsessions = 0;
	public static double averagesessiontime=0;
	public static Session currentSession;
	public static List<Session> sessionList = new ArrayList<>();

	public static void processInput(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		Timestamp ts = null;
		String tsstr = null;
		EventName e = null;
		while (line != null) {
			if (line.startsWith("ggstart")) {
				e = EventName.ggstart;
				tsstr = line.substring(8);
				ts = Timestamp.valueOf(tsstr);
				processNewSession(new Session(e, ts, null));
			} else if (line.startsWith("ggstop")) {
				e = EventName.ggstop;
				tsstr = line.substring(7);
				ts = Timestamp.valueOf(tsstr);
				processNewSession(new Session(e, null, ts));
			}
			line = br.readLine();
		}
		if (getDifference(currentSession.getStarttimestamp(), currentSession.getEndtimestamp()) >= 60) {
			averagesessiontime=(averagesessiontime*totalsessions+getDifference(currentSession.getStarttimestamp(), currentSession.getEndtimestamp()))/(totalsessions+1);
			validsessions++;
			totalsessions++;
		} else
			totalsessions++;
		br.close();
	}

	public static void processNewSession(Session newSession) {

		if (currentSession == null) {
			currentSession = newSession;
		} else {
			if (newSession.getEventname() == EventName.ggstart) {
				if (getDifference(currentSession.getEndtimestamp(), newSession.getStarttimestamp()) <= 30) {
					currentSession.setEndtimestamp(null);
				} else {
					if (getDifference(currentSession.getStarttimestamp(), currentSession.getEndtimestamp()) >= 60) {
						averagesessiontime=(averagesessiontime*totalsessions+getDifference(currentSession.getStarttimestamp(), currentSession.getEndtimestamp()))/(totalsessions+1);
						validsessions++;
						totalsessions++;
					} else
						totalsessions++;
					currentSession = newSession;
				}
			} else if (newSession.getEventname() == EventName.ggstop) {
				if (getDifference(currentSession.getStarttimestamp(), newSession.getEndtimestamp()) <= 1) {
					currentSession = null;
				} else {
					currentSession.setEndtimestamp(newSession.getEndtimestamp());

				}
			}
		}

	}

	public static long getDifference(Timestamp oldTime, Timestamp newTime) {
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = newTime.getTime();
		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		return diffSeconds;
	}

	public static void main(String args[]) {
		try {
			processInput("C:\\Users\\Ramniwas\\workspace\\Assignment\\src\\Session.txt");
		} catch (IOException e) {
			System.out.println("Invalid Input Exception is: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Total Sessions : " + totalsessions);
		System.out.println("Valid Sessions : " + validsessions);
		System.out.println("Average Session Time : "+averagesessiontime+" seconds");
	}
}
