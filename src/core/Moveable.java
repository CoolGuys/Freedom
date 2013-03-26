package core;

public interface Moveable {
	public void move(String diretion);
	public boolean checkIfBeingMoved();
	public void tellIfBeingMoved(boolean isMoved);
}
