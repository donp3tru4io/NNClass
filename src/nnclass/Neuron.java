/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnclass;

import java.util.ArrayList;

/**
 *
 * @author donp3tru4io
 */
public class Neuron {
    
    private ArrayList<Chain> input,output;
    private double value,dW;
    
    public Neuron()
    {
        input = new ArrayList<Chain>();
        output = new ArrayList<Chain>();
    }
    
    public void addInputChain(Chain chain)
    {
        input.add(chain);
    }
    
    public void addOutputChain(Chain chain)
    {
        output.add(chain);
    }
    
    public ArrayList<Chain> getInputChain()
    {
        return input;
    }
    
    public ArrayList<Chain> getOutputChain()
    {
        return output;
    }
    
    public double getValue()
    {
        return value;
    }
    
    public double calcValue()
    {
        value = 0;
        
        for (int i = 0; i < input.size();i++)
        {
            value+= input.get(i).getWeight()*input.get(i).getA().getValue();
        }
        
        value = f(value);
        
        return value;
    }
    
    public void setValue(double v)
    {
        value = v;
    }
    
    private double f(double x)
    {
        return 1.0/(1+Math.exp(-x));
    }
    
    private double fdx(double x)
    {
        return f(x)*(1 - f(x));
    }
    
    public double calcdW(double error)
    {
        dW = error * fdx(error);
        return dW;
    }
    
    public double getdW()
    {
        return dW;
    }
}
