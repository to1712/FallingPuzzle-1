package fallingpuzzle.model.utils;

public class Vector2D {
	
	/*
	 * CUSTOM VECTOR CLASS
	 * IT DOES MOST OF THE OPERATIONS
	 * YOU CAN DO WITH 2D VECTORS
	 */
	
	private double x;
	private double y;
	
	//constructors
	public Vector2D() {
		this.x = 0.0;
		this.y = 0.0;
	}
	
	public Vector2D( Vector2D vec )  {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public Vector2D( float x, float y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D( double x, double y ) {
		this.x = x;
		this.y = y;
	}
		
	//METHODS WHICH RETURN NEW OBJECTS
	public Vector2D Normalize() {
		Vector2D temp = new Vector2D( this );
		final double magnitude = temp.getMagnitude();
		if( temp.x != 0 )
			temp.x /= magnitude;
		if( temp.y != 0 )
			temp.y /= magnitude;
		return temp;
	}
	
	public Vector2D productWithScalar( double scalar ) {
		Vector2D temp = new Vector2D( this.x, this.y );
		temp.x *= scalar;
		temp.y *= scalar;
		return temp;
	}
	
	public double dotProduct( Vector2D vec ) {
		return this.x * vec.x + this.y * vec.y;
		
	}
	
	//METHODS WHICH MODIFY THIS OBJECT
	public Vector2D invertX() {
		this.x = -x;
		return this;
	}
	
	public Vector2D invertY() {
		this.y = -y;
		return this;
	}
	
	public Vector2D invert() {
		this.x = -x;
		this.y = -y;
		return this;
	}
	
	public Vector2D add( Vector2D vec ) {
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
		
	public Vector2D add( double x, double y ) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2D subtract( Vector2D vec ) {
		this.x -= vec.x;
		this.y -= vec.y;
		return this;
	}
	
	
	public Vector2D subtract( double x, double y ) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2D translate( Vector2D direction, double factor ) {
		this.x += direction.getX() * factor;
		this.y += direction.getY() * factor;
		return this;
	}
		
	public double distance( Vector2D vec ) {
		return Math.sqrt( ( vec.x - x ) * ( vec.x - x ) + ( vec.y - y ) * ( vec.y - y ) );
	}
	
	public double distance( double x, double y ) {
		return Math.sqrt( ( x - this.x ) * ( x - this.x) + ( y - this.y ) * ( y - this.y ) );
	}
	
	public Vector2D round() {
		this.x = Math.round( x );
		this.y = Math.round( y );
		return this;
	}
	
	//rounds to float, pretty useless tbh
	public Vector2D floatIt() {
		this.x = ( float ) this.x;
		this.y = ( float ) this.y;
		return this;
	}
	
	public double getMagnitude() {
		return Math.sqrt( x * x + y * y );
	}
	
	public void setX( double x ) { this.x = (float) x; }
	public void setY( double y ) { this.y = (float) y; }
	public double getX() { return x; }
	public double getY() { return y; }
	
}
