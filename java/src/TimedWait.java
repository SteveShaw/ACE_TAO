/*************************************************
 *
 * = PACKAGE
 *    JACE.ASX
 *
 * = FILENAME
 *    TimedWait.java
 *
 *@author Prashant Jain and Doug Schmidt
 *
 *************************************************/
package JACE.ASX;

public abstract class TimedWait
{
  /**
   * Default Constructor. Sets "this" to be used for the delegation of
   * the wait() call to. 
   */
  public TimedWait ()
  {
    object_ = this;
  }

  /**
   * Constructor. Allows subclasses to supply us with an Object that
   * is delegated the wait() call.
   *@param obj The Object that is delegated the wait() call.
   */
  public TimedWait (Object obj)
  {
    object_ = obj;
  }

  /**
   * Hook method that needs to be implemented by subclasses.
   */
  public abstract boolean condition ();

  /**
   * Wait until condition becomes true. Note that the method
   * blocks. Also note that this method is final to ensure that no one
   * overrides it. 
   * IMPORTANT: This method assumes it is called with the object_'s
   * monitor lock already held.
   *@exception InterruptedException Interrupted during wait
   */
  public final void timedWait () throws InterruptedException
  {
    // Acquire the monitor lock.
    if (!condition ()) 
      {
	// Only attempt to perform the wait if the condition isn't
	// true initially.
	for (;;) 
	  {
	    // Wait until we are notified.
	    object_.wait ();

	  // Recheck the condition.
	  if (condition ()) 
	    break; // Condition became true.
	  // else we were falsely notified so go back into wait
	  }
      }
  }

  /**
   * Template Method that implements the actual timed wait. Note that
   * this method is final to ensure that no one overrides it. 
   * IMPORTANT: This method assumes it is called with the object_'s
   * monitor lock already held.
   * If the specified wait time is zero, this checks the condition,
   * then returns on success or throws a TimeoutException on failure.
   *@param tv Absolute time to wait until before throwing an exception
   * if the condition isn't satisfied
   *@exception java.lang.InterruptedException Interrupted during wait
   *@exception JACE.ASX.TimeoutException Reached timeout specified
   */
  public final void timedWait (TimeValue tv)
    throws InterruptedException,
           TimeoutException 
  {
    if (tv == null) {
	this.timedWait();
	return;
    }

    // Acquire the monitor lock.
    if (!condition ()) 
      {
	long start = System.currentTimeMillis();
	long waitTime = tv.getMilliTime() - start;

	// Safety check since there is a possibility that it is
	// exactly the same time as the tv.  That would cause
	// waitTime to be 0, and since Java's wait(timeout) blocks
	// when timeout is 0, it would mean trouble.
	if (waitTime < 1)
	    throw new TimeoutException();

	for (;;) {
	  // Wait until we are notified.
	  object_.wait (waitTime);

	  // Recheck the condition.
	  if (!condition ()) {

	    long now = System.currentTimeMillis();
	    long timeSoFar = now - start;
	    
	    // Timed out!
	    if (now >= tv.getMilliTime ()) 
	      throw new TimeoutException ();
	    else 
	      // We still have some time left to wait, so adjust the
	      // wait_time.
	      waitTime -= timeSoFar; 
	  }
	  else
	    break;  // Condition became true.
	}
      }
  }

  /**
   * Notify any one thread waiting on the object_.  
   * IMPORTANT: This method assumes it is called with the object_'s
   * monitor lock already held.
   */
  public final void signal () {
    object_.notify ();
  }

  /**
   * Notify all threads waiting on the object_.  
   * IMPORTANT: This method assumes it is called with the object_'s
   * monitor lock already held.
   */
  public final void broadcast () {
    object_.notifyAll ();
  }

  /** 
   * The object we delegate to. If a subclass gives us a particular
   * object,  we use that to delegate to, otherwise, we ``delegate''
   * to ourself (i.e., this).
   */
  protected Object object_;

}
