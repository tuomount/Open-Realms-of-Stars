package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.Coordinate;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Test for FleetList class
 */
import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;

public class FleetListTest {
	
	@Test
	public void constructorTest() {
		FleetList fleetlist = new FleetList();
		assertEquals(0, fleetlist.getNumberOfFleets()); 
		assertNull(fleetlist.getCurrent());
	}

	@Test
	public void addtest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();
		fleetlist.add(fleet1);
		assertEquals(0, fleetlist.getIndex());
		assertEquals(fleet1, fleetlist.getCurrent());

		fleetlist.add(fleet2);
		assertEquals(0, fleetlist.getIndex());
		assertEquals(fleet1, fleetlist.getCurrent());
	}

	@Test
	public void removeTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		fleetlist.add(fleet1); // 0
		fleetlist.add(fleet2); // 1
		fleetlist.add(fleet3); // 2

		// indexToremove =< index
		fleetlist.remove(0);
		assertEquals(2, fleetlist.getNumberOfFleets());
		assertEquals(fleet2, fleetlist.getCurrent());
		// indexToremove > index
		fleetlist.remove(1);
		assertEquals(1, fleetlist.getNumberOfFleets());
		assertEquals(fleet2, fleetlist.getCurrent());

	}

	@Test
	public void getNextTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList(); //

		assertNull(fleetlist.getNext());
		fleetlist.add(fleet1);
		assertEquals(fleet1, fleetlist.getNext()); 

		fleetlist.add(fleet2);
		fleetlist.add(fleet3);
		assertEquals(fleet1, fleetlist.getCurrent());
		assertEquals(fleet2, fleetlist.getNext());
		assertEquals(fleet3, fleetlist.getNext());
		assertEquals(fleet1, fleetlist.getNext());

	}

	@Test
	public void getFirstTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList(); //

		assertNull(fleetlist.getFirst());
		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		assertEquals(fleet1, fleetlist.getFirst());
	}

	@Test
	public void getPrevTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList(); //

		assertNull(fleetlist.getPrev());
		fleetlist.add(fleet1);
		assertEquals(fleet1, fleetlist.getPrev());

		fleetlist.add(fleet2);
		fleetlist.add(fleet3);
		assertEquals(fleet1, fleetlist.getCurrent());
		assertEquals(fleet3, fleetlist.getPrev());
		assertEquals(fleet2, fleetlist.getPrev());
		assertEquals(fleet1, fleetlist.getPrev());
	}

	@Test
	public void getByIndexTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		assertNull(fleetlist.getByIndex(0));
		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		fleetlist.add(fleet3);// index=0,
		assertNull(fleetlist.getByIndex(3));
		assertNull(fleetlist.getByIndex(-1));
		assertEquals(fleet2, fleetlist.getByIndex(1));

	}

	@Test
	public void getByNameTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		assertNull(fleetlist.getByName("Fleet #1"));
		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		fleetlist.add(fleet3);
		assertEquals(fleet1, fleetlist.getByName("Fleet #1"));
		assertNotEquals(fleet1, fleetlist.getByName("Fleet #2"));

	}

	@Test
	public void getIndexByNameTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		assertEquals(-1, fleetlist.getIndexByName("Fleet #1"));
		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		fleetlist.add(fleet3);
		assertEquals(0, fleetlist.getIndexByName("Fleet #1"));
		assertNotEquals(3, fleetlist.getIndexByName("Fleet #4")); // i >
																	// fleetlist.size
		assertNotEquals(0, fleetlist.getIndexByName("Fleet #2")); // NOT
																	// fleet.getName().equals

	}

	@Test
	public void recalculateListTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();
		
		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		fleetlist.add(fleet3);
		fleetlist.remove(0);
		fleetlist.remove(0);
		
		fleetlist.recalculateList();
		

	}

	@Test
	public void howManyFleetWithStartingNamesTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		fleetlist.add(fleet1);
		fleetlist.add(fleet2);
		fleetlist.add(fleet3);

		assertEquals(3, fleetlist.howManyFleetWithStartingNames("Fleet"));
		assertEquals(0, fleetlist.howManyFleetWithStartingNames("abc"));
	}

	@Test
	public void isUniqueNameTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();

		fleetlist.add(fleet1);
		fleetlist.add(fleet2);

		assertTrue(fleetlist.isUniqueName("abc", null));
		assertEquals(false, fleetlist.isUniqueName("fleet #1", null));
		assertEquals(true, fleetlist.isUniqueName("fleet #1", fleet1));
		assertEquals(false, fleetlist.isUniqueName("fleet #1", fleet2));
	}

	@Test
	public void generateUniqueNameTest() {
		Fleet fleet1 = Mockito.mock(Fleet.class);
		Mockito.when(fleet1.getName()).thenReturn("Fleet #1");
		Fleet fleet2 = Mockito.mock(Fleet.class);
		Mockito.when(fleet2.getName()).thenReturn("Fleet #2");
		Fleet fleet3 = Mockito.mock(Fleet.class);
		Mockito.when(fleet3.getName()).thenReturn("Fleet #3");
		FleetList fleetlist = new FleetList();
		
		assertEquals("Fleet #1",fleetlist.generateUniqueName());
		fleetlist.add(fleet1); //2
		fleetlist.add(fleet2); //3
		assertEquals("Fleet #3",fleetlist.generateUniqueName());
		
		fleetlist.add(fleet3);//4
		fleetlist.add(fleet3);//5
		fleetlist.add(fleet3);//6
		assertEquals("Fleet #6",fleetlist.generateUniqueName());
		
	}
}
