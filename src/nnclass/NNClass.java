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
public class NNClass {

    /**
     * @param args the command line arguments
     */
    
     class A
     {
         private A f;
         public A()
         {
             
         }
         
     }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        double[][] input = new double[][]
        {
            {0,0,0},
            {0,0,1},
            {0,1,0},
            {0,1,1},
            {1,0,0},
            {1,0,1},
            {1,1,0},
            {1,1,1}
        };
        
        double[] expectation = new double[]{0,1,0,0,1,1,0,1};
        
        int[] hiddenLayers = new int[]{2};
        
        NeuralNetwork nn = new NeuralNetwork(3,1,hiddenLayers);
        nn.train(input, expectation, 5000,0.08);
        
        
        
        
    }
    
}
