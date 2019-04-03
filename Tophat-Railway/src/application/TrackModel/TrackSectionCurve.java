package application.TrackModel;

import java.util.Map;

public class TrackSectionCurve extends TrackSection {

	final private double radius;
	final private double centerX;
	final private double centerY;
	final private boolean isClockwise;

	public TrackSectionCurve(String lineName, char sectionID, int firstBlockID, double startX, double startY,
			double endX, double endY, TrackJunction junctionA, TrackJunction junctionB, Map<Integer, TrackBlock> blocks,
			double radius, double centerX, double centerY, boolean isClockwise) {
		super(lineName, sectionID, firstBlockID, startX, startY, endX, endY, junctionA, junctionB, blocks);
		this.radius = radius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.isClockwise = isClockwise;
	}

	@Override
	void calculateCoordinates(TrainLocation trainLocation) {
		if (trainLocation.getSectionID() != sectionID)
			throw new IllegalArgumentException(
					"Train: " + trainLocation.getTrainID() + " is not on Section: " + sectionID);

		int currentBlockID = trainLocation.getBlockID();
		if (!blocks.containsKey(currentBlockID))
			throw new IllegalArgumentException(
					"Section: " + sectionID + " does not contain Block: " + trainLocation.getBlockID());

		TrackBlock currentBlock = getBlock(trainLocation.getBlockID());
		double blockDisplacement = trainLocation.getBlockDisplacement();
		if (currentBlock.getLength() < blockDisplacement)
			throw new IllegalArgumentException("displacement cannot be larger than block length");
		else if (blockDisplacement < 0.0)
			throw new IllegalArgumentException("displacement cannot be negative");

		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID < currentBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		sectionDisplacement += blockDisplacement;

		double angle = Math.atan2(startY - centerY, startX - centerX);

		if (isClockwise)
			angle = angle - sectionDisplacement / radius;
		else
			angle = angle + sectionDisplacement / radius;

		double coordX = centerX + radius * Math.cos(angle);
		double coordY = centerY + radius * Math.sin(angle);
		trainLocation.setCoordinates(coordX, coordY);

	}

	public double getRadius() {
		return radius;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public boolean isClockwise() {
		return isClockwise;
	}

}
