package com.example.demo.dtos;

public class SeatResponse {
	private int seatId;
	private String seatNumber;
	private String _class;
	
	public SeatResponse() {
		seatId = 0;
		seatNumber = "";
		_class = "";
	}
	
	public SeatResponse(int seatId, String seatNumber, String _class) {
		super();
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this._class = _class;
	}
	
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String get_class() {
		return _class;
	}
	public void set_class(String _class) {
		this._class = _class;
	}
}
