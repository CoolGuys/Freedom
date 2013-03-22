package core;

public interface Movable {
	public void move(String diretion);
	public boolean checkIfBeingMoved();
	public void tellIfBeingMoved(boolean isMoved);
}
