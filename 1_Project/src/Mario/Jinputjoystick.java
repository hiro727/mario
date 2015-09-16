package Mario;
import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Jinputjoystick {
	    
    private Controller controller;
        
    // Controller buttons states
    private ArrayList<Boolean> buttonsValues;
    
    
    public Jinputjoystick(Controller.Type controllerType){
    	loadNativeLibrary();
        initialize();
        initController(controllerType, null);
    }
    
    /**
     * Creates a controller, of one of the types that has been given.
     * Controller type which is first found will be created.
     * 
     * @param controllerType_1 Desired controller type.
     * @param controllerType_2 Desired controller type.
     */
    public Jinputjoystick(Controller.Type controllerType_1, Controller.Type controllerType_2){
    	loadNativeLibrary();
    	////System.out.println("constructor has been called");
        initialize();
        initController(controllerType_1, controllerType_2);
    }
    
    private void loadNativeLibrary() {
/* 		//System.setProperty("java.library.path", System.getProperty("java.library.path")+Frames.fullPath);
    	String allPath = System.getProperty("java.library.path");
    	String comPath = Frames.fullPath.substring(1, Frames.fullPath.length()-1)+"jinput-dx8.dll";
    	//System.out.println(comPath);
    	allPath += ";"+comPath;
   		System.out.println(allPath);System.out.println();
    	String each[] = allPath.split(";");
    	for(int i=0;i<each.length;i++){
    		System.out.println(each[i]);
    	}
    	System.setProperty("java.library.path", allPath);
		allPath = System.getProperty("java.library.path");
    	String each[] = allPath.split(";");
		for(int i=0;i<each.length;i++){
			System.out.println(each[i]);
		}
		
		System.loadLibrary(Frames.fullPath+"jinput-dx8.dll");
*/	}

	private void initialize(){
    	////System.out.println("initializing");
        this.controller = null;
        this.buttonsValues = new ArrayList<Boolean>();
    }
    
    /**
     * Save first founded controller of given type.
     * 
     * @param controllerType Desired controller type.
     */
    private void initController(Controller.Type controllerType_1, Controller.Type controllerType_2){
    	////System.out.println("initializing controller");
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        //System.out.println("initializing...");
        for(int i=0; i < controllers.length && controller == null; i++) {
            if(controllers[i].getType() == controllerType_1 ||controllers[i].getType() == controllerType_2){
                controller = controllers[i];//System.out.println("controller initialized");
                break;
            }
        }
        
    }
    
    
    
    
    
    
    

    
    
    
    
    
    
    

    
    
    
    
    
    
    

    
    
    
    
    
    
    /**Methods for each action (ie x,y,z-axis rotation,buttons, hat switch)
     */
    //Checks if the controller is connected/valid.
    public boolean isControllerConnected(){
        try {
            return controller.poll();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    /**
     * Gets the controller type.
     */
    public Controller.Type getControllerType(){
    	//System.out.println("getting the controller type");
        return controller.getType();
    }
    
    
    /**
     * Gets the human readable controller name.
     */
    public String getControllerName(){
        return controller.getName();
    }
    
    
    /**
     * Check and save current controller state (controller components values).
     * Must be called every time before using controller state methods (eg. method for x axis value),
     * so that you get latest controller components values.
     * 
     * @return True if controller is connected/valid, false otherwise.
     */
    public boolean pollController(){
    	//System.out.println("check if isn't disconnected");
        boolean isControllerValid;
        //System.out.println(buttonsValues.size());
        // Clear previous values of buttons.
        buttonsValues.clear();
        
        isControllerValid = controller.poll();
        if(!isControllerValid)
            return false;
        
        Component[] components = controller.getComponents();
        
        for(int i=0; i < components.length; i++) {
            Component component = components[i];
            
            
   /*         // Add states of the buttons
            if(component.getIdentifier().toString().equals(0)||component.getIdentifier().toString().equals(1)
            		||component.getIdentifier().toString().equals(2)||component.getIdentifier().toString().equals(3)
            		||component.getIdentifier().toString().equals(8)||component.getIdentifier().toString().equals(9)
            		||component.getIdentifier().toString().equals(12))
    */      if(!component.isAnalog())	
                if(component.getPollData() == 1.0f)
                    buttonsValues.add(Boolean.TRUE);
                else
                    buttonsValues.add(Boolean.FALSE);
            //System.out.println(component.getIdentifier()+"  "+buttonsValues);
        }
        
        return isControllerValid;
    }
    
    
    /**
     * Checks if component with given identifier exists.
     */
    public boolean componentExists(Identifier identifier){
    	//System.out.println("checking if the component exists");
        Component component = controller.getComponent(identifier);
        
        if(component != null)
            return true;
        else
            return false;
    }
    
    
    /**
     * Gets value of component with given identifier.
     */
    public float getComponentValue(Identifier identifier){
        return controller.getComponent(identifier).getPollData();
    }
    

    /**
     * How many buttons does controller have?
     */
    public int getNumberOfButtons(){
        return buttonsValues.size();
    }
    
    /**
     * Controller buttons states. Index of element in array list correspond to 
     * button number on the controller. 
     * If element is true then button is pressed, if element is false then 
     * button is not pressed.
     * 
     * @return Array list of states of all controller buttons.
     */
    public ArrayList<Boolean> getButtonsValues(){
    	//System.out.println("getting buttons values");
        return buttonsValues;
    }
    
    /**
     * Gets value of required button.
     * 
     * @param index Index of a button in array list.
     * @return True if button is pressed, false otherwise.
     */
    public boolean getButtonValue(int index){
    	//System.out.println("cheking number of buttons");
         return buttonsValues.get(index);
    }
    
    
    /**
     * Value of axis named X Axis.
     * 
     * @return X Axis value.
     */
    public float getXAxisValue(){
    	//System.out.println("getting x-axis value");
        Identifier identifier = Component.Identifier.Axis.X;
        return controller.getComponent(identifier).getPollData();
    }
    
    /**
     * Value of axis named X Axis in percentage.
     * Percentages increases from left to right.
     * If idle (in center) returns 50, if joystick axis is pushed to the left 
     * edge returns 0 and if it's pushed to the right returns 100.
     * 
     * @return X Axis value in percentage.
     */
    public int getXAxisPercentage(){
    	//System.out.println("getting x-axis percentage");
        float xAxisValue = this.getXAxisValue();
        int xAxisValuePercentage = (int)((2 - (1 - xAxisValue)) * 100) / 2;
        
        return xAxisValuePercentage;
    }
    
    
    /**
     * Value of axis named Y Axis.
     * 
     * @return Y Axis value.
     */
    public float getYAxisValue(){
    	//System.out.println("getting y-axis value");
        Identifier identifier = Component.Identifier.Axis.Y;
        return controller.getComponent(identifier).getPollData();
    }

    /**
     * Value of axis named Y Axis in percentage.
     * Percentages increases from top to bottom.
     * If idle (in center) returns 50, if joystick axis is pushed to the top 
     * edge returns 0 and if it is pushed to the bottom returns 100.
     * 
     * @return Y Axis value in percentage.
     */
    public int getYAxisPercentage(){
    	//System.out.println("getting y-axis percentage");
        float yAxisValue = this.getYAxisValue();
        int yAxisValuePercentage = (int)((2 - (1 - yAxisValue)) * 100) / 2;
        
        return yAxisValuePercentage;
    }
    
    
    /**
     * Value of axis named Z Rotation.
     * 
     * @return Z Rotation value.
     */
    public float getZRotationValue(){
    	//System.out.println("getting rotation value");
        Identifier identifier = Component.Identifier.Axis.RZ;
        return controller.getComponent(identifier).getPollData();
    }
    
    /**
     * Value of axis named Z Rotation in percentage.
     * Percentages increases from top to bottom.
     * If idle (in center) returns 50, if joystick axis is pushed to the top 
     * edge returns 0 and if it is pushed to the bottom returns 100.
     * 
     * @return Z Rotation value in percentage.
     */
    public int getZRotationPercentage(){
    	//System.out.println("getting rotation percentage");
        float zRotation = this.getZRotationValue();
        int zRotationValuePercentage = (int)((2 - (1 - zRotation)) * 100) / 2;
        
        return zRotationValuePercentage;
    }
    
    
    /**
     * Value of axis named Z Axis.
     * 
     * @return Z Axis value.
     */
    public float getZAxisValue(){
    	//System.out.println("getting z-axis value");
        Identifier identifier = Component.Identifier.Axis.Z;
        return controller.getComponent(identifier).getPollData();
    }
    
    /**
     * Value of axis named Z Axis in percentage.
     * Percentages increases from left to right.
     * If idle (in center) returns 50, if joystick axis is pushed to the left 
     * edge returns 0 and if it's pushed to the right returns 100.
     * 
     * @return Z Axis value in percentage.
     */
    public int getZAxisPercentage(){
    	//System.out.println("getting z-axis percentage");
        float zAxisValue = this.getZAxisValue();
        int zAxisValuePercentage = (int)((2 - (1 - zAxisValue)) * 100) / 2;
        
        return zAxisValuePercentage;
    }
    
    
    /**
     * Value of axis named X Rotation.
     * 
     * @return X Rotation value.
     */
    public float getXRotationValue(){
    	//System.out.println("getting x-rotation value");
        Identifier identifier = Component.Identifier.Axis.RX;
        return controller.getComponent(identifier).getPollData();
    }
    
    /**
     * Value of axis named X Rotation in percentage.
     * Percentages increases from left to right.
     * If idle (in center) returns 50, if joystick axis is pushed to the left 
     * edge returns 0 and if it's pushed to the right returns 100.
     * 
     * @return X Rotation value in percentage.
     */
    public int getXRotationPercentage(){
    	//System.out.println("getting x-rotation percentage");
        float xRotationValue = this.getXRotationValue();
        int xRotationValuePercentage = (int)((2 - (1 - xRotationValue)) * 100) / 2;
        
        return xRotationValuePercentage;
    }
    
    
    /**
     * Value of axis named Y Rotation.
     * 
     * @return Y Rotation value.
     */
    public float getYRotationValue(){
    	//System.out.println("getting y-rotation value");
        Identifier identifier = Component.Identifier.Axis.RY;
        return controller.getComponent(identifier).getPollData();
    }
    
    /**
     * Value of axis named Y Rotation in percentage.
     * Percentages increases from top to bottom.
     * If idle (in center) returns 50, if joystick axis is pushed to the top 
     * edge returns 0 and if it is pushed to the bottom returns 100.
     * 
     * @return Y Rotation value in percentage.
     */
    public int getYRotationPercentage(){
    	//System.out.println("getting y-rotation percentage");
        float yRotationValue = this.getYRotationValue();
        int yRotationValuePercentage = (int)((2 - (1 - yRotationValue)) * 100) / 2;
        
        return yRotationValuePercentage;
    }
    
    
    /**
     * Gets position of the Hat Switch.
     * Float number that is returned by this method correspond with 
     * positions in the JInput class Component.POV.
     * 
     * @return Float number that corresponds with the Hat Switch position.
     */
    public float getHatSwitchPosition(){
    	//System.out.println("getting hat switch position");
        Identifier identifier = Component.Identifier.Axis.POV;
        return controller.getComponent(identifier).getPollData();
    }
    
    
    
    
    
    
    /* Left joystick */
    
    /**
     * X position of left controller joystick.
     * 
     * The same as method getXAxisValue().
     * 
     * @see joystickTest.JInputJoystick#getXAxisValue()
     * 
     * @return Float value (from -1.0f to 1.0f) corresponding to left controller joystick on x coordinate.
     */
    public float getX_LeftJoystick_Value(){
    	//System.out.println("getting x-leftjoystick value");
        return this.getXAxisValue();
    }
    
    /**
     * X position, in percentages, of left controller joystick.
     * 
     * The same as method getXAxisPercentage().
     * 
     * @see joystickTest.JInputJoystick#getXAxisPercentage()
     * 
     * @return Int value (from 0 to 100) corresponding to left controller joystick on x coordinate.
     */
    public int getX_LeftJoystick_Percentage(){
    	//System.out.println("getting x-leftjoystick percentage");
        return this.getXAxisPercentage();
    }
    
    
    /**
     * Y position of left controller joystick.
     * 
     * The same as method getYAxisValue().
     * 
     * @see joystickTest.JInputJoystick#getYAxisValue()
     * 
     * @return Float value (from -1.0f to 1.0f) corresponding to left controller joystick on y coordinate.
     */
    public float getY_LeftJoystick_Value(){
    	//System.out.println("getting y-leftjoystick value");
        return this.getYAxisValue();
    }
    
    /**
     * Y position, in percentages, of left controller joystick.
     * 
     * The same as method getYAxisPercentage().
     * 
     * @see joystickTest.JInputJoystick#getYAxisPercentage()
     * 
     * @return Int value (from 0 to 100) corresponding to left controller joystick on y coordinate.
     */
    public int getY_LeftJoystick_Percentage(){
    	//System.out.println("getting y-leftjoystick percentage");
        return this.getYAxisPercentage();
    }
    
    
    /* Right joystick */

    /**
     * X position of right controller joystick.
     * 
     * The same as method getZAxisValue() if controller type is Controller.Type.STICK. 
     * The same as method getXRotationValue() if controller type is Controller.Type.GAMEPAD.
     * 
     * @see joystickTest.JInputJoystick#getZAxisValue()
     * @see joystickTest.JInputJoystick#getXRotationValue()
     * 
     * @return Float value (from -1.0f to 1.0f) corresponding to right controller joystick on x coordinate.
     */
    public float getX_RightJoystick_Value(){
    	//System.out.println("getting x-rightjoystick value");
        float xValueRightJoystick;
        
        // stick type controller
        if(this.controller.getType() == Controller.Type.STICK)
        {
            xValueRightJoystick = this.getZAxisValue();
        }
        // gamepad type controller
        else
        {
            xValueRightJoystick = this.getXRotationValue();
        }
        
        return xValueRightJoystick;
    }
    
    /**
     * X position, in percentages, of right controller joystick.
     * 
     * The same as method getZAxisPercentage() if controller type is Controller.Type.STICK. 
     * The same as method getXRotationPercentage() if controller type is Controller.Type.GAMEPAD.
     * 
     * @see joystickTest.JInputJoystick#getZAxisPercentage()
     * @see joystickTest.JInputJoystick#getXRotationPercentage()
     * 
     * @return Int value (from 0 to 100) corresponding to right controller joystick on x coordinate.
     */
    public int getX_RightJoystick_Percentage(){
    	//System.out.println("getting x-rightjoystick percentage");
        int xValueRightJoystickPercentage;
        
        // stick type controller
        if(this.controller.getType() == Controller.Type.STICK)
        {
            xValueRightJoystickPercentage = this.getZAxisPercentage();
        }
        // gamepad type controller
        else
        {
            xValueRightJoystickPercentage = this.getXRotationPercentage();
        }
        
        return xValueRightJoystickPercentage;
    }
    
    
    /**
     * Y position of right controller joystick.
     * 
     * The same as method getZRotationValue() if controller type is Controller.Type.STICK. 
     * The same as method getYRotationValue() if controller type is Controller.Type.GAMEPAD.
     * 
     * @see joystickTest.JInputJoystick#getZRotationValue()
     * @see joystickTest.JInputJoystick#getYRotationValue()
     * 
     * @return Float value (from -1.0f to 1.0f) corresponding to right controller joystick on y coordinate.
     */
    public float getY_RightJoystick_Value(){
    	//System.out.println("getting y-rightjoystick value");
        float yValueRightJoystick;
        
        // stick type controller
        if(this.controller.getType() == Controller.Type.STICK)
        {
            yValueRightJoystick = this.getZRotationValue();
        }
        // gamepad type controller
        else
        {
            yValueRightJoystick = this.getYRotationValue();
        }
        
        return yValueRightJoystick;
    }
    
    /**
     * Y position, in percentages, of right controller joystick.
     * 
     * The same as method getZRotationPercentage() if controller type is Controller.Type.STICK. 
     * The same as method getYRotationPercentage() if controller type is Controller.Type.GAMEPAD.
     * 
     * @see joystickTest.JInputJoystick#getZRotationPercentage()
     * @see joystickTest.JInputJoystick#getYRotationPercentage()
     * 
     * @return Int value (from 0 to 100) corresponding to right controller joystick on y coordinate.
     */
    public int getY_RightJoystick_Percentage(){
    	//System.out.println("getting y-rightjoystick percentage");
        int yValueRightJoystickPercentage;
        
        // stick type controller
        if(this.controller.getType() == Controller.Type.STICK)
        {
            yValueRightJoystickPercentage = this.getZRotationPercentage();
        }
        // gamepad type controller
        else
        {
            yValueRightJoystickPercentage = this.getYRotationPercentage();
        }
        
        return yValueRightJoystickPercentage;
    }
}
