package org.openRealmOfStars.player.ship.shipdesign;

public final class ShipDesignConsts {

  /**
   * Message when ship design is good to go
   */
  public static final String DESIGN_OK = "Design is OK!";
  
  /**
   * Message when ship is missing engine
   */
  public static final String ENGINE_IS_MISSING = "Engine is missing!\n";
	
  /**
   * Message when ship contains weapons even hull does not allow them.
   */
  public static final String NO_WEAPONS_ALLOWED = "No weapons allowed in ";
  
  /**
   * Message when ship contains more than one jammer
   */
  public static final String MANY_JAMMERS = "Only one jammer is allowed!";
	
  /**
   * Message when ship contains more than one targeting computer
   */
  public static final String MANY_COMPUTERS = "Only one targeting computer"
   + " is allowed!";
	  
  /**
   * The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
   * and so on. Thus, the caller should be prevented from constructing objects
   * of this class, by declaring this private constructor.
   */
  private ShipDesignConsts() {
	// this prevents even the native class from
	// calling this ctor as well :
	throw new AssertionError();
  }

}
