/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MSharedAreaTwo;

/**
 *
 * @author omp
 */
public class MSharedAreaTwo implements IMSharedAreaTwo {
    
    private static IMSharedAreaTwo areaTwo;
    
    private MSharedAreaTwo() {
    }
    public static IMSharedAreaTwo getInstance() {
        // singleton
        if ( areaTwo == null )
            areaTwo = new MSharedAreaTwo();
        return areaTwo;
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
