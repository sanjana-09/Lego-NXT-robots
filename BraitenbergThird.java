import ShefRobot.*;
import java.io.*;
import java.util.Scanner;

public class BraitenbergThird {
	public static void main(String[] args) throws InterruptedException {
		//Create a robot object to use and connect to it
        Robot myRobot = new Robot();
		int topCap = 700;
        Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
        Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
        ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S1);
		UltrasonicSensor leftSensor = myRobot.getUltrasonicSensor(Sensor.Port.S4);


    	Scanner scan = new Scanner( System.in );
		
		//distance and light
		double leftDistance = leftSensor.getDistance();
		double lightIntensity = colorSensor.getAmbient();
		
		//normalise
		double leftReading = 1.7 - Math.min(1.7,leftDistance);

		
		//r = f(intensity)
		double intention = 1.6 - lightIntensity;

		//motivation
		double motivation;
		System.out.print("Enter a number from 0 to 1:");
    	motivation = scan.nextFloat();
		System.out.println("Motivation:" + motivation);
	
		//e= r-i
		double error = intention - leftReading;

		int baseSpeed = 100;
		int leftV = baseSpeed;
		int rightV = baseSpeed;
		
		//left speed
		
		if (error >0) {
			leftV = (int)Math.round(baseSpeed + 1000*motivation*Math.abs(error));
			leftV = Math.min(leftV, topCap);
		}
		
		//right speed
		if (error < 0) {
			rightV = (int)Math.round(baseSpeed + 1000*motivation*Math.abs(error));
			rightV = Math.min(rightV, topCap);
		}
		
		while (true) {
			
			//get distance
			leftDistance = leftSensor.getDistance();
			lightIntensity = colorSensor.getAmbient();

			
			//normalise both
			leftReading = 1.7 - Math.min(1.7,leftDistance);
			
			intention = 1.6 - lightIntensity;
			
			//e = r-i
			error = intention - leftReading;
			
		if (error > 0) {
			leftV = (int)Math.round(baseSpeed + 1000*motivation*Math.abs(error));
			leftV = Math.min(leftV, topCap);
		}
		
		//right speed
		if (error < 0) {
			rightV = (int)Math.round(baseSpeed + 1000*motivation*Math.abs(error));
			rightV = Math.min(rightV, topCap);
		}
		
			//set speeds
			leftMotor.setSpeed(leftV);
			rightMotor.setSpeed(rightV);
			
			//left move
			leftMotor.forward();
			System.out.println("left unhappy");
			System.out.println("leftspeed: " + leftV);

	
			//right move
			rightMotor.forward();
			System.out.println("right unhappy");
			System.out.println("rightspeed: " + rightV);

			
			Thread.sleep(500);
		}
		
	}
	
	//works like braitenberg for r = 2i
}