import java.sql.Timestamp;

public class Session {

	private EventName eventname;
	private Timestamp starttimestamp;
	private Timestamp endtimestamp;

	public EventName getEventname() {
		return eventname;
	}

	public void setEventname(EventName eventname) {
		this.eventname = eventname;
	}

	public Timestamp getStarttimestamp() {
		return starttimestamp;
	}

	public void setStarttimestamp(Timestamp starttimestamp) {
		this.starttimestamp = starttimestamp;
	}

	public Timestamp getEndtimestamp() {
		return endtimestamp;
	}

	public void setEndtimestamp(Timestamp endtimestamp) {
		this.endtimestamp = endtimestamp;
	}

	public Session(EventName eventname, Timestamp starttimestamp, Timestamp endtimestamp) {
		super();
		this.eventname = eventname;
		this.starttimestamp = starttimestamp;
		this.endtimestamp = endtimestamp;
	}

}
