import java.util.Calendar;
import java.util.Date;

//Mustafa Said Çanak	150120020
public class SmartLight extends SmartObject implements LocationControl, Programmable {
	private boolean hasLightTurned, programAction;
	private Calendar programTime;
	private Date date;

	public SmartLight(String alias, String macId) {
		super(alias, macId);
		setProgramTime(programTime);
	}

	public void turnOnLight() {

		if (isConnectionStatus()) {
			if (hasLightTurned == false) {
				this.hasLightTurned = true;
				this.programAction = true;
				System.out.println(
						"Smart Light - " + getAlias() + " is turned on now (Current time: " + getCurrentTime() + ")");
			} else if (hasLightTurned == true) {
				System.out.println("Smart Light - " + getAlias() + " has been already turned on");
			}
		}
	}

	public void turnOffLight() {
		if (isConnectionStatus()) {
			if (hasLightTurned == true) {
				this.hasLightTurned = false;
				this.programAction = false;
				System.out.println(
						"Smart Light - " + getAlias() + " is turned off now (Current time: " + getCurrentTime() + ")");
			} else if (hasLightTurned == false) {
				System.out.println("Smart Light - " + getAlias() + " has been already turned off");
			}
		}

	}

	public String getCurrentTime() {
		date = new Date();
		programTime = Calendar.getInstance();
		programTime.setTime(date);
		String hour = programTime.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + programTime.get(Calendar.HOUR_OF_DAY)
				: "" + programTime.get(Calendar.HOUR_OF_DAY);
		String minutes = programTime.get(Calendar.MINUTE) < 10 ? "0" + programTime.get(Calendar.MINUTE)
				: "" + programTime.get(Calendar.MINUTE);
		String seconds = programTime.get(Calendar.SECOND) < 10 ? "0" + programTime.get(Calendar.SECOND)
				: "" + programTime.get(Calendar.SECOND);
		return hour + ":" + minutes + ":" + seconds;

	}

	@Override
	public void setTimer(int seconds) {
		if (isConnectionStatus()) {
			date = new Date();
			programTime = Calendar.getInstance();
			programTime.setTime(date);
			if (isHasLightTurned() == false) {
				System.out.println("Smart Light - " + getAlias() + " will be turned on " + seconds + " seconds later!");
				System.out.println("(Current time: " + getCurrentTime() + ")");
				programTime = addTime(programTime, seconds);
			} else if (isHasLightTurned() == true) {
				System.out
						.println("Smart Light - " + getAlias() + " will be turned off " + seconds + " seconds later!");
				System.out.println("(Current time: " + getCurrentTime() + ")");
				programTime = addTime(programTime, seconds);
			}
		}

	}

	public Calendar addTime(Calendar programTime, int seconds) {
		int newSeconds = seconds % 60;
		int remainingValue = seconds / 60;
		int newMinutes = remainingValue % 60;
		remainingValue /= 60;
		int newHours = remainingValue % 24;
		programTime.add(Calendar.SECOND, newSeconds);
		programTime.add(Calendar.MINUTE, newMinutes);
		programTime.add(Calendar.HOUR_OF_DAY, newHours);
		return programTime;
	}

	@Override
	public void cancelTimer() {
		if (isConnectionStatus() == true) {
			setProgramTime(null);
		}
	}

	@Override
	public void runProgram() {
		if (isConnectionStatus() == true) {
			date = new Date();
			Calendar currentTime = Calendar.getInstance();
			currentTime.setTime(date);
			if (isSameTime(currentTime, getProgramTime())) {
				System.out.println("Run Program -> Smart Light - " + getAlias());
				if (programAction == true) {
					turnOnLight();
				} else if (programAction == false) {
					turnOffLight();
				}
			}
			setProgramTime(null);
		}
	}

	public boolean isSameTime(Calendar cal1, Calendar cal2) {
		// check if they are in the same time
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
				&& cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND));
	}

	@Override
	public void onLeave() {
		if (isConnectionStatus()) {
			System.out.println("On Come -> SmartLight - " + getAlias());
			turnOffLight();
		}

	}

	@Override
	public void onCome() {
		if (isConnectionStatus()) {
			System.out.println("On Leave -> SmartLight - " + getAlias());
			turnOnLight();
		}

	}

	@Override
	public boolean testObject() {
		if (isConnectionStatus()) {
			SmartObjectToString();
			turnOnLight();
			turnOffLight();
			System.out.println("Test completed for SmartLight");
			return true;
		} else
			return false;
	}

	@Override
	public boolean shutDownObject() {
		if (isConnectionStatus()) {
			SmartObjectToString();
			if (isHasLightTurned())
				turnOffLight();
			return true;
		} else
			return false;
	}

	public boolean isHasLightTurned() {
		return hasLightTurned;
	}

	public void setHasLightTurned(boolean hasLightTurned) {
		this.hasLightTurned = hasLightTurned;
	}

	public boolean isProgramAction() {
		return programAction;
	}

	public void setProgramAction(boolean programAction) {
		this.programAction = programAction;
	}

	public Calendar getProgramTime() {
		return programTime;
	}

	public void setProgramTime(Calendar programTime) {
		this.programTime = programTime;
	}

}
