/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnclass;

/**
 *
 * @author donp3tru4io
 */
public class Chain {
    
    private Neuron a,b;
    private double weight;
    
    public Chain(Neuron A, Neuron B)
    {
        a = A;
        b = B;
    }
    
    public double changeWeight(double learningRate)
    {
        
        weight -= a.getValue()*b.getdW()*learningRate;
        return weight;
    }

    public Neuron getA()
    {
        return a;
    }
    
    public Neuron getB()
    {
        return b;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public void setWeight(double w)
    {
        weight = w;
    }
    
}
