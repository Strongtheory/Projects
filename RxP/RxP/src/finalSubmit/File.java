/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brian Surber
 */
public class File {
    
    String name;
    byte[] data;
    
    public File(String name, byte[] data)   {
        this.name = name;
        this.data = data;
    }
    
    public String getName()    {
        return name;
    }
    
    public byte[] getData() {
        return data;
    }
}
