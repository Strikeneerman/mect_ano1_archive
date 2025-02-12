/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MSharedAreaOne;

/**
 *
 * @author omp
 */
public class MSharedAreaOne implements IMSharedAreaOne {
    
    private static IMSharedAreaOne areaOne;
    
    private MSharedAreaOne() {
    }
    public static IMSharedAreaOne getInstance() {
        // singleton
        if ( areaOne == null )
            areaOne = new MSharedAreaOne();
        return areaOne;
    }
    @Override
    public void mOneOne_1() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void mOneOne_2() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void mOneTwo_1() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public boolean mOneTwo_2() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
