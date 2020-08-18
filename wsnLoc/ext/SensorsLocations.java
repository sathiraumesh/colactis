/*------------------------------------------------------------------------
 * 
 * this file is part of the PSO methods for WSN Localization.
 *  
 * Copyright (c) 2013-2014, Hussein S. Al-Olimat.
 * 
 *------------------------------------------------------------------------ 
 *
 * This file is part of clocacits: a set of computational intelligence 
 * methods implemented using Java for multi-objective multi-level 
 * optimization problems. 
 * 
 * clocacits contains the implementations of methods proposed in a master 
 * thesis entitled: Optimizing Cloudlet Scheduling and Wireless Sensor 
 * Localization using Computational Intelligence Techniques. 
 * Thesis by: Hussein S. Al-Olimat, the University of Toledo, July 2014. 
 * 
 * clocacits is a free library: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * clocacits is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with clocacits.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *------------------------------------------------------------------------
 */

package wsnLoc.ext;

import java.io.*;
import java.util.*;

public class SensorsLocations {

	public static int AnchorNode = 1;
	public static int SensorNode = 2;
	
	public static int maxXindex = 1000;
	public static int maxYindex = 1000;
	
	public static int numberOfSensors = 200;
	public static int numberOfAnchors = 40;
	
	@SuppressWarnings("unchecked")
	public ArrayList<Node> ReadLocations(String FileName) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(FileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        ArrayList<Node> nodesList = new ArrayList<Node>();
        
        nodesList = (ArrayList<Node>) ois.readObject();
        
        ois.close();
        
        return nodesList;
	}

	public static void main(String[] args) {
		genrateSensorLocations();
	}


	public static void genrateSensorLocations(){
		ArrayList<Node> nodesList = new ArrayList<Node>();
		Map<Integer,Integer> cordinates = new TreeMap<Integer,Integer>();
		Random random = new Random();
		while (cordinates.size()<numberOfSensors){

			int x = random.nextInt(maxXindex-0);
			int y = random.nextInt(maxYindex-0);

			cordinates.put(x,y);

		}

		int nodeId = 0;
		for (Map.Entry<Integer,Integer> entry : cordinates.entrySet()){
			Node node = new Node();
			node.x=entry.getKey();
			node.y=entry.getValue();
			node.id =nodeId;
			node.type=SensorNode;
			nodesList.add(node);
			nodeId++;
			System.out.println("Key = " + entry.getKey() +
					", Value = " + entry.getValue());
	}

		for (int i = 0; i <numberOfAnchors ; i++) {

				nodesList.get(i).type=AnchorNode;
		}

		FileOutputStream f = null;
		ObjectOutputStream o = null ;

		try {
			f = new FileOutputStream(new File("200_sensor_40_anchor_1000_area.wsnLoc"));
			o = new ObjectOutputStream(f);

				o.writeObject(nodesList);



			o.close();

		} catch (Exception e) {

			e.printStackTrace();

		}finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


}
	
	//R1 -- based on Time-bounded paper / no longer used .. instead ZigBee 3 ranges are used
	public int getMinimumRange(){
		
		double r1 = Math.sqrt(Math.pow(maxXindex, 2)/numberOfSensors);
		
		return (int)r1;
	}
	
	//R2
	public int getMaximumRange(){
		double r2 = Math.sqrt(Math.pow(maxXindex, 2) * 4/numberOfSensors);
		
		return (int)r2;
	}
}
